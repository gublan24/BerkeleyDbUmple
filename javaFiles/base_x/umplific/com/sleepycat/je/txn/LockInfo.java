/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.txn;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.Tracer;
import java.util.WeakHashMap;
import java.util.Map;
import java.util.Collections;
import com.sleepycat.je.log.entry.*;

// line 3 "../../../../LockInfo.ump"
// line 3 "../../../../LockInfo_static.ump"
public class LockInfo implements Cloneable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LockInfo()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Called when the je.txn.deadlockStackTrace property is changed.
   */
  // line 23 "../../../../LockInfo.ump"
   static  void setDeadlockStackTrace(boolean enable){
    deadlockStackTrace = enable;
  }


  /**
   * 
   * For unit testing only.
   */
  // line 30 "../../../../LockInfo.ump"
   public static  boolean getDeadlockStackTrace(){
    return deadlockStackTrace;
  }


  /**
   * 
   * Construct a new LockInfo.  public for Sizeof program.
   */
  // line 37 "../../../../LockInfo.ump"
   public  LockInfo(Locker locker, LockType lockType){
    this.locker = locker;
	this.lockType = lockType;
	if (deadlockStackTrace) {
	    traceExceptionMap.put(this, new StackTraceAtLockTime());
	}
  }


  /**
   * 
   * Change this lockInfo over to the prescribed locker.
   */
  // line 48 "../../../../LockInfo.ump"
  public void setLocker(Locker locker){
    this.locker = locker;
  }


  /**
   * 
   * @return The transaction associated with this Lock.
   */
  // line 55 "../../../../LockInfo.ump"
  public Locker getLocker(){
    return locker;
  }


  /**
   * 
   * @return The LockType associated with this Lock.
   */
  // line 62 "../../../../LockInfo.ump"
  public void setLockType(LockType lockType){
    this.lockType = lockType;
  }


  /**
   * 
   * @return The LockType associated with this Lock.
   */
  // line 69 "../../../../LockInfo.ump"
  public LockType getLockType(){
    return lockType;
  }

  // line 73 "../../../../LockInfo.ump"
   public Object clone() throws CloneNotSupportedException{
    return super.clone();
  }


  /**
   * 
   * Debugging
   */
  // line 80 "../../../../LockInfo.ump"
   public void dump(){
    System.out.println(this);
  }

  // line 84 "../../../../LockInfo.ump"
   public String toString(){
    StringBuffer buf = new StringBuffer(500);
	buf.append("<LockInfo locker=\"");
	buf.append(locker);
	buf.append("\" type=\"");
	buf.append(lockType);
	buf.append("\"/>");
	if (deadlockStackTrace) {
	    Exception traceException = (Exception) traceExceptionMap.get(this);
	    if (traceException != null) {
		buf.append(" lock taken at: ");
		buf.append(Tracer.getStackTrace(traceException));
	    }
	}
	return buf.toString();
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 11 "../../../../LockInfo.ump"
  private Locker locker ;
// line 13 "../../../../LockInfo.ump"
  private LockType lockType ;
// line 15 "../../../../LockInfo.ump"
  private static boolean deadlockStackTrace = false ;
// line 17 "../../../../LockInfo.ump"
  private static Map traceExceptionMap = Collections.synchronizedMap(new WeakHashMap()) ;

// line 4 "../../../../LockInfo_static.ump"
  static class StackTraceAtLockTime extends Exception 
  {
    
  }

  
}