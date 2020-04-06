import factory.model.MachineCreationManager;
import factory.view.MainView;

public class Main {

  public static void main(String[] args) {
//    while(1>0) {}
    MainView mainView = new MainView(new MachineCreationManager());
  }

}