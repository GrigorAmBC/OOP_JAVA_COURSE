package ru.nsu.fit.grigor.stack_calculator.exception;

public class MissingIdentifierException extends ExecutionException {
  @Override
  public String toString() {
    return "Identifier is missing!";
  }
}
