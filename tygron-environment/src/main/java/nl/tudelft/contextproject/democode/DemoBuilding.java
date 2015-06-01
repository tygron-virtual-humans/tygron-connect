package nl.tudelft.contextproject.democode;

import nl.tudelft.contextproject.tygron.api.CallType;
import nl.tudelft.contextproject.tygron.api.Connector;
import nl.tudelft.contextproject.tygron.api.Environment;
import nl.tudelft.contextproject.tygron.api.HttpConnection;
import nl.tudelft.contextproject.tygron.api.Session;
import nl.tudelft.contextproject.tygron.api.SessionManager;
import nl.tudelft.contextproject.tygron.handlers.JsonObjectResultHandler;
import nl.tudelft.contextproject.tygron.objects.Stakeholder;
import nl.tudelft.contextproject.tygron.objects.StakeholderList;

import org.json.JSONArray;

import java.io.FileWriter;
import java.io.IOException;

public class DemoBuilding {

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

    // Get housing corporation
    StakeholderList stakeholderList = environment.loadStakeHolders();
    Stakeholder selectedStakeholder = null;
    for (Stakeholder stakeholder : stakeholderList) {
      if (stakeholder.getType().equals("HOUSING_CORPORATION")) {
        selectedStakeholder = stakeholder;
      }
    }

    System.out.println("Building done: " + environment.build(selectedStakeholder, 50));

  }
}
