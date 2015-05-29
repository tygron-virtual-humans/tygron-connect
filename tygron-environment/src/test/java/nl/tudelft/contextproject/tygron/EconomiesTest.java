package nl.tudelft.contextproject.tygron;

import static org.junit.Assert.assertEquals;
import nl.tudelft.contextproject.democode.CachedFileReader;
import nl.tudelft.contextproject.tygron.objects.EconomyList;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;

public class EconomiesTest {
  EconomyList economy;
  
  /**
   * Creates an economy from a cached file.
   */
  @Before
  public void setupEconomy() {
    String contents = CachedFileReader.getFileContents("/serverResponses"
        + "/testmap/lists/economies.json");
    JSONArray json = new JSONArray(contents);
    economy = new EconomyList(json);
  }
  
  @Test
  public void categoryTest() {
    assertEquals("SOCIAL", economy.get(0).getCategory());
  }
  
  @Test
  public void stateTest() {
    assertEquals("GOOD", economy.get(0).getState());
  }
  
  @Test
  public void getId() {
    assertEquals(0,economy.get(0).getId());
  }
  
  @Test
  public void toStringTest() {
    assertEquals("{\"id\":0,\"category\":\"SOCIAL\",\"state\":\"GOOD\"}",economy.get(0).toString());
  }
}
