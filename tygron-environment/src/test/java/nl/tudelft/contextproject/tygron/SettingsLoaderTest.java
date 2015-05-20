package nl.tudelft.contextproject.tygron;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import nl.tudelft.contextproject.tygron.SettingsLoader;

import org.junit.Before;
import org.junit.Test;

public class SettingsLoaderTest {
  SettingsLoader settingsLoader;

  /**
   * Initialize test.
   */
  @Before
  public void start() {
    try {
      settingsLoader = new SettingsLoader(
          "../src/main/resources/testconfiguration.cfg");
    } catch (Exception e) {
      e.printStackTrace();
      fail("File not found or could not be read");
    }
  }

  @Test
  public void test_username() {
    assertTrue(settingsLoader.getUsername().equals("demousername"));
  }

  @Test
  public void test_password() {
    assertTrue(settingsLoader.getPassword().equals("demopassword"));
  }
}
