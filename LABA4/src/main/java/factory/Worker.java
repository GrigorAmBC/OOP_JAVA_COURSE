package factory;

import org.jetbrains.annotations.NotNull;

public class Worker extends Thread {
  private Warehouse<MachineEngine> engineWarehouse;
  private Warehouse<MachineAccessory> accessoryWarehouse;
  private Warehouse<MachineFrame> frameWarehouse;

  public Worker(@NotNull Warehouse<MachineEngine> engineWarehouse, @NotNull Warehouse<MachineAccessory> accessoryWarehouse, @NotNull Warehouse<MachineFrame> frameWarehouse) {
    this.accessoryWarehouse = accessoryWarehouse;
    this.engineWarehouse = engineWarehouse;
    this.frameWarehouse = frameWarehouse;
  }

  @Override
  public void run() {
    MachineAccessory accessory = accessoryWarehouse.removeItem();
    MachineFrame frame = frameWarehouse.removeItem();
    MachineEngine engine = engineWarehouse.removeItem();

    Machine machine = new Machine(frame, accessory, engine);
    //todo: how to return this object?
  }
}
