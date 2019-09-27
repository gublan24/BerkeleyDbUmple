/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;

// line 3 "../../../../LogManager.ump"
// line 3 "../../../../LogManager_inner.ump"
public class LogManager
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LogManager()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 12 "../../../../LogManager.ump"
   public boolean getChecksumOnRead(){
    return doChecksumOnRead;
  }

  // line 16 "../../../../LogManager.ump"
   protected static  int hook504(int r){
    r -= CHECKSUM_BYTES;
	return original(r);
  }

  // line 21 "../../../../LogManager.ump"
   protected void hook505(DbConfigManager configManager) throws DatabaseException{
    doChecksumOnRead = configManager.getBoolean(EnvironmentParams.LOG_CHECKSUM_READ);
	original(configManager);
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.log;
  
  @MethodObject
  // line 4 "../../../../LogManager_inner.ump"
  public static class LogManager_getLogEntryFromLogSource
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public LogManager_getLogEntryFromLogSource()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../LogManager_inner.ump"
     protected void hook506() throws DatabaseException,ClosedChannelException,Exception{
      if (_this.doChecksumOnRead) {
            validator.update(_this.envImpl,entryBuffer,itemSize,false);
            validator.validate(_this.envImpl,storedChecksum,lsn);
          }
          original();
    }
  
    // line 13 "../../../../LogManager_inner.ump"
     protected void hook507() throws DatabaseException,ClosedChannelException,Exception{
      validator=null;
          storedChecksum=LogUtils.getUnsignedInt(entryBuffer);
          if (_this.doChecksumOnRead) {
            validator=new ChecksumValidator();
            validator.update(_this.envImpl,entryBuffer,_this.HEADER_CONTENT_BYTES(),false);
          }
          original();
    }
  
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 5 "../../../../LogManager.ump"
  static final int HEADER_CHECKSUM_OFFSET = 0 ;
// line 7 "../../../../LogManager.ump"
  private boolean doChecksumOnRead ;
// line 9 "../../../../LogManager.ump"
  protected static ChecksumValidator validator ;

  
}