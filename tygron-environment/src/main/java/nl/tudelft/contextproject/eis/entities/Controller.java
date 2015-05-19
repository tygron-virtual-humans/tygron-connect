package nl.tudelft.contextproject.eis.entities;

import nl.tudelft.contextproject.eis.TygronPercept;
import nl.tudelft.contextproject.tygron.Session;

import eis.eis2java.annotation.AsPercept;
import eis.eis2java.translation.Filter;

import java.util.List;

public class Controller {
  private IndicatorEntity indicators;
  private StakeholderEntity stakeholders;
  
  /**
   * Controller constructor.
   * @param controller the session object
   */
  public Controller(Session controller) {
    indicators = new IndicatorEntity(controller.loadIndicators());
    stakeholders = new StakeholderEntity(controller.loadStakeHolders());
  }
  

  /**
   * Percept the stakeholders from the environment.
   * @return the stakeholders
   */
  @AsPercept(name = "stakeholder", multiplePercepts = true, 
      multipleArguments = true, filter = Filter.Type.ONCE)
  public List<TygronPercept> stakeholder() {
    return stakeholders.stakeholder();
  }
  
  /**
   * Percepts the indicators on change.
   * @return the list of indicators
   */
  @AsPercept(name = "indicator", multiplePercepts = true, 
      multipleArguments = true, filter = Filter.Type.ON_CHANGE)
  public List<TygronPercept> progressIndicator() {  
    return indicators.progressIndicator();
  }
  
  /**
   * Percepts the initIndicators once.
   * @return the list of indicators
   */
  @AsPercept(name = "initIndicator", multiplePercepts = true, 
      multipleArguments = true, filter = Filter.Type.ONCE)
  public List<TygronPercept> initIndicator() {  
    return stakeholders.initIndicator();
  }

}
