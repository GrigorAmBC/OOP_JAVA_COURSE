package model.port;

public interface ClientCommunication {
  enum Request {
    post_message, get_messages, get_users, login
  }

  void login() throws Exception;
  void updateUserList() throws Exception;
  void updateMessages() throws Exception;
  void readUserRequest() throws Exception;
  void disconnectUser();
}
