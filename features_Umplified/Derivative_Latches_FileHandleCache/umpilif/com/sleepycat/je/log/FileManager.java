/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;

// line 3 "../../../../FileManager.ump"
public class FileManager
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public FileManager()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 8 "../../../../FileManager.ump"
   protected void hook449(EnvironmentImpl envImpl) throws DatabaseException{
    fileCacheLatch = LatchSupport.makeLatch(DEBUG_NAME + "_fileCache", envImpl);
	original(envImpl);
  }

  // line 14 "../../../../FileManager.ump"
   protected FileHandle hook450(long fileNum, Long fileId, FileHandle fileHandle) throws LogException,DatabaseException{
    fileCacheLatch.acquire();
	try {
	    fileHandle = original(fileNum, fileId, fileHandle);
	} finally {
	    fileCacheLatch.release();
	}
	return fileHandle;
  }


  /**
   * 
   * Close all file handles and empty the cache.
   */
  // line 27 "../../../../FileManager.ump"
   public void clear() throws IOException,DatabaseException{
    fileCacheLatch.acquire();
	original();
  }

  // line 32 "../../../../FileManager.ump"
   protected void hook451() throws IOException,DatabaseException{
    try {
	    original();
	} finally {
	    fileCacheLatch.release();
	}
  }


  /**
   * 
   * Clear a file out of the file cache regardless of mode type.
   */
  // line 43 "../../../../FileManager.ump"
   private void clearFileCache(long fileNum) throws IOException,DatabaseException{
    fileCacheLatch.acquire();
	try {
	    original(fileNum);
	} finally {
	    fileCacheLatch.release();
	}
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 5 "../../../../FileManager.ump"
  private Latch fileCacheLatch ;

  
}