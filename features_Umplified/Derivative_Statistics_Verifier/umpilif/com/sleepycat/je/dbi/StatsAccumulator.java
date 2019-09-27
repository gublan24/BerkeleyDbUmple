/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;

// line 3 "../../../../StatsAccumulator.ump"
public class StatsAccumulator
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public StatsAccumulator()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 6 "../../../../StatsAccumulator.ump"
  public void verifyNode(Node node){
    
  }

  // line 9 "../../../../StatsAccumulator.ump"
   protected void hook363(IN node){
    verifyNode(node);
	original(node);
  }

  // line 14 "../../../../StatsAccumulator.ump"
   protected void hook364(BIN node){
    verifyNode(node);
	original(node);
  }

  // line 19 "../../../../StatsAccumulator.ump"
   protected void hook365(DIN node){
    verifyNode(node);
	original(node);
  }

  // line 24 "../../../../StatsAccumulator.ump"
   protected void hook366(DBIN node){
    verifyNode(node);
	original(node);
  }

  // line 29 "../../../../StatsAccumulator.ump"
   protected void hook367(DupCountLN node){
    verifyNode(node);
	original(node);
  }

}