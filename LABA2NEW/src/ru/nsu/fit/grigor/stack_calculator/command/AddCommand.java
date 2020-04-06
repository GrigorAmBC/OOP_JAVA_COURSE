package ru.nsu.fit.grigor.stack_calculator.command;

import ru.nsu.fit.grigor.stack_calculator.exception.ExecutionException;
import ru.nsu.fit.grigor.stack_calculator.exception.MissingStackArgumentException;
import ru.nsu.fit.grigor.stack_calculator.interfaces.Command;

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
