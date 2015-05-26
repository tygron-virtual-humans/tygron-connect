package nl.tudelft.contextproject.tygron;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Settings {
  private static final Logger logger = LoggerFactory.getLogger(Settings.class);
  private String username;
  private String password;

  /**
   * Set the username and password.
   */
  public Settings() {
    try {
      SettingsLoader settingsLoader = getSettingsLoader("configuration.cfg");
      this.username = settingsLoader.getUsername();
      this.password = settingsLoader.getPassword();
    } catch (Exception e) {
      logger.info("Using fallback username/password");
      username = "fallbackusername";
      password = "fallbackpassword";
    }
  }
  
  protected SettingsLoader getSettingsLoader(String cfg) throws Exception {
    return new SettingsLoader(cfg);
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

