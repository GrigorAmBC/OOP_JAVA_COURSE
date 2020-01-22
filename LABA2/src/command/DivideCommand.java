package command;

import exception.ExecutionException;
import exception.MissingStackArgumentException;
import exception.ZeroDivisionException;
import interfaces.Command;

import java.util.List;
import java.util.Map;

public class DivideCommand implements Command {
  @Override
  public void execute(List<Double> stack, Map<String, Double> parameters) throws ExecutionException {
    if (stack.size() < 2)
      throw new MissingStackArgumentException();

    Double a1, a2;// TODO: maybe check size and throw exception??
    a2 = stack.remove(stack.size() - 1);
    a1 = stack.remove(stack.size() - 1);
    if (a2 == 0)
      throw new ZeroDivisionException();
    stack.add(a1 / a2);
  }
}
