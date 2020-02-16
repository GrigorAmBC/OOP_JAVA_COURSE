package ru.nsu.fit.grigor.bomb_sweeper.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ScoreSavior implements ScoreRepository {
  private List<Score> scoreList;
  private String scoreFileName = "scores.txt";

  public ScoreSavior() {
    scoreList = new ArrayList<>();
    try {
      File file = new File(scoreFileName);
      file.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    }
    setScoreList();
  }

  @Override
  public void addScore(Score newScore) {
      setScoreList();
      for (Score score : scoreList) {
        if (newScore.getMode() == score.getMode()) {
          if (newScore.getTime() < score.getTime()) {
            scoreList.remove(score);
            scoreList.add(newScore);
            saveScoreListToFile();
          }
          return;
        }
      }

      scoreList.add(newScore);
      saveScoreListToFile();
  }


  @Override
  public List<Score> getScores() {
    return this.scoreList;
  }

  private void saveScoreListToFile() {
    Type foundListType = new TypeToken<ArrayList<Score>>() {
    }.getType();
    Gson gson = new Gson();
    try (Writer writer = new OutputStreamWriter(new FileOutputStream(scoreFileName))) {
      writer.write(gson.toJson(scoreList, foundListType));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void setScoreList() {
    Gson gson = new Gson();
    try (Reader reader = new InputStreamReader(new FileInputStream(scoreFileName))) {
      Type foundListType = new TypeToken<ArrayList<Score>>() {
      }.getType();
      scoreList.clear();
      List<Score> scores = gson.fromJson(reader, foundListType);
      if (scores != null)
        scoreList.addAll(scores);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


}
