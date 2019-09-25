/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
package com.sleepycat.je.tree;
import de.ovgu.cide.jakutil.*;
// line 3 "../../../../TreeWalkerStatsAccumulator.ump"
public interface TreeWalkerStatsAccumulator
{
  
  public void processIN(IN node, Long nid, int level) ;

  public void processBIN(BIN node, Long nid, int level) ;

  public void processDIN(DIN node, Long nid, int level) ;

  public void processDBIN(DBIN node, Long nid, int level) ;

  public void processDupCountLN(DupCountLN node, Long nid) ;

  public void incrementLNCount() ;

  public void incrementDeletedLNCount() ;

}