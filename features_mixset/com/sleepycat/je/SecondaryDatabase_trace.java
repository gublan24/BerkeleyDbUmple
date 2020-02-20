/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;

// line 4 "../../../LoggingFinest_SecondaryDatabase_inner.ump"
// line 4 "../../../Derivative_LoggingFinest_LoggingBase_SecondaryDatabase_inner.ump"
public class SecondaryDatabase_trace
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SecondaryDatabase_trace()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 6 "../../../LoggingFinest_SecondaryDatabase_inner.ump"
  public  SecondaryDatabase_trace(SecondaryDatabase _this, Level level, String methodName){
    this._this=_this;
        this.level=level;
        this.methodName=methodName;
  }

  // line 11 "../../../LoggingFinest_SecondaryDatabase_inner.ump"
  public void execute() throws DatabaseException{
    // line 6 "../../../Derivative_LoggingFinest_LoggingBase_SecondaryDatabase_inner.ump"
    logger=envHandle.getEnvironmentImpl().getLogger();
            if (logger.isLoggable(level)) {
              sb=new StringBuffer();
              sb.append(methodName);
              sb.append(" name=").append(_this.getDebugName());
              sb.append(" primary=").append(_this.primaryDb.getDebugName());
              logger.log(level,sb.toString());
            }
    // END OF UMPLE BEFORE INJECTION
    
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 12 "../../../LoggingFinest_SecondaryDatabase_inner.ump"
  protected SecondaryDatabase _this ;
// line 13 "../../../LoggingFinest_SecondaryDatabase_inner.ump"
  protected Level level ;
// line 14 "../../../LoggingFinest_SecondaryDatabase_inner.ump"
  protected String methodName ;
// line 15 "../../../LoggingFinest_SecondaryDatabase_inner.ump"
  protected Logger logger ;
// line 16 "../../../LoggingFinest_SecondaryDatabase_inner.ump"
  protected StringBuffer sb ;

  
}