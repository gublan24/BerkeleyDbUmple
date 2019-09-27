/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;

// line 3 "../../../../FileReader.ump"
public class FileReader
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public FileReader()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 6 "../../../../FileReader.ump"
   protected void hook469(FileHandle fileHandle) throws IOException,DatabaseException,EOFException{
    try {
	    original(fileHandle);
	} finally {
	    fileHandle.release();
	}
  }

  // line 14 "../../../../FileReader.ump"
   protected void hook470(FileHandle fileHandle) throws DatabaseException,EOFException,IOException{
    fileHandle.release();
	original(fileHandle);
  }

  // line 19 "../../../../FileReader.ump"
   protected void hook471(FileHandle fileHandle) throws DatabaseException,EOFException{
    if (fileHandle != null) {
	    fileHandle.release();
	}
	original(fileHandle);
  }

}