package nl.tudelft.contextproject.tygron.handlers.objects;

import nl.tudelft.contextproject.tygron.handlers.JsonArrayResultHandler;
import nl.tudelft.contextproject.tygron.handlers.ResultHandler;
import nl.tudelft.contextproject.tygron.objects.ZoneList;

public class ZoneListResultHandler extends ResultHandler<ZoneList> {

  @Override
  public ZoneList handleResult(String input) {
    return new ZoneList(new JsonArrayResultHandler().handleResult(input));
  }
}
