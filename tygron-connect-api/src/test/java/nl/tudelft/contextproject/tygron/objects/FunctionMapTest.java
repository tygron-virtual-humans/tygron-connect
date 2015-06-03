package nl.tudelft.contextproject.tygron.objects;

import static org.junit.Assert.assertEquals;

import nl.tudelft.contextproject.tygron.CachedFileReader;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;

public class FunctionMapTest {
  FunctionMap functionMap;
  
  /**
   * Creates an economy from a cached file.
   */
  @Before
  public void setupEconomy() {
    String contents = CachedFileReader.getFileContents("/serverResponses"
        + "/testmap/lists/functions.json");
    JSONArray json = new JSONArray(contents);
    functionMap = new FunctionMap(json);
  }
  
  @Test
  public void mapSize() {
    assertEquals(248,functionMap.size());
  }
  
  @Test
  public void getId() {
    assertEquals(826,functionMap.get(826).getId());
  } 
  
  @Test
  public void getMax_floors() {
    assertEquals(1,functionMap.get(826).getMax_floors());
  }
  
  @Test
  public void getMin_floors() {
    assertEquals(1,functionMap.get(826).getMin_floors());
  }
  
  @Test
  public void getName() {
    assertEquals("Deployable Flood Barrier",functionMap.get(826).getName());
  }
  
  @Test
  public void getVersion() {
    assertEquals(228,functionMap.get(826).getVersion());
  }
}
