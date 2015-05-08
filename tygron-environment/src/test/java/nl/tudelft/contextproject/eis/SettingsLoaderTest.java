package nl.tudelft.contextproject.eis;

import static org.junit.Assert.assertTrue;

import nl.tudelft.contextproject.tygron.TygronSettingsLoader;

import org.junit.Test;

public class SettingsLoaderTest {
  @Test
  public void test_username() {
    TygronSettingsLoader settingsLoader;
    try {
      settingsLoader = new TygronSettingsLoader("configuration.cfg.dist");
      assertTrue(settingsLoader.getUsername().equals("demousername"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void test_password() {
    TygronSettingsLoader settingsLoader;
    try {
      settingsLoader = new TygronSettingsLoader("configuration.cfg.dist");
      assertTrue(settingsLoader.getPassword().equals("demopassword"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
