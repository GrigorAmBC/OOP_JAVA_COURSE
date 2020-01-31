package com.BombSweeper.view;

import com.BombSweeper.model.IModelSubscriber;
import com.BombSweeper.model.Model;

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
