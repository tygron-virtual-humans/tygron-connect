package nl.tudelft.contextproject.tygron.objects;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Server Words are references from "keys" to "values" for translations. 
 * Messages are passed on as integers, and with the server words we can translate them to regular languages.
 *
 */
public class ServerWords extends ArrayList<String> {
  /**
   * Serial ID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Parses server words from a JSONArray.
   * @param input The array containing the server words.
   */
  public ServerWords(JSONArray input) {
    for (int i = 0; i < input.length(); i++) {
      String content = input.getJSONObject(i).getJSONObject("ServerWord").getString("translation");
      this.add(content);
    }
  }
}
