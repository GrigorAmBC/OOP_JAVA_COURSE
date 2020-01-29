import exception.ExecutionException;
import factory.*;
import interfaces.Command;
import interfaces.CommandFactory;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

//TODO: logging, unit tests, other requirements
public class Main {
  private static List<Command> commands = new ArrayList<>();
  private static Map<String, Double> parameters = new HashMap<>();
  private static List<Double> stack = new ArrayList<>();
  public static Logger logger = Logger.getLogger(Main.class.getName());//todo: try configuring logger in a file, not from code

  public static void main(String[] args) {
    // open file or data stream
    BufferedReader reader = null;
    try {
      reader = args.length > 0 ? new BufferedReader(new FileReader(args[0])) :
              new BufferedReader(new InputStreamReader(System.in));
      setupStack(reader);
      logger.log(Level.INFO, "Commands were successfully built.");
    } catch (IOException e) {
      e.printStackTrace();
      logger.log(Level.SEVERE, "Error while opening the file with commands.");
    } finally {
      if (reader != null) {
        try {//TODO: is every case is ok here?
          reader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

/*    if (args.length > 0) {
      try {
        reader = new BufferedReader(new FileReader(args[0]));
        setupStack(reader);
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        if (reader != null) {
          try {
            reader.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    } else {
      // read from the terminal
      reader = new BufferedReader(new InputStreamReader(System.in));
      setupStack(reader);
    }*/
    // fetch, setup data of the stack
    execute();
  }

  private static void execute() {
    for (Command command : commands) {
      try {
        command.execute(stack, parameters); // TODO: change to execution context
        logger.log(Level.INFO, "Command successfully executed.");
      } catch (ExecutionException e) {
        logger.log(Level.WARNING, "Error while executing commands.");
        System.out.println(e.toString()); //TODO: maybe change message with the line of error??
        System.console();
      }
    }
  }

  private static void setupStack(BufferedReader reader) throws IOException {
    List<String> commandArguments = new ArrayList<>();
    String line;
    StringBuilder word = new StringBuilder();
    CommandFactory commandFactory;
    while ((line = reader.readLine()) != null) {
      char[] charArray = line.toCharArray();
      for (int i = 0; i < charArray.length; i++) {
        char a = charArray[i];
        if (a == ' ' || a == '#') {
          if (!word.toString().isEmpty()) {
            commandArguments.add(word.toString());
            word.setLength(0);// PUSH A     .  PUSH A #. PUSH A#. PUSH A.
          }
          if (a == '#')
            break;
        } else {
          word.append(a);
        }

        if (i == charArray.length-1) {
          if (!word.toString().isEmpty()) {
            commandArguments.add(word.toString());
            word.setLength(0);// PUSH A     .  PUSH A #. PUSH A#. PUSH A.
          }
        }

      }
      if (commandArguments.isEmpty())
        continue;

      commandFactory = createCommandFactory(commandArguments.remove(0));
      commands.add(commandFactory.factoryMethod(commandArguments));// TODO: change factory method as to fulfill the lab
      commandArguments.clear();
    }


  }

  private static CommandFactory createCommandFactory(String str) {
    return switch (str) {
      case "-" -> new SubstractCommandFactory();
      case "+" -> new AddCommandFactory();
      case "*" -> new MultiplyCommandFactory();
      case "/" -> new DivideCommandFactory();
      case "DEFINE" -> new DefineCommandFactory();
      case "SQRT" -> new SQRTCommandFactory();
      case "PUSH" -> new PushCommandFactory();
      case "POP" -> new PopCommandFactory();
      case "PRINT" -> new PrintCommandFactory();
      default -> new DummyCommandFactory();
    };
  }
}
