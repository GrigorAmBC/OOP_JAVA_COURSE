package factory;

import org.jetbrains.annotations.NotNull;

public class Dealer extends Thread {
  private long requestPeriod;// can be changed from ui
  private Warehouse<Machine> machineWarehouse;


  public Dealer(@NotNull Warehouse<Machine> machineWarehouse, long requestPeriod) {
    assert requestPeriod > 0; //todo: may be deleted
    this.requestPeriod = requestPeriod;
    this.machineWarehouse = machineWarehouse;
  }

  public void setRequestPeriod(long requestPeriod) {// todo maybe syncronized??
    this.requestPeriod = requestPeriod;
  }

  @Override
  public void run() {
    while (true) {
      try {
        Thread.sleep(requestPeriod);
        machineWarehouse.removeItem();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
