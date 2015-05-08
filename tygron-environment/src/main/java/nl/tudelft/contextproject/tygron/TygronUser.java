package nl.tudelft.contextproject.tygron;

import org.apache.http.HttpResponse;
import org.json.JSONObject;

public class TygronUser {

  private static TygronHttp http;

  private boolean ACTIVE = false;
  private String DOMAIN = "";
  private String USERNAME = "";
  private String FIRSTNAME = "";
  private String LASTNAME = "";
  private String NICKNAME = "";
  
  
  private int ID = 0;
  private long LASTLOGIN = 0;
  private String MAXOPTION = "";

  public TygronUser(TygronHttp localhttp) {
    http = localhttp;
  }

  /**
   * Load user settings from API.
   */
  public void loadSettings() {
    if (http != null) {
      // Request user info
      HttpResponse response = http.requestGet("services/myuser/");
      JSONObject userObj = http.getJsonFromResponse(response);
      
      ACTIVE = Boolean.getBoolean(userObj.getString("active"));
      DOMAIN = userObj.get("domain").toString();
      USERNAME = userObj.getString("userName");
      FIRSTNAME = userObj.getString("firstName");
      LASTNAME = userObj.getString("lastName");
      NICKNAME = userObj.getString("nickName");
      
      
      ID = Integer.parseInt(userObj.getString("id"));
      LASTLOGIN = Long.parseLong(userObj.getString("lastLogin"));
      MAXOPTION = userObj.getString("maxOption");
    }
  }

}
