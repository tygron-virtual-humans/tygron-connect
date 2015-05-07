package nl.tudelft.contextproject.tygron;

public class TygronSettings {

	private final String USERNAME = "";
	private final String PASSWORD = "";
	
	public TygronSettings(){
		
	}
	
	/**
	 * Return the Tygron username.
	 * @return
	 */
	public String getUserName(){
		return this.USERNAME;
	}
	
	/**
	 * Return the Tygon password.
	 * @return
	 */
	public String getPassword(){
		return this.PASSWORD;
	}
}
