package nl.tudelft.contextproject.tygron.eis.translators;

import eis.iilang.Identifier;
import eis.iilang.Parameter;
import nl.tudelft.contextproject.tygron.eis.Configuration;
import nl.tudelft.contextproject.tygron.eis.ParamEnum;

import eis.eis2java.exception.TranslationException;
import eis.eis2java.translation.Parameter2Java;
import eis.eis2java.translation.Translator;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ConfigurationTranslator implements Parameter2Java<Configuration> {

  public ConfigurationTranslator() {
    // used for testing
  }
  
  @Override
  public Configuration translate(Parameter parameter)
      throws TranslationException {
    Translator translator = Translator.getInstance();

    @SuppressWarnings("unchecked")
    Map<Identifier, Parameter> params = translator.translate2Java(
        parameter, HashMap.class);

    Configuration configuration = new Configuration();

    for (Entry<Identifier, Parameter> entry : params.entrySet()) {
      ParamEnum param = translator.translate2Java(entry.getKey(),
          ParamEnum.class);
      switch (param) {
        case STAKEHOLDER:
          configuration.setStakeholder(translator.translate2Java(
              entry.getValue(), Integer.class));
          break;
        case MAP:
          configuration.setMap(translator.translate2Java(entry.getValue(),
              String.class));
          break;
        case SLOT:
          configuration.setSlot(translator.translate2Java(entry.getValue(),
              Integer.class));
          break;
        default:
          break;
      }
    }

    return configuration;
  }

  @Override
  public Class<Configuration> translatesTo() {
    return Configuration.class;
  }

}
