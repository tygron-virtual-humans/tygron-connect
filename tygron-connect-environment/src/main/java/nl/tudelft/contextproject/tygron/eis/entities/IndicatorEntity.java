package nl.tudelft.contextproject.tygron.eis.entities;

import nl.tudelft.contextproject.tygron.api.Environment;
import nl.tudelft.contextproject.tygron.eis.TygronPercept;
import nl.tudelft.contextproject.tygron.objects.indicators.Indicator;
import nl.tudelft.contextproject.tygron.objects.indicators.IndicatorList;

import java.util.ArrayList;
import java.util.List;

public class IndicatorEntity {
  private Environment environment;
  
  /**
   * Creates an Indicator Entity.
   * @param environment the environment that provides this entity
   */
  public IndicatorEntity(Environment environment) {
    this.environment = environment;
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
    IndicatorList indicatorList = environment.get(IndicatorList.class);

    for (Indicator indicator : indicatorList) {
      result.add(new TygronPercept(indicator.getId(), indicator.getType().toLowerCase(),
              indicator.getName().toLowerCase(), indicator.getProgress(),
              indicator.getCurrent(), indicator.getTarget()));
    }
    
    return result;
  }
}
