package nl.tudelft.contextproject.tygron.objects;

import nl.tudelft.contextproject.tygron.api.CallType;
import nl.tudelft.contextproject.tygron.api.Environment;
import nl.tudelft.contextproject.tygron.api.HttpConnection;
import nl.tudelft.contextproject.tygron.handlers.JsonObjectResultHandler;
import nl.tudelft.contextproject.tygron.objects.PopUp.TypeValue;
import nl.tudelft.contextproject.util.PolygonUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PopUpHandler {
  
  private int requestsOpen;
  
  // When selling land, 0 is YES, 1 is NO, 2 is GIVE FOR FREE
  // When requesting confirmation, 0 is OK
  // When requesting a building permit, 0 is YES, 1 is NO
  // When answering a building permit request, 0 is YES, 1 is NO
  
  public enum EventValue {
    LAND_SELL_REQUEST_SENT, LAND_BUY__REQUEST_SENT, LAND_TRANSACTION_APPROVED, 
    LAND_TRANSACION_REFUSED, LAND_BUY_REQUEST_RECEIVED, LAND_SELL_REQUEST_RECEIVED, 
    PERMIT_REQUEST_ASK, PERMIT_REQUEST_RECEIVED, PERMIT_REQUEST_SENT, 
    PERMIT_REQUEST_APPROVED, PERMIT_REQUEST_REFUSED, ZONING_DIVERGED, PLAN_PERFORM_ASK
  }
  
  private Environment environment;
  
  private int version;
  private int stakeholderId;
  private List<PopUp> list;
  private Map<EventValue, String> wordsMap;
  
  /**
   * A list containing all new, active popups.
   * @param env The environment
   * @param stakeholderId The id of the player's stakeholder
   */
  public PopUpHandler(Environment env, int stakeholderId) {
    this.environment = env;
    this.stakeholderId = stakeholderId;
    this.version = 0;
    this.requestsOpen = 0;
    this.wordsMap = new HashMap<EventValue, String>();
    this.list = new ArrayList<PopUp>();
    loadServerWords();
  }
  
  /**
   * Loads all the server words about popups.
   */
  private void loadServerWords() {
    String res;
    res = replace(getServerWord(2));
    wordsMap.put(EventValue.PERMIT_REQUEST_RECEIVED, res);
    res = replace(getServerWord(165));
    wordsMap.put(EventValue.ZONING_DIVERGED, res);
    res = replace(getServerWord(92));
    wordsMap.put(EventValue.LAND_BUY_REQUEST_RECEIVED, res);
    res = replace(getServerWord(93));
    wordsMap.put(EventValue.LAND_TRANSACTION_APPROVED, res);
    res = replace(getServerWord(94));
    wordsMap.put(EventValue.LAND_SELL_REQUEST_RECEIVED, res);
    res = replace(getServerWord(95));
    wordsMap.put(EventValue.LAND_TRANSACION_REFUSED, res);
    res = replace(getServerWord(127));
    wordsMap.put(EventValue.LAND_BUY__REQUEST_SENT, res);
    res = replace(getServerWord(128));
    wordsMap.put(EventValue.LAND_SELL_REQUEST_SENT, res);
    res = replace(getServerWord(135));
    wordsMap.put(EventValue.PERMIT_REQUEST_REFUSED, res);
    res = replace(getServerWord(137));
    wordsMap.put(EventValue.PERMIT_REQUEST_ASK, res);
    res = replace(getServerWord(139));
    wordsMap.put(EventValue.PERMIT_REQUEST_APPROVED, res);
    res = replace(getServerWord(140));
    wordsMap.put(EventValue.PERMIT_REQUEST_SENT, res);
    res = replace(getServerWord(134));
    wordsMap.put(EventValue.PLAN_PERFORM_ASK, res);
  }
  
  /**
   * Gets a server word from the Tygron API.
   * @return The server word translation
   */
  private String getServerWord(int wordId) {
    return environment.get(ServerWords.class).get(wordId);
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
    JSONObject dataObject = HttpConnection.getInstance().getUpdate(new JsonObjectResultHandler(),
            true, getRequestObject());
    if (dataObject != null) {
      JSONObject items = dataObject.getJSONObject("items");
      if (items.has("POPUPS")) {
        updateList(items.getJSONObject("POPUPS"));
      } else {
        list = new ArrayList<PopUp>();
      }
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
    String newstring = string.substring(0, string.length() - 1);
    newstring = newstring.replaceAll("[^.?0-9]+", " ");
    return newstring.trim().split(" ");
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
        case PLAN_PERFORM_ASK:
          planPerformAsk(popUp);
          break;
        case LAND_SELL_REQUEST_SENT:
          landRequestSent();
          break;
        case LAND_BUY__REQUEST_SENT:
          landRequestSent();
          break;
        default: // Do nothing (PERMIT_REQUEST_SENT)
          break;
      }
    }
  }
  
  private void landRequestSent() {
    incr();
  }

  private void landTransactionApproved(PopUp popUp) {
    decr();
    answer(popUp, 0);
    // TODO Send info to stakeholder
  }
  
  private void landTransactionRefused(PopUp popUp) {
    decr();
    answer(popUp, 0);
    // TODO Send info to stakeholder
  }
  
  private void landBuyRequestReceived(PopUp popUp) {
    // Accept all land transaction requests
    answer(popUp, 0);
    
    // TODO Send info to stakeholder
    popUp.getCost();
    popUp.getSurface();
  }
  
  private void landSellRequestReceived(PopUp popUp) {
    // Accept all land transaction requests
    answer(popUp, 0);
    
    // TODO Send info to stakeholder
    popUp.getCost();
    popUp.getSurface();
  }
  
  private void permitRequestAsk(PopUp popUp) {
    answer(popUp, 0);
    incr();
  }
  
  private void permitRequestReceived(PopUp popUp) {
    answer(popUp, 0);
    // TODO Send info to stakeholder
  }

  private void zoneDiverged(PopUp popUp) {
    changeZones(popUp.getLinkId());
    // TODO Send info to stakeholder
  }

  private void permitRequestRefused(PopUp popUp) {
    decr();
    answer(popUp, 0);
    // TODO Send info to stakeholder
  }

  private void permitRequestApproved(PopUp popUp) {
    decr();
    answer(popUp, 0);
    // TODO Send info to stakeholder
  }
  
  private void planPerformAsk(PopUp popUp) {
    answer(popUp, 0);
    // TODO Send info to stakeholder
  }
  
  private void answer(PopUp popUp, int answer) {
    JSONArray parameters = new JSONArray();
    parameters.put(stakeholderId);
    parameters.put(popUp.getId());
    parameters.put(answer);
    if (popUp.getType() == TypeValue.INTERACTION_WITH_DATE) {
      parameters.put(0);
      HttpConnection.getInstance().execute("event/PlayerEventType/POPUP_ANSWER_WITH_DATE/",
          CallType.POST, new JsonObjectResultHandler(), true, parameters);
    } else {
      HttpConnection.getInstance().execute("event/PlayerEventType/POPUP_ANSWER/", 
          CallType.POST, new JsonObjectResultHandler(), true, parameters);
    }
  }
  
  /**
   * Change zones to include the given building's category and floors.
   * @param buildingId The building's id.
   */
  private void changeZones(int buildingId) {
    Building building = environment.get(BuildingList.class).getId(buildingId);
    Function function = environment.get(FunctionMap.class).get(building.getFunctionId());
    
    for (Zone zone : environment.get(ZoneList.class)) {
      if (PolygonUtil.polygonIntersects(zone.getPolygon(), building.getPolygon())) {
        // Add function category to zone.
        JSONArray parameters = new JSONArray();
        parameters.put(stakeholderId);
        parameters.put(zone.getId());
        parameters.put(function.getCategoryValue().toString());
        HttpConnection.getInstance().execute("event/PlayerEventType/ZONE_ADD_FUNCTION_CATEGORY/", CallType.POST,
                new JsonObjectResultHandler(), true, parameters);
        
        // Change max floors allowed in zone
        parameters = new JSONArray();
        parameters.put(stakeholderId);
        parameters.put(zone.getId());
        parameters.put(max(zone.getAllowedFloors(), building.getFloors()));
        HttpConnection.getInstance().execute("event/PlayerEventType/ZONE_SET_MAX_FLOORS/", CallType.POST,
                new JsonObjectResultHandler(), true, parameters);
      }
    }
  }
  
  public int requestsOpen() {
    return requestsOpen;
  }
  
  public List<PopUp> getList() {
    return list;
  }
  
  private void incr() {
    requestsOpen++;
  }
  
  private void decr() {
    if (requestsOpen > 0) {
      requestsOpen--;
    }
  }
  
  private int max(int i1, int i2) {
    if (i1 > i2) {
      return i1;
    }
    return i2;
  }
}
