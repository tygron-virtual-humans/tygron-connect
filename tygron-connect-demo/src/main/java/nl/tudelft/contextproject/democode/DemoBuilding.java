package nl.tudelft.contextproject.democode;

import nl.tudelft.contextproject.tygron.api.Connector;
import nl.tudelft.contextproject.tygron.api.Environment;
import nl.tudelft.contextproject.tygron.api.Session;
import nl.tudelft.contextproject.tygron.api.actions.BuildAction;
import nl.tudelft.contextproject.tygron.api.actions.BuyLandAction;
import nl.tudelft.contextproject.tygron.api.actions.DemolishAction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;

public class DemoBuilding {

  private static final Logger logger = LoggerFactory.getLogger(DemoBuilding.class);
  
  private DemoBuilding(){
	// Static class
  }
  
  /**
   * Main method illustrated how to build.
   * @param args Not used.
   * @throws IOException Exception from FileWriter.
   */
  public static void main(String[]  args) throws IOException {
 
    Connector connector = new Connector();

    // Get a session
    connector.connectToMap("testmap");
    Session session = connector.getSession();
    
    // Write the API link to a file
    FileWriter writer = new FileWriter("demoBuildingText");
    writer.write("Session link: " + "https://server2.tygron.com:3022/api/slots/" + session.getId() + "/?token=" + session.getServerToken() + "\n");
    writer.close();
  
    Environment environment = session.getEnvironment();
    
    // Wait for environment to load in all data.
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } 
    
    environment.allowGameInteraction(true);
    environment.setStakeholder(1);
    
    logger.info("Demolishing done: " + new DemolishAction(environment).demolish(5000));
    
    logger.info("Buying land done: " + new BuyLandAction(environment).buyLand(5000, 200));
    
    for (int i = 0; i < 10; i++) {
      logger.info("Building done " + i +  ": " + new BuildAction(environment).build(50, 0));
    }
    for (int i = 0; i < 10; i++) {
      logger.info("Park done " + i +  ": " + new BuildAction(environment).build(50, 1));
    }
    for (int i = 0; i < 10; i++) {
      logger.info("Parking lot done " + i +  ": " + new BuildAction(environment).build(50, 2));
    }
    
    environment.releaseStakeholder();
  }
}
