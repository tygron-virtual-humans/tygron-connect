package nl.tudelft.contextproject.tygron;

import org.json.JSONObject;

public class User {

  private boolean active;
  private String domain;
  private String userName;
  private String firstName;
  private String lastName;
  private String nickName;

  private int id;
  private long lastLogin;
  private String maxOption;

  /**
   * User Object.
   * @param userObj JSONData containing the user object
   */
  public User(JSONObject userObj) {
    active = userObj.getBoolean("active");
    domain = userObj.getString("domain");
    userName = userObj.getString("userName");
    firstName = userObj.getString("firstName");
    lastName = userObj.getString("lastName");
    nickName = userObj.getString("nickName");

    id = userObj.getInt("id");
    lastLogin = userObj.getLong("lastLogin");
    maxOption = userObj.getString("maxOption");
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
