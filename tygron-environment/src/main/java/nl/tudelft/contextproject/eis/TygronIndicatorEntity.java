package nl.tudelft.contextproject.eis;

import nl.tudelft.contextproject.tygron.Indicator;
import nl.tudelft.contextproject.tygron.IndicatorList;

import eis.eis2java.annotation.AsPercept;
import eis.eis2java.translation.Filter;
import java.util.List;

public class TygronIndicatorEntity {
  private IndicatorList indicatorList;
  
  /**
   * Creates an Indicator Entity.
   * @param indicatorList the list with indicators
   */
  public TygronIndicatorEntity(IndicatorList indicatorList) {
    this.indicatorList = indicatorList;
  }
  
  /**
   * Percepts the indicators on change.
   * @return the list of indicators
   */
  @AsPercept(name = "indicator", multiplePercepts = true, 
      multipleArguments = true, filter = Filter.Type.ON_CHANGE)
  public List<Indicator> progressIndicator() {    
    return indicatorList;
  }
}
