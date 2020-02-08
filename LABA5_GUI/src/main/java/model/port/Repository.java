package model.port;

import model.Model;
import model.entity.Message;
import model.entity.User;

import java.util.List;

public interface Repository {
  Model<List<User>> getUserListModel();
  Model<List<Message>> getMessageListModel();
  User getCurrentUser();
  void postMessage(Message message);
  void startFetchingData();
}
