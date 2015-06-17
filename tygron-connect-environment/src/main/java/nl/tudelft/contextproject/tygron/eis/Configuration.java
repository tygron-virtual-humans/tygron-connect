package nl.tudelft.contextproject.tygron.eis;

public class Configuration {
  private int stakeholder;
  private String map;
  private int slot;

  public Configuration() {
    slot = -1;
    stakeholder = -1;
  }

  public void setStakeholder(int stakeholderParametersList) {
    stakeholder = stakeholderParametersList;
  }

  public int getStakeholder() {
    return stakeholder;
  }

  public void setMap(String map) {
    this.map = map;
  }

  public String getMap() {
    return map;
  }
  
  public void setSlot(int slot) {
    this.slot = slot;
  }

  public int getSlot() {
    return slot;
  }

}
