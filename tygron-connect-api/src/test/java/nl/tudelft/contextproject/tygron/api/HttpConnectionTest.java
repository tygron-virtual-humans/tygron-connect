package nl.tudelft.contextproject.tygron.api;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import nl.tudelft.contextproject.tygron.Settings;
import nl.tudelft.contextproject.tygron.handlers.JsonObjectResultHandler;
import nl.tudelft.contextproject.tygron.handlers.StringResultHandler;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HttpConnectionTest {
  @Mock
  JSONArray parameters;

  @Mock
  Settings settings;

  @Mock
  HttpUriRequest request;

  @Mock
  Session session;

  @Mock
  HttpClient client;

  @Mock
  HttpResponse response;

  @Mock
  BasicResponseHandler handler;

  @Mock
  BasicResponseHandler objHandler;

  HttpConnection connection;

  String responseString = "{\"responseResponse\": \"response\"}";

  /**
   * Sets up the tests.
   * @throws Exception throws exception on failure
   */
  @Before
  public void setupMock() throws Exception {
    when(parameters.toString()).thenReturn("[]");

    when(settings.getUserName()).thenReturn("username");
    when(settings.getPassword()).thenReturn("password");

    when(session.getId()).thenReturn(17);

    HttpConnection.setSettings(settings);
    connection = HttpConnection.getInstance();

    when(handler.handleResponse(any(HttpResponse.class))).thenReturn(
        responseString);
    connection.handler = handler;

    when(client.execute(any(HttpUriRequest.class))).thenReturn(response);
    connection.client = client;
  }

  @Test
  public void testAuthString() {
    connection.getAuthString();
    verify(settings).getUserName();
    verify(settings).getPassword();
  }

  @Test
  public void testDefaultHeaders() {
    connection.addDefaultHeaders(request);
    verify(request, atLeast(3)).setHeader(anyString(), anyString());
  }

  @Test
  public void testApiUrlWithSession() {
    String url = connection.getApiUrl("event", session);
    verify(session).getId();
    assertEquals("https://server2.tygron.com:3022/api/slots/17/event?f=JSON",
        url);
  }

  @Test
  public void testApiUrl() {
    String url = connection.getApiUrl("event", null);
    assertEquals("https://server2.tygron.com:3022/api/event?f=JSON", url);
  }

  @Test
  public void testApiExecute() {
    String result = connection.execute(request);
    assertEquals(responseString, result);
  }

  @Test
  public void testApiExecuteParams() {
    String result = connection.execute("event", CallType.GET,
        new StringResultHandler());
    assertEquals(responseString, result);
  }

  @Test
  public void testUpdate() {
    JSONObject obj = connection.getUpdate(new JsonObjectResultHandler(),
        session, new JSONObject());
    assertEquals("response", obj.getString("responseResponse"));
  }
}
