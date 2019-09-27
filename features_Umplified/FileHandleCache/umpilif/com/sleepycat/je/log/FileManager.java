/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;

// line 3 "../../../../FileManager.ump"
public class FileManager
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public FileManager()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 8 "../../../../FileManager.ump"
  public Set getCacheKeys(){
    return fileCache.getCacheKeys();
  }


  /**
   * 
   * Clear a file out of the file cache regardless of mode type.
   */
  // line 15 "../../../../FileManager.ump"
   private void clearFileCache(long fileNum) throws IOException,DatabaseException{
    fileCache.remove(fileNum);
  }

  // line 19 "../../../../FileManager.ump"
   protected void hook451() throws IOException,DatabaseException{
    fileCache.clear();
  }

  // line 23 "../../../../FileManager.ump"
   protected void hook457(DbConfigManager configManager) throws DatabaseException{
    fileCache = new FileCache(configManager);
	original(configManager);
  }

  // line 28 "../../../../FileManager.ump"
   protected void hook458(long fileNum) throws DatabaseException,IOException{
    clearFileCache(fileNum);
	original(fileNum);
  }

  // line 33 "../../../../FileManager.ump"
   protected void hook459(long fileNum) throws DatabaseException,IOException{
    clearFileCache(fileNum);
	original(fileNum);
  }

  // line 38 "../../../../FileManager.ump"
   protected void hook460(long fileNum, Long fileId, FileHandle fileHandle) throws LogException,DatabaseException{
    while (true) {
	    original(fileNum, fileId, fileHandle);
	}
  }

  // line 44 "../../../../FileManager.ump"
   protected void hook461(ByteBuffer data){
    data.position(0);
	original(data);
  }


  /**
   * 
   * Close all file handles and empty the cache.
   */
  // line 52 "../../../../FileManager.ump"
   public void clear() throws IOException,DatabaseException{
    {
	    this.hook451();
	}
	original();
  }

  // line 60 "../../../../FileManager.ump"
   protected FileHandle hook462(long fileNum, Long fileId, FileHandle fileHandle) throws LogException,DatabaseException{
    fileHandle = fileCache.get(fileId);
	if (fileHandle == null) {
	    fileHandle = original(fileNum, fileId, fileHandle);
	}
	return fileHandle;
  }

  // line 69 "../../../../FileManager.ump"
   protected FileHandle hook463(long fileNum, Long fileId, FileHandle fileHandle) throws LogException,DatabaseException{
    fileHandle = fileCache.get(fileId);
	if (fileHandle == null) {
	    fileHandle = original(fileNum, fileId, fileHandle);
	}
	return fileHandle;
  }

  // line 77 "../../../../FileManager.ump"
   protected void hook464(Long fileId, FileHandle fileHandle) throws LogException,DatabaseException{
    fileCache.add(fileId, fileHandle);
	original(fileId, fileHandle);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 5 "../../../../FileManager.ump"
  private FileCache fileCache ;

  
}