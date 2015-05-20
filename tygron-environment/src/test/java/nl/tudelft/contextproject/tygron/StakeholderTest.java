package nl.tudelft.contextproject.tygron;

import static org.junit.Assert.assertEquals;

import nl.tudelft.contextproject.democode.CachedFileReader;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;

public class StakeholderTest {
  StakeholderList stakeholderList;
  
  @Test
  public void stakeholderTest() {
    assertEquals("Municipality", stakeholderList.get(0).getName());
  }
  
  /**
   * Init test objects.
   */
  @Before
  public void setupObject() {
    String contents = CachedFileReader.getFileContents("/serverResponses/testmap/lists/stakeholders.json");
    JSONArray json = new JSONArray(contents);
    stakeholderList = new StakeholderList(json);
  }
}
