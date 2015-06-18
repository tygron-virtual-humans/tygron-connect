package nl.tudelft.contextproject.tygron.handlers.objects;

import static org.junit.Assert.assertEquals;

import nl.tudelft.contextproject.tygron.CachedFileReader;
import nl.tudelft.contextproject.tygron.objects.indicators.IndicatorList;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;

public class IndicatorHandlerTest {
  IndicatorListResultHandler handler;
  String contents;
  IndicatorList list;
  
  /**
   * Load file.
   */
  @Before
  public void setup() {
    String file = "/serverResponses/testmap/lists/indicators.json";
    contents = CachedFileReader.getFileContents(file);
    list = new IndicatorList(new JSONArray(contents));
    
    handler = new IndicatorListResultHandler();
  }
  
  @Test
  public void handleTest() {
    assertEquals(list.size(), handler.handleResult(contents).size());
  }
}
