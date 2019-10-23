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

// line 3 "../../../../FileHandleCache_FileCache.ump"
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

  // line 22 "../../../../FileHandleCache_FileCache.ump"
  public  FileCache(DbConfigManager configManager) throws DatabaseException{
    fileMap = new Hashtable();
	    fileList = new LinkedList();
	    fileCacheSize = configManager.getInt(EnvironmentParams.LOG_FILE_CACHE_SIZE);
  }

  // line 28 "../../../../FileHandleCache_FileCache.ump"
  public FileHandle get(Long fileId){
    return (FileHandle) fileMap.get(fileId);
  }

  // line 32 "../../../../FileHandleCache_FileCache.ump"
  public void add(Long fileId, FileHandle fileHandle) throws DatabaseException{
    if (fileList.size() >= fileCacheSize) {
			  Iterator iter = fileList.iterator();
			  boolean done = false;
			  while (!done && iter.hasNext()) {
			Long evictId = (Long) iter.next();
			FileHandle evictTarget = (FileHandle) fileMap.get(evictId);

		    Label438:
try {
	      fileMap.remove(evictId);
	      iter.remove();
	      evictTarget.close();
	      } 
       catch (IOException e) 
       {
	    throw new DatabaseException(e);
	    } finally {
      Label441:
	    //this.hook441(evictTarget);
	  }
	done = true;
 		//	this.hook438(iter, done, evictId, evictTarget);
			  }
		}
		fileList.add(fileId);
		fileMap.put(fileId, fileHandle);
  }


  /**
   * 
   * Take any file handles corresponding to this file name out of the cache. A file handle could be there twice, in rd only and in r/w mode.
   */
  // line 50 "../../../../FileHandleCache_FileCache.ump"
  public void remove(long fileNum) throws IOException,DatabaseException{
    Iterator iter = fileList.iterator();
			while (iter.hasNext()) {
					Long evictId = (Long) iter.next();
					if (evictId.longValue() == fileNum) {
						FileHandle evictTarget = (FileHandle) fileMap.get(evictId);

						try { 
                Label439: 						//this.hook439(iter, evictId, evictTarget);
								//this.hook442(evictTarget);
									Label442: //remove(long fileNum)
									fileMap.remove(evictId);
									iter.remove();
									evictTarget.close();
															}
						finally {
								Label439_1: //end of hook439
						}
					}
			}
  }

  // line 72 "../../../../FileHandleCache_FileCache.ump"
  public void clear() throws IOException,DatabaseException{
    Iterator iter = fileMap.values().iterator();
			while (iter.hasNext()) {
					FileHandle fileHandle = (FileHandle) iter.next();
					//this.hook440(iter, fileHandle);
				  Label440:
// this.hook443(fileHandle);
    Label443:
	  fileHandle.close();
	  iter.remove();
	 // this.hook443(fileHandle);
					Label443:
					fileHandle.close();
					iter.remove();
					Label440_1: // end of hook440
			}
			fileMap.clear();
			fileList.clear();
  }

  // line 87 "../../../../FileHandleCache_FileCache.ump"
  public Set getCacheKeys(){
    return fileMap.keySet();
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 15 "../../../../FileHandleCache_FileCache.ump"
  private Map fileMap ;
// line 17 "../../../../FileHandleCache_FileCache.ump"
  private LinkedList fileList ;
// line 19 "../../../../FileHandleCache_FileCache.ump"
  private int fileCacheSize ;

  
}