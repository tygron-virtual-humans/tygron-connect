package nl.tudelft.contextproject.tygron.eis.entities;

import nl.tudelft.contextproject.tygron.api.Environment;
import nl.tudelft.contextproject.tygron.eis.TygronPercept;
import nl.tudelft.contextproject.tygron.objects.Economy;
import nl.tudelft.contextproject.tygron.objects.EconomyList;

import java.util.ArrayList;
import java.util.List;

public class EconomyEntity {
  private Environment environment;
  
  /**
   * Creates an Economy Entity.
   * @param environment the environment that provides this entity
   */
  public EconomyEntity(Environment environment) {
    this.environment = environment;
  }
  
  /**
   * Percepts the economies.
   * @return the list of economies
   */
  public List<TygronPercept> economies() {  
    List<TygronPercept> result = new ArrayList<>();
    EconomyList economyList = environment.get(EconomyList.class);

    for (Economy economy : economyList) {
      result.add(new TygronPercept(economy.getId(), economy.getCategory(), economy.getState()));
    }
    
    return result;
  }
}
