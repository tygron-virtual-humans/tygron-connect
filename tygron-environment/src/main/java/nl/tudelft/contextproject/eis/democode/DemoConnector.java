package nl.tudelft.contextproject.eis.democode;

import nl.tudelft.contextproject.tygron.Connector;
import nl.tudelft.contextproject.tygron.SessionManager;

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
    
    int sessionSlot = sesM.createSession("testmap");
    System.out.println("Creating session in slot " + sessionSlot);
    boolean sessionKill = sesM.killSession(sessionSlot);
    System.out.println("Killing session, result: " + sessionKill);
  }
}
