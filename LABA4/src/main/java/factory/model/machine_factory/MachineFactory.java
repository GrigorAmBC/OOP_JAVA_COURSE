package factory.model.machine_factory;


import factory.model.Warehouse;
import factory.model.interfaces.CloseableThread;
import factory.model.machine.Machine;
import factory.model.machine.MachineAccessory;
import factory.model.machine.MachineEngine;
import factory.model.machine.MachineFrame;
import org.jetbrains.annotations.NotNull;
import threadpool.ThreadPool;

public class MachineFactory implements CloseableThread {
  private Warehouse<MachineEngine> engineWarehouse;
  private Warehouse<MachineAccessory> accessoryWarehouse;
  private Warehouse<MachineFrame> frameWarehouse;
  private Warehouse<Machine> machineWarehouse;
  private final ThreadPool threadPool;
  private int machinesBuilt;
  private int numberOfWorkers;

  public MachineFactory(@NotNull Warehouse<Machine> machineWarehouse, @NotNull Warehouse<MachineFrame> frameWarehouse, @NotNull Warehouse<MachineAccessory> accessoryWarehouse, @NotNull Warehouse<MachineEngine> engineWarehouse,  int numberOfWorkers) {
    this.numberOfWorkers = numberOfWorkers;
    threadPool = new ThreadPool(numberOfWorkers);

    this.engineWarehouse = engineWarehouse;
    this.accessoryWarehouse = accessoryWarehouse;
    this.frameWarehouse = frameWarehouse;
    this.machineWarehouse = machineWarehouse;
  }


  public synchronized void createMachine() {
    Worker worker = new Worker(this);
    threadPool.addTask(worker, worker);
  }

  public synchronized void appendMachinesBuilt() {
    machinesBuilt++;
  }

  public synchronized int getNumberOfMachinesBuilt() {
    return machinesBuilt;
  }

  public int getNumberOfWorkers() {
    return numberOfWorkers;
  }

  public int getNumberOfBuildingMachines() {
    synchronized (threadPool) {
      return threadPool.getNumberOfTasksLeft();
    }
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

  @Override
  public void closeThread() {
    threadPool.closeThread();
  }
}