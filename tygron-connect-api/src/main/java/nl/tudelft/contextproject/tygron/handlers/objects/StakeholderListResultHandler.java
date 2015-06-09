package nl.tudelft.contextproject.tygron.handlers.objects;

import nl.tudelft.contextproject.tygron.handlers.JsonArrayResultHandler;
import nl.tudelft.contextproject.tygron.handlers.ResultHandler;
import nl.tudelft.contextproject.tygron.objects.StakeholderList;

public class StakeholderListResultHandler extends ResultHandler<StakeholderList> {

  @Override
  public StakeholderList handleResult(String input) {
    return new StakeholderList(new JsonArrayResultHandler().handleResult(input));
  }
}
