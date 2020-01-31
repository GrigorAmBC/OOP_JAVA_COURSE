package com.BombSweeper.model;

import java.util.*;

public class GameGrid {
  private List<GameSquare> gridList = new ArrayList<>();
  private int rows;
  private int cols;
  private int numberOfMines;
  private Result result = Result.NotDefined;

  public enum Result {
    NotDefined, Win, Lose
  }

  public int getColumnCount() {
    return cols;
  }

  public int getRowCount() {
    return rows;
  }

  public GameGrid() {
    this.rows = 0;
    this.cols = 0;
    this.numberOfMines = 0;
  }

  public void setup(int rows, int columns, int numberOfMines) {
    assert (rows > 0 && columns > 0 && numberOfMines > 0);
    this.rows = rows;
    this.cols = columns;
    this.numberOfMines = numberOfMines;
    this.result = Result.NotDefined;

    gridList.clear();

    List<Boolean> minePositions = new ArrayList<>();
    for (int i = 0; i < rows * columns; i++) {
      minePositions.add(i < numberOfMines);
    }
    Collections.shuffle(minePositions);
    for (int i = 0; i < rows * columns; i++) {
      gridList.add(new GameSquare(i, minePositions.get(i)));
    }
    setNumberOfMinesAroundSquares();
  }

  private void setNumberOfMinesAroundSquares() {
    for (int i = 0, end = rows * cols; i < end; i++) {
      int k = 0;
      int curX = getXByPosition(i), curY = getYByPosition(i);
      for (int x = curX - 1; x <= curX + 1; x++)
        for (int y = curY - 1; y <= curY + 1; y++)
          if (checkCoordinate(x, y) && getSquare(x, y).hasMine()) {
            k++;
          }
      gridList.get(i).setNumberOfMinesAround(k);
    }
  }

  private GameSquare getSquare(int X, int Y) {
    return gridList.get(getPositionByCoordinates(X, Y));
  }

  private boolean checkCoordinate(int x, int y) {
    return x >= 0 && x < cols && y >= 0 && y < rows;
  }

  public ListIterator<GameSquare> getIterator() {
    return gridList.listIterator();
  }

  public GameSquare getSquare(int i) {
    assert (i < gridList.size());
    return gridList.get(i);
  }

  public void openGameSquaresAroundZero(int position) {
    assert position < gridList.size();

    if (gridList.get(position).getState() == GameSquare.SquareState.TouchedEmpty) {
      return;
    } else if (gridList.get(position).getNumberOfMinesAround() != 0) {
      gridList.get(position).setState(GameSquare.SquareState.TouchedEmpty);
      return;
    } else {
      gridList.get(position).setState(GameSquare.SquareState.TouchedEmpty);
    }

    int curX = getXByPosition(position), curY = getYByPosition(position);
    for (int x = curX - 1; x <= curX + 1; x++)
      for (int y = curY - 1; y <= curY + 1; y++)
        if (checkCoordinate(x, y)) {
          openGameSquaresAroundZero(getPositionByCoordinates(x, y));
        }
  }

  public int getXByPosition(int position) {
    return position % cols;
  }

  public int getYByPosition(int position) {
    return position / cols;
  }

  public int getPositionByCoordinates(int X, int Y) {
    return Y * cols + X;
  }

  public Result getResult() {
    return result;
  }

  public boolean checkResult() {
    ListIterator<GameSquare> iterator = getIterator();
    Result result = Result.Win;
    if (!iterator.hasNext()) {
      return false;
    }
    while (iterator.hasNext()) {
      GameSquare square = iterator.next();
      if (square.getState() == GameSquare.SquareState.Exploded) {
        result = Result.Lose;
        break;
      } else if (!square.hasMine() && square.getState() == GameSquare.SquareState.Untouched) {
        result = Result.NotDefined;
      }
    }
    if (result != Result.NotDefined) {
      this.result = result;
      return true;
    }
    return false;
  }
}
