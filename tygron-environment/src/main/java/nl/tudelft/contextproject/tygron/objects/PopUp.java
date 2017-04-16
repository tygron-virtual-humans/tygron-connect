package nl.tudelft.contextproject.tygron.objects;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PopUp {
  
  private enum TypeValue {
    INTERACTION, INFORMATION
  }
  
  private enum EventValue {
    LAND_SELL_REQUEST, LAND_SELL_APPROVED, LAND_SELL_REFUSED, LAND_BUY_REQUEST, LAND_BUY_APPROVED,
    LAND_BUY_REFUSED, BUILD_PERMISSION_REQUEST, BUILD_PERMISSION_APPROVED, BUILD_PERMISSION_REFUSED
  }
  
  private int id;
  private int version;
  private List<Integer> visibleForActorIds;
  private String title;
  private String text;
  private int linkId;
  private String polygons;
  private String linkType;
  private String point;
  
  private TypeValue type;
  private EventValue event;
  private int cost; // can be null
  private int surface; // can be null
  
  // When selling land, 0 is YES, 1 is NO, 2 is GIVE FOR FREE
  // When requesting confirmation, 0 is OK
  // When requesting a building permit, 0 is YES, 1 is NO
  // When answering a building permit request, 0 is YES, 1 is NO
  
  /**
   * Creates a popup object.
   * @param object The JSONObject to be read.
   */
  public PopUp(JSONObject object) {
    JSONObject popUp = object.getJSONObject("PopupData");
    
    id = popUp.getInt("id");
    version = popUp.getInt("version");
    
    visibleForActorIds = new ArrayList<Integer>();
    JSONArray array = popUp.getJSONArray("visibleForActorIDs");
    for (int i = 0; i < array.length(); i++) {
      visibleForActorIds.add(array.getInt(i));
    }
    
    String tempTitle = popUp.getString("title");
    title = tempTitle.equals("NO TITLE SET") ? null : tempTitle;
    text = popUp.getString("text");
    parseText();
    linkId = popUp.getInt("linkID");
    polygons = popUp.optString("polygons");
    linkType = popUp.getString("linkType");
    type = TypeValue.valueOf(popUp.getString("type"));
    point = popUp.getString("point");
  }
  
  private void parseText() {
    
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

  public String getPolygons() {
    return polygons;
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
  
  @Override
  public String toString() {
    JSONObject str = new JSONObject();
    str.put("id", this.id);
    str.put("title", this.title);
    str.put("text", this.text);
    return str.toString();
  }
}
