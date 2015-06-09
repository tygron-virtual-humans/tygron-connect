package nl.tudelft.contextproject.tygron.handlers.objects;

import nl.tudelft.contextproject.tygron.handlers.JsonArrayResultHandler;
import nl.tudelft.contextproject.tygron.handlers.ResultHandler;
import nl.tudelft.contextproject.tygron.objects.FunctionMap;

public class FunctionMapResultHandler extends ResultHandler<FunctionMap> {
  @Override
  public FunctionMap handleResult(String input) {
    return new FunctionMap(new JsonArrayResultHandler().handleResult(input));
  }
}
