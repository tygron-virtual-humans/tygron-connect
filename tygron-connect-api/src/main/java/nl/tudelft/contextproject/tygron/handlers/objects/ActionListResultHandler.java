package nl.tudelft.contextproject.tygron.handlers.objects;

import nl.tudelft.contextproject.tygron.handlers.JsonArrayResultHandler;
import nl.tudelft.contextproject.tygron.handlers.ResultHandler;
import nl.tudelft.contextproject.tygron.objects.ActionList;

public class ActionListResultHandler extends ResultHandler<ActionList> {
  @Override
  public ActionList handleResult(String input) {
    return new ActionList(new JsonArrayResultHandler().handleResult(input));
  }
}

