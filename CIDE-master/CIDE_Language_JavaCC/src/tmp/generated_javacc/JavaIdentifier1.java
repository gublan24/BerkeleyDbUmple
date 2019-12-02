package tmp.generated_javacc;

import cide.gast.*;
import cide.gparser.*;
import cide.greferences.*;
import java.util.*;

public class JavaIdentifier1 extends JavaIdentifier {
  public JavaIdentifier1(ASTStringNode identifier, Token firstToken, Token lastToken) {
    super(new Property[] {
      new PropertyOne<ASTStringNode>("identifier", identifier)
    }, firstToken, lastToken);
  }
  public JavaIdentifier1(Property[] properties, IToken firstToken, IToken lastToken) {
    super(properties,firstToken,lastToken);
  }
  public IASTNode deepCopy() {
    return new JavaIdentifier1(cloneProperties(),firstToken,lastToken);
  }
  public ASTStringNode getIdentifier() {
    return ((PropertyOne<ASTStringNode>)getProperty("identifier")).getValue();
  }
}
