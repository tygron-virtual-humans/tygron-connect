package nl.tudelft.contextproject.tygron.handlers.objects;

import nl.tudelft.contextproject.tygron.handlers.JsonArrayResultHandler;
import nl.tudelft.contextproject.tygron.handlers.ResultHandler;
import nl.tudelft.contextproject.tygron.objects.EconomyList;

public class EconomyListResultHandler extends ResultHandler<EconomyList> {
  @Override
  public EconomyList handleResult(String input) {
    return new EconomyList(new JsonArrayResultHandler().handleResult(input));
  }
}
