package ru.nsu.fit.grigor.bomb_sweeper.view.swing;

import ru.nsu.fit.grigor.bomb_sweeper.view.MessageViewer;

import javax.swing.*;

public class SwingMessageViewer implements MessageViewer {
  @Override
  public void showMessage(String message) {
    JOptionPane.showMessageDialog(null, message);
  }
}
