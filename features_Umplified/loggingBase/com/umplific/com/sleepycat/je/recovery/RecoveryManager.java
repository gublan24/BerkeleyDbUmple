/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.recovery;

// line 3 "../../../../RecoveryManager.ump"
// line 3 "../../../../RecoveryManager_inner.ump"
public class RecoveryManager
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public RecoveryManager()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  @MethodObject
  // line 4 "../../../../RecoveryManager_inner.ump"
  public static class RecoveryManager_trace
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public RecoveryManager_trace()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../RecoveryManager_inner.ump"
    public void execute(){
      logger=database.getDbEnvironment().getLogger();
          useLevel=level;
          if (!success) {
            useLevel=Level.SEVERE;
          }
          if (logger.isLoggable(useLevel)) {
            sb=new StringBuffer();
            sb.append(debugType);
            sb.append(" success=").append(success);
            sb.append(" node=");
            sb.append(node.getNodeId());
            sb.append(" lsn=");
            sb.append(DbLsn.getNoFormatString(logLsn));
            if (parent != null) {
              sb.append(" parent=").append(parent.getNodeId());
            }
            sb.append(" found=");
            sb.append(found);
            sb.append(" replaced=");
            sb.append(replaced);
            sb.append(" inserted=");
            sb.append(inserted);
            if (replacedLsn != DbLsn.NULL_LSN) {
              sb.append(" replacedLsn=");
              sb.append(DbLsn.getNoFormatString(replacedLsn));
            }
            if (abortLsn != DbLsn.NULL_LSN) {
              sb.append(" abortLsn=");
              sb.append(DbLsn.getNoFormatString(abortLsn));
            }
            sb.append(" index=").append(index);
            logger.log(useLevel,sb.toString());
          }
          original();  //@Abdulaziz should be updated .
    }
  
  }
}