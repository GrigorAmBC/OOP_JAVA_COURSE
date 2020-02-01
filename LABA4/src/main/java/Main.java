import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

class Processor implements Runnable {
  private int id;

  public Processor(int id) {
    this.id = id;
  }

  public void run() {
    System.out.println("Started." + id);


    System.out.println("Ended." + id);
  }
}

class A {
  private int i = 0;

  public synchronized void add() {
    System.out.println(Thread.currentThread().getId() + " in add");
    i++;
    notify();
  }

  public synchronized void get() {
    long id = Thread.currentThread().getId();
    System.out.println(id + " in get");
    if (i < 1) {
      try {
        System.out.println(id + " wait in");
        wait();
        System.out.println(id + " wait out");
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    } else {
      i--;
      System.out.println(id + " got");
    }

  }
}

public class Main {
  static final A object = new A();

  public static void main(String[] args) {

    Queue<Runnable> r;
    r.offer();

    ThreadPoolExecutor
    A a = new A();
    Thread p1 = new Thread(() -> a.get());
    Thread p2 = new Thread(() -> a.add());
    for (int i = 0; i < 4; i++) {

      p1.start();
      p2.start();

      try {
        Thread.sleep(10000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
   /*

    ExecutorService executor = Executors.newFixedThreadPool(2);

    for (int i = 0;i < 5; i++) {
      executor.submit(new Processor(i));
    }

    executor.shutdown();

    System.out.println("All tasks submitted.");

    try {
      executor.awaitTermination(1, TimeUnit.DAYS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.println("All tasks completed.");

  }
}
*/