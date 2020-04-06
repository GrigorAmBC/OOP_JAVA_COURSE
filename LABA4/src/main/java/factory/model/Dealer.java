package factory.model;

import factory.model.interfaces.PeriodSetter;
import factory.model.machine.Machine;
import org.jetbrains.annotations.NotNull;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Dealer extends Thread implements PeriodSetter {
  private long requestPeriod = 10;
  private Warehouse<Machine> machineWarehouse;
  private final int id;
  private boolean threadActive = true;
  private static boolean logSale = false;
  private static int dealerCount = 0;
  private String saleLogFileName = "sales.log";

  public Dealer(@NotNull Warehouse<Machine> machineWarehouse) {
    this.machineWarehouse = machineWarehouse;
    dealerCount++;
    id = dealerCount;
    start();
  }

  @Override
  public void run() {
    while (threadActive) {
      try {
        Thread.sleep(requestPeriod);
        Machine machine = machineWarehouse.removeItem();
        if (logSale)
          logSale(machine);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public static void setLogSale(boolean logSale) {
    Dealer.logSale = logSale;
  }

  @Override
  public void setPeriod(long millis) {
    this.requestPeriod = millis;
  }

  /*@Override
  public void closeThread() {
    threadActive = false;
  }*/

  private void logSale(@NotNull Machine machine) {
    // todo: move this to another class??
    StringBuilder builder = new StringBuilder();
    builder.append(new Date())
            .append(": Dealer")
            .append(id)
            .append(": Auto: ")
            .append(machine.getId())
            .append("(Frame:")
            .append(machine.getFrameId())
            .append(", Accessory: ")
            .append(machine.getAccessoryId())
            .append(", Engine: ")
            .append(machine.getEngineId())
            .append(").\n");

    try (FileWriter fw = new FileWriter(saleLogFileName, true)) {
      fw.append(builder.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
