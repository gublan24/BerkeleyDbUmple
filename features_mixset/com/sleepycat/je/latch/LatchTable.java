/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.latch;
import de.ovgu.cide.jakutil.*;
import java.util.WeakHashMap;
import java.util.Set;
import java.util.Map;
import java.util.List;
import java.util.Iterator;
import java.util.HashSet;
import java.util.Collections;

// line 3 "../../../../Latches_LatchTable.ump"
public class LatchTable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LatchTable()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 18 "../../../../Latches_LatchTable.ump"
  public  LatchTable(String tableName){
    this.tableName = tableName;
	latchesByThread = Collections.synchronizedMap(new WeakHashMap());
  }


  /**
   * 
   * Only call under the assert system. This records latching by thread.
   */
  // line 26 "../../../../Latches_LatchTable.ump"
  public boolean noteLatch(Object latch) throws LatchException{
    Thread cur = Thread.currentThread();
	Set threadLatches = (Set) latchesByThread.get(cur);
	if (threadLatches == null) {
	    threadLatches = new HashSet();
	    latchesByThread.put(cur, threadLatches);
	}
	threadLatches.add(latch);
	return true;
  }


  /**
   * 
   * Only call under the assert system. This records latching by thread.
   * @return true if unnoted successfully.
   */
  // line 41 "../../../../Latches_LatchTable.ump"
  public boolean unNoteLatch(Object latch, String name){
    Thread cur = Thread.currentThread();
	Set threadLatches = (Set) latchesByThread.get(cur);
	if (threadLatches == null) {
	    return false;
	} else {
	    return threadLatches.remove(latch);
	}
  }


  /**
   * 
   * Only call under the assert system. This counts held latches.
   */
  // line 54 "../../../../Latches_LatchTable.ump"
  public int countLatchesHeld(){
    Thread cur = Thread.currentThread();
	Set threadLatches = (Set) latchesByThread.get(cur);
	if (threadLatches != null) {
	    return threadLatches.size();
	} else {
	    return 0;
	}
  }

  // line 64 "../../../../Latches_LatchTable.ump"
  public String latchesHeldToString(){
    Thread cur = Thread.currentThread();
	Set threadLatches = (Set) latchesByThread.get(cur);
	StringBuffer sb = new StringBuffer();
	if (threadLatches != null) {
	    Iterator i = threadLatches.iterator();
	    while (i.hasNext()) {
		sb.append(i.next()).append('\n');
	    }
	}
	return sb.toString();
  }

  // line 77 "../../../../Latches_LatchTable.ump"
  public void clearNotes(){
    latchesByThread.clear();
  }


  /**
   * 
   * For concocting exception messages.
   */
  // line 84 "../../../../Latches_LatchTable.ump"
  public String getNameString(String name){
    StringBuffer sb = new StringBuffer(tableName);
	if (name != null) {
	    sb.append("(").append(name).append(")");
	}
	return sb.toString();
  }


  /**
   * 
   * Formats a latch owner and waiters.
   */
  // line 95 "../../../../Latches_LatchTable.ump"
  public String toString(String name, Object owner, List waiters, int startIndex){
    StringBuffer sb = new StringBuffer();
	sb.append("<LATCH ");
	if (name != null) {
	    sb.append("[name: ").append(name).append("] ");
	}
	sb.append("[owner: ").append(owner).append("]");
	if (waiters != null && waiters.size() > startIndex) {
	    sb.append(" [waiters: ");
	    for (int i = startIndex; i < waiters.size(); i++) {
		sb.append(waiters.get(i)).append(" ");
	    }
	    sb.append("]");
	}
	sb.append(">");
	return sb.toString();
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 13 "../../../../Latches_LatchTable.ump"
  private String tableName ;
// line 15 "../../../../Latches_LatchTable.ump"
  private Map latchesByThread ;

  
}