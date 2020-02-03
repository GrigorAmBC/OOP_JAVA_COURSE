package factory.model;

import factory.model.interfaces.CloseableThread;
import factory.model.interfaces.WarehouseItemListener;
import factory.model.machine.Machine;
import factory.model.machine_factory.MachineFactory;
import org.jetbrains.annotations.NotNull;

public class MachineWarehouseController extends Thread implements WarehouseItemListener, CloseableThread {
  private final MachineFactory factory;
  private boolean threadActive = false;
  private volatile boolean makeOrder = false;

  public MachineWarehouseController(@NotNull Warehouse<Machine> machineWarehouse, @NotNull MachineFactory factory) {
    this.factory = factory;
    for (int i = 0, size = machineWarehouse.getCapacity(); i < size; i++) {
      factory.createMachine();
    }
  }

  @Override
  public void run() {
    while (threadActive) {
      if (makeOrder) {
        factory.createMachine();
        makeOrder = false;
      }
    }
  }

  @Override
  public synchronized void itemRemoved() {
    makeOrder = true;
    if (threadActive)
      return;

    threadActive = true;
    start();

  }

  @Override
  public void closeThread() {
    threadActive = false;
  }
}
