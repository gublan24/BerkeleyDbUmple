/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;

// line 3 "../../../Environment.ump"
public class Environment
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Environment()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 9 "../../../Environment.ump"
   public boolean verify(VerifyConfig config, PrintStream out) throws DatabaseException{
    checkHandleIsValid();
	checkEnv();
	VerifyConfig useConfig = (config == null) ? VerifyConfig.DEFAULT : config;
	return environmentImpl.verify(useConfig, out);
  }

}