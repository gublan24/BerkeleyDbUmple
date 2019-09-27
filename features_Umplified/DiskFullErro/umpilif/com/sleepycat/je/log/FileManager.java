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

  // line 7 "../../../../FileManager.ump"
   protected void hook465(LogBuffer fullBuffer, long firstLsn, RandomAccessFile file) throws DatabaseException,ClosedChannelException,IOException{
    assert fullBuffer.getRewriteAllowed() || (DbLsn.getFileOffset(firstLsn) >= file.length()
		|| file.length() == firstLogEntryOffset()) : "FileManager would overwrite non-empty file 0x"
			+ Long.toHexString(DbLsn.getFileNumber(firstLsn)) + " lsnOffset=0x"
			+ Long.toHexString(DbLsn.getFileOffset(firstLsn)) + " fileLength=0x"
			+ Long.toHexString(file.length());
	original(fullBuffer, firstLsn, file);
  }

  // line 17 "../../../../FileManager.ump"
   protected void hook466(LogBuffer fullBuffer, long firstLsn, RandomAccessFile file, ByteBuffer data, IOException IOE) throws DatabaseException{
    try {
	    if (IO_EXCEPTION_TESTING) {
		throw new IOException("generated for testing");
	    }
	    writeToFile(file, data, DbLsn.getFileOffset(firstLsn));
	} catch (IOException IOE2) {
	    fullBuffer.setRewriteAllowed();
	    throw new DatabaseException(IOE2);
	}
	if (false)
	    original(fullBuffer, firstLsn, file, data, IOE);
  }

}