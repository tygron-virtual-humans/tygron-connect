package nl.tudelft.contextproject.tygron.api.loaders;

import nl.tudelft.contextproject.tygron.api.CallType;
import nl.tudelft.contextproject.tygron.api.HttpConnection;
import nl.tudelft.contextproject.tygron.handlers.objects.FunctionMapResultHandler;
import nl.tudelft.contextproject.tygron.objects.FunctionMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FunctionMapLoader extends Loader<FunctionMap> {
  private static final Logger logger = LoggerFactory.getLogger(FunctionMapLoader.class);

  @Override
  protected FunctionMap load() {
    logger.debug("Loading functions");
    return HttpConnection.getInstance().execute("lists/functions",
        CallType.GET, new FunctionMapResultHandler(), true);
  }

  @Override
  public Class<FunctionMap> getDataClass() {
    return FunctionMap.class;
  }

  @Override
  public RefreshInterval getRefreshInterval() {
    return RefreshInterval.NEVER;
  }
}
