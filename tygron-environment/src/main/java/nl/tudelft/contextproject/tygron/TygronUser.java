package nl.tudelft.contextproject.tygron;

import org.apache.http.HttpResponse;
import org.json.JSONObject;

public class TygronUser {

  private static TygronHttp http;

  private boolean ACCOUNT_ACTIVE = false;
  private String ACCOUNT_DOMAIN = "";
  private String ACCOUNT_USERNAME = "";
  private String ACCOUNT_FIRSTNAME = "";
  private String ACCOUNT_LASTNAME = "";
  private String ACCOUNT_NICKNAME = "";
  
  
  private int ACCOUNT_ID = 0;
  private long ACCOUNT_LASTLOGIN = 0;
  private String ACCOUNT_MAXOPTION = "";

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
      
      ACCOUNT_ACTIVE = (boolean) userObj.get("active");
      ACCOUNT_DOMAIN = userObj.get("domain").toString();
      ACCOUNT_USERNAME = userObj.get("userName").toString();
      ACCOUNT_FIRSTNAME = userObj.get("firstName").toString();
      ACCOUNT_LASTNAME = userObj.get("lastName").toString();
      ACCOUNT_NICKNAME = userObj.get("nickName").toString();
      
      
      ACCOUNT_ID = Integer.parseInt(userObj.get("id").toString());
      ACCOUNT_LASTLOGIN = Long.parseLong(userObj.get("lastLogin").toString());
      ACCOUNT_MAXOPTION = userObj.get("maxOption").toString();
    }
  }

public boolean isActive() {
	return ACCOUNT_ACTIVE;
}

public String getDomain() {
	return ACCOUNT_DOMAIN;
}

public String getUserName() {
	return ACCOUNT_USERNAME;
}

public String getFirstName() {
	return ACCOUNT_FIRSTNAME;
}

public String getLastName() {
	return ACCOUNT_LASTNAME;
}

public String getNickName() {
	return ACCOUNT_NICKNAME;
}

public int getID() {
	return ACCOUNT_ID;
}

public long getLastLogin() {
	return ACCOUNT_LASTLOGIN;
}

public String getMaxOption() {
	return ACCOUNT_MAXOPTION;
}

}
