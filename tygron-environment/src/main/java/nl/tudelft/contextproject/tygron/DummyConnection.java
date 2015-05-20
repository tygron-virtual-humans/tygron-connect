package nl.tudelft.contextproject.tygron;

import org.json.JSONArray;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A connection that reads from a file and only returns that response.
 * 
 */
public class DummyConnection extends Connection {

  String response;

  /**
   * Method that provides a file with a response.
   * 
   * @param inputFile
   *          file to be read
   */
  public void setFile(File inputFile) {
    try {
      response = new String(Files.readAllBytes(inputFile.toPath()),
          StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Method that provides a file with a response.
   * 
   * @param filePath
   *          file to be read
   */
  public void setFile(String filePath) {
    try {
      URL resourceUrl = getClass().getResource(filePath);
      Path resourcePath = Paths.get(resourceUrl.toURI());
      response = new String(Files.readAllBytes(resourcePath),
          StandardCharsets.UTF_8);
    } catch (URISyntaxException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String callGetEvent(String eventName) {
    return response;
  }

  @Override
  public String callPostEvent(String eventName, JSONArray parameters) {
    return response;
  }

  @Override
  public String callSessionGetEvent(String eventName) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String callSessionPostEvent(String eventName, JSONArray parameters) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setServerToken(String serverToken) {
    // TODO Auto-generated method stub
  }

  @Override
  public void setSessionId(int sessionId) {
    // TODO Auto-generated method stub
  }
}
