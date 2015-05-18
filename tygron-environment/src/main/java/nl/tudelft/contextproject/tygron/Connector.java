package nl.tudelft.contextproject.tygron;

/**
 * The TygronConnector is the bridge between the Tygron API and JAVA.
 */
public class Connector {

  private Settings settings;
  private User user;
  private Session session;
  private SessionManager sessionManager;
  private static HttpConnection connection;

  /**
   * Create a new TygronConnector.
   */
  public Connector() {
    // Load settings first
    settings = new Settings();

    // Now load our HTTP Object, it depends on settings
    connection = new HttpConnection(settings);

    // Now load user, it depends on http
    user = new User(connection);

    // Now load session, it depends on user
    sessionManager = new SessionManager(connection);
    
    session = sessionManager.createOrFindSessionAndJoin("testmap");
  }
  
  /**
   * Return the session manager.
   */
  public SessionManager getSessionManager() {
    return sessionManager;
  }
  
  /**
   * Return the connection manager.
   */
  public Connection getConnectionManager() {
    return connection;
  }
}
