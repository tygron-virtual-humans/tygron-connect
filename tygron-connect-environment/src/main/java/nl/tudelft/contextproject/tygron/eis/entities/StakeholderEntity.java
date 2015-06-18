package nl.tudelft.contextproject.tygron.eis.entities;

import nl.tudelft.contextproject.tygron.eis.TygronPercept;
import nl.tudelft.contextproject.tygron.objects.Stakeholder;
import nl.tudelft.contextproject.tygron.objects.StakeholderList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class StakeholderEntity {
  private StakeholderList stakeholders;
  
  /**
   * Construct a new stakeholder entity.
   * @param stakeholders the stakeholder list
   */
  public StakeholderEntity(StakeholderList stakeholders) {
    this.stakeholders = stakeholders;
  }
  
  /**
   * Percept the stakeholders from the environment.
   * @return the stakeholders
   */
  public List<TygronPercept> stakeholder() {
    List<TygronPercept> result = new ArrayList<>();

    for (Stakeholder stakeholder : stakeholders) {
      result.add(new TygronPercept(stakeholder.getId(),
              stakeholder.getName(), stakeholder.getShortName()));
    }
    
    return result;
  }
  
  /**
   * Percept the initIndicators from the environment.
   * @return the initIndicators
   */
  public List<TygronPercept> initIndicator() {
    List<TygronPercept> result = new ArrayList<>();

    for (Object stakeholder : stakeholders) {
      Map<Integer, Double> weightMap = stakeholder.getIndicatorWeights();
      for (Entry<Integer, Double> entry : weightMap.entrySet()) {
        if (entry.getValue() > 0.0) {
          result.add(new TygronPercept(stakeholder.getId(),
                  entry.getKey(), entry.getValue()));
        }
      }
    }
    
    return result;
  }
}
