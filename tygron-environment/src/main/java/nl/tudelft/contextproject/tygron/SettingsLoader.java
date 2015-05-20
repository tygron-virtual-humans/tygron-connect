package nl.tudelft.contextproject.tygron;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SettingsLoader {
  final private static Logger logger = LoggerFactory.getLogger(HttpConnection.class);
  Properties config;

  String username = "";
  String password = "";

  /**
   * Groups can individually decide what username they will fall back on if the
   * loading or reading of the cfg file fails.
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
      readConfig(new FileInputStream(configFile));
    }
  }

  /**
   * Read in the file from filepath and assign values to variables.
   * 
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
