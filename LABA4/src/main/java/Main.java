import factory.model.MachineCreationManager;
import factory.view.MainView;

public class Main {

  public static void main(String[] args) {
    MainView mainView = new MainView(new MachineCreationManager());

    /*Thread th1 = new Thread(new Runnable() {
      private final Object object = new Object();

      @Override
      public void run() {
        while (true) {
          synchronized (object) {
            try {
              object.wait();
            } catch (InterruptedException e) {
              System.out.println("In interruptedException!");
              return;
            }
          }

        }
      }
    });

    th1.start();
    try {
      Thread.sleep(3000);
      th1.interrupt();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }*/
  }
}