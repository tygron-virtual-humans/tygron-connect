package nl.tudelft.contextproject.eis.entities;

import nl.tudelft.contextproject.eis.TygronPercept;
import nl.tudelft.contextproject.tygron.Indicator;
import nl.tudelft.contextproject.tygron.IndicatorList;

import java.util.ArrayList;
import java.util.List;

public class IndicatorEntity {
  private IndicatorList indicatorList;
  
  /**
   * Creates an Indicator Entity.
   * @param indicatorList the list with indicators
   */
  public IndicatorEntity(IndicatorList indicatorList) {
    this.indicatorList = indicatorList;
  }
  
  /**
   * Percepts the indicators.
   *  Id
   *  Type
   *  Name
   *  Progress
   *  Current
   *  Target
   * @return the list of indicators
   */
  public List<TygronPercept> progressIndicator() {  
    List<TygronPercept> result = new ArrayList<>();
    
    for (int i = 0; i < indicatorList.size(); i++) {
      Indicator ind = indicatorList.get(i);
      result.add(new TygronPercept(ind.getId(), ind.getType().toLowerCase(),
          ind.getName().toLowerCase(), ind.getProgress(),
          ind.getCurrent(), ind.getTarget()));
    }
    
    return result;
  }
}
