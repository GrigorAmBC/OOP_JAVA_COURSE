package com.BombSweeper.model

import com.BombSweeper.model.GameGrid;
import com.BombSweeper.model.Model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.TimerTask;

public class GameModel {
  private Model<GameGrid> gameGridModel;
  private Model<Integer> timerModel;
  private Model<Integer> mineModel;

  enum Mode {Novice, Advanced, Expert}

  enum ModelType {
    GameGrid, Timer, Mine
  }

  private boolean isGameOn = false;


  public GameModel() {
    // default
    gameGridModel = new Model<>(new GameGrid());
    timerModel = new Model<>(0);
    mineModel = new Model<>(0);
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

  public List<String> getScore() {
    // 1 - Define some data structure for a Score
    // 2 - Use gson for serialization/de..
    //
//
  }

  private boolean gameOverCheck() {

  }

  /*public GameGrid getTable() {
    return gameGridModel;
  }*/


  public class Reminder {
    Timer timer;

    public Reminder(int seconds) {
      timer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          timerModel.setProperty();
        }
      });
    }

    class RemindTask extends TimerTask {
      public void run() {
        System.out.println("Time's up!");
        timer.cancel(); //Terminate the timer thread
      }
    }

  public void startGame(Mode mode) {

    if (mode == Mode.Novice) {
      gameGridModel.getProperty().setup(9, 9, 10);
      this.mineModel.setProperty(10);
    } else if (mode == Mode.Advanced) {
      gameGridModel.getProperty().setup(16, 16, 40);
      this.mineModel.setProperty(40);
    } else {
      gameGridModel.getProperty().setup(16, 30, 99);
      this.mineModel.setProperty(99);
    }
    timerModel.

  }




  //notifyObservers();
  // setup grid - notify
  // TODO:start clock


}

  private void notifySubscribers() {
    gameGridModel.notifySubscribers();
    timerModel.notifySubscribers();
    mineModel.notifySubscribers();
  }

  public void exit() { // ending of programm

  }

  private void saveScore() {

  }

/*
  @Override
  public void subscribe(Observer observer) {
    if (observer == null)
      throw new NullPointerException("Null observer");
    if (subscribers.contains(observer))
      throw new IllegalArgumentException("Observer already subscribed");
    subscribers.add(observer);
  }

  @Override
  public void unsubscribe(Observer observer) {
    if (observer == null)
      throw new NullPointerException("Null observer");
    if (!subscribers.remove(observer))
      throw new IllegalArgumentException("Observer already subscribed");
  }

  @Override
  public void notifyObservers() {
    for (Observer observer : subscribers) {
      observer.modelChanged(gameGrid);
    }
  }

*/
}
