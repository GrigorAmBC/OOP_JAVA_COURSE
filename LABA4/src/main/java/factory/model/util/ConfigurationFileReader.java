package factory.model.util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ConfigurationFileReader {

  private String cfgFileName = "/configuration.txt";
  private Map<String, Integer> settings = new HashMap<>();

  public ConfigurationFileReader() {
    InputStream inputstream = null;
    try {
      inputstream = ConfigurationFileReader.class.getResourceAsStream(cfgFileName);
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
    finally {
      if (inputstream != null) {
        try { inputstream.close(); }
        catch (IOException e) { System.err.println(e.getLocalizedMessage()); }
      }
    }
  }


    /*
    BufferedReader reader = null;
    try {
      reader = new BufferedReader(new FileReader(cfgFileName));
      String line = reader.readLine();
      while ((line = reader.readLine()) != null) {
        String[] args = line.split("=");
        settings.put(args[0], Integer.parseInt(args[1]));
      }
    } catch (Exception ignored) {
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }}
*/


  public Map<String, Integer> getConfigurationMap() {
    return settings;
  }

}
