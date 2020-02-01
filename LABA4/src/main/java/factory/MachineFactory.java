package factory;

import threadpool.Task;
import threadpool.ThreadPool;

public class MachineFactory  extends ThreadPool {
  private Warehouse<MachineEngine> engineWarehouse;
  private Warehouse<MachineAccessory> accessoryWarehouse;
  private Warehouse<MachineFrame> frameWarehouse;

  Task task = new Task() {
    @Override
    public void performWork() {
      MachineAccessory accessory = accessoryWarehouse.removeItem();
      MachineFrame frame = frameWarehouse.removeItem();
      MachineEngine engine = engineWarehouse.removeItem();

      Machine machine = new Machine(frame, accessory, engine);
    }
  };


  public MachineFactory(int numberOfWorkers) {
    super(numberOfWorkers);
  }

}
