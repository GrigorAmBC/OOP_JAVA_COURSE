package threadpool;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ThreadPool {
  private BlockingQueue<ThreadPoolTask> taskQueue;
  private List<PooledThread> availableThreads;


  private class WorkerThread extends Thread {
    @Override
    public void run() {
      ThreadPoolTask task;
      while (true) {
        synchronized (taskQueue) {
          while (taskQueue.isEmpty()) {
            try {
              wait();
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
  }

  public ThreadPool(int numberOfThreads) {// todo: maybe choose amount of threads??
    taskQueue = new LinkedBlockingQueue<>();
    availableThreads = new ArrayList<>();

    for (int i = 0; i < numberOfThreads; i++) {
      availableThreads.add(new PooledThread(taskList));
    }
  }

  public void addTask(@NotNull Task task) {
    addTask(task, null);
  }

  public void addTask(@NotNull Task task, TaskListener listener) {

    try {
      taskQueue.put(new ThreadPoolTask(task, listener));
    } catch (InterruptedException ignored) {
    }
    synchronized (taskQueue) {
      taskQueue.add(new ThreadPoolTask(task, listener));
      taskQueue.notify();
    }
  }

  void taskInterrupted(Task task) {

  }

  void taskFinished(Task task) {

  }

  void taskStarted(Task task) {

  }


}
