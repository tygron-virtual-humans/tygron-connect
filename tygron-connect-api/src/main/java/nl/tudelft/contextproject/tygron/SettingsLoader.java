package nl.tudelft.contextproject.tygron;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Loads the user/password for the Tygron API from the configuration file.
 * 
 */
public class SettingsLoader {
  private static final Logger logger = LoggerFactory.getLogger(SettingsLoader.class);
  Properties config;

  String username;
  String password;

  /**
   * Groups can individually decide what username they will fall back on if the
   * loading or reading of the cfg file fails.
   * 
   * @param path the path of the config file
   * @throws Exception when fails
   */
  public SettingsLoader(String path) throws Exception {
    File jarFile = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
    if (jarFile.exists() && !jarFile.isDirectory()) {
      File configFile = new File(jarFile.getParent(), path);
      logger.info("Using config file " + configFile.getAbsolutePath());
      readConfig(new FileInputStream(configFile));
    } else {
      File configFile = new File(path);
      logger.info("Using config file " + configFile.getAbsolutePath());
      FileInputStream stream = new FileInputStream(configFile);
      readConfig(stream);
      stream.close();
    }
  }

  /**
   * Read in the file from filepath and assign values to variables.
   * 
   * @param stream the inputstream of the file
   * @throws Exception
   *           Exception for when read fails or if file is not found.
   */
  public void readConfig(InputStream stream) throws Exception {
    config = new Properties();
    config.load(stream);
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

