package nl.tudelft.contextproject.tygron;

import static org.junit.Assert.assertEquals;

import nl.tudelft.contextproject.democode.CachedFileReader;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;

public class IndicatorTest {
  IndicatorList indicatorList;

  /**
   * Init test objects.
   */
  @Before
  public void setupObject() {
    String file = "/serverResponses/testmap/lists/indicators.json";
    String contents = CachedFileReader.getFileContents(file);
    JSONArray json = new JSONArray(contents);
    indicatorList = new IndicatorList(json);
  }

  @Test
  public void indicatorTest() {
    assertEquals("Budget Municipality", indicatorList.get(0).getName());
  }
}
