package nl.tudelft.contextproject.tygron;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;

/**
 * A connection that reads from a file and only returns that response.
 *
 */
public class TygronDummyConnection implements TygronConnection {
  JSONObject response;
  
  /**
   * Constructor that provides a file with a response.
   * @param inputFile file to be read
   */
  public TygronDummyConnection(File inputFile) {
    try {
      String fileText = new String(Files.readAllBytes(inputFile.toPath()), StandardCharsets.UTF_8);
      response = new JSONObject(fileText);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public JSONObject callGetEvent(String eventName) {
    return response;
  }

  @Override
  public JSONObject callPostEvent(String eventName, Map<String, String> parameters) {
    return response;
  }

}
