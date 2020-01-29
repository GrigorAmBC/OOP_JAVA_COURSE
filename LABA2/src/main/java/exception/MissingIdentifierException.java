package exception;

public class MissingIdentifierException extends ExecutionException {
  @Override
  public String toString() {
    return "Identifier is missing!";
  }
}
