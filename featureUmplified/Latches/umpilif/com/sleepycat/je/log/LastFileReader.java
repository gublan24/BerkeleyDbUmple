/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;

// line 3 "../../../../LastFileReader.ump"
public class LastFileReader
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LastFileReader()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 6 "../../../../LastFileReader.ump"
   protected void hook477(FileHandle fileHandle) throws IOException,DatabaseException{
    fileHandle.release();
	original(fileHandle);
  }

  // line 11 "../../../../LastFileReader.ump"
   protected void hook478(FileHandle fileHandle) throws IOException,DatabaseException{
    if (fileHandle != null) {
	    fileHandle.release();
	}
	original(fileHandle);
  }

}