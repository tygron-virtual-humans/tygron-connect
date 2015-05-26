package nl.tudelft.contextproject.tygron;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(value = MockitoJUnitRunner.class)
public class SettingsTest {
  Settings settings;

  @Mock
  private SettingsLoader settingsloader;

  /**
   * Sets up the settings test.
   * @throws Exception Can not mock.
   */
  @Before
  public void setupSettingsTest() throws Exception {
    settings = spy(new Settings());
    doReturn(settingsloader).when(settings)
        .getSettingsLoader(any(String.class));
  }

  @Test
  public void getUserName() {
    assertEquals(settings.getUserName(), "fallbackusername");
  }

  @Test
  public void getPassword() {
    assertEquals(settings.getPassword(), "fallbackpassword");
  }
}
