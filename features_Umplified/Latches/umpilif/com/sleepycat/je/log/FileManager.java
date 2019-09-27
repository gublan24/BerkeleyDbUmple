/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;
import com.sleepycat.je.latch.LatchSupport;
import com.sleepycat.je.latch.Latch;

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
   protected void hook453(FileHandle fileHandle) throws LogException,DatabaseException{
    fileHandle.latch();
	original(fileHandle);
  }

  // line 13 "../../../../FileManager.ump"
   protected void hook454(FileHandle fileHandle) throws LogException,DatabaseException{
    fileHandle.release();
	original(fileHandle);
  }

}