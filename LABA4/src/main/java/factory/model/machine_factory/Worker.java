package factory.model.machine_factory;

import factory.model.Warehouse;
import factory.model.machine.Machine;
import factory.model.machine.MachineAccessory;
import factory.model.machine.MachineEngine;
import factory.model.machine.MachineFrame;
import org.jetbrains.annotations.NotNull;
import threadpool.Task;
import threadpool.TaskListener;

public class Worker implements Task, TaskListener {
  private Warehouse<MachineEngine> engineWarehouse;
  private Warehouse<MachineAccessory> accessoryWarehouse;
  private Warehouse<MachineFrame> frameWarehouse;
  private Warehouse<Machine> machineWarehouse;
  private Machine createdMachine;
  private MachineFactory factory;

  public Worker(@NotNull MachineFactory factory) {
    this.factory = factory;
    this.accessoryWarehouse = factory.getAccessoryWarehouse();
    this.engineWarehouse = factory.getEngineWarehouse();
    this.frameWarehouse = factory.getFrameWarehouse();
    this.machineWarehouse = factory.getMachineWarehouse();
  }

  @Override
  public void performWork() throws InterruptedException {
    MachineAccessory accessory = accessoryWarehouse.removeItem();

    MachineFrame frame = frameWarehouse.removeItem();

    MachineEngine engine = engineWarehouse.removeItem();

    createdMachine = new Machine(frame, accessory, engine);
  }

  @Override
  public void taskFinished(Task task) throws InterruptedException {
    factory.appendMachinesBuilt();
    machineWarehouse.addItem(createdMachine);
  }

  private void getMachinePartFromWarehouse() {
    /*try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }*/
  }

  private void constructMachine() {
    /*try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }*/
  }
}
