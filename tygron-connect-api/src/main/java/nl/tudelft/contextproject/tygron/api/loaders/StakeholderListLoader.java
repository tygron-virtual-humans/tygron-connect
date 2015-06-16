package nl.tudelft.contextproject.tygron.api.loaders;

import nl.tudelft.contextproject.tygron.api.CallType;
import nl.tudelft.contextproject.tygron.api.HttpConnection;
import nl.tudelft.contextproject.tygron.api.Session;
import nl.tudelft.contextproject.tygron.handlers.objects.StakeholderListResultHandler;
import nl.tudelft.contextproject.tygron.objects.StakeholderList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StakeholderListLoader extends Loader<StakeholderList> {
  private static final Logger logger = LoggerFactory.getLogger(StakeholderListLoader.class);

  public StakeholderListLoader(Session session) {
    super(session);
  }

  @Override
  public StakeholderList load() {
    logger.debug("Loading stakeholders");
    return HttpConnection.getInstance().execute("lists/"
        + "stakeholders/", CallType.GET, new StakeholderListResultHandler(), session);
  }

  @Override
  public Class<StakeholderList> getDataClass() {
    return StakeholderList.class;
  }
}
