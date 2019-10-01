/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.tree;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.log.LogUtils;
import com.sleepycat.je.log.LogException;
import com.sleepycat.je.log.LogEntryType;
import com.sleepycat.je.dbi.DatabaseId;
import java.nio.ByteBuffer;

// line 3 "../../../../NameLN.ump"
public class NameLN extends LN
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public NameLN()
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
   * In the ideal world, we'd have a base LN class so that this NameLN doesn't have a superfluous data field, but we want to optimize the LN class for size and speed right now.
   */
  // line 24 "../../../../NameLN.ump"
   public  NameLN(DatabaseId id){
    super(new byte[0]);
	this.id = id;
	deleted = false;
  }


  /**
   * 
   * Create an empty NameLN, to be filled in from the log.
   */
  // line 33 "../../../../NameLN.ump"
   public  NameLN(){
    super();
	id = new DatabaseId();
  }

  // line 38 "../../../../NameLN.ump"
   public boolean isDeleted(){
    return deleted;
  }

  // line 42 "../../../../NameLN.ump"
  public void makeDeleted(){
    deleted = true;
  }

  // line 46 "../../../../NameLN.ump"
   public DatabaseId getId(){
    return id;
  }

  // line 50 "../../../../NameLN.ump"
   public void setId(DatabaseId id){
    this.id = id;
  }

  // line 54 "../../../../NameLN.ump"
   public String toString(){
    return dumpString(0, true);
  }

  // line 58 "../../../../NameLN.ump"
   public String beginTag(){
    return BEGIN_TAG;
  }

  // line 62 "../../../../NameLN.ump"
   public String endTag(){
    return END_TAG;
  }

  // line 66 "../../../../NameLN.ump"
   public String dumpString(int nSpaces, boolean dumpTags){
    StringBuffer sb = new StringBuffer();
	sb.append(super.dumpString(nSpaces, dumpTags));
	sb.append('\n');
	sb.append(TreeUtils.indent(nSpaces));
	sb.append("<deleted val=\"").append(Boolean.toString(deleted));
	sb.append("\">");
	sb.append('\n');
	sb.append(TreeUtils.indent(nSpaces));
	sb.append("<id val=\"").append(id);
	sb.append("\">");
	sb.append('\n');
	return sb.toString();
  }


  /**
   * 
   * Log type for transactional entries.
   */
  // line 84 "../../../../NameLN.ump"
   protected LogEntryType getTransactionalLogType(){
    return LogEntryType.LOG_NAMELN_TRANSACTIONAL;
  }


  /**
   * 
   * @see LN#getLogType
   */
  // line 91 "../../../../NameLN.ump"
   public LogEntryType getLogType(){
    return LogEntryType.LOG_NAMELN;
  }


  /**
   * 
   * @see LN#getLogSize
   */
  // line 98 "../../../../NameLN.ump"
   public int getLogSize(){
    return super.getLogSize() + id.getLogSize() + LogUtils.getBooleanLogSize();
  }


  /**
   * 
   * @see LN#writeToLog
   */
  // line 105 "../../../../NameLN.ump"
   public void writeToLog(ByteBuffer logBuffer){
    super.writeToLog(logBuffer);
	id.writeToLog(logBuffer);
	LogUtils.writeBoolean(logBuffer, deleted);
  }


  /**
   * 
   * @see LN#readFromLog
   */
  // line 114 "../../../../NameLN.ump"
   public void readFromLog(ByteBuffer itemBuffer, byte entryTypeVersion) throws LogException{
    super.readFromLog(itemBuffer, entryTypeVersion);
	id.readFromLog(itemBuffer, entryTypeVersion);
	deleted = LogUtils.readBoolean(itemBuffer);
  }


  /**
   * 
   * Dump additional fields. Done this way so the additional info can be within the XML tags defining the dumped log entry.
   */
  // line 123 "../../../../NameLN.ump"
   protected void dumpLogAdditional(StringBuffer sb, boolean verbose){
    id.dumpLog(sb, true);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 12 "../../../../NameLN.ump"
  private static final String BEGIN_TAG = "<nameLN>" ;
// line 14 "../../../../NameLN.ump"
  private static final String END_TAG = "</nameLN>" ;
// line 16 "../../../../NameLN.ump"
  private DatabaseId id ;
// line 18 "../../../../NameLN.ump"
  private boolean deleted ;

  
}