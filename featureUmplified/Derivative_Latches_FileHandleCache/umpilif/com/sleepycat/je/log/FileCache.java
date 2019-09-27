/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;

// line 3 "../../../../FileCache.ump"
public class FileCache
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public FileCache()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 6 "../../../../FileCache.ump"
   protected void hook438(Iterator iter, boolean done, Long evictId, FileHandle evictTarget) throws DatabaseException{
    if (evictTarget.latchNoWait()) {
	    original(iter, done, evictId, evictTarget);
	}
  }

  // line 12 "../../../../FileCache.ump"
   protected void hook439(Iterator iter, Long evictId, FileHandle evictTarget) throws IOException,DatabaseException{
    try {
	    original(iter, evictId, evictTarget);
	} finally {
	    evictTarget.release();
	}
  }

  // line 20 "../../../../FileCache.ump"
   protected void hook440(Iterator iter, FileHandle fileHandle) throws IOException,DatabaseException{
    try {
	    original(iter, fileHandle);
	} finally {
	    fileHandle.release();
	}
  }

  // line 28 "../../../../FileCache.ump"
   protected void hook441(FileHandle evictTarget) throws DatabaseException{
    evictTarget.release();
	original(evictTarget);
  }

  // line 33 "../../../../FileCache.ump"
   protected void hook442(FileHandle evictTarget) throws IOException,DatabaseException{
    evictTarget.latch();
	original(evictTarget);
  }

  // line 38 "../../../../FileCache.ump"
   protected void hook443(FileHandle fileHandle) throws IOException,DatabaseException{
    fileHandle.latch();
	original(fileHandle);
  }

}