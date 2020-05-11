package model;

public interface ModelSubscriber<P> {
  void modelChanged(Model<P> model);
}
