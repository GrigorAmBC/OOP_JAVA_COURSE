package ru.nsu.fit.grigor.bomb_sweeper.view.console;

import ru.nsu.fit.grigor.bomb_sweeper.model.GameGrid;
import ru.nsu.fit.grigor.bomb_sweeper.model.GameModel;
import ru.nsu.fit.grigor.bomb_sweeper.model.GameSquare;
import ru.nsu.fit.grigor.bomb_sweeper.model.Score;
import ru.nsu.fit.grigor.bomb_sweeper.view.MessageViewer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static ru.nsu.fit.grigor.bomb_sweeper.model.GameModel.Mode.*;

public class MainConsole {
  public enum MenuItem {Exit, About, Start, Scores}

  private GameModel gameModel;
  private ConsoleGrid consoleGrid = new ConsoleGrid();
  private MessageViewer messageViewer = new ConsoleMessageViewer();


  public MainConsole() {
    gameModel = new GameModel(messageViewer);
    startGame();
  }

  private void startGame() {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    while (true) {
      try {
        switch (requestMenu(reader)) {
          case Start -> {
            while (requestGameMode(reader)) {
            }

            while (true) {
              paint();
              while (requestCoords(reader)) {
              }
              GameGrid.Result result = gameModel.getGameGridModel().getProperty().getResult();
              switch (result) {
                case Lose -> {
                  paint();
                  System.out.println("You fired a bomb! Game over");
                  reader.readLine();
                }
                case Win -> {
                  paint();
                  System.out.println("You won! Game over.");
                  reader.readLine();
                }
              }

              if (result != GameGrid.Result.NotDefined)
                break;
            }
          }
          case Exit -> {
            gameModel.exit();
            return;
          }
          case About -> {
            System.out.println("About game: " + gameModel.getAbout());
            reader.readLine();
          }
          case Scores -> {
            StringBuilder builder = new StringBuilder();
            List<Score> scores = gameModel.getScores();
            for (Score score : scores) {
              builder.append("\t").append(score.getMode().toString()).append(": ").append(score.getTime()).append("\n");
            }
            if (scores.size() == 0)
              builder.append("\t").append("No scores yet! You may be the first.");

            System.out.println("High scores: ");
            System.out.println(builder.toString());
            reader.readLine();
          }
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  }

  private boolean requestCoords(BufferedReader reader) throws IOException {
    // return true if something is wrong
    int row, col;
    try {
      System.out.print("Enter ROW number: ");
      row = Integer.parseInt(reader.readLine());
      System.out.print("Enter COLUMN number: ");
      col = Integer.parseInt(reader.readLine());
      if (!gameModel.getGameGridModel().getProperty().checkCoordinate(col, row)) {
        System.out.println("Coordinates are out of bounds! Please, try again.");
        reader.readLine();
        return true;
      }
      System.out.println("Do you want to FLAG/UNFLAG?  Yes : No");

      if (reader.readLine().toLowerCase().contains("y")) {
        gameModel.editGameSquare(getPosition(row, col), GameSquare.SquareState.Flag);
      } else {
        gameModel.editGameSquare(getPosition(row, col), GameSquare.SquareState.TouchedEmpty);
      }
    } catch (NumberFormatException e) {
      System.out.println("Wrong input. Try again");
      reader.readLine();
      return true;
    }

    return false;
  }

  private int getPosition(int row, int col) {
    return row * gameModel.getGameGridModel().getProperty().getRowCount() + col;
  }

  public synchronized void paint() {
    System.out.flush();

    // print mines
    System.out.println("Mines: " + gameModel.getMineModel().getProperty());

    // print timer
    System.out.println("Time: " + gameModel.getTimerModel().getProperty());

    // print grid
    consoleGrid.paintGrid(gameModel.getGameGridModel().getProperty());
  }

  private MenuItem requestMenu(BufferedReader reader) throws IOException {
    boolean flag;
    MenuItem item;

    do {
      flag = true;
      System.out.println("Game menu:");
      System.out.println("\tSTART to start new game");
      System.out.println("\tSCORES to see high scores");
      System.out.println("\tABOUT to see info about game");
      System.out.println("\tEXIT to exit");

      String str = reader.readLine();
      switch (str.toLowerCase()) {
        case "start" -> item = MenuItem.Start;
        case "exit" -> item = MenuItem.Exit;
        case "about" -> item = MenuItem.About;
        case "scores" -> item = MenuItem.Scores;
        default -> {
          item = MenuItem.Scores;
          flag = false;
          System.out.println("There's no such menu item. Please, pick one out of the list.");
          reader.readLine();
        }
      }
    } while (!flag);

    return item;
  }

  private boolean requestGameMode(BufferedReader reader) throws IOException {
    System.out.println("Choose the game mode, please");
    System.out.println("Novice, Advanced, Expert, Custom");
    System.out.println("Choose from 1 to 4 according to modes.");

    String str = reader.readLine();
    GameModel.Mode mode;
    try {
      mode = GameModel.Mode.values()[Integer.parseInt(str) - 1];
    } catch (RuntimeException e) {
      System.out.println("Wrong mode! Please, try again.");
      System.out.println("Press a key to continue");
      reader.readLine();
      return true;
    }

    if (mode != Custom) {
      gameModel.startGame(mode);
    } else {
      int rows, cols, numberOfMines;

      while (true) {
        System.out.println();
        try {
          System.out.print("Enter ROW number: ");
          rows = Integer.parseInt(reader.readLine());
          System.out.print("Enter COLUMN number: ");
          cols = Integer.parseInt(reader.readLine());
          System.out.print("Enter NUMBER of MINES: ");
          numberOfMines = Integer.parseInt(reader.readLine());
        } catch (NumberFormatException e) {
          System.out.println("Wrong input. Try again");
          continue;
        }

        if (!GameGrid.checkGridParameters(rows, cols, numberOfMines)) {
          System.out.println("Incorrect parameters. Please, try again.");
          reader.readLine();
        } else
          break;
      }

      gameModel.startCustomGame(rows, cols, numberOfMines);
    }

    return false;
  }
}