package factory;

import org.jetbrains.annotations.NotNull;

public class MachineWarehouseController extends Thread {//todo:extends Thread??
  private Warehouse<Machine> machineWarehouse;

  public MachineWarehouseController(@NotNull Warehouse<Machine> machineWarehouse) {
    this.machineWarehouse = machineWarehouse;
  }

  @Override
  public void run() {

  }
}
