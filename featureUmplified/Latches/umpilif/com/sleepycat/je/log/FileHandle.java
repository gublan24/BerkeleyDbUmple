/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;
import com.sleepycat.je.latch.LatchSupport;
import com.sleepycat.je.latch.Latch;

// line 3 "../../../../FileHandle.ump"
public class FileHandle
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public FileHandle()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 10 "../../../../FileHandle.ump"
  public void latch() throws DatabaseException{
    fileLatch.acquire();
  }

  // line 14 "../../../../FileHandle.ump"
  public boolean latchNoWait() throws DatabaseException{
    return fileLatch.acquireNoWait();
  }

  // line 18 "../../../../FileHandle.ump"
  public void release() throws DatabaseException{
    fileLatch.release();
  }

  // line 22 "../../../../FileHandle.ump"
   protected void hook444(String fileName, EnvironmentImpl env){
    fileLatch = LatchSupport.makeLatch(fileName + "_fileHandle", env);
	original(fileName, env);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 7 "../../../../FileHandle.ump"
  private Latch fileLatch ;

  
}