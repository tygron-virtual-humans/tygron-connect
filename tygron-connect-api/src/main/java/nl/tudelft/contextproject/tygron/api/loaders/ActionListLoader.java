package nl.tudelft.contextproject.tygron.api.loaders;

import nl.tudelft.contextproject.tygron.api.CallType;
import nl.tudelft.contextproject.tygron.api.HttpConnection;
import nl.tudelft.contextproject.tygron.api.Session;
import nl.tudelft.contextproject.tygron.handlers.objects.ActionListResultHandler;
import nl.tudelft.contextproject.tygron.objects.ActionList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActionListLoader extends Loader<ActionList> {
  private static final Logger logger = LoggerFactory.getLogger(BuildingListLoader.class);

  @Override
  protected ActionList load() {
    logger.debug("Loading buildings");
    return HttpConnection.getInstance().execute("lists/actionmenus",
            CallType.GET, new ActionListResultHandler(), true);
  }

  @Override
  public Class<ActionList> getDataClass() {
    return ActionList.class;
  }

  @Override
  public RefreshInterval getRefreshInterval() {
    return RefreshInterval.NEVER;
  }
}
