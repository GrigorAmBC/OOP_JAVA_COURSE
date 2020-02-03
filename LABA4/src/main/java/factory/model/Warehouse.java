package factory.model;

import factory.model.interfaces.WarehouseItemListener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Warehouse<P> {
  private final int capacity;
  private List<P> items;
  private WarehouseItemListener itemListener = null;

  public Warehouse(int capacity) {
    assert capacity > 0;
    this.capacity = capacity;
    items = new ArrayList<>();
  }

  public synchronized void addItem(@NotNull P item) {
    if (items.size() < capacity) {
      items.add(item);
      notify();
    } else {
      try {
        wait();
        items.add(item);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public synchronized P removeItem() {
    try {
      while (items.isEmpty()) wait();
      if (itemListener != null)
        itemListener.itemRemoved();
      notify();
      return items.remove(0);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return null;
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
