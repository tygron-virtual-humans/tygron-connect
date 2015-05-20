package nl.tudelft.contextproject.tygron.eis.translators;

import eis.iilang.Identifier;
import eis.iilang.Parameter;
import nl.tudelft.contextproject.tygron.eis.ParamEnum;
import eis.eis2java.exception.TranslationException;
import eis.eis2java.translation.Parameter2Java;

public class ParamEnumTranslator implements Parameter2Java<ParamEnum> {

  @Override
  public ParamEnum translate(Parameter parameter) throws TranslationException {
    if (!(parameter instanceof Identifier)) {
      throw new TranslationException();
    }
    String id = ((Identifier) parameter).getValue();
    
    for (ParamEnum params : ParamEnum.values()) {
      if (params.getParam().equals(id)) {
        return params;
      }
    }
    
    throw new TranslationException();
  }

  @Override
  public Class<ParamEnum> translatesTo() {
    return ParamEnum.class;
  }

}
