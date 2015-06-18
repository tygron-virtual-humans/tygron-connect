package nl.tudelft.contextproject.tygron.handlers.objects;

import nl.tudelft.contextproject.tygron.handlers.JsonArrayResultHandler;
import nl.tudelft.contextproject.tygron.handlers.ResultHandler;
import nl.tudelft.contextproject.tygron.objects.ServerWords;

public class ServerWordsResultHandler extends ResultHandler<ServerWords> {
  @Override
  public ServerWords handleResult(String input) {
    return new ServerWords(new JsonArrayResultHandler().handleResult(input));
  }
}
