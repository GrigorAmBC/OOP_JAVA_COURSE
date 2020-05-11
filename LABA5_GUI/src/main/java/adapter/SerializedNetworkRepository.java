package adapter;

import model.Model;
import model.entity.Message;
import model.entity.User;
import model.port.Repository;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SerializedNetworkRepository extends Thread implements Repository {
  private User user;
  private Socket socket;
  private ObjectInputStream in;
  private ObjectOutputStream out;
  private Model<List<Message>> messageListModel = new Model<>(new ArrayList<>());
  private Model<List<User>> userListModel = new Model<>(new ArrayList<>());
  private ExecutorService pool = Executors.newFixedThreadPool(1);

  public SerializedNetworkRepository(User user) {
    this.user = user;
    connect();
  }

  @Override
  public void run() {
    try {
      out.writeObject(user);
      while (true) {
          processRequest((String) in.readObject());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void processRequest(String text) throws IOException, ClassNotFoundException {
    if (text.equals(Request.get_users.toString())) {
      getUserListModel().getProperty().clear();
      List<User> users = (ArrayList) in.readObject();
      if (users != null)
        getUserListModel().getProperty().addAll(users);
      userListModel.setProperty(userListModel.getProperty());
    } else if (text.equals(Request.get_messages.toString())) {
      List<Message> messages = (ArrayList) in.readObject();
      if (messages != null)
        messageListModel.getProperty().addAll(messages);
      messageListModel.setProperty(messageListModel.getProperty());
    }
  }

  @Override
  public Model<List<User>> getUserListModel() {
    return userListModel;
  }

  @Override
  public Model<List<Message>> getMessageListModel() {
    return messageListModel;
  }

  @Override
  public User getCurrentUser() {
    return user;
  }

  @Override
  public void postMessage(Message message) {
    pool.submit(() -> {
      try {
        out.writeObject(Request.post_message.toString());
        out.writeObject(message);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }

  @Override
  public void startFetchingData() {
    start();
  }

  public void connect() {
    try {
      socket = new Socket("localhost", 2048);
      out = new ObjectOutputStream(socket.getOutputStream());
      in = new ObjectInputStream(socket.getInputStream());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
