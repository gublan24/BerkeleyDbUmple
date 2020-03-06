/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.latch;
import com.sleepycat.je.dbi.EnvironmentImpl;

/**
 * 
 * Various constructs to support Latches.  Switch hitting for 1.4 vs Java 5
 * JVM latch implementation (i.e. our's vs the JVM's), and assert-based
 * latch counting code.
 */
// line 19 "../../../../Latches_LatchSupport.ump"
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

  // line 53 "../../../../Latches_LatchSupport.ump"
   public static  Class getJava5LatchClass(){
    return JAVA5_LATCH_CLASS;
  }

  // line 57 "../../../../Latches_LatchSupport.ump"
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

	    /* Something bad happened.  Revert back to our 1.4 latches. */
	    JAVA5_LATCH_CLASS = null;
	    return new LatchImpl(name, env);
	}
  }

  // line 75 "../../../../Latches_LatchSupport.ump"
   public static  Latch makeLatch(EnvironmentImpl env){
    if (JAVA5_LATCH_CLASS == null) {
	    return new LatchImpl(env);
	} else {
	    try {
		return (Latch) JAVA5_LATCH_CLASS.newInstance();
	    } catch (InstantiationException IE) {
	    } catch (IllegalAccessException IAE) {
	    }

	    /* Something bad happened.  Revert back to our 1.4 latches. */
	    JAVA5_LATCH_CLASS = null;
	    return new LatchImpl(env);
	}
  }

  // line 92 "../../../../Latches_LatchSupport.ump"
   public static  SharedLatch makeSharedLatch(String name, EnvironmentImpl env){
    if (JAVA5_SHARED_LATCH_CLASS == null) {
	    return new SharedLatchImpl(name, env);
	} else {
	    try {
		SharedLatch ret = (SharedLatch)
		    JAVA5_SHARED_LATCH_CLASS.newInstance();
		ret.setName(name);
		return ret;
	    } catch (InstantiationException IE) {
	    } catch (IllegalAccessException IAE) {
	    }

	    /* Something bad happened.  Revert back to our 1.4 latches. */
	    JAVA5_SHARED_LATCH_CLASS = null;
	    return new SharedLatchImpl(name, env);
	}
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 23 "../../../../Latches_LatchSupport.ump"
  private static String DISABLE_JAVA5_LATCHES = "je.disable.java5.latches" ;
// line 25 "../../../../Latches_LatchSupport.ump"
  private static Class JAVA5_LATCH_CLASS = null ;
// line 27 "../../../../Latches_LatchSupport.ump"
  private static Class JAVA5_SHARED_LATCH_CLASS = null ;

// line 29 "../../../../Latches_LatchSupport.ump"
  static 
  {
    try {
	    if (System.getProperty(DISABLE_JAVA5_LATCHES) == null) {
		Class.forName("java.util.concurrent.locks.ReentrantLock");
		JAVA5_LATCH_CLASS = Class.forName
		    ("com.sleepycat.je.latch.Java5LatchImpl");
	    }
	} catch (ClassNotFoundException CNFE) {
	}
  }

// line 40 "../../../../Latches_LatchSupport.ump"
  static 
  {
    try {
	    if (System.getProperty(DISABLE_JAVA5_LATCHES) == null) {
		Class.forName
		    ("java.util.concurrent.locks.ReentrantReadWriteLock");
		JAVA5_SHARED_LATCH_CLASS = Class.forName
		    ("com.sleepycat.je.latch.Java5SharedLatchImpl");
	    }
	} catch (ClassNotFoundException CNFE) {
	}
  }
// line 111 "../../../../Latches_LatchSupport.ump"
  static LatchTable latchTable = new LatchTable("LatchImpl") ;

// line 116 "../../../../Latches_LatchSupport.ump"
  static public int countLatchesHeld () 
  {
    return latchTable.countLatchesHeld();
  }

// line 121 "../../../../Latches_LatchSupport.ump"
  static public void dumpLatchesHeld () 
  {
    System.out.println(latchesHeldToString());
  }

// line 126 "../../../../Latches_LatchSupport.ump"
  static public String latchesHeldToString () 
  {
    return latchTable.latchesHeldToString();
  }

// line 131 "../../../../Latches_LatchSupport.ump"
  static public void clearNotes () 
  {
    latchTable.clearNotes();
  }

  
}