package ru.nsu.fit.grigor.bomb_sweeper.view;

import ru.nsu.fit.grigor.bomb_sweeper.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

import static ru.nsu.fit.grigor.bomb_sweeper.model.GameModel.Mode.*;

public class MainView extends JFrame implements IModelSubscriber<GameGrid> {
  GameModel gameModel = new GameModel();
  GameGridView gridView = new GameGridView();
  JPanel gamePanel = new JPanel();

  public MainView() {
    super("MineSweeper");
    setSize(1280, 720);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    gamePanel.setLayout(new GridBagLayout());
    GridBagConstraints gc = new GridBagConstraints();

    // menu
    JMenuBar menuBar;
    JMenu gameMenu;
    JMenuItem exitMenuItem, aboutMenuItem, scoreMenuItem, newGameMenuItem;

    //Create the menu bar.
    menuBar = new JMenuBar();
    gameMenu = new JMenu("Game");

    //Build menu.
    exitMenuItem = new JMenuItem("Exit");
    aboutMenuItem = new JMenuItem("About");
    scoreMenuItem = new JMenuItem("High Scores");
    newGameMenuItem = new JMenuItem("New game");


    gameMenu.add(newGameMenuItem);
    gameMenu.add(scoreMenuItem);
    gameMenu.add(aboutMenuItem);
    gameMenu.add(exitMenuItem);


    menuBar.add(gameMenu);

    this.setJMenuBar(menuBar);

    // menu listeners
    exitMenuItem.addActionListener(e -> {
      gameModel.exit();
      dispose();
    });
    aboutMenuItem.addActionListener(e -> {
      JOptionPane.showMessageDialog(this, "This game was originally" +
              " created for African children from Bangalour.");
    });
    scoreMenuItem.addActionListener(e -> {
      StringBuilder builder = new StringBuilder();
      List<Score> scores = gameModel.getScores();
      for (Score score : scores) {
        builder.append(score.getMode().toString()).append(": ").append(score.getTime()).append("\n");
      }
      if (scores.size() == 0)
        builder.append("No scores yet! You may be the first.");
      JOptionPane.showMessageDialog(this, builder.toString(), "High scores", JOptionPane.INFORMATION_MESSAGE);
    });

    newGameMenuItem.addActionListener(e -> {
      requestGameMode();
    });

    // mines
    gc.gridx = 0;
    gc.gridy = 1;
    gc.anchor = GridBagConstraints.LINE_START;
    TextAreaView mineTextArea = new TextAreaView();
    gameModel.getMineModel().subscribe(mineTextArea);
    gamePanel.add(mineTextArea, gc);


    // timer
    gc.gridx = 0;
    gc.gridy = 1;
    gc.anchor = GridBagConstraints.LINE_END;
    TextAreaView timerTextArea = new TextAreaView();
    gameModel.getTimerModel().subscribe(timerTextArea);
    gamePanel.add(timerTextArea, gc);


    // grid
    gc.gridx = 0;
    gc.gridy = 2;
    gameModel.getGameGridModel().subscribe(gridView);
    gameModel.getGameGridModel().subscribe(this);
    gridView.setControllerModel(gameModel);
    gamePanel.add(gridView, gc);
    add(gamePanel);

    setVisible(true);

    requestGameMode();
  }

  private void requestGameMode() {
    GameModel.Mode[] options = {Novice, Advanced, Expert, Custom};

    GameModel.Mode mode;
    int x = JOptionPane.showOptionDialog(null, "Choose the game mode, please",
            "Minesweeper Mode",
            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
    if (x != JOptionPane.CLOSED_OPTION) {
      mode = switch (x) {
        case 1 -> Advanced;
        case 2 -> Expert;
        case 0 -> Novice;
        case 3 -> Custom;
        default -> Novice;
      };

      if (mode == Custom) {
        JPanel myPanel = new JPanel();
        JTextField rowField = new HintTextField("rows      ");
        JTextField colField = new HintTextField("cols      ");
        JTextField mineNumberField = new HintTextField("mine number");

        myPanel.add(rowField);
        myPanel.add(colField);
        myPanel.add(mineNumberField);
        int rows, cols, numberOfMines;
      while (true) {
        JOptionPane.showMessageDialog(null, myPanel);
        try {
          rows = Integer.parseInt(rowField.getText());
          cols = Integer.parseInt(colField.getText());
          numberOfMines = Integer.parseInt(mineNumberField.getText());
        } catch (NumberFormatException e) {
          JOptionPane.showMessageDialog(null, "Wrong numbers.");
          continue;
        }

        if (gameModel.checkCustomMode(rows, cols, numberOfMines)) {
          gameModel.startCustomGame(rows, cols, numberOfMines);
          break;
        }
      }
      } else
        gameModel.startGame(mode);
    }
  }

  @Override
  public void modelChanged(Model<GameGrid> model) {
    switch (model.getProperty().getResult()) {
      case Lose -> JOptionPane.showMessageDialog(this, "You fired a bomb! Game over.", "Game over", JOptionPane.INFORMATION_MESSAGE);
      case Win -> JOptionPane.showMessageDialog(this, "You won! Game over.", "Game over", JOptionPane.INFORMATION_MESSAGE);
    }
  }

  private static class HintTextField extends JTextField implements FocusListener {

    private final String hint;
    private boolean showingHint;

    public HintTextField(final String hint) {
      super(hint);
      this.hint = hint;
      this.showingHint = true;
      super.addFocusListener(this);
    }

    @Override
    public void focusGained(FocusEvent e) {
      if (this.getText().isEmpty()) {
        super.setText("");
        showingHint = false;
      }
    }

    @Override
    public void focusLost(FocusEvent e) {
      if (this.getText().isEmpty()) {
        super.setText(hint);
        showingHint = true;
      }
    }

    @Override
    public String getText() {
      return showingHint ? "" : super.getText();
    }
  }
}
