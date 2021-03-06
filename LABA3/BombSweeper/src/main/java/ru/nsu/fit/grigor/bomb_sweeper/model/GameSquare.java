package ru.nsu.fit.grigor.bomb_sweeper.model;


public class GameSquare {
  private int position;
  private boolean bomb;
  private SquareState state = SquareState.Untouched;
  private int numberOfMinsAround = 0;



  public enum SquareState {
    TouchedEmpty, Flag, Untouched, Exploded;
  }
  public GameSquare(int position, boolean isBomb) {
    this.position = position;
    bomb = isBomb;
  }

  public int getPosition() {
    return position;
  }

  public int getNumberOfMinesAround() {
    return numberOfMinsAround;
  }

  public void setNumberOfMinesAround(int number) {
    if (number < 0)
      throw new IllegalArgumentException("Negative number of mines around a square");
    numberOfMinsAround = number;
  }

  public boolean hasMine() {
    return bomb;
  }

  public boolean hasFlag() {
    return state == GameSquare.SquareState.Flag;

  }

  public SquareState getState() {
    return state;
  }

  public void setState(SquareState state) {
    this.state = state;
  }
}
