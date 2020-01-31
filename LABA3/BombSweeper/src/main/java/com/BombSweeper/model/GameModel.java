package com.BombSweeper.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GameModel {
  private Model<GameGrid> gameGridModel;
  private Model<Integer> timerModel;
  private Model<Integer> mineModel;
  private Timer timer;
  private Mode currentMode;
  private List<Score> scoreList;
  private String scoreFileName = "scores.txt";
  private boolean isGameOn;

  public enum Mode {Novice, Advanced, Expert}

  public GameModel() {
    gameGridModel = new Model<>(new GameGrid());
    timerModel = new Model<>(0);
    mineModel = new Model<>(0);
    scoreList = new ArrayList<>();
    isGameOn = false;

    timer = new Timer(1000, e -> {
      timerModel.setProperty(timerModel.getProperty() + 1);
    });

    try {
      File file = new File(scoreFileName);
      file.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    }
    setScoreList();
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

  public List<Score> getScores() {
    return scoreList;
  }

  private void setScoreList() {
    Gson gson = new Gson();
    Reader reader = null;
    try {
      reader = new InputStreamReader(new FileInputStream(scoreFileName));
      Type foundListType = new TypeToken<ArrayList<Score>>() {
      }.getType();
      scoreList.clear();
      List<Score> scores = gson.fromJson(reader, foundListType);
      if (scores != null)
        scoreList.addAll(scores);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (null != reader) {
        try {
          reader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  private void saveScoreListToFile() {
    Type foundListType = new TypeToken<ArrayList<Score>>() {
    }.getType();
    Gson gson = new Gson();
    Writer writer = null;
    try {
      writer = new OutputStreamWriter(new FileOutputStream(scoreFileName));
      writer.write(gson.toJson(scoreList, foundListType));
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (null != writer) {
        try {
          writer.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  private void saveScore(Mode mode, int time) {
    setScoreList();
    Score newScore = new Score(time, mode);
    for (Score score : scoreList) {
      if (newScore.getMode() == score.getMode()) {
        if (newScore.getTime() < score.getTime()) {
          scoreList.remove(score);
          scoreList.add(newScore);
          saveScoreListToFile();
        }
        return;
      }
    }

    scoreList.add(newScore);
    saveScoreListToFile();
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
      if (state == GameSquare.SquareState.Untouched) {
        square.setState(GameSquare.SquareState.Untouched);
        mineModel.setProperty(mineModel.getProperty() + 1);
      }
    }


    if (gameGridModel.getProperty().checkResult()) {
      gameGridModel.setProperty(gameGridModel.getProperty());
      gameOver(gameGridModel.getProperty().getResult());
    } else
      gameGridModel.setProperty(gameGridModel.getProperty());
  }

  private void gameOver(GameGrid.Result result) {
    timer.stop();
    isGameOn = false;
    if (result == GameGrid.Result.Win)
      saveScore(currentMode, timerModel.getProperty());
  }

}
