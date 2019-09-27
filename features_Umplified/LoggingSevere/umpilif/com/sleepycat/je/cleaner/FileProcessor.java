/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.cleaner;

// line 3 "../../../../FileProcessor.ump"
public class FileProcessor
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public FileProcessor()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 6 "../../../../FileProcessor.ump"
   protected void hook122(IOException IOE) throws DatabaseException{
    Tracer.trace(env, "Cleaner", "doClean", "", IOE);
	original(IOE);
  }

  // line 11 "../../../../FileProcessor.ump"
   protected void hook123(String traceMsg) throws DatabaseException{
    Tracer.trace(Level.SEVERE, env, traceMsg);
	original(traceMsg);
  }

}