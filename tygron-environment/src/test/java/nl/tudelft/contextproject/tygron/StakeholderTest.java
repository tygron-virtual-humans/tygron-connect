package nl.tudelft.contextproject.tygron;

import static org.junit.Assert.assertEquals;
import nl.tudelft.contextproject.democode.CachedFileReader;
import nl.tudelft.contextproject.tygron.objects.StakeholderList;

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

  @Test
  public void nameTest() {
    assertEquals("Municipality", stakeholderList.get(0).getName());
  }

  @Test
  public void idTest() {
    assertEquals(0, stakeholderList.get(0).getId());
  }

  @Test
  public void shortNameTest() {
    assertEquals("Municipality", stakeholderList.get(0).getShortName());
  }

  @Test
  public void descriptionTest() {
    assertEquals("Municipality description", stakeholderList.get(0).getDescription());
  }

  @Test
  public void getOwnedLandsTest() {
    assertEquals("[0, 1, 2]", stakeholderList.get(0).getOwnedLands().toString());
  }
}
