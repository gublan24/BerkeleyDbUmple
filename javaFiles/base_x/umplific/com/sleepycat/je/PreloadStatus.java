/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;
import de.ovgu.cide.jakutil.*;
import java.io.Serializable;

// line 3 "../../../PreloadStatus.ump"
public class PreloadStatus
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PreloadStatus()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 17 "../../../PreloadStatus.ump"
   private  PreloadStatus(String statusName){
    this.statusName = statusName;
  }

  // line 21 "../../../PreloadStatus.ump"
   public String toString(){
    return "PreloadStatus." + statusName;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 8 "../../../PreloadStatus.ump"
  private String statusName ;
// line 10 "../../../PreloadStatus.ump"
  public static final PreloadStatus SUCCESS = new PreloadStatus("SUCCESS") ;
// line 12 "../../../PreloadStatus.ump"
  public static final PreloadStatus FILLED_CACHE = new PreloadStatus("FILLED_CACHE") ;
// line 14 "../../../PreloadStatus.ump"
  public static final PreloadStatus EXCEEDED_TIME = new PreloadStatus("EXCEEDED_TIME") ;

  
}