/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.latch;
import de.ovgu.cide.jakutil.*;
import java.io.Serializable;
import com.sleepycat.je.log.entry.*;
import com.sleepycat.bind.serial.*;

// line 3 "../../../../Derivative_Latches_Statistics_LatchStats.ump"
public class LatchStats implements Cloneable,Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LatchStats()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 46 "../../../../Derivative_Latches_Statistics_LatchStats.ump"
   public String toString(){
    StringBuffer sb = new StringBuffer();
	sb.append("nAcquiresNoWaiters=").append(nAcquiresNoWaiters).append('\n');
	sb.append("nAcquiresSelfOwned=").append(nAcquiresSelfOwned).append('\n');
	sb.append("nAcquiresUpgrade=").append(nAcquiresUpgrade).append('\n');
	sb.append("nAcquiresWithContention=").append(nAcquiresWithContention).append('\n');
	sb.append("nAcquiresNoWaitSuccessful=").append(nAcquireNoWaitSuccessful).append('\n');
	sb.append("nAcquiresNoWaitUnSuccessful=").append(nAcquireNoWaitUnsuccessful).append('\n');
	sb.append("nAcquiresSharedSuccessful=").append(nAcquireSharedSuccessful).append('\n');
	return sb.toString();
  }

  // line 58 "../../../../Derivative_Latches_Statistics_LatchStats.ump"
   public Object clone() throws CloneNotSupportedException{
    return super.clone();
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 8 "../../../../Derivative_Latches_Statistics_LatchStats.ump"
  public int nAcquiresNoWaiters = 0 ;
// line 13 "../../../../Derivative_Latches_Statistics_LatchStats.ump"
  public int nAcquiresSelfOwned = 0 ;
// line 18 "../../../../Derivative_Latches_Statistics_LatchStats.ump"
  public int nAcquiresUpgrade = 0 ;
// line 23 "../../../../Derivative_Latches_Statistics_LatchStats.ump"
  public int nAcquiresWithContention = 0 ;
// line 28 "../../../../Derivative_Latches_Statistics_LatchStats.ump"
  public int nAcquireNoWaitSuccessful = 0 ;
// line 33 "../../../../Derivative_Latches_Statistics_LatchStats.ump"
  public int nAcquireNoWaitUnsuccessful = 0 ;
// line 38 "../../../../Derivative_Latches_Statistics_LatchStats.ump"
  public int nAcquireSharedSuccessful = 0 ;
// line 43 "../../../../Derivative_Latches_Statistics_LatchStats.ump"
  public int nReleases = 0 ;

  
}