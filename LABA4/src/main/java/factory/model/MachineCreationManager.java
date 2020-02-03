package factory.model;

import factory.model.machine.Machine;
import factory.model.machine.MachineAccessory;
import factory.model.machine.MachineEngine;
import factory.model.machine.MachineFrame;
import factory.model.machine_factory.MachineFactory;
import factory.model.machine_factory.MachineFactoryBuilder;
import factory.model.util.ConfigurationFileReader;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MachineCreationManager {
  private boolean logSale;
  private Warehouse<MachineFrame> frameWarehouse;
  private Warehouse<MachineAccessory> accessoryWarehouse;
  private Warehouse<MachineEngine> engineWarehouse;
  private Warehouse<Machine> machineWarehouse;

  private MachineFactory factory;

  private List<Supplier<MachineFrame>> frameSuppliers;
  private List<Supplier<MachineAccessory>> accessorySuppliers;
  private List<Supplier<MachineEngine>> engineSuppliers;

  private List<Dealer> dealers;
  private ConfigurationFileReader configReader;
  private MachineWarehouseController controller;

  public MachineCreationManager() {
    configReader = new ConfigurationFileReader();
    Map<String, Integer> settings = configReader.getConfigurationMap();

    // logging
    logSale = 1 == settings.get("LogSale");
    Dealer.setLogSale(logSale);

    // warehouses
    frameWarehouse = new Warehouse<>(settings.get("FrameWarehouseCapacity"));
    accessoryWarehouse = new Warehouse<>(settings.get("AccessoryWarehouseCapacity"));
    engineWarehouse = new Warehouse<>(settings.get("EngineWarehouseCapacity"));
    machineWarehouse = new Warehouse<>(settings.get("MachineWarehouseCapacity"));

    // machine factory
    factory = new MachineFactoryBuilder()
            .setAccessoryWarehouse(accessoryWarehouse)
            .setEngineWarehouse(engineWarehouse)
            .setFrameWarehouse(frameWarehouse)
            .setNumberOfWorkers(settings.get("Workers"))
            .setMachineWarehouse(machineWarehouse)
            .buildMachineFactory();

    // suppliers
    frameSuppliers = new LinkedList<>();
    for (int i = 0, size = settings.get("FrameSuppliers"); i < size; i++) {
      frameSuppliers.add(new Supplier<>(frameWarehouse) {
        @Override
        protected MachineFrame createItem() {
          return new MachineFrame(UUID.randomUUID().toString());
        }
      });
    }

    engineSuppliers = new LinkedList<>();
    for (int i = 0, size = settings.get("EngineSuppliers"); i < size; i++) {
      engineSuppliers.add(new Supplier<>(engineWarehouse) {
        @Override
        protected MachineEngine createItem() {
          return new MachineEngine(UUID.randomUUID().toString());
        }
      });
    }
    accessorySuppliers = new LinkedList<>();
    for (int i = 0, size = settings.get("AccessorySuppliers"); i < size; i++) {
      accessorySuppliers.add(new Supplier<>(accessoryWarehouse) {
        @Override
        protected MachineAccessory createItem() {
          return new MachineAccessory(UUID.randomUUID().toString());
        }
      });
    }

    // dealers
    dealers = new LinkedList<>();
    for (int i = 0, size = settings.get("Dealers"); i < size; i++) {
      dealers.add(new Dealer(machineWarehouse));
    }

    // controller
    controller = new MachineWarehouseController(machineWarehouse, factory);
    machineWarehouse.setItemListener(controller);
  }

  public Warehouse<MachineFrame> getFrameWarehouse() {
    return frameWarehouse;
  }

  public Warehouse<MachineAccessory> getAccessoryWarehouse() {
    return accessoryWarehouse;
  }

  public Warehouse<MachineEngine> getEngineWarehouse() {
    return engineWarehouse;
  }

  public Warehouse<Machine> getMachineWarehouse() {
    return machineWarehouse;
  }

  public List<Supplier<MachineFrame>> getFrameSuppliers() {
    return frameSuppliers;
  }

  public List<Supplier<MachineAccessory>> getAccessorySuppliers() {
    return accessorySuppliers;
  }

  public List<Supplier<MachineEngine>> getEngineSuppliers() {
    return engineSuppliers;
  }

  public List<Dealer> getDealers() {
    return dealers;
  }

  public MachineFactory getFactory() {
    return factory;
  }
}
