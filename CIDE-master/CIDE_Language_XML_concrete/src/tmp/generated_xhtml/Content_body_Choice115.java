package tmp.generated_xhtml;

import cide.gast.*;
import cide.gparser.*;
import cide.greferences.*;
import java.util.*;

public class Content_body_Choice115 extends Content_body_Choice1 {
  public Content_body_Choice115(Element_address element_address, Token firstToken, Token lastToken) {
    super(new Property[] {
      new PropertyOne<Element_address>("element_address", element_address)
    }, firstToken, lastToken);
  }
  public Content_body_Choice115(Property[] properties, IToken firstToken, IToken lastToken) {
    super(properties,firstToken,lastToken);
  }
  public ASTNode deepCopy() {
    return new Content_body_Choice115(cloneProperties(),firstToken,lastToken);
  }
  public Element_address getElement_address() {
    return ((PropertyOne<Element_address>)getProperty("element_address")).getValue();
  }
}
