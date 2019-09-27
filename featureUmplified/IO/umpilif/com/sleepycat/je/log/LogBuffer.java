/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;

// line 3 "../../../../LogBuffer.ump"
public class LogBuffer
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LogBuffer()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 6 "../../../../LogBuffer.ump"
   protected void hook482(int capacity) throws DatabaseException{
    buffer = ByteBuffer.allocate(capacity);
	original(capacity);
  }

}