import model.NetworkRepository;
import model.entity.User;
import model.port.Repository;
import view.ChatView;

import javax.swing.*;

public class ChatApp {
  private Repository repository;
  private ChatView chatView;

  public ChatApp() {
    String userName = requestUserName();

    repository = new NetworkRepository(new User(userName));
    chatView = new ChatView(repository, "Chat");

  }

  private String requestUserName() {
    String userName;
    while (true) {
      userName = JOptionPane.showInputDialog("Nickname to login");
      if (validateUserName(userName)) {
        JOptionPane.showMessageDialog(null, "Wrong name. Please, try again.");
      } else
        break;
    }
    return userName;
  }

  private boolean validateUserName(String userName) {
    return userName.length() > 15;
  }


}
