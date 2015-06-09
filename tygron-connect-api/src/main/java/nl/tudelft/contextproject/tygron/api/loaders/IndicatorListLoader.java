package nl.tudelft.contextproject.tygron.api.loaders;

import nl.tudelft.contextproject.tygron.api.CallType;
import nl.tudelft.contextproject.tygron.api.HttpConnection;
import nl.tudelft.contextproject.tygron.api.Session;
import nl.tudelft.contextproject.tygron.handlers.objects.IndicatorListResultHandler;
import nl.tudelft.contextproject.tygron.objects.indicators.IndicatorList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IndicatorListLoader extends Loader<IndicatorList> {
  private static final Logger logger = LoggerFactory.getLogger(IndicatorListLoader.class);

  public IndicatorListLoader(HttpConnection connection, Session session) {
    super(connection, session);
  }

  @Override
  public IndicatorList load() {
    logger.debug("Loading indicators");
    return connection.execute("lists/"
            + "indicators", CallType.GET, new IndicatorListResultHandler(), session);
  }

  @Override
  public Class<IndicatorList> getDataClass() {
    return IndicatorList.class;
  }
}
