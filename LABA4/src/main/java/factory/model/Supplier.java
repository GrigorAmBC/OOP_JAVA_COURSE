package factory.model;

import factory.model.interfaces.CloseableThread;
import factory.model.interfaces.PeriodSetter;
import factory.model.machine.MachinePart;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public abstract class Supplier<P extends MachinePart> extends Thread
        implements PeriodSetter, CloseableThread {
  private Warehouse<P> warehouse;
  private long creationPeriod = 100;
  private int numberOfItemsProduced = 0;
  private boolean threadActive = true;
  private final String id = UUID.randomUUID().toString();
  ;

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
        System.out.println("A supplier thread (" + id + ") was interrupted!");
        return;
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

  @Override
  public void closeThread() {
    interrupt();
  }
}
