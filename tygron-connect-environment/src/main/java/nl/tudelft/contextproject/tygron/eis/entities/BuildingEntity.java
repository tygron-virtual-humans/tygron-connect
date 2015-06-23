package nl.tudelft.contextproject.tygron.eis.entities;

import nl.tudelft.contextproject.tygron.api.Environment;
import nl.tudelft.contextproject.tygron.eis.TygronPercept;
import nl.tudelft.contextproject.tygron.objects.Building;
import nl.tudelft.contextproject.tygron.objects.BuildingList;

import java.util.ArrayList;
import java.util.List;

public class BuildingEntity {
  private Environment environment;
  
  /**
   * Creates an buildings Entity.
   * @param environment the environment that provides this entity
   */
  public BuildingEntity(Environment environment) {
    this.environment = environment;
  }
  
  /**
   * Percepts the buildings.
   * @return the list of buildings
   */
  public List<TygronPercept> buildings() {  
    List<TygronPercept> result = new ArrayList<>();
    BuildingList buildingList = environment.get(BuildingList.class);

    for (Building building : buildingList) {
      result.add(new TygronPercept(building.getId(), building.getName()));
    }
    
    return result;
  }
}
