package nl.tudelft.contextproject.tygron.eis.entities;

import nl.tudelft.contextproject.tygron.eis.TygronPercept;
import nl.tudelft.contextproject.tygron.objects.Economy;
import nl.tudelft.contextproject.tygron.objects.EconomyList;

import java.util.ArrayList;
import java.util.List;

public class EconomyEntity {
  private EconomyList economyList;
  
  /**
   * Creates an Economy Entity.
   * @param economyList the list with economies
   */
  public EconomyEntity(EconomyList economyList) {
    this.economyList = economyList;
  }
  
  /**
   * Percepts the economies.
   * @return the list of economies
   */
  public List<TygronPercept> economies() {  
    List<TygronPercept> result = new ArrayList<>();

    for (Economy economy : economyList) {
      result.add(new TygronPercept(economy.getId(), economy.getCategory(), economy.getState()));
    }
    
    return result;
  }
}
