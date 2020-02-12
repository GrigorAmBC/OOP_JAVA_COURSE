package ru.nsu.fit.grigor.bomb_sweeper.model;

public interface IModelSubscriber<P> {
  void modelChanged(Model<P> model);
}