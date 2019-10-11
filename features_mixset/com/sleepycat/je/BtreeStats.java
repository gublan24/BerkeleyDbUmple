/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;
import de.ovgu.cide.jakutil.*;

// line 3 "../../../Statistics_BtreeStats.ump"
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
  // line 38 "../../../Statistics_BtreeStats.ump"
   public long getBottomInternalNodeCount(){
    return binCount;
  }


  /**
   * 
   * Internal use only.
   */
  // line 45 "../../../Statistics_BtreeStats.ump"
   public void setBottomInternalNodeCount(long val){
    binCount = val;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 52 "../../../Statistics_BtreeStats.ump"
   public long getDuplicateBottomInternalNodeCount(){
    return dbinCount;
  }


  /**
   * 
   * Internal use only.
   */
  // line 59 "../../../Statistics_BtreeStats.ump"
   public void setDuplicateBottomInternalNodeCount(long val){
    dbinCount = val;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 66 "../../../Statistics_BtreeStats.ump"
   public long getDeletedLeafNodeCount(){
    return deletedLNCount;
  }


  /**
   * 
   * Internal use only.
   */
  // line 73 "../../../Statistics_BtreeStats.ump"
   public void setDeletedLeafNodeCount(long val){
    deletedLNCount = val;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 80 "../../../Statistics_BtreeStats.ump"
   public long getDupCountLeafNodeCount(){
    return dupCountLNCount;
  }


  /**
   * 
   * Internal use only.
   */
  // line 87 "../../../Statistics_BtreeStats.ump"
   public void setDupCountLeafNodeCount(long val){
    dupCountLNCount = val;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 94 "../../../Statistics_BtreeStats.ump"
   public long getInternalNodeCount(){
    return inCount;
  }


  /**
   * 
   * Internal use only.
   */
  // line 101 "../../../Statistics_BtreeStats.ump"
   public void setInternalNodeCount(long val){
    inCount = val;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 108 "../../../Statistics_BtreeStats.ump"
   public long getDuplicateInternalNodeCount(){
    return dinCount;
  }


  /**
   * 
   * Internal use only.
   */
  // line 115 "../../../Statistics_BtreeStats.ump"
   public void setDuplicateInternalNodeCount(long val){
    dinCount = val;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 122 "../../../Statistics_BtreeStats.ump"
   public long getLeafNodeCount(){
    return lnCount;
  }


  /**
   * 
   * Internal use only.
   */
  // line 129 "../../../Statistics_BtreeStats.ump"
   public void setLeafNodeCount(long val){
    lnCount = val;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 136 "../../../Statistics_BtreeStats.ump"
   public int getMainTreeMaxDepth(){
    return mainTreeMaxDepth;
  }


  /**
   * 
   * Internal use only.
   */
  // line 143 "../../../Statistics_BtreeStats.ump"
   public void setMainTreeMaxDepth(int val){
    mainTreeMaxDepth = val;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 150 "../../../Statistics_BtreeStats.ump"
   public int getDuplicateTreeMaxDepth(){
    return duplicateTreeMaxDepth;
  }


  /**
   * 
   * Internal use only.
   */
  // line 157 "../../../Statistics_BtreeStats.ump"
   public void setDuplicateTreeMaxDepth(int val){
    duplicateTreeMaxDepth = val;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 164 "../../../Statistics_BtreeStats.ump"
   public long[] getINsByLevel(){
    return insByLevel;
  }


  /**
   * 
   * Internal use only.
   */
  // line 171 "../../../Statistics_BtreeStats.ump"
   public void setINsByLevel(long [] insByLevel){
    this.insByLevel = insByLevel;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 178 "../../../Statistics_BtreeStats.ump"
   public long[] getBINsByLevel(){
    return binsByLevel;
  }


  /**
   * 
   * Internal use only.
   */
  // line 185 "../../../Statistics_BtreeStats.ump"
   public void setBINsByLevel(long [] binsByLevel){
    this.binsByLevel = binsByLevel;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 192 "../../../Statistics_BtreeStats.ump"
   public long[] getDINsByLevel(){
    return dinsByLevel;
  }


  /**
   * 
   * Internal use only.
   */
  // line 199 "../../../Statistics_BtreeStats.ump"
   public void setDINsByLevel(long [] dinsByLevel){
    this.dinsByLevel = dinsByLevel;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 206 "../../../Statistics_BtreeStats.ump"
   public long[] getDBINsByLevel(){
    return dbinsByLevel;
  }


  /**
   * 
   * Internal use only.
   */
  // line 213 "../../../Statistics_BtreeStats.ump"
   public void setDBINsByLevel(long [] dbinsByLevel){
    this.dbinsByLevel = dbinsByLevel;
  }

  // line 217 "../../../Statistics_BtreeStats.ump"
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
  // line 230 "../../../Statistics_BtreeStats.ump"
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
  
  // line 8 "../../../Statistics_BtreeStats.ump"
  private long binCount ;
// line 10 "../../../Statistics_BtreeStats.ump"
  private long dbinCount ;
// line 12 "../../../Statistics_BtreeStats.ump"
  private long deletedLNCount ;
// line 14 "../../../Statistics_BtreeStats.ump"
  private long dupCountLNCount ;
// line 16 "../../../Statistics_BtreeStats.ump"
  private long inCount ;
// line 18 "../../../Statistics_BtreeStats.ump"
  private long dinCount ;
// line 20 "../../../Statistics_BtreeStats.ump"
  private long lnCount ;
// line 22 "../../../Statistics_BtreeStats.ump"
  private int mainTreeMaxDepth ;
// line 24 "../../../Statistics_BtreeStats.ump"
  private int duplicateTreeMaxDepth ;
// line 26 "../../../Statistics_BtreeStats.ump"
  private long[] insByLevel ;
// line 28 "../../../Statistics_BtreeStats.ump"
  private long[] binsByLevel ;
// line 30 "../../../Statistics_BtreeStats.ump"
  private long[] dinsByLevel ;
// line 32 "../../../Statistics_BtreeStats.ump"
  private long[] dbinsByLevel ;

  
}