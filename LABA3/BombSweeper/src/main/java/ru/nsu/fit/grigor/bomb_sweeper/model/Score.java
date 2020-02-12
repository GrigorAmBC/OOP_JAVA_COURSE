package ru.nsu.fit.grigor.bomb_sweeper.model;

public class Score {
  private Integer time;
  private GameModel.Mode mode;

  public Score(int time, GameModel.Mode mode) {
    this.time = time;
    this.mode = mode;
  }

  public Integer getTime() {
    return time;
  }

  public void setTime(Integer time) {
    this.time = time;
  }

  public GameModel.Mode getMode() {
    return mode;
  }

  public void setMode(GameModel.Mode mode) {
    this.mode = mode;
  }
}
