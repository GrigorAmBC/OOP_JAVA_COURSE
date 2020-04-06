package threadpool;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ThreadPool  {
  private final List<ThreadPoolTask> taskQueue;//todo:replace with List and  synchronize
  private List<WorkerThread> availableThreads;

 /* @Override
  public void closeThread() {
    for (WorkerThread thread : availableThreads) {
      thread.closeThread();
    }
  }*/

  public ThreadPool(int numberOfThreads) {// todo: maybe choose amount of threads??
    taskQueue = new ArrayList<>();
    availableThreads = new ArrayList<>();

    for (int i = 0; i < numberOfThreads; i++) {
      availableThreads.add(new WorkerThread());
    }

    for (WorkerThread thread : availableThreads) {
      thread.start();
    }
  }

  public int getNumberOfTasksLeft() {
    synchronized (taskQueue) {
      return taskQueue.size();
    }
  }

  public void addTask(@NotNull Task task, TaskListener listener) {
    synchronized (taskQueue) {
      taskQueue.add(new ThreadPoolTask(task, listener));
      taskQueue.notify();
    }
  }

  private class WorkerThread extends Thread {
    private volatile boolean threadActive = true;

    @Override
    public void run() {
      ThreadPoolTask task;
      while (threadActive) {
        synchronized (taskQueue) {
          while (taskQueue.isEmpty()) {
            try {
              taskQueue.wait();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }

          task = taskQueue.remove(taskQueue.size() - 1);
        }
        task.prepare();
        task.go();
        task.finish();
      }
    }

    /*@Override
    public void closeThread() {
      threadActive = false;
    }*/
  }
}
