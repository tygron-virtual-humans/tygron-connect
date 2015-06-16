package nl.tudelft.contextproject.democode;

import nl.tudelft.contextproject.tygron.api.Connector;
import nl.tudelft.contextproject.tygron.api.Session;

import nl.tudelft.contextproject.tygron.objects.BuildingList;
import nl.tudelft.contextproject.tygron.objects.EconomyList;
import nl.tudelft.contextproject.tygron.objects.StakeholderList;
import nl.tudelft.contextproject.tygron.objects.indicators.IndicatorList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemoConnector {

  private DemoConnector() {
    // Static class
  }
  
  /**
   * Demo Connector setup.
   * @param args Main Arguments
   */
  public static void main(String[] args) {
    final Logger logger = LoggerFactory.getLogger(DemoConnector.class);
    
    // General setup for http
    Connector con = new Connector();
    con.connectToMap("testmap");
    
    // Session Manager
    Session sess = con.getSession();
   
    logger.info("Compatible API Data/Functions:");
    logger.info(sess.getCompatibleOperations().toString());
    
    logger.info("Loading stake holders:");
    logger.info(sess.getEnvironment().get(StakeholderList.class).toString());
    
    logger.info("Loading indicators:");
    logger.info(sess.getEnvironment().get(IndicatorList.class).toString());
    
    logger.info("Loading economies:");
    logger.info(sess.getEnvironment().get(EconomyList.class).toString());
    
    logger.info("Loading buildings:");
    logger.info(sess.getEnvironment().get(BuildingList.class).toString());
    
    logger.info("Setting stakeholder:");
    sess.getEnvironment().setStakeholder(1); 
   
    sess.closeSession(false);
  }
}

