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
     protected void hook455() throws IOException,DatabaseException{
      channel=file.getChannel();
          original();
    }
  
    // line 10 "../../../../FileManager_inner.ump"
     protected void hook445() throws IOException,DatabaseException{
      totalBytesWritten=channel.write(data,destOffset);
          original();
    }
  
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.log;
  
  @MethodObject
  // line 14 "../../../../FileManager_inner.ump"
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
  
    // line 16 "../../../../FileManager_inner.ump"
    public void execute() throws IOException{
      channel=file.getChannel();
          original();
    }
  
    // line 20 "../../../../FileManager_inner.ump"
     protected void hook446() throws IOException{
      channel.read(readBuffer,offset);
          original();
    }
  
  }
}