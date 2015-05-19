package nl.tudelft.contextproject.eis.translators;

import eis.iilang.Identifier;
import eis.iilang.Parameter;
import eis.iilang.ParameterList;

import eis.eis2java.exception.TranslationException;
import eis.eis2java.translation.Parameter2Java;

import java.util.HashMap;

@SuppressWarnings("rawtypes")
public class HashMapTranslator implements Parameter2Java<HashMap> {

  @Override
  public HashMap translate(Parameter parameter) throws TranslationException {
    if (!(parameter instanceof ParameterList)) {
      throw new TranslationException("Hashmap Translation on an object that is not a list");
    }
    
    return translateEntries((ParameterList) parameter);
  }

  private HashMap translateEntries(ParameterList parameter) throws TranslationException {
    HashMap<Identifier, Parameter> map = new HashMap<Identifier, Parameter>();
    
    for (Parameter entry : parameter) {
      if (!(entry instanceof ParameterList)) {
        throw new TranslationException("Hashmap Translation on an object that is not a list");
      }
      
      ParameterList entryList = (ParameterList) entry;
      
      Parameter key = entryList.get(0);
      Parameter value = entryList.get(1);

      map.put((Identifier) key,value);
    }
    
    return map;
  }

  @Override
  public Class<HashMap> translatesTo() {
    return HashMap.class;
  }

}
