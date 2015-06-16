package nl.tudelft.contextproject.tygron.handlers.objects;

import nl.tudelft.contextproject.tygron.handlers.JsonArrayResultHandler;
import nl.tudelft.contextproject.tygron.handlers.ResultHandler;
import nl.tudelft.contextproject.tygron.objects.BuildingList;

public class BuildingListResultHandler extends ResultHandler<BuildingList> {
  @Override
  public BuildingList handleResult(String input) {
    return new BuildingList(new JsonArrayResultHandler().handleResult(input));
  }
}
