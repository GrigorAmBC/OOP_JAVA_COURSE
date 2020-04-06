package ru.nsu.fit.grigor.stack_calculator.command;

import ru.nsu.fit.grigor.stack_calculator.exception.ExecutionException;
import ru.nsu.fit.grigor.stack_calculator.exception.IdentifierFormatException;
import ru.nsu.fit.grigor.stack_calculator.exception.MissingCommandArgumentException;
import ru.nsu.fit.grigor.stack_calculator.interfaces.Command;

import java.util.List;
import java.util.Map;

public class DefineCommand implements Command {
  private String key;
  private Double value;

  @Override
  public void build(List<String> arguments) {
    if (arguments.size() < 2)
      return;// todo: exception here?

    key = arguments.get(0);
    value = Double.parseDouble(arguments.get(1));
  }


  @Override
  public void execute(List<Double> stack, Map<String, Double> parameters) throws ExecutionException {
    if (value.isNaN() || key.isEmpty())
      throw new MissingCommandArgumentException();
    try {
      Double.parseDouble(key);
    } catch (NumberFormatException e) {
      parameters.put(key, value);
      return;
    }
    throw new IdentifierFormatException();
  }
}


