package tmp.generated_xhtml;

import cide.gast.*;
import cide.gparser.*;
import cide.greferences.*;
import java.util.*;

public class Content_caption_Choice19 extends Content_caption_Choice1 {
  public Content_caption_Choice19(Element_tt element_tt, Token firstToken, Token lastToken) {
    super(new Property[] {
      new PropertyOne<Element_tt>("element_tt", element_tt)
    }, firstToken, lastToken);
  }
  public Content_caption_Choice19(Property[] properties, IToken firstToken, IToken lastToken) {
    super(properties,firstToken,lastToken);
  }
  public ASTNode deepCopy() {
    return new Content_caption_Choice19(cloneProperties(),firstToken,lastToken);
  }
  public Element_tt getElement_tt() {
    return ((PropertyOne<Element_tt>)getProperty("element_tt")).getValue();
  }
}
