package factory;

import org.jetbrains.annotations.NotNull;

public abstract class Supplier<P extends MachinePart> extends Thread {
  private Warehouse<P> warehouse;
  private long creationPeriod; // can be changed with UI


  public Supplier(@NotNull Warehouse<P> warehouse, long millis) {
    assert millis > 0;
    this.warehouse = warehouse;
    creationPeriod = millis;


    //todo: do we start at once?
  }

  @Override
  public void run() {//todo: is this correct??
    while (true) {
      try {
        Thread.sleep(creationPeriod);
        warehouse.addItem(createItem());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public void setCreationPeriod(long millis) {
    assert millis > 0;
    this.creationPeriod = millis;
  }

  protected abstract P createItem();
}
