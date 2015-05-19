package nl.tudelft.contextproject.entities;

import nl.tudelft.contextproject.eis.TygronPercept;
import nl.tudelft.contextproject.tygron.Stakeholder;
import nl.tudelft.contextproject.tygron.StakeholderList;

import java.util.ArrayList;
import java.util.List;

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
    
    for (int i = 0; i < stakeholders.size(); i++) {
      Stakeholder stakeholder = stakeholders.get(i);
      result.add(new TygronPercept(stakeholder.getId(),
          stakeholder.getName(),stakeholder.getShortName()));
    }
    
    return result;
  }
}
