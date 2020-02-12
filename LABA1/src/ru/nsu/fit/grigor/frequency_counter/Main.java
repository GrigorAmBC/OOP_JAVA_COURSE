package ru.nsu.fit.grigor.frequency_counter;

import java.io.*;

public class Main {

  public static void main(String[] args) {
    if (args.length < 1)
      throw new IllegalArgumentException();
    String inputFileName = args[0];
    FrequencyCounter frequencyCounter = null;
    try (Reader reader = new InputStreamReader(new FileInputStream(inputFileName))) {
      frequencyCounter = new FrequencyCounter();
      frequencyCounter.buildSetOfWords(reader);
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }

    String outputFileName = "frequency.csv";
    try (Writer writer = new OutputStreamWriter(new FileOutputStream(outputFileName))) {
      frequencyCounter.writeSetOfWordsToCSV(writer);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

