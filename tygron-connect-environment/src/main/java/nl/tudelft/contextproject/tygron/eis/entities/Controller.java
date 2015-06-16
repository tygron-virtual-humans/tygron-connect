package nl.tudelft.contextproject.tygron.eis.entities;

import nl.tudelft.contextproject.tygron.api.Environment;
import nl.tudelft.contextproject.tygron.api.Session;
import nl.tudelft.contextproject.tygron.api.actions.AskMoneyAction;
import nl.tudelft.contextproject.tygron.api.actions.BuildAction;
import nl.tudelft.contextproject.tygron.api.actions.BuyLandAction;
import nl.tudelft.contextproject.tygron.api.actions.GiveMoneyAction;
import nl.tudelft.contextproject.tygron.eis.TygronPercept;
import eis.eis2java.annotation.AsAction;
import eis.eis2java.annotation.AsPercept;
import eis.eis2java.translation.Filter;

import java.util.List;

public class Controller {
  private IndicatorEntity indicators;
  private StakeholderEntity stakeholders;
  private EconomyEntity economies;
  private BuildingEntity buildings;
  private Environment env;
  private Session session;

  /**
   * Controller constructor.
   * 
   * @param controller
   *          the session object.
   */
  public Controller(Session controller) {
    session = controller;
    env = controller.getEnvironment();
    indicators = new IndicatorEntity(env.loadIndicators());
    stakeholders = new StakeholderEntity(env.loadStakeholders());
    economies = new EconomyEntity(env.loadEconomies());
    buildings = new BuildingEntity(env.loadBuildings());
  }

  /**
   * Percept the stakeholders from the environment.
   * 
   * @return the stakeholders.
   */
  @AsPercept(name = "stakeholder", multiplePercepts = true, multipleArguments = true, filter = Filter.Type.ONCE)
  public List<TygronPercept> stakeholder() {
    return stakeholders.stakeholder();
  }

  /**
   * Percepts the indicators on change.
   * 
   * @return the list of indicators.
   */
  @AsPercept(name = "indicator", multiplePercepts = true, multipleArguments = true, filter = Filter.Type.ON_CHANGE)
  public List<TygronPercept> progressIndicator() {
    return indicators.progressIndicator();
  }

  /**
   * Percepts the initIndicators once.
   * 
   * @return the list of indicators.
   */
  @AsPercept(name = "initIndicator", multiplePercepts = true, multipleArguments = true, filter = Filter.Type.ONCE)
  public List<TygronPercept> initIndicator() {
    return stakeholders.initIndicator();
  }
  
  /**
   * Percepts the own stakeholder once.
   * 
   * @return the selfstakeholder.
   */
  @AsPercept(name = "stakeholderSelf", filter = Filter.Type.ONCE)
  public TygronPercept stakeholderSelf() {
    return new TygronPercept(env.getStakeholderId());
  }

  /**
   * Percepts the initIndicators on change.
   * 
   * @return the list of indicators.
   */
  @AsPercept(name = "economy", multiplePercepts = true, multipleArguments = true, filter = Filter.Type.ON_CHANGE)
  public List<TygronPercept> economies() {
    return economies.economies();
  }
  
  /**
   * Percepts the buildings on change.
   * 
   * @return the list of buildings.
   */
  @AsPercept(name = "building", multiplePercepts = true, multipleArguments = true, filter = Filter.Type.ON_CHANGE)
  public List<TygronPercept> buildings() {
    return buildings.buildings();
  }
  
  /**
   * Percepts the available land on change.
   * 
   * @return the available land.
   */
  @AsPercept(name = "availableLand", filter = Filter.Type.ON_CHANGE)
  public double availableLand() {
    return env.getAvailableSurface();
  }
  
  /**
   * Percepts all the land of the stakeholder on change.
   * 
   * @return the land of this stakeholder.
   */
  @AsPercept(name = "allLand", filter = Filter.Type.ON_CHANGE)
  public double allLand() {
    return env.getAllSurface();
  }
  
  /**
   * Percepts the number of permits on change.
   * 
   * @return the number of permits of this stakeholder.
   */
  @AsPercept(name = "permits", filter = Filter.Type.ON_CHANGE)
  public int permits() {
    return env.requestsOpen();
  }
  
  /**
   * Build action.
   * @param surface the surface to build.
   * @param type the type of the building.
   */
  @AsAction(name = "build")
  public void build(int surface, int type) {
    new BuildAction(session).build(surface, type);
  }
  
  /**
   * Buy land action.
   * @param surface the surface to buy.
   * @param cost the price to buy it.
   */
  @AsAction(name = "buyLand")
  public void buyLand(int surface, int cost) {
    new BuyLandAction(session).buyLand(surface, cost);
  }
  
  /**
   * Ask money action.
   * @param stakeholder the stakeholder to ask money from.
   * @param the amount of money.
   */
  @AsAction(name = "askMoney")
  public void ask(int stakeholder, int amount) {
    new AskMoneyAction(session).askMoney(stakeholder, amount);
  }
  
  /**
   * Give money action.
   * @param stakeholder the stakeholder to give money to.
   * @param the amount of money.
   */
  @AsAction(name = "giveMoney")
  public void give(int stakeholder, int amount) {
    new GiveMoneyAction(session).giveMoney(stakeholder, amount);
  }

}
