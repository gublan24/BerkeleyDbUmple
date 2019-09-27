/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;

// line 3 "../../../../FileReader.ump"
// line 3 "../../../../FileReader_inner.ump"
public class FileReader
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public FileReader()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Whether to always validate the checksum, even for non-target entries.
   */
  // line 15 "../../../../FileReader.ump"
   public void setAlwaysValidateChecksum(boolean validate){
    alwaysValidateChecksum = validate;
  }


  /**
   * 
   * Reset the checksum and add the header bytes. This method must be called with the entry header data at the buffer mark.
   */
  // line 22 "../../../../FileReader.ump"
   private void startChecksum(ByteBuffer dataBuffer) throws DatabaseException{
    cksumValidator.reset();
	int entryStart = threadSafeBufferPosition(dataBuffer);
	dataBuffer.reset();
	cksumValidator.update(env, dataBuffer, LogManager.HEADER_CONTENT_BYTES(), anticipateChecksumErrors);
	threadSafeBufferPosition(dataBuffer, entryStart);
  }


  /**
   * 
   * Add the entry bytes to the checksum and check the value. This method must be called with the buffer positioned at the start of the entry.
   */
  // line 33 "../../../../FileReader.ump"
   private void validateChecksum(ByteBuffer entryBuffer) throws DatabaseException{
    cksumValidator.update(env, entryBuffer, currentEntrySize, anticipateChecksumErrors);
	cksumValidator.validate(env, currentEntryChecksum, readBufferFileNum, currentEntryOffset,
		anticipateChecksumErrors);
  }

  // line 39 "../../../../FileReader.ump"
   protected void hook472() throws IOException,DatabaseException{
    if (doValidateChecksum) {
	    cksumValidator = new ChecksumValidator();
	}
	original();
  }

  // line 46 "../../../../FileReader.ump"
   protected void hook473(EnvironmentImpl env) throws IOException,DatabaseException{
    this.doValidateChecksum = env.getLogManager().getChecksumOnRead();
	original(env);
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.log;
  
  @MethodObject
  // line 4 "../../../../FileReader_inner.ump"
  public static class FileReader_readNextEntry
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public FileReader_readNextEntry()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../FileReader_inner.ump"
     protected void hook474() throws DatabaseException,IOException,EOFException{
      if (doValidate) {
            _this.validateChecksum(dataBuffer);
          }
          original();
    }
  
    // line 12 "../../../../FileReader_inner.ump"
     protected void hook475() throws DatabaseException,IOException,EOFException{
      collectData|=doValidate;
          if (doValidate) {
            _this.startChecksum(dataBuffer);
          }
          original();
    }
  
    // line 19 "../../../../FileReader_inner.ump"
     protected void hook476() throws DatabaseException,IOException,EOFException{
      doValidate=_this.doValidateChecksum && (isTargetEntry || _this.alwaysValidateChecksum);
          original();
    }
  
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 5 "../../../../FileReader.ump"
  protected ChecksumValidator cksumValidator ;
// line 7 "../../../../FileReader.ump"
  private boolean doValidateChecksum ;
// line 9 "../../../../FileReader.ump"
  private boolean alwaysValidateChecksum ;

  
}