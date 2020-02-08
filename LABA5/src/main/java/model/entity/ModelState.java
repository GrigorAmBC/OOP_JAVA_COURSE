package model.entity;

public class ModelState {
  private boolean userStateChanged = false;
  private boolean messageStateChanged = false;

  public void setUserStateChanged(boolean userStateChanged) {
    this.userStateChanged = userStateChanged;
  }

  public boolean isUserStateChanged() {
    return userStateChanged;
  }

  public boolean isMessageStateChanged() {
    return messageStateChanged;
  }

  public void setMessageStateChanged(boolean messageStateChanged) {
    this.messageStateChanged = messageStateChanged;
  }
}
