package nl.tudelft.contextproject.tygron.api.loaders;

import nl.tudelft.contextproject.tygron.api.CallType;
import nl.tudelft.contextproject.tygron.api.HttpConnection;
import nl.tudelft.contextproject.tygron.handlers.objects.LandMapResultHandler;
import nl.tudelft.contextproject.tygron.objects.LandMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Loads LandMap.
 */
public class LandMapLoader extends Loader<LandMap> {
  private static final Logger logger = LoggerFactory.getLogger(LandMapLoader.class);

  @Override
  protected LandMap load() {
    logger.debug("Loading lands");
    return HttpConnection.getInstance().execute("lists/lands",
            CallType.GET, new LandMapResultHandler(), true);
  }

  @Override
  public Class<LandMap> getDataClass() {
    return LandMap.class;
  }

  @Override
  public RefreshInterval getRefreshInterval() {
    return RefreshInterval.NORMAL;
  }
}
