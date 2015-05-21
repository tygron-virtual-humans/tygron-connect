package nl.tudelft.contextproject.tygron.objects;

import nl.tudelft.contextproject.tygron.api.Session;

import java.util.ArrayList;

public class PopUpList extends ArrayList<PopUp> {
  
  private int version;

  /**
   * Serial ID.
   */
  private static final long serialVersionUID = 1L;
  
  /** A list containing all active popups.
   * 
   * @param array The id of the player's stakeholder id
   */
  public PopUpList(int stakeholderId) {
    
  }
  
  public void updateList(Session session) {
  }
}
