package tmp.generated_xhtml;

import cide.gast.*;
import cide.gparser.*;
import cide.greferences.*;
import java.util.*;

public class Content_big_Choice121 extends Content_big_Choice1 {
  public Content_big_Choice121(Element_var element_var, Token firstToken, Token lastToken) {
    super(new Property[] {
      new PropertyOne<Element_var>("element_var", element_var)
    }, firstToken, lastToken);
  }
  public Content_big_Choice121(Property[] properties, IToken firstToken, IToken lastToken) {
    super(properties,firstToken,lastToken);
  }
  public ASTNode deepCopy() {
    return new Content_big_Choice121(cloneProperties(),firstToken,lastToken);
  }
  public Element_var getElement_var() {
    return ((PropertyOne<Element_var>)getProperty("element_var")).getValue();
  }
}
