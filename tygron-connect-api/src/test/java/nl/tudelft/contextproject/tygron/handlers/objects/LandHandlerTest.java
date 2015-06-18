package nl.tudelft.contextproject.tygron.handlers.objects;

import static org.junit.Assert.assertEquals;

import nl.tudelft.contextproject.tygron.CachedFileReader;
import nl.tudelft.contextproject.tygron.objects.LandMap;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;

public class LandHandlerTest {
  LandMapResultHandler handler;
  String contents;
  LandMap map;
  
  /**
   * Load file.
   */
  @Before
  public void setup() {
    String file = "/serverResponses/testmap/lists/land.json";
    contents = CachedFileReader.getFileContents(file);
    map = new LandMap(new JSONArray(contents));
    
    handler = new LandMapResultHandler();
  }
  
  @Test
  public void handleTest() {
    assertEquals(map.size(), handler.handleResult(contents).size());
  }
}
