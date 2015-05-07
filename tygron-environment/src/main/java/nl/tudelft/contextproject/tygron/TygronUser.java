package nl.tudelft.contextproject.tygron;

import org.json.JSONObject;

public class TygronUser {

	private static TygronHTTP http;
	
	private boolean ACCOUNT_ACTIVE = false;
	private String ACCOUNT_DOMAIN = "";
	private String ACCOUNT_USERNAME = "";
	
	public TygronUser(TygronHTTP localhttp){
		http = localhttp;
	}
	
	public void loadSettings(){
		if(http != null){
			// Todo: insert call to fetch userdata
			JSONObject userObj = new JSONObject();
		}
	}
	
}
