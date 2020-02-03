package factory.model.machine;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class Machine {
  private MachineEngine engine;
  private MachineAccessory accessory;
  private MachineFrame frame;
  private final String id;

  public Machine(@NotNull MachineFrame frame, @NotNull MachineAccessory accessory, @NotNull MachineEngine engine) {
    this.engine = engine;
    this.frame = frame;
    this.accessory = accessory;
    this.id = UUID.randomUUID().toString();
  }

  public String getEngineId() {
    return engine.getId();
  }

  public String getAccessoryId() {
    return accessory.getId();
  }

  public String getFrameId() {
    return frame.getId();
  }

  public String getId() {
    return id;
  }
}
