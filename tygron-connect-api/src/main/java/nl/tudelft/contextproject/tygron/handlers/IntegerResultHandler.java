package nl.tudelft.contextproject.tygron.handlers;

public class IntegerResultHandler extends ResultHandler<Integer> {
  @Override
  public Integer handleResult(String input) {
    return Integer.valueOf(input);
  }
}
