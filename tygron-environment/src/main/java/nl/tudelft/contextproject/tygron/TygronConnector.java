package nl.tudelft.contextproject.tygron;

public class TygronConnector {

  private TygronSettings settings;
  private TygronUser user;
  private TygronSession session;
  private static TygronHttpConnection http;

  public TygronConnector() {
    // Load settings first
    settings = new TygronSettings();

    // Now load our HTTP Object, it depends on settings
    http = new TygronHttpConnection(settings);

    // Now load user, it depends on http
    user = new TygronUser(http);

    // Now load session, it depends on user
    session = new TygronSession();
  }
}
