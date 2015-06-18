package nl.tudelft.contextproject.tygron.objects;

import nl.tudelft.contextproject.tygron.objects.PopUpHandler.EventValue;
import nl.tudelft.contextproject.util.PolygonUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A PopUp is a Tygron Object that can be answered. Popups are user-actions
 * ingame that can (usually) be accepted or rejected. They approve or disapprove
 * the game actions from another users perspective.
 *  */
public class PopUp {

  public enum TypeValue {
    INTERACTION, INFORMATION, INTERACTION_WITH_DATE
  }

  private int id;
  private int version;
  private List<Integer> visibleForActorIds;
  private String title;
  private String text;
  private int linkId;
  private String linkType;
  private String point;

  private TypeValue type;
  private EventValue event;
  private int cost; // can be null
  private int surface; // can be null

  /**
   * Creates a popup object.
   * @param object The JSONObject to be read.
   */
  public PopUp(JSONObject object) {
    JSONObject popUp = object.getJSONObject("PopupData");

    id = popUp.getInt("id");
    version = popUp.getInt("version");

    visibleForActorIds = new ArrayList<>();
    JSONArray array = popUp.getJSONArray("visibleForActorIDs");
    for (int i = 0; i < array.length(); i++) {
      visibleForActorIds.add(array.getInt(i));
    }

    String tempTitle = popUp.getString("title");
    title = "NO TITLE SET".equals(tempTitle) ? null : tempTitle;
    text = popUp.getString("text");
    linkId = popUp.getInt("linkID");
    
    String polygonString = popUp.optString("polygons");
    if (!polygonString.isEmpty()) {
      PolygonUtil.createPolygonFromWkt(popUp.optString("polygons"));
    }
    linkType = popUp.getString("linkType");
    type = TypeValue.valueOf(popUp.getString("type"));
    point = popUp.getString("point");
  }

  public void setEvent(EventValue event) {
    this.event = event;
  }

  public void setCost(int cost) {
    this.cost = cost;
  }

  public void setSurface(int surface) {
    this.surface = surface;
  }

  public int getId() {
    return id;
  }

  public int getVersion() {
    return version;
  }

  public List<Integer> getVisibleForActorIds() {
    return visibleForActorIds;
  }

  public String getTitle() {
    return title;
  }

  public String getText() {
    return text;
  }

  public int getLinkId() {
    return linkId;
  }

  public String getLinkType() {
    return linkType;
  }

  public TypeValue getType() {
    return type;
  }

  public String getPoint() {
    return point;
  }

  public EventValue getEvent() {
    return event;
  }

  public int getCost() {
    return cost;
  }

  public int getSurface() {
    return surface;
  }
}

