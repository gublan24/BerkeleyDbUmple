/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.DatabaseException;

// line 3 "../../../../FileHandleSource.ump"
public class FileHandleSource extends FileSource
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public FileHandleSource()
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

  // line 11 "../../../../FileHandleSource.ump"
  public  FileHandleSource(FileHandle fileHandle, int readBufferSize, FileManager fileManager){
    super(fileHandle.getFile(), readBufferSize, fileManager);
	this.fileHandle = fileHandle;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 8 "../../../../FileHandleSource.ump"
  private FileHandle fileHandle ;

  
}