/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.util;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.CmdUtil;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.config.EnvironmentParams;
import com.sleepycat.je.Transaction;
import com.sleepycat.je.OperationStatus;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.EnvironmentMutableConfig;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.Environment;
import com.sleepycat.je.DbInternal;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.Database;
import com.sleepycat.je.Cursor;
import com.sleepycat.je.CheckpointConfig;
import java.text.DecimalFormat;
import java.io.File;

// line 3 "../../../../DbRunAction.ump"
public class DbRunAction
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DbRunAction()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 29 "../../../../DbRunAction.ump"
   public static  void main(String [] argv){
    Thread.currentThread().setUncaughtExceptionHandler(new com.sleepycat.je.util.DbRecover.UmpleExceptionHandler());
    Thread.setDefaultUncaughtExceptionHandler(new com.sleepycat.je.util.DbRecover.UmpleExceptionHandler());
    new DbRunAction_main(argv).execute();
  }

  // line 33 "../../../../DbRunAction.ump"
   private static  String getSecs(long start, long end){
    return (end - start) / 1000 + " secs";
  }

  // line 37 "../../../../DbRunAction.ump"
   private static  void preload(Environment env, String dbName) throws DatabaseException{
    System.out.println("Preload starting");
	Database db = env.openDatabase(null, dbName, null);
	Cursor cursor = db.openCursor(null, null);
	try {
	    DatabaseEntry key = new DatabaseEntry();
	    DatabaseEntry data = new DatabaseEntry();
	    int count = 0;
	    while (cursor.getNext(key, data, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
		count++;
		if ((count % 50000) == 0) {
		    System.out.println(count + "...");
		}
	    }
	    System.out.println("Preloaded " + count + " records");
	} finally {
	    cursor.close();
	    db.close();
	}
  }

  // line 58 "../../../../DbRunAction.ump"
   private static  void usage(){
    System.out.println("Usage: \n " + CmdUtil.getJavaCommand(DbRunAction.class));
	System.out.println("  -h <environment home> ");
	System.out.println("  -a <clean|compress|evict|checkpoint|removedb>");
	System.out.println("  -ro (read-only - defaults to read-write)");
	System.out.println("  -s <dbName> (for preloading of evict or db remove)");
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 24 "../../../../DbRunAction.ump"
  private static final int CLEAN = 1 ;
// line 26 "../../../../DbRunAction.ump"
  private static final int CHECKPOINT = 4 ;

  
}