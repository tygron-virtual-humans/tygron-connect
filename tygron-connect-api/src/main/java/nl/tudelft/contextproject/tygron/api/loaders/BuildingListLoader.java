package nl.tudelft.contextproject.tygron.api.loaders;

import nl.tudelft.contextproject.tygron.api.CallType;
import nl.tudelft.contextproject.tygron.api.HttpConnection;
import nl.tudelft.contextproject.tygron.api.Session;
import nl.tudelft.contextproject.tygron.handlers.objects.BuildingListResultHandler;
import nl.tudelft.contextproject.tygron.objects.BuildingList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BuildingListLoader extends Loader<BuildingList> {
  private static final Logger logger = LoggerFactory.getLogger(BuildingListLoader.class);

  public BuildingListLoader(Session session) {
    super(session);
  }

  @Override
  protected BuildingList load() {
    logger.debug("Loading buildings");
    return HttpConnection.getInstance().execute("lists/"
            + "buildings", CallType.GET, new BuildingListResultHandler(), session);
  }

  @Override
  public Class<BuildingList> getDataClass() {
    return BuildingList.class;
  }

  @Override
  public RefreshInterval getRefreshInterval() {
    return RefreshInterval.NORMAL;
  }
}
