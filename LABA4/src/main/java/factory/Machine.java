package factory;

import org.jetbrains.annotations.NotNull;

public class Machine {
  private MachineEngine engine;
  private MachineAccessory accessory;
  private MachineFrame frame;

  public Machine(@NotNull MachineFrame frame, @NotNull MachineAccessory accessory, @NotNull MachineEngine engine) {
    this.engine = engine;
    this.frame = frame;
    this.accessory = accessory;
  }

  public int getEngineId() {
    return engine.getId();
  }

  public int getAccessoryId() {
    return accessory.getId();
  }

  public int getFrameId() {
    return frame.getId();
  }
}
