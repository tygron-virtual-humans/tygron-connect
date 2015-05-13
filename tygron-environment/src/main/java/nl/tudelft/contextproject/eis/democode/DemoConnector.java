package nl.tudelft.contextproject.eis.democode;

import nl.tudelft.contextproject.tygron.TygronConnector;
import nl.tudelft.contextproject.tygron.TygronSessionManager;

public class DemoConnector {

  /**
   * Demo Connector setup.
   * @param args Main Arguments
   */
  public static void main(String[] args) {
    // General setup for http
    TygronConnector con = new TygronConnector();
    
    // Session Manager
    TygronSessionManager sesM = con.getSessionManager();
    
    sesM.createSession("testmap");
  }
}
