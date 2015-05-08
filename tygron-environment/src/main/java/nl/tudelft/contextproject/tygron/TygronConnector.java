package nl.tudelft.contextproject.tygron;

/**
 * The TygronConnector is the bridge between the Tygron API and JAVA.
 */
public class TygronConnector {

  private TygronSettings settings;
  private TygronUser user;
  private TygronSession session;
  private static TygronHttpConnection http;

  /**
   * Create a new TygronConnector.
   */
  public TygronConnector() {
    // Load settings first
    settings = new TygronSettings();

    // Now load our HTTP Object, it depends on settings
    http = new TygronHttpConnection(settings);

    // Now load user, it depends on http
    user = new TygronUser(http);

    // Now load session, it depends on user
    session = new TygronSession(http);
    
    System.out.println(session.getJoinableSessions());
  }
}
