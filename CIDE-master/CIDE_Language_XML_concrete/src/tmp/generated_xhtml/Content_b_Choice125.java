package tmp.generated_xhtml;

import cide.gast.*;
import cide.gparser.*;
import cide.greferences.*;
import java.util.*;

public class Content_b_Choice125 extends Content_b_Choice1 {
  public Content_b_Choice125(Element_sub element_sub, Token firstToken, Token lastToken) {
    super(new Property[] {
      new PropertyOne<Element_sub>("element_sub", element_sub)
    }, firstToken, lastToken);
  }
  public Content_b_Choice125(Property[] properties, IToken firstToken, IToken lastToken) {
    super(properties,firstToken,lastToken);
  }
  public ASTNode deepCopy() {
    return new Content_b_Choice125(cloneProperties(),firstToken,lastToken);
  }
  public Element_sub getElement_sub() {
    return ((PropertyOne<Element_sub>)getProperty("element_sub")).getValue();
  }
}