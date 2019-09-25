/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.utilint;
import de.ovgu.cide.jakutil.*;

// line 3 "../../../../TestHookExecute.ump"
public class TestHookExecute
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TestHookExecute()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 7 "../../../../TestHookExecute.ump"
   public static  boolean doHookIfSet(TestHook testHook){
    if (testHook != null) {
	    testHook.doHook();
	}
	return true;
  }

}