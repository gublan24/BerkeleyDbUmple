/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;
import de.ovgu.cide.jakutil.*;

// line 3 "../../../ForeignKeyDeleteAction.ump"
public class ForeignKeyDeleteAction
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ForeignKeyDeleteAction()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 24 "../../../ForeignKeyDeleteAction.ump"
   private  ForeignKeyDeleteAction(String name){
    this.name = name;
  }

  // line 28 "../../../ForeignKeyDeleteAction.ump"
   public String toString(){
    return "ForeignKeyDeleteAction." + name;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 6 "../../../ForeignKeyDeleteAction.ump"
  private String name ;
// line 11 "../../../ForeignKeyDeleteAction.ump"
  public final static ForeignKeyDeleteAction ABORT = new ForeignKeyDeleteAction("ABORT") ;
// line 16 "../../../ForeignKeyDeleteAction.ump"
  public final static ForeignKeyDeleteAction CASCADE = new ForeignKeyDeleteAction("CASCADE") ;
// line 21 "../../../ForeignKeyDeleteAction.ump"
  public final static ForeignKeyDeleteAction NULLIFY = new ForeignKeyDeleteAction("NULLIFY") ;

  
}