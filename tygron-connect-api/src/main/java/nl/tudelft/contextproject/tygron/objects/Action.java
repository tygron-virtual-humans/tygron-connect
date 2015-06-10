package nl.tudelft.contextproject.tygron.objects;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Action {

  private int id;
  private List<Integer> functionTypes;
  private List<String> specialOptions;
  private String name;
  private Map<Integer, Boolean> activeForStakeholder;
  
  /**
   * Creates an action object to hold information about an action from the action menu.
   * @param input The JSONObject to be parsed.
   */
  public Action(JSONObject input) {
    id = input.getInt("id");
    
    functionTypes = new ArrayList<Integer>();
    JSONArray functionList = input.getJSONArray("functionTypes");
    for (int i = 0; i < functionList.length(); i++) {
      functionTypes.add(functionList.getInt(i));
    }
    
    specialOptions = new ArrayList<String>();
    JSONArray optionList = input.getJSONArray("specialOptions");
    for (int i = 0; i < optionList.length(); i++) {
      String optionType = optionList.getJSONObject(i).getString("SpecialOption.Type");
      specialOptions.add(optionType);
    }
      
    name = input.getString("name");
    
    activeForStakeholder = new HashMap<Integer, Boolean>();
    JSONObject activeObject = input.getJSONObject("activeForStakeholder");
    JSONArray stakeholderIds = activeObject.names();
    for (int i = 0; i < stakeholderIds.length(); i++) {
      String stakeholderId = stakeholderIds.getString(i);
      boolean active = activeObject.getBoolean(stakeholderId);
      activeForStakeholder.put(stakeholderIds.getInt(i), active);
    }
  }

  public int getId() {
    return id;
  }

  public List<Integer> getFunctionTypes() {
    return functionTypes;
  }

  public List<String> getSpecialOptions() {
    return specialOptions;
  }

  public String getName() {
    return name;
  }

  public Map<Integer, Boolean> getActiveForStakeholder() {
    return activeForStakeholder;
  }
  
  
}
