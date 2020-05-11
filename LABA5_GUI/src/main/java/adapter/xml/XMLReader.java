package adapter.xml;

import model.entity.User;
import model.port.Repository;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class XMLReader {
  private ObjectInputStream inputStream;
  private Document currentMessage;

  public XMLReader(InputStream is) {
    try {
      inputStream = new ObjectInputStream(is);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  public void readXMLMessage() throws IOException {
    try {
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      String str = (String) inputStream.readObject();

      currentMessage = dBuilder.parse(new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8)));
    } catch (ParserConfigurationException | SAXException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  public String getCommandOrEventName() {
    try {
      return currentMessage.getDocumentElement().getAttribute("name");
    } catch (NullPointerException e) {
      return "";
    }
  }

  public String getMessage() {
    try {
      return currentMessage.getElementsByTagName("message").item(0).getTextContent();
    } catch (NullPointerException e) {
      return "";
    }
  }

  public String getName() {
    try {
      return currentMessage.getElementsByTagName("name").item(0).getTextContent();
    } catch (NullPointerException e) {
      return "";
    }
  }

  public List<User> getUsers() {
    List<User> userList = new ArrayList<>();
    NodeList users = currentMessage.getElementsByTagName("user");
    for (int i = 0; i < users.getLength(); i++) {
      Node user = users.item(i);
      if (user.getNodeType() == Node.ELEMENT_NODE) {
        Element userElement = (Element) user;
        userList.add(new User(userElement
                .getElementsByTagName("name")
                .item(0).getTextContent()));
      }
    }

    return userList;
  }

  public void close() throws IOException {
    inputStream.close();
  }
}
