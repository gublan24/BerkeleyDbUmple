/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;
import de.ovgu.cide.jakutil.*;

// line 3 "../../../SequenceStats.ump"
public class SequenceStats
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SequenceStats()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 24 "../../../SequenceStats.ump"
  public  SequenceStats(int nGets, int nCachedGets, long current, long value, long lastValue, long min, long max, int cacheSize){
    this.nGets = nGets;
	this.nCachedGets = nCachedGets;
	this.current = current;
	this.value = value;
	this.lastValue = lastValue;
	this.min = min;
	this.max = max;
	this.cacheSize = cacheSize;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 38 "../../../SequenceStats.ump"
   public int getNGets(){
    return nGets;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 45 "../../../SequenceStats.ump"
   public int getNCachedGets(){
    return nCachedGets;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 52 "../../../SequenceStats.ump"
   public long getCurrent(){
    return current;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 59 "../../../SequenceStats.ump"
   public long getValue(){
    return value;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 66 "../../../SequenceStats.ump"
   public long getLastValue(){
    return lastValue;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 73 "../../../SequenceStats.ump"
   public long getMin(){
    return min;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 80 "../../../SequenceStats.ump"
   public long getMax(){
    return max;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 87 "../../../SequenceStats.ump"
   public int getCacheSize(){
    return cacheSize;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 94 "../../../SequenceStats.ump"
   public String toString(){
    return "nGets=" + nGets + "\nnCachedGets=" + nCachedGets + "\ncurrent=" + current + "\nvalue=" + value
		+ "\nlastValue=" + lastValue + "\nmin=" + min + "\nmax=" + max + "\ncacheSize=" + cacheSize;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 6 "../../../SequenceStats.ump"
  private int nGets ;
// line 8 "../../../SequenceStats.ump"
  private int nCachedGets ;
// line 10 "../../../SequenceStats.ump"
  private long current ;
// line 12 "../../../SequenceStats.ump"
  private long value ;
// line 14 "../../../SequenceStats.ump"
  private long lastValue ;
// line 16 "../../../SequenceStats.ump"
  private long min ;
// line 18 "../../../SequenceStats.ump"
  private long max ;
// line 20 "../../../SequenceStats.ump"
  private int cacheSize ;

  
}