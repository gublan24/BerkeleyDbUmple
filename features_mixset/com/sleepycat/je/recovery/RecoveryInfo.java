/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.recovery;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.DbLsn;

// line 3 "../../../../RecoveryInfo.ump"
public class RecoveryInfo
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public RecoveryInfo()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 48 "../../../../RecoveryInfo.ump"
   public String toString(){
    StringBuffer sb = new StringBuffer();
	sb.append("Recovery Info");
	appendLsn(sb, " lastUsed=", lastUsedLsn);
	appendLsn(sb, " nextAvail=", nextAvailableLsn);
	appendLsn(sb, " ckptStart=", checkpointStartLsn);
	appendLsn(sb, " firstActive=", firstActiveLsn);
	appendLsn(sb, " ckptEnd=", checkpointEndLsn);
	appendLsn(sb, " useRoot=", useRootLsn);
	sb.append(" ckptEnd=<").append(checkpointEnd).append(">");
	sb.append(" useMaxNodeId=").append(useMaxNodeId);
	sb.append(" useMaxDbId=").append(useMaxDbId);
	sb.append(" useMaxTxnId=").append(useMaxTxnId);
	sb.append(" numMapINs=").append(numMapINs);
	sb.append(" numOtherINs=").append(numOtherINs);
	sb.append(" numBinDeltas=").append(numBinDeltas);
	sb.append(" numDuplicateINs=").append(numDuplicateINs);
	sb.append(" lnFound=").append(lnFound);
	sb.append(" lnNotFound=").append(lnNotFound);
	sb.append(" lnInserted=").append(lnInserted);
	sb.append(" lnReplaced=").append(lnReplaced);
	sb.append(" nRepeatIteratorReads=").append(nRepeatIteratorReads);
	return sb.toString();
  }

  // line 73 "../../../../RecoveryInfo.ump"
   private void appendLsn(StringBuffer sb, String name, long lsn){
    if (lsn != DbLsn.NULL_LSN) {
	    sb.append(name).append(DbLsn.getNoFormatString(lsn));
	}
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 7 "../../../../RecoveryInfo.ump"
  public long lastUsedLsn = DbLsn.NULL_LSN ;
// line 9 "../../../../RecoveryInfo.ump"
  public long nextAvailableLsn = DbLsn.NULL_LSN ;
// line 11 "../../../../RecoveryInfo.ump"
  public long firstActiveLsn = DbLsn.NULL_LSN ;
// line 13 "../../../../RecoveryInfo.ump"
  public long checkpointStartLsn = DbLsn.NULL_LSN ;
// line 15 "../../../../RecoveryInfo.ump"
  public long checkpointEndLsn = DbLsn.NULL_LSN ;
// line 17 "../../../../RecoveryInfo.ump"
  public long useRootLsn = DbLsn.NULL_LSN ;
// line 19 "../../../../RecoveryInfo.ump"
  public long partialCheckpointStartLsn = DbLsn.NULL_LSN ;
// line 21 "../../../../RecoveryInfo.ump"
  public CheckpointEnd checkpointEnd ;
// line 23 "../../../../RecoveryInfo.ump"
  public long useMaxNodeId ;
// line 25 "../../../../RecoveryInfo.ump"
  public int useMaxDbId ;
// line 27 "../../../../RecoveryInfo.ump"
  public long useMaxTxnId ;
// line 29 "../../../../RecoveryInfo.ump"
  public int numMapINs ;
// line 31 "../../../../RecoveryInfo.ump"
  public int numOtherINs ;
// line 33 "../../../../RecoveryInfo.ump"
  public int numBinDeltas ;
// line 35 "../../../../RecoveryInfo.ump"
  public int numDuplicateINs ;
// line 37 "../../../../RecoveryInfo.ump"
  public int lnFound ;
// line 39 "../../../../RecoveryInfo.ump"
  public int lnNotFound ;
// line 41 "../../../../RecoveryInfo.ump"
  public int lnInserted ;
// line 43 "../../../../RecoveryInfo.ump"
  public int lnReplaced ;
// line 45 "../../../../RecoveryInfo.ump"
  public int nRepeatIteratorReads ;

  
}