package nl.tudelft.contextproject.tygron;

public class TygronSettings {

  private String username = "";
  private String password = "";
  /**
   * Set the username and password.
   */
  public TygronSettings() {
    TygronSettingsLoader settingsLoader = new TygronSettingsLoader(
        "configuration.cfg");
    this.username = settingsLoader.getUsername();
    this.password = settingsLoader.getPassword();
  }

  /**
   * Return the Tygron username.
   * 
   * @return Tygron username.
   */
  public String getUserName() {
    return this.username;
  }

  /**
   * Return the Tygon password.
   * 
   * @return Tygron password.
   */
  public String getPassword() {
    return this.password;
  }
}
