package factory.model.machine_factory;

import factory.model.Warehouse;
import factory.model.machine.Machine;
import factory.model.machine.MachineAccessory;
import factory.model.machine.MachineEngine;
import factory.model.machine.MachineFrame;
import org.jetbrains.annotations.NotNull;

public class MachineFactoryBuilder {
  private MachineFactory factory = new MachineFactory();

  public MachineFactoryBuilder setFrameWarehouse(@NotNull Warehouse<MachineFrame> warehouse) {
    factory.setFrameWarehouse(warehouse);
    return this;
  }

  public MachineFactoryBuilder setAccessoryWarehouse(@NotNull Warehouse<MachineAccessory> warehouse) {
    factory.setAccessoryWarehouse(warehouse);
    return this;
  }

  public MachineFactoryBuilder setEngineWarehouse(@NotNull Warehouse<MachineEngine> warehouse) {
    factory.setEngineWarehouse(warehouse);
    return this;
  }

  public MachineFactoryBuilder setNumberOfWorkers(int numberOfWorkers) {
    factory.setNumberOfWorkers(numberOfWorkers);
    return this;
  }

  public MachineFactoryBuilder setMachineWarehouse(@NotNull Warehouse<Machine> warehouse) {
    factory.setMachineWarehouse(warehouse);
    return this;
  }

  public MachineFactory buildMachineFactory() {
    return factory;
  }
}
