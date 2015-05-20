package nl.tudelft.contextproject.eis;

public enum ParamEnum {
  STAKEHOLDER("stakeholder"), 
  MAP("map");
  
  private String param;

  private ParamEnum(String name) {
    this.param = name;
  }

  public String getParam() {
    return param;
  }
}
