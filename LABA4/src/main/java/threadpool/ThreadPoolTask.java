package threadpool;

import org.jetbrains.annotations.NotNull;

public class ThreadPoolTask {
  private TaskListener listener;
  private Task task;

  public ThreadPoolTask(@NotNull Task task, TaskListener listener) {
    this.listener = listener;
    this.task = task;
  }

  public void prepare() {
    if (listener != null)
      listener.taskStarted(task);
  }

  public void go() {
    task.performWork();
  }

  public void finish() {
    if (listener != null)
      listener.taskFinished(task);
  }

}
