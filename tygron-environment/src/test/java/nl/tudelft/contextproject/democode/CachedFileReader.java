package nl.tudelft.contextproject.democode;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CachedFileReader {
  /**
   * Reads a file and returns it's contents.
   * @param filePath the file that should be read
   * @return it's contents
   */
  public static String getFileContents(String filePath) {
    try {
      URL resourceUrl = CachedFileReader.class.getResource(filePath);
      Path resourcePath = Paths.get(resourceUrl.toURI());
      byte[] bytes = Files.readAllBytes(resourcePath);
      return new String(bytes, StandardCharsets.UTF_8);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
