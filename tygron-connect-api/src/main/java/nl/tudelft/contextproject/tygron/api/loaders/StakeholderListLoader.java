package nl.tudelft.contextproject.tygron.api.loaders;

import nl.tudelft.contextproject.tygron.api.CallType;
import nl.tudelft.contextproject.tygron.api.HttpConnection;
import nl.tudelft.contextproject.tygron.handlers.objects.StakeholderListResultHandler;
import nl.tudelft.contextproject.tygron.objects.Action;
import nl.tudelft.contextproject.tygron.objects.ActionList;
import nl.tudelft.contextproject.tygron.objects.Stakeholder;
import nl.tudelft.contextproject.tygron.objects.StakeholderList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Loads StakeholderList.
 */
public class StakeholderListLoader extends Loader<StakeholderList> {
  private static final Logger logger = LoggerFactory.getLogger(StakeholderListLoader.class);


  @Override
  public StakeholderList load() {
    logger.debug("Loading stakeholders");
    StakeholderList stakeholders = HttpConnection.getInstance().execute("lists/"
            + "stakeholders/", CallType.GET, new StakeholderListResultHandler(), true);
    setActions(stakeholders);
    return stakeholders;
  }

  /**
   * Load actions and assign their functions to stakeholders.
   */
  private void setActions(StakeholderList stakeholderList) {
    ActionList actionList = new ActionListLoader().get();
    for (Action action : actionList) {
      Map<Integer, Boolean> activeForStakeholder = action.getActiveForStakeholder();
      Set<Integer> stakeholders = activeForStakeholder.keySet();
      Map<Integer, List<Integer>> functionMap = new HashMap<>();
      for (Integer stakeholderId : stakeholders) {
        if (activeForStakeholder.get(stakeholderId)) {
          functionMap.put(stakeholderId, action.getFunctionTypes());
        }
      }
      setFunctions(functionMap, stakeholderList);
    }
  }

  private void setFunctions(Map<Integer, List<Integer>> functionsMap, StakeholderList stakeholderList) {
    for (Stakeholder stakeholder : stakeholderList) {
      List<Integer> functions = functionsMap.get(stakeholder.getId());
      if (functions != null) {
        stakeholder.addAllowedFunctions(functions);
      }
    }
  }

  @Override
  public Class<StakeholderList> getDataClass() {
    return StakeholderList.class;
  }

  @Override
  public RefreshInterval getRefreshInterval() {
    return RefreshInterval.NORMAL;
  }
}
