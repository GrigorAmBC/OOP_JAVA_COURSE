package factory.model.util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ConfigurationFileReader {
  private String cfgFileName = "/configuration.txt";
  private Map<String, Integer> settings = new HashMap<>();

  public ConfigurationFileReader() {
    try (InputStream inputstream = ConfigurationFileReader.class.getResourceAsStream(cfgFileName)) {
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputstream));
      String line;
      while ((line = reader.readLine()) != null) {
        String[] args = line.split("=");
        if (args[1].equals("true"))
          args[1] = "1";
        else if (args[1].equals("false"))
          args[1] = "0";
        settings.put(args[0], Integer.parseInt(args[1]));
      }
    }
    catch (Exception e) {
      System.err.println(e.getLocalizedMessage());
    }
  }

  public Map<String, Integer> getConfigurationMap() {
    return settings;
  }
}
