package model.entity;

public class ModelState {
  private boolean isUserStateChanged = false;
  private boolean isMessageStateChanged = false;

  public void setUserStateChanged(boolean userStateChanged) {
    this.isUserStateChanged = userStateChanged;
  }

  public boolean userStateChanged() {
    return isUserStateChanged;
  }

  public boolean messageStateChanged() {
    return isMessageStateChanged;
  }

  public void setMessageStateChanged(boolean messageStateChanged) {
    this.isMessageStateChanged = messageStateChanged;
  }
}
