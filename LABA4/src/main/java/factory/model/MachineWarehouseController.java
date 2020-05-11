package factory.model;

import factory.model.interfaces.CloseableThread;
import factory.model.interfaces.WarehouseItemListener;
import factory.model.machine.Machine;
import factory.model.machine_factory.MachineFactory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MachineWarehouseController extends Thread implements WarehouseItemListener, CloseableThread {
  private final MachineFactory factory;
  private boolean threadActive = false;
  private final List<Boolean> orders = new ArrayList<>();

  public MachineWarehouseController(@NotNull Warehouse<Machine> machineWarehouse, @NotNull MachineFactory factory) {
    this.factory = factory;
    for (int i = 0, size = machineWarehouse.getCapacity(); i < size; i++) {
      factory.createMachine();
    }
    threadActive = true;
    start();
  }

  @Override
  public void run() {
    while (threadActive) {//todo
      try {
        synchronized (orders) {
          while (orders.isEmpty()) {
            orders.wait();
          }
          orders.remove(0);
          factory.createMachine();
        }
      } catch (InterruptedException ignored) {
        System.out.println("Controller thread was interrupted!");
        return;
      }
    }
  }


  @Override
  public void itemRemoved() {
    synchronized (orders) {
      orders.add(true);
      orders.notify();
    }
  }

  @Override
  public void closeThread() {
    interrupt();
  }
}
