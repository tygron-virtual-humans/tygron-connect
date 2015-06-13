package nl.tudelft.contextproject.tygron.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import nl.tudelft.contextproject.tygron.CachedFileReader;
import nl.tudelft.contextproject.tygron.objects.User;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

public class UserTest {
  User user;
  
  /**
   * Initialises user test.
   */
  @Before
  public void setupUserTest() {
    String file = "/serverResponses/testmap/lists/user.json";
    String contents = CachedFileReader.getFileContents(file);
    JSONObject json = new JSONObject(contents);
    user = new User(json);
  }
  
  @Test
  public void isActiveTest() {
    assertTrue(user.isActive());
  }
  
  @Test
  public void getDomainTest() {
    assertEquals("tudelft",user.getDomain());
  }
  
  @Test
  public void geUsernameTest() {
    assertEquals("testtestson1994@gmail.com",user.getUserName());
  }
  
  @Test
  public void getFirstNameTest() {
    assertEquals("test",user.getFirstName());
  }
  
  @Test
  public void getLastNameTest() {
    assertEquals("testson",user.getLastName());
  }
  
  @Test
  public void getNickNameTest() {
    assertEquals("User 54", user.getNickName());
  }
  
  @Test
  public void getIdTest() {
    assertEquals(54,user.getId());
  }
  
  @Test
  public void getLastLoginTest() {
    assertEquals(1430904581437L,user.getLastLogin());
  }
  
  @Test
  public void maxOptionTest() {
    assertEquals("EDITOR",user.getMaxOption());
  }
}
