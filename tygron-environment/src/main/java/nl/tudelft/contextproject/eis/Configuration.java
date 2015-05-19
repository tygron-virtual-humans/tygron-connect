package nl.tudelft.contextproject.eis;


public class Configuration {
  
  private int stakeholders;
  
  public Configuration() {
    
  }
  
  public void setStakeholder(int stakeholderParametersList) {
    stakeholders = stakeholderParametersList;
  }
  
  public int getStakeholder() {
    return stakeholders;
  }

}
