package nl.tudelft.contextproject.eis;

import eis.eis2java.annotation.AsPercept;
import eis.eis2java.translation.Filter;

import java.util.ArrayList;
import java.util.List;

public class TygronEntity {

  public TygronEntity() {

  }

  //SEND ONCE PERCEPTS
  
  /**
   * Stubbed method for percepting initIndicator.
   * @return percept of the initIndicator, currently a stub
   */
  @AsPercept(name = "initIndicator", multipleArguments = true, filter = Filter.Type.ONCE)
  public List<Object> initIndicator() {
    List<Object> result = new ArrayList<>();
    result.add("Green");
    result.add(6);
    result.add(1);
    
    return result;
  }
  
  /**
   * Stubbed method for percepting target.
   * @return percept of the target, currently a stub
   */
  @AsPercept(name = "target", multipleArguments = true, filter = Filter.Type.ONCE)
  public List<Object> target() {
    List<Object> result = new ArrayList<>();
    result.add(2);
    result.add("Build Houses");
    
    return result;
  }
  
  /**
   * Stubbed method for percepting stakeholder.
   * @return percept of the stakeholder, currently a stub
   */
  @AsPercept(name = "stakeholder", multipleArguments = true, filter = Filter.Type.ONCE)
  public List<Object> stakeholder() {
    List<Object> result = new ArrayList<>();
    result.add(3);
    result.add("Municipality");
    
    return result;
  }
  
  /**
   * Stubbed method for percepting otherStakeholders.
   * @return percept of the otherStakeholders, currently a stub
   */
  @AsPercept(name = "otherStakeholders", multiplePercepts = true,
      multipleArguments = true, filter = Filter.Type.ONCE)
  public List<List<Object>> otherStakeholders() {
    List<List<Object>> result = new ArrayList<>();
    
    List<Object> education = new ArrayList<>();
    education.add(1);
    education.add("Education");
    result.add(education);
    
    List<Object> housing = new ArrayList<>();
    housing.add(2);
    housing.add("Housing");
    result.add(housing);
    
    List<Object> company = new ArrayList<>();
    company.add(4);
    company.add("Company");
    result.add(company);
    
    return result;
  }
  
  //ON CHANGE PERCEPTS
  
  /**
   * Stubbed method for percepting progressIndicator.
   * @return percept of the progressIndicator, currently a stub
   */
  @AsPercept(name = "progressIndicator", multiplePercepts = true, 
      multipleArguments = true, filter = Filter.Type.ON_CHANGE)
  public List<List<Object>> progressIndicator() {
    List<List<Object>> result = new ArrayList<>();
    
    List<Object> green = new ArrayList<>();
    green.add("Green");
    green.add(6);
    result.add(green);
    
    List<Object> budget = new ArrayList<>();
    budget.add("Budget");
    budget.add(2);
    result.add(budget);
    
    List<Object> livability = new ArrayList<>();
    livability.add("Livability");
    livability.add(8);
    result.add(livability);
    
    List<Object> parking = new ArrayList<>();
    parking.add("Parking");
    parking.add(3);
    result.add(parking);
    
    List<Object> housing = new ArrayList<>();
    housing.add("Housing");
    housing.add(5);
    result.add(housing);
    
    List<Object> total = new ArrayList<>();
    total.add("Total");
    total.add(24);
    result.add(total);
    
    return result;
  }
  
  /**
   * Stubbed method for percepting ground.
   * @return percept of the ground, currently a stub
   */
  @AsPercept(name = "groundpercept", multiplePercepts = true, 
      multipleArguments = true, filter = Filter.Type.ON_CHANGE)
  public List<List<Object>> ground() {
    List<List<Object>> result = new ArrayList<>();

    List<Object> free = new ArrayList<>();
    free.add("free");
    free.add(1038);
    result.add(free);
    
    List<Object> holder1 = new ArrayList<>();
    holder1.add(2);
    holder1.add(1976);
    result.add(holder1);

    List<Object> holder2 = new ArrayList<>();
    holder2.add(4);
    holder2.add(2);
    result.add(holder2);
    
    return result;
  }
  
  /**
   * Stubbed method for percepting budget.
   * @return percept of the budget, currently a stub
   */
  @AsPercept(name = "budget", filter = Filter.Type.ON_CHANGE)
  public int budget() {
    return 123456;
  }
  
  /**
   * Stubbed method for percepting permit.
   * @return percept of the permit, currently a stub
   */
  @AsPercept(name = "permit", filter = Filter.Type.ON_CHANGE)
  public List<Object> permit() {
    List<Object> result = new ArrayList<>();

    result.add(2);
    result.add(192);
    result.add("progress");
    result.add("House");
    
    return result;
  }
  
  
}
