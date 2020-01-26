package com.BombSweeper.view;

import com.BombSweeper.model.GameGrid;
import com.BombSweeper.model.GameSquare;
import com.BombSweeper.model.GameSquare.SquareState;

import javax.swing.*;
import java.awt.*;

public class GameSquareView{
  // TODO: maybe use generics and subscribe each SquareView to
  // TODO: the corresponding gameSquare??
  private static Image untouchedSquareImage;
  private static Image touche

  private SquareState state;


  public GameSquareView() {
    this.state = SquareState.Untouched;

  }

  private void updateIcon() {
    this.set
    this.setImage(switch (state){
      SquareState.Untouched -> new Image().s;
      SquareState.Touched -> ;
      SquareState.Flag -> ;
      SquareState.Unknown -> ;
    });
  }

  public void updateView(GameSquare square) {

  }


}
