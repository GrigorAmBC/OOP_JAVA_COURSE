import com.BombSweeper.Observable;
import com.BombSweeper.Observer;
import com.BombSweeper.model.GameGrid;

import java.util.ArrayList;
import java.util.List;

public class GameModel {
  private List<Observer> subscribers = new ArrayList<>();


  enum Mode {Novice, Advanced, Expert}
  private GameGrid gameGrid;
  private boolean isGameOn = false;


  public GameModel() {


  }


  public List<String> getScore() {
    // 1 - Define some data structure for a Score
    // 2 - Use gson for serialization/de..
//
//
  }

  public boolean gameOverCheck() {

  }

  public GameGrid getTable() {
    return gameGrid;
  }

  public void startGame(Mode mode) {
    gameGrid = switch (mode) {
      Mode.Novice -> new GameGrid(9, 9, 10);
      Mode.Advanced -> new GameGrid(16, 16, 40);
      Mode.Expert -> new GameGrid(16, 30, 99);
    };
    notifyObservers();
    // setup grid - notify
    // TODO:start clock


  }

  public void exit() { // ending of programm

  }

  private void saveScore() {

  }


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


}
