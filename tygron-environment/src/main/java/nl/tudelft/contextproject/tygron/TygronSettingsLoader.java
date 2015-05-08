package nl.tudelft.contextproject.tygron;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class TygronSettingsLoader {
  Properties config;
  InputStream input = null;

  String username = "";
  String password = "";

  /**
   * Groups can individually decide what username they will fall back on if the
   * loading or reading of the cfg file fails.
   */
  public TygronSettingsLoader(String path) throws Exception {
    readConfig(path);
  }

  /**
   * Read in the file from filepath and assign values to variables.
   * 
   * @param path
   *          File Path to configuration file.
   * @throws Exception
   *           Exception for when read fails or if file is not found.
   */
  private void readConfig(String path) throws Exception {
    input = new FileInputStream(path);
    config = new Properties();

    config.load(input);
    username = config.getProperty("username");
    password = config.getProperty("password");
  }

  /**
   * Return Tygron Username.
   * 
   * @return Tygron Username.
   */
  public String getUsername() {
    return username;
  }

  /**
   * Return Tygron Password.
   * 
   * @return Tygron Password.
   */
  public String getPassword() {
    return password;
  }
}
