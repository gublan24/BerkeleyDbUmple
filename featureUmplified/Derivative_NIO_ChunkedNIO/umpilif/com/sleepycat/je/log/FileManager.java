/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;

// line 3 "../../../../FileManager.ump"
// line 3 "../../../../FileManager_inner.ump"
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
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.log;
  
  @MethodObject
  // line 4 "../../../../FileManager_inner.ump"
  public static class FileManager_writeToFile
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public FileManager_writeToFile()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../FileManager_inner.ump"
     protected void hook445() throws IOException,DatabaseException{
      if (_this.chunkedNIOSize > 0) {
            useData=data.duplicate();
            origLimit=useData.limit();
            useData.limit(useData.position());
            while (useData.limit() < origLimit) {
              useData.limit((int)(Math.min(useData.limit() + _this.chunkedNIOSize,origLimit)));
              bytesWritten=channel.write(useData,destOffset);
              destOffset+=bytesWritten;
              totalBytesWritten+=bytesWritten;
            }
          }
     else {
            original();
          }
    }
  
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.log;
  
  @MethodObject
  // line 22 "../../../../FileManager_inner.ump"
  public static class FileManager_readFromFile
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public FileManager_readFromFile()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 24 "../../../../FileManager_inner.ump"
     protected void hook446() throws IOException{
      if (_this.chunkedNIOSize > 0) {
            readLength=readBuffer.limit();
            currentPosition=offset;
            while (readBuffer.position() < readLength) {
              readBuffer.limit((int)(Math.min(readBuffer.limit() + _this.chunkedNIOSize,readLength)));
              bytesRead1=channel.read(readBuffer,currentPosition);
              if (bytesRead1 < 1)           break;
              currentPosition+=bytesRead1;
            }
          }
     else {
            original();
          }
    }
  
  }
}