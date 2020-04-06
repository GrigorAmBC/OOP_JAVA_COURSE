package ru.nsu.fit.grigor.stack_calculator.factory;

import ru.nsu.fit.grigor.stack_calculator.command.DummyCommand;
import ru.nsu.fit.grigor.stack_calculator.interfaces.Command;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class CommandFactoryCreator {
  private Map<String, Command> commandClassMap = new HashMap<>();
  private Properties properties = new Properties();
  private String configFileName = "config.txt";

  public CommandFactoryCreator() {
    // read from the file commands
    try (InputStream in = CommandFactoryCreator.class.getResourceAsStream(configFileName)) {
      properties.load(in);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public Command commandFactory(String commandName, List<String> args) {
    if (commandClassMap.get(commandName) != null) {
      return commandClassMap.get(commandName);
    } else {

      try {
        Command command = (Command) Class.forName(
                "ru.nsu.fit.grigor.stack_calculator.command."
                        + properties.getProperty(commandName))
                .getDeclaredConstructor().newInstance();

        command.build(args);
        commandClassMap.put(commandName, command);
        return command;
      } catch (Exception e) {
        e.printStackTrace();
      }

    }
    return new DummyCommand();
  }

}
