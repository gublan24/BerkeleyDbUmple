/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.utilint;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.tree.TreeUtils;
import com.sleepycat.je.log.LogReadable;
import com.sleepycat.je.log.FileManager;
import java.util.Arrays;

// line 3 "../../../../DbLsn.ump"
public class DbLsn
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DbLsn()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 18 "../../../../DbLsn.ump"
   public static  long makeLsn(long fileNumber, long fileOffset){
    return fileOffset & INT_MASK | ((fileNumber & INT_MASK) << 32);
  }

  // line 22 "../../../../DbLsn.ump"
   public static  long longToLsn(Long lsn){
    if (lsn == null) {
	    return NULL_LSN;
	}
	return lsn.longValue();
  }


  /**
   * 
   * Return the file number for this DbLsn.
   * @return the number for this DbLsn.
   */
  // line 33 "../../../../DbLsn.ump"
   public static  long getFileNumber(long lsn){
    return (lsn >> 32) & INT_MASK;
  }


  /**
   * 
   * Return the file offset for this DbLsn.
   * @return the offset for this DbLsn.
   */
  // line 41 "../../../../DbLsn.ump"
   public static  long getFileOffset(long lsn){
    return (lsn & INT_MASK);
  }

  // line 45 "../../../../DbLsn.ump"
   private static  int compareLong(long l1, long l2){
    if (l1 < l2) {
	    return -1;
	} else if (l1 > l2) {
	    return 1;
	} else {
	    return 0;
	}
  }

  // line 55 "../../../../DbLsn.ump"
   public static  int compareTo(long lsn1, long lsn2){
    if (lsn1 == NULL_LSN || lsn2 == NULL_LSN) {
	    throw new NullPointerException();
	}
	long fileNumber1 = getFileNumber(lsn1);
	long fileNumber2 = getFileNumber(lsn2);
	if (fileNumber1 == fileNumber2) {
	    return compareLong(getFileOffset(lsn1), getFileOffset(lsn2));
	} else {
	    return compareLong(fileNumber1, fileNumber2);
	}
  }

  // line 68 "../../../../DbLsn.ump"
   public static  String toString(long lsn){
    return "<DbLsn val=\"0x" + Long.toHexString(getFileNumber(lsn)) + "/0x" + Long.toHexString(getFileOffset(lsn))
		+ "\"/>";
  }

  // line 73 "../../../../DbLsn.ump"
   public static  String getNoFormatString(long lsn){
    return "0x" + Long.toHexString(getFileNumber(lsn)) + "/0x" + Long.toHexString(getFileOffset(lsn));
  }

  // line 77 "../../../../DbLsn.ump"
   public static  String dumpString(long lsn, int nSpaces){
    StringBuffer sb = new StringBuffer();
	sb.append(TreeUtils.indent(nSpaces));
	sb.append(toString(lsn));
	return sb.toString();
  }


  /**
   * 
   * Return the logsize in bytes between these two LSNs. This is an approximation; the logs might actually be a little more or less in size. This assumes that no log files have been cleaned.
   */
  // line 87 "../../../../DbLsn.ump"
   public static  long getNoCleaningDistance(long thisLsn, long otherLsn, long logFileSize){
    long diff = 0;
	assert thisLsn != NULL_LSN;
	long myFile = getFileNumber(thisLsn);
	if (otherLsn == NULL_LSN) {
	    otherLsn = 0;
	}
	long otherFile = getFileNumber(otherLsn);
	if (myFile == otherFile) {
	    diff = Math.abs(getFileOffset(thisLsn) - getFileOffset(otherLsn));
	} else if (myFile > otherFile) {
	    diff = calcDiff(myFile - otherFile, logFileSize, thisLsn, otherLsn);
	} else {
	    diff = calcDiff(otherFile - myFile, logFileSize, otherLsn, thisLsn);
	}
	return diff;
  }


  /**
   * 
   * Return the logsize in bytes between these two LSNs. This is an approximation; the logs might actually be a little more or less in size. This assumes that log files might have been cleaned.
   */
  // line 108 "../../../../DbLsn.ump"
   public static  long getWithCleaningDistance(long thisLsn, FileManager fileManager, long otherLsn, long logFileSize){
    long diff = 0;
	assert thisLsn != NULL_LSN;
	long myFile = getFileNumber(thisLsn);
	if (otherLsn == NULL_LSN) {
	    otherLsn = 0;
	}
	long otherFile = getFileNumber(otherLsn);
	if (myFile == otherFile) {
	    diff = Math.abs(getFileOffset(thisLsn) - getFileOffset(otherLsn));
	} else {
	    Long[] fileNums = fileManager.getAllFileNumbers();
	    int myFileIdx = Arrays.binarySearch(fileNums, new Long(myFile));
	    int otherFileIdx = Arrays.binarySearch(fileNums, new Long(otherFile));
	    if (myFileIdx > otherFileIdx) {
		diff = calcDiff(myFileIdx - otherFileIdx, logFileSize, thisLsn, otherLsn);
	    } else {
		diff = calcDiff(otherFileIdx - myFileIdx, logFileSize, otherLsn, thisLsn);
	    }
	}
	return diff;
  }

  // line 131 "../../../../DbLsn.ump"
   private static  long calcDiff(long fileDistance, long logFileSize, long laterLsn, long earlierLsn){
    long diff = fileDistance * logFileSize;
	diff += getFileOffset(laterLsn);
	diff -= getFileOffset(earlierLsn);
	return diff;
  }


  /**
   * 
   * @see LogReadable#logEntryIsTransactional.
   */
  // line 141 "../../../../DbLsn.ump"
   public boolean logEntryIsTransactionalX(){
    return false;
  }


  /**
   * 
   * @see LogReadable#getTransactionId
   */
  // line 148 "../../../../DbLsn.ump"
   public long getTransactionIdX(){
    return 0;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 10 "../../../../DbLsn.ump"
  static final long INT_MASK = 0xFFFFFFFFL ;
// line 12 "../../../../DbLsn.ump"
  public static final long MAX_FILE_OFFSET = 0xFFFFFFFFL ;
// line 14 "../../../../DbLsn.ump"
  public static final long NULL_LSN = -1 ;

  
}