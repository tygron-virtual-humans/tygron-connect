package nl.tudelft.contextproject.tygron.handlers.objects;

import nl.tudelft.contextproject.tygron.handlers.JsonArrayResultHandler;
import nl.tudelft.contextproject.tygron.handlers.ResultHandler;
import nl.tudelft.contextproject.tygron.objects.LandMap;

public class LandMapResultHandler extends ResultHandler<LandMap> {
  @Override
  public LandMap handleResult(String input) {
    return new LandMap(new JsonArrayResultHandler().handleResult(input));
  }
}
