package nl.tudelft.contextproject.tygron.api.loaders;

import nl.tudelft.contextproject.tygron.api.CallType;
import nl.tudelft.contextproject.tygron.api.HttpConnection;
import nl.tudelft.contextproject.tygron.handlers.objects.ZoneListResultHandler;
import nl.tudelft.contextproject.tygron.objects.ZoneList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZoneListLoader extends Loader<ZoneList> {
  private static final Logger logger = LoggerFactory.getLogger(ZoneListLoader.class);

  @Override
  public ZoneList load() {
    logger.debug("Loading zones");
    return HttpConnection.getInstance().execute("lists/"
            + "zones", CallType.GET, new ZoneListResultHandler(), true);
  }

  @Override
  public Class<ZoneList> getDataClass() {
    return ZoneList.class;
  }

  @Override
  public RefreshInterval getRefreshInterval() {
    return RefreshInterval.NORMAL;
  }
}
