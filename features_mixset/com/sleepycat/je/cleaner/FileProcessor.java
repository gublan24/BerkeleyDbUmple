/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.cleaner;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.Tracer;
import com.sleepycat.je.utilint.DbLsn;
import com.sleepycat.je.utilint.DaemonThread;
import com.sleepycat.je.txn.LockType;
import com.sleepycat.je.txn.LockResult;
import com.sleepycat.je.txn.LockGrantType;
import com.sleepycat.je.txn.BasicLocker;
import com.sleepycat.je.tree.WithRootLatched;
import com.sleepycat.je.tree.TreeLocation;
import com.sleepycat.je.tree.Tree;
import com.sleepycat.je.tree.SearchResult;
import com.sleepycat.je.tree.LN;
import com.sleepycat.je.tree.IN;
import com.sleepycat.je.tree.DIN;
import com.sleepycat.je.tree.ChildReference;
import com.sleepycat.je.tree.BIN;
import com.sleepycat.je.log.CleanerFileReader;
import com.sleepycat.je.dbi.MemoryBudget;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.dbi.DbTree;
import com.sleepycat.je.dbi.DatabaseImpl;
import com.sleepycat.je.dbi.DatabaseId;
import com.sleepycat.je.DatabaseException;
import java.util.logging.Level;
import java.util.TreeMap;
import java.util.SortedMap;
import java.util.Set;
import java.util.Map;
import java.util.Iterator;
import java.util.HashSet;
import java.util.HashMap;
import java.io.IOException;
import com.sleepycat.je.utilint.*;

// line 3 "../../../../FileProcessor.ump"
// line 3 "../../../../FileProcessor_static.ump"
public class FileProcessor extends DaemonThread
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public FileProcessor()
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

  // line 60 "../../../../FileProcessor.ump"
  public  FileProcessor(String name, EnvironmentImpl env, Cleaner cleaner, UtilizationProfile profile, FileSelector fileSelector){
    super(0, name, env);
  this.env = env;
  this.cleaner = cleaner;
  this.fileSelector = fileSelector;
  this.profile = profile;
  }

  // line 68 "../../../../FileProcessor.ump"
   public void clearEnv(){
    env = null;
  cleaner = null;
  fileSelector = null;
  profile = null;
  }


  /**
   * 
   * Return the number of retries when a deadlock exception occurs.
   */
  // line 78 "../../../../FileProcessor.ump"
   protected int nDeadlockRetries() throws DatabaseException{
    return cleaner.nDeadlockRetries;
  }


  /**
   * 
   * Cleaner doesn't have a work queue so just throw an exception if it's ever called.
   */
  // line 85 "../../../../FileProcessor.ump"
   public void addToQueue(Object o) throws DatabaseException{
    throw new DatabaseException("Cleaner.addToQueue should never be called.");
  }


  /**
   * 
   * Activates the cleaner. Is normally called when je.cleaner.byteInterval bytes are written to the log.
   */
  // line 92 "../../../../FileProcessor.ump"
   public void onWakeup() throws DatabaseException{
    doClean(true, true, false);
  }


  /**
   * 
   * Cleans selected files and returns the number of files cleaned. May be called by the daemon thread or programatically.
   * @param invokedFromDaemoncurrently has no effect.
   * @param cleanMultipleFilesis true to clean until we're under budget, or false to cleanat most one file.
   * @param forceCleaningis true to clean even if we're not under the utilizationthreshold.
   * @return the number of files cleaned, not including files cleanedunsuccessfully.
   */
  // line 104 "../../../../FileProcessor.ump"
   public synchronized  int doClean(boolean invokedFromDaemon, boolean cleanMultipleFiles, boolean forceCleaning) throws DatabaseException{
    if (env.isClosed()) {
   return 0;
  }
  int nOriginalLogFiles = profile.getNumberOfFiles();
  int nFilesCleaned = 0;
  while (true) {
   if (nFilesCleaned >= nOriginalLogFiles) {
    break;
   }
   if (env.isClosing()) {
    break;
   }
   cleaner.processPending();
   cleaner.deleteSafeToDeleteFiles();
   boolean needLowUtilizationSet = cleaner.clusterResident || cleaner.clusterAll;
   Long fileNum = fileSelector.selectFileForCleaning(profile, forceCleaning, needLowUtilizationSet,
    cleaner.maxBatchFiles);
   cleaner.updateReadOnlyFileCollections();
   if (fileNum == null) {
    break;
   }
   Label138: ;//this.hook138();
    boolean finished = false;
   long fileNumValue = fileNum.longValue();
   int runId = ++cleaner.nCleanerRuns;
   try {
    String traceMsg = "CleanerRun " + runId + " on file 0x" + Long.toHexString(fileNumValue);
    Label139:   ; //traceMsg = this.hook139(traceMsg);
     Label121:   ; //this.hook121(traceMsg);
     if (DEBUG_TRACING) {
      System.out.println("\n" + traceMsg);
     }
    if (processFile(fileNum)) {
     fileSelector.addCleanedFile(fileNum);
     nFilesCleaned += 1;
     Label140:   ; //this.hook140();
      finished = true;
    }
   } catch (IOException IOE) {
    Label122:   ; //this.hook122(IOE);
     throw new DatabaseException(IOE);
   }
   finally {
    if (!finished) {
     fileSelector.putBackFileForCleaning(fileNum);
    }
    String traceMsg = "CleanerRun " + runId + " on file 0x" + Long.toHexString(fileNumValue) +
     " invokedFromDaemon=" + invokedFromDaemon + " finished=" + finished;
    Label141:   ; //traceMsg = this.hook141(traceMsg);
     Label123:   ; //this.hook123(traceMsg);
     if (DEBUG_TRACING) {
      System.out.println("\n" + traceMsg);
     }
   }
   if (!cleanMultipleFiles) {
    break;
   }
  }
  return nFilesCleaned;
  }


  /**
   * 
   * Process all log entries in the given file. Note that we check for obsolete entries using the active TFS (TrackedFileSummary) for a file while it is being processed, and we prohibit flushing (eviction) of that offset information until file processing is complete. An entry could become obsolete because: 1- normal application activity deletes or updates the entry, 2- proactive migration migrates the entry before we process it, or 3- if trackDetail is false. However, checking the TFS is expensive if it has many entries, because we perform a linear search. There is a tradeoff between the cost of the TFS lookup and its benefit, which is to avoid a tree search if the entry is obsolete. Note that many more lookups for non-obsolete entries than obsolete entries will typically be done. In spite of that we check the tracked summary to avoid the situation where eviction does proactive migration, and evicts a BIN that is very soon afterward fetched during cleaning.
   * @return false if we aborted file processing because the environment isbeing closed.
   */
  // line 170 "../../../../FileProcessor.ump"
   private boolean processFile(Long fileNum) throws DatabaseException,IOException{
    return new FileProcessor_processFile(this, fileNum).execute();
  }


  /**
   * 
   * Processes the first LN in the look ahead cache and removes it from the cache. While the BIN is latched, look through the BIN for other LNs in the cache; if any match, process them to avoid a tree search later.
   * @param info
   * @param offset
   */
  // line 180 "../../../../FileProcessor.ump"
   private void processLN(Long fileNum, TreeLocation location, Long offset, LNInfo info, Object lookAheadCachep, Map dbCache) throws DatabaseException{
    new FileProcessor_processLN(this, fileNum, location, offset, info, lookAheadCachep, dbCache).execute();
  }


  /**
   * 
   * Processes an LN that was found in the tree. Lock the LN's node ID and then set the entry's MIGRATE flag if the LSN of the LN log entry is the active LSN in the tree.
   * @param infoidentifies the LN log entry.
   * @param logLsnis the LSN of the log entry.
   * @param treeLsnis the LSN found in the tree.
   * @param binis the BIN found in the tree; is latched on method entry andexit.
   * @param indexis the BIN index found in the tree.
   * @param parentDINis non-null for a DupCountLN only; if non-null, is latched onmethod entry and exit.
   */
  // line 194 "../../../../FileProcessor.ump"
   private void processFoundLN(LNInfo info, long logLsn, long treeLsn, BIN bin, int index, DIN parentDIN) throws DatabaseException{
    LN ln = info.getLN();
  byte[] key = info.getKey();
  byte[] dupKey = info.getDupKey();
  DatabaseImpl db = bin.getDatabase();
  boolean isDupCountLN = parentDIN != null;
  boolean obsolete = false;
  boolean migrated = false;
  boolean lockDenied = false;
  boolean completed = false;
  long nodeId = ln.getNodeId();
  BasicLocker locker = null;
  try {
   Tree tree = db.getTree();
   assert tree != null;
   if (treeLsn != logLsn) {
    locker = new BasicLocker(env);
    LockResult lockRet = locker.nonBlockingLock(nodeId, LockType.READ, db);
    if (lockRet.getLockGrant() == LockGrantType.DENIED) {
     Label142:   ; //this.hook142();
      lockDenied = true;
    }
    else {
     Label143:   ; //this.hook143();
      obsolete = true;
    }
   }
   if (!obsolete && !lockDenied) {
    if (isDupCountLN) {
     ChildReference dclRef = parentDIN.getDupCountLNRef();
     dclRef.setMigrate(true);
     parentDIN.setDirty(true);
     if (treeLsn == logLsn && dclRef.getTarget() == null) {
      ln.postFetchInit(db, logLsn);
      parentDIN.updateDupCountLN(ln);
     }
    } else {
     bin.setMigrate(index, true);
     bin.setDirty(true);
     if (treeLsn == logLsn && bin.getTarget(index) == null) {
      ln.postFetchInit(db, logLsn);
      bin.updateEntry(index, ln);
     }
     if (PROHIBIT_DELTAS_WHEN_FETCHING && bin.getGeneration() == 0) {
      bin.setProhibitNextDelta();
     }
     bin.setGeneration();
    }
    Label144:   ; //this.hook144();
     migrated = true;
   }
   completed = true;
  } finally {
   if (locker != null) {
    locker.operationEnd();
   }
   if (completed && lockDenied) {
    fileSelector.addPendingLN(ln, db.getId(), key, dupKey);
   }
   Label124:   ; ;//this.hook124(logLsn, ln, obsolete, migrated, completed);
  }
  }


  /**
   * 
   * If an IN is still in use in the in-memory tree, dirty it. The checkpoint invoked at the end of the cleaning run will end up rewriting it.
   */
  // line 260 "../../../../FileProcessor.ump"
   private void processIN(IN inClone, DatabaseImpl db, long lsn) throws DatabaseException{
    try {
   boolean obsolete = false;
   boolean dirtied = false;
   boolean completed = false;
   //this.hook125(inClone, db, lsn, obsolete, dirtied, completed);
   Label125:;
    boolean b = db == null;
   //b = this.hook159(db, b);
   Label159:   ;
    if (b) {
     //this.hook160(db);
     Label160:   ; 
     Label151:   ; //this.hook151();
      obsolete = true;
     completed = true;
     return; //throw new ReturnVoid();
    }
   Tree tree = db.getTree();
   assert tree != null;
   IN inInTree = findINInTree(tree, db, inClone, lsn);
   if (inInTree == null) {
    Label152:   ; //this.hook152();
     obsolete = true;
   }
   else {
    Label153:   ; //this.hook153();
     inInTree.setDirty(true);
    inInTree.setProhibitNextDelta();
    Label136:   ; //this.hook136(inInTree);
     dirtied = true;
   }
   completed = true;
   //End of hook125
  } 
  finally {
    Label125_1: ; //;
  }
  }


  /**
   * 
   * Given a clone of an IN that has been taken out of the log, try to find it in the tree and verify that it is the current one in the log. Returns the node in the tree if it is found and it is current re: LSN's. Otherwise returns null if the clone is not found in the tree or it's not the latest version. Caller is responsible for unlatching the returned IN.
   */
  // line 305 "../../../../FileProcessor.ump"
   private IN findINInTree(Tree tree, DatabaseImpl db, IN inClone, long lsn) throws DatabaseException{
    try {
   if (inClone.isDbRoot()) {
    IN rootIN = isRoot(tree, db, inClone, lsn);
    if (rootIN == null) {
     return null;
    } else {
     return rootIN;
    }
   }
   inClone.latch(Cleaner.UPDATE_GENERATION);
   SearchResult result = null;
   Label134:   ; //this.hook134(tree, db, inClone, lsn, result);
    result = tree.getParentINForChildIN(inClone, true, Cleaner.UPDATE_GENERATION, inClone.getLevel(), null);
   if (!result.exactParentFound) {
     return null;
   }
   int compareVal = DbLsn.compareTo(result.parent.getLsn(result.index), lsn);
   if (compareVal > 0) {
     return null;
   } else {
    IN in ;
    if (compareVal == 0) {
     in = (IN) result.parent.getTarget(result.index);
     if ( in == null) {
      in = inClone; in .postFetchInit(db, lsn);
      result.parent.updateEntry(result.index, in );
     }
    } else {
     in = (IN) result.parent.fetchTarget(result.index);
    } in .latch(Cleaner.UPDATE_GENERATION);
        return in ;
   }
  } 
 finally {
    Label134_1: ;
      }
  }


  /**
   * 
   * Check if the cloned IN is the same node as the root in tree. Return the real root if it is, null otherwise. If non-null is returned, the returned IN (the root) is latched -- caller is responsible for unlatching it.
   */
  // line 347 "../../../../FileProcessor.ump"
   private IN isRoot(Tree tree, DatabaseImpl db, IN inClone, long lsn) throws DatabaseException{
    RootDoWork rdw = new RootDoWork(db, inClone, lsn);
  return tree.withRootLatchedShared(rdw);
  }


  /**
   * 
   * XXX: Was this intended to override Thread.toString()? If so it no longer does, because we separated Thread from DaemonThread.
   */
  // line 355 "../../../../FileProcessor.ump"
   public String toString(){
    StringBuffer sb = new StringBuffer();
  sb.append("<Cleaner name=\"").append(name).append("\"/>");
  return sb.toString();
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  import com.sleepycat.je.tree.*;
  
  // line 4 "../../../../FileProcessor_static.ump"
  public static class RootDoWork implements WithRootLatched
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public RootDoWork()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 10 "../../../../FileProcessor_static.ump"
    public  RootDoWork(DatabaseImpl db, IN inClone, long lsn){
      this.db=db;
          this.inClone=inClone;
          this.lsn=lsn;
    }
  
    // line 15 "../../../../FileProcessor_static.ump"
     public IN doWork(ChildReference root) throws DatabaseException{
      if (root == null || root.fetchTarget(db,null).getNodeId() != inClone.getNodeId()) {
            return null;
          }
          if (DbLsn.compareTo(root.getLsn(),lsn) <= 0) {
            IN rootIN=(IN)root.fetchTarget(db,null);
            rootIN.latch(Cleaner.UPDATE_GENERATION);
            return rootIN;
          }
     else {
            return null;
          }
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 6 "../../../../FileProcessor_static.ump"
    private DatabaseImpl db ;
  // line 7 "../../../../FileProcessor_static.ump"
    private IN inClone ;
  // line 8 "../../../../FileProcessor_static.ump"
    private long lsn ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 28 "../../../../FileProcessor_static.ump"
  public static class FileProcessor_processFile
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public FileProcessor_processFile()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 30 "../../../../FileProcessor_static.ump"
    public  FileProcessor_processFile(FileProcessor _this, Long fileNum){
      this._this=_this;
          this.fileNum=fileNum;
    }
  
    // line 34 "../../../../FileProcessor_static.ump"
    public boolean execute() throws DatabaseException,IOException{
      obsoleteOffsets=new PackedOffsets();
          tfs=_this.profile.getObsoleteDetail(fileNum,obsoleteOffsets,true);
          obsoleteIter=obsoleteOffsets.iterator();
          nextObsolete=-1;
          readBufferSize=_this.cleaner.readBufferSize;
          Label128:   ; //this.hook128();
          //this.hook161();
          Label161:   ;
          Label119:   ; //this.hook119();
          Label127:   ; //this.hook127();
          Label154:   ; //this.hook154();
          dbCache=new HashMap();
          try {
            reader=new CleanerFileReader(_this.env,readBufferSize,DbLsn.NULL_LSN,fileNum);
            Label137:   ; //this.hook137();
            dbMapTree=_this.env.getDbMapTree();
            location=new TreeLocation();
            nProcessedLNs=0;
            while (reader.readNextEntry()) {
              Label146:   ; //this.hook146();
              lsn=reader.getLastLsn();
              fileOffset=DbLsn.getFileOffset(lsn);
              isLN=reader.isLN();
              isIN=reader.isIN();
              isRoot=reader.isRoot();
              isObsolete=false;
              if (_this.env.isClosing()) {
                return false;
              }
              while (nextObsolete < fileOffset && obsoleteIter.hasNext()) {
                nextObsolete=obsoleteIter.next();
              }
              if (nextObsolete == fileOffset) {
                isObsolete=true;
              }
              if (!isObsolete && !isLN && !isIN&& !isRoot) {
                isObsolete=true;
              }
              if (!isObsolete && isLN && reader.getLN().isDeleted()) {
                isObsolete=true;
              }
              if (!isObsolete && tfs != null && tfs.containsObsoleteOffset(fileOffset)) {
                isObsolete=true;
              }
              if (isObsolete) {
                Label147:   ; //this.hook147();
                Label156:   ; //this.hook156();
                continue;
              }
              Label120:   ; //this.hook120();
              if (isLN) {
                targetLN=reader.getLN();
                dbId2=reader.getDatabaseId();
                key=reader.getKey();
                dupKey=reader.getDupTreeKey();
                aLsn=new Long(DbLsn.getFileOffset(lsn));
                aLninfo=new LNInfo(targetLN,dbId2,key,dupKey);
                Label130:   ; //this.hook130();
    						p=null;
          			Label131:   ; //this.hook131();
          			_this.processLN(fileNum,location,aLsn,aLninfo,p,dbCache);
  //end hook130
                nProcessedLNs+=1;
                if (nProcessedLNs % _this.PROCESS_PENDING_EVERY_N_LNS == 0) {
                  _this.cleaner.processPending();
                }
              }
     else           if (isIN) {
                targetIN=reader.getIN();
                dbId3=reader.getDatabaseId();
                db3=dbMapTree.getDb(dbId3,_this.cleaner.lockTimeout,dbCache);
                targetIN.setDatabase(db3);
                _this.processIN(targetIN,db3,lsn);
              }
     else           if (isRoot) {
                _this.env.rewriteMapTreeRoot(lsn);
              }
     else {
                assert false;
              }
            }
            Label129:   ; //this.hook129();
            Label155:   ; //this.hook155();
            Label145:   ; //this.hook145();
          }
      finally {
            //this.hook162();
            Label162:   ;
            if (tfs != null) {
              tfs.setAllowFlush(true);
            }
          }
          return true;
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 128 "../../../../FileProcessor_static.ump"
    protected FileProcessor _this ;
  // line 129 "../../../../FileProcessor_static.ump"
    protected Long fileNum ;
  // line 130 "../../../../FileProcessor_static.ump"
    protected PackedOffsets obsoleteOffsets ;
  // line 131 "../../../../FileProcessor_static.ump"
    protected TrackedFileSummary tfs ;
  // line 132 "../../../../FileProcessor_static.ump"
    protected PackedOffsets.Iterator obsoleteIter ;
  // line 133 "../../../../FileProcessor_static.ump"
    protected long nextObsolete ;
  // line 134 "../../../../FileProcessor_static.ump"
    protected int readBufferSize ;
  // line 135 "../../../../FileProcessor_static.ump"
    protected int lookAheadCacheSize ;
  // line 136 "../../../../FileProcessor_static.ump"
    protected int adjustMem ;
  // line 137 "../../../../FileProcessor_static.ump"
    protected MemoryBudget budget ;
  // line 138 "../../../../FileProcessor_static.ump"
    protected Set checkPendingDbSet ;
  // line 139 "../../../../FileProcessor_static.ump"
    protected Map dbCache ;
  // line 140 "../../../../FileProcessor_static.ump"
    protected CleanerFileReader reader ;
  // line 141 "../../../../FileProcessor_static.ump"
    protected DbTree dbMapTree ;
  // line 142 "../../../../FileProcessor_static.ump"
    protected TreeLocation location ;
  // line 143 "../../../../FileProcessor_static.ump"
    protected int nProcessedLNs ;
  // line 144 "../../../../FileProcessor_static.ump"
    protected long lsn ;
  // line 145 "../../../../FileProcessor_static.ump"
    protected long fileOffset ;
  // line 146 "../../../../FileProcessor_static.ump"
    protected boolean isLN ;
  // line 147 "../../../../FileProcessor_static.ump"
    protected boolean isIN ;
  // line 148 "../../../../FileProcessor_static.ump"
    protected boolean isRoot ;
  // line 149 "../../../../FileProcessor_static.ump"
    protected boolean isObsolete ;
  // line 150 "../../../../FileProcessor_static.ump"
    protected DatabaseId dbId1 ;
  // line 151 "../../../../FileProcessor_static.ump"
    protected LN targetLN ;
  // line 152 "../../../../FileProcessor_static.ump"
    protected DatabaseId dbId2 ;
  // line 153 "../../../../FileProcessor_static.ump"
    protected byte[] key ;
  // line 154 "../../../../FileProcessor_static.ump"
    protected byte[] dupKey ;
  // line 155 "../../../../FileProcessor_static.ump"
    protected Long aLsn ;
  // line 156 "../../../../FileProcessor_static.ump"
    protected LNInfo aLninfo ;
  // line 157 "../../../../FileProcessor_static.ump"
    protected Object p ;
  // line 158 "../../../../FileProcessor_static.ump"
    protected IN targetIN ;
  // line 159 "../../../../FileProcessor_static.ump"
    protected DatabaseId dbId3 ;
  // line 160 "../../../../FileProcessor_static.ump"
    protected DatabaseImpl db3 ;
  // line 161 "../../../../FileProcessor_static.ump"
    protected DatabaseId dbId ;
  // line 162 "../../../../FileProcessor_static.ump"
    protected DatabaseImpl db ;
  
    
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 199 "../../../../FileProcessor_static.ump"
  public static class FileProcessor_processLN
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public FileProcessor_processLN()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 201 "../../../../FileProcessor_static.ump"
    public  FileProcessor_processLN(FileProcessor _this, Long fileNum, TreeLocation location, Long offset, LNInfo info, Object lookAheadCachep, Map dbCache){
      this._this=_this;
          this.fileNum=fileNum;
          this.location=location;
          this.offset=offset;
          this.info=info;
          this.lookAheadCachep=lookAheadCachep;
          this.dbCache=dbCache;
    }
  
    // line 210 "../../../../FileProcessor_static.ump"
    public void execute() throws DatabaseException{
      Label132:   ; //this.hook132();
          ln=info.getLN();
          key=info.getKey();
          dupKey=info.getDupKey();
          logLsn=DbLsn.makeLsn(fileNum.longValue(),offset.longValue());
          db=_this.env.getDbMapTree().getDb(info.getDbId(),_this.cleaner.lockTimeout,dbCache);
          processedHere=true;
          obsolete=false;
          completed=false;
          bin=null;
          parentDIN=null;
          try {
            b=db == null;
            Label157:   ; //this.hook157();
            if (b) {
              Label158:   ; //this.hook158();
              Label148:   ; //this.hook148();
              obsolete=true;
              completed=true;
              return;
            }
            tree=db.getTree();
            assert tree != null;
            parentFound=tree.getParentBINForChildLN(location,key,dupKey,ln,false,true,false,Cleaner.UPDATE_GENERATION);
            bin=location.bin;
            index=location.index;
            if (!parentFound) {
              Label149:   ; //this.hook149();
              obsolete=true;
              completed=true;
              return;
            }
            if (bin.isEntryKnownDeleted(index)) {
              Label150:   ; //this.hook150();
              obsolete=true;
              completed=true;
              return;
            }
            isDupCountLN=ln.containsDuplicates();
    {
            }
            if (isDupCountLN) {
              parentDIN=(DIN)bin.fetchTarget(index);
              parentDIN.latch(Cleaner.UPDATE_GENERATION);
              dclRef=parentDIN.getDupCountLNRef();
              treeLsn=dclRef.getLsn();
            }
     else {
              treeLsn=bin.getLsn(index);
            }
            processedHere=false;
            _this.processFoundLN(info,logLsn,treeLsn,bin,index,parentDIN);
            completed=true;
            Label133:   ; //this.hook133();
            return;
          }
      finally {
            Label135:   ; //this.hook135();
            Label126:   ; //this.hook126();
          }
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 271 "../../../../FileProcessor_static.ump"
    protected FileProcessor _this ;
  // line 272 "../../../../FileProcessor_static.ump"
    protected Long fileNum ;
  // line 273 "../../../../FileProcessor_static.ump"
    protected TreeLocation location ;
  // line 274 "../../../../FileProcessor_static.ump"
    protected Long offset ;
  // line 275 "../../../../FileProcessor_static.ump"
    protected LNInfo info ;
  // line 276 "../../../../FileProcessor_static.ump"
    protected Object lookAheadCachep ;
  // line 277 "../../../../FileProcessor_static.ump"
    protected Map dbCache ;
  // line 278 "../../../../FileProcessor_static.ump"
    protected LN ln ;
  // line 279 "../../../../FileProcessor_static.ump"
    protected byte[] key ;
  // line 280 "../../../../FileProcessor_static.ump"
    protected byte[] dupKey ;
  // line 281 "../../../../FileProcessor_static.ump"
    protected long logLsn ;
  // line 282 "../../../../FileProcessor_static.ump"
    protected DatabaseImpl db ;
  // line 283 "../../../../FileProcessor_static.ump"
    protected boolean processedHere ;
  // line 284 "../../../../FileProcessor_static.ump"
    protected boolean obsolete ;
  // line 285 "../../../../FileProcessor_static.ump"
    protected boolean completed ;
  // line 286 "../../../../FileProcessor_static.ump"
    protected BIN bin ;
  // line 287 "../../../../FileProcessor_static.ump"
    protected DIN parentDIN ;
  // line 288 "../../../../FileProcessor_static.ump"
    protected boolean b ;
  // line 289 "../../../../FileProcessor_static.ump"
    protected Tree tree ;
  // line 290 "../../../../FileProcessor_static.ump"
    protected boolean parentFound ;
  // line 291 "../../../../FileProcessor_static.ump"
    protected int index ;
  // line 292 "../../../../FileProcessor_static.ump"
    protected boolean isDupCountLN ;
  // line 293 "../../../../FileProcessor_static.ump"
    protected long treeLsn ;
  // line 294 "../../../../FileProcessor_static.ump"
    protected ChildReference dclRef ;
  // line 295 "../../../../FileProcessor_static.ump"
    protected long lsn ;
  // line 296 "../../../../FileProcessor_static.ump"
    protected Long myOffset ;
  // line 297 "../../../../FileProcessor_static.ump"
    protected LNInfo myInfo ;
  
    
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 41 "../../../../FileProcessor.ump"
  private static final int PROCESS_PENDING_EVERY_N_LNS = 100 ;
// line 46 "../../../../FileProcessor.ump"
  private static final boolean PROHIBIT_DELTAS_WHEN_FETCHING = false ;
// line 48 "../../../../FileProcessor.ump"
  private static final boolean DEBUG_TRACING = false ;
// line 50 "../../../../FileProcessor.ump"
  private EnvironmentImpl env ;
// line 52 "../../../../FileProcessor.ump"
  private Cleaner cleaner ;
// line 54 "../../../../FileProcessor.ump"
  private FileSelector fileSelector ;
// line 56 "../../../../FileProcessor.ump"
  private UtilizationProfile profile ;

  
}