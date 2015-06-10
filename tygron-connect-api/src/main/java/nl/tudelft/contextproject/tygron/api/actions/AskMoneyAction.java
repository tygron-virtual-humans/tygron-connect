package nl.tudelft.contextproject.tygron.api.actions;

import nl.tudelft.contextproject.tygron.api.CallType;
import nl.tudelft.contextproject.tygron.api.Environment;
import nl.tudelft.contextproject.tygron.api.HttpConnection;
import nl.tudelft.contextproject.tygron.api.Session;
import nl.tudelft.contextproject.tygron.handlers.StringResultHandler;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AskMoneyAction {
  
  private static final Logger logger = LoggerFactory.getLogger(AskMoneyAction.class);
  
  private Session session;
  private Environment environment;
  
  public AskMoneyAction(Session session) {
    this.session = session;
    this.environment = session.getEnvironment();
  }
  
  /**
   * Ask money from the given stakeholder.
   * @param giverId The id of the stakeholder to ask money from.
   * @param amount The amount of money to ask for.
   */
  public void askMoney(int giverId, double amount) {
    logger.debug("Asking money from stakeholder #" + giverId);
    if (environment.getBudget(giverId) >= amount) {
      AskMoneyRequest askMoneyRequest = new AskMoneyRequest(session.getStakeholderId(), giverId, amount);
      HttpConnection.getInstance().execute("event/PlayerEventType/MONEY_TRANSFER_ASK/",
          CallType.POST, new StringResultHandler(), true, askMoneyRequest);
    } else {
      logger.debug("Stakeholder #" + giverId + " has less money than asked");
    }
  }
  
  class AskMoneyRequest extends JSONArray {
    public AskMoneyRequest(int askerId, int giverId, double amount) {
      this.put(askerId);
      this.put("SUBSIDY");
      this.put(giverId);
      this.put(askerId);
      this.put("");
      this.put(amount);
    }
  }
}
