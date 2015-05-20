package nl.tudelft.contextproject.tygron.handlers;

public abstract class ResultHandler<T> {
  public abstract T handleResult(String input);
}
