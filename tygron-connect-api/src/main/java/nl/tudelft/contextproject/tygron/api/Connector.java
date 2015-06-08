package nl.tudelft.contextproject.tygron.api;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.tudelft.contextproject.tygron.Settings;
import nl.tudelft.contextproject.tygron.handlers.JsonObjectResultHandler;
import nl.tudelft.contextproject.tygron.objects.User;

/**
 * The TygronConnector is the bridge between the Tygron API and JAVA.
 */
public class Connector {

  private static final Logger logger = LoggerFactory.getLogger(Connector.class);

  private User user;
  private Session session;
  private SessionManager sessionManager;

  /**
   * Create a new TygronConnector.
   */
  public Connector() {
    logger.info("Connector loading.");

    // Now load user, it depends on http
    JSONObject userObj = HttpConnection.getInstance().execute("services/myuser", CallType.GET, new JsonObjectResultHandler());
    user = new User(userObj);

    logger.info("Using user #" + user.getId() + " " + user.getUserName() + " " + user.getFirstName() + " "
        + user.getLastName());

    // Create a new session manager
    sessionManager = new SessionManager();
    logger.info("Connector loading complete.");
  }
  

  /**
   * Connect to a session on a mapname.
   * @param mapName The project name.
   */
  public void connectToMap(String mapName) {
    session = sessionManager.createOrFindSessionAndJoin(mapName);
  }
  
  /**
   * Connect to a session on a mapname, but have a prefered slot.
   * @param mapName The project name.
   * @param slotId The session slot id.
   */
  public void connectToMap(String mapName, int slotId) {
    session = sessionManager.createOrFindSessionAndJoin(mapName,slotId);
  }

  /**
   * Return the session manager.
   * @return the session manager
   */
  public SessionManager getSessionManager() {
    return sessionManager;
  }

  /**
   * Return the session.
   * @return the session
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
