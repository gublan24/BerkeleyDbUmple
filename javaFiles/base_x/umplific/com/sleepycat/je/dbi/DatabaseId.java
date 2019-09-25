/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.log.LogWritable;
import com.sleepycat.je.log.LogUtils;
import com.sleepycat.je.log.LogReadable;
import com.sleepycat.je.DatabaseException;
import java.nio.ByteBuffer;
import java.io.UnsupportedEncodingException;
import com.sleepycat.je.log.*;

// line 3 "../../../../DatabaseId.ump"
public class DatabaseId implements LogWritable,LogReadable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DatabaseId()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   */
  // line 21 "../../../../DatabaseId.ump"
   public  DatabaseId(int id){
    this.id = id;
  }


  /**
   * 
   * Uninitialized database id, for logging.
   */
  // line 28 "../../../../DatabaseId.ump"
   public  DatabaseId(){
    
  }


  /**
   * 
   * @return id value
   */
  // line 34 "../../../../DatabaseId.ump"
   public int getId(){
    return id;
  }


  /**
   * 
   * @return id as bytes, for use as a key
   */
  // line 41 "../../../../DatabaseId.ump"
   public byte[] getBytes() throws DatabaseException{
    try {
	    return toString().getBytes("UTF-8");
	} catch (UnsupportedEncodingException UEE) {
	    throw new DatabaseException(UEE);
	}
  }


  /**
   * 
   * Compare two DatabaseImpl Id's.
   */
  // line 52 "../../../../DatabaseId.ump"
   public boolean equals(Object obj){
    if (this == obj) {
	    return true;
	}
	if (!(obj instanceof DatabaseId)) {
	    return false;
	}
	return ((DatabaseId) obj).id == id;
  }

  // line 62 "../../../../DatabaseId.ump"
   public int hashCode(){
    return id;
  }

  // line 66 "../../../../DatabaseId.ump"
   public String toString(){
    return Integer.toString(id);
  }


  /**
   * 
   * see Comparable#compareTo
   */
  // line 73 "../../../../DatabaseId.ump"
   public int compareTo(Object o){
    if (o == null) {
	    throw new NullPointerException();
	}
	DatabaseId argId = (DatabaseId) o;
	if (id == argId.id) {
	    return 0;
	} else if (id > argId.id) {
	    return 1;
	} else {
	    return -1;
	}
  }


  /**
   * 
   * @see LogWritable#getLogSize
   */
  // line 90 "../../../../DatabaseId.ump"
   public int getLogSize(){
    return LogUtils.getIntLogSize();
  }


  /**
   * 
   * @see LogWritable#writeToLog
   */
  // line 97 "../../../../DatabaseId.ump"
   public void writeToLog(ByteBuffer logBuffer){
    LogUtils.writeInt(logBuffer, id);
  }


  /**
   * 
   * @see LogReadable#readFromLog
   */
  // line 104 "../../../../DatabaseId.ump"
   public void readFromLog(ByteBuffer itemBuffer, byte entryTypeVersion){
    id = LogUtils.readInt(itemBuffer);
  }


  /**
   * 
   * @see LogReadable#dumpLog
   */
  // line 111 "../../../../DatabaseId.ump"
   public void dumpLog(StringBuffer sb, boolean verbose){
    sb.append("<dbId id=\"");
	sb.append(id);
	sb.append("\"/>");
  }


  /**
   * 
   * @see LogReadable#logEntryIsTransactional
   */
  // line 120 "../../../../DatabaseId.ump"
   public boolean logEntryIsTransactional(){
    return false;
  }


  /**
   * 
   * @see LogReadable#getTransactionId
   */
  // line 127 "../../../../DatabaseId.ump"
   public long getTransactionId(){
    return 0;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 16 "../../../../DatabaseId.ump"
  private int id ;

  
}