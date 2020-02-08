package view;

import model.IModelSubscriber;
import model.Model;
import model.entity.Message;
import model.entity.User;
import model.port.Repository;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UserListPanel extends JTextPane implements IModelSubscriber<List<User>> {

  public UserListPanel(Repository repository) {
    repository.getUserListModel().subscribe(this);
    setBounds(520, 25, 156, 320);
    setEditable(true);
    setFont(new Font("Arial, sans-serif", Font.PLAIN, 15));
    setMargin(new Insets(6, 6, 6, 6));
    setEditable(false);

//    setContentType("text/html");
//    putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true);
  }

  @Override
  public void modelChanged(Model<List<User>> model) {
    this.setText("");
    for (User user : model.getProperty()) {
      try {
        Document doc = getDocument();
        String text = user.getUserNickName() + "\n";
        doc.insertString(doc.getLength(), text, null);
      } catch (BadLocationException exc) {
        exc.printStackTrace();
      }
    }
  }
}
