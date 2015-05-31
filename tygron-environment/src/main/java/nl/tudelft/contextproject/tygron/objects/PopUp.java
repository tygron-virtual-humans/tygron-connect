package nl.tudelft.contextproject.tygron.objects;

import com.esri.core.geometry.Polygon;

import nl.tudelft.contextproject.tygron.objects.PopUpHandler.EventValue;
import nl.tudelft.contextproject.util.PolygonUtil;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class PopUp {
  private static final Logger logger = LoggerFactory.getLogger(PopUp.class);
  
  private enum TypeValue {
    INTERACTION, INFORMATION
  }

  private int id;
  private int version;
  private List<Integer> visibleForActorIds;
  private String title;
  private String text;
  private int linkId;
  private Polygon polygon;
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

    visibleForActorIds = new ArrayList<Integer>();
    JSONArray array = popUp.getJSONArray("visibleForActorIDs");
    for (int i = 0; i < array.length(); i++) {
      visibleForActorIds.add(array.getInt(i));
    }

    String tempTitle = popUp.getString("title");
    title = "NO TITLE SET".equals(tempTitle) ? null : tempTitle;
    text = popUp.getString("text");
    linkId = popUp.getInt("linkID");
    try {
      polygon = PolygonUtil.createPolygonFromWkt(popUp.optString("polygons"));
    } catch (Exception e) {
      logger.info("Error parsing Building with string " + popUp.toString());
      throw new RuntimeException(e);
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

  public Polygon getPolygon() {
    return polygon;
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

