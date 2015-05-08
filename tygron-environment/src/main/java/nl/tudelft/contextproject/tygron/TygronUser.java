package nl.tudelft.contextproject.tygron;

import org.json.JSONObject;

public class TygronUser {

  private static TygronConnection http;

  private boolean active = false;
  private String domain = "";
  private String userName = "";
  private String firstName = "";
  private String lastName = "";
  private String nickName = "";
  
  
  private int id = 0;
  private long lastLogin = 0;
  private String maxOption = "";

  public TygronUser(TygronConnection localhttp) {
    http = localhttp;
  }

  /**
   * Load user settings from API.
   */
  public void loadSettings() {
    if (http != null) {
      // Request user info
      JSONObject userObj = http.callGetEvent("services/myuser/");
      
      active = userObj.getBoolean("active");
      System.out.println(active);
      domain = userObj.getString("domain");
      System.out.println(domain);
      userName = userObj.getString("userName");
      System.out.println(userName);
      firstName = userObj.getString("firstName");
      System.out.println(firstName);
      lastName = userObj.getString("lastName");
      nickName = userObj.getString("nickName");
      
      
      id = userObj.getInt("id");
      lastLogin = userObj.getLong("lastLogin");
      maxOption = userObj.getString("maxOption");
    }
  }

public boolean isActive() {
	return active;
}

public String getDomain() {
	return domain;
}

public String getUserName() {
	return userName;
}

public String getFirstName() {
	return firstName;
}

public String getLastName() {
	return lastName;
}

public String getNickName() {
	return nickName;
}

public int getId() {
	return id;
}

public long getLastLogin() {
	return lastLogin;
}

public String getMaxOption() {
	return maxOption;
}

}
