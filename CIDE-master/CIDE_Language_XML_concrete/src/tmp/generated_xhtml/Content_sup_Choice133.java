package tmp.generated_xhtml;

import cide.gast.*;
import cide.gparser.*;
import cide.greferences.*;
import java.util.*;

public class Content_sup_Choice133 extends Content_sup_Choice1 {
  public Content_sup_Choice133(Element_del element_del, Token firstToken, Token lastToken) {
    super(new Property[] {
      new PropertyOne<Element_del>("element_del", element_del)
    }, firstToken, lastToken);
  }
  public Content_sup_Choice133(Property[] properties, IToken firstToken, IToken lastToken) {
    super(properties,firstToken,lastToken);
  }
  public ASTNode deepCopy() {
    return new Content_sup_Choice133(cloneProperties(),firstToken,lastToken);
  }
  public Element_del getElement_del() {
    return ((PropertyOne<Element_del>)getProperty("element_del")).getValue();
  }
}
