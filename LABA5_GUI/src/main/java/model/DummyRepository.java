package model;

import model.entity.Message;
import model.entity.User;
import model.port.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DummyRepository implements Repository {
  Model<List<User>> userModel = new Model<>(new ArrayList<>());
  Model<List<Message>> messageModel = new Model<>(new ArrayList<>());

  public DummyRepository() {
    List<User> users = userModel.getProperty();
    users.add(new User("George"));
    users.add(new User("Many"));
    users.add(new User("Herculus"));
    users.add(new User("Zeus"));
    userModel.setProperty(users);

    List<Message> messages = new ArrayList<>();
    Date date = new Date();
    messages.add(new Message("George", date, "Hey"));
    messages.add(new Message("Many", date, "Hey1"));
    messages.add(new Message("Herculus", date, "Hey2"));
    messages.add(new Message("Zeus", date, "Hey3"));
    messageModel.setProperty(messages);
  }

  @Override
  public Model<List<User>> getUserListModel() {
    return userModel;
  }

  @Override
  public Model<List<Message>> getMessageListModel() {
    return messageModel;
  }

  @Override
  public User getCurrentUser() {
    return new User("Grigor");
  }

  @Override
  public void postMessage(Message message) {
    messageModel.getProperty().add(message);
    messageModel.setProperty(messageModel.getProperty());
  }

  @Override
  public void startFetchingData() {

  }
}

