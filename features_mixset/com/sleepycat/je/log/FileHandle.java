/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.DatabaseException;
import java.io.RandomAccessFile;
import java.io.IOException;
import com.sleepycat.je.latch.LatchSupport;
import com.sleepycat.je.latch.Latch;

// line 3 "../../../../FileHandle.ump"
// line 3 "../../../../Latches_FileHandle.ump"
public class FileHandle
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public FileHandle()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 15 "../../../../FileHandle.ump"
  public  FileHandle(RandomAccessFile file, String fileName, EnvironmentImpl env, boolean oldHeaderVersion){
    this.file = file;
	this.oldHeaderVersion = oldHeaderVersion;
	Label444:
fileLatch = LatchSupport.makeLatch(fileName + "_fileHandle", env);
//	original(fileName, env);
 //this.hook444(fileName, env);
  }

  // line 21 "../../../../FileHandle.ump"
  public RandomAccessFile getFile(){
    return file;
  }

  // line 25 "../../../../FileHandle.ump"
  public boolean isOldHeaderVersion(){
    return oldHeaderVersion;
  }

  // line 29 "../../../../FileHandle.ump"
  public void close() throws IOException{
    if (file != null) {
	    file.close();
	    file = null;
	}
  }

  // line 10 "../../../../Latches_FileHandle.ump"
  public void latch() throws DatabaseException{
    fileLatch.acquire();
  }

  // line 14 "../../../../Latches_FileHandle.ump"
  public boolean latchNoWait() throws DatabaseException{
    return fileLatch.acquireNoWait();
  }

  // line 18 "../../../../Latches_FileHandle.ump"
  public void release() throws DatabaseException{
    fileLatch.release();
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 10 "../../../../FileHandle.ump"
  private RandomAccessFile file ;
// line 12 "../../../../FileHandle.ump"
  private boolean oldHeaderVersion ;
// line 7 "../../../../Latches_FileHandle.ump"
  private Latch fileLatch ;

  
}