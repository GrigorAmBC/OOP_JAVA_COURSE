package model.entity;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
  private Date date;
  private String userNickName;
  private String text;

  public Message(String userNickName, Date date, String text) {
    this.date = date;
    this.userNickName = userNickName;
    this.text = text;
  }

  public Date getDate() {
    return date;
  }

  public String getUserNickName() {
    return userNickName;
  }

  public String getText() {
    return text;
  }

}