package ru.nsu.fit.grigor.bomb_sweeper.model;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

public class Model<P> {
  private P property;
  private Collection<ModelSubscriber<P>> subscribers = new CopyOnWriteArrayList<>();

  public Model(@NotNull P property) {
    this.property = property;
  }

  public P getProperty() {
    return property;
  }

  public void setProperty(P property) {
    if (property == null)
      throw new NullPointerException("Empty parameter");
    this.property = property;
    notifySubscribers();
  }

  protected void notifySubscribers() {
    for (final ModelSubscriber<P> subscriber : subscribers) {
      subscriber.modelChanged(this);
    }
  }

  private void notifySubscriber(@NotNull ModelSubscriber<P> subscriber) {
    subscriber.modelChanged(this);
  }

  public void subscribe(@NotNull ModelSubscriber<P> subscriber) {
    if (subscribers.contains(subscriber)) {
      throw new IllegalArgumentException("Repeated subscription: " + subscriber);
    }

    subscribers.add(subscriber);
    notifySubscriber(subscriber);
  }

  public void unsubscribe(@NotNull ModelSubscriber<P> subscriber) {
    if (!subscribers.contains(subscriber))
      throw new IllegalArgumentException("Unknown subscriber: " +
              subscriber);
    subscribers.remove(subscriber);
  }

  @Override
  public String toString() {
    return property.toString();
  }
}
