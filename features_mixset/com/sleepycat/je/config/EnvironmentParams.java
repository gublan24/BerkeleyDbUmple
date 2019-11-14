/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.config;
import de.ovgu.cide.jakutil.*;
import java.util.TreeSet;
import java.util.Map;
import java.util.Iterator;
import java.util.HashMap;
import java.io.FileWriter;
import java.io.File;

// line 3 "../../../../EnvironmentParams.ump"
// line 3 "../../../../loggingBase_EnvironmentParams.ump"
// line 3 "../../../../loggingConsoleHandler_EnvironmentParams.ump"
// line 3 "../../../../LoggingDbLogHandler_EnvironmentParams.ump"
// line 3 "../../../../LoggingFileHandler_EnvironmentParams.ump"
// line 3 "../../../../Evictor_EnvironmentParams.ump"
// line 3 "../../../../INCompressor_EnvironmentParams.ump"
// line 3 "../../../../CPTime_EnvironmentParams.ump"
// line 3 "../../../../CPBytes_EnvironmentParams.ump"
// line 3 "../../../../CheckpointerDaemon_EnvironmentParams.ump"
// line 3 "../../../../Checksum_EnvironmentParams.ump"
// line 3 "../../../../Latches_EnvironmentParams.ump"
// line 3 "../../../../LookAHEADCache_EnvironmentParams.ump"
// line 3 "../../../../FSync_EnvironmentParams.ump"
// line 3 "../../../../LoggingCleaner_EnvironmentParams.ump"
// line 3 "../../../../LoggingEvictor_EnvironmentParams.ump"
// line 3 "../../../../LoggingRecovery_EnvironmentParams.ump"
// line 3 "../../../../LoggingFine_EnvironmentParams.ump"
// line 3 "../../../../Derivative_Evictor_EvictorDaemon_EnvironmentParams.ump"
public class EnvironmentParams
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public EnvironmentParams()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 12 "../../../../EnvironmentParams.ump"
  public final static Map SUPPORTED_PARAMS = new HashMap() ;

// line 14 ../../../../EnvironmentParams.ump
  public static final LongConfigParam MAX_MEMORY = new LongConfigParam("je.maxMemory", null, null, new Long(0), true,
	    "# Specify the cache size in bytes, as an absolute number. The system\n"
		    + "# attempts to stay within this budget and will evict database\n"
		    + "# objects when it comes within a prescribed margin of the limit.\n"
		    + "# By default, this parameter is 0 and JE instead sizes the cache\n"
		    + "# proportionally to the memory available to the JVM, based on\n" + "# je.maxMemoryPercent.");

    public static final IntConfigParam MAX_MEMORY_PERCENT = new IntConfigParam("je.maxMemoryPercent", new Integer(1),
	    new Integer(90), new Integer(60), true,
	    "# By default, JE sizes the cache as a percentage of the maximum\n"
		    + "# memory available to the JVM. For example, if the JVM is\n"
		    + "# started with -Xmx128M, the cache size will be\n"
		    + "#           (je.maxMemoryPercent * 128M) / 100\n"
		    + "# Setting je.maxMemory to an non-zero value will override\n" + "# je.maxMemoryPercent");

    public static final BooleanConfigParam ENV_RECOVERY = new BooleanConfigParam("je.env.recovery", true, false,
	    "# If true, an environment is created with recovery and the related\n" + "# daemons threads enabled.");

    public static final BooleanConfigParam ENV_RECOVERY_FORCE_CHECKPOINT = new BooleanConfigParam(
	    "je.env.recoveryForceCheckpoint", false, false,
	    "# If true, a checkpoint is forced following recovery, even if the\n" + "# log ends with a checkpoint.");

    public static final BooleanConfigParam ENV_RUN_INCOMPRESSOR = new BooleanConfigParam("je.env.runINCompressor", true,
	    true, "# If true, starts up the INCompressor.\n" + "# This parameter is true by default");

    public static final BooleanConfigParam ENV_RUN_EVICTOR = new BooleanConfigParam("je.env.runEvictor", false, true,
	    "# If true, starts up the evictor.\n" + "# This parameter is false by default\n"
		    + "# (deprecated, eviction is performed in-line");

    public static final BooleanConfigParam ENV_RUN_CHECKPOINTER = new BooleanConfigParam("je.env.runCheckpointer", true,
	    true, "# If true, starts up the checkpointer.\n" + "# This parameter is true by default");

    public static final BooleanConfigParam ENV_RUN_CLEANER = new BooleanConfigParam("je.env.runCleaner", true, true,
	    "# If true, starts up the cleaner.\n" + "# This parameter is true by default");

    public static final BooleanConfigParam ENV_CHECK_LEAKS = new BooleanConfigParam("je.env.checkLeaks", true, false,
	    "# Debugging support: check leaked locks and txns at env close.");

    public static final BooleanConfigParam ENV_FORCED_YIELD = new BooleanConfigParam("je.env.forcedYield", false, false,
	    "# Debugging support: call Thread.yield() at strategic points.");

    public static final BooleanConfigParam ENV_INIT_TXN = new BooleanConfigParam("je.env.isTransactional", false, false,
	    "# If true, create the environment w/ transactions.");

    public static final BooleanConfigParam ENV_INIT_LOCKING = new BooleanConfigParam("je.env.isLocking", true, false,
	    "# If true, create the environment with locking.");

    public static final BooleanConfigParam ENV_RDONLY = new BooleanConfigParam("je.env.isReadOnly", false, false,
	    "# If true, create the environment read only.");

    public static final int MIN_LOG_BUFFER_SIZE = 2048;

    private static final int NUM_LOG_BUFFERS_DEFAULT = 3;

    public static final long LOG_MEM_SIZE_MIN = NUM_LOG_BUFFERS_DEFAULT * MIN_LOG_BUFFER_SIZE;

    public static final String LOG_MEM_SIZE_MIN_STRING = Long.toString(LOG_MEM_SIZE_MIN);

    public static final LongConfigParam LOG_MEM_SIZE = new LongConfigParam("je.log.totalBufferBytes",
	    new Long(LOG_MEM_SIZE_MIN), null, new Long(0), false,
	    "# The total memory taken by log buffers, in bytes. If 0, use\n" + "# 7% of je.maxMemory");

    public static final IntConfigParam NUM_LOG_BUFFERS = new IntConfigParam("je.log.numBuffers", new Integer(2), null,
	    new Integer(NUM_LOG_BUFFERS_DEFAULT), false, "# The number of JE log buffers");

    public static final IntConfigParam LOG_BUFFER_MAX_SIZE = new IntConfigParam("je.log.bufferSize",
	    new Integer(1 << 10), null, new Integer(1 << 20), false, "# maximum starting size of a JE log buffer");

    public static final IntConfigParam LOG_FAULT_READ_SIZE = new IntConfigParam("je.log.faultReadSize", new Integer(32),
	    null, new Integer(2048), false, "# The buffer size for faulting in objects from disk, in bytes.");

    public static final IntConfigParam LOG_ITERATOR_READ_SIZE = new IntConfigParam("je.log.iteratorReadSize",
	    new Integer(128), null, new Integer(8192), false,
	    "# The read buffer size for log iterators, which are used when\n"
		    + "# scanning the log during activities like log cleaning and\n"
		    + "# environment open, in bytes. This may grow as the system encounters\n"
		    + "# larger log entries");

    public static final IntConfigParam LOG_ITERATOR_MAX_SIZE = new IntConfigParam("je.log.iteratorMaxSize",
	    new Integer(128), null, new Integer(16777216), false,
	    "# The maximum read buffer size for log iterators, which are used\n"
		    + "# when scanning the log during activities like log cleaning\n"
		    + "# and environment open, in bytes.");

    public static final LongConfigParam LOG_FILE_MAX = new LongConfigParam("je.log.fileMax", new Long(1000000),
	    new Long(4294967296L), new Long(10000000), false,
	    "# The maximum size of each individual JE log file, in bytes.");

    public static final BooleanConfigParam LOG_MEMORY_ONLY = new BooleanConfigParam("je.log.memOnly", false, false,
	    "# If true, operates in an in-memory fashion without\n"
		    + "# flushing the log to disk. The system operates until it runs\n"
		    + "# out of memory, in which case a java.lang.OutOfMemory error is\n" + "# thrown.");

    public static final IntConfigParam LOG_FILE_CACHE_SIZE = new IntConfigParam("je.log.fileCacheSize", new Integer(3),
	    null, new Integer(100), false, "# The size of the file handle cache.");

    public static final LongConfigParam LOG_CHUNKED_NIO = new LongConfigParam("je.log.chunkedNIO", new Long(0L),
	    new Long(1 << 26), new Long(0L), false, "# If non-0 (default is 0) break all IO into chunks of this size.\n"
		    + "# This setting is only used if je.log.useNIO=true.");

    public static final IntConfigParam NODE_MAX = new IntConfigParam("je.nodeMaxEntries", new Integer(4),
	    new Integer(32767), new Integer(128), false, "# The maximum number of entries in an internal btree node.\n"
		    + "# This can be set per-database using the DatabaseConfig object.");

    public static final IntConfigParam NODE_MAX_DUPTREE = new IntConfigParam("je.nodeDupTreeMaxEntries", new Integer(4),
	    new Integer(32767), new Integer(128), false,
	    "# The maximum number of entries in an internal dup btree node.\n"
		    + "# This can be set per-database using the DatabaseConfig object.");

    public static final IntConfigParam BIN_MAX_DELTAS = new IntConfigParam("je.tree.maxDelta", new Integer(0),
	    new Integer(100), new Integer(10), false, "# After this many deltas, logs a full version.");

    public static final IntConfigParam BIN_DELTA_PERCENT = new IntConfigParam("je.tree.binDelta", new Integer(0),
	    new Integer(75), new Integer(25), false, "# If less than this percentage of entries are changed on a BIN,\n"
		    + "# logs a delta instead of a full version.");

    public static final IntConfigParam COMPRESSOR_RETRY = new IntConfigParam("je.compressor.deadlockRetry",
	    new Integer(0), new Integer(Integer.MAX_VALUE), new Integer(3), false,
	    "# Number of times to retry a compression run if a deadlock occurs.");

    public static final LongConfigParam COMPRESSOR_LOCK_TIMEOUT = new LongConfigParam("je.compressor.lockTimeout",
	    new Long(0), new Long(4294967296L), new Long(500000L), false,
	    "# The lock timeout for compressor transactions in microseconds.");

    public static final BooleanConfigParam COMPRESSOR_PURGE_ROOT = new BooleanConfigParam("je.compressor.purgeRoot",
	    false, false,
	    "# If true, when the compressor encounters an empty tree, the root\n" + "# node of the tree is deleted.");

    public static final IntConfigParam CLEANER_MIN_UTILIZATION = new IntConfigParam("je.cleaner.minUtilization",
	    new Integer(0), new Integer(90), new Integer(50), true,
	    "# The cleaner will keep the total disk space utilization percentage\n"
		    + "# above this value. The default is set to 50 percent.");

    public static final IntConfigParam CLEANER_MIN_FILE_UTILIZATION = new IntConfigParam(
	    "je.cleaner.minFileUtilization", new Integer(0), new Integer(50), new Integer(5), true,
	    "# A log file will be cleaned if its utilization percentage is below\n"
		    + "# this value, irrespective of total utilization. The default is\n" + "# set to 5 percent.");

    public static final LongConfigParam CLEANER_BYTES_INTERVAL = new LongConfigParam("je.cleaner.bytesInterval",
	    new Long(0), new Long(Long.MAX_VALUE), new Long(0), true,
	    "# The cleaner checks disk utilization every time we write this many\n"
		    + "# bytes to the log.  If zero (and by default) it is set to the\n"
		    + "# je.log.fileMax value divided by four.");

    public static final IntConfigParam CLEANER_DEADLOCK_RETRY = new IntConfigParam("je.cleaner.deadlockRetry",
	    new Integer(0), new Integer(Integer.MAX_VALUE), new Integer(3), true,
	    "# The number of times to retry cleaning if a deadlock occurs.\n" + "# The default is set to 3.");

    public static final LongConfigParam CLEANER_LOCK_TIMEOUT = new LongConfigParam("je.cleaner.lockTimeout",
	    new Long(0), new Long(4294967296L), new Long(500000L), true,
	    "# The lock timeout for cleaner transactions in microseconds.\n" + "# The default is set to 0.5 seconds.");

    public static final BooleanConfigParam CLEANER_REMOVE = new BooleanConfigParam("je.cleaner.expunge", true, true,
	    "# If true, the cleaner deletes log files after successful cleaning.\n"
		    + "# If false, the cleaner changes log file extensions to .DEL\n"
		    + "# instead of deleting them. The default is set to true.");

    public static final IntConfigParam CLEANER_MIN_FILES_TO_DELETE = new IntConfigParam("je.cleaner.minFilesToDelete",
	    new Integer(1), new Integer(1000000), new Integer(5), false, "# (deprecated, no longer used");

    public static final IntConfigParam CLEANER_RETRIES = new IntConfigParam("je.cleaner.retries", new Integer(0),
	    new Integer(1000), new Integer(10), false, "# (deprecated, no longer used");

    public static final IntConfigParam CLEANER_RESTART_RETRIES = new IntConfigParam("je.cleaner.restartRetries",
	    new Integer(0), new Integer(1000), new Integer(5), false, "# (deprecated, no longer used");

    public static final IntConfigParam CLEANER_MIN_AGE = new IntConfigParam("je.cleaner.minAge", new Integer(1),
	    new Integer(1000), new Integer(2), true,
	    "# The minimum age of a file (number of files between it and the\n"
		    + "# active file) to qualify it for cleaning under any conditions.\n"
		    + "# The default is set to 2.");

    public static final BooleanConfigParam CLEANER_CLUSTER = new BooleanConfigParam("je.cleaner.cluster", false, true,
	    "# *** Experimental and may be removed in a future release. ***\n"
		    + "# If true, eviction and checkpointing will cluster records by key\n"
		    + "# value, migrating them from low utilization files if they are\n" + "# resident.\n"
		    + "# The cluster and clusterAll properties may not both be set to true.");

    public static final BooleanConfigParam CLEANER_CLUSTER_ALL = new BooleanConfigParam("je.cleaner.clusterAll", false,
	    true,
	    "# *** Experimental and may be removed in a future release. ***\n"
		    + "# If true, eviction and checkpointing will cluster records by key\n"
		    + "# value, migrating them from low utilization files whether or not\n" + "# they are resident.\n"
		    + "# The cluster and clusterAll properties may not both be set to true.");

    public static final IntConfigParam CLEANER_MAX_BATCH_FILES = new IntConfigParam("je.cleaner.maxBatchFiles",
	    new Integer(0), new Integer(100000), new Integer(0), true,
	    "# The maximum number of log files in the cleaner's backlog, or\n"
		    + "# zero if there is no limit.  Changing this property can impact the\n"
		    + "# performance of some out-of-memory applications.");

    public static final IntConfigParam CLEANER_READ_SIZE = new IntConfigParam("je.cleaner.readSize", new Integer(128),
	    null, new Integer(0), true, "# The read buffer size for cleaning.  If zero (the default), then\n"
		    + "# je.log.iteratorReadSize value is used.");

    public static final BooleanConfigParam CLEANER_TRACK_DETAIL = new BooleanConfigParam("je.cleaner.trackDetail", true,
	    false, "# If true, the cleaner tracks and stores detailed information that\n"
		    + "# is used to decrease the cost of cleaning.");

    public static final IntConfigParam CLEANER_DETAIL_MAX_MEMORY_PERCENTAGE = new IntConfigParam(
	    "je.cleaner.detailMaxMemoryPercentage", new Integer(1), new Integer(90), new Integer(2), true,
	    "# Tracking of detailed cleaning information will use no more than\n"
		    + "# this percentage of the cache.  The default value is two percent.\n"
		    + "# This setting is only used if je.cleaner.trackDetail=true.");

    public static final BooleanConfigParam CLEANER_RMW_FIX = new BooleanConfigParam("je.cleaner.rmwFix", true, false,
	    "# If true, detail information is discarded that was added by earlier\n"
		    + "# versions of JE if it may be invalid.  This may be set to false\n"
		    + "# for increased performance, but only if LockMode.RMW was never used.");

    public static final ConfigParam CLEANER_FORCE_CLEAN_FILES = new ConfigParam("je.cleaner.forceCleanFiles", "", false,
	    "# Specifies a list of files or file ranges to force clean.  This is\n"
		    + "# intended for use in forcing the cleaning of a large number of log\n"
		    + "# files.  File numbers are in hex and are comma separated or hyphen\n"
		    + "# separated to specify ranges, e.g.: '9,a,b-d' will clean 5 files.");

    public static final IntConfigParam CLEANER_THREADS = new IntConfigParam("je.cleaner.threads", new Integer(1), null,
	    new Integer(1), true,
	    "# The number of threads allocated by the cleaner for log file\n"
		    + "# processing.  If the cleaner backlog becomes large, increase this\n"
		    + "# value.  The default is set to 1.");

    public static final IntConfigParam N_LOCK_TABLES = new IntConfigParam("je.lock.nLockTables", new Integer(1),
	    new Integer(32767), new Integer(1), false,
	    "# Number of Lock Tables.  Set this to a value other than 1 when\n"
		    + "# an application has multiple threads performing concurrent JE\n"
		    + "# operations.  It should be set to a prime number, and in general\n"
		    + "# not higher than the number of application threads performing JE\n" + "# operations.");

    public static final LongConfigParam LOCK_TIMEOUT = new LongConfigParam("je.lock.timeout", new Long(0),
	    new Long(4294967296L), new Long(500000L), false, "# The lock timeout in microseconds.");

    public static final LongConfigParam TXN_TIMEOUT = new LongConfigParam("je.txn.timeout", new Long(0),
	    new Long(4294967296L), new Long(0), false,
	    "# The transaction timeout, in microseconds. A value of 0 means no limit.");

    public static final BooleanConfigParam TXN_SERIALIZABLE_ISOLATION = new BooleanConfigParam(
	    "je.txn.serializableIsolation", false, false,
	    "# Transactions have the Serializable (Degree 3) isolation level.  The\n"
		    + "# default is false, which implies the Repeatable Read isolation level.");

    public static final BooleanConfigParam TXN_DEADLOCK_STACK_TRACE = new BooleanConfigParam(
	    "je.txn.deadlockStackTrace", false, true,
	    "# Set this parameter to true to add stacktrace information to deadlock\n"
		    + "# (lock timeout) exception messages.  The stack trace will show where\n"
		    + "# each lock was taken.  The default is false, and true should only be\n"
		    + "# used during debugging because of the added memory/processing cost.\n"
		    + "# This parameter is 'static' across all environments.");

    public static final BooleanConfigParam TXN_DUMPLOCKS = new BooleanConfigParam("je.txn.dumpLocks", false, true,
	    "# Dump the lock table when a lock timeout is encountered, for\n" + "# debugging assistance.");

    public static void main(String argv[]) {
	if (argv.length != 1) {
	    throw new IllegalArgumentException("Usage: EnvironmentParams " + "<samplePropertyFile>");
	}
	try {
	    FileWriter exampleFile = new FileWriter(new File(argv[0]));
	    TreeSet paramNames = new TreeSet(SUPPORTED_PARAMS.keySet());
	    Iterator iter = paramNames.iterator();
	    exampleFile.write("####################################################\n"
		    + "# Example Berkeley DB, Java Edition property file\n"
		    + "# Each parameter is set to its default value\n"
		    + "####################################################\n\n");
	    while (iter.hasNext()) {
		String paramName = (String) iter.next();
		ConfigParam param = (ConfigParam) SUPPORTED_PARAMS.get(paramName);
		exampleFile.write(param.getDescription() + "\n");
		String extraDesc = param.getExtraDescription();
		if (extraDesc != null) {
		    exampleFile.write(extraDesc + "\n");
		}
		exampleFile.write("#" + param.getName() + "=" + param.getDefault() + "\n# (mutable at run time: "
			+ param.isMutable() + ")\n\n");
	    }
	    exampleFile.close();
	} catch (Exception e) {
	    e.printStackTrace();
	    System.exit(-1);
	}
    }

    static void addSupportedParam(ConfigParam param) {
	SUPPORTED_PARAMS.put(param.getName(), param);
    }
// line 5 ../../../../loggingBase_EnvironmentParams.ump
  public static final ConfigParam JE_LOGGING_LEVEL = new ConfigParam("java.util.logging.level", "FINEST", false,
	    "# Trace messages equal and above this level will be logged.\n"
		    + "# Value should be one of the predefined java.util.logging.Level values");// line 5 "../../../../loggingConsoleHandler_EnvironmentParams.ump"
  public static final BooleanConfigParam JE_LOGGING_CONSOLE = new BooleanConfigParam(
	    "java.util.logging.ConsoleHandler.on", false, false, "# Use ConsoleHandler in logging system.") ;
// line 5 "../../../../LoggingDbLogHandler_EnvironmentParams.ump"
  public static final BooleanConfigParam JE_LOGGING_DBLOG = new BooleanConfigParam(
	    "java.util.logging.DbLogHandler.on", true, false, "# Use DbLogHandler in logging system.") ;
// line 5 "../../../../LoggingFileHandler_EnvironmentParams.ump"
  public static final BooleanConfigParam JE_LOGGING_FILE = new BooleanConfigParam("java.util.logging.FileHandler.on",
	    true, false, "# Use FileHandler in logging system.") ;
// line 8 "../../../../LoggingFileHandler_EnvironmentParams.ump"
  public static final IntConfigParam JE_LOGGING_FILE_LIMIT = new IntConfigParam("java.util.logging.FileHandler.limit",
	    new Integer(1000), new Integer(1000000000), new Integer(10000000), false,
	    "# Log file limit for FileHandler.") ;
// line 12 "../../../../LoggingFileHandler_EnvironmentParams.ump"
  public static final IntConfigParam JE_LOGGING_FILE_COUNT = new IntConfigParam("java.util.logging.FileHandler.count",
	    new Integer(1), null, new Integer(10), false, "# Log file count for FileHandler.") ;

// line 5 ../../../../Evictor_EnvironmentParams.ump
  public static final LongConfigParam EVICTOR_EVICT_BYTES = new LongConfigParam("je.evictor.evictBytes",
	    new Long(1024), null, new Long(524288), false,
	    "# When eviction happens, the evictor will push memory usage to this\n"
		    + "# number of bytes below je.maxMemory.  The default is 512KB and the\n"
		    + "# minimum is 1 KB (1024).");

    public static final IntConfigParam EVICTOR_USEMEM_FLOOR = new IntConfigParam("je.evictor.useMemoryFloor",
	    new Integer(50), new Integer(100), new Integer(95), false,
	    "# When eviction happens, the evictor will push memory usage to this\n" + "# percentage of je.maxMemory."
		    + "# (deprecated in favor of je.evictor.evictBytes");

    public static final IntConfigParam EVICTOR_NODE_SCAN_PERCENTAGE = new IntConfigParam(
	    "je.evictor.nodeScanPercentage", new Integer(1), new Integer(100), new Integer(10), false,
	    "# The evictor percentage of total nodes to scan per wakeup.\n"
		    + "# (deprecated in favor of je.evictor.nodesPerScan");

    public static final IntConfigParam EVICTOR_EVICTION_BATCH_PERCENTAGE = new IntConfigParam(
	    "je.evictor.evictionBatchPercentage", new Integer(1), new Integer(100), new Integer(10), false,
	    "# The evictor percentage of scanned nodes to evict per wakeup.\n" + "# (deprecated)");

    public static final IntConfigParam EVICTOR_NODES_PER_SCAN = new IntConfigParam("je.evictor.nodesPerScan",
	    new Integer(1), new Integer(1000), new Integer(10), false, "# The number of nodes in one evictor scan");

    public static final IntConfigParam EVICTOR_CRITICAL_PERCENTAGE = new IntConfigParam("je.evictor.criticalPercentage",
	    new Integer(0), new Integer(1000), new Integer(0), false,
	    "# At this percentage over the allotted cache, critical eviction\n" + "# will start."
		    + "# (deprecated, eviction is performed in-line");

    public static final BooleanConfigParam EVICTOR_LRU_ONLY = new BooleanConfigParam("je.evictor.lruOnly", true, false,
	    "# If true (the default), use an LRU-only policy to select nodes for\n"
		    + "# eviction.  If false, select by Btree level first, and then by LRU.");// line 5 "../../../../INCompressor_EnvironmentParams.ump"
  public static final LongConfigParam COMPRESSOR_WAKEUP_INTERVAL = new LongConfigParam("je.compressor.wakeupInterval",
	    new Long(1000000), new Long(4294967296L), new Long(5000000), false,
	    "# The compressor wakeup interval in microseconds.") ;

// line 5 ../../../../CPTime_EnvironmentParams.ump
  public static final LongConfigParam CHECKPOINTER_WAKEUP_INTERVAL = new LongConfigParam(
	    "je.checkpointer.wakeupInterval", new Long(1000000), new Long(4294967296L), new Long(0), false,
	    "# The checkpointer wakeup interval in microseconds. By default, this\n"
		    + "# is inactive and we wakeup the checkpointer as a function of the\n"
		    + "# number of bytes written to the log. (je.checkpointer.bytesInterval)");
// line 5 ../../../../CPBytes_EnvironmentParams.ump
  public static final LongConfigParam CHECKPOINTER_BYTES_INTERVAL = new LongConfigParam(
	    "je.checkpointer.bytesInterval", new Long(0), new Long(Long.MAX_VALUE), new Long(20000000), false,
	    "# Ask the checkpointer to run every time we write this many bytes\n"
		    + "# to the log. If set, supercedes je.checkpointer.wakeupInterval. To\n"
		    + "# use time based checkpointing, set this to 0.");// line 5 "../../../../CheckpointerDaemon_EnvironmentParams.ump"
  public static final IntConfigParam CHECKPOINTER_RETRY = new IntConfigParam("je.checkpointer.deadlockRetry",
	    new Integer(0), new Integer(Integer.MAX_VALUE), new Integer(3), false,
	    "# The number of times to retry a checkpoint if it runs into a deadlock.") ;
// line 5 "../../../../Checksum_EnvironmentParams.ump"
  public static final BooleanConfigParam LOG_CHECKSUM_READ = new BooleanConfigParam("je.log.checksumRead", true,
	    false, "# If true, perform a checksum check when reading entries from log.") ;

// line 5 ../../../../Latches_EnvironmentParams.ump
  public static final BooleanConfigParam ENV_FAIR_LATCHES = new BooleanConfigParam("je.env.fairLatches", false, false,
	    "# If true, use latches instead of synchronized blocks to\n"
		    + "# implement the lock table and log write mutexes. Latches require\n"
		    + "# that threads queue to obtain the mutex in question and\n"
		    + "# therefore guarantee that there will be no mutex starvation, but \n"
		    + "# do incur a performance penalty. Latches should not be necessary in\n"
		    + "# most cases, so synchronized blocks are the default. An application\n"
		    + "# that puts heavy load on JE with threads with different thread\n"
		    + "# priorities might find it useful to use latches.  In a Java 5 JVM,\n"
		    + "# where java.util.concurrent.locks.ReentrantLock is used for the\n"
		    + "# latch implementation, this parameter will determine whether they\n"
		    + "# are 'fair' or not.  This parameter is 'static' across all\n" + "# environments.\n");
// line 5 ../../../../LookAHEADCache_EnvironmentParams.ump
  public static final IntConfigParam CLEANER_LOOK_AHEAD_CACHE_SIZE = new IntConfigParam(
	    "je.cleaner.lookAheadCacheSize", new Integer(0), null, new Integer(8192), true,
	    "# The look ahead cache size for cleaning in bytes.  Increasing this\n"
		    + "# value can reduce the number of Btree lookups.");// line 5 "../../../../FSync_EnvironmentParams.ump"
  public static final LongConfigParam LOG_FSYNC_TIMEOUT = new LongConfigParam("je.log.fsyncTimeout", new Long(10000L),
	    null, new Long(500000L), false, "# Timeout limit for group file sync, in microseconds.") ;

// line 5 ../../../../LoggingCleaner_EnvironmentParams.ump
  public static final ConfigParam JE_LOGGING_LEVEL_CLEANER = new ConfigParam("java.util.logging.level.cleaner",
	    "FINE", true, "# Cleaner specific detailed trace messages will be issued at this\n"
		    + "# level. The Value should be one of the predefined \n" + "# java.util.logging.Level values");
// line 5 ../../../../LoggingEvictor_EnvironmentParams.ump
  public static final ConfigParam JE_LOGGING_LEVEL_EVICTOR = new ConfigParam("java.util.logging.level.evictor",
	    "FINE", false, "# Evictor specific trace messages will be issued at this level.\n"
		    + "# Value should be one of the predefined java.util.logging.Level values");
// line 5 ../../../../LoggingRecovery_EnvironmentParams.ump
  public static final ConfigParam JE_LOGGING_LEVEL_RECOVERY = new ConfigParam("java.util.logging.level.recovery",
	    "FINE", false, "# Recovery specific trace messages will be issued at this level.\n"
		    + "# Value should be one of the predefined java.util.logging.Level values");
// line 5 ../../../../LoggingFine_EnvironmentParams.ump
  public static final ConfigParam JE_LOGGING_LEVEL_LOCKMGR = new ConfigParam("java.util.logging.level.lockMgr",
	    "FINE", false, "# Lock manager specific trace messages will be issued at this level.\n"
		    + "# Value should be one of the predefined java.util.logging.Level values");// line 5 "../../../../Derivative_Evictor_EvictorDaemon_EnvironmentParams.ump"
  public static final IntConfigParam EVICTOR_RETRY = new IntConfigParam("je.evictor.deadlockRetry", new Integer(0),
	    new Integer(Integer.MAX_VALUE), new Integer(3), false,
	    "# The number of times to retry the evictor if it runs into a deadlock.") ;

  
}