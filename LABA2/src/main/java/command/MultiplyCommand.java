package command;

import exception.ExecutionException;
import exception.MissingStackArgumentException;
import interfaces.Command;

import java.util.List;
import java.util.Map;

public class MultiplyCommand implements Command {
  @Override
  public void execute(List<Double> stack, Map<String, Double> parameters) throws ExecutionException {
    if (stack.size() < 2)
      throw new MissingStackArgumentException();
    Double a1, a2;
    a2 = stack.remove(stack.size()-1);
    a1 = stack.remove(stack.size()-1);
    stack.add(a1*a2);
  }
}
