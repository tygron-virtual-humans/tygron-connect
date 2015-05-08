package nl.tudelft.contextproject.tygron;

public class TygronConnector {

  private TygronSettings settings;
  private TygronUser user;
  private TygronSession session;
  private static TygronHttp http;

  public TygronConnector() {
    setup();
  }

  /**
   * Set up/ create required objects.
   */
  public void setup() {

    // Load settings first
    settings = new TygronSettings();

    // Now load our HTTP Object, it depends on settings
    http = new TygronHttp(settings);

    // Now load user, it depends on http
    user = new TygronUser(http);

    // Now load session, it depends on user
    session = new TygronSession();

  }
}
