package nl.tudelft.contextproject.tygron.handlers.objects;

import static org.junit.Assert.assertEquals;

import nl.tudelft.contextproject.tygron.CachedFileReader;
import nl.tudelft.contextproject.tygron.objects.ActionList;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;

public class ActionHandlerTest {
  ActionListResultHandler handler;
  String contents;
  ActionList list;
  
  /**
   * Load file.
   */
  @Before
  public void setup() {
    String file = "/serverResponses/testmap/lists/action.json";
    contents = CachedFileReader.getFileContents(file);
    list = new ActionList(new JSONArray(contents));
    
    handler = new ActionListResultHandler();
  }
  
  @Test
  public void handleTest() {
    assertEquals(list.size(), handler.handleResult(contents).size());
  }
}
