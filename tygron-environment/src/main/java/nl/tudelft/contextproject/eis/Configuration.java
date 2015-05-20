package nl.tudelft.contextproject.eis;

public class Configuration {

  private int stakeholders;
  private String map;

  public Configuration() {

  }

  public void setStakeholder(int stakeholderParametersList) {
    stakeholders = stakeholderParametersList;
  }

  public int getStakeholder() {
    return stakeholders;
  }

  public void setMap(String map) {
    this.map = map;
  }

  public String getMap() {
    return map;
  }

}
