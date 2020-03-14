package ru.nsu.fit.grigor.bomb_sweeper.view.console;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.grigor.bomb_sweeper.model.GameGrid;
import ru.nsu.fit.grigor.bomb_sweeper.model.GameSquare;

import java.util.List;

public class ConsoleGrid {

  public ConsoleGrid() {
  }

  public void paintGrid(@NotNull GameGrid gameGrid) {
    int rows = gameGrid.getRowCount(), cols = gameGrid.getColumnCount();
    List<GameSquare> squareList = gameGrid.getGridList();
    System.out.print("    0  ");
    for (int i = 1; i < cols; i++)
      System.out.print(i + (i == cols - 1 ? "\n" : "  "));

    for (int i = rows - 1; i >= 0; i--) {
      System.out.print(i + (i < 10 ? "   " : "  "));
      for (int j = 0; j < cols; j++) {
        GameSquare square = squareList.get(i * rows + j);
        System.out.print(switch (square.getState()) {
          case Flag -> "F";
          case TouchedEmpty -> square.getNumberOfMinesAround();
          case Untouched -> "N";
          case Exploded -> "*";
        });
        System.out.print("  ");
      }
      System.out.println();
    }
  }
}
