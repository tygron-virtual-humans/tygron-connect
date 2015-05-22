package nl.tudelft.contextproject.tygron.eis.translators;

import static org.junit.Assert.assertEquals;

import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.ParameterList;
import nl.tudelft.contextproject.tygron.eis.Configuration;

import org.junit.Before;
import org.junit.Test;

import eis.eis2java.exception.TranslationException;
import eis.eis2java.translation.Translator;


public class ConfigurationTranslatorTest {

  ConfigurationTranslator translator;
  
  /**
   * initialize the test.
   */
  @Before
  public void test_init() {
    translator = new ConfigurationTranslator();
    Translator.getInstance().registerParameter2JavaTranslator(new HashMapTranslator());
    Translator.getInstance().registerParameter2JavaTranslator(new ParamEnumTranslator());
  }
  
  @Test
  public void test() throws TranslationException {
    ParameterList parametersStakeholder = new ParameterList();
    parametersStakeholder.add(new Identifier("stakeholder"));
    parametersStakeholder.add(new Numeral(1));
    
    ParameterList parametersSlot = new ParameterList();
    parametersSlot.add(new Identifier("slot"));
    parametersSlot.add(new Numeral(1));
    
    ParameterList parametersMap = new ParameterList();
    parametersMap.add(new Identifier("map"));
    parametersMap.add(new Identifier("testmap"));
    
    ParameterList list = new ParameterList();
    list.add(parametersMap);
    list.add(parametersSlot);
    list.add(parametersStakeholder);
    
    Configuration config = translator.translate(list);

    assertEquals("testmap",config.getMap());
    assertEquals(1,config.getSlot());
    assertEquals(1,config.getStakeholder());     
  }

  @Test
  public void test_translatesTo() {
    assertEquals(Configuration.class,translator.translatesTo());
  }
  
}
