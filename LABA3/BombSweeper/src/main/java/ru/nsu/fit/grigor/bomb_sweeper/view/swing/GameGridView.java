package ru.nsu.fit.grigor.bomb_sweeper.view.swing;

import ru.nsu.fit.grigor.bomb_sweeper.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class GameGridView extends JPanel implements ModelSubscriber<GameGrid> {
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
          GameSquare.SquareState state = squareView.getState();
          if (e.getClickCount() == 2) {
            if (state == GameSquare.SquareState.TouchedEmpty) {
              model.doubleClick(squareView.getPosition());
            }
          }
          if (SwingUtilities.isRightMouseButton(e)) {
            if (state == GameSquare.SquareState.Flag) {
              model.editGameSquare(squareView.getPosition(), GameSquare.SquareState.Untouched);
            } else if (state == GameSquare.SquareState.Untouched) {
              model.editGameSquare(squareView.getPosition(), GameSquare.SquareState.Flag);
            }
          } else if (SwingUtilities.isLeftMouseButton(e)) {
            if (state == GameSquare.SquareState.Untouched) {
              model.editGameSquare(squareView.getPosition(), GameSquare.SquareState.TouchedEmpty);
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

    ListIterator<GameSquare> dataIterator = gridData.getIterator();
    ListIterator<GameSquareView> viewIterator = squareViews.listIterator();
    while (dataIterator.hasNext() && viewIterator.hasNext()) {
      GameSquare square = dataIterator.next();
      GameSquareView squareView = viewIterator.next();
      squareView.setState(square.getState(), square.getNumberOfMinesAround());
    }

 /*   if (dataIterator.hasNext() || viewIterator.hasNext()) {//todo: wtf?
      Logger.getGlobal().info("Mismatch between number of swing squares and data squares.");
    }*/
  }

  public void setControllerModel(GameModel model) {
    this.model = model;
  }
}
