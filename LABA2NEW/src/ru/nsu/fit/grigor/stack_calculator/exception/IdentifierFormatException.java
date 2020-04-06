package ru.nsu.fit.grigor.stack_calculator.exception;

public class IdentifierFormatException extends ExecutionException {
  @Override
  public String toString() {
    return "Wrong identifier format! It must be a sequence of alphabet symbols!";
  }
}
