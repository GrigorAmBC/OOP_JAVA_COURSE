package ru.nsu.fit.grigor.bomb_sweeper.model;

import javax.swing.*;
import java.util.List;

public class GameModel {
  private Model<GameGrid> gameGridModel;
  private Model<Integer> timerModel;
  private Model<Integer> mineModel;
  private Timer timer;
  private Mode currentMode;

  public boolean isGameOn() {
    return isGameOn;
  }

  private boolean isGameOn;
  private ScoreRepository scoreRepository = new ScoreSavior();

  public void doubleClick(GameSquare.SquareState state, int position) {
    assert position >= 0;
    if (gameGridModel.getProperty().openSquares(state, position)) {
      if (gameGridModel.getProperty().checkResult()) {
        gameGridModel.setProperty(gameGridModel.getProperty());
        gameOver(gameGridModel.getProperty().getResult());
      } else
        gameGridModel.setProperty(gameGridModel.getProperty());
    }
  }

  public String getAbout() {
    return "This game was originally created for African children from Bangalour.";
  }

  public enum Mode {
    Novice, Advanced(), Expert, Custom;
  }

  public GameModel() {
    gameGridModel = new Model<>(new GameGrid());
    timerModel = new Model<>(0);
    mineModel = new Model<>(0);
    isGameOn = false;

    timer = new Timer(1000, e -> {
      timerModel.setProperty(timerModel.getProperty() + 1);
    });
  }

  public Model<GameGrid> getGameGridModel() {
    return gameGridModel;
  }

  public Model<Integer> getTimerModel() {
    return timerModel;
  }

  public Model<Integer> getMineModel() {
    return mineModel;
  }

  public void startGame(Mode mode) {
    assert mode != Mode.Custom;

    currentMode = mode;
    isGameOn = true;
    if (mode == Mode.Novice) {
      gameGridModel.getProperty().setup(9, 9, 10);
      this.mineModel.setProperty(10);
    } else if (mode == Mode.Advanced) {
      gameGridModel.getProperty().setup(16, 16, 40);
      this.mineModel.setProperty(40);
    } else if (mode == Mode.Expert) {
      gameGridModel.getProperty().setup(16, 30, 99);
      this.mineModel.setProperty(99);
    }

    gameGridModel.setProperty(gameGridModel.getProperty());
    timerModel.setProperty(0);
    timer.restart();
  }

  public boolean checkCustomMode(int rows, int cols, int numberOfMines) {
    return rows > 0 && cols > 0
            && rows < 16 && cols < 30
            && numberOfMines >= 0 && numberOfMines < rows * cols;
  }

  public void startCustomGame(int rows, int cols, int numberOfMines) {
    assert checkCustomMode(rows, cols, numberOfMines);
    currentMode = Mode.Custom;
    isGameOn = true;

    gameGridModel.getProperty().setup(rows, cols, numberOfMines);
    this.mineModel.setProperty(numberOfMines);

    gameGridModel.setProperty(gameGridModel.getProperty());
    timerModel.setProperty(0);
    timer.restart();
  }

  public List<Score> getScores() {
    return scoreRepository.getScores();
  }

  public void exit() {
    timer.stop();
    timer = null;
  }

  public void editGameSquare(int position, GameSquare.SquareState state) {
    assert (position >= 0);
    if (!isGameOn)
      return;

    GameGrid grid = gameGridModel.getProperty();
    GameSquare square = grid.getSquare(position);

    if (square.getState() == GameSquare.SquareState.Untouched) {
      switch (state) {
        case Flag -> {
          if (mineModel.getProperty() > 0) {
            square.setState(GameSquare.SquareState.Flag);
            mineModel.setProperty(mineModel.getProperty() - 1);
          }
        }
        case TouchedEmpty -> {
          if (square.hasMine()) {
            square.setState(GameSquare.SquareState.Exploded);
          } else {
            grid.openGameSquaresAroundZero(position);
            square.setState(GameSquare.SquareState.TouchedEmpty);
          }
        }
      }
    } else if (square.getState() == GameSquare.SquareState.Flag) {
      if (state == GameSquare.SquareState.Untouched || state == GameSquare.SquareState.Flag) {
        square.setState(GameSquare.SquareState.Untouched);
        mineModel.setProperty(mineModel.getProperty() + 1);
      }
    }

    gameGridModel.getProperty().checkResult();
    switch (gameGridModel.getProperty().getResult()) {
      case Win -> {
        gameOver(gameGridModel.getProperty().getResult());
        gameGridModel.setProperty(gameGridModel.getProperty());
      }
      case Lose -> {
        gameOver(gameGridModel.getProperty().getResult());
        gameGridModel.getProperty().openMines();
        gameGridModel.setProperty(gameGridModel.getProperty());
      }
      default -> gameGridModel.setProperty(gameGridModel.getProperty());
    }
  }

  private void gameOver(GameGrid.Result result) {
    timer.stop();
    isGameOn = false;
    if (result == GameGrid.Result.Win)
      scoreRepository.addScore(new Score(timerModel.getProperty(), currentMode));
  }

}
