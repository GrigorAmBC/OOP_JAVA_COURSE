package command;

import exception.ExecutionException;
import exception.MissingStackArgumentException;
import exception.SQRTNegativeArgumentException;
import interfaces.Command;

import java.util.List;
import java.util.Map;

public class SQRTCommand implements Command {
  @Override
  public void execute(List<Double> stack, Map<String, Double> parameters) throws ExecutionException {
    if (stack.isEmpty())
      throw new MissingStackArgumentException();
    if (stack.get(stack.size()-1) < 0)
      throw new SQRTNegativeArgumentException();
    stack.add(Math.sqrt(stack.remove(stack.size()-1)));
  }
}
