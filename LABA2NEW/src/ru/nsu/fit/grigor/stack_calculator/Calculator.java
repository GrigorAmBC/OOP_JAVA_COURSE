package ru.nsu.fit.grigor.stack_calculator;

import ru.nsu.fit.grigor.stack_calculator.exception.ExecutionException;
import ru.nsu.fit.grigor.stack_calculator.factory.CommandFactoryCreator;
import ru.nsu.fit.grigor.stack_calculator.interfaces.Command;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Calculator {
  private List<Command> commands = new ArrayList<>();
  private Map<String, Double> parameters = new HashMap<>();
  private List<Double> stack = new ArrayList<>();
  private Logger logger = Logger.getLogger(Main.class.getName());//todo: try configuring logger in a file, not from code
  private CommandFactoryCreator commandFactoryCreator;

  public Calculator(String[] args) {
    commandFactoryCreator = new CommandFactoryCreator();

    try (BufferedReader reader = args.length > 0 ? new BufferedReader(new FileReader(args[0])) :
            new BufferedReader(new InputStreamReader(System.in))) {
      setupStack(reader);
      logger.log(Level.INFO, "Commands were successfully built.");
    } catch (IOException e) {
      e.printStackTrace();
      logger.log(Level.SEVERE, "Error while opening the file with commands.");
    }
  }

  public void execute() {
    for (Command command : commands) {
      try {
        command.execute(stack, parameters);
        logger.log(Level.INFO, "Command successfully executed.");
      } catch (ExecutionException e) {
        logger.log(Level.WARNING, "Error while executing a command.");
        e.printStackTrace();
      }
    }
  }

  private void setupStack(BufferedReader reader) throws IOException {
    List<String> commandArguments = new ArrayList<>();
    String line;
    StringBuilder word = new StringBuilder();
    while ((line = reader.readLine()) != null) {
      if (line.equals("END"))
        return;
      for (char c : line.toUpperCase().toCharArray()) {
        if (c == ' ' && word.length() != 0) {
          commandArguments.add(word.toString());
          word.setLength(0);
        } else if (c != '#')
          word.append(c);
        else
          break;
      }
      if (word.length() != 0) {
        commandArguments.add(word.toString());
        word.setLength(0);
      }

      if (commandArguments.isEmpty())
        continue;

      commands.add(commandFactoryCreator.commandFactory(
              commandArguments.remove(0),
              commandArguments));
      commandArguments.clear();
    }
  }
}
