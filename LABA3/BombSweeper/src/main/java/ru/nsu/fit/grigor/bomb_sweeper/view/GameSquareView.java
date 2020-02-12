package ru.nsu.fit.grigor.bomb_sweeper.view;

import ru.nsu.fit.grigor.bomb_sweeper.model.GameSquare;

import javax.swing.*;
import java.awt.*;

public class GameSquareView extends JButton {
  private GameSquare.SquareState state;
  private int position;
  private final static ImageIcon iconFlag, iconMine, iconUntouched;
  public final static int SIZE = 30;

  static {
    iconFlag = new ImageIcon(ClassLoader.getSystemResource("flag_square.jpg"));
    iconMine = new ImageIcon(ClassLoader.getSystemResource("mine_square.jpg"));
    iconUntouched = new ImageIcon(ClassLoader.getSystemResource("untouched_square.png"));//"untouched_square.png"));
  }


  public GameSquareView() {
    this.state = GameSquare.SquareState.Untouched;
    setIcon(iconUntouched);
    setPreferredSize(new Dimension(SIZE, SIZE));
    setMargin(new Insets(1, 1, 1, 1));
  }


  public GameSquare.SquareState getState() {
    return state;
  }

  private void setState(GameSquare.SquareState state) {
    this.state = state;

    if (state == GameSquare.SquareState.TouchedEmpty) {
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

  public void setState(GameSquare.SquareState state, int numberOfMines) {
    this.state= state;
    if (state == GameSquare.SquareState.TouchedEmpty) {
      setBackground(Color.white);
      setIcon(null);
      setText(numberOfMines != 0 ? String.valueOf(numberOfMines) : "");
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
