package nl.tudelft.contextproject.tygron.handlers.objects;

import static org.junit.Assert.assertEquals;

import nl.tudelft.contextproject.tygron.CachedFileReader;
import nl.tudelft.contextproject.tygron.objects.EconomyList;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;

public class EconomyHandlerTest {
  EconomyListResultHandler handler;
  String contents;
  EconomyList list;
  
  /**
   * Load file.
   */
  @Before
  public void setup() {
    String file = "/serverResponses/testmap/lists/economies.json";
    contents = CachedFileReader.getFileContents(file);
    list = new EconomyList(new JSONArray(contents));
    
    handler = new EconomyListResultHandler();
  }
  
  @Test
  public void handleTest() {
    assertEquals(list.size(), handler.handleResult(contents).size());
  }
}