package factory.model.machine_factory;


import factory.model.Warehouse;
import factory.model.machine.Machine;
import factory.model.machine.MachineAccessory;
import factory.model.machine.MachineEngine;
import factory.model.machine.MachineFrame;
import org.jetbrains.annotations.NotNull;
import threadpool.ThreadPool;

public class MachineFactory {
  private Warehouse<MachineEngine> engineWarehouse;
  private Warehouse<MachineAccessory> accessoryWarehouse;
  private Warehouse<MachineFrame> frameWarehouse;
  private Warehouse<Machine> machineWarehouse;
  private ThreadPool threadPool;
  private int machinesBuilt = 0;
  private int numberOfWorkers;

  public synchronized void createMachine() {
    Worker worker = new Worker(this);
    threadPool.addTask(worker, worker);
  }

  public synchronized void appendMachinesBuilt() {
    machinesBuilt++;
  }

  public int getNumberOfMachinesBuilt() {
    return machinesBuilt;
  }

  public int getNumberOfWorkers() {
    return numberOfWorkers;
  }

  public int getNumberOfBuildingMachines() {
    return threadPool.getNumberOfTasksLeft();
  }

  public void setNumberOfWorkers(int numberOfWorkers) {
    assert threadPool == null;
    this.numberOfWorkers = numberOfWorkers;
    threadPool = new ThreadPool(numberOfWorkers);
  }

  public Warehouse<MachineEngine> getEngineWarehouse() {
    return engineWarehouse;
  }

  public void setEngineWarehouse(@NotNull Warehouse<MachineEngine> engineWarehouse) {
    this.engineWarehouse = engineWarehouse;
  }

  public Warehouse<MachineAccessory> getAccessoryWarehouse() {
    return accessoryWarehouse;
  }

  public void setAccessoryWarehouse(@NotNull Warehouse<MachineAccessory> accessoryWarehouse) {
    this.accessoryWarehouse = accessoryWarehouse;
  }

  public Warehouse<MachineFrame> getFrameWarehouse() {
    return frameWarehouse;
  }

  public void setFrameWarehouse(@NotNull Warehouse<MachineFrame> frameWarehouse) {
    this.frameWarehouse = frameWarehouse;
  }

  public Warehouse<Machine> getMachineWarehouse() {
    return machineWarehouse;
  }

  public void setMachineWarehouse(@NotNull Warehouse<Machine> machineWarehouse) {
    this.machineWarehouse = machineWarehouse;
  }
}









/*

public class MachineFactory {
  private Warehouse<MachineEngine> engineWarehouse;
  private Warehouse<MachineAccessory> accessoryWarehouse;
  private Warehouse<MachineFrame> frameWarehouse;
  private ThreadPool threadPool;


  public MachineFactory(int numberOfWorkers, List<Warehouse<MachinePart>> warehouses) {
    threadPool = new ThreadPool(numberOfWorkers);
    engineWarehouse =

  }

  public void createMachine() {
    threadPool.addTask(task, );
  }

}
*/
