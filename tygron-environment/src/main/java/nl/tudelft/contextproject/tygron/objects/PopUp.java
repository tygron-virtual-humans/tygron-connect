package nl.tudelft.contextproject.tygron.objects;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PopUp {
  
  private int id;
  private int version;
  private List<Integer> visibleForActorIds;
  private String title;
  private String text;
  private int linkId;
  private String polygons;
  private String linkType;
  private String type;
  private String point;
  
  // When selling land, 0 is YES, 1 is NO, 2 is GIVE FOR FREE
  // When requesting confirmation, 0 is OK
  // When requesting a building permit, 0 is YES, 1 is NO
  // When answering a building permit request, 0 is YES, 1 is NO
  
  /**
   * 
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
    
    title = popUp.getString("title");
    text = popUp.getString("text");
    linkId = popUp.getInt("linkID");
    polygons = popUp.optString("polygons");
    linkType = popUp.getString("linkType");
    type = popUp.getString("type");
    point = popUp.getString("point");
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

  public String getType() {
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
