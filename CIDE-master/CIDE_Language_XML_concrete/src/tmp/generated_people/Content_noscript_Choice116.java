package tmp.generated_people;

import cide.gast.*;
import cide.gparser.*;
import cide.greferences.*;
import java.util.*;

public class Content_noscript_Choice116 extends Content_noscript_Choice1 {
  public Content_noscript_Choice116(Element_fieldset element_fieldset, Token firstToken, Token lastToken) {
    super(new Property[] {
      new PropertyOne<Element_fieldset>("element_fieldset", element_fieldset)
    }, firstToken, lastToken);
  }
  public Content_noscript_Choice116(Property[] properties, IToken firstToken, IToken lastToken) {
    super(properties,firstToken,lastToken);
  }
  public ASTNode deepCopy() {
    return new Content_noscript_Choice116(cloneProperties(),firstToken,lastToken);
  }
  public Element_fieldset getElement_fieldset() {
    return ((PropertyOne<Element_fieldset>)getProperty("element_fieldset")).getValue();
  }
}