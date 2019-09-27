/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.latch;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.dbi.EnvironmentImpl;

// line 3 "../../../../LatchSupport.ump"
public class LatchSupport
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LatchSupport()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 16 "../../../../LatchSupport.ump"
   public static  Class getJava5LatchClass(){
    return JAVA5_LATCH_CLASS;
  }

  // line 20 "../../../../LatchSupport.ump"
   public static  Latch makeLatch(String name, EnvironmentImpl env){
    if (JAVA5_LATCH_CLASS == null) {
	    return new LatchImpl(name, env);
	} else {
	    try {
		Latch ret = (Latch) JAVA5_LATCH_CLASS.newInstance();
		ret.setName(name);
		return ret;
	    } catch (InstantiationException IE) {
	    } catch (IllegalAccessException IAE) {
	    }
	    JAVA5_LATCH_CLASS = null;
	    return new LatchImpl(name, env);
	}
  }

  // line 36 "../../../../LatchSupport.ump"
   public static  Latch makeLatch(EnvironmentImpl env){
    if (JAVA5_LATCH_CLASS == null) {
	    return new LatchImpl(env);
	} else {
	    try {
		return (Latch) JAVA5_LATCH_CLASS.newInstance();
	    } catch (InstantiationException IE) {
	    } catch (IllegalAccessException IAE) {
	    }
	    JAVA5_LATCH_CLASS = null;
	    return new LatchImpl(env);
	}
  }

  // line 50 "../../../../LatchSupport.ump"
   public static  SharedLatch makeSharedLatch(String name, EnvironmentImpl env){
    if (JAVA5_SHARED_LATCH_CLASS == null) {
	    return new SharedLatchImpl(name, env);
	} else {
	    try {
		SharedLatch ret = (SharedLatch) JAVA5_SHARED_LATCH_CLASS.newInstance();
		ret.setName(name);
		return ret;
	    } catch (InstantiationException IE) {
	    } catch (IllegalAccessException IAE) {
	    }
	    JAVA5_SHARED_LATCH_CLASS = null;
	    return new SharedLatchImpl(name, env);
	}
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 7 "../../../../LatchSupport.ump"
  private static String DISABLE_JAVA5_LATCHES = "je.disable.java5.latches" ;
// line 9 "../../../../LatchSupport.ump"
  private static Class JAVA5_LATCH_CLASS = null ;
// line 11 "../../../../LatchSupport.ump"
  private static Class JAVA5_SHARED_LATCH_CLASS = null ;
// line 13 "../../../../LatchSupport.ump"
  static LatchTable latchTable = new LatchTable("LatchImpl") ;

// line 68 "../../../../LatchSupport.ump"
  static public int countLatchesHeld () 
  {
    return latchTable.countLatchesHeld();
  }

// line 72 "../../../../LatchSupport.ump"
  static public void dumpLatchesHeld () 
  {
    System.out.println(latchesHeldToString());
  }

// line 76 "../../../../LatchSupport.ump"
  static public String latchesHeldToString () 
  {
    return latchTable.latchesHeldToString();
  }

// line 80 "../../../../LatchSupport.ump"
  static public void clearNotes () 
  {
    latchTable.clearNotes();
  }

  
}