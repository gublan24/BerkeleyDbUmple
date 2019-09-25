/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;
import de.ovgu.cide.jakutil.*;

// line 3 "../../../../GetMode.ump"
public class GetMode
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public GetMode()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 23 "../../../../GetMode.ump"
   private  GetMode(String name, boolean forward){
    this.name = name;
	this.forward = forward;
  }

  // line 32 "../../../../GetMode.ump"
   public String toString(){
    return name;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 6 "../../../../GetMode.ump"
  private String name ;
// line 8 "../../../../GetMode.ump"
  private boolean forward ;
// line 10 "../../../../GetMode.ump"
  public static final GetMode NEXT = new GetMode("NEXT", true) ;
// line 12 "../../../../GetMode.ump"
  public static final GetMode PREV = new GetMode("PREV", false) ;
// line 14 "../../../../GetMode.ump"
  public static final GetMode NEXT_DUP = new GetMode("NEXT_DUP", true) ;
// line 16 "../../../../GetMode.ump"
  public static final GetMode PREV_DUP = new GetMode("PREV_DUP", false) ;
// line 18 "../../../../GetMode.ump"
  public static final GetMode NEXT_NODUP = new GetMode("NEXT_NODUP", true) ;
// line 20 "../../../../GetMode.ump"
  public static final GetMode PREV_NODUP = new GetMode("PREV_NODUP", false) ;

// line 27 "../../../../GetMode.ump"
  public final boolean isForward () 
  {
    return forward;
  }

  
}