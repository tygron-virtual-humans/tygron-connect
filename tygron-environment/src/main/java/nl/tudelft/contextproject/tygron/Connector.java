package nl.tudelft.contextproject.tygron;

/**
 * The TygronConnector is the bridge between the Tygron API and JAVA.
 */
public class Connector {
  private User user;
  private Session session;
  private SessionManager sessionManager;
  private Connection connection;

  /**
   * Create a new TygronConnector.
   */
  public Connector() {
    this(new HttpConnection(new Settings()));
  }
  
  /**
   * Create a new TygronConnector with a given connection.
   * @param connection the connection to use.
   */
  public Connector(Connection connection) {
    this.connection = connection;
    
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
