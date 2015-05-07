package nl.tudelft.contextproject.tygron;


public class TygronConnector {

	private TygronSettings settings;
	private TygronUser user;
	private TygronSession session;
	private TygronHTTP http;
	
	public TygronConnector(){
		setup();
	}

	/**
	 * Set up/ create required objects.
	 */
	public void setup(){
		settings = new TygronSettings();
		user = new TygronUser();
		session = new TygronSession();
		
		http = new TygronHTTP(settings);
	}
}
