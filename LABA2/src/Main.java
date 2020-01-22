import exception.ExecutionException;
import exception.SQRTNegativeArgumentException;
import factory.*;
import interfaces.Command;
import interfaces.CommandFactory;

import java.io.*;
import java.util.*;


public class Main {
  private static List<Command> commands = new ArrayList<>();
  private static Map<String, Double> parameters = new HashMap<>();
  private static List<Double> stack = new ArrayList<>();

  public static void main(String[] args) {
    // open file or data stream
    BufferedReader reader = null;
    try {
      reader = args.length > 0 ? new BufferedReader(new FileReader(args[0])) :
              new BufferedReader(new InputStreamReader(System.in));
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
    StringBuilder message = new StringBuilder();
    for (Command command : commands) {
      try {
        command.execute(stack, parameters); // TODO: change to execution context
      } catch (ExecutionException e) {
        System.out.println(e.toString()); //TODO: maybe change message with the line of error??
      }
    }
  }

  private static void setupStack(BufferedReader reader) throws IOException {
    List<String> commandArguments = new ArrayList<>();
    String line;
    StringBuilder word = new StringBuilder();
    CommandFactory commandFactory;
    while ((line = reader.readLine()) != null) {
      for (char a : line.toCharArray()) {
        if (a == ' ' || a == '#') {
          if (word.toString().isEmpty()) {
            throw new IllegalArgumentException();
          } // TODO: review, somethign is wrong here

          commandArguments.add(word.toString());
          word.setLength(0);
          if (a == '#')
            break;
        } else {
          word.append(a);
        }
      }

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
      default -> throw new RuntimeException();
    };
  }
}
