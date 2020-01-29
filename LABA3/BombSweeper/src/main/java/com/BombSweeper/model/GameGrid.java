package com.BombSweeper.model;

import java.util.*;

public class GameGrid {//todo: make changes available
  private List<GameSquare> grid = new ArrayList<>();
  private int rows;
  private int cols;
  private int numberOfMines;

  public int getColumnNumber() { //TODO: replace with tuple in BL??
    return rows;
  }

  public int getRowNumber() { //TODO: replace with tuple in BL??
    return cols;
  }

  public GameGrid() {
    this.rows = 0;
    this.cols = 0;
    this.numberOfMines = 0;
  }

  public void setup(int rows, int columns, int numberOfMines) {
    assert(rows > 0 && columns > 0 && numberOfMines > 0);//TODO: move this to another place
    this.rows = rows;
    this.cols = columns;
    this.numberOfMines = numberOfMines;

    grid.clear();
    // setup grid
    List<Boolean> tmp = new ArrayList<>(this.rows * cols);
    for (int i = 0; i < this.numberOfMines; i++) {
      tmp.set(i, true);
    }
    Collections.shuffle(tmp);
    for (int i = 0; i < this.rows * cols; i++) {// TODO: check this
      grid.add(new GameSquare(getXByPosition(i), getYByPosition(i), tmp.get(i)));
    }
    setNumberOfMinsAroundSquares();
  }

  private void setNumberOfMinsAroundSquares() {
    for (int i = 0, end = rows * cols; i < end; i++) {
      int k = 0;
      if (i - 1 >= 0 && grid.get(i - 1).hasMine()) {
        k++;
      }
      if (i + 1 < end && grid.get(i + 1).hasMine()) {
        k++;
      }
      if (i - rows >= 0 && grid.get(i - rows).hasMine()) {
        k++;
      }
      if (i + rows < end && grid.get(i + rows).hasMine()) {
        k++;
      }
      if (i - rows + 1 >= 0 && grid.get(i - rows + 1).hasMine()) {
        k++;
      }
      if (i - rows - 1 >= 0 && grid.get(i - rows - 1).hasMine()) {
        k++;
      }
      if (i + rows + 1 < end && grid.get(i + rows + 1).hasMine()) {
        k++;
      }
      if (i + rows - 1 < end && grid.get(i + rows - 1).hasMine()) {
        k++;
      }

      grid.get(i).setNumberOfMinsAround(k);
    }
  }

  public GameSquare getGameSquare(int position) {
    assert(position >= 0);
    return grid.get(position);
  }

  private int getXByPosition(int X) { // TODO: check
    return X / cols + 1;
  }

  private int getYByPosition(int Y) {// TODO: check
    return Y / rows + 1;
  }

  private int getPositionInList(int X, int Y) {// TODO: check
    return X * Y - 1;
  }

  public ListIterator<GameSquare> getIterator() {
    return grid.listIterator();
  }

  public GameSquare getSquare(int X, int Y) {
    return grid.get(getPositionInList(X, Y));
  }

  public GameSquare getSquare(int i) {
    assert(i < grid.size());
    return grid.get(i);
  }

  public int getNumberOfMines() {
    return numberOfMines;
  }


}
