package nl.tudelft.contextproject.tygron.eis.translators;

import static org.junit.Assert.assertEquals;
import nl.tudelft.contextproject.tygron.eis.ParamEnum;

import org.junit.Before;
import org.junit.Test;

import eis.eis2java.exception.TranslationException;
import eis.exceptions.ManagementException;
import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Parameter;

public class ParamEnumTranslatorTest {

  private ParamEnumTranslator translator;
 
  @Before
  public void startTests() {
    translator = new ParamEnumTranslator();
  }
  
  @Test
  public void test_translate_stakeholder() throws TranslationException {
    Parameter param = new Identifier("stakeholder");
    assertEquals(ParamEnum.STAKEHOLDER,translator.translate(param));
  }
  
  @Test
  public void test_translate_slot() throws TranslationException {
    Parameter param = new Identifier("slot");
    assertEquals(ParamEnum.SLOT,translator.translate(param));
  }
  
  @Test
  public void test_translate_map() throws TranslationException {
    Parameter param = new Identifier("map");
    assertEquals(ParamEnum.MAP,translator.translate(param));
  }
  
  @Test(expected = TranslationException.class) 
  public void test_translate_invalid() throws TranslationException {
    Parameter param = new Identifier("INVVALID");
    translator.translate(param);
  }
  
  @Test(expected = TranslationException.class) 
  public void test_translate_no_identifier() throws TranslationException {
    Parameter param = new Numeral(4);
    translator.translate(param);
  }
  
  @Test
  public void test_translatesTo() {
    assertEquals(ParamEnum.class,translator.translatesTo());
  }

}
