package nl.tudelft.contextproject.tygron.api;

import java.io.Serializable;

import nl.tudelft.contextproject.tygron.Settings;
import nl.tudelft.contextproject.tygron.handlers.JsonObjectResultHandler;
import nl.tudelft.contextproject.tygron.objects.User;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The TygronConnector is the bridge between the Tygron API and JAVA.
 */
public class Connector implements Serializable {

  /**
   * Seial version ID.
   */
  private static final long serialVersionUID = 1L;

  private static final Logger logger = LoggerFactory.getLogger(Connector.class);

  private User user;
  private Session session;
  private SessionManager sessionManager;
  private HttpConnection connection;

  /**
   * Create a new TygronConnector.
   */
  public Connector() {
    this(new HttpConnection(new Settings()));
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
    JSONObject userObj = connection.execute("services/myuser", CallType.GET, new JsonObjectResultHandler());
    user = new User(userObj);

    logger.info("Using user #" + user.getId() + " " + user.getUserName() + " " + user.getFirstName() + " "
        + user.getLastName());

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
   * Returns the connection.
   */
  public HttpConnection getConnection() {
    return connection;
  }

  /**
   * Return the session.
   */
  public Session getSession() {
    return session;
  }

  /**
   * Cleanup function, should be called to reset or shut down.
   */
  public void cleanup() {
    if (session != null) {
      session.closeSession(false);
    }
  }
}
