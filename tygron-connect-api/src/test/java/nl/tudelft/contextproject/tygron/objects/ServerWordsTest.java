package nl.tudelft.contextproject.tygron.objects;

import static org.junit.Assert.assertEquals;

import nl.tudelft.contextproject.tygron.CachedFileReader;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;

public class ServerWordsTest {
  
  ServerWords serverWords;
  
  /**
   * Loads serverwords from a file.
   */
  @Before
  public void setup() {
    String file = "/serverResponses/testmap/lists/serverWords.json";
    String contents = CachedFileReader.getFileContents(file);
    JSONArray result = new JSONArray(contents);
    serverWords = new ServerWords(result);
  }
  
  @Test
  public void firstWordTest() {
    assertEquals("Budget", serverWords.get(0));
  }
  
  @Test
  public void lastWordTest() {
    assertEquals("Usage", serverWords.get(193));
  }
}
