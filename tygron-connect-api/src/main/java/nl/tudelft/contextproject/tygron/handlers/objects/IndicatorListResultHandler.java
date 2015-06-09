package nl.tudelft.contextproject.tygron.handlers.objects;

import nl.tudelft.contextproject.tygron.handlers.JsonArrayResultHandler;
import nl.tudelft.contextproject.tygron.handlers.ResultHandler;
import nl.tudelft.contextproject.tygron.objects.indicators.IndicatorList;

public class IndicatorListResultHandler extends ResultHandler<IndicatorList> {
  @Override
  public IndicatorList handleResult(String input) {
    return new IndicatorList(new JsonArrayResultHandler().handleResult(input));
  }
}
