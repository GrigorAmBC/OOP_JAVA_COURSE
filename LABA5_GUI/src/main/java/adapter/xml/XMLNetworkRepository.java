package adapter.xml;

import model.Model;
import model.entity.Message;
import model.entity.User;
import model.port.Repository;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class XMLNetworkRepository extends Thread implements Repository {
  private User user;
  private Socket socket;
  private XMLReader reader;
  private XMLWriter writer;
  private Model<List<Message>> messageListModel;
  private Model<List<User>> userListModel;
  private ExecutorService pool = Executors.newFixedThreadPool(1);
  private boolean connected;

  public XMLNetworkRepository(User user) {
    this.user = user;

    this.messageListModel = new Model<>(new ArrayList<>());
    this.userListModel = new Model<>(new ArrayList<>());
    connect();
  }

  @Override
  public void run() {
    try {
      writer.sendEventMessage(Request.login.toString(), "", user.getUserNickName());

      while (connected) {
        try {
          reader.readXMLMessage();
        } catch (IOException e) {
          break;
        }

        if (reader.getCommandOrEventName().equals(Request.get_users.toString())) {
          getUserListModel().getProperty().clear();

          List<User> users = reader.getUsers();

          if (!users.isEmpty()) {
            getUserListModel().getProperty().addAll(users);
          }
          userListModel.notifySubscribers();
        } else if (reader.getCommandOrEventName().equals(Request.get_messages.toString())) {
          Message message = new Message(reader.getName(), new Date(), reader.getMessage());//todo:change date
          messageListModel.getProperty().add(message);
          messageListModel.notifySubscribers();
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
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
        writer.sendEventMessage(Request.post_message.toString(), message.getText(), message.getUserNickName());
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }

  @Override
  public void startFetchingData() {
    if (!connected)
      connect();
    start();
  }

  private void connect() {
    try {
      socket = new Socket("localhost", 2048);
      writer = new XMLWriter(socket.getOutputStream());
      reader = new XMLReader(socket.getInputStream());
      connected = true;
    } catch (Exception e) {
      connected = false;
      e.printStackTrace();
    }
  }
}
