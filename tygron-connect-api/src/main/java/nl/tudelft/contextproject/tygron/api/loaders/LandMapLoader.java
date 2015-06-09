package nl.tudelft.contextproject.tygron.api.loaders;

import nl.tudelft.contextproject.tygron.api.CallType;
import nl.tudelft.contextproject.tygron.api.HttpConnection;
import nl.tudelft.contextproject.tygron.api.Session;
import nl.tudelft.contextproject.tygron.handlers.objects.LandMapResultHandler;
import nl.tudelft.contextproject.tygron.objects.LandMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LandMapLoader extends Loader<LandMap> {
  private static final Logger logger = LoggerFactory.getLogger(LandMapLoader.class);

  public LandMapLoader(HttpConnection connection, Session session) {
    super(connection, session);
  }

  @Override
  protected LandMap load() {
    logger.debug("Loading lands");
    return connection.execute("lists/lands",
            CallType.GET, new LandMapResultHandler(), session);
  }

  @Override
  public Class<LandMap> getDataClass() {
    return LandMap.class;
  }
}
