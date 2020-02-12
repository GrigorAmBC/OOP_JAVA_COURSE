package ru.nsu.fit.grigor.bomb_sweeper.view;

import ru.nsu.fit.grigor.bomb_sweeper.model.IModelSubscriber;
import ru.nsu.fit.grigor.bomb_sweeper.model.Model;

import javax.swing.*;
import java.awt.*;

public class TextAreaView extends JTextArea implements IModelSubscriber<Integer> {

  public TextAreaView() {
    setEnabled(false);
    setFont(new Font(null, Font.PLAIN, 22));
    this.setDisabledTextColor(Color.BLACK);
  }

  @Override
  public void modelChanged(Model<Integer> model) {
    setText(model.getProperty().toString());
  }
}
