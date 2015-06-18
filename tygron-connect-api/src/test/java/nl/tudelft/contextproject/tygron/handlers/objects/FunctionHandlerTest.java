package nl.tudelft.contextproject.tygron.handlers.objects;

import static org.junit.Assert.assertEquals;

import nl.tudelft.contextproject.tygron.CachedFileReader;
import nl.tudelft.contextproject.tygron.objects.FunctionMap;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;

public class FunctionHandlerTest {
  FunctionMapResultHandler handler;
  String contents;
  FunctionMap map;
  
  /**
   * Load file.
   */
  @Before
  public void setup() {
    String file = "/serverResponses/testmap/lists/functions.json";
    contents = CachedFileReader.getFileContents(file);
    map = new FunctionMap(new JSONArray(contents));
    
    handler = new FunctionMapResultHandler();
  }
  
  @Test
  public void handleTest() {
    assertEquals(map.size(), handler.handleResult(contents).size());
  }
}
