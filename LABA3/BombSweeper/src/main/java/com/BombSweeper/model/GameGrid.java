package com.BombSweeper.model;

import java.util.*;

public class GameGrid {
  private List<GameSquare> grid = new ArrayList<>();
  private int sizeX;
  private int sizeY;
  private int numberOfMins;

  public int getSizeX() { //TODO: replace with tuple in BL??
    return sizeX;
  }

  public int getSizeY() { //TODO: replace with tuple in BL??
    return sizeY;
  }

  public GameGrid(int sizeX, int sizeY, int numberOfMins) {
    if (sizeX < 1 || sizeY < 1) {
      throw new IllegalArgumentException("Wrong size of the game grid");
    }
    if (numberOfMins < 0) {
      throw new IllegalArgumentException("Wrong number of mins");
    }

    this.sizeX = sizeX;
    this.sizeY = sizeY;
    this.numberOfMins = numberOfMins;
    setup();
  }

  private void setup() {
    grid.clear();
    // setup grid
    List<Boolean> tmp = new ArrayList<>(sizeX * sizeY);
    for (int i = 0; i < numberOfMins; i++) {
      tmp.set(i, true);
    }
    Collections.shuffle(tmp);
    for (int i = 0; i < sizeX * sizeY; i++) {// TODO: check this
      grid.add(new GameSquare(getXByPosition(i), getYByPosition(i), tmp.get(i)));
    }
    setNumberOfMinsAroundSquares();
  }

  private void setNumberOfMinsAroundSquares() {
    for (int i = 0, end = sizeX * sizeY; i < end; i++) {
      int k = 0;
      if (i - 1 >= 0 && grid.get(i - 1).hasMine()) {
        k++;
      }
      if (i + 1 < end && grid.get(i + 1).hasMine()) {
        k++;
      }
      if (i - sizeX >= 0 && grid.get(i - sizeX).hasMine()) {
        k++;
      }
      if (i + sizeX < end && grid.get(i + sizeX).hasMine()) {
        k++;
      }
      if (i - sizeX + 1 >= 0 && grid.get(i - sizeX + 1).hasMine()) {
        k++;
      }
      if (i - sizeX - 1 >= 0 && grid.get(i - sizeX - 1).hasMine()) {
        k++;
      }
      if (i + sizeX + 1 < end && grid.get(i + sizeX + 1).hasMine()) {
        k++;
      }
      if (i + sizeX - 1 < end && grid.get(i + sizeX - 1).hasMine()) {
        k++;
      }

      grid.get(i).setNumberOfMinsAround(k);
    }
  }


  private int getXByPosition(int X) { // TODO: check
    return X / sizeY + 1;
  }

  private int getYByPosition(int Y) {// TODO: check
    return Y / sizeX + 1;
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

  public int getNumberOfMins() {
    return numberOfMins;
  }
}
