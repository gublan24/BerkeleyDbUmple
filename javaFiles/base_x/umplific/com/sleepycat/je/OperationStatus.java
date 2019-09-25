/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;
import de.ovgu.cide.jakutil.*;

// line 3 "../../../OperationStatus.ump"
public class OperationStatus
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public OperationStatus()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 29 "../../../OperationStatus.ump"
   private  OperationStatus(String statusName){
    this.statusName = statusName;
  }

  // line 33 "../../../OperationStatus.ump"
   public String toString(){
    return "OperationStatus." + statusName;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 9 "../../../OperationStatus.ump"
  public static final OperationStatus SUCCESS = new OperationStatus("SUCCESS") ;
// line 14 "../../../OperationStatus.ump"
  public static final OperationStatus KEYEXIST = new OperationStatus("KEYEXIST") ;
// line 19 "../../../OperationStatus.ump"
  public static final OperationStatus KEYEMPTY = new OperationStatus("KEYEMPTY") ;
// line 24 "../../../OperationStatus.ump"
  public static final OperationStatus NOTFOUND = new OperationStatus("NOTFOUND") ;
// line 26 "../../../OperationStatus.ump"
  private String statusName ;

  
}