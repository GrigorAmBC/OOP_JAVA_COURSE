package command;

import exception.ExecutionException;
import exception.MissingIdentifierException;
import interfaces.Command;

import java.util.List;
import java.util.Map;

public class PushCommand implements Command {
  private String arg;

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

  @Override
  public void build(List<String> arguments) {
    if (arguments.size() < 1)
      return;
    arg = arguments.get(0);
  }
}
