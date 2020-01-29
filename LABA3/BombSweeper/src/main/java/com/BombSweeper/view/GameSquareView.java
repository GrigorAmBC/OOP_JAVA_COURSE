package com.BombSweeper.view;

import com.BombSweeper.model.GameSquare.SquareState;

import javax.swing.*;
import java.awt.*;

public class GameSquareView extends JButton {
  private SquareState state;
  private int position;
  private final static ImageIcon iconFlag, iconMine, iconUntouched;
  public final static int SIZE = 30;

  static {
    iconFlag = new ImageIcon(ClassLoader.getSystemResource("flag_square.jpg"));
    iconMine = new ImageIcon(ClassLoader.getSystemResource("mine_square.jpg"));
    iconUntouched = new ImageIcon(ClassLoader.getSystemResource("untouched_square.png"));//"untouched_square.png"));
  }


  public GameSquareView() {
    this.state = SquareState.Untouched;
    setIcon(iconUntouched);
    setPreferredSize(new Dimension(SIZE, SIZE));
  }


  public SquareState getState() {
    return state;
  }

  public void setState(SquareState state) {
    this.state = state;

    if (state == SquareState.TouchedEmpty) {
      setState(state, 0);
      return;
    }

    setIcon(switch (state) {
      case Exploded -> iconMine;
      case Flag -> iconFlag;
      case Untouched -> iconUntouched;
      default -> null;
    });
  }

  public void setState(SquareState state, int numberOfMines) {
    if (state == SquareState.TouchedEmpty) {
      setBackground(Color.lightGray);
      setIcon(null);
      setText(String.valueOf(numberOfMines));
    } else
      setState(state);
  }

  public void setPosition(int position) {
    this.position = position;
  }

  public int getPosition() {
    return position;
  }
}
