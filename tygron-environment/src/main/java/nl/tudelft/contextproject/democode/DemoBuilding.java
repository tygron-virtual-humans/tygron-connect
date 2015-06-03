package nl.tudelft.contextproject.democode;

import nl.tudelft.contextproject.tygron.api.CallType;
import nl.tudelft.contextproject.tygron.api.Connector;
import nl.tudelft.contextproject.tygron.api.Environment;
import nl.tudelft.contextproject.tygron.api.HttpConnection;
import nl.tudelft.contextproject.tygron.api.Session;
import nl.tudelft.contextproject.tygron.api.SessionManager;
import nl.tudelft.contextproject.tygron.handlers.JsonObjectResultHandler;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;

public class DemoBuilding {

  private static final Logger logger = LoggerFactory.getLogger(DemoBuilding.class);
  
  /**
   * Main method illustrated how to build.
   * @param args Not used.
   * @throws IOException Exception from FileWriter.
   */
  public static void main(String[]  args) throws IOException {
 
    Connector connector = new Connector();

    // Get a session
    SessionManager sessionManager = connector.getSessionManager();
    Session session = sessionManager.createOrFindSessionAndJoin("example2");

    // Write the API link to a file
    FileWriter writer = new FileWriter("demoBuildingText");
    writer.write("Session link: " + "https://server2.tygron.com:3022/api/slots/" + session.getId() + "/?token=" + session.getServerToken() + "\n");
    writer.close();

    HttpConnection connection = connector.getConnection();

    // Allow interaction
    String allowInteraction = "event/SettingsLogicEventType/SETTINGS_ALLOW_GAME_INTERACTION/";
    JSONArray param = new JSONArray();
    param.put(true);
    connection.execute(allowInteraction, CallType.POST, new JsonObjectResultHandler(), 
        session, param);
  
    Environment environment = session.getEnvironment();
    environment.setStakeholder(2);
    
    for (int i = 0; i < 50; i++) {
      logger.info("Building done " + i +  ": " + environment.build(50, 0));
    }
    for (int i = 0; i < 50; i++) {
      logger.info("Park done " + i +  ": " + environment.build(50, 1));
    }
    for (int i = 0; i < 50; i++) {
      logger.info("Parking lot done " + i +  ": " + environment.build(50, 2));
    }
  }
}
