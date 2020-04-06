package ru.nsu.fit.grigor.stack_calculator.command;

import ru.nsu.fit.grigor.stack_calculator.exception.ExecutionException;
import ru.nsu.fit.grigor.stack_calculator.exception.MissingIdentifierException;
import ru.nsu.fit.grigor.stack_calculator.interfaces.Command;

import java.util.List;
import java.util.Map;

public class PushCommand implements Command {
  private String arg;

  @Override
  public void build(List<String> arguments) {
    if (arguments.isEmpty())
      return;// todo
    arg = arguments.get(0);
  }

  @Override
  public void execute(List<Double> stack, Map<String, Double> parameters) throws ExecutionException {
    char start = arg.charAt(0);
    if (start >= '0' && start <= '9') {
      stack.add(Double.parseDouble(arg));
    } else {
      if (parameters.containsKey(arg)) {
        stack.add(parameters.get(arg));
      } else {
        throw new MissingIdentifierException();
      }
    }
  }

}
