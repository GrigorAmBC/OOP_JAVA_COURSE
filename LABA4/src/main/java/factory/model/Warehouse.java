package factory.model;

import factory.model.interfaces.WarehouseItemListener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Warehouse<P> {
  private final int capacity;
  private final List<P> items;
  private WarehouseItemListener itemListener = null;

  public Warehouse(int capacity) {
    this.capacity = capacity;
    items = new ArrayList<>();
  }

  public void addItem(@NotNull P item) throws InterruptedException {//todo
    synchronized (items) {
      while (items.size() >= capacity) items.wait();
      items.add(item);
      items.notify();
    }
  }

  public P removeItem() throws InterruptedException {
    synchronized (items) {
      while (items.isEmpty()) items.wait();
      if (itemListener != null)
        itemListener.itemRemoved();
      items.notify();
      return items.remove(0);
    }
  }

  public int getCapacity() {
    return capacity;
  }

  public void setItemListener(WarehouseItemListener itemListener) {
    assert itemListener != null;
    this.itemListener = itemListener;
  }

  public int getCurrentAmountOfItems() {
    return items.size();
  }
}
