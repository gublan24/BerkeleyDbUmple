/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.tree;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.DbLsn;

// line 3 "../../../../TrackingInfo.ump"
public class TrackingInfo
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TrackingInfo()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 12 "../../../../TrackingInfo.ump"
   public  TrackingInfo(long lsn, long nodeId){
    this.lsn = lsn;
	this.nodeId = nodeId;
  }

  // line 17 "../../../../TrackingInfo.ump"
   public String toString(){
    return "lsn=" + DbLsn.getNoFormatString(lsn) + " node=" + nodeId;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 7 "../../../../TrackingInfo.ump"
  private long lsn ;
// line 9 "../../../../TrackingInfo.ump"
  private long nodeId ;

  
}