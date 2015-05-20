package nl.tudelft.contextproject.tygron.results;

public class BooleanResultHandler extends ResultHandler<Boolean> {
  @Override
  public Boolean handleResult(String input) {
    return Boolean.valueOf(input);
  }
}
