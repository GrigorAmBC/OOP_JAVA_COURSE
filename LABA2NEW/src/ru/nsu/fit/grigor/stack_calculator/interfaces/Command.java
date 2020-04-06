package ru.nsu.fit.grigor.stack_calculator.interfaces;


import ru.nsu.fit.grigor.stack_calculator.exception.ExecutionException;

import java.util.List;
import java.util.Map;

public interface Command {
  void execute(List<Double> stack, Map<String, Double> parameters) throws ExecutionException;
  //void setCommandLineNumber(int line);
  default void build(List<String> arguments) {

  }
}
