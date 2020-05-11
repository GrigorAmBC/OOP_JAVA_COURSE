package threadpool;

public interface TaskListener {
  default void taskInterrupted(Task task) {
  }

  void taskFinished(Task task) throws InterruptedException;

  default void taskStarted(Task task) {
  }
}
