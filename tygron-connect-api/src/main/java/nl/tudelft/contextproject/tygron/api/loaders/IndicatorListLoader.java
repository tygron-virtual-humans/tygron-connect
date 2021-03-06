package nl.tudelft.contextproject.tygron.api.loaders;

import nl.tudelft.contextproject.tygron.api.CallType;
import nl.tudelft.contextproject.tygron.api.HttpConnection;
import nl.tudelft.contextproject.tygron.handlers.objects.IndicatorListResultHandler;
import nl.tudelft.contextproject.tygron.objects.indicators.IndicatorList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Loads IndicatorList.
 */
public class IndicatorListLoader extends Loader<IndicatorList> {
  private static final Logger logger = LoggerFactory.getLogger(IndicatorListLoader.class);

  @Override
  public IndicatorList load() {
    logger.debug("Loading indicators");
    return HttpConnection.getInstance().execute("lists/"
            + "indicators", CallType.GET, new IndicatorListResultHandler(), true);
  }

  @Override
  public Class<IndicatorList> getDataClass() {
    return IndicatorList.class;
  }

  @Override
  public RefreshInterval getRefreshInterval() {
    return RefreshInterval.NORMAL;
  }
}
