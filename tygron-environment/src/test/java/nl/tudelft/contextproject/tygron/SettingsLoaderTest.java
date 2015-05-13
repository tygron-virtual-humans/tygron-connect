package nl.tudelft.contextproject.tygron;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import nl.tudelft.contextproject.tygron.SettingsLoader;

import org.junit.Test;

public class SettingsLoaderTest {
  @Test
  public void test_username() {
    SettingsLoader settingsLoader;
    try {
      settingsLoader = new SettingsLoader("src/main/resources/testconfiguration.cfg");
      assertTrue(settingsLoader.getUsername().equals("demousername"));
    } catch (Exception e) {
      e.printStackTrace();
      fail("File not found or could not be read");
    }
  }

  @Test
  public void test_password() {
    SettingsLoader settingsLoader;
    try {
      settingsLoader = new SettingsLoader("src/main/resources/testconfiguration.cfg");
      assertTrue(settingsLoader.getPassword().equals("demopassword"));
    } catch (Exception e) {
      e.printStackTrace();
      fail("File not found or could not be read");
    }
  }
}
