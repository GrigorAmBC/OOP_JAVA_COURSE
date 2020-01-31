package com.BombSweeper.view;

import com.BombSweeper.model.*;
import com.BombSweeper.model.GameSquare.SquareState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class GameGridView extends JPanel implements IModelSubscriber<GameGrid> {
  private int rows;
  private int cols;
  private List<GameSquareView> squareViews;
  private GameModel model = null;

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
      squareView.setPosition(i);
      squareView.addMouseListener(new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
          super.mousePressed(e);
          SquareState state = squareView.getState();
          if (SwingUtilities.isRightMouseButton(e)) {
            if (state == SquareState.Flag) {
              model.editGameSquare(squareView.getPosition(), SquareState.Untouched);
            } else if (state == SquareState.Untouched) {
              model.editGameSquare(squareView.getPosition(), SquareState.Flag);
            }
          } else if (SwingUtilities.isLeftMouseButton(e)) {
            if (state == SquareState.Untouched) {
              model.editGameSquare(squareView.getPosition(), SquareState.TouchedEmpty);
            }
          }
        }
      });

      squareViews.add(squareView);
      add(squareView);
    }
  }

  @Override
  public void modelChanged(Model<GameGrid> model) {
    GameGrid gridData = model.getProperty();
    if (rows != gridData.getRowCount() || cols != gridData.getColumnCount()) {
      setupGrid(gridData.getRowCount(), gridData.getColumnCount());
    }

    ListIterator<GameSquare> iterator = gridData.getIterator();
    for (int i = 0; iterator.hasNext(); i++) {
      GameSquare square = iterator.next();
      GameSquareView squareView = squareViews.get(i);
      squareView.setState(square.getState(), square.getNumberOfMinesAround());
    }

    if (gridData.getResult() == GameGrid.Result.Lose) {
      showMines(gridData);
    }
  }

  private void showMines(GameGrid gameGrid) {
    ListIterator<GameSquare> iterator = gameGrid.getIterator();

    for (int i = 0; iterator.hasNext(); i++) {
      GameSquare square = iterator.next();
      GameSquareView squareView = squareViews.get(i);

      if (square.hasMine()) {
        squareView.setState(SquareState.Exploded, 0);
      }

    }
  }

  public void setControllerModel(GameModel model) {
    this.model = model;
  }
}
