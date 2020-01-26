package com.BombSweeper;

import com.BombSweeper.model.GameGrid;

public interface Observer {
  void modelChanged(GameGrid gameGrid);
}
