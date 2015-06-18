package nl.tudelft.contextproject.tygron.handlers.objects;

import static org.junit.Assert.assertEquals;

import nl.tudelft.contextproject.tygron.CachedFileReader;
import nl.tudelft.contextproject.tygron.objects.ServerWords;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;

public class ServerWordsHandlerTest {
  ServerWordsResultHandler handler;
  String contents;
  ServerWords list;
  
  /**
   * Load file.
   */
  @Before
  public void setup() {
    String file = "/serverResponses/testmap/lists/serverWords.json";
    contents = CachedFileReader.getFileContents(file);
    list = new ServerWords(new JSONArray(contents));
    
    handler = new ServerWordsResultHandler();
  }
  
  @Test
  public void handleTest() {
    assertEquals(list.size(), handler.handleResult(contents).size());
  }
}