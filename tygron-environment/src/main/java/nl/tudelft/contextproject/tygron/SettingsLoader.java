package nl.tudelft.contextproject.tygron;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class SettingsLoader {
  Properties config;

  String username = "";
  String password = "";

  /**
   * Groups can individually decide what username they will fall back on if the
   * loading or reading of the cfg file fails.
   */
  public SettingsLoader(String path) throws Exception {
    File jarFile = new File(this.getClass().getProtectionDomain()
        .getCodeSource().getLocation().toURI().getPath());
    readConfig(new FileInputStream(new File(jarFile.getParent(), path)));
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
