package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.entity.Message;
import model.entity.User;
import model.port.Repository;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class NetworkRepository implements Repository {
  private User user;
  private Socket socket;
  private ObjectInputStream in;//BufferedReader
  private ObjectOutputStream out;//PrintWriter
  private Gson gson = new Gson();
  private Model<List<Message>> messageListModel;
  private Model<List<User>> userListModel;
  private ExecutorService pool = Executors.newFixedThreadPool(2);
  private Runnable runnable;

  private enum Request {
    post_message, get_messages, get_users
  }

  public NetworkRepository(User user) {
    this.user = user;

    this.messageListModel = new Model<>(new ArrayList<>());
    this.userListModel = new Model<>(new ArrayList<>());

    this.runnable = () -> {
      try {
        socket = new Socket("localhost", 2048);
        out = new ObjectOutputStream(socket.getOutputStream());//PrintWriter(socket.getOutputStream(), true);
        in = new ObjectInputStream(socket.getInputStream());//new BufferedReader(new InputStreamReader(socket.getInputStream()));

//          out.println(gson.toJson(user));//todo:assign user
        out.writeObject(user);
        String text = "";
        while (true) {
          while ((text = (String) in.readObject()) != null) {
            if (text.equals(Request.get_users.toString())) {
//                Type userListType = new TypeToken<ArrayList<User>>() {
//                }.getType();
              getUserListModel().getProperty().clear();
              List<User> users = (ArrayList) in.readObject();//gson.fromJson(in, userListType);
              if (users != null)
                getUserListModel().getProperty().addAll(users);
              userListModel.setProperty(userListModel.getProperty());//todo: make notifying from other thread;
            } else if (text.equals(Request.get_messages.toString())) {
//                Type messageListType = new TypeToken<ArrayList<Message>>() {
//                }.getType();
              List<Message> messages = (ArrayList) in.readObject();//gson.fromJson(in, messageListType);
              if (messages != null)
                messageListModel.getProperty().addAll(messages);
              messageListModel.setProperty(messageListModel.getProperty());
            }
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    };
  }

  @Override
  public Model<List<User>> getUserListModel() {
    return userListModel;
  }

  @Override
  public Model<List<Message>> getMessageListModel() {
    return messageListModel;
  }


  @Override
  public User getCurrentUser() {
    return user;
  }

  /*@Override
  public List<User> getUsers() {


    List<User> userList = new ArrayList<>();
    var future =
            pool.submit(() -> {
              out.println(Request.get_users.toString());

 *//*uilder = new StringBuilder();
      String text;
      while (true) {
        try {
          if ((text = in.readLine()) == null) break;
        } catch (IOException e) {
          e.printStackTrace();
        }
        builder.append(text);
      }
*//*

              Type foundListType = new TypeToken<ArrayList<User>>() {
              }.getType();
              List<User> messages = gson.fromJson(in, foundListType);
              if (messages != null)
                userList.addAll(messages);
            });

    return userList;
  }*/


  @Override
  public void postMessage(Message message) {
    pool.submit(() -> {
      try {
        out.writeObject(Request.post_message.toString());
        out.writeObject(message);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }

  @Override
  public void startFetchingData() {
    if (runnable != null) {
      pool.execute(runnable);
      runnable = null;
    }
  }
}
