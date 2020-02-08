package model;

public interface IModelSubscriber<P> {
  void modelChanged(Model<P> model);
}
