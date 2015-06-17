package nl.tudelft.contextproject.tygron.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import nl.tudelft.contextproject.tygron.CachedFileReader;
import nl.tudelft.contextproject.tygron.api.CallType;
import nl.tudelft.contextproject.tygron.api.Environment;
import nl.tudelft.contextproject.tygron.api.HttpConnection;
import nl.tudelft.contextproject.tygron.handlers.JsonObjectResultHandler;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(HttpConnection.class)
public class PopUpTest {
  PopUpHandler popUpHandler;
  ServerWords serverWords;
  BuildingList buildingList;
  FunctionMap functionMap;
  ZoneList zoneList;
  
  @Mock
  HttpConnection connection;
  
  @Mock
  Environment environment;
  
  /**
   * Loads all server words, pop ups, and sets all mock classes.
   */
  @Before
  public void setup() {
    PowerMockito.mockStatic(HttpConnection.class);
    BDDMockito.given(HttpConnection.getInstance()).willReturn(connection);
    
    // Load pop ups
    String popUpFile = "/serverResponses/testmap/lists/popUp.json";
    String popUpContents = CachedFileReader.getFileContents(popUpFile);
    JSONObject popUpResult = new JSONObject(popUpContents);
    Mockito.when(connection.getUpdate(Mockito.any(JsonObjectResultHandler.class),
        Mockito.eq(true), Mockito.any(JSONObject.class))).thenReturn(popUpResult);
    
    // Load server words
    String serverWordsFile = "/serverResponses/testmap/lists/serverWords.json";
    String serverWordsContents = CachedFileReader.getFileContents(serverWordsFile);
    JSONArray serverWordsResult = new JSONArray(serverWordsContents);
    serverWords = new ServerWords(serverWordsResult);
    Mockito.when(environment.get(ServerWords.class)).thenReturn(serverWords);
    
    // Load buildings
    String buildingsFile = "/serverResponses/testmap/lists/building.json";
    String buildingsContents = CachedFileReader.getFileContents(buildingsFile);
    JSONArray buildingsResult = new JSONArray(buildingsContents);
    buildingList = new BuildingList(buildingsResult);
    Mockito.when(environment.get(BuildingList.class)).thenReturn(buildingList);
    
    // Load functions
    String functionsFile = "/serverResponses/testmap/lists/functions.json";
    String functionsContents = CachedFileReader.getFileContents(functionsFile);
    JSONArray functionsResult = new JSONArray(functionsContents);
    functionMap = new FunctionMap(functionsResult);
    Mockito.when(environment.get(FunctionMap.class)).thenReturn(functionMap);
    
    // Load zones
    String zonesFile = "/serverResponses/testmap/lists/zone.json";
    String zonesContents = CachedFileReader.getFileContents(zonesFile);
    JSONArray zonesResult = new JSONArray(zonesContents);
    zoneList = new ZoneList(zonesResult);
    Mockito.when(environment.get(ZoneList.class)).thenReturn(zoneList);
    
    Mockito.when(connection.execute(Mockito.anyString(), Mockito.eq(CallType.POST), 
        Mockito.any(JsonObjectResultHandler.class), Mockito.eq(true),  Mockito.any(JSONArray.class))).thenReturn(null);
    
    popUpHandler = new PopUpHandler(environment, 1);
  }
  
  @Test
  public void requestsOpenTest() {
    assertEquals(0, popUpHandler.requestsOpen());
    popUpHandler.loadPopUps();
    assertEquals(1, popUpHandler.requestsOpen());
  }
  
  @Test
  public void getListTest() {
    assertTrue(popUpHandler.getList().isEmpty());
    popUpHandler.loadPopUps();
    assertEquals(13, popUpHandler.getList().size());
  }
  
  @Test
  public void getListNull() {
    Mockito.when(connection.getUpdate(Mockito.any(JsonObjectResultHandler.class),
        Mockito.eq(true), Mockito.any(JSONObject.class))).thenReturn(null);
    
    popUpHandler.loadPopUps();
    assertTrue(popUpHandler.getList().isEmpty());
  }
  
  @Test
  public void getListEmpty() {
    JSONObject popUpResult = new JSONObject("{\"items\":{}}");
    Mockito.when(connection.getUpdate(Mockito.any(JsonObjectResultHandler.class),
        Mockito.eq(true), Mockito.any(JSONObject.class))).thenReturn(popUpResult);
    
    popUpHandler.loadPopUps();
    assertTrue(popUpHandler.getList().isEmpty());
  }
  
  @Test
  public void getLinkType() {
    popUpHandler.loadPopUps();
    PopUp popUp = popUpHandler.getList().get(0);
    assertEquals("SPECIAL_OPTIONS", popUp.getLinkType());
  }
  
  @Test
  public void getPointTest() {
    popUpHandler.loadPopUps();
    PopUp popUp = popUpHandler.getList().get(0);
    assertEquals("POINT (81.742314 169.256918)", popUp.getPoint());
  }
  
  @Test
  public void getTitleTest() {
    popUpHandler.loadPopUps();
    PopUp popUp = popUpHandler.getList().get(1);
    assertEquals("Housing Corporation wants to purchase land", popUp.getTitle());
  }
  
}
