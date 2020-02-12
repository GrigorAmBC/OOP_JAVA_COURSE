package ru.nsu.fit.grigor.frequency_counter;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.*;

public class FrequencyCounter {
  private Map<String, Integer> words = new HashMap<>();

  private void insertWord(String word) {
    words.merge(word, 1,(tableElement, tableElement2) -> tableElement+1);
  }

  public void buildSetOfWords(@NotNull Reader reader) throws IOException {
    StringBuilder builder = new StringBuilder();
    words.clear();
    int symbol;
    while ((symbol = reader.read()) != -1) {
      if (Character.isLetterOrDigit(symbol)) {
        builder.append(Character.toLowerCase((char)symbol));
      } else if (!(builder.length() == 0)) {
        insertWord(builder.toString());
        builder.setLength(0);
      }
    }

    if (!(builder.length() == 0)) {
      insertWord(builder.toString());
      builder.setLength(0);
    }
  }

  private List<Map.Entry<String, Integer>> getSortedWords() {
    List<Map.Entry<String, Integer>> result = new ArrayList<>(this.words.entrySet());
    result.sort((o1, o2) -> {
      if (o1.getValue() > o2.getValue())
        return -1;
      else if (o1.getValue() < o2.getValue())
        return 1;
      else if (o1.getKey().equals(o2.getKey()))
        return 0;

      return -1;
    });

    return result;
  }

  private int getAmountOfOcurrences() {
    int sum = 0;
    for (Map.Entry<String, Integer> a : words.entrySet()) {
      sum += a.getValue();
    }

    return sum;
  }

  public void writeSetOfWordsToCSV(@NotNull Writer writer) throws IOException {
    writer.write("Word,Frequency,Frequency(%)\n");

    List<Map.Entry<String, Integer>> sortedList = getSortedWords();
    int overallFrequency = getAmountOfOcurrences();
    for (Map.Entry<String, Integer> o : sortedList) {
      writer.append(o.getKey()).append(",")
              .append(o.getValue().toString()).append(",")
              .append(String.valueOf(100.*o.getValue() / overallFrequency)).append("\n");
    }
  }
}