package nl.tudelft.contextproject.tygron.api.loaders;

import nl.tudelft.contextproject.tygron.api.CallType;
import nl.tudelft.contextproject.tygron.api.HttpConnection;
import nl.tudelft.contextproject.tygron.api.Session;
import nl.tudelft.contextproject.tygron.handlers.objects.EconomyListResultHandler;
import nl.tudelft.contextproject.tygron.objects.EconomyList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EconomyListLoader extends Loader<EconomyList> {
  private static final Logger logger = LoggerFactory.getLogger(EconomyListLoader.class);

  @Override
  public EconomyList load() {
    logger.debug("Loading economies");
    return HttpConnection.getInstance().execute("lists/"
            + "economies", CallType.GET, new EconomyListResultHandler(), true);
  }

  @Override
  public Class<EconomyList> getDataClass() {
    return EconomyList.class;
  }

  @Override
  public RefreshInterval getRefreshInterval() {
    return RefreshInterval.NORMAL;
  }
}
