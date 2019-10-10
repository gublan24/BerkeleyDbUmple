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
// line 3 "../../../../loggingConsoleHandler_DbRunAction.ump"
// line 3 "../../../../DbRunAction_inner.ump"
// line 3 "../../../../LoggingDbLogHandler_DbRunAction.ump"
// line 3 "../../../../LoggingDbLogHandler_DbRunAction_inner.ump"
// line 3 "../../../../Evictor_DbRunAction.ump"
// line 3 "../../../../Evictor_DbRunAction_inner.ump"
// line 3 "../../../../DeleteOp_DbRunAction.ump"
// line 3 "../../../../DeleteOp_DbRunAction_inner.ump"
// line 3 "../../../../INCompressor_DbRunAction.ump"
// line 3 "../../../../INCompressor_DbRunAction_inner.ump"
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

  // line 8 "../../../../Evictor_DbRunAction.ump"
   private static  void doEvict(Environment env) throws DatabaseException{
    new DbRunAction_doEvict(env).execute();
  }

  // line 8 "../../../../DeleteOp_DbRunAction.ump"
   private static  void removeAndClean(Environment env, String name) throws DatabaseException{
    long a, b, c, d, e, f;
			Transaction txn = env.beginTransaction(null, null);
			CheckpointConfig force = new CheckpointConfig();
			force.setForce(true);
			a = System.currentTimeMillis();
			env.removeDatabase(txn, name);
			b = System.currentTimeMillis();
			txn.commit();
			c = System.currentTimeMillis();
			int cleanedCount = 0;
			while (env.cleanLog() > 0) {
					cleanedCount++;
			}
			d = System.currentTimeMillis();
			System.out.println("cleanedCount=" + cleanedCount);
			e = 0;
			f = 0;
			if (cleanedCount > 0) {
					e = System.currentTimeMillis();
					env.checkpoint(force);
					f = System.currentTimeMillis();
			}
			System.out.println("Remove of " + name + " remove: " + getSecs(a, b) + " commit: " + getSecs(b, c) + " clean: "
				+ getSecs(c, d) + " checkpoint: " + getSecs(e, f));
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  @MethodObject
    @MethodObject
    @MethodObject
    @MethodObject
    @MethodObject
    @MethodObject
  // line 4 "../../../../DbRunAction_static.ump"
  // line 4 "../../../../DbRunAction_inner.ump"
  // line 4 "../../../../LoggingDbLogHandler_DbRunAction_inner.ump"
  // line 35 "../../../../Evictor_DbRunAction_inner.ump"
  // line 4 "../../../../DeleteOp_DbRunAction_inner.ump"
  // line 4 "../../../../INCompressor_DbRunAction_inner.ump"
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
                 Label841:
  if (action.equalsIgnoreCase("compress")) {
            doAction=COMPRESS;
          }
   //  else {
     //       original();
       //   }
  // this.hook841();
  							if (action.equalsIgnoreCase("checkpoint")) {
  											doAction=CHECKPOINT;
  										}
  											Label846: //this.hook846();
  											Label843:
  if (action.equalsIgnoreCase("removedb")) {
            doAction=REMOVEDB;
          }
  			// else { usage(); System.exit(1); } or// original();
  				   //}
   //this.hook843();
  							 else {
  
  											Label839:
  											 usage();
  											 System.exit(1);
  										}
  							//End of hook841
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
            Label848:
  envConfig.setConfigParam(EnvironmentParams.JE_LOGGING_CONSOLE.getName(),"true");
          //original(); //@Abdulaziz aaa
  
            Label847:
  if (readOnly) {
            envConfig.setConfigParam(EnvironmentParams.JE_LOGGING_DBLOG.getName(),"false");
            envConfig.setReadOnly(true);
          }
          //original();
  
            //this.hook845();
            Label845:
  if (doAction == EVICT) {
            envConfig.setConfigParam(EnvironmentParams.ENV_RUN_EVICTOR.getName(),"false");
            envConfig.setConfigParam(EnvironmentParams.EVICTOR_CRITICAL_PERCENTAGE.getName(),"1000");
          }
          //original();
  
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
            Label840:
  if (doAction == COMPRESS) {
            env.compress();
          }
          //original();
   //this.hook840();
            //this.hook844();
            Label844:
  if (doAction == EVICT) {
            preload(env,dbName);
          }
          //original();
  
            if (doAction == CHECKPOINT) {
              env.checkpoint(forceConfig);
            }
            Label842:
  if (doAction == REMOVEDB) {
            removeAndClean(env,dbName);}
         // original();
   //this.hook842();
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
  
    // line 128 "../../../../DbRunAction_static.ump"
     protected void hook838() throws Exception{
      
    }
  
  
    /**
     * protected void hook839() throws Exception {
     * usage();
     * System.exit(1);
     * }
     * protected void hook840() throws Exception {
     * }
     */
    // line 137 "../../../../DbRunAction_static.ump"
     protected void hook841() throws Exception{
      
    }
  
  
    /**
     * protected void hook842() throws Exception {
     * }
     * protected void hook843() throws Exception {
     * this.hook839();
     * }
     * protected void hook844() throws Exception {
     * }
     * protected void hook845() throws Exception {
     * }
     * protected void hook846() throws Exception {
     * this.hook843();
     * }
     */
    // line 151 "../../../../DbRunAction_static.ump"
     protected void hook847() throws Exception{
      
    }
  
    // line 153 "../../../../DbRunAction_static.ump"
     protected void hook848() throws Exception{
      
    }
  
    // line 50 "../../../../Evictor_DbRunAction_inner.ump"
     protected void hook846() throws Exception{
      if (action.equalsIgnoreCase("evict")) {
            doAction=EVICT;
          }
     else {
            original();
          }
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 107 "../../../../DbRunAction_static.ump"
    protected String[] argv ;
  // line 108 "../../../../DbRunAction_static.ump"
    protected long recoveryStart ;
  // line 109 "../../../../DbRunAction_static.ump"
    protected long actionStart ;
  // line 110 "../../../../DbRunAction_static.ump"
    protected long actionEnd ;
  // line 111 "../../../../DbRunAction_static.ump"
    protected int whichArg ;
  // line 112 "../../../../DbRunAction_static.ump"
    protected String dbName ;
  // line 113 "../../../../DbRunAction_static.ump"
    protected int doAction ;
  // line 114 "../../../../DbRunAction_static.ump"
    protected String envHome ;
  // line 115 "../../../../DbRunAction_static.ump"
    protected boolean readOnly ;
  // line 116 "../../../../DbRunAction_static.ump"
    protected String nextArg ;
  // line 117 "../../../../DbRunAction_static.ump"
    protected String action ;
  // line 118 "../../../../DbRunAction_static.ump"
    protected EnvironmentConfig envConfig ;
  // line 119 "../../../../DbRunAction_static.ump"
    protected Environment env ;
  // line 120 "../../../../DbRunAction_static.ump"
    protected CheckpointConfig forceConfig ;
  // line 121 "../../../../DbRunAction_static.ump"
    protected int nFiles ;
  // line 122 "../../../../DbRunAction_static.ump"
    protected DatabaseConfig dbConfig ;
  // line 123 "../../../../DbRunAction_static.ump"
    protected Database db ;
  // line 124 "../../../../DbRunAction_static.ump"
    protected DecimalFormat f ;
  // line 125 "../../../../DbRunAction_static.ump"
    protected long recoveryDuration ;
  // line 126 "../../../../DbRunAction_static.ump"
    protected long actionDuration ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  @MethodObject
  // line 4 "../../../../Evictor_DbRunAction_inner.ump"
  public static class DbRunAction_doEvict
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public DbRunAction_doEvict()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../Evictor_DbRunAction_inner.ump"
    public  DbRunAction_doEvict(Environment env){
      this.env=env;
    }
  
    // line 9 "../../../../Evictor_DbRunAction_inner.ump"
    public void execute() throws DatabaseException{
      envImpl=DbInternal.envGetEnvironmentImpl(env);
          //this.hook837();
          Label837:
          c=new EnvironmentMutableConfig();
          //this.hook836();
          Label836:
          env.setMutableConfig(c);
          start=System.currentTimeMillis();
          env.evictMemory();
          end=System.currentTimeMillis();
          f=new DecimalFormat();
          f.setMaximumFractionDigits(2);
          System.out.println("evict time=" + f.format(end - start));
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 23 "../../../../Evictor_DbRunAction_inner.ump"
    protected Environment env ;
  // line 24 "../../../../Evictor_DbRunAction_inner.ump"
    protected EnvironmentImpl envImpl ;
  // line 25 "../../../../Evictor_DbRunAction_inner.ump"
    protected long cacheUsage ;
  // line 26 "../../../../Evictor_DbRunAction_inner.ump"
    protected EnvironmentMutableConfig c ;
  // line 27 "../../../../Evictor_DbRunAction_inner.ump"
    protected long start ;
  // line 28 "../../../../Evictor_DbRunAction_inner.ump"
    protected long end ;
  // line 29 "../../../../Evictor_DbRunAction_inner.ump"
    protected DecimalFormat f ;
  
    
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 24 "../../../../DbRunAction.ump"
  private static final int CLEAN = 1 ;
// line 26 "../../../../DbRunAction.ump"
  private static final int CHECKPOINT = 4 ;
// line 5 "../../../../Evictor_DbRunAction.ump"
  private static final int EVICT = 3 ;
// line 5 "../../../../DeleteOp_DbRunAction.ump"
  private static final int REMOVEDB = 5 ;
// line 5 "../../../../INCompressor_DbRunAction.ump"
  private static final int COMPRESS = 2 ;

  
}