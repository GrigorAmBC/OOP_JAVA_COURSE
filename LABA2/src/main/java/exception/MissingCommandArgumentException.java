package exception;

public class MissingCommandArgumentException extends  ExecutionException{
  @Override
  public String toString() {
    return "Arguments for the command are missing!";
  }
}
