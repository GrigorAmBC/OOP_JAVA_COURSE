package threadpool;

import factory.model.interfaces.CloseableThread;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool implements CloseableThread {
  private BlockingQueue<ThreadPoolTask> taskQueue;
  private List<WorkerThread> availableThreads;

  private class WorkerThread extends Thread implements CloseableThread{
    private boolean threadActive = true;

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
          task = taskQueue.remove();
        }
        task.prepare();
        task.go();
        task.finish();
      }
    }

    @Override
    public void closeThread() {
      threadActive = false;
    }
  }

  @Override
  public void closeThread() {
    for (WorkerThread thread : availableThreads) {
      thread.closeThread();
    }
  }

  public ThreadPool(int numberOfThreads) {// todo: maybe choose amount of threads??
    taskQueue = new LinkedBlockingQueue<>();
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
}
