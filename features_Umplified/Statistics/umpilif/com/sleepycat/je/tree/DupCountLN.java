/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.tree;

// line 3 "../../../../DupCountLN.ump"
public class DupCountLN
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DupCountLN()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 6 "../../../../DupCountLN.ump"
   public void accumulateStats(TreeWalkerStatsAccumulator acc){
    acc.processDupCountLN(this, new Long(getNodeId()));
  }

}