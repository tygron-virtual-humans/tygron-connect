package nl.tudelft.contextproject.tygron.eis.entities;

import nl.tudelft.contextproject.tygron.eis.TygronPercept;
import nl.tudelft.contextproject.tygron.objects.indicators.Indicator;
import nl.tudelft.contextproject.tygron.objects.indicators.IndicatorList;

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

    for (Indicator indicator : indicatorList) {
      result.add(new TygronPercept(indicator.getId(), indicator.getType().toLowerCase(),
              indicator.getName().toLowerCase(), indicator.getProgress(),
              indicator.getCurrent(), indicator.getTarget()));
    }
    
    return result;
  }
}
