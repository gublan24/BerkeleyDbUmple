/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.DbLsn;
import com.sleepycat.je.utilint.Adler32;
import com.sleepycat.je.dbi.EnvironmentImpl;
import java.util.zip.Checksum;
import java.nio.ByteBuffer;

// line 3 "../../../../Checksum_ChecksumValidator.ump"
public class ChecksumValidator
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ChecksumValidator()
  {
    // line 18 "../../../../Checksum_ChecksumValidator.ump"
    //ChecksumValidator() {
            cksum = Adler32.makeChecksum();
    // END OF UMPLE AFTER INJECTION
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 23 "../../../../Checksum_ChecksumValidator.ump"
  public void reset(){
    cksum.reset();
  }


  /**
   * 
   * Add this byte buffer to the checksum. Assume the byte buffer is already positioned at the data.
   * @param buf target buffer
   * @param length of data
   */
  // line 33 "../../../../Checksum_ChecksumValidator.ump"
  public void update(EnvironmentImpl env, ByteBuffer buf, int length, boolean anticipateChecksumErrors) throws DbChecksumException{
    if (buf == null) {
            throw new DbChecksumException((anticipateChecksumErrors ? null : env),
                "null buffer given to checksum validation, probably " + " result of 0's in log file. " +
                anticipateChecksumErrors);
        }
        int bufStart = buf.position();
        if (DEBUG) {
            System.out.println("bufStart = " + bufStart + " length = " + length);
        }
        if (buf.hasArray()) {
            cksum.update(buf.array(), bufStart, length);
        } else {
            for (int i = bufStart; i < (length + bufStart); i++) {
                cksum.update(buf.get(i));
            }
        }
  }

  // line 52 "../../../../Checksum_ChecksumValidator.ump"
  public void validate(EnvironmentImpl env, long expectedChecksum, long lsn) throws DbChecksumException{
    if (expectedChecksum != cksum.getValue()) {
            throw new DbChecksumException(env, "Location " + DbLsn.getNoFormatString(lsn) + " expected " +
                expectedChecksum + " got " + cksum.getValue());
        }
  }

  // line 60 "../../../../Checksum_ChecksumValidator.ump"
  public void validate(EnvironmentImpl env, long expectedChecksum, long fileNum, long fileOffset, boolean anticipateChecksumErrors) throws DbChecksumException{
    if (expectedChecksum != cksum.getValue()) {
            long problemLsn = DbLsn.makeLsn(fileNum, fileOffset);
            throw new DbChecksumException((anticipateChecksumErrors ? null : env),
                "Location " + DbLsn.getNoFormatString(problemLsn) + " expected " + expectedChecksum + " got " +
                cksum.getValue());
        }
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 11 "../../../../Checksum_ChecksumValidator.ump"
  private static final boolean DEBUG = false ;
// line 13 "../../../../Checksum_ChecksumValidator.ump"
  private Checksum cksum ;

  
}