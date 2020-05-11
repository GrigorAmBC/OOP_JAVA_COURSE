package adapter;

import model.entity.Message;
import model.entity.User;
import model.port.ClientCommunication;
import model.port.Repository;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SerializedClientCommunication implements ClientCommunication {
  private Socket socket;
  private ObjectInputStream in;
  private ObjectOutputStream out;
  private Repository repository;
  private Message lastMessage = null;
  private ExecutorService pool = Executors.newFixedThreadPool(1);
  private Future<?> future;
  private User user;
  private volatile boolean userDisconnect = false;

  public SerializedClientCommunication(@NotNull Socket socket, @NotNull Repository repository) {
    this.socket = socket;
    this.repository = repository;
    try {
      out = new ObjectOutputStream(socket.getOutputStream());
      in = new ObjectInputStream(socket.getInputStream());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void login() throws Exception {
    if (userDisconnect) {
      throw new Exception("Cannot login user. User disconnected.");
    }
    user = (User) in.readObject();
    repository.loginUser(user);
  }

  @Override
  public void updateUserList() throws Exception {
    if (userDisconnect) {
      throw new Exception("Cannot update user list. User disconnected.");
    }
    out.writeObject(Request.get_users.toString());
    out.writeObject(repository.getUsers());
  }

  @Override
  public void updateMessages() throws Exception {
    if (userDisconnect) {
      throw new Exception("Cannot update messages. User disconnected.");
    }

    out.writeObject(Request.get_messages.toString());
    List<Message> messages = repository.getMessages(lastMessage);
    out.writeObject(messages);
    if (!messages.isEmpty()) {
      lastMessage = messages.get(messages.size() - 1);
    }
  }

  @Override
  public void readUserRequest() throws Exception {
    if (userDisconnect) {
      throw new Exception("Cannot read user request. User disconnected.");
    }

    if (future == null || future.isDone()) {
      future = pool.submit(() -> {
        try {
          if (in.readObject().toString().equals(Request.post_message.toString())) {
            repository.postMessage((Message) in.readObject());
          }
        } catch (Exception e) {
          e.printStackTrace();
          this.userDisconnect = true;
        }
      });
    }
  }

  @Override
  public void disconnectUser() {
    userDisconnect = true;
    repository.logoutUser(user);
    try {
      in.close();
      out.close();
      socket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
