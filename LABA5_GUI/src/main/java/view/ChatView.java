package view;

import model.entity.Message;
import model.port.Repository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;


public class ChatView extends JFrame {
  private Repository repository;

  public ChatView(Repository repository, String name) {
    super(name);
    this.repository = repository;
    Font font = new Font("Arial, sans-serif", Font.PLAIN, 15);
    getContentPane().setLayout(null);
    setSize(700, 500);
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    // messages panel
    MessagesPanel messagesPanel = new MessagesPanel(repository);
    JScrollPane scrollPane = new JScrollPane(messagesPanel);
    scrollPane.setBounds(25, 25, 490, 320);
    add(scrollPane);

    // users
    UserListPanel userListPanel = new UserListPanel(repository);
    JScrollPane userListScrollPane = new JScrollPane(userListPanel);
    userListScrollPane.setBounds(520, 25, 156, 320);
    add(userListScrollPane);

    // send message panel
    JTextField inputTextField = new JTextField();
    inputTextField.setBounds(0, 350, 400, 50);
    inputTextField.setFont(font);
    inputTextField.setMargin(new Insets(6, 6, 6, 6));
    final JScrollPane textInputScrollPane = new JScrollPane(inputTextField);
    textInputScrollPane.setBounds(25, 350, 650, 50);
    add(textInputScrollPane);

    // send message button
    final JButton sendButton = new JButton("Send");
    sendButton.setFont(font);
    sendButton.setBounds(575, 410, 100, 35);
    add(sendButton);
    sendButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        sendMessage(inputTextField.getText());
        inputTextField.setText("");
      }
    });
    inputTextField.addKeyListener(new KeyAdapter() {
      // send message on Enter
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          sendMessage(inputTextField.getText());
          inputTextField.setText("");
        }
      }
    });

    setLocationRelativeTo(null);
    setVisible(true);
    repository.startFetchingData();
  }

  private void sendMessage(String text) {
    if (!text.isEmpty()) {
      Message message = new Message(repository.getCurrentUser().getUserNickName(),
              new Date(), text);
      repository.postMessage(message);
    }
  }
}
