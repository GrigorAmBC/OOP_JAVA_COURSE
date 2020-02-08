package view;

import model.entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatGUI extends JFrame {

  private int height = 30;
  private int width = 100;

  public ChatGUI() {
    setSize(900, 500);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLayout(new GridBagLayout());

    GridBagConstraints gc = new GridBagConstraints();
    gc.anchor = GridBagConstraints.LINE_START;

    // chat messages
      gc.gridx = 0;
      gc.gridy = 0;
      MessagesPanel messagesPanel = new MessagesPanel();
      add(messagesPanel, gc);

    // message panel
    gc.gridy = 1;
    JTextArea messageTextArea = new JTextArea();
    messageTextArea.setFont(new Font("Tahome", Font.PLAIN, 22));
   messageTextArea.setPreferredSize(new Dimension(width, height));;
    JPanel textPanel = new JPanel();
    textPanel.add(messageTextArea);
    add(textPanel, gc);

    // send btn
    gc.gridx = 1;
    JButton sendButton = new JButton();
    sendButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // todo
      }
    });

    // user list
    gc.gridy = 0;
    UserListPanel userListPanel = new UserListPanel();

    add(userListPanel, gc);

    setLocationRelativeTo(null);
    setVisible(true);
  }
}
