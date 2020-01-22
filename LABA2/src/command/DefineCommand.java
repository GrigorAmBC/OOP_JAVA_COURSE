package command;

import exception.ExecutionException;
import exception.MissingCommandArgumentException;
import interfaces.Command;

import java.util.List;
import java.util.Map;

public class DefineCommand implements Command {
  private String key = "";
  private Double value;

  @Override
  public void execute(List<Double> stack, Map<String, Double> parameters) throws ExecutionException {
    if (value.isNaN() || key.isEmpty())
      throw new MissingCommandArgumentException();
    parameters.put(key, value);
  }

  @Override
  public void build(List<String> arguments) {
    if (arguments.size() < 2)
      return;

    key = arguments.get(0);
    value = Double.parseDouble(arguments.get(1));
  }
}


