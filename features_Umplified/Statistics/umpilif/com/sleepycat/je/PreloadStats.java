/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;

// line 3 "../../../PreloadStats.ump"
public class PreloadStats
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
   * Resets all stats.
   */
  // line 29 "../../../PreloadStats.ump"
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
  // line 42 "../../../PreloadStats.ump"
   public int getNINsLoaded(){
    return nINsLoaded;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 49 "../../../PreloadStats.ump"
   public int getNBINsLoaded(){
    return nBINsLoaded;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 56 "../../../PreloadStats.ump"
   public int getNLNsLoaded(){
    return nLNsLoaded;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 63 "../../../PreloadStats.ump"
   public int getNDINsLoaded(){
    return nDINsLoaded;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 70 "../../../PreloadStats.ump"
   public int getNDBINsLoaded(){
    return nDBINsLoaded;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 77 "../../../PreloadStats.ump"
   public int getNDupCountLNsLoaded(){
    return nDupCountLNsLoaded;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 84 "../../../PreloadStats.ump"
   public PreloadStatus getStatus(){
    return status;
  }


  /**
   * 
   * Internal use only.
   */
  // line 91 "../../../PreloadStats.ump"
   public void setNINsLoaded(int nINsLoaded){
    this.nINsLoaded = nINsLoaded;
  }


  /**
   * 
   * Internal use only.
   */
  // line 98 "../../../PreloadStats.ump"
   public void setNBINsLoaded(int nBINsLoaded){
    this.nBINsLoaded = nBINsLoaded;
  }


  /**
   * 
   * Internal use only.
   */
  // line 105 "../../../PreloadStats.ump"
   public void setNLNsLoaded(int nLNsLoaded){
    this.nLNsLoaded = nLNsLoaded;
  }


  /**
   * 
   * Internal use only.
   */
  // line 112 "../../../PreloadStats.ump"
   public void setNDINsLoaded(int nDINsLoaded){
    this.nDINsLoaded = nDINsLoaded;
  }


  /**
   * 
   * Internal use only.
   */
  // line 119 "../../../PreloadStats.ump"
   public void setNDBINsLoaded(int nDBINsLoaded){
    this.nDBINsLoaded = nDBINsLoaded;
  }


  /**
   * 
   * Internal use only.
   */
  // line 126 "../../../PreloadStats.ump"
   public void setNDupCountLNsLoaded(int nDupCountLNsLoaded){
    this.nDupCountLNsLoaded = nDupCountLNsLoaded;
  }


  /**
   * 
   * Internal use only.
   */
  // line 133 "../../../PreloadStats.ump"
   public void setStatus(PreloadStatus status){
    this.status = status;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 140 "../../../PreloadStats.ump"
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
  // line 155 "../../../PreloadStats.ump"
  public  PreloadStats(){
    reset();
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 8 "../../../PreloadStats.ump"
  public int nINsLoaded ;
// line 10 "../../../PreloadStats.ump"
  public int nBINsLoaded ;
// line 12 "../../../PreloadStats.ump"
  public int nLNsLoaded ;
// line 14 "../../../PreloadStats.ump"
  public int nDINsLoaded ;
// line 16 "../../../PreloadStats.ump"
  public int nDBINsLoaded ;
// line 18 "../../../PreloadStats.ump"
  public int nDupCountLNsLoaded ;
// line 23 "../../../PreloadStats.ump"
  public PreloadStatus status ;

  
}