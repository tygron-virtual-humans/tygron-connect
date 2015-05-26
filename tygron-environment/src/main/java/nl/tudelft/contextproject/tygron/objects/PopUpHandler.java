package nl.tudelft.contextproject.tygron.objects;

import nl.tudelft.contextproject.tygron.api.CallType;
import nl.tudelft.contextproject.tygron.api.HttpConnection;
import nl.tudelft.contextproject.tygron.api.Session;
import nl.tudelft.contextproject.tygron.handlers.JsonObjectResultHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PopUpHandler {
  
  // When selling land, 0 is YES, 1 is NO, 2 is GIVE FOR FREE
  // When requesting confirmation, 0 is OK
  // When requesting a building permit, 0 is YES, 1 is NO
  // When answering a building permit request, 0 is YES, 1 is NO
  
  public enum EventValue {
    LAND_SELL_REQUEST_SENT, LAND_BUY__REQUEST_SENT, LAND_TRANSACTION_APPROVED, 
    LAND_TRANSACION_REFUSED, LAND_BUY_REQUEST_RECEIVED, LAND_SELL_REQUEST_RECEIVED, 
    PERMIT_REQUEST_ASK, PERMIT_REQUEST_RECEIVED, PERMIT_REQUEST_SENT, 
    PERMIT_REQUEST_APPROVED, PERMIT_REQUEST_REFUSED, ZONING_DIVERGED, 
    ZONING_DIVERGED_PERSONAL
  }
  
  private HttpConnection apiConnection;
  private Session session;
  
  private int version;
  private int stakeholderId;
  private List<PopUp> list;
  private Map<EventValue, String> wordsMap;
  
  /**
   * A list containing all new, active popups.
   * @param localApiConnection The connection
   * @param session The session
   * @param stakeholderId The id of the player's stakeholder
   */
  public PopUpHandler(HttpConnection localApiConnection, Session session, int stakeholderId) {
    apiConnection = localApiConnection;
    this.session = session;
    this.stakeholderId = stakeholderId;
    this.version = 0;
    this.wordsMap = new HashMap<EventValue, String>();
    loadServerWords();
  }
  
  /**
   * Loads all the server words about popups.
   */
  private void loadServerWords() {
    String res;
    res = replace(getServerWord(3));
    wordsMap.put(EventValue.PERMIT_REQUEST_RECEIVED, res);
    res = replace(getServerWord(6));
    wordsMap.put(EventValue.ZONING_DIVERGED, res);
    res = replace(getServerWord(7));
    wordsMap.put(EventValue.ZONING_DIVERGED_PERSONAL, res);
    res = replace(getServerWord(180));
    wordsMap.put(EventValue.LAND_BUY_REQUEST_RECEIVED, res);
    res = replace(getServerWord(181));
    wordsMap.put(EventValue.LAND_TRANSACTION_APPROVED, res);
    res = replace(getServerWord(182));
    wordsMap.put(EventValue.LAND_SELL_REQUEST_RECEIVED, res);
    res = replace(getServerWord(183));
    wordsMap.put(EventValue.LAND_TRANSACION_REFUSED, res);
    res = replace(getServerWord(223));
    wordsMap.put(EventValue.LAND_BUY__REQUEST_SENT, res);
    res = replace(getServerWord(224));
    wordsMap.put(EventValue.LAND_SELL_REQUEST_SENT, res);
    res = replace(getServerWord(231));
    wordsMap.put(EventValue.PERMIT_REQUEST_REFUSED, res);
    res = replace(getServerWord(233));
    wordsMap.put(EventValue.PERMIT_REQUEST_ASK, res);
    res = replace(getServerWord(234));
    wordsMap.put(EventValue.PERMIT_REQUEST_APPROVED, res);
    res = replace(getServerWord(236));
    wordsMap.put(EventValue.PERMIT_REQUEST_SENT, res);
  }
  
  /**
   * Gets a server word from the Tygron API.
   * @return The server word translation
   */
  private String getServerWord(int wordId) {
    return apiConnection.execute("lists/serverwords/" + wordId + "/", CallType.GET, 
        new JsonObjectResultHandler(), session).getString("translation");
  }
  
  /**
   * Replaces every occurence of an line break or %s with a regular expression.
   * @return A regular expression
   */
  private String replace(String text) {
    String res = text.replace("\\n", "(.*)").replace("%s", "(.*)");
    res =  res.replace("</p>", "(.*)").replace("<p>", "(.*)");
    return "(.*)" + res + "(.*)";
  }
  
  /**
   * Gets new popups from the API update.
   */
  public void loadPopUps() {
    JSONObject dataObject = apiConnection.getUpdate(new JsonObjectResultHandler(), session, 
        getRequestObject());
    if (dataObject != null) {
      updateList(dataObject.getJSONObject("items").getJSONObject("POPUPS"));
    } else {
      list = new ArrayList<PopUp>();
    }
  }
  
  /**
   * Creates a JSONObject which can be used as input for the Tygron API update.
   * @return The input for update
   */
  private JSONObject getRequestObject() {
    return new JSONObject("{\"POPUPS\":" + this.version + "}");
  }
  
  /**
   * Puts all new popups visible to the user in a list.
   * @param object The JSONObject containing new popups
   */
  private void updateList(JSONObject object) {
    list = new ArrayList<PopUp>();
    JSONArray popUpArray = object.getJSONArray("[Lnl.tytech.core.data.item.Item;");
    for (int i = 0; i < popUpArray.length(); i++) {
      PopUp popUp = new PopUp(popUpArray.getJSONObject(i));
      this.version = max(this.version, popUp.getVersion());
      if (popUp.getVisibleForActorIds().contains(this.stakeholderId)) {
        setEvent(popUp);
        list.add(popUp);
      }
    }
    handlePopUps();
  }
  
  /**
   * Finds a match in wordsList for the popUp text and sets the popup's event.
   * @param popUp The popUp
   */
  private void setEvent(PopUp popUp) {
    String text = popUp.getText().trim();
    Set<EventValue> events = wordsMap.keySet();
    for (EventValue event : events) {
      if (text.matches(wordsMap.get(event))) {
        popUp.setEvent(event);
        setEventParameters(popUp);
        return;
      }
    }
  }
  
  /**
   * Set cost and surface for each popup, if applicable.
   * @param popUp The popup
   */
  private void setEventParameters(PopUp popUp) {
    EventValue event = popUp.getEvent();
    String[] numList = getNumbers(popUp.getText());
    int cost;
    int surface;
    switch (event) {
      case LAND_TRANSACTION_APPROVED:
        cost = Integer.parseInt(numList[0].replace(".", ""));
        popUp.setCost(cost);
        break;
      case LAND_BUY_REQUEST_RECEIVED:
        surface = Integer.parseInt(numList[0].replace(".", ""));
        popUp.setSurface(surface);
        cost = Integer.parseInt(numList[1].replace(".", ""));
        popUp.setCost(cost);
        break;
      case LAND_SELL_REQUEST_RECEIVED:
        surface = Integer.parseInt(numList[0].replace(".", ""));
        popUp.setSurface(surface);
        cost = Integer.parseInt(numList[1].replace(".", ""));
        popUp.setCost(cost);
        break;
      default: // Do nothing (LAND_SELL_REQUEST_SENT, LAND_BUY__REQUEST_SENT, PERMIT_REQUEST_SENT)
        break;
    }
  }
    
  /**
   * Extracts numbers from a string.
   * @param string The string
   * @return The array of numbers
   */
  private String[] getNumbers(String string) { 
    string = string.substring(0, string.length() - 1);
    string = string.replaceAll("[^.?0-9]+", " ");
    return string.trim().split(" ");
  }
  
  /**
   * Handles the popups appropriately.
   */
  private void handlePopUps() {
    for (PopUp popUp : list) {
      EventValue event = popUp.getEvent();
      switch (event) {
        case LAND_TRANSACTION_APPROVED:
          landTransactionApproved(popUp);
          break;
        case LAND_TRANSACION_REFUSED:
          landTransactionRefused(popUp);
          break;
        case LAND_BUY_REQUEST_RECEIVED:
          landBuyRequestReceived(popUp);
          break;
        case LAND_SELL_REQUEST_RECEIVED:
          landSellRequestReceived(popUp);
          break;
        case PERMIT_REQUEST_ASK:
          permitRequestAsk(popUp);
          break;
        case PERMIT_REQUEST_RECEIVED:
          permitRequestReceived(popUp);
          break;
        case PERMIT_REQUEST_APPROVED:
          permitRequestApproved(popUp);
          break;
        case PERMIT_REQUEST_REFUSED:
          permitRequestRefused(popUp);
          break;
        case ZONING_DIVERGED:
          zoneDiverged(popUp);
          break;
        case ZONING_DIVERGED_PERSONAL:
          zoneDivergedPersonal(popUp);
          break;
        default: // Do nothing (LAND_SELL_REQUEST_SENT, LAND_BUY__REQUEST_SENT, PERMIT_REQUEST_SENT)
          break;
      }
    }
  }

  private void landTransactionApproved(PopUp popUp) {
    // TODO Send info to stakeholder
  }
  
  private void landTransactionRefused(PopUp popUp) {
    // TODO Send info to stakeholder
  }
  
  private void landBuyRequestReceived(PopUp popUp) {
    // TODO Send info to stakeholder
  }
  
  private void landSellRequestReceived(PopUp popUp) {
    // TODO Send info to stakeholder
  }
  
  private void permitRequestAsk(PopUp popUp) {
    JSONArray parameters = new JSONArray();
    parameters.put(stakeholderId);
    parameters.put(popUp.getId());
    parameters.put(0);
    apiConnection.execute("event/PlayerEventType/POPUP_ANSWER/", CallType.POST, 
        new JsonObjectResultHandler(), session, parameters);
  }
  
  private void permitRequestReceived(PopUp popUp) {
    // TODO Send info to stakeholder
  }
  
  private void zoneDivergedPersonal(PopUp popUp) {
    // TODO Change zone max floors and function categories
  }

  private void zoneDiverged(PopUp popUp) {
    // TODO Send info to stakeholder
  }

  private void permitRequestRefused(PopUp popUp) {
    // TODO Send info to stakeholder
  }

  private void permitRequestApproved(PopUp popUp) {
    // TODO Send info to stakeholder
  }
  
  public List<PopUp> getList() {
    return list;
  }
  
  private int max(int i1, int i2) {
    if (i1 > i2) {
      return i1;
    }
    return i2;
  }
}
