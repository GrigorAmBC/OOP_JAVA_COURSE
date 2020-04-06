package ru.nsu.fit.grigor.stack_calculator.command;

import ru.nsu.fit.grigor.stack_calculator.exception.ExecutionException;
import ru.nsu.fit.grigor.stack_calculator.exception.MissingStackArgumentException;
import ru.nsu.fit.grigor.stack_calculator.interfaces.Command;

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
