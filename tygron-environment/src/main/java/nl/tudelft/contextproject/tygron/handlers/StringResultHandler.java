package nl.tudelft.contextproject.tygron.handlers;

public class StringResultHandler extends ResultHandler<String> {
  @Override
  public String handleResult(String input) {
    return String.valueOf(input);
  }
}
