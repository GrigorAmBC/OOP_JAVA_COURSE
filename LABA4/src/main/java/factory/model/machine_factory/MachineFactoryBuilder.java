package factory.model.machine_factory;

import factory.model.Warehouse;
import factory.model.machine.Machine;
import factory.model.machine.MachineAccessory;
import factory.model.machine.MachineEngine;
import factory.model.machine.MachineFrame;
import org.jetbrains.annotations.NotNull;

public class MachineFactoryBuilder {
  private Warehouse<MachineEngine> engineWarehouse;
  private Warehouse<MachineAccessory> accessoryWarehouse;
  private Warehouse<MachineFrame> frameWarehouse;
  private Warehouse<Machine> machineWarehouse;
  private int numberOfWorkers;

  public MachineFactoryBuilder setFrameWarehouse(@NotNull Warehouse<MachineFrame> warehouse) {
    frameWarehouse = warehouse;
    return this;
  }

  public MachineFactoryBuilder setAccessoryWarehouse(@NotNull Warehouse<MachineAccessory> warehouse) {
    accessoryWarehouse = warehouse;
    return this;
  }

  public MachineFactoryBuilder setEngineWarehouse(@NotNull Warehouse<MachineEngine> warehouse) {
    engineWarehouse = warehouse;
    return this;
  }

  public MachineFactoryBuilder setNumberOfWorkers(int numberOfWorkers) {
    this.numberOfWorkers = numberOfWorkers;
    return this;
  }

  public MachineFactoryBuilder setMachineWarehouse(@NotNull Warehouse<Machine> warehouse) {
    machineWarehouse = warehouse;
    return this;
  }

  public MachineFactory buildMachineFactory() {
    MachineFactory factory = new MachineFactory(machineWarehouse, frameWarehouse,
            accessoryWarehouse, engineWarehouse,
            numberOfWorkers);
    return factory;
  }
}
