package nl.tudelft.contextproject.eis;

import static org.junit.Assert.assertTrue;

import nl.tudelft.contextproject.tygron.TygronSettingsLoader;

import org.junit.Test;

public class SettingsLoaderTest {
  @Test
  public void test_username() {
    TygronSettingsLoader settingsLoader = new TygronSettingsLoader(
        "configuration.cfg.dist");

    assertTrue(settingsLoader.getUsername().equals("demousername"));
  }

  @Test
  public void test_password() {
    TygronSettingsLoader settingsLoader = new TygronSettingsLoader(
        "configuration.cfg.dist");

    assertTrue(settingsLoader.getPassword().equals("demopassword"));
  }
}
