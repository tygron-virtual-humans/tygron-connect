package nl.tudelft.contextproject.tygron.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import nl.tudelft.contextproject.tygron.CachedFileReader;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;

public class FunctionMapTest {
  FunctionMap functionMap;
  
  /**
   * Creates an function map from a cached file.
   */
  @Before
  public void setup() {
    String contents = CachedFileReader.getFileContents("/serverResponses"
        + "/testmap/lists/functions.json");
    JSONArray json = new JSONArray(contents);
    functionMap = new FunctionMap(json);
  }
  
  @Test
  public void mapSize() {
    assertEquals(248,functionMap.size());
  }
  
  @Test
  public void getId() {
    assertEquals(826,functionMap.get(826).getId());
  } 
  
  @Test
  public void getMax_floors() {
    assertEquals(1,functionMap.get(826).getMax_floors());
  }
  
  @Test
  public void getMin_floors() {
    assertEquals(1,functionMap.get(826).getMin_floors());
  }
  
  @Test
  public void getName() {
    assertEquals("Deployable Flood Barrier",functionMap.get(826).getName());
  }
  
  @Test
  public void getVersion() {
    assertEquals(228,functionMap.get(826).getVersion());
  }
  
  @Test
  public void getCategoryValue() {
    assertEquals("OTHER",functionMap.get(826).getCategoryValue().toString());
  }
  
  @Test
  public void isRightTypeCase0() {
    assertTrue(functionMap.get(834).isRightType(0));
  }

  @Test
  public void isRightTypeCase1() {
    assertTrue(functionMap.get(9).isRightType(1));
  }
  
  @Test
  public void isRightTypeCase2() {
    assertTrue(functionMap.get(156).isRightType(2));
  }
  
  @Test
  public void isRightTypeCase3() {
    assertTrue(functionMap.get(242).isRightType(3));
  }
  
  @Test
  public void isRightTypeCaseMinus() {
    assertFalse(functionMap.get(826).isRightType(-1));
  }
  
  @Test
  public void hasEnoughFloorsTrue() {
    assertTrue(functionMap.get(826).hasEnoughFloors(1));
  }
  
  @Test
  public void hasEnoughFloorsHigher() {
    assertFalse(functionMap.get(826).hasEnoughFloors(2));
  }
  
  @Test
  public void hasEnoughFloorsLower() {
    assertFalse(functionMap.get(826).hasEnoughFloors(0));
  }
}
