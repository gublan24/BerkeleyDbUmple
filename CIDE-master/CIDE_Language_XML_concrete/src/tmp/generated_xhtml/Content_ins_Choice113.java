package tmp.generated_xhtml;

import cide.gast.*;
import cide.gparser.*;
import cide.greferences.*;
import java.util.*;

public class Content_ins_Choice113 extends Content_ins_Choice1 {
  public Content_ins_Choice113(Element_pre element_pre, Token firstToken, Token lastToken) {
    super(new Property[] {
      new PropertyOne<Element_pre>("element_pre", element_pre)
    }, firstToken, lastToken);
  }
  public Content_ins_Choice113(Property[] properties, IToken firstToken, IToken lastToken) {
    super(properties,firstToken,lastToken);
  }
  public ASTNode deepCopy() {
    return new Content_ins_Choice113(cloneProperties(),firstToken,lastToken);
  }
  public Element_pre getElement_pre() {
    return ((PropertyOne<Element_pre>)getProperty("element_pre")).getValue();
  }
}
