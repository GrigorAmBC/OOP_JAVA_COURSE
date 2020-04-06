package ru.nsu.fit.grigor.stack_calculator.command;

import ru.nsu.fit.grigor.stack_calculator.exception.ExecutionException;
import ru.nsu.fit.grigor.stack_calculator.exception.MissingStackArgumentException;
import ru.nsu.fit.grigor.stack_calculator.exception.SQRTNegativeArgumentException;
import ru.nsu.fit.grigor.stack_calculator.interfaces.Command;

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
