package nl.tudelft.contextproject.tygron.eis.entities;

import nl.tudelft.contextproject.tygron.eis.TygronPercept;
import nl.tudelft.contextproject.tygron.objects.Building;
import nl.tudelft.contextproject.tygron.objects.BuildingList;

import java.util.ArrayList;
import java.util.List;

public class BuildingEntity {
  private BuildingList buildingList;
  
  /**
   * Creates an buildings Entity.
   * @param economyList the list with buildings
   */
  public BuildingEntity(BuildingList buildingList) {
    this.buildingList = buildingList;
  }
  
  /**
   * Percepts the buildings.
   * @return the list of buildings
   */
  public List<TygronPercept> buildings() {  
    List<TygronPercept> result = new ArrayList<>();
    
    for (int i = 0; i < buildingList.size(); i++) {
      Building build = buildingList.get(i);
      result.add(new TygronPercept(build.getId(), build.getName()));
    }
    
    return result;
  }
}
