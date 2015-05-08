package nl.tudelft.contextproject.tygron;

import org.json.JSONObject;

import java.util.Map;

public interface TygronConnection {
  public JSONObject callGetEvent(String eventName);
  
  public JSONObject callPostEvent(String eventName, Map<String, String> parameters);
}
