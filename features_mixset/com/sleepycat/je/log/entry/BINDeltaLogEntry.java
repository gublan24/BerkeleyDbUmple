/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log.entry;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.tree.IN;
import com.sleepycat.je.tree.BINDelta;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.dbi.DatabaseId;
import com.sleepycat.je.DatabaseException;

// line 3 "../../../../../BINDeltaLogEntry.ump"
public class BINDeltaLogEntry extends SingleItemLogEntry implements INContainingEntry
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public BINDeltaLogEntry(LogReadable aItem)
  {
    super(aItem);
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
   * @param logClass
   */
  // line 17 "../../../../../BINDeltaLogEntry.ump"
   public  BINDeltaLogEntry(Class logClass){
    super(logClass);
  }

  // line 21 "../../../../../BINDeltaLogEntry.ump"
   public IN getIN(EnvironmentImpl env) throws DatabaseException{
    BINDelta delta = (BINDelta) getMainItem();
	return delta.reconstituteBIN(env);
  }

  // line 26 "../../../../../BINDeltaLogEntry.ump"
   public DatabaseId getDbId(){
    BINDelta delta = (BINDelta) getMainItem();
	return delta.getDbId();
  }


  /**
   * 
   * @return the LSN that represents this IN. For this BINDelta, it'sthe last full version.
   */
  // line 34 "../../../../../BINDeltaLogEntry.ump"
   public long getLsnOfIN(long lastReadLsn){
    BINDelta delta = (BINDelta) getMainItem();
	return delta.getLastFullLsn();
  }

}