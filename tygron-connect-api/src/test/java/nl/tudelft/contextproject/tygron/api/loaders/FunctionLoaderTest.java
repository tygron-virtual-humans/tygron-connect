package nl.tudelft.contextproject.tygron.api.loaders;

import static org.junit.Assert.assertEquals;

import nl.tudelft.contextproject.tygron.CachedFileReader;
import nl.tudelft.contextproject.tygron.api.CallType;
import nl.tudelft.contextproject.tygron.api.HttpConnection;
import nl.tudelft.contextproject.tygron.handlers.objects.FunctionMapResultHandler;
import nl.tudelft.contextproject.tygron.objects.FunctionMap;

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
public class FunctionLoaderTest {
  FunctionMapLoader loader;
  
  @Mock
  HttpConnection connection;
  
  /**
   * Mock HttpConnection.
   */
  @Before
  public void setup() {
    PowerMockito.mockStatic(HttpConnection.class);
    BDDMockito.given(HttpConnection.getInstance()).willReturn(connection);
    
    String file = "/serverResponses/testmap/lists/functions.json";
    String contents = CachedFileReader.getFileContents(file);
    JSONArray result = new JSONArray(contents);
    Mockito.when(connection.execute(Mockito.anyString(), Mockito.eq(CallType.GET), 
        Mockito.any(FunctionMapResultHandler.class), Mockito.eq(true))).thenReturn(new FunctionMap(result));
    
    loader = new FunctionMapLoader();
  }
  
  @Test
  public void loadTest() {
    assertEquals(248, loader.load().size());
  }
  
  @Test
  public void getTest() {
    assertEquals(248, loader.get().size());
  }
  
  @Test
  public void getDataClassTest() {
    assertEquals(FunctionMap.class, loader.getDataClass());
  }
  
  @Test
  public void getRefreshInterval() {
    assertEquals("NEVER", loader.getRefreshInterval().toString());
  }
}
