package command;

import exception.ExecutionException;
import exception.MissingStackArgumentException;
import interfaces.Command;

import java.util.List;
import java.util.Map;

public class AddCommand implements Command {

  @Override
  public void execute(List<Double> stack, Map<String, Double> parameters) throws ExecutionException {
    Double a1, a2;
    if (stack.size() < 2)
      throw new MissingStackArgumentException();
    a2 = stack.remove(stack.size()-1);
    a1 = stack.remove(stack.size()-1);
    stack.add(a1+a2);
  }

}
