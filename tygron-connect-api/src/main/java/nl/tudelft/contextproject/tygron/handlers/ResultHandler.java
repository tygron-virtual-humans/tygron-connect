package nl.tudelft.contextproject.tygron.handlers;

/**
 * A ResultHandler is given an API response String and is able to transform this string into a different object.
 * @param <T>
 */
public abstract class ResultHandler<T> {
  public abstract T handleResult(String input);
}
