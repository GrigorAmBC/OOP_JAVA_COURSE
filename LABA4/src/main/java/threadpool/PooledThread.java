package threadpool;

import java.util.concurrent.BlockingQueue;

public class PooledThread extends Thread {

  // todo: thread has to remove a task whenever it comes and the thread is not busy
  // todo: so maybe some listener??

  private BlockingQueue<ThreadPoolTask> taskQueue;

  public PooledThread(BlockingQueue<ThreadPoolTask> tasks) {
    taskQueue = tasks;
  }

  @Override
  public void run() {
    performTask();
  }

  private void performTask() {
    ThreadPoolTask task;
    if (!taskQueue.isEmpty())
      task = taskQueue.remove();
    else
      throw new NullPointerException("No tasks to run.");

    task.prepare();
    task.go();
    task.finish();
  }

}
