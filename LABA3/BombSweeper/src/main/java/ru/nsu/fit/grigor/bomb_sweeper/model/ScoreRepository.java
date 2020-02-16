package ru.nsu.fit.grigor.bomb_sweeper.model;

import java.util.List;

public interface ScoreRepository {
  void addScore(Score score);
  List<Score> getScores();
}
