package factory;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Warehouse<P> {
  private final int capacity;// todo: to upper?
  private List<P> items;

  public Warehouse(int capacity) {
    assert capacity > 0;
    this.capacity = capacity;
  }

  // todo: check this

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
    if (items.isEmpty()) {
      try {
        wait();
        return items.remove(0);//todo: is this correct? loops here are not permitted?
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    } else {
      notify();
      return items.remove(0);
    }

    //todo:delete
    System.out.println("Error in warehouse! with remove");
    return null;
  }

  public int getCapacity() {
    return capacity;
  }
}
