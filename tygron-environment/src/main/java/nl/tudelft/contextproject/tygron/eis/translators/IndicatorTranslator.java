package nl.tudelft.contextproject.tygron.eis.translators;

import eis.iilang.Function;
import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Parameter;
import nl.tudelft.contextproject.tygron.objects.indicators.Indicator;
import eis.eis2java.exception.TranslationException;
import eis.eis2java.translation.Java2Parameter;
import eis.eis2java.translation.Parameter2Java;
import eis.eis2java.translation.Translator;

public class IndicatorTranslator implements 
    Java2Parameter<Indicator>, Parameter2Java<Indicator> {

  /* (non-Javadoc)
   * @see eis.eis2java.translation.Parameter2Java#translate(eis.iilang.Parameter)
   */
  @Override
  public Indicator translate(Parameter parameter) throws TranslationException {
    return Translator.getInstance().translate2Java(parameter, Indicator.class);
  }

  /* (non-Javadoc)
   * @see eis.eis2java.translation.Java2Parameter#translate(java.lang.Object)
   */
  @Override
  public Parameter[] translate(Indicator ind) throws TranslationException {
    return new Parameter[] { new Function(
          "indicator",
          new Identifier(ind.getType().toLowerCase()),
          new Identifier(ind.getName().toLowerCase()),
          new Numeral(ind.getProgress())
        )};
  }
  
  /* (non-Javadoc)
   * @see eis.eis2java.translation.Parameter2Java#translatesTo()
   */
  @Override
  public Class<Indicator> translatesTo() {
    return Indicator.class;
  }

  /* (non-Javadoc)
   * @see eis.eis2java.translation.Java2Parameter#translatesFrom()
   */
  @Override
  public Class<? extends Indicator> translatesFrom() {
    return Indicator.class;
  }

}
