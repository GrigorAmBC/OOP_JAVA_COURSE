package exception;

public class ZeroDivisionException extends ExecutionException {
  @Override
  public String toString() {
    return "Cannot divide by zero!";
  }
}
