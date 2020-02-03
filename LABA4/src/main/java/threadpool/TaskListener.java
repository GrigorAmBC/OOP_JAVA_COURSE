package threadpool;

public interface TaskListener {
  default void taskInterrupted(Task task) {
  }

  void taskFinished(Task task);

  default void taskStarted(Task task) {
  }
}
