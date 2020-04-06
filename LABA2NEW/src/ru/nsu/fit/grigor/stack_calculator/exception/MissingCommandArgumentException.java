package ru.nsu.fit.grigor.stack_calculator.exception;

public class MissingCommandArgumentException extends  ExecutionException{
  @Override
  public String toString() {
    return "Arguments for the command are missing!";
  }
}
