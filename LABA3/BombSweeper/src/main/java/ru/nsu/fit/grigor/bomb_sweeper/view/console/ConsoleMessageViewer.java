package ru.nsu.fit.grigor.bomb_sweeper.view.console;

import ru.nsu.fit.grigor.bomb_sweeper.view.MessageViewer;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsoleMessageViewer implements MessageViewer {

  @Override
  public void showMessage(String message) {
    Logger.getGlobal().log(Level.INFO, message);
  }
}
