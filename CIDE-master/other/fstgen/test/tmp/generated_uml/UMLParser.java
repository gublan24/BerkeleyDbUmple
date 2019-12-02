/* Generated By:JavaCC: Do not edit this line. UMLParser.java */
package tmp.generated_uml;

import java.io.*;
import java.util.*;
import cide.gast.*;
import cide.gparser.*;
import de.ovgu.cide.fstgen.ast.AbstractFSTParser;

public class UMLParser extends AbstractFSTParser implements UMLParserConstants {

  final public FSTInfo Document(boolean inTerminal) throws ParseException {
                                         Token first=null,t;FSTInfo n;
     first=getToken(1); productionStart(inTerminal);
    n = Prolog(inTerminal);
                              replaceName(n);
    n = Element(inTerminal);
                                                                      replaceName(n);
    label_1:
    while (true) {
      if (jj_2_1(1)) {
        ;
      } else {
        break label_1;
      }
      n = Misc(inTerminal);
                                                                                                            replaceName(n);
    }
                                                                                                                                {if (true) return productionEndNonTerminal("Document","-","-");}
    throw new Error("Missing return statement in function");
  }

  final public FSTInfo Misc(boolean inTerminal) throws ParseException {
                                     Token first=null,t;FSTInfo n;
     first=getToken(1); productionStart(inTerminal);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case COMMENT_START:
      n = Comment(true);
                         replaceName(n);
                                           {if (true) return productionEndTerminal("Misc1","{AUTO}","{AUTO}","error",first,token);}
      break;
    default:
      jj_la1[0] = jj_gen;
      if (getToken(1).image.trim().equals("")) {
        jj_consume_token(PCDATA);
                                                                   {if (true) return productionEndTerminal("Misc2","{AUTO}","{AUTO}","error",first,token);}
      } else {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case PI_START:
          n = PI(true);
                    replaceName(n);
                                      {if (true) return productionEndTerminal("Misc3","{AUTO}","{AUTO}","error",first,token);}
          break;
        default:
          jj_la1[1] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
    }
    throw new Error("Missing return statement in function");
  }

  final public FSTInfo PI(boolean inTerminal) throws ParseException {
                                   Token first=null,t;FSTInfo n;
     first=getToken(1); productionStart(inTerminal);
    jj_consume_token(PI_START);
    jj_consume_token(PI_END);
                       {if (true) return productionEndTerminal("PI","-","-","error",first,token);}
    throw new Error("Missing return statement in function");
  }

  final public FSTInfo Prolog(boolean inTerminal) throws ParseException {
                                       Token first=null,t;FSTInfo n;
     first=getToken(1); productionStart(inTerminal);
    n = XMLDecl(true);
                         replaceName(n);
    label_2:
    while (true) {
      if (jj_2_2(1)) {
        ;
      } else {
        break label_2;
      }
      n = Misc(true);
                                                         replaceName(n);
    }
                                                                             {if (true) return productionEndTerminal("Prolog","-","-","error",first,token);}
    throw new Error("Missing return statement in function");
  }

  final public FSTInfo XMLDecl(boolean inTerminal) throws ParseException {
                                        Token first=null,t;FSTInfo n;
     first=getToken(1); productionStart(inTerminal);
    jj_consume_token(XMLOPEN);
    label_3:
    while (true) {
      n = Attribute(true);
                                    replaceName(n);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ATTR_NAME:
        ;
        break;
      default:
        jj_la1[2] = jj_gen;
        break label_3;
      }
    }
    jj_consume_token(QEND);
                                                               {if (true) return productionEndTerminal("XMLDecl","-","-","error",first,token);}
    throw new Error("Missing return statement in function");
  }

  final public FSTInfo CDSect(boolean inTerminal) throws ParseException {
                                       Token first=null,t;FSTInfo n;
     first=getToken(1); productionStart(inTerminal);
    jj_consume_token(CDSTART);
    jj_consume_token(CDEND);
                             {if (true) return productionEndTerminal("CDSect","-","-","error",first,token);}
    throw new Error("Missing return statement in function");
  }

  final public FSTInfo Comment(boolean inTerminal) throws ParseException {
                                        Token first=null,t;FSTInfo n;
     first=getToken(1); productionStart(inTerminal);
    jj_consume_token(COMMENT_START);
    jj_consume_token(COMMENT_END);
                              {if (true) return productionEndTerminal("Comment","-","-","error",first,token);}
    throw new Error("Missing return statement in function");
  }

  final public FSTInfo Element(boolean inTerminal) throws ParseException {
                                        Token first=null,t;FSTInfo n;
     first=getToken(1); productionStart(inTerminal);
    if (jj_2_3(2147483647)) {
      n = EmptyElemTag(inTerminal);
                                                              replaceName("EmptyElemTag", n);
                                                                                                replaceName(n);
                                                                                                                  {if (true) return productionEndNonTerminal("ElementContent1","-","{EmptyElemTag}");}
    } else {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case SELEMENT_START:
        n = STag(inTerminal);
                            replaceName("STag", n);
                                                      replaceName(n);
        label_4:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case SELEMENT_START:
          case COMMENT_START:
          case CDSTART:
          case PCDATA:
            ;
            break;
          default:
            jj_la1[3] = jj_gen;
            break label_4;
          }
          n = Content(inTerminal);
                                                                                               replaceName(n);
        }
        n = ETag(inTerminal);
                                                                                                                                      replaceName(n);
                                                                                                                                                        {if (true) return productionEndNonTerminal("ElementContent2","-","{STag}");}
        break;
      default:
        jj_la1[4] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
    throw new Error("Missing return statement in function");
  }

  final public FSTInfo ElementId(boolean inTerminal) throws ParseException {
                                          Token first=null,t;FSTInfo n;
     first=getToken(1); productionStart(inTerminal);
    t = jj_consume_token(ELEMENT_ID);
                        replaceName(new FSTInfo("<ELEMENT_ID>",t.toString()));
                                                                                 {if (true) return productionEndTerminal("ElementId","{<ELEMENT_ID>}","{<ELEMENT_ID>}","error",first,token);}
    throw new Error("Missing return statement in function");
  }

  final public FSTInfo EmptyElemTag(boolean inTerminal) throws ParseException {
                                             Token first=null,t;FSTInfo n;
     first=getToken(1); productionStart(inTerminal);
    jj_consume_token(SELEMENT_START);
    n = ElementId(inTerminal);
                                     replaceName("ElementId", n);
                                                                    replaceName(n);
    label_5:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ATTR_NAME:
        ;
        break;
      default:
        jj_la1[5] = jj_gen;
        break label_5;
      }
      n = Attribute(inTerminal);
                                                                                                               replaceName(n);
    }
    jj_consume_token(SLASHEND);
                                                                                                                                              {if (true) return productionEndNonTerminal("EmptyElemTag","{ElementId}","{ElementId}");}
    throw new Error("Missing return statement in function");
  }

  final public FSTInfo STag(boolean inTerminal) throws ParseException {
                                     Token first=null,t;FSTInfo n;
     first=getToken(1); productionStart(inTerminal);
    jj_consume_token(SELEMENT_START);
    n = ElementId(inTerminal);
                                     replaceName("ElementId", n);
                                                                    replaceName(n);
    label_6:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ATTR_NAME:
        ;
        break;
      default:
        jj_la1[6] = jj_gen;
        break label_6;
      }
      n = Attribute(inTerminal);
                                                                                                               replaceName(n);
    }
    jj_consume_token(ELEMENT_END);
                                                                                                                                                 {if (true) return productionEndNonTerminal("STag","-","{ElementId}_{Attr_name}");}
    throw new Error("Missing return statement in function");
  }

  final public FSTInfo ETag(boolean inTerminal) throws ParseException {
                                     Token first=null,t;FSTInfo n;
     first=getToken(1); productionStart(inTerminal);
    jj_consume_token(EELEMENT_START);
    n = ElementId(true);
                                replaceName(n);
    jj_consume_token(ELEMENT_END);
                                                                {if (true) return productionEndTerminal("ETag","-","-","error",first,token);}
    throw new Error("Missing return statement in function");
  }

  final public FSTInfo Attribute(boolean inTerminal) throws ParseException {
                                          Token first=null,t;FSTInfo n;
     first=getToken(1); productionStart(inTerminal);
    t = jj_consume_token(ATTR_NAME);
                       replaceName(new FSTInfo("<ATTR_NAME>",t.toString()));
    jj_consume_token(ATTR_EQ);
    t = jj_consume_token(ATTR_VAL);
                                                                                                      replaceName(new FSTInfo("<ATTR_VAL>",t.toString()));
                                                                                                                                                             {if (true) return productionEndTerminal("Attr_{<ATTR_NAME>}","{<ATTR_VAL>}","{<ATTR_VAL>}","error",first,token);}
    throw new Error("Missing return statement in function");
  }

  final public FSTInfo Content(boolean inTerminal) throws ParseException {
                                        Token first=null,t;FSTInfo n;
     first=getToken(1); productionStart(inTerminal);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case SELEMENT_START:
      n = Element(inTerminal);
                               replaceName("Element", n);
                                                            replaceName(n);
                                                                              {if (true) return productionEndNonTerminal("Element","{Element}","{Element}");}
      break;
    case COMMENT_START:
      n = Comment(true);
                         replaceName(n);
                                           {if (true) return productionEndTerminal("CommentContent","{AUTO}","{AUTO}","error",first,token);}
      break;
    case CDSTART:
      n = CDSect(true);
                        replaceName(n);
                                          {if (true) return productionEndTerminal("CDSectContent","{AUTO}","{AUTO}","error",first,token);}
      break;
    case PCDATA:
      jj_consume_token(PCDATA);
                  {if (true) return productionEndTerminal("PCDataContent","{AUTO}","{AUTO}","error",first,token);}
      break;
    default:
      jj_la1[7] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final private boolean jj_2_1(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_1(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  final private boolean jj_2_2(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_2(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(1, xla); }
  }

  final private boolean jj_2_3(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_3(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(2, xla); }
  }

  final private boolean jj_3R_14() {
    if (jj_scan_token(COMMENT_START)) return true;
    return false;
  }

  final private boolean jj_3R_15() {
    if (jj_scan_token(PI_START)) return true;
    return false;
  }

  final private boolean jj_3_2() {
    if (jj_3R_7()) return true;
    return false;
  }

  final private boolean jj_3_1() {
    if (jj_3R_7()) return true;
    return false;
  }

  final private boolean jj_3_3() {
    if (jj_3R_8()) return true;
    return false;
  }

  final private boolean jj_3R_11() {
    if (jj_3R_15()) return true;
    return false;
  }

  final private boolean jj_3R_8() {
    if (jj_scan_token(SELEMENT_START)) return true;
    if (jj_3R_12()) return true;
    Token xsp;
    while (true) {
      xsp = jj_scanpos;
      if (jj_3R_13()) { jj_scanpos = xsp; break; }
    }
    if (jj_scan_token(SLASHEND)) return true;
    return false;
  }

  final private boolean jj_3R_10() {
    if (jj_scan_token(PCDATA)) return true;
    return false;
  }

  final private boolean jj_3R_9() {
    if (jj_3R_14()) return true;
    return false;
  }

  final private boolean jj_3R_16() {
    if (jj_scan_token(ATTR_NAME)) return true;
    if (jj_scan_token(ATTR_EQ)) return true;
    if (jj_scan_token(ATTR_VAL)) return true;
    return false;
  }

  final private boolean jj_3R_12() {
    if (jj_scan_token(ELEMENT_ID)) return true;
    return false;
  }

  final private boolean jj_3R_7() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_9()) {
    jj_scanpos = xsp;
    lookingAhead = true;
    jj_semLA = getToken(1).image.trim().equals("");
    lookingAhead = false;
    if (!jj_semLA || jj_3R_10()) {
    jj_scanpos = xsp;
    if (jj_3R_11()) return true;
    }
    }
    return false;
  }

  final private boolean jj_3R_13() {
    if (jj_3R_16()) return true;
    return false;
  }

  public UMLParserTokenManager token_source;
  public Token token, jj_nt;
  private int jj_ntk;
  private Token jj_scanpos, jj_lastpos;
  private int jj_la;
  public boolean lookingAhead = false;
  private boolean jj_semLA;
  private int jj_gen;
  final private int[] jj_la1 = new int[8];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_0();
      jj_la1_1();
   }
   private static void jj_la1_0() {
      jj_la1_0 = new int[] {0x20000,0x10000,0x400000,0xe4000,0x4000,0x400000,0x400000,0xe4000,};
   }
   private static void jj_la1_1() {
      jj_la1_1 = new int[] {0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,};
   }
  final private JJCalls[] jj_2_rtns = new JJCalls[3];
  private boolean jj_rescan = false;
  private int jj_gc = 0;

  public UMLParser(CharStream stream) {
    token_source = new UMLParserTokenManager(stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 8; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  public void ReInit(CharStream stream) {
    token_source.ReInit(stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 8; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  public UMLParser(UMLParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 8; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  public void ReInit(UMLParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 8; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  final private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      if (++jj_gc > 100) {
        jj_gc = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) {
          JJCalls c = jj_2_rtns[i];
          while (c != null) {
            if (c.gen < jj_gen) c.first = null;
            c = c.next;
          }
        }
      }
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  static private final class LookaheadSuccess extends java.lang.Error { }
  final private LookaheadSuccess jj_ls = new LookaheadSuccess();
  final private boolean jj_scan_token(int kind) {
    if (jj_scanpos == jj_lastpos) {
      jj_la--;
      if (jj_scanpos.next == null) {
        jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
      } else {
        jj_lastpos = jj_scanpos = jj_scanpos.next;
      }
    } else {
      jj_scanpos = jj_scanpos.next;
    }
    if (jj_rescan) {
      int i = 0; Token tok = token;
      while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
      if (tok != null) jj_add_error_token(kind, i);
    }
    if (jj_scanpos.kind != kind) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
    return false;
  }

  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

  final public Token getToken(int index) {
    Token t = lookingAhead ? jj_scanpos : token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  final private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.Vector jj_expentries = new java.util.Vector();
  private int[] jj_expentry;
  private int jj_kind = -1;
  private int[] jj_lasttokens = new int[100];
  private int jj_endpos;

  private void jj_add_error_token(int kind, int pos) {
    if (pos >= 100) return;
    if (pos == jj_endpos + 1) {
      jj_lasttokens[jj_endpos++] = kind;
    } else if (jj_endpos != 0) {
      jj_expentry = new int[jj_endpos];
      for (int i = 0; i < jj_endpos; i++) {
        jj_expentry[i] = jj_lasttokens[i];
      }
      boolean exists = false;
      for (java.util.Enumeration e = jj_expentries.elements(); e.hasMoreElements();) {
        int[] oldentry = (int[])(e.nextElement());
        if (oldentry.length == jj_expentry.length) {
          exists = true;
          for (int i = 0; i < jj_expentry.length; i++) {
            if (oldentry[i] != jj_expentry[i]) {
              exists = false;
              break;
            }
          }
          if (exists) break;
        }
      }
      if (!exists) jj_expentries.addElement(jj_expentry);
      if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
    }
  }

  public ParseException generateParseException() {
    jj_expentries.removeAllElements();
    boolean[] la1tokens = new boolean[34];
    for (int i = 0; i < 34; i++) {
      la1tokens[i] = false;
    }
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 8; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 34; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.addElement(jj_expentry);
      }
    }
    jj_endpos = 0;
    jj_rescan_token();
    jj_add_error_token(0, 0);
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = (int[])jj_expentries.elementAt(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  final public void enable_tracing() {
  }

  final public void disable_tracing() {
  }

  final private void jj_rescan_token() {
    jj_rescan = true;
    for (int i = 0; i < 3; i++) {
    try {
      JJCalls p = jj_2_rtns[i];
      do {
        if (p.gen > jj_gen) {
          jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
          switch (i) {
            case 0: jj_3_1(); break;
            case 1: jj_3_2(); break;
            case 2: jj_3_3(); break;
          }
        }
        p = p.next;
      } while (p != null);
      } catch(LookaheadSuccess ls) { }
    }
    jj_rescan = false;
  }

  final private void jj_save(int index, int xla) {
    JJCalls p = jj_2_rtns[index];
    while (p.gen > jj_gen) {
      if (p.next == null) { p = p.next = new JJCalls(); break; }
      p = p.next;
    }
    p.gen = jj_gen + xla - jj_la; p.first = token; p.arg = xla;
  }

  static final class JJCalls {
    int gen;
    Token first;
    int arg;
    JJCalls next;
  }

}
