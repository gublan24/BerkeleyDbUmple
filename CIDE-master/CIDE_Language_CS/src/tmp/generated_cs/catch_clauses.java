package tmp.generated_cs;

import cide.gast.*;
import cide.gparser.*;
import cide.greferences.*;
import java.util.*;

public class catch_clauses extends GenASTNode {
  public catch_clauses(ArrayList<catch_clause> catch_clause, Token firstToken, Token lastToken) {
    super(new Property[] {
      new PropertyOneOrMore<catch_clause>("catch_clause", catch_clause)
    }, firstToken, lastToken);
  }
  public catch_clauses(Property[] properties, IToken firstToken, IToken lastToken) {
    super(properties,firstToken,lastToken);
  }
  public IASTNode deepCopy() {
    return new catch_clauses(cloneProperties(),firstToken,lastToken);
  }
  public ArrayList<catch_clause> getCatch_clause() {
    return ((PropertyOneOrMore<catch_clause>)getProperty("catch_clause")).getValue();
  }
}
