import adapter.SerializedNetworkRepository;
import adapter.xml.XMLNetworkRepository;
import model.entity.User;
import view.ChatView;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

  String ip = "127.0.0.1";

  public static void main(String[] args) throws IOException {



    String nickname;
    while (true) {//request username
      nickname = JOptionPane.showInputDialog(null, "Nickname to login: ", "Nickname", JOptionPane.INFORMATION_MESSAGE);
      if (nickname == null)
        return;
      if (!nickname.isEmpty()) {
        break;
      }
      JOptionPane.showInternalMessageDialog(null, "Too short nickname. And it should start with upper case");
    }

    ChatView view = new ChatView(new SerializedNetworkRepository(new User(nickname)), "Chat");
//    ChatView view = new ChatView(new XMLNetworkRepository(new User(nickname)), nickname);


  }
}
