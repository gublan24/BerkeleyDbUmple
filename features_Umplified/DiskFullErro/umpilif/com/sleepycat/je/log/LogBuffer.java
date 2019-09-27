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

  // line 8 "../../../../LogBuffer.ump"
  public boolean getRewriteAllowed(){
    return rewriteAllowed;
  }

  // line 12 "../../../../LogBuffer.ump"
  public void setRewriteAllowed(){
    rewriteAllowed = true;
  }

  // line 16 "../../../../LogBuffer.ump"
  public  LogBuffer(ByteBuffer buffer, long firstLsn) throws DatabaseException{
    rewriteAllowed = false;
  }

  // line 20 "../../../../LogBuffer.ump"
  public void reinit() throws DatabaseException{
    original();
	rewriteAllowed = false;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 5 "../../../../LogBuffer.ump"
  private boolean rewriteAllowed ;

  
}