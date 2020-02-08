import model.DummyRepository;
import model.Model;
import model.NetworkRepository;
import model.entity.Message;
import model.entity.User;
import model.port.Repository;
import view.ChatView;
import view.DummyView;
import view.MessagesPanel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {

  String ip = "127.0.0.1";

  public static void main(String[] args) {
    String nickname;
    while (true) {
      nickname = JOptionPane.showInputDialog(null, "Nickname to login: ", "Nickname", JOptionPane.INFORMATION_MESSAGE);
      if (nickname == null)
        return;
      if (nickname.length() != 0 && Character.isUpperCase(nickname.codePointAt(0)))
        break;
      JOptionPane.showInternalMessageDialog(null, "Too short nickname. And it should start with upper case");
    }
    ChatView view = new ChatView(new NetworkRepository(new User(nickname)), "Chat");
  }


}
