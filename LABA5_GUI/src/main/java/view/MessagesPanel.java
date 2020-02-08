package view;

import model.IModelSubscriber;
import model.Model;
import model.entity.Message;
import model.port.Repository;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class MessagesPanel extends JTextPane implements IModelSubscriber<List<Message>> {
  private List<Message> messageList = new ArrayList<>();
  private String userName;
  private Repository repository;

  @Override
  public void modelChanged(Model<List<Message>> model) {
    int size = messageList.size();
    int newSize = model.getProperty().size();

//    SimpleDateFormat dt = new SimpleDateFormat("dd hh:mm:ss");

    for (int i = size; i < newSize; i++) {
      messageList.add(model.getProperty().get(i));
      try {
        Message message = messageList.get(i);
        String text = message.getUserNickName().equals(userName) ? "You" : message.getUserNickName();
        text = text + ": " + message.getText()
                + "         ("+ getTime(message.getDate()) + ").\n";
        appendText(text);
      } catch (BadLocationException e) {
        e.printStackTrace();
      }
    }
  }
  private String getTime(Date date) {
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    return sdf.format(date);
  }

  private void appendText(String text) throws BadLocationException {
    Document doc = getDocument();
    doc.insertString(doc.getLength(), text, null);
    setCaretPosition(doc.getLength());
  }

  public MessagesPanel(Repository repository) {
    setBounds(25, 25, 490, 320);
    setFont(new Font("Arial, sans-serif", Font.PLAIN, 15));
    setMargin(new Insets(6, 6, 6, 6));
    setEditable(false);
    setContentType("text/html");
    putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true);

    repository.getMessageListModel().subscribe(this);
    this.repository = repository;
    this.userName = repository.getCurrentUser().getUserNickName();
  }


 /* private static class MessageView extends JPanel {
    private JTextArea messageTextArea = new JTextArea();
    private JTextArea nameTextArea = new JTextArea();
    private JTextArea dateTextArea = new JTextArea();

    public MessageView(Message message) {
      this.setLayout(null);
      messageTextArea.setText(message.getText());
      nameTextArea.setText(message.getUserNickName());
      dateTextArea.setText(message.getDate().toString());
      this.setBackground(Color.WHITE);

      nameTextArea.setEditable(false);
      messageTextArea.setEditable(false);
      dateTextArea.setEditable(false);

      nameTextArea.setFont(new Font("Tahoma", Font.PLAIN, 20));
      messageTextArea.setFont(new Font("Tahoma", Font.BOLD, 16));
      dateTextArea.setFont(new Font("Tahoma", Font.PLAIN, 12));

      add(nameTextArea);
      add(messageTextArea);
      add(dateTextArea);
    }
  }*/
}


