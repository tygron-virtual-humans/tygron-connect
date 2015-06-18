package nl.tudelft.contextproject.tygron.handlers.objects;

import static org.junit.Assert.assertEquals;

import nl.tudelft.contextproject.tygron.CachedFileReader;
import nl.tudelft.contextproject.tygron.objects.BuildingList;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;

public class BuildingHandlerTest {
  BuildingListResultHandler handler;
  String contents;
  BuildingList list;
  
  /**
   * Load file.
   */
  @Before
  public void setup() {
    String file = "/serverResponses/testmap/lists/building.json";
    contents = CachedFileReader.getFileContents(file);
    list = new BuildingList(new JSONArray(contents));
    
    handler = new BuildingListResultHandler();
  }
  
  @Test
  public void handleTest() {
    assertEquals(list.size(), handler.handleResult(contents).size());
  }
}