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
  // Default constructor has been disabled.  


  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Resets all stats.
   */
  // line 31 "../../../Statistics_PreloadStats.ump"
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
  // line 44 "../../../Statistics_PreloadStats.ump"
   public int getNINsLoaded(){
    return nINsLoaded;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 51 "../../../Statistics_PreloadStats.ump"
   public int getNBINsLoaded(){
    return nBINsLoaded;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 58 "../../../Statistics_PreloadStats.ump"
   public int getNLNsLoaded(){
    return nLNsLoaded;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 65 "../../../Statistics_PreloadStats.ump"
   public int getNDINsLoaded(){
    return nDINsLoaded;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 72 "../../../Statistics_PreloadStats.ump"
   public int getNDBINsLoaded(){
    return nDBINsLoaded;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 79 "../../../Statistics_PreloadStats.ump"
   public int getNDupCountLNsLoaded(){
    return nDupCountLNsLoaded;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 86 "../../../Statistics_PreloadStats.ump"
   public PreloadStatus getStatus(){
    return status;
  }


  /**
   * 
   * Internal use only.
   */
  // line 93 "../../../Statistics_PreloadStats.ump"
   public void setNINsLoaded(int nINsLoaded){
    this.nINsLoaded = nINsLoaded;
  }


  /**
   * 
   * Internal use only.
   */
  // line 100 "../../../Statistics_PreloadStats.ump"
   public void setNBINsLoaded(int nBINsLoaded){
    this.nBINsLoaded = nBINsLoaded;
  }


  /**
   * 
   * Internal use only.
   */
  // line 107 "../../../Statistics_PreloadStats.ump"
   public void setNLNsLoaded(int nLNsLoaded){
    this.nLNsLoaded = nLNsLoaded;
  }


  /**
   * 
   * Internal use only.
   */
  // line 114 "../../../Statistics_PreloadStats.ump"
   public void setNDINsLoaded(int nDINsLoaded){
    this.nDINsLoaded = nDINsLoaded;
  }


  /**
   * 
   * Internal use only.
   */
  // line 121 "../../../Statistics_PreloadStats.ump"
   public void setNDBINsLoaded(int nDBINsLoaded){
    this.nDBINsLoaded = nDBINsLoaded;
  }


  /**
   * 
   * Internal use only.
   */
  // line 128 "../../../Statistics_PreloadStats.ump"
   public void setNDupCountLNsLoaded(int nDupCountLNsLoaded){
    this.nDupCountLNsLoaded = nDupCountLNsLoaded;
  }


  /**
   * 
   * Internal use only.
   */
  // line 135 "../../../Statistics_PreloadStats.ump"
   public void setStatus(PreloadStatus status){
    this.status = status;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 142 "../../../Statistics_PreloadStats.ump"
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


  /**
   * 
   * Internal use only.
   */
  // line 157 "../../../Statistics_PreloadStats.ump"
  public  PreloadStats(){
    reset();
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