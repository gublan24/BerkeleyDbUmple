/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;
import de.ovgu.cide.jakutil.*;

// line 3 "../../../BtreeStats.ump"
public class BtreeStats extends DatabaseStats
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public BtreeStats()
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


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 37 "../../../BtreeStats.ump"
   public long getBottomInternalNodeCount(){
    return binCount;
  }


  /**
   * 
   * Internal use only.
   */
  // line 44 "../../../BtreeStats.ump"
   public void setBottomInternalNodeCount(long val){
    binCount = val;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 51 "../../../BtreeStats.ump"
   public long getDuplicateBottomInternalNodeCount(){
    return dbinCount;
  }


  /**
   * 
   * Internal use only.
   */
  // line 58 "../../../BtreeStats.ump"
   public void setDuplicateBottomInternalNodeCount(long val){
    dbinCount = val;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 65 "../../../BtreeStats.ump"
   public long getDeletedLeafNodeCount(){
    return deletedLNCount;
  }


  /**
   * 
   * Internal use only.
   */
  // line 72 "../../../BtreeStats.ump"
   public void setDeletedLeafNodeCount(long val){
    deletedLNCount = val;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 79 "../../../BtreeStats.ump"
   public long getDupCountLeafNodeCount(){
    return dupCountLNCount;
  }


  /**
   * 
   * Internal use only.
   */
  // line 86 "../../../BtreeStats.ump"
   public void setDupCountLeafNodeCount(long val){
    dupCountLNCount = val;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 93 "../../../BtreeStats.ump"
   public long getInternalNodeCount(){
    return inCount;
  }


  /**
   * 
   * Internal use only.
   */
  // line 100 "../../../BtreeStats.ump"
   public void setInternalNodeCount(long val){
    inCount = val;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 107 "../../../BtreeStats.ump"
   public long getDuplicateInternalNodeCount(){
    return dinCount;
  }


  /**
   * 
   * Internal use only.
   */
  // line 114 "../../../BtreeStats.ump"
   public void setDuplicateInternalNodeCount(long val){
    dinCount = val;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 121 "../../../BtreeStats.ump"
   public long getLeafNodeCount(){
    return lnCount;
  }


  /**
   * 
   * Internal use only.
   */
  // line 128 "../../../BtreeStats.ump"
   public void setLeafNodeCount(long val){
    lnCount = val;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 135 "../../../BtreeStats.ump"
   public int getMainTreeMaxDepth(){
    return mainTreeMaxDepth;
  }


  /**
   * 
   * Internal use only.
   */
  // line 142 "../../../BtreeStats.ump"
   public void setMainTreeMaxDepth(int val){
    mainTreeMaxDepth = val;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 149 "../../../BtreeStats.ump"
   public int getDuplicateTreeMaxDepth(){
    return duplicateTreeMaxDepth;
  }


  /**
   * 
   * Internal use only.
   */
  // line 156 "../../../BtreeStats.ump"
   public void setDuplicateTreeMaxDepth(int val){
    duplicateTreeMaxDepth = val;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 163 "../../../BtreeStats.ump"
   public long[] getINsByLevel(){
    return insByLevel;
  }


  /**
   * 
   * Internal use only.
   */
  // line 170 "../../../BtreeStats.ump"
   public void setINsByLevel(long [] insByLevel){
    this.insByLevel = insByLevel;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 177 "../../../BtreeStats.ump"
   public long[] getBINsByLevel(){
    return binsByLevel;
  }


  /**
   * 
   * Internal use only.
   */
  // line 184 "../../../BtreeStats.ump"
   public void setBINsByLevel(long [] binsByLevel){
    this.binsByLevel = binsByLevel;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 191 "../../../BtreeStats.ump"
   public long[] getDINsByLevel(){
    return dinsByLevel;
  }


  /**
   * 
   * Internal use only.
   */
  // line 198 "../../../BtreeStats.ump"
   public void setDINsByLevel(long [] dinsByLevel){
    this.dinsByLevel = dinsByLevel;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 205 "../../../BtreeStats.ump"
   public long[] getDBINsByLevel(){
    return dbinsByLevel;
  }


  /**
   * 
   * Internal use only.
   */
  // line 212 "../../../BtreeStats.ump"
   public void setDBINsByLevel(long [] dbinsByLevel){
    this.dbinsByLevel = dbinsByLevel;
  }

  // line 216 "../../../BtreeStats.ump"
   private void arrayToString(long [] arr, StringBuffer sb){
    for (int i = 0; i < arr.length; i++) {
	    long count = arr[i];
	    if (count > 0) {
		sb.append("  level ").append(i);
		sb.append(": count=").append(count).append("\n");
	    }
	}
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 229 "../../../BtreeStats.ump"
   public String toString(){
    StringBuffer sb = new StringBuffer();
	if (binCount > 0) {
	    sb.append("numBottomInternalNodes=");
	    sb.append(binCount).append("\n");
	    arrayToString(binsByLevel, sb);
	}
	if (inCount > 0) {
	    sb.append("numInternalNodes=");
	    sb.append(inCount).append("\n");
	    arrayToString(insByLevel, sb);
	}
	if (dinCount > 0) {
	    sb.append("numDuplicateInternalNodes=");
	    sb.append(dinCount).append("\n");
	    arrayToString(dinsByLevel, sb);
	}
	if (dbinCount > 0) {
	    sb.append("numDuplicateBottomInternalNodes=");
	    sb.append(dbinCount).append("\n");
	    arrayToString(dbinsByLevel, sb);
	}
	sb.append("numLeafNodes=").append(lnCount).append("\n");
	sb.append("numDeletedLeafNodes=").append(deletedLNCount).append("\n");
	sb.append("numDuplicateCountLeafNodes=").append(dupCountLNCount).append("\n");
	sb.append("mainTreeMaxDepth=").append(mainTreeMaxDepth).append("\n");
	sb.append("duplicateTreeMaxDepth=").append(duplicateTreeMaxDepth).append("\n");
	return sb.toString();
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 7 "../../../BtreeStats.ump"
  private long binCount ;
// line 9 "../../../BtreeStats.ump"
  private long dbinCount ;
// line 11 "../../../BtreeStats.ump"
  private long deletedLNCount ;
// line 13 "../../../BtreeStats.ump"
  private long dupCountLNCount ;
// line 15 "../../../BtreeStats.ump"
  private long inCount ;
// line 17 "../../../BtreeStats.ump"
  private long dinCount ;
// line 19 "../../../BtreeStats.ump"
  private long lnCount ;
// line 21 "../../../BtreeStats.ump"
  private int mainTreeMaxDepth ;
// line 23 "../../../BtreeStats.ump"
  private int duplicateTreeMaxDepth ;
// line 25 "../../../BtreeStats.ump"
  private long[] insByLevel ;
// line 27 "../../../BtreeStats.ump"
  private long[] binsByLevel ;
// line 29 "../../../BtreeStats.ump"
  private long[] dinsByLevel ;
// line 31 "../../../BtreeStats.ump"
  private long[] dbinsByLevel ;

  
}