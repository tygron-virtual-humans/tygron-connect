package nl.tudelft.contextproject.tygron.api.actions;

import nl.tudelft.contextproject.tygron.api.CallType;
import nl.tudelft.contextproject.tygron.api.Environment;
import nl.tudelft.contextproject.tygron.api.HttpConnection;
import nl.tudelft.contextproject.tygron.api.Session;
import nl.tudelft.contextproject.tygron.handlers.StringResultHandler;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GiveMoneyAction {
  
  private static final Logger logger = LoggerFactory.getLogger(GiveMoneyAction.class);
  
  private Session session;
  private Environment environment;
  
  public GiveMoneyAction(Session session) {
    this.session = session;
    this.environment = session.getEnvironment();
  }
  
  /**
   * Give money to the given stakeholder.
   * @param receiverId The id of the stakeholder to give money to.
   * @param amount The amount of money to ask for.
   */
  public void giveMoney(int receiverId, double amount) {
    logger.debug("Giving money to stakeholder #" + receiverId);
    if (environment.getBudget(session.getStakeholderId()) >= amount) {
      GiveMoneyRequest giveMoneyRequest = new GiveMoneyRequest(session.getStakeholderId(), receiverId, amount);
      HttpConnection.getInstance().execute("event/PlayerEventType/MONEY_TRANSFER_GIVE/",
          CallType.POST, new StringResultHandler(), true, giveMoneyRequest);
    } else {
      logger.debug("Selected stakeholder has less money than given");
    }
  }
  
  class GiveMoneyRequest extends JSONArray {
    public GiveMoneyRequest(int giverId, int receiverId, double amount) {
      this.put(giverId);
      this.put("TRANSFER");
      this.put(giverId);
      this.put(receiverId);
      this.put("");
      this.put(amount);
    }
  }
}
