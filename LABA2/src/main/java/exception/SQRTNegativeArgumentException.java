package exception;

public class SQRTNegativeArgumentException extends ExecutionException {
  @Override
  public String toString() {
    return "Cannot compute the square root of a negative number!";
  }
}
