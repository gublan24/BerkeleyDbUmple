/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.dbi.DbConfigManager;
import com.sleepycat.je.config.EnvironmentParams;
import com.sleepycat.je.DatabaseException;
import java.util.Set;
import java.util.Map;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Hashtable;
import java.io.IOException;

// line 3 "../../../../FileCache.ump"
public class FileCache
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public FileCache()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 22 "../../../../FileCache.ump"
  public  FileCache(DbConfigManager configManager) throws DatabaseException{
    fileMap = new Hashtable();
	fileList = new LinkedList();
	fileCacheSize = configManager.getInt(EnvironmentParams.LOG_FILE_CACHE_SIZE);
  }

  // line 28 "../../../../FileCache.ump"
  public FileHandle get(Long fileId){
    return (FileHandle) fileMap.get(fileId);
  }

  // line 32 "../../../../FileCache.ump"
  public void add(Long fileId, FileHandle fileHandle) throws DatabaseException{
    if (fileList.size() >= fileCacheSize) {
	    Iterator iter = fileList.iterator();
	    boolean done = false;
	    while (!done && iter.hasNext()) {
		Long evictId = (Long) iter.next();
		FileHandle evictTarget = (FileHandle) fileMap.get(evictId);
		this.hook438(iter, done, evictId, evictTarget);
	    }
	}
	fileList.add(fileId);
	fileMap.put(fileId, fileHandle);
  }


  /**
   * 
   * Take any file handles corresponding to this file name out of the cache. A file handle could be there twice, in rd only and in r/w mode.
   */
  // line 49 "../../../../FileCache.ump"
  public void remove(long fileNum) throws IOException,DatabaseException{
    Iterator iter = fileList.iterator();
	while (iter.hasNext()) {
	    Long evictId = (Long) iter.next();
	    if (evictId.longValue() == fileNum) {
		FileHandle evictTarget = (FileHandle) fileMap.get(evictId);
		this.hook439(iter, evictId, evictTarget);
	    }
	}
  }

  // line 60 "../../../../FileCache.ump"
  public void clear() throws IOException,DatabaseException{
    Iterator iter = fileMap.values().iterator();
	while (iter.hasNext()) {
	    FileHandle fileHandle = (FileHandle) iter.next();
	    this.hook440(iter, fileHandle);
	}
	fileMap.clear();
	fileList.clear();
  }

  // line 70 "../../../../FileCache.ump"
  public Set getCacheKeys(){
    return fileMap.keySet();
  }

  // line 74 "../../../../FileCache.ump"
   protected void hook438(Iterator iter, boolean done, Long evictId, FileHandle evictTarget) throws DatabaseException{
    try {
	    fileMap.remove(evictId);
	    iter.remove();
	    evictTarget.close();
	} catch (IOException e) {
	    throw new DatabaseException(e);
	} finally {
	    this.hook441(evictTarget);
	}
	done = true;
  }

  // line 87 "../../../../FileCache.ump"
   protected void hook439(Iterator iter, Long evictId, FileHandle evictTarget) throws IOException,DatabaseException{
    this.hook442(evictTarget);
	fileMap.remove(evictId);
	iter.remove();
	evictTarget.close();
  }

  // line 94 "../../../../FileCache.ump"
   protected void hook440(Iterator iter, FileHandle fileHandle) throws IOException,DatabaseException{
    this.hook443(fileHandle);
	fileHandle.close();
	iter.remove();
  }

  // line 100 "../../../../FileCache.ump"
   protected void hook441(FileHandle evictTarget) throws DatabaseException{
    
  }

  // line 103 "../../../../FileCache.ump"
   protected void hook442(FileHandle evictTarget) throws IOException,DatabaseException{
    
  }

  // line 106 "../../../../FileCache.ump"
   protected void hook443(FileHandle fileHandle) throws IOException,DatabaseException{
    
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 15 "../../../../FileCache.ump"
  private Map fileMap ;
// line 17 "../../../../FileCache.ump"
  private LinkedList fileList ;
// line 19 "../../../../FileCache.ump"
  private int fileCacheSize ;

  
}