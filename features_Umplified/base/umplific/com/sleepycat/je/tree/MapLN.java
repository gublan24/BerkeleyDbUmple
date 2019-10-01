/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.tree;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.log.LogUtils;
import com.sleepycat.je.log.LogException;
import com.sleepycat.je.log.LogEntryType;
import com.sleepycat.je.dbi.DatabaseImpl;
import com.sleepycat.je.DatabaseException;
import java.nio.ByteBuffer;

// line 3 "../../../../MapLN.ump"
public class MapLN extends LN
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public MapLN()
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
   * Create a new MapLn to hold a new databaseImpl. In the ideal world, we'd have a base LN class so that this MapLN doesn't have a superfluous data field, but we want to optimize the LN class for size and speed right now.
   */
  // line 25 "../../../../MapLN.ump"
   public  MapLN(DatabaseImpl db){
    super(new byte[0]);
	databaseImpl = db;
	deleted = false;
  }


  /**
   * 
   * Create an empty MapLN, to be filled in from the log.
   */
  // line 34 "../../../../MapLN.ump"
   public  MapLN() throws DatabaseException{
    super();
	databaseImpl = new DatabaseImpl();
  }

  // line 39 "../../../../MapLN.ump"
   public boolean isDeleted(){
    return deleted;
  }

  // line 43 "../../../../MapLN.ump"
  public void makeDeleted(){
    deleted = true;
	databaseImpl.getTree().setRoot(null, true);
  }

  // line 48 "../../../../MapLN.ump"
   public DatabaseImpl getDatabase(){
    return databaseImpl;
  }


  /**
   * 
   * Initialize a node that has been faulted in from the log.
   */
  // line 55 "../../../../MapLN.ump"
   public void postFetchInit(DatabaseImpl db, long sourceLsn) throws DatabaseException{
    databaseImpl.setEnvironmentImpl(db.getDbEnvironment());
  }

  // line 59 "../../../../MapLN.ump"
   public String toString(){
    return dumpString(0, true);
  }

  // line 63 "../../../../MapLN.ump"
   public String beginTag(){
    return BEGIN_TAG;
  }

  // line 67 "../../../../MapLN.ump"
   public String endTag(){
    return END_TAG;
  }

  // line 71 "../../../../MapLN.ump"
   public String dumpString(int nSpaces, boolean dumpTags){
    StringBuffer sb = new StringBuffer();
	sb.append(super.dumpString(nSpaces, dumpTags));
	sb.append('\n');
	sb.append(TreeUtils.indent(nSpaces));
	sb.append("<deleted val=\"").append(Boolean.toString(deleted));
	sb.append("\">");
	sb.append('\n');
	sb.append(databaseImpl.dumpString(nSpaces));
	return sb.toString();
  }


  /**
   * 
   * Log type for transactional entries.
   */
  // line 86 "../../../../MapLN.ump"
   protected LogEntryType getTransactionalLogType(){
    return LogEntryType.LOG_MAPLN_TRANSACTIONAL;
  }


  /**
   * 
   * @see LN#getLogType
   */
  // line 93 "../../../../MapLN.ump"
   public LogEntryType getLogType(){
    return LogEntryType.LOG_MAPLN;
  }


  /**
   * 
   * @see LN#getLogSize
   */
  // line 100 "../../../../MapLN.ump"
   public int getLogSize(){
    return super.getLogSize() + databaseImpl.getLogSize() + LogUtils.getBooleanLogSize();
  }


  /**
   * 
   * @see LN#writeToLog
   */
  // line 107 "../../../../MapLN.ump"
   public void writeToLog(ByteBuffer logBuffer){
    super.writeToLog(logBuffer);
	databaseImpl.writeToLog(logBuffer);
	LogUtils.writeBoolean(logBuffer, deleted);
  }


  /**
   * 
   * @see LN#readFromLog
   */
  // line 116 "../../../../MapLN.ump"
   public void readFromLog(ByteBuffer itemBuffer, byte entryTypeVersion) throws LogException{
    super.readFromLog(itemBuffer, entryTypeVersion);
	databaseImpl.readFromLog(itemBuffer, entryTypeVersion);
	deleted = LogUtils.readBoolean(itemBuffer);
  }


  /**
   * 
   * Dump additional fields. Done this way so the additional info can be within the XML tags defining the dumped log entry.
   */
  // line 125 "../../../../MapLN.ump"
   protected void dumpLogAdditional(StringBuffer sb, boolean verbose){
    databaseImpl.dumpLog(sb, true);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 13 "../../../../MapLN.ump"
  private static final String BEGIN_TAG = "<mapLN>" ;
// line 15 "../../../../MapLN.ump"
  private static final String END_TAG = "</mapLN>" ;
// line 17 "../../../../MapLN.ump"
  private DatabaseImpl databaseImpl ;
// line 19 "../../../../MapLN.ump"
  private boolean deleted ;

  
}