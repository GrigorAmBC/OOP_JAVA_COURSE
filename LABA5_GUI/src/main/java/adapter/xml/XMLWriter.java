package adapter.xml;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;

public class XMLWriter {
  private ObjectOutputStream outputStream;

  public XMLWriter(OutputStream os) {
    try {
      outputStream = new ObjectOutputStream(os);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  private void sendMessage(Document outMessage) throws IOException {
    try {
      TransformerFactory tf = TransformerFactory.newInstance();
      Transformer trans = tf.newTransformer();
      StringWriter sw = new StringWriter();

      trans.transform(new DOMSource(outMessage), new StreamResult(sw));
      outputStream.writeObject(sw.toString());
    } catch (TransformerException e) {
      e.printStackTrace();
    }
  }

  public void sendEventMessage(String type, String message, String name) throws IOException {
    try {
      DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
      Document outMessage = documentBuilder.newDocument();
      Element root = outMessage.createElement("event");
      Attr commandName = outMessage.createAttribute("name");
      commandName.setValue(type);
      root.setAttributeNode(commandName);
      outMessage.appendChild(root);

      if (!message.equals("")) {
        Element msg = outMessage.createElement("message");
        msg.appendChild(outMessage.createTextNode(message));
        root.appendChild(msg);
      }

      Element outName = outMessage.createElement("name");
      outName.appendChild(outMessage.createTextNode(name));
      root.appendChild(outName);

      sendMessage(outMessage);
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    }
  }

  public void close() throws IOException{
    outputStream.close();
  }
}
