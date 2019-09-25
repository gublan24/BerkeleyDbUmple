/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.txn.Locker;
import com.sleepycat.je.dbi.GetMode;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.dbi.DatabaseImpl;
import com.sleepycat.je.dbi.CursorImpl;
import java.io.File;

// line 3 "../../../DbInternal.ump"
public class DbInternal
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DbInternal()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Proxy to Database.invalidate()
   */
  // line 16 "../../../DbInternal.ump"
   public static  void dbInvalidate(Database db){
    db.invalidate();
  }


  /**
   * 
   * Proxy to Database.setHandleLockOwnerTxn
   */
  // line 23 "../../../DbInternal.ump"
   public static  void dbSetHandleLocker(Database db, Locker locker){
    db.setHandleLocker(locker);
  }


  /**
   * 
   * Proxy to Environment.getDbEnvironment
   */
  // line 30 "../../../DbInternal.ump"
   public static  EnvironmentImpl envGetEnvironmentImpl(Environment env){
    return env.getEnvironmentImpl();
  }


  /**
   * 
   * Proxy to new Cursor(DatabaseImpl, Locker, CursorConfig)
   */
  // line 38 "../../../DbInternal.ump"
   public static  Cursor newCursor(DatabaseImpl dbImpl, Locker locker, CursorConfig cursorConfig) throws DatabaseException{
    return new Cursor(dbImpl, locker, cursorConfig);
  }


  /**
   * 
   * Proxy to new Cursor.position().
   */
  // line 46 "../../../DbInternal.ump"
   public static  OperationStatus position(Cursor cursor, DatabaseEntry key, DatabaseEntry data, LockMode lockMode, boolean first) throws DatabaseException{
    return cursor.position(key, data, lockMode, first);
  }


  /**
   * 
   * Proxy to new Cursor.retrieveNext().
   */
  // line 54 "../../../DbInternal.ump"
   public static  OperationStatus retrieveNext(Cursor cursor, DatabaseEntry key, DatabaseEntry data, LockMode lockMode, GetMode getMode) throws DatabaseException{
    return cursor.retrieveNext(key, data, lockMode, getMode);
  }


  /**
   * 
   * Proxy to Cursor.advanceCursor()
   */
  // line 61 "../../../DbInternal.ump"
   public static  boolean advanceCursor(Cursor cursor, DatabaseEntry key, DatabaseEntry data){
    return cursor.advanceCursor(key, data);
  }


  /**
   * 
   * Proxy to Cursor.getCursorImpl()
   */
  // line 68 "../../../DbInternal.ump"
   public static  CursorImpl getCursorImpl(Cursor cursor){
    return cursor.getCursorImpl();
  }


  /**
   * 
   * Proxy to Database.getDatabase()
   */
  // line 75 "../../../DbInternal.ump"
   public static  DatabaseImpl dbGetDatabaseImpl(Database db){
    return db.getDatabaseImpl();
  }


  /**
   * 
   * Proxy to JoinCursor.getSortedCursors()
   */
  // line 82 "../../../DbInternal.ump"
   public static  Cursor[] getSortedCursors(JoinCursor cursor){
    return cursor.getSortedCursors();
  }


  /**
   * 
   * Proxy to EnvironmentConfig.setLoadPropertyFile()
   */
  // line 89 "../../../DbInternal.ump"
   public static  void setLoadPropertyFile(EnvironmentConfig config, boolean loadProperties){
    config.setLoadPropertyFile(loadProperties);
  }


  /**
   * 
   * Proxy to EnvironmentConfig.setCreateUP()
   */
  // line 96 "../../../DbInternal.ump"
   public static  void setCreateUP(EnvironmentConfig config, boolean checkpointUP){
    config.setCreateUP(checkpointUP);
  }


  /**
   * 
   * Proxy to EnvironmentConfig.getCreateUP()
   */
  // line 103 "../../../DbInternal.ump"
   public static  boolean getCreateUP(EnvironmentConfig config){
    return config.getCreateUP();
  }


  /**
   * 
   * Proxy to EnvironmentConfig.setCheckpointUP()
   */
  // line 110 "../../../DbInternal.ump"
   public static  void setCheckpointUP(EnvironmentConfig config, boolean checkpointUP){
    config.setCheckpointUP(checkpointUP);
  }


  /**
   * 
   * Proxy to EnvironmentConfig.getCheckpointUP()
   */
  // line 117 "../../../DbInternal.ump"
   public static  boolean getCheckpointUP(EnvironmentConfig config){
    return config.getCheckpointUP();
  }


  /**
   * 
   * Proxy to EnvironmentConfig.setTxnReadCommitted()
   */
  // line 124 "../../../DbInternal.ump"
   public static  void setTxnReadCommitted(EnvironmentConfig config, boolean txnReadCommitted){
    config.setTxnReadCommitted(txnReadCommitted);
  }


  /**
   * 
   * Proxy to EnvironmentConfig.setTxnReadCommitted()
   */
  // line 131 "../../../DbInternal.ump"
   public static  boolean getTxnReadCommitted(EnvironmentConfig config){
    return config.getTxnReadCommitted();
  }


  /**
   * 
   * Proxy to EnvironmentConfig.cloneConfig()
   */
  // line 138 "../../../DbInternal.ump"
   public static  EnvironmentConfig cloneConfig(EnvironmentConfig config){
    return config.cloneConfig();
  }


  /**
   * 
   * Proxy to EnvironmentMutableConfig.cloneMutableConfig()
   */
  // line 145 "../../../DbInternal.ump"
   public static  EnvironmentMutableConfig cloneMutableConfig(EnvironmentMutableConfig config){
    return config.cloneMutableConfig();
  }


  /**
   * 
   * Proxy to EnvironmentMutableConfig.checkImmutablePropsForEquality()
   */
  // line 153 "../../../DbInternal.ump"
   public static  void checkImmutablePropsForEquality(EnvironmentMutableConfig config, EnvironmentMutableConfig passedConfig) throws IllegalArgumentException{
    config.checkImmutablePropsForEquality(passedConfig);
  }


  /**
   * 
   * Proxy to EnvironmentMutableConfig.copyMutablePropsTo()
   */
  // line 160 "../../../DbInternal.ump"
   public static  void copyMutablePropsTo(EnvironmentMutableConfig config, EnvironmentMutableConfig toConfig){
    config.copyMutablePropsTo(toConfig);
  }


  /**
   * 
   * Proxy to EnvironmentMutableConfig.validateParams.
   */
  // line 167 "../../../DbInternal.ump"
   public static  void disableParameterValidation(EnvironmentMutableConfig config){
    config.setValidateParams(false);
  }


  /**
   * 
   * Proxy to DatabaseConfig.setUseExistingConfig()
   */
  // line 174 "../../../DbInternal.ump"
   public static  void setUseExistingConfig(DatabaseConfig config, boolean useExistingConfig){
    config.setUseExistingConfig(useExistingConfig);
  }


  /**
   * 
   * Proxy to DatabaseConfig.match(DatabaseConfig()
   */
  // line 181 "../../../DbInternal.ump"
   public static  void databaseConfigValidate(DatabaseConfig config1, DatabaseConfig config2) throws DatabaseException{
    config1.validate(config2);
  }


  /**
   * 
   * Proxy to Transaction.getLocker()
   */
  // line 188 "../../../DbInternal.ump"
   public static  Locker getLocker(Transaction txn) throws DatabaseException{
    return txn.getLocker();
  }


  /**
   * 
   * Proxy to Environment.getDefaultTxnConfig()
   */
  // line 195 "../../../DbInternal.ump"
   public static  TransactionConfig getDefaultTxnConfig(Environment env){
    return env.getDefaultTxnConfig();
  }


  /**
   * 
   * Get an Environment only if the environment is already open. This will register this Enviroment in the EnvironmentImpl's reference count, but will not configure the environment.
   * @return null if the environment is not already open.
   */
  // line 203 "../../../DbInternal.ump"
   public static  Environment getEnvironmentShell(File environmentHome){
    Environment env = null;
	try {
	    env = new Environment(environmentHome);
	    if (env.getEnvironmentImpl() == null) {
		env = null;
	    }
	} catch (DatabaseException e) {
	}
	return env;
  }

}