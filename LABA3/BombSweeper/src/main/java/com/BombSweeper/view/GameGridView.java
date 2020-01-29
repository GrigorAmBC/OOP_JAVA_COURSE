package com.BombSweeper.view;

import com.BombSweeper.model.GameGrid;
import com.BombSweeper.model.GameSquare;
import com.BombSweeper.model.GameSquare.SquareState;
import com.BombSweeper.model.IModelSubscriber;
import com.BombSweeper.model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class GameGridView extends JPanel implements IModelSubscriber<GameGrid>, ActionListener {
  private int rows;
  private int cols;
  private List<GameSquareView> squareViews;

  public GameGridView() {
    squareViews = new ArrayList<>();
  }

  private void setupGrid(int rows, int cols) {
    this.rows = rows;
    this.cols = cols;

    this.removeAll();
    squareViews.clear();
    setLayout(new GridLayout(rows, cols));

    for (int i = 0; i < rows * cols; i++) {
      GameSquareView squareView = new GameSquareView();
      squareView.addActionListener(this);//TODO: do we need to remove listener before it?
      squareViews.add(squareView);
      add(squareView);
    }
  }

  @Override
  public void modelChanged(Model<GameGrid> model) {
    GameGrid gridData = model.getProperty();
    if (rows != gridData.getRowNumber() || cols != gridData.getColumnNumber()) {
      setupGrid(gridData.getRowNumber(), gridData.getColumnNumber());
    }
    //TODO: subscribe on result Model<Result>, and show bombs when it's the end
    ListIterator<GameSquare> iterator = gridData.getIterator();

    for (int i = 0; iterator.hasNext(); i++) {
      GameSquare square = iterator.next();
      GameSquareView squareView = squareViews.get(i);

      if (square.getState() == squareView.getState())
        continue;

      if (square.getState() == SquareState.TouchedEmpty) {
        squareView.setState(square.getState(), square.getNumberOfMinsAround());
      } else
        squareView.setState(square.getState());
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {

  }
}
