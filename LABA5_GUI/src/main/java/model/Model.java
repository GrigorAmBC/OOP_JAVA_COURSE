package model;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

public class Model<P> {
  private P property;
  private Collection<IModelSubscriber<P>> subscribers = new CopyOnWriteArrayList<>();

  public Model(P property) {
    if (property == null)
      throw new NullPointerException("Empty parameter");
    this.property = property;
  }

  public synchronized P getProperty() {
    return property;
  }

  public synchronized void setProperty(P property) {
    if (property == null)
      throw new NullPointerException("Empty parameter");
    this.property = property;
    notifySubscribers();
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
      throw new NullPointerException("Empty parameter");
    if (subscribers.contains(subscriber)) {
      throw new IllegalArgumentException("Repeated subscription: " + subscriber);
    }

    subscribers.add(subscriber);
    notifySubscriber(subscriber);
  }

  public void unsubscribe(IModelSubscriber<P> subscriber) {
    if (subscriber == null)
      throw new NullPointerException("Empty parameter");
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