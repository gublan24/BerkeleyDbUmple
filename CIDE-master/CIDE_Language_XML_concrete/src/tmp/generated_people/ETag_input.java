package tmp.generated_people;

import cide.gast.*;
import cide.gparser.*;
import cide.greferences.*;
import java.util.*;

public class ETag_input extends GenASTNode {
  public ETag_input(Token firstToken, Token lastToken) {
    super(new Property[] {
    }, firstToken, lastToken);
  }
  public ETag_input(Property[] properties, IToken firstToken, IToken lastToken) {
    super(properties,firstToken,lastToken);
  }
  public ASTNode deepCopy() {
    return new ETag_input(cloneProperties(),firstToken,lastToken);
  }
}
