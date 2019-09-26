/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.txn;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.log.LogEntryType;

// line 3 "../../../../TxnAbort.ump"
public class TxnAbort extends TxnEnd
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TxnAbort()
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

  // line 9 "../../../../TxnAbort.ump"
   public  TxnAbort(long id, long lastLsn){
    super(id, lastLsn);
  }


  /**
   * 
   * For constructing from the log.
   */
  // line 16 "../../../../TxnAbort.ump"
   public  TxnAbort(){
    
  }


  /**
   * 
   * @see TxnEnd#getLogType
   */
  // line 22 "../../../../TxnAbort.ump"
   public LogEntryType getLogType(){
    return LogEntryType.LOG_TXN_ABORT;
  }

  // line 26 "../../../../TxnAbort.ump"
   protected String getTagName(){
    return "TxnAbort";
  }

}