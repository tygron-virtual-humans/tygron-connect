package nl.tudelft.contextproject.tygron.eis.translators;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

import eis.eis2java.exception.TranslationException;
import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Parameter;
import eis.iilang.ParameterList;

public class HashMapTranslatorTest {

  HashMapTranslator translator;
  
  @Before
  public void init_test() {
    translator = new HashMapTranslator();
  }
  
  @Test
  public void test_translate() throws TranslationException {
    ParameterList parameters = new ParameterList();
    parameters.add(new Identifier("stakeholder"));
    parameters.add(new Numeral(1));
    ParameterList list = new ParameterList();
    list.add(parameters);
    
    @SuppressWarnings("unchecked")
    HashMap<Identifier,Parameter> result = translator.translate(list);
    
    Entry<Identifier, Parameter> entry = result.entrySet().iterator().next();
    
    assertEquals(new Identifier("stakeholder"),entry.getKey());
    assertEquals(new Numeral(1),entry.getValue());
    
  }
  
  @Test(expected = TranslationException.class)
  public void test_translate_noInnerList() throws TranslationException {
    ParameterList parameters = new ParameterList();
    parameters.add(new Identifier("stakeholder"));
    parameters.add(new Numeral(1));
    translator.translate(parameters);
  }
  
  @Test(expected = TranslationException.class)
  public void test_translate_noList() throws TranslationException {
    Parameter param = new Identifier("stakeholder");
    translator.translate(param);
  }

  @Test
  public void test_translatesTo() {
    assertEquals(HashMap.class, translator.translatesTo());
  }
}
