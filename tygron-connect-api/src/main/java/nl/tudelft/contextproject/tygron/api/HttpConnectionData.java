package nl.tudelft.contextproject.tygron.api;

/**
 * Encapsulates Session data.
 */
public class HttpConnectionData {
  private String clientToken;
  private String serverToken;
  private int sessionId;
  
  public String getClientToken() {
    return clientToken;
  }
  
  public void setClientToken(String clientToken) {
    this.clientToken = clientToken;
  }
  
  public String getServerToken() {
    return serverToken;
  }
  
  public void setServerToken(String serverToken) {
    this.serverToken = serverToken;
  }
  
  public int getSessionId() {
    return sessionId;
  }
  
  public void setSessionId(int sessionId) {
    this.sessionId = sessionId;
  }
  
}
