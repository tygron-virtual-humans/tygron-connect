package nl.tudelft.contextproject.tygron.objects;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A Function is a type of object that can be placed in the game.
 */
public class Function {
  
  public enum CategoryType {
    SOCIAL, EDUCATION, OTHER, PARK, ROAD, STUDENT, LEISURE, LUXE, SHOPPING, PAVED_AREA, OFFICES, 
    NORMAL, SENIOR, HEALTHCARE, INDUSTRY, AGRICULTURE, NATURE, UNDERGROUND_WITH_FREE_TOP, 
    UNDERGROUND_WITH_TOP_BUILDING, GARDEN
  }
  
  private static List<CategoryType> housingType = Arrays.asList(CategoryType.SOCIAL, 
      CategoryType.NORMAL, CategoryType.STUDENT, CategoryType.LUXE, CategoryType.SENIOR);
  
  private static List<CategoryType> parkingType = Arrays.asList(CategoryType.PAVED_AREA);
  
  private static List<CategoryType> parkType = Arrays.asList(CategoryType.PARK, 
      CategoryType.GARDEN, CategoryType.NATURE);
  
  private static List<CategoryType> officeType = Arrays.asList(CategoryType.OFFICES);
  
  private int id;
  private int version;
  private int maxFloors;
  private int minFloors;
  private String name;
  private CategoryType categoryValue;
  
  /**
   * Constructs a Function from a JSONObject.
   * @param function The input object
   */
  public Function(JSONObject function) {
    id = function.getInt("id");
    version = function.getInt("version");
    name = function.getString("name");
    for (Object object : function.getJSONObject("categoryValues").keySet()) {
      categoryValue = CategoryType.valueOf(object.toString());
    }
    
    JSONObject functionValues = function.getJSONObject("functionValues");
    maxFloors = functionValues.getInt("MAX_FLOORS");
    minFloors = functionValues.getInt("MIN_FLOORS");
  }

  public int getId() {
    return id;
  }

  public int getVersion() {
    return version;
  }

  public int getMaxFloors() {
    return maxFloors;
  }

  public int getMinFloors() {
    return minFloors;
  }

  public String getName() {
    return name;
  }
  
  public CategoryType getCategoryValue() {
    return categoryValue;
  }
  
  /**
   * Determines whether the function is of the correct type.
   * @param type The type for the function.
   * @return Whether the function is the correct type.
   */
  public boolean isRightType(int type) {
    List<Function.CategoryType> categoryList;
    switch (type) {
      case 0:
        categoryList = housingType;
        break;
      case 1:
        categoryList = parkType;
        break;
      case 2:
        categoryList = parkingType;
        break;
      case 3:
        categoryList = officeType;
        break;
      default:
        categoryList = new ArrayList<>();
        break;
    }
    return categoryList.contains(this.categoryValue);
  }
  
  /**
   * Determines whether the function can be built with the given amount of floors.
   * @param floors The amount of floors.
   * @return Whether the function has the right amount of floors.
   */
  public boolean hasEnoughFloors(int floors) {
    return floors <= this.maxFloors && floors >= this.minFloors;
  }
}
