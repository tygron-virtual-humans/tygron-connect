package nl.tudelft.contextproject.tygron;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Connection {
  public abstract String callGetEvent(String eventName);
  
  public JSONObject callGetEventObject(String eventName) {
    return new JSONObject(callGetEvent(eventName));
  }
  
  public JSONArray callGetEventArray(String eventName) {
    return new JSONArray(callGetEvent(eventName));
  }
  
  public abstract String callPostEvent(String eventName, JSONArray parameters);
  
  public JSONObject callPostEventObject(String eventName, JSONArray parameters) {
    return new JSONObject(callPostEvent(eventName, parameters));
  }
  
  public JSONArray callPostEventArray(String eventName, JSONArray parameters) {
    return new JSONArray(callPostEvent(eventName, parameters));
  }
}
