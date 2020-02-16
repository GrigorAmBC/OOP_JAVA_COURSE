package ru.nsu.fit.grigor.bomb_sweeper.model;

public interface ModelSubscriber<P> {
  void modelChanged(Model<P> model);
}