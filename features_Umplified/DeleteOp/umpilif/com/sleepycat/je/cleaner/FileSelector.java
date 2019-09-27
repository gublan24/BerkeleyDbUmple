/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.cleaner;

// line 3 "../../../../FileSelector.ump"
public class FileSelector
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public FileSelector()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 38 "../../../../FileSelector.ump"
   protected void hook163(){
    pendingDBs = new HashSet();
	original();
  }

  // line 43 "../../../../FileSelector.ump"
   protected void hook164(){
    anyPendingDuringCheckpoint |= !pendingDBs.isEmpty();
	original();
  }

  // line 48 "../../../../FileSelector.ump"
   protected boolean hook165(boolean b){
    b &= pendingDBs.isEmpty();
	return original(b);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 5 "../../../../FileSelector.ump"
  private Set pendingDBs ;

// line 10 "../../../../FileSelector.ump"
  synchronized boolean addPendingDB (DatabaseId dbId) 
  {
    boolean added = pendingDBs.add(dbId);
	anyPendingDuringCheckpoint = true;
	return added;
  }

// line 19 "../../../../FileSelector.ump"
  synchronized DatabaseId[] getPendingDBs () 
  {
    if (pendingDBs.size() > 0) {
	    DatabaseId[] dbs = new DatabaseId[pendingDBs.size()];
	    pendingDBs.toArray(dbs);
	    return dbs;
	} else {
	    return null;
	}
  }

// line 32 "../../../../FileSelector.ump"
  synchronized void removePendingDB (DatabaseId dbId) 
  {
    pendingDBs.remove(dbId);
	updateProcessedFiles();
  }

  
}