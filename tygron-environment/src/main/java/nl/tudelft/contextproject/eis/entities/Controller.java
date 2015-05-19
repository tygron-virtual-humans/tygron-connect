package nl.tudelft.contextproject.eis.entities;

import nl.tudelft.contextproject.eis.TygronPercept;
import nl.tudelft.contextproject.tygron.Session;

import eis.eis2java.annotation.AsPercept;
import eis.eis2java.translation.Filter;

import java.util.List;

public class Controller {
  private IndicatorEntity indicators;
  private StakeholderEntity stakeholders;
  private EconomyEntity economies;
  
  /**
   * Controller constructor.
   * @param controller the session object
   */
  public Controller(Session controller) {
    indicators = new IndicatorEntity(controller.loadIndicators());
    stakeholders = new StakeholderEntity(controller.loadStakeHolders());
    economies = new EconomyEntity(controller.loadEconomies());
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

  /**
   * Percepts the initIndicators on change.
   * @return the list of indicators
   */
  @AsPercept(name = "economy", multiplePercepts = true, 
      multipleArguments = true, filter = Filter.Type.ON_CHANGE)
  public List<TygronPercept> economies() {  
    return economies.economies();
  }
  
}
