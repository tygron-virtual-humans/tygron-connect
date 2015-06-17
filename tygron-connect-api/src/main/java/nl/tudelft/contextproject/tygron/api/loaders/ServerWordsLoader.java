package nl.tudelft.contextproject.tygron.api.loaders;

import nl.tudelft.contextproject.tygron.api.CallType;
import nl.tudelft.contextproject.tygron.api.HttpConnection;
import nl.tudelft.contextproject.tygron.handlers.objects.ServerWordsResultHandler;
import nl.tudelft.contextproject.tygron.objects.ServerWords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerWordsLoader extends Loader<ServerWords> {
  private static final Logger logger = LoggerFactory.getLogger(ServerWordsLoader.class);

  @Override
  protected ServerWords load() {
    logger.debug("Loading ServerWords");
    return HttpConnection.getInstance().execute("lists/serverwords/",
            CallType.GET, new ServerWordsResultHandler(), true);
  }

  @Override
  public Class<ServerWords> getDataClass() {
    return ServerWords.class;
  }

  @Override
  public RefreshInterval getRefreshInterval() {
    return RefreshInterval.NEVER;
  }
}
