import factory.model.MachineCreationManager;
import factory.view.MainView;

public class Main {

  public static void main(String[] args) {
    MainView mainView = new MainView(new MachineCreationManager());
  }

}