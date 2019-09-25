/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.txn;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.log.LogEntryType;

// line 3 "../../../../TxnCommit.ump"
public class TxnCommit extends TxnEnd
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TxnCommit()
  {
    super();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }

  // line 9 "../../../../TxnCommit.ump"
   public  TxnCommit(long id, long lastLsn){
    super(id, lastLsn);
  }


  /**
   * 
   * For constructing from the log.
   */
  // line 16 "../../../../TxnCommit.ump"
   public  TxnCommit(){
    
  }


  /**
   * 
   * @see TxnEnd#getLogType
   */
  // line 22 "../../../../TxnCommit.ump"
   public LogEntryType getLogType(){
    return LogEntryType.LOG_TXN_COMMIT;
  }

  // line 26 "../../../../TxnCommit.ump"
   protected String getTagName(){
    return "TxnCommit";
  }

}