package nl.tudelft.contextproject.tygron;

import static org.junit.Assert.assertEquals;

import nl.tudelft.contextproject.democode.CachedFileReader;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

public class EconomiesTest {
  Economy economy;
  
  /**
   * Creates an economy from a cached file.
   */
  @Before
  public void setupEconomy() {
    String contents = CachedFileReader.getFileContents("/serverResponses/testmap/lists/economies.json");
    JSONObject json = new JSONObject(contents);
    economy = new Economy(json);
  }
  
  @Test
  public void indicatorTest() {
    assertEquals("SOCIAL", economy.getCategory());
    assertEquals("GOOD", economy.getState());
  }
}
