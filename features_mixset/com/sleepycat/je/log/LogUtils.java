/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;
import de.ovgu.cide.jakutil.*;
import javax.transaction.xa.Xid;
import java.sql.Timestamp;
import java.nio.ByteBuffer;

// line 3 "../../../../LogUtils.ump"
// line 3 "../../../../LogUtils_static.ump"
public class LogUtils
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LogUtils()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Marshall a long into the next 4 bytes in this buffer. Necessary when the long is used to hold an unsigned int.
   */
  // line 26 "../../../../LogUtils.ump"
   public static  void writeUnsignedInt(ByteBuffer buf, long value){
    buf.put((byte) (value >>> 0));
	buf.put((byte) (value >>> 8));
	buf.put((byte) (value >>> 16));
	buf.put((byte) (value >>> 24));
  }


  /**
   * 
   * Unmarshall the next four bytes which hold an unsigned int into a long.
   */
  // line 36 "../../../../LogUtils.ump"
   public static  long getUnsignedInt(ByteBuffer buf){
    long ret = (buf.get() & 0xFFL) << 0;
	ret += (buf.get() & 0xFFL) << 8;
	ret += (buf.get() & 0xFFL) << 16;
	ret += (buf.get() & 0xFFL) << 24;
	return ret;
  }


  /**
   * 
   * Write a short into the log.
   */
  // line 47 "../../../../LogUtils.ump"
   public static  void writeShort(ByteBuffer logBuf, short i){
    byte b = (byte) ((i >> 0) & 0xff);
	logBuf.put(b);
	b = (byte) ((i >> 8) & 0xff);
	logBuf.put(b);
  }


  /**
   * 
   * Read a short from the log.
   */
  // line 57 "../../../../LogUtils.ump"
   public static  short readShort(ByteBuffer logBuf){
    return (short) (((logBuf.get() & 0xFF) << 0) + ((logBuf.get() & 0xFF) << 8));
  }


  /**
   * 
   * Write an int into the log.
   */
  // line 64 "../../../../LogUtils.ump"
   public static  void writeInt(ByteBuffer logBuf, int i){
    byte b = (byte) ((i >> 0) & 0xff);
	logBuf.put(b);
	b = (byte) ((i >> 8) & 0xff);
	logBuf.put(b);
	b = (byte) ((i >> 16) & 0xff);
	logBuf.put(b);
	b = (byte) ((i >> 24) & 0xff);
	logBuf.put(b);
  }


  /**
   * 
   * Read a int from the log.
   */
  // line 78 "../../../../LogUtils.ump"
   public static  int readInt(ByteBuffer logBuf){
    int ret = (logBuf.get() & 0xFF) << 0;
	ret += (logBuf.get() & 0xFF) << 8;
	ret += (logBuf.get() & 0xFF) << 16;
	ret += (logBuf.get() & 0xFF) << 24;
	return ret;
  }


  /**
   * 
   * @return log storage size for a byteArray.
   */
  // line 89 "../../../../LogUtils.ump"
   public static  int getIntLogSize(){
    return INT_BYTES;
  }


  /**
   * 
   * Write an int into the log in MSB order.  Used for ordered keys.
   */
  // line 96 "../../../../LogUtils.ump"
   public static  void writeIntMSB(ByteBuffer logBuf, int i){
    byte b = (byte) ((i >> 24) & 0xff);
	logBuf.put(b);
	b = (byte) ((i >> 16) & 0xff);
	logBuf.put(b);
	b = (byte) ((i >> 8) & 0xff);
	logBuf.put(b);
	b = (byte) ((i >> 0) & 0xff);
	logBuf.put(b);
  }


  /**
   * 
   * Read a int from the log in MSB order.  Used for ordered keys.
   */
  // line 110 "../../../../LogUtils.ump"
   public static  int readIntMSB(ByteBuffer logBuf){
    int ret = (logBuf.get() & 0xFF) << 24;
	ret += (logBuf.get() & 0xFF) << 16;
	ret += (logBuf.get() & 0xFF) << 8;
	ret += (logBuf.get() & 0xFF) << 0;
	return ret;
  }


  /**
   * 
   * Write a long into the log.
   */
  // line 121 "../../../../LogUtils.ump"
   public static  void writeLong(ByteBuffer logBuf, long l){
    byte b = (byte) (l >>> 0);
	logBuf.put(b);
	b = (byte) (l >>> 8);
	logBuf.put(b);
	b = (byte) (l >>> 16);
	logBuf.put(b);
	b = (byte) (l >>> 24);
	logBuf.put(b);
	b = (byte) (l >>> 32);
	logBuf.put(b);
	b = (byte) (l >>> 40);
	logBuf.put(b);
	b = (byte) (l >>> 48);
	logBuf.put(b);
	b = (byte) (l >>> 56);
	logBuf.put(b);
  }


  /**
   * 
   * Read a long from the log.
   */
  // line 143 "../../../../LogUtils.ump"
   public static  long readLong(ByteBuffer logBuf){
    long ret = (logBuf.get() & 0xFFL) << 0;
	ret += (logBuf.get() & 0xFFL) << 8;
	ret += (logBuf.get() & 0xFFL) << 16;
	ret += (logBuf.get() & 0xFFL) << 24;
	ret += (logBuf.get() & 0xFFL) << 32;
	ret += (logBuf.get() & 0xFFL) << 40;
	ret += (logBuf.get() & 0xFFL) << 48;
	ret += (logBuf.get() & 0xFFL) << 56;
	return ret;
  }


  /**
   * 
   * @return log storage size for a byteArray.
   */
  // line 158 "../../../../LogUtils.ump"
   public static  int getLongLogSize(){
    return LONG_BYTES;
  }


  /**
   * 
   * Write a byte array into the log. The size is stored first as an integer.
   */
  // line 165 "../../../../LogUtils.ump"
   public static  void writeByteArray(ByteBuffer logBuf, byte [] b){
    writeInt(logBuf, b.length);
	logBuf.put(b);
  }


  /**
   * 
   * Read a byte array from the log. The size is stored first as an integer.
   */
  // line 173 "../../../../LogUtils.ump"
   public static  byte[] readByteArray(ByteBuffer logBuf){
    int size = readInt(logBuf);
	if (DEBUG) {
	    System.out.println("pos = " + logBuf.position() + " byteArray is " + size + " on read");
	}
	if (size == 0) {
	    return ZERO_LENGTH_BYTE_ARRAY;
	}
	byte[] b = new byte[size];
	logBuf.get(b);
	return b;
  }


  /**
   * 
   * @return log storage size for a byteArray
   */
  // line 189 "../../../../LogUtils.ump"
   public static  int getByteArrayLogSize(byte [] b){
    return INT_BYTES + b.length;
  }


  /**
   * 
   * Write a string into the log. The size is stored first as an integer.
   */
  // line 196 "../../../../LogUtils.ump"
   public static  void writeString(ByteBuffer logBuf, String stringVal){
    writeByteArray(logBuf, stringVal.getBytes());
  }


  /**
   * 
   * Read a string from the log. The size is stored first as an integer.
   */
  // line 203 "../../../../LogUtils.ump"
   public static  String readString(ByteBuffer logBuf){
    return new String(readByteArray(logBuf));
  }


  /**
   * 
   * @return log storage size for a string
   */
  // line 210 "../../../../LogUtils.ump"
   public static  int getStringLogSize(String s){
    return INT_BYTES + s.length();
  }


  /**
   * 
   * Write a timestamp into the log.
   */
  // line 217 "../../../../LogUtils.ump"
   public static  void writeTimestamp(ByteBuffer logBuf, Timestamp time){
    writeLong(logBuf, time.getTime());
  }


  /**
   * 
   * Read a timestamp from the log.
   */
  // line 224 "../../../../LogUtils.ump"
   public static  Timestamp readTimestamp(ByteBuffer logBuf){
    long millis = readLong(logBuf);
	return new Timestamp(millis);
  }


  /**
   * 
   * @return log storage size for a timestamp
   */
  // line 232 "../../../../LogUtils.ump"
   public static  int getTimestampLogSize(){
    return LONG_BYTES;
  }


  /**
   * 
   * Write a boolean into the log.
   */
  // line 239 "../../../../LogUtils.ump"
   public static  void writeBoolean(ByteBuffer logBuf, boolean bool){
    byte val = bool ? (byte) 1 : (byte) 0;
	logBuf.put(val);
  }


  /**
   * 
   * Read a boolean from the log.
   */
  // line 247 "../../../../LogUtils.ump"
   public static  boolean readBoolean(ByteBuffer logBuf){
    byte val = logBuf.get();
	return (val == (byte) 1) ? true : false;
  }


  /**
   * 
   * @return log storage size for a boolean.
   */
  // line 255 "../../../../LogUtils.ump"
   public static  int getBooleanLogSize(){
    return 1;
  }

  // line 259 "../../../../LogUtils.ump"
   public static  boolean dumpBoolean(ByteBuffer itemBuffer, StringBuffer sb, String tag){
    sb.append("<");
	sb.append(tag);
	sb.append(" exists = \"");
	boolean exists = readBoolean(itemBuffer);
	sb.append(exists);
	if (exists) {
	    sb.append("\">");
	} else {
	    sb.append("\"/>");
	}
	return exists;
  }


  /**
   * 
   * The byte[]'s in Xid's are known to be 255 or less in length.  So instead of using read/writeByteArray(), we can save 6 bytes per record by making the byte[] length be 1 byte instead of 4.
   */
  // line 276 "../../../../LogUtils.ump"
   public static  int getXidSize(Xid xid){
    byte[] gid = xid.getGlobalTransactionId();
	byte[] bqual = xid.getBranchQualifier();
	return INT_BYTES + 1 + 1 + (gid == null ? 0 : gid.length) + (bqual == null ? 0 : bqual.length);
  }

  // line 282 "../../../../LogUtils.ump"
   public static  void writeXid(ByteBuffer logBuf, Xid xid){
    byte[] gid = xid.getGlobalTransactionId();
	byte[] bqual = xid.getBranchQualifier();
	writeInt(logBuf, xid.getFormatId());
	if (gid == null) {
	    logBuf.put((byte) -1);
	} else {
	    logBuf.put((byte) (gid.length));
	    logBuf.put(gid);
	}
	if (bqual == null) {
	    logBuf.put((byte) -1);
	} else {
	    logBuf.put((byte) (bqual.length));
	    logBuf.put(bqual);
	}
  }

  // line 300 "../../../../LogUtils.ump"
   public static  Xid readXid(ByteBuffer logBuf){
    int formatId = readInt(logBuf);
	int gidLen = logBuf.get();
	byte[] gid = null;
	if (gidLen >= 0) {
	    gid = new byte[gidLen];
	    logBuf.get(gid);
	}
	int bqualLen = logBuf.get();
	byte[] bqual = null;
	if (bqualLen >= 0) {
	    bqual = new byte[bqualLen];
	    logBuf.get(bqual);
	}
	return new XidImpl(formatId, gid, bqual);
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 6 "../../../../LogUtils_static.ump"
  public static class XidImpl implements Xid
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public XidImpl()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 12 "../../../../LogUtils_static.ump"
     public  XidImpl(int formatId, byte [] gid, byte [] bqual){
      this.formatId=formatId;
          this.gid=gid;
          this.bqual=bqual;
    }
  
    // line 17 "../../../../LogUtils_static.ump"
     public int getFormatId(){
      return formatId;
    }
  
    // line 20 "../../../../LogUtils_static.ump"
     public byte[] getGlobalTransactionId(){
      return gid;
    }
  
    // line 23 "../../../../LogUtils_static.ump"
     public byte[] getBranchQualifier(){
      return bqual;
    }
  
    // line 26 "../../../../LogUtils_static.ump"
     public boolean equals(Object o){
      if (!(o instanceof XidImpl)) {
            return false;
          }
          XidImpl xid=(XidImpl)o;
          if (xid.getFormatId() != formatId) {
            return false;
          }
          if (compareByteArrays(xid.getGlobalTransactionId(),gid) && compareByteArrays(xid.getBranchQualifier(),bqual)) {
            return true;
          }
          return false;
    }
  
    // line 39 "../../../../LogUtils_static.ump"
     public int hashCode(){
      int code=formatId;
          if (gid != null) {
            for (int i=0; i < gid.length; i++) {
              code+=gid[i];
            }
          }
          if (bqual != null) {
            for (int i=0; i < bqual.length; i++) {
              code+=bqual[i];
            }
          }
          return code;
    }
  
    // line 53 "../../../../LogUtils_static.ump"
     private boolean compareByteArrays(byte [] b1, byte [] b2){
      if (b1 == null || b2 == null) {
            return b1 == b2;
          }
          if (b1.length != b2.length) {
            return false;
          }
          for (int i=0; i < b1.length; i++) {
            if (b1[i] != b2[i]) {
              return false;
            }
          }
          return true;
    }
  
    // line 67 "../../../../LogUtils_static.ump"
     public String toString(){
      StringBuffer sb=new StringBuffer();
          sb.append("<Xid formatId=\"").append(formatId);
          sb.append("\" gTxnId=\"");
          if (gid == null) {
            sb.append("null");
          }
     else {
            sb.append(new String(gid));
          }
          sb.append("\" bqual=\"");
          if (bqual == null) {
            sb.append("null");
          }
     else {
            sb.append(new String(bqual));
          }
          sb.append("\"/>");
          return sb.toString();
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 8 "../../../../LogUtils_static.ump"
    private int formatId ;
  // line 9 "../../../../LogUtils_static.ump"
    private byte[] gid ;
  // line 10 "../../../../LogUtils_static.ump"
    private byte[] bqual ;
  
    
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 10 "../../../../LogUtils.ump"
  public static final int SHORT_BYTES = 2 ;
// line 12 "../../../../LogUtils.ump"
  public static final int INT_BYTES = 4 ;
// line 14 "../../../../LogUtils.ump"
  public static final int LONG_BYTES = 8 ;
// line 16 "../../../../LogUtils.ump"
  public static final int UNSIGNED_INT_BYTES = 4 ;
// line 18 "../../../../LogUtils.ump"
  private static final boolean DEBUG = false ;
// line 20 "../../../../LogUtils.ump"
  public static final byte[] ZERO_LENGTH_BYTE_ARRAY = new byte[0] ;

  
}