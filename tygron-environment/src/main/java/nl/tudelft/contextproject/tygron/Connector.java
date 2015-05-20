package nl.tudelft.contextproject.tygron;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The TygronConnector is the bridge between the Tygron API and JAVA.
 */
public class Connector {
  
  final Logger logger = LoggerFactory.getLogger(Connector.class);

  private User user;
  private Session session;
  private SessionManager sessionManager;
  private HttpConnection connection;

  /**
   * Create a new TygronConnector.
   */
  public Connector() {
    this(new HttpConnection());
  }

  /**
   * Create a new TygronConnector with a given connection.
   * 
   * @param connection
   *          the connection to use.
   */
  public Connector(HttpConnection connection) {

    logger.info("Connector loading.");

    this.connection = connection;

    // Now load user, it depends on http
    user = new User(connection);
    user.loadSettings();

    logger.info("Using user #" + user.getId() + " " + user.getUserName() + " "
        + user.getFirstName() + " " + user.getLastName());

    // Now load session, it depends on user
    sessionManager = new SessionManager(connection);

    session = sessionManager.createOrFindSessionAndJoin("testmap");

    logger.info("Connector loading complete.");
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
  public HttpConnection getConnectionManager() {
    return connection;
  }

  /**
   * Return the session.
   */
  public Session getSession() {
    return session;
  }
}
