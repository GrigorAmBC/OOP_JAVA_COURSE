package com.BombSweeper.model;


import com.BombSweeper.Observable;
import com.BombSweeper.Observer;


public class GameSquare {
  public enum SquareState{
    Touched, Flag, Unknown, Untouched
  }

  private int coordinateX;
  private int coordinateY;
  private boolean bomb = false;
  private SquareState state = SquareState.Untouched;
  private int numberOfMinsAround = 0;

  public GameSquare(int X, int Y) {
    if (X < 1 || Y < 1)
      throw new IllegalArgumentException("W");
    coordinateX = X;
    coordinateY = Y;
  }

  public GameSquare(int X, int Y, boolean isBomb) {
    if (X < 1 || Y < 1)
      throw new IllegalArgumentException("W");
    coordinateX = X;
    coordinateY = Y;
    bomb = isBomb;
  }

  public int getCoordinateX() {
    return coordinateX;
  }

  public int getCoordinateY() {
    return coordinateY;
  }

  int getNumberOfMinsAround() {
    return numberOfMinsAround;
  }

  public void setNumberOfMinsAround(int number) {
    if (number < 0)
      throw new IllegalArgumentException("Negative number of mins around a square");
    numberOfMinsAround = number;
  }

  public boolean hasMine() {
    return bomb;
  }

  public SquareState getState() {
    return state;
  }

  public void setState(SquareState state) {
    this.state = state;
  }



}
