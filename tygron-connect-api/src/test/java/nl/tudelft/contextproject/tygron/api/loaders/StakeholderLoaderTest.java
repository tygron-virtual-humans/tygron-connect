package nl.tudelft.contextproject.tygron.api.loaders;

import static org.junit.Assert.assertEquals;

import nl.tudelft.contextproject.tygron.CachedFileReader;
import nl.tudelft.contextproject.tygron.api.CallType;
import nl.tudelft.contextproject.tygron.api.HttpConnection;
import nl.tudelft.contextproject.tygron.handlers.objects.ActionListResultHandler;
import nl.tudelft.contextproject.tygron.handlers.objects.StakeholderListResultHandler;
import nl.tudelft.contextproject.tygron.objects.ActionList;
import nl.tudelft.contextproject.tygron.objects.StakeholderList;

import org.json.JSONArray;
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
public class StakeholderLoaderTest {
  StakeholderListLoader loader;
  StakeholderList stakeholderList;
  
  @Mock
  HttpConnection connection;
  
  /**
   * Mock HttpConnection.
   */
  @Before
  public void setup() {
    PowerMockito.mockStatic(HttpConnection.class);
    BDDMockito.given(HttpConnection.getInstance()).willReturn(connection);
    
    String file = "/serverResponses/testmap/lists/stakeholders.json";
    String contents = CachedFileReader.getFileContents(file);
    JSONArray result = new JSONArray(contents);
    stakeholderList = new StakeholderList(result);
    Mockito.when(connection.execute(Mockito.eq("lists/stakeholders/"), Mockito.eq(CallType.GET), 
        Mockito.any(StakeholderListResultHandler.class), Mockito.eq(true))).thenReturn(stakeholderList);
    
    String file2 = "/serverResponses/testmap/lists/action.json";
    String contents2 = CachedFileReader.getFileContents(file2);
    JSONArray result2 = new JSONArray(contents2);
    ActionList actionList = new ActionList(result2);
    Mockito.when(connection.execute(Mockito.eq("lists/actionmenus"), Mockito.eq(CallType.GET), 
        Mockito.any(ActionListResultHandler.class), Mockito.eq(true))).thenReturn(actionList);
    
    loader = new StakeholderListLoader();
  }
  
  @Test
  public void loadTest() {
    assertEquals(3, loader.load().size());
  }
  
  @Test
  public void getTest() {
    assertEquals(3, loader.get().size());
  }
  
  @Test
  public void loadNoActionsTest() {
    ActionList actionList = new ActionList(new JSONArray("[]"));
    Mockito.when(connection.execute(Mockito.eq("lists/actionmenus"), Mockito.eq(CallType.GET), 
        Mockito.any(ActionListResultHandler.class), Mockito.eq(true))).thenReturn(actionList);
    assertEquals(3, loader.load().size());
  }
  
  @Test
  public void getDataClassTest() {
    assertEquals(StakeholderList.class, loader.getDataClass());
  }
  
  @Test
  public void getRefreshInterval() {
    assertEquals("NORMAL", loader.getRefreshInterval().toString());
  }
}