package factory.model;

import factory.model.interfaces.PeriodSetter;
import factory.model.machine.MachinePart;
import org.jetbrains.annotations.NotNull;

public abstract class Supplier<P extends MachinePart> extends Thread
        implements PeriodSetter {
  private Warehouse<P> warehouse;
  private long creationPeriod = 1000;
  private int numberOfItemsProduced = 0;
  private boolean threadActive = true;

  public Supplier(@NotNull Warehouse<P> warehouse) {
    this.warehouse = warehouse;
    start();
  }

  @Override
  public void run() {
    while (threadActive) {
      try {
        Thread.sleep(creationPeriod);
        numberOfItemsProduced++;
        warehouse.addItem(createItem());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void setPeriod(long millis) {
    this.creationPeriod = millis;
  }

  protected abstract P createItem();

  public int getNumberOfItemsProduced() {
    return numberOfItemsProduced;
  }

  /*@Override
  public void closeThread() {
    threadActive = false;
  }*/
}
