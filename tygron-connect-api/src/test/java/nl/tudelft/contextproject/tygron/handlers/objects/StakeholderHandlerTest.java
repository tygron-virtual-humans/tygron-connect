package nl.tudelft.contextproject.tygron.handlers.objects;

import static org.junit.Assert.assertEquals;

import nl.tudelft.contextproject.tygron.CachedFileReader;
import nl.tudelft.contextproject.tygron.objects.StakeholderList;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;

public class StakeholderHandlerTest {
  StakeholderListResultHandler handler;
  String contents;
  StakeholderList list;
  
  /**
   * Load file.
   */
  @Before
  public void setup() {
    String file = "/serverResponses/testmap/lists/stakeholders.json";
    contents = CachedFileReader.getFileContents(file);
    list = new StakeholderList(new JSONArray(contents));
    
    handler = new StakeholderListResultHandler();
  }
  
  @Test
  public void handleTest() {
    assertEquals(list.size(), handler.handleResult(contents).size());
  }
}