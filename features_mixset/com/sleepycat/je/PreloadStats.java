/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;
import de.ovgu.cide.jakutil.*;
import java.io.Serializable;
import com.sleepycat.bind.serial.*;

// line 3 "../../../PreloadStats.ump"
// line 3 "../../../Statistics_PreloadStats.ump"
public class PreloadStats implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PreloadStats()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Internal use only.
   */
  // line 12 "../../../PreloadStats.ump"
   public  PreloadStats(){
    reset();
  }


  /**
   * 
   * Resets all stats.
   */
  // line 29 "../../../Statistics_PreloadStats.ump"
   private void reset(){
    nINsLoaded = 0;
		nBINsLoaded = 0;
		nLNsLoaded = 0;
		nDINsLoaded = 0;
		nDBINsLoaded = 0;
		nDupCountLNsLoaded = 0;
		status = PreloadStatus.SUCCESS;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 42 "../../../Statistics_PreloadStats.ump"
   public int getNINsLoaded(){
    return nINsLoaded;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 49 "../../../Statistics_PreloadStats.ump"
   public int getNBINsLoaded(){
    return nBINsLoaded;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 56 "../../../Statistics_PreloadStats.ump"
   public int getNLNsLoaded(){
    return nLNsLoaded;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 63 "../../../Statistics_PreloadStats.ump"
   public int getNDINsLoaded(){
    return nDINsLoaded;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 70 "../../../Statistics_PreloadStats.ump"
   public int getNDBINsLoaded(){
    return nDBINsLoaded;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 77 "../../../Statistics_PreloadStats.ump"
   public int getNDupCountLNsLoaded(){
    return nDupCountLNsLoaded;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 84 "../../../Statistics_PreloadStats.ump"
   public PreloadStatus getStatus(){
    return status;
  }


  /**
   * 
   * Internal use only.
   */
  // line 91 "../../../Statistics_PreloadStats.ump"
   public void setNINsLoaded(int nINsLoaded){
    this.nINsLoaded = nINsLoaded;
  }


  /**
   * 
   * Internal use only.
   */
  // line 98 "../../../Statistics_PreloadStats.ump"
   public void setNBINsLoaded(int nBINsLoaded){
    this.nBINsLoaded = nBINsLoaded;
  }


  /**
   * 
   * Internal use only.
   */
  // line 105 "../../../Statistics_PreloadStats.ump"
   public void setNLNsLoaded(int nLNsLoaded){
    this.nLNsLoaded = nLNsLoaded;
  }


  /**
   * 
   * Internal use only.
   */
  // line 112 "../../../Statistics_PreloadStats.ump"
   public void setNDINsLoaded(int nDINsLoaded){
    this.nDINsLoaded = nDINsLoaded;
  }


  /**
   * 
   * Internal use only.
   */
  // line 119 "../../../Statistics_PreloadStats.ump"
   public void setNDBINsLoaded(int nDBINsLoaded){
    this.nDBINsLoaded = nDBINsLoaded;
  }


  /**
   * 
   * Internal use only.
   */
  // line 126 "../../../Statistics_PreloadStats.ump"
   public void setNDupCountLNsLoaded(int nDupCountLNsLoaded){
    this.nDupCountLNsLoaded = nDupCountLNsLoaded;
  }


  /**
   * 
   * Internal use only.
   */
  // line 133 "../../../Statistics_PreloadStats.ump"
   public void setStatus(PreloadStatus status){
    this.status = status;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 140 "../../../Statistics_PreloadStats.ump"
   public String toString(){
    StringBuffer sb = new StringBuffer();
			sb.append("status=").append(status).append('\n');
			sb.append("nINsLoaded=").append(nINsLoaded).append('\n');
			sb.append("nBINsLoaded=").append(nBINsLoaded).append('\n');
			sb.append("nLNsLoaded=").append(nLNsLoaded).append('\n');
			sb.append("nDINsLoaded=").append(nDINsLoaded).append('\n');
			sb.append("nDBINsLoaded=").append(nDBINsLoaded).append('\n');
			sb.append("nDupCountLNsLoaded=").append(nDupCountLNsLoaded).append('\n');
			return sb.toString();
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 8 "../../../Statistics_PreloadStats.ump"
  public int nINsLoaded ;
// line 10 "../../../Statistics_PreloadStats.ump"
  public int nBINsLoaded ;
// line 12 "../../../Statistics_PreloadStats.ump"
  public int nLNsLoaded ;
// line 14 "../../../Statistics_PreloadStats.ump"
  public int nDINsLoaded ;
// line 16 "../../../Statistics_PreloadStats.ump"
  public int nDBINsLoaded ;
// line 18 "../../../Statistics_PreloadStats.ump"
  public int nDupCountLNsLoaded ;
// line 23 "../../../Statistics_PreloadStats.ump"
  public PreloadStatus status ;

  
}