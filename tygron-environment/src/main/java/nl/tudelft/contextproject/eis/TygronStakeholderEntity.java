package nl.tudelft.contextproject.eis;

import nl.tudelft.contextproject.tygron.Stakeholder;
import nl.tudelft.contextproject.tygron.StakeholderList;

import eis.eis2java.annotation.AsPercept;
import eis.eis2java.translation.Filter;

import java.util.ArrayList;
import java.util.List;

public class TygronStakeholderEntity {
  private StakeholderList stakeholders;
  
  /**
   * Construct a new stakeholder entity.
   * @param stakeholders the stakeholder list
   */
  public TygronStakeholderEntity(StakeholderList stakeholders) {
    this.stakeholders = stakeholders;
  }
  
  /**
   * Percept the stakeholders from the environment.
   * @return the stakeholders
   */
  @AsPercept(name = "stakeholder", multiplePercepts = true, 
      multipleArguments = true, filter = Filter.Type.ONCE)
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
