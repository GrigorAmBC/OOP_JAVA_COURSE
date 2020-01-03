import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.*;

public class FrequencyCounter {
  private TreeMap<String, Integer> words = new TreeMap<>();

  private void insertWord(String word) {
    words.merge(word, 1,(tableElement, tableElement2) -> tableElement+1);
  }

  public void buildSetOfWords(Reader reader) throws IOException {
    if (reader == null)
      throw new IllegalArgumentException();// TODO: change to nonnull

    StringBuilder builder = new StringBuilder();

    words.clear();
    int symbol;
    while ((symbol = reader.read()) != -1) {
      if (Character.isLetterOrDigit(symbol)) {
        builder.append(Character.toLowerCase((char)symbol));
      } else if (!builder.toString().isEmpty()) {
        insertWord(builder.toString());
        builder.delete(0, builder.length());
      }
    }

    if (!builder.toString().isEmpty()) {
      insertWord(builder.toString());
      builder.delete(0, builder.length() - 1);
    }
  }

  private SortedSet<Map.Entry<String, Integer>> getSortedWords() {
    TreeSet<Map.Entry<String, Integer>> sortedEntries = new TreeSet<>(new Comparator<Map.Entry<String, Integer>>() {
      @Override
      public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
        if (o1.getValue() > o2.getValue())
          return -1;
        else if (o1.getValue() < o2.getValue())
          return 1;
        else if (o1.getKey().equals(o2.getKey()))
          return 0;

        return -1;
      }
    });
    sortedEntries.addAll(words.entrySet());
    return sortedEntries;
  }

  private int getAmountOfOcurrences() {
    int sum = 0;
    for (Map.Entry<String, Integer> a : words.entrySet()) {
      sum += a.getValue();
    }

    return sum;
  }

  public void writeSetOfWordsToCSV(Writer writer) throws IOException {
    if (writer == null)
      throw new IllegalArgumentException();

    writer.write("Word,Frequency,Frequency(%)\n");

    SortedSet<Map.Entry<String, Integer>> sortedSet = getSortedWords();
    int overallFrequency = getAmountOfOcurrences();
    StringBuilder builder = new StringBuilder();
    for (Map.Entry<String, Integer> o : sortedSet) {
      builder.append(o.getKey()).append(",")
              .append(o.getValue()).append(",")
              .append((double) 100*o.getValue() / overallFrequency).append("\n");

      writer.write(builder.toString());
      builder.delete(0, builder.length());
    }
  }


  public TreeMap<String, Integer> getWords() {
    return words;
  }
}
