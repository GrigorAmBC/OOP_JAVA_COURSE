package com.BombSweeper.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.CopyOnWriteArrayList;

public class Model<P> {
  private P property;
  private Collection<IModelSubscriber<P>> subscribers = new CopyOnWriteArrayList<>();

  public Model(P property) {
    if (property == null)
      throw new NullPointerException("Пустой параметр");
    this.property = property;
  }

  public P getProperty() {
    return property;
  }

  public void setProperty(P property) {
    if (property == null)
      throw new NullPointerException("Пустой параметр");
    this.property = property;
  }

  protected void notifySubscribers() {
    for (final IModelSubscriber<P> subscriber : subscribers) {
      subscriber.modelChanged(this);
    }
  }

  private void notifySubscriber(IModelSubscriber<P> subscriber) {
    assert subscriber != null;
    subscriber.modelChanged(this);
  }

  public void subscribe(IModelSubscriber<P> subscriber) {
    if (subscriber == null)
      throw new NullPointerException("Пустой параметр");
    if (subscribers.contains(subscriber)) {
      throw new IllegalArgumentException("Повторная подписка: " + subscriber);
    }

    subscribers.add(subscriber);
    notifySubscriber(subscriber);
  }

  public void unsubscribe(IModelSubscriber<P> subscriber) {
    if (subscriber == null)
      throw new NullPointerException("Пустой параметр");
    if (!subscribers.contains(subscriber))
      throw new IllegalArgumentException("Неизвестный подписчик: " +
              subscriber);
    subscribers.remove(subscriber);
  }

  @Override
  public String toString() {
    return property.toString();
  }
}
