package tmp.generated_xhtml;

import cide.gast.*;
import cide.gparser.*;
import cide.greferences.*;
import java.util.*;

public class Content_strong_Choice120 extends Content_strong_Choice1 {
  public Content_strong_Choice120(Element_kbd element_kbd, Token firstToken, Token lastToken) {
    super(new Property[] {
      new PropertyOne<Element_kbd>("element_kbd", element_kbd)
    }, firstToken, lastToken);
  }
  public Content_strong_Choice120(Property[] properties, IToken firstToken, IToken lastToken) {
    super(properties,firstToken,lastToken);
  }
  public ASTNode deepCopy() {
    return new Content_strong_Choice120(cloneProperties(),firstToken,lastToken);
  }
  public Element_kbd getElement_kbd() {
    return ((PropertyOne<Element_kbd>)getProperty("element_kbd")).getValue();
  }
}