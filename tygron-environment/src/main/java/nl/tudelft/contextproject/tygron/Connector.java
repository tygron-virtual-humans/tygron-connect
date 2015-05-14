package nl.tudelft.contextproject.tygron;

/**
 * The TygronConnector is the bridge between the Tygron API and JAVA.
 */
public class Connector {

  private Settings settings;
  private User user;
  private Session session;
  private SessionManager sessionManager;
  private static HttpConnection http;

  /**
   * Create a new TygronConnector.
   */
  public Connector() {
    // Load settings first
    settings = new Settings();

    // Now load our HTTP Object, it depends on settings
    http = new HttpConnection(settings);

    // Now load user, it depends on http
    user = new User(http);

    // Now load session, it depends on user
    sessionManager = new SessionManager(http);
    
    session = sessionManager.createOrJoinSession("testmap");
  }
  
  /**
   * Return the session manager.
   */
  public SessionManager getSessionManager() {
    return sessionManager;
  }
}
