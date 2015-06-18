package nl.tudelft.contextproject.tygron.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import nl.tudelft.contextproject.tygron.CachedFileReader;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;

public class ActionTest {
  ActionList actions;

  /**
   * Creates a action list from a cached file.
   */
  @Before
  public void setup() {
    String file = "/serverResponses/testmap/lists/action.json";
    String contents = CachedFileReader.getFileContents(file);
    JSONArray result = new JSONArray(contents);
    actions = new ActionList(result);
  }

  @Test
  public void idTest() {
    assertEquals(5, actions.get(0).getId());
  }
  
  @Test
  public void nameTest() {
    assertEquals("Demolish", actions.get(0).getName());
  }
  
  @Test
  public void functionTypesTest() {
    assertEquals(new Integer(642), actions.get(1).getFunctionTypes().get(0));
  }
  
  @Test
  public void specialOptionsTest() {
    assertEquals("DEMOLISH", actions.get(0).getSpecialOptions().get(0));
  }
  
  @Test
  public void activeForStakeholderTrueTest() {
    assertTrue(actions.get(0).getActiveForStakeholder().get(0));
  }
  
  @Test
  public void activeForStakeholderFalseTest() {
    assertFalse(actions.get(1).getActiveForStakeholder().get(0));
  }
}