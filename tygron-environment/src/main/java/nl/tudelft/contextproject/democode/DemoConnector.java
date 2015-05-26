package nl.tudelft.contextproject.democode;

import nl.tudelft.contextproject.tygron.api.Connector;
import nl.tudelft.contextproject.tygron.api.Session;
import nl.tudelft.contextproject.tygron.api.SessionManager;

public class DemoConnector {

  /**
   * Demo Connector setup.
   * @param args Main Arguments
   */
  public static void main(String[] args) {
    // General setup for http
    
    Connector con = new Connector();
    
    // Session Manager
    SessionManager sesM = con.getSessionManager();
    int sessionSlot = sesM.startSession("testmap");
    
    Session sess = new Session(con.getConnection());
    sess.setId(sessionSlot);
    sesM.joinSession(sess);
   
    System.out.println("Compatible API Data/Functions:");
    System.out.println(sess.getCompatibleOperations());
    
    System.out.println("Loading stake holders:");
    System.out.println(sess.getEnvironment().loadStakeHolders());
    
    System.out.println("Loading indicators:");
    System.out.println(sess.getEnvironment().loadIndicators());
    
    System.out.println("Loading economies:");
    System.out.println(sess.getEnvironment().loadEconomies());   
    
    System.out.println("Loading buildings:");
    System.out.println(sess.getEnvironment().loadBuildings());       
   
    sess.closeSession(false);
  }
}
