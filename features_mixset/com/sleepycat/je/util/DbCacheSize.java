/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.util;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.CmdUtil;
import com.sleepycat.je.dbi.MemoryBudget;
import com.sleepycat.je.OperationStatus;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.Environment;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.Database;
import java.util.Random;
import java.text.NumberFormat;
import java.math.BigInteger;
import java.io.PrintStream;
import java.io.File;
import com.sleepycat.je.EnvironmentStats;

// line 3 "../../../../MemoryBudget_DbCacheSize.ump"
// line 3 "../../../../MemoryBudget_DbCacheSize_inner.ump"
// line 3 "../../../../Derivative_Statistics_MemoryBudget_DbCacheSize.ump"
// line 3 "../../../../Derivative_Statistics_MemoryBudget_DbCacheSize_inner.ump"
public class DbCacheSize
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DbCacheSize()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 8 "../../../../Derivative_Statistics_MemoryBudget_DbCacheSize.ump"
   private static  void printStats(PrintStream out, Environment env, String msg) throws DatabaseException{
    out.println();
			out.println(msg + ':');
			EnvironmentStats stats = env.getStats(null);
			out.println("CacheSize=" + INT_FORMAT.format(stats.getCacheTotalBytes()) + " BtreeSize="
				+ INT_FORMAT.format(stats.getCacheDataBytes()));
			if (stats.getNNodesScanned() > 0) {
					out.println("*** All records did not fit in the cache ***");
			}
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 7 "../../../../MemoryBudget_DbCacheSize_inner.ump"
  // line 4 "../../../../Derivative_Statistics_MemoryBudget_DbCacheSize_inner.ump"
  public static class DbCacheSize_insertRecords
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public DbCacheSize_insertRecords()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 11 "../../../../MemoryBudget_DbCacheSize_inner.ump"
     public  DbCacheSize_insertRecords(PrintStream out, Environment env, Database db, long records, int keySize, int dataSize, boolean randomKeys) throws DatabaseException{
      DatabaseEntry key = new DatabaseEntry();
        DatabaseEntry data = new DatabaseEntry(new byte[dataSize]);
        BigInteger bigInt = BigInteger.ZERO;
        Random rnd = new Random(123);
  
        for (int i = 0; i < records; i += 1) {
  
            if (randomKeys) {
                byte[] a = new byte[keySize];
                rnd.nextBytes(a);
                key.setData(a);
            } else {
                bigInt = bigInt.add(BigInteger.ONE);
                byte[] a = bigInt.toByteArray();
                if (a.length < keySize) {
                    byte[] a2 = new byte[keySize];
                    System.arraycopy(a, 0, a2, a2.length - a.length, a.length);
                    a = a2;
                } else if (a.length > keySize) {
                    out.println("*** Key doesn't fit value=" + bigInt +
                                " byte length=" + a.length);
                    return;
                }
                key.setData(a);
            }
  
            OperationStatus status = db.putNoOverwrite(null, key, data);
            if (status == OperationStatus.KEYEXIST && randomKeys) {
                i -= 1;
                out.println("Random key already exists -- retrying");
                continue;
            }
            if (status != OperationStatus.SUCCESS) {
                out.println("*** " + status);
                return;
            }
  
            if (i % 10000 == 0) {
              Label833:
  stats=env.getStats(null);
          if (stats.getNNodesScanned() > 0) {
            out.println("*** Ran out of cache memory at record " + i + " -- try increasing the Java heap size ***");
            return; //throw new ReturnVoid();
          }
         // original();
   //this.hook833();
      
                out.print(".");
                out.flush();
            }
        }
    }
  
    // line 59 "../../../../MemoryBudget_DbCacheSize_inner.ump"
    public void execute() throws DatabaseException{
      // code move to DbCacheSize_insertRecords(..)
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 62 "../../../../MemoryBudget_DbCacheSize_inner.ump"
    protected PrintStream out ;
  // line 63 "../../../../MemoryBudget_DbCacheSize_inner.ump"
    protected Environment env ;
  // line 64 "../../../../MemoryBudget_DbCacheSize_inner.ump"
    protected Database db ;
  // line 65 "../../../../MemoryBudget_DbCacheSize_inner.ump"
    protected long records ;
  // line 66 "../../../../MemoryBudget_DbCacheSize_inner.ump"
    protected int keySize ;
  // line 67 "../../../../MemoryBudget_DbCacheSize_inner.ump"
    protected int dataSize ;
  // line 68 "../../../../MemoryBudget_DbCacheSize_inner.ump"
    protected boolean randomKeys ;
  // line 69 "../../../../MemoryBudget_DbCacheSize_inner.ump"
    protected DatabaseEntry key ;
  // line 70 "../../../../MemoryBudget_DbCacheSize_inner.ump"
    protected DatabaseEntry data ;
  // line 71 "../../../../MemoryBudget_DbCacheSize_inner.ump"
    protected BigInteger bigInt ;
  // line 72 "../../../../MemoryBudget_DbCacheSize_inner.ump"
    protected Random rnd ;
  // line 73 "../../../../MemoryBudget_DbCacheSize_inner.ump"
    protected byte[] a ;
  // line 74 "../../../../MemoryBudget_DbCacheSize_inner.ump"
    protected byte[] a2 ;
  // line 75 "../../../../MemoryBudget_DbCacheSize_inner.ump"
    protected byte[] a3 ;
  // line 76 "../../../../MemoryBudget_DbCacheSize_inner.ump"
    protected OperationStatus status ;
  // line 77 "../../../../MemoryBudget_DbCacheSize_inner.ump"
    protected EnvironmentStats stats ;
  
    
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 20 "../../../../MemoryBudget_DbCacheSize.ump"
  private static final NumberFormat INT_FORMAT = NumberFormat.getIntegerInstance() ;

// line 22 ../../../../MemoryBudget_DbCacheSize.ump
  private static final String HEADER = "    Cache Size      Btree Size  Description\n"
	    + "--------------  --------------  -----------";

    private static final int COLUMN_WIDTH = 14;

    private static final int COLUMN_SEPARATOR = 2;

    public static void main(String[] args) {
	try {
	    long records = 0;
	    int keySize = 0;
	    int dataSize = 0;
	    int nodeMax = 128;
	    int density = 80;
	    long overhead = 0;
	    File measureDir = null;
	    boolean measureRandom = false;
	    for (int i = 0; i < args.length; i += 1) {
		String name = args[i];
		String val = null;
		if (i < args.length - 1 && !args[i + 1].startsWith("-")) {
		    i += 1;
		    val = args[i];
		}
		if (name.equals("-records")) {
		    if (val == null) {
			usage("No value after -records");
		    }
		    try {
			records = Long.parseLong(val);
		    } catch (NumberFormatException e) {
			usage(val + " is not a number");
		    }
		    if (records <= 0) {
			usage(val + " is not a positive integer");
		    }
		} else if (name.equals("-key")) {
		    if (val == null) {
			usage("No value after -key");
		    }
		    try {
			keySize = Integer.parseInt(val);
		    } catch (NumberFormatException e) {
			usage(val + " is not a number");
		    }
		    if (keySize <= 0) {
			usage(val + " is not a positive integer");
		    }
		} else if (name.equals("-data")) {
		    if (val == null) {
			usage("No value after -data");
		    }
		    try {
			dataSize = Integer.parseInt(val);
		    } catch (NumberFormatException e) {
			usage(val + " is not a number");
		    }
		    if (dataSize <= 0) {
			usage(val + " is not a positive integer");
		    }
		} else if (name.equals("-nodemax")) {
		    if (val == null) {
			usage("No value after -nodemax");
		    }
		    try {
			nodeMax = Integer.parseInt(val);
		    } catch (NumberFormatException e) {
			usage(val + " is not a number");
		    }
		    if (nodeMax <= 0) {
			usage(val + " is not a positive integer");
		    }
		} else if (name.equals("-density")) {
		    if (val == null) {
			usage("No value after -density");
		    }
		    try {
			density = Integer.parseInt(val);
		    } catch (NumberFormatException e) {
			usage(val + " is not a number");
		    }
		    if (density < 1 || density > 100) {
			usage(val + " is not betwen 1 and 100");
		    }
		} else if (name.equals("-overhead")) {
		    if (val == null) {
			usage("No value after -overhead");
		    }
		    try {
			overhead = Long.parseLong(val);
		    } catch (NumberFormatException e) {
			usage(val + " is not a number");
		    }
		    if (overhead < 0) {
			usage(val + " is not a non-negative integer");
		    }
		} else if (name.equals("-measure")) {
		    if (val == null) {
			usage("No value after -measure");
		    }
		    measureDir = new File(val);
		} else if (name.equals("-measurerandom")) {
		    measureRandom = true;
		} else {
		    usage("Unknown arg: " + name);
		}
	    }
	    if (records == 0) {
		usage("-records not specified");
	    }
	    if (keySize == 0) {
		usage("-key not specified");
	    }
	    printCacheSizes(System.out, records, keySize, dataSize, nodeMax, density, overhead);
	    if (measureDir != null) {
		measure(System.out, measureDir, records, keySize, dataSize, nodeMax, measureRandom);
	    }
	} catch (Throwable e) {
	    e.printStackTrace(System.out);
	}
    }

    private static void usage(String msg) {
	if (msg != null) {
	    System.out.println(msg);
	}
	System.out.println("usage:" + "\njava " + CmdUtil.getJavaCommand(DbCacheSize.class) + "\n   -records <count>"
		+ "\n      # Total records (key/data pairs); required" + "\n   -key <bytes> "
		+ "\n      # Average key bytes per record; required" + "\n  [-data <bytes>]"
		+ "\n      # Average data bytes per record; if omitted no leaf"
		+ "\n      # node sizes are included in the output" + "\n  [-nodemax <entries>]"
		+ "\n      # Number of entries per Btree node; default: 128" + "\n  [-density <percentage>]"
		+ "\n      # Percentage of node entries occupied; default: 80" + "\n  [-overhead <bytes>]"
		+ "\n      # Overhead of non-Btree objects (log buffers, locks,"
		+ "\n      # etc); default: 10% of total cache size" + "\n  [-measure <environmentHomeDirectory>]"
		+ "\n      # An empty directory used to write a database to find"
		+ "\n      # the actual cache size; default: do not measure" + "\n  [-measurerandom"
		+ "\n      # With -measure insert randomly generated keys;"
		+ "\n      # default: insert sequential keys");
	System.exit(2);
    }

    private static void printCacheSizes(PrintStream out, long records, int keySize, int dataSize, int nodeMax,
	    int density, long overhead) {
	out.println("Inputs:" + " records=" + records + " keySize=" + keySize + " dataSize=" + dataSize + " nodeMax="
		+ nodeMax + " density=" + density + '%' + " overhead=" + ((overhead > 0) ? overhead : 10) + "%");
	int nodeAvg = (nodeMax * density) / 100;
	long nBinEntries = (records * nodeMax) / nodeAvg;
	long nBinNodes = (nBinEntries + nodeMax - 1) / nodeMax;
	long nInNodes = 0;
	int nLevels = 1;
	for (long n = nBinNodes; n > 0; n /= nodeMax) {
	    nInNodes += n;
	    nLevels += 1;
	}
	long minInSize = nInNodes * calcInSize(nodeMax, nodeAvg, keySize, true);
	long maxInSize = nInNodes * calcInSize(nodeMax, nodeAvg, keySize, false);
	long lnSize = 0;
	if (dataSize > 0) {
	    lnSize = records * calcLnSize(dataSize);
	}
	out.println();
	out.println(HEADER);
	out.println(line(minInSize, overhead, "Minimum, internal nodes only"));
	out.println(line(maxInSize, overhead, "Maximum, internal nodes only"));
	if (dataSize > 0) {
	    out.println(line(minInSize + lnSize, overhead, "Minimum, internal nodes and leaf nodes"));
	    out.println(line(maxInSize + lnSize, overhead, "Maximum, internal nodes and leaf nodes"));
	} else {
	    out.println("\nTo get leaf node sizing specify -data");
	}
	out.println("\nBtree levels: " + nLevels);
    }

    private static int calcInSize(int nodeMax, int nodeAvg, int keySize, boolean lsnCompression) {
	int size = MemoryBudget.IN_FIXED_OVERHEAD;
	size += MemoryBudget.byteArraySize(nodeMax) + (nodeMax * (2 * MemoryBudget.ARRAY_ITEM_OVERHEAD));
	if (lsnCompression) {
	    size += MemoryBudget.byteArraySize(nodeMax * 2);
	} else {
	    size += MemoryBudget.BYTE_ARRAY_OVERHEAD + (nodeMax * MemoryBudget.LONG_OVERHEAD);
	}
	size += (nodeAvg + 1) * MemoryBudget.byteArraySize(keySize);
	return size;
    }

    private static int calcLnSize(int dataSize) {
	return MemoryBudget.LN_OVERHEAD + MemoryBudget.byteArraySize(dataSize);
    }

    private static String line(long btreeSize, long overhead, String comment) {
	long cacheSize;
	if (overhead == 0) {
	    cacheSize = (100 * btreeSize) / 90;
	} else {
	    cacheSize = btreeSize + overhead;
	}
	StringBuffer buf = new StringBuffer(100);
	column(buf, INT_FORMAT.format(cacheSize));
	column(buf, INT_FORMAT.format(btreeSize));
	column(buf, comment);
	return buf.toString();
    }

    private static void column(StringBuffer buf, String str) {
	int start = buf.length();
	while (buf.length() - start + str.length() < COLUMN_WIDTH) {
	    buf.append(' ');
	}
	buf.append(str);
	for (int i = 0; i < COLUMN_SEPARATOR; i += 1) {
	    buf.append(' ');
	}
    }

    private static void measure(PrintStream out, File dir, long records, int keySize, int dataSize, int nodeMax,
	    boolean randomKeys) throws DatabaseException {
			String[] fileNames = dir.list();
			if (fileNames != null && fileNames.length > 0) {
					usage("Directory is not empty: " + dir);
			}
			Environment env = openEnvironment(dir, true);
			Database db = openDatabase(env, nodeMax, true);
			try {
					out.println("\nMeasuring with cache size: " + INT_FORMAT.format(env.getConfig().getCacheSize()));
					insertRecords(out, env, db, records, keySize, dataSize, randomKeys);

				  Label832:				 // hook832(out, env);
					db.close();
					env.close();
					env = openEnvironment(dir, false);
					db = openDatabase(env, nodeMax, false);
					out.println("\nPreloading with cache size: " + INT_FORMAT.format(env.getConfig().getCacheSize()));
					preloadRecords(out, db);
				  Label831:			;		//hook831(out, env);
			} finally {
					try {
				db.close();
				env.close();
					} catch (Exception e) {
				out.println("During close: " + e);
					}
			}
    }

    private static Environment openEnvironment(File dir, boolean allowCreate) throws DatabaseException {
	EnvironmentConfig envConfig = new EnvironmentConfig();
	envConfig.setAllowCreate(allowCreate);
	envConfig.setCachePercent(90);
	return new Environment(dir, envConfig);
    }

    private static Database openDatabase(Environment env, int nodeMax, boolean allowCreate) throws DatabaseException {
	DatabaseConfig dbConfig = new DatabaseConfig();
	dbConfig.setAllowCreate(allowCreate);
	dbConfig.setNodeMaxEntries(nodeMax);
	return env.openDatabase(null, "foo", dbConfig);
    }

    private static void insertRecords(PrintStream out, Environment env, Database db, long records, int keySize,
	    int dataSize, boolean randomKeys) throws DatabaseException {
	new DbCacheSize_insertRecords(out, env, db, records, keySize, dataSize, randomKeys).execute();
    }

    private static void preloadRecords(final PrintStream out, final Database db) throws DatabaseException {
	Thread thread = new Thread() {
	    public void run() {
		while (true) {
		    try {
			out.print(".");
			out.flush();
			Thread.sleep(5 * 1000);
		    } catch (InterruptedException e) {
			break;
		    }
		}
	    }
	};
	thread.start();
	db.preload(0);
	thread.interrupt();
	try {
	    thread.join();
	} catch (InterruptedException e) {
	    e.printStackTrace(out);
	}
    }

  //  protected static void hook831(PrintStream out, Environment env) throws DatabaseException {
  //  }

  //  protected static void hook832(PrintStream out, Environment env) throws DatabaseException {
  //  }
  
}