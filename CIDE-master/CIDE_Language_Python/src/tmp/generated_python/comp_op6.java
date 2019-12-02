package tmp.generated_python;

import cide.gast.*;
import cide.gparser.*;
import cide.greferences.*;
import java.util.*;

public class comp_op6 extends comp_op {
  public comp_op6(ASTStringNode lessgreater, Token firstToken, Token lastToken) {
    super(new Property[] {
      new PropertyOne<ASTStringNode>("lessgreater", lessgreater)
    }, firstToken, lastToken);
  }
  public comp_op6(Property[] properties, IToken firstToken, IToken lastToken) {
    super(properties,firstToken,lastToken);
  }
  public IASTNode deepCopy() {
    return new comp_op6(cloneProperties(),firstToken,lastToken);
  }
  public ASTStringNode getLessgreater() {
    return ((PropertyOne<ASTStringNode>)getProperty("lessgreater")).getValue();
  }
}
