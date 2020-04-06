package ru.nsu.fit.grigor.stack_calculator.exception;

public class MissingCommandException extends ExecutionException {
  @Override
  public String toString() {
    return "There's no such command!";
  }
}
