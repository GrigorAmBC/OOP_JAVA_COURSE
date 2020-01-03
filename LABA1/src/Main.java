import java.io.*;

public class Main {

  public static void main(String[] args) {
    if (args.length < 1)
      throw new IllegalArgumentException();
    String inputFileName = args[0];
    FrequencyCounter frequencyCounter = null;
    Reader reader = null;
    try {
      reader = new InputStreamReader(new FileInputStream(inputFileName));
      frequencyCounter = new FrequencyCounter();
      frequencyCounter.buildSetOfWords(reader);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (null != reader) {
        try {
          reader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    if (frequencyCounter == null)
      return;

    String outputFileName = "frequency.csv";
    Writer writer = null;
    try {
      writer = new OutputStreamWriter(new FileOutputStream(outputFileName));
      frequencyCounter.writeSetOfWordsToCSV(writer);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (null != writer) {
        try {
          writer.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
