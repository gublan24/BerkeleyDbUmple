/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.compat;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.TransactionConfig;
import com.sleepycat.je.Transaction;
import com.sleepycat.je.SecondaryDatabase;
import com.sleepycat.je.SecondaryCursor;
import com.sleepycat.je.SecondaryConfig;
import com.sleepycat.je.OperationStatus;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.Environment;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.Database;
import com.sleepycat.je.CursorConfig;
import com.sleepycat.je.Cursor;
import java.util.Comparator;
import java.io.*;

// line 3 "../../../DbCompat.ump"
public class DbCompat
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DbCompat()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 50 "../../../DbCompat.ump"
   public static  boolean getInitializeLocking(EnvironmentConfig config){
    return true;
  }

  // line 54 "../../../DbCompat.ump"
   public static  boolean getInitializeCDB(EnvironmentConfig config){
    return false;
  }

  // line 58 "../../../DbCompat.ump"
   public static  boolean isTypeBtree(DatabaseConfig dbConfig){
    return true;
  }

  // line 62 "../../../DbCompat.ump"
   public static  boolean isTypeHash(DatabaseConfig dbConfig){
    return false;
  }

  // line 66 "../../../DbCompat.ump"
   public static  boolean isTypeQueue(DatabaseConfig dbConfig){
    return false;
  }

  // line 70 "../../../DbCompat.ump"
   public static  boolean isTypeRecno(DatabaseConfig dbConfig){
    return false;
  }

  // line 74 "../../../DbCompat.ump"
   public static  boolean getBtreeRecordNumbers(DatabaseConfig dbConfig){
    return false;
  }

  // line 78 "../../../DbCompat.ump"
   public static  boolean getReadUncommitted(DatabaseConfig dbConfig){
    return true;
  }

  // line 82 "../../../DbCompat.ump"
   public static  boolean getRenumbering(DatabaseConfig dbConfig){
    return false;
  }

  // line 86 "../../../DbCompat.ump"
   public static  boolean getSortedDuplicates(DatabaseConfig dbConfig){
    return dbConfig.getSortedDuplicates();
  }

  // line 90 "../../../DbCompat.ump"
   public static  boolean getUnsortedDuplicates(DatabaseConfig dbConfig){
    return false;
  }

  // line 94 "../../../DbCompat.ump"
   public static  CursorConfig cloneCursorConfig(CursorConfig config){
    CursorConfig newConfig = new CursorConfig();
	newConfig.setReadCommitted(config.getReadCommitted());
	newConfig.setReadUncommitted(config.getReadUncommitted());
	return newConfig;
  }

  // line 101 "../../../DbCompat.ump"
   public static  boolean getWriteCursor(CursorConfig config){
    return false;
  }

  // line 105 "../../../DbCompat.ump"
   public static  void setWriteCursor(CursorConfig config, boolean write){
    if (write) {
	    throw new UnsupportedOperationException();
	}
  }

  // line 111 "../../../DbCompat.ump"
   public static  void setRecordNumber(DatabaseEntry entry, int recNum){
    throw new UnsupportedOperationException();
  }

  // line 115 "../../../DbCompat.ump"
   public static  int getRecordNumber(DatabaseEntry entry){
    throw new UnsupportedOperationException();
  }

  // line 119 "../../../DbCompat.ump"
   public static  String getDatabaseFile(Database db) throws DatabaseException{
    return null;
  }

  // line 124 "../../../DbCompat.ump"
   public static  OperationStatus getCurrentRecordNumber(Cursor cursor, DatabaseEntry key, LockMode lockMode) throws DatabaseException{
    throw new UnsupportedOperationException();
  }

  // line 129 "../../../DbCompat.ump"
   public static  OperationStatus getSearchRecordNumber(Cursor cursor, DatabaseEntry key, DatabaseEntry data, LockMode lockMode) throws DatabaseException{
    throw new UnsupportedOperationException();
  }

  // line 134 "../../../DbCompat.ump"
   public static  OperationStatus getSearchRecordNumber(SecondaryCursor cursor, DatabaseEntry key, DatabaseEntry pKey, DatabaseEntry data, LockMode lockMode) throws DatabaseException{
    throw new UnsupportedOperationException();
  }

  // line 139 "../../../DbCompat.ump"
   public static  OperationStatus putAfter(Cursor cursor, DatabaseEntry key, DatabaseEntry data) throws DatabaseException{
    throw new UnsupportedOperationException();
  }

  // line 144 "../../../DbCompat.ump"
   public static  OperationStatus putBefore(Cursor cursor, DatabaseEntry key, DatabaseEntry data) throws DatabaseException{
    throw new UnsupportedOperationException();
  }

  // line 149 "../../../DbCompat.ump"
   public static  OperationStatus append(Database db, Transaction txn, DatabaseEntry key, DatabaseEntry data) throws DatabaseException{
    throw new UnsupportedOperationException();
  }

  // line 153 "../../../DbCompat.ump"
   public static  Transaction getThreadTransaction(Environment env) throws DatabaseException{
    return env.getThreadTransaction();
  }

  // line 157 "../../../DbCompat.ump"
   public static  void setInitializeCache(EnvironmentConfig config, boolean val){
    if (!val) {
	    throw new UnsupportedOperationException();
	}
  }

  // line 163 "../../../DbCompat.ump"
   public static  void setInitializeLocking(EnvironmentConfig config, boolean val){
    if (!val) {
	    throw new UnsupportedOperationException();
	}
  }

  // line 169 "../../../DbCompat.ump"
   public static  void setInitializeCDB(EnvironmentConfig config, boolean val){
    if (val) {
	    throw new UnsupportedOperationException();
	}
  }

  // line 175 "../../../DbCompat.ump"
   public static  void setLockDetectModeOldest(EnvironmentConfig config){
    
  }

  // line 178 "../../../DbCompat.ump"
   public static  void setSerializableIsolation(TransactionConfig config, boolean val){
    config.setSerializableIsolation(val);
  }

  // line 182 "../../../DbCompat.ump"
   public static  void setBtreeComparator(DatabaseConfig dbConfig, Comparator comparator){
    dbConfig.setBtreeComparator(comparator.getClass());
  }

  // line 186 "../../../DbCompat.ump"
   public static  void setTypeBtree(DatabaseConfig dbConfig){
    
  }

  // line 189 "../../../DbCompat.ump"
   public static  void setTypeHash(DatabaseConfig dbConfig){
    throw new UnsupportedOperationException();
  }

  // line 193 "../../../DbCompat.ump"
   public static  void setTypeRecno(DatabaseConfig dbConfig){
    throw new UnsupportedOperationException();
  }

  // line 197 "../../../DbCompat.ump"
   public static  void setTypeQueue(DatabaseConfig dbConfig){
    throw new UnsupportedOperationException();
  }

  // line 201 "../../../DbCompat.ump"
   public static  void setBtreeRecordNumbers(DatabaseConfig dbConfig, boolean val){
    throw new UnsupportedOperationException();
  }

  // line 205 "../../../DbCompat.ump"
   public static  void setReadUncommitted(DatabaseConfig dbConfig, boolean val){
    
  }

  // line 208 "../../../DbCompat.ump"
   public static  void setRenumbering(DatabaseConfig dbConfig, boolean val){
    throw new UnsupportedOperationException();
  }

  // line 212 "../../../DbCompat.ump"
   public static  void setSortedDuplicates(DatabaseConfig dbConfig, boolean val){
    dbConfig.setSortedDuplicates(val);
  }

  // line 216 "../../../DbCompat.ump"
   public static  void setUnsortedDuplicates(DatabaseConfig dbConfig, boolean val){
    if (val) {
	    throw new UnsupportedOperationException();
	}
  }

  // line 222 "../../../DbCompat.ump"
   public static  void setRecordLength(DatabaseConfig dbConfig, int val){
    if (val != 0) {
	    throw new UnsupportedOperationException();
	}
  }

  // line 228 "../../../DbCompat.ump"
   public static  void setRecordPad(DatabaseConfig dbConfig, int val){
    throw new UnsupportedOperationException();
  }

  // line 233 "../../../DbCompat.ump"
   public static  Database openDatabase(Environment env, Transaction txn, String file, String name, DatabaseConfig config) throws DatabaseException,FileNotFoundException{
    return env.openDatabase(txn, makeDbName(file, name), config);
  }

  // line 238 "../../../DbCompat.ump"
   public static  SecondaryDatabase openSecondaryDatabase(Environment env, Transaction txn, String file, String name, Database primary, SecondaryConfig config) throws DatabaseException,FileNotFoundException{
    return env.openSecondaryDatabase(txn, makeDbName(file, name), primary, config);
  }

  // line 242 "../../../DbCompat.ump"
   private static  String makeDbName(String file, String name){
    if (file == null) {
	    return name;
	} else {
	    if (name != null) {
		return file + '.' + name;
	    } else {
		return file;
	    }
	}
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 23 "../../../DbCompat.ump"
  public static final boolean CDB = false ;
// line 25 "../../../DbCompat.ump"
  public static final boolean JOIN = true ;
// line 27 "../../../DbCompat.ump"
  public static final boolean NESTED_TRANSACTIONS = false ;
// line 29 "../../../DbCompat.ump"
  public static final boolean INSERTION_ORDERED_DUPLICATES = false ;
// line 31 "../../../DbCompat.ump"
  public static final boolean SEPARATE_DATABASE_FILES = false ;
// line 33 "../../../DbCompat.ump"
  public static final boolean MEMORY_SUBSYSTEM = false ;
// line 35 "../../../DbCompat.ump"
  public static final boolean LOCK_SUBSYSTEM = false ;
// line 37 "../../../DbCompat.ump"
  public static final boolean HASH_METHOD = false ;
// line 39 "../../../DbCompat.ump"
  public static final boolean RECNO_METHOD = false ;
// line 41 "../../../DbCompat.ump"
  public static final boolean QUEUE_METHOD = false ;
// line 43 "../../../DbCompat.ump"
  public static final boolean BTREE_RECNUM_METHOD = false ;
// line 45 "../../../DbCompat.ump"
  public static final boolean OPTIONAL_READ_UNCOMMITTED = false ;
// line 47 "../../../DbCompat.ump"
  public static final boolean SECONDARIES = true ;

  
}