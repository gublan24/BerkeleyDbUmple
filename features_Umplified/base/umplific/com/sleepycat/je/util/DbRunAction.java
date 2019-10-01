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
// line 3 "../../../../DbRunAction_static.ump"
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
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  @MethodObject
  // line 4 "../../../../DbRunAction_static.ump"
  public static class DbRunAction_main
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public DbRunAction_main()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../DbRunAction_static.ump"
    public  DbRunAction_main(String [] argv){
      this.argv=argv;
    }
  
    // line 9 "../../../../DbRunAction_static.ump"
    public void execute(){
      recoveryStart=0;
          actionStart=0;
          actionEnd=0;
          try {
            whichArg=0;
            if (argv.length == 0) {
              usage();
              System.exit(1);
            }
            dbName=null;
            doAction=0;
            envHome=".";
            readOnly=false;
            while (whichArg < argv.length) {
              nextArg=argv[whichArg];
              if (nextArg.equals("-h")) {
                whichArg++;
                envHome=CmdUtil.getArg(argv,whichArg);
              }
     else           if (nextArg.equals("-a")) {
                whichArg++;
                action=CmdUtil.getArg(argv,whichArg);
                if (action.equalsIgnoreCase("clean")) {
                  doAction=CLEAN;
                }
     else {
                  this.hook841();
                }
              }
     else           if (nextArg.equals("-ro")) {
                readOnly=true;
              }
     else           if (nextArg.equals("-s")) {
                dbName=argv[++whichArg];
              }
     else {
                throw new IllegalArgumentException(nextArg + " is not a supported option.");
              }
              whichArg++;
            }
            envConfig=new EnvironmentConfig();
            this.hook848();
            this.hook847();
            this.hook845();
            recoveryStart=System.currentTimeMillis();
            env=new Environment(new File(envHome),envConfig);
            forceConfig=new CheckpointConfig();
            forceConfig.setForce(true);
            actionStart=System.currentTimeMillis();
            if (doAction == CLEAN) {
              while (true) {
                nFiles=env.cleanLog();
                System.out.println("Files cleaned: " + nFiles);
                if (nFiles == 0) {
                  break;
                }
              }
              env.checkpoint(forceConfig);
            }
            this.hook840();
            this.hook844();
            if (doAction == CHECKPOINT) {
              env.checkpoint(forceConfig);
            }
            this.hook842();
            this.hook838();
            actionEnd=System.currentTimeMillis();
            env.close();
          }
     catch (      Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            usage();
            System.exit(1);
          }
     finally {
            f=new DecimalFormat();
            f.setMaximumFractionDigits(2);
            recoveryDuration=actionStart - recoveryStart;
            System.out.println("\nrecovery time = " + f.format(recoveryDuration) + " millis "+ f.format((double)recoveryDuration / 60000)+ " minutes");
            actionDuration=actionEnd - actionStart;
            System.out.println("action time = " + f.format(actionDuration) + " millis "+ f.format(actionDuration / 60000)+ " minutes");
          }
    }
  
    // line 114 "../../../../DbRunAction_static.ump"
     protected void hook838() throws Exception{
      
    }
  
    // line 116 "../../../../DbRunAction_static.ump"
     protected void hook839() throws Exception{
      usage();
          System.exit(1);
    }
  
    // line 120 "../../../../DbRunAction_static.ump"
     protected void hook840() throws Exception{
      
    }
  
    // line 122 "../../../../DbRunAction_static.ump"
     protected void hook841() throws Exception{
      if (action.equalsIgnoreCase("checkpoint")) {
            doAction=CHECKPOINT;
          }
     else {
            this.hook846();
          }
    }
  
    // line 130 "../../../../DbRunAction_static.ump"
     protected void hook842() throws Exception{
      
    }
  
    // line 132 "../../../../DbRunAction_static.ump"
     protected void hook843() throws Exception{
      this.hook839();
    }
  
    // line 135 "../../../../DbRunAction_static.ump"
     protected void hook844() throws Exception{
      
    }
  
    // line 137 "../../../../DbRunAction_static.ump"
     protected void hook845() throws Exception{
      
    }
  
    // line 139 "../../../../DbRunAction_static.ump"
     protected void hook846() throws Exception{
      this.hook843();
    }
  
    // line 142 "../../../../DbRunAction_static.ump"
     protected void hook847() throws Exception{
      
    }
  
    // line 144 "../../../../DbRunAction_static.ump"
     protected void hook848() throws Exception{
      
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 93 "../../../../DbRunAction_static.ump"
    protected String[] argv ;
  // line 94 "../../../../DbRunAction_static.ump"
    protected long recoveryStart ;
  // line 95 "../../../../DbRunAction_static.ump"
    protected long actionStart ;
  // line 96 "../../../../DbRunAction_static.ump"
    protected long actionEnd ;
  // line 97 "../../../../DbRunAction_static.ump"
    protected int whichArg ;
  // line 98 "../../../../DbRunAction_static.ump"
    protected String dbName ;
  // line 99 "../../../../DbRunAction_static.ump"
    protected int doAction ;
  // line 100 "../../../../DbRunAction_static.ump"
    protected String envHome ;
  // line 101 "../../../../DbRunAction_static.ump"
    protected boolean readOnly ;
  // line 102 "../../../../DbRunAction_static.ump"
    protected String nextArg ;
  // line 103 "../../../../DbRunAction_static.ump"
    protected String action ;
  // line 104 "../../../../DbRunAction_static.ump"
    protected EnvironmentConfig envConfig ;
  // line 105 "../../../../DbRunAction_static.ump"
    protected Environment env ;
  // line 106 "../../../../DbRunAction_static.ump"
    protected CheckpointConfig forceConfig ;
  // line 107 "../../../../DbRunAction_static.ump"
    protected int nFiles ;
  // line 108 "../../../../DbRunAction_static.ump"
    protected DatabaseConfig dbConfig ;
  // line 109 "../../../../DbRunAction_static.ump"
    protected Database db ;
  // line 110 "../../../../DbRunAction_static.ump"
    protected DecimalFormat f ;
  // line 111 "../../../../DbRunAction_static.ump"
    protected long recoveryDuration ;
  // line 112 "../../../../DbRunAction_static.ump"
    protected long actionDuration ;
  
    
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 24 "../../../../DbRunAction.ump"
  private static final int CLEAN = 1 ;
// line 26 "../../../../DbRunAction.ump"
  private static final int CHECKPOINT = 4 ;

  
}