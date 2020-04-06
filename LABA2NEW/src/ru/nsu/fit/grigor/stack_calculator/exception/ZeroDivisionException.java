package ru.nsu.fit.grigor.stack_calculator.exception;

public class ZeroDivisionException extends ExecutionException {
  @Override
  public String toString() {
    return "Cannot divide by zero!";
  }
}
