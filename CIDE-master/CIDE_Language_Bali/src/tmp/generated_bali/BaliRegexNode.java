package tmp.generated_bali;

import cide.gast.*;
import cide.gparser.*;
import cide.greferences.*;
import java.util.*;

public class BaliRegexNode extends AngleRegex {
  public BaliRegexNode(ASTStringNode bali_token, Token firstToken, Token lastToken) {
    super(new Property[] {
      new PropertyOne<ASTStringNode>("bali_token", bali_token)
    }, firstToken, lastToken);
  }
  public BaliRegexNode(Property[] properties, IToken firstToken, IToken lastToken) {
    super(properties,firstToken,lastToken);
  }
  public IASTNode deepCopy() {
    return new BaliRegexNode(cloneProperties(),firstToken,lastToken);
  }
  public ASTStringNode getBali_token() {
    return ((PropertyOne<ASTStringNode>)getProperty("bali_token")).getValue();
  }
}
