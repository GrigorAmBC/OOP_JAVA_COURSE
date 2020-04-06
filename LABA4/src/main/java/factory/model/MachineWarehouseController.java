package factory.model;

import factory.model.interfaces.WarehouseItemListener;
import factory.model.machine.Machine;
import factory.model.machine_factory.MachineFactory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MachineWarehouseController extends Thread implements WarehouseItemListener {
  private final MachineFactory factory;
  private boolean threadActive = false;
  private volatile boolean makeOrder = false;
  private List<Boolean> orders = new ArrayList<>();

  public MachineWarehouseController(@NotNull Warehouse<Machine> machineWarehouse, @NotNull MachineFactory factory) {
    this.factory = factory;
    for (int i = 0, size = machineWarehouse.getCapacity(); i < size; i++) {
      factory.createMachine();
    }
  }

  @Override
  public void run() {
    synchronized (orders) {
      while (threadActive) {//todo
        while (orders.isEmpty()) {
          try {
            orders.wait();
          } catch (InterruptedException ignored) {

          }
        }

        orders.remove(0);
        factory.createMachine();
      }
    }

    /*
    while (threadActive) {//todo
      if (makeOrder) {
          makeOrder = false;
          factory.createMachine();
      }
    }*/
  }

  @Override
  public synchronized void itemRemoved() {
    synchronized (orders) {
      orders.add(true);
      orders.notify();
    }
    makeOrder = true;
    if (threadActive)
      return;

    threadActive = true;
    start();
  }





  /*@Override
  public void closeThread() {
    threadActive = false;
  }*/
}
