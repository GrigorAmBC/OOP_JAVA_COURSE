package exception;

public class MissingStackArgumentException extends ExecutionException {
  @Override
  public String toString() {
    return "Stack arguments are missing, cannot operate correctly!";
  }
}
