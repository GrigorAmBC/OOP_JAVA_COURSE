package model.entity;

import java.io.Serializable;

public class User implements Serializable {
  private String userNickName;


  public User(String userNickName) {
    this.userNickName = userNickName;
  }


  public String getUserNickName() {
    return userNickName;
  }
}

