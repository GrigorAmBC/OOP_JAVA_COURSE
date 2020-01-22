package command;

import exception.ExecutionException;
import exception.MissingStackArgumentException;
import interfaces.Command;

import java.util.List;
import java.util.Map;

public class PopCommand implements Command {

  @Override
  public void execute(List<Double> stack, Map<String, Double> parameters) throws ExecutionException {
    if (stack.size() < 1)
      throw new MissingStackArgumentException();
    stack.remove(stack.size()-1);
  }
}
