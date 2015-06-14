package nl.tudelft.contextproject.tygron.objects;

import static org.junit.Assert.assertEquals;

import nl.tudelft.contextproject.tygron.CachedFileReader;
import nl.tudelft.contextproject.tygron.objects.StakeholderList;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
  
  @Test
  public void allowedFunctionTest() {
    List<Integer> allowedFunctions = new ArrayList<Integer>();
    allowedFunctions.add(2);
    Stakeholder stakeholder = stakeholderList.get(0);
    stakeholder.addAllowedFunctions(allowedFunctions);
    assertEquals(new Integer(2), stakeholder.getAllowedFunctions().get(0));
  }
  
  @Test
  public void typeTest() {
    assertEquals("MUNICIPALITY", stakeholderList.get(0).getType());
  }
  
  @Test
  public void indicatorTest() {
    assertEquals(new Double(0.15), stakeholderList.get(0).getIndicatorWeights().get(0));
  }
}
