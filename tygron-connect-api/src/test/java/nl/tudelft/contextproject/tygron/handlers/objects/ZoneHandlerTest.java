package nl.tudelft.contextproject.tygron.handlers.objects;

import static org.junit.Assert.assertEquals;

import nl.tudelft.contextproject.tygron.CachedFileReader;
import nl.tudelft.contextproject.tygron.objects.ZoneList;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;

public class ZoneHandlerTest {
  ZoneListResultHandler handler;
  String contents;
  ZoneList list;
  
  /**
   * Load file.
   */
  @Before
  public void setup() {
    String file = "/serverResponses/testmap/lists/zone.json";
    contents = CachedFileReader.getFileContents(file);
    list = new ZoneList(new JSONArray(contents));
    
    handler = new ZoneListResultHandler();
  }
  
  @Test
  public void handleTest() {
    assertEquals(list.size(), handler.handleResult(contents).size());
  }
}