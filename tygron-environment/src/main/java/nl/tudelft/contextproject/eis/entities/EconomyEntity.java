package nl.tudelft.contextproject.eis.entities;

import nl.tudelft.contextproject.eis.TygronPercept;
import nl.tudelft.contextproject.tygron.Economy;
import nl.tudelft.contextproject.tygron.EconomyList;

import java.util.ArrayList;
import java.util.List;

public class EconomyEntity {
  private EconomyList economyList;
  
  /**
   * Creates an Indicator Entity.
   * @param indicatorList the list with indicators
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
    
    for (int i = 0; i < economyList.size(); i++) {
      Economy ec = economyList.get(i);
      result.add(new TygronPercept(ec.getId(),ec.getCategory(),ec.getState()));
    }
    
    return result;
  }
}
