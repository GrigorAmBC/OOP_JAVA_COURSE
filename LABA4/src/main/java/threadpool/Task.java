package threadpool;

public interface Task {
  void performWork() throws InterruptedException;
}
