package nl.tudelft.contextproject.tygron;

import org.json.JSONObject;

public class TygronUser {

  private static TygronHttp http;

  private boolean ACCOUNT_ACTIVE = false;
  private String ACCOUNT_DOMAIN = "";
  private String ACCOUNT_USERNAME = "";

  public TygronUser(TygronHttp localhttp) {
    http = localhttp;
  }

  /**
   * Load user settings from API.
   */
  public void loadSettings() {
    if (http != null) {
      // Todo: insert call to fetch userdata
      JSONObject userObj = new JSONObject();
    }
  }

}
