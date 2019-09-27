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
     protected void hook447() throws IOException,DatabaseException{
      assert data.hasArray();
          assert data.arrayOffset() == 0;
          pos=data.position();
          size=data.limit() - pos;
          file.seek(destOffset);
          file.write(data.array(),pos,size);
          data.position(pos + size);
          totalBytesWritten=size;
    }
  
    // line 16 "../../../../FileManager_inner.ump"
    public int execute() throws IOException,DatabaseException{
      int result=original();
    {
            this.hook447();
          }
          return result;
    }
  
  }  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.log;
  
  @MethodObject
  // line 23 "../../../../FileManager_inner.ump"
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
  
    // line 25 "../../../../FileManager_inner.ump"
     protected void hook448() throws IOException{
      assert readBuffer.hasArray();
          assert readBuffer.arrayOffset() == 0;
          pos=readBuffer.position();
          size=readBuffer.limit() - pos;
          file.seek(offset);
          bytesRead2=file.read(readBuffer.array(),pos,size);
          if (bytesRead2 > 0) {
            readBuffer.position(pos + bytesRead2);
          }
    }
  
    // line 36 "../../../../FileManager_inner.ump"
    public void execute() throws IOException{
      original();
    {
            this.hook448();
          }
    }
  
  }
}