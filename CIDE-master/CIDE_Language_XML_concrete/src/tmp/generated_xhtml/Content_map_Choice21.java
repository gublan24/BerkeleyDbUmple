package tmp.generated_xhtml;

import cide.gast.*;
import cide.gparser.*;
import cide.greferences.*;
import java.util.*;

public class Content_map_Choice21 extends Content_map_Choice2 {
  public Content_map_Choice21(Element_p element_p, Token firstToken, Token lastToken) {
    super(new Property[] {
      new PropertyOne<Element_p>("element_p", element_p)
    }, firstToken, lastToken);
  }
  public Content_map_Choice21(Property[] properties, IToken firstToken, IToken lastToken) {
    super(properties,firstToken,lastToken);
  }
  public ASTNode deepCopy() {
    return new Content_map_Choice21(cloneProperties(),firstToken,lastToken);
  }
  public Element_p getElement_p() {
    return ((PropertyOne<Element_p>)getProperty("element_p")).getValue();
  }
}
