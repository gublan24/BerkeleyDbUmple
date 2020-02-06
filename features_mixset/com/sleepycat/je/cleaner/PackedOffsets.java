/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.cleaner;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.log.LogWritable;
import com.sleepycat.je.log.LogUtils;
import com.sleepycat.je.log.LogReadable;
import java.util.Arrays;
import java.nio.ByteBuffer;
import com.sleepycat.je.log.*;

// line 3 "../../../../PackedOffsets.ump"
// line 3 "../../../../PackedOffsets_static.ump"
public class PackedOffsets implements LogWritable,LogReadable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PackedOffsets()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Creates an empty object.
   * public PackedOffsets() {
   * }
   * 
   * Returns an iterator over all offsets.
   */
  // line 26 "../../../../PackedOffsets.ump"
  public Iterator iterator(){
    return new Iterator();
  }


  /**
   * 
   * Packs the given offsets, replacing any offsets stored in this object.
   */
  // line 33 "../../../../PackedOffsets.ump"
   public void pack(long [] offsets){
    short[] newData = new short[offsets.length * 3];
	Arrays.sort(offsets);
	int dataIndex = 0;
	long priorVal = 0;
	for (int i = 0; i < offsets.length; i += 1) {
	    long val = offsets[i];
	    dataIndex = append(newData, dataIndex, val - priorVal);
	    priorVal = val;
	}
	data = new short[dataIndex];
	System.arraycopy(newData, 0, data, 0, dataIndex);
	size = offsets.length;
  }


  /**
   * 
   * Returns the unpacked offsets.
   */
  // line 51 "../../../../PackedOffsets.ump"
  public long[] toArray(){
    long[] offsets = new long[size];
	int index = 0;
	Iterator iter = iterator();
	while (iter.hasNext()) {
	    offsets[index++] = iter.next();
	}
	assert index == size;
	return offsets;
  }


  /**
   * 
   * Copies the given value as a packed long to the array starting at the given index.  Returns the index of the next position in the array.
   */
  // line 65 "../../../../PackedOffsets.ump"
   private int append(short [] to, int index, long val){
    assert val >= 0;
	while (true) {
	    short s = (short) (val & 0x7fff);
	    val >>>= 15;
	    if (val > 0) {
		to[index++] = (short) (-1 - s);
	    } else {
		to[index++] = s;
		break;
	    }
	}
	return index;
  }


  /**
   * 
   * @see LogWritable#getLogSize
   */
  // line 83 "../../../../PackedOffsets.ump"
   public int getLogSize(){
    return (2 * LogUtils.getIntLogSize()) + ((data != null) ? (data.length * LogUtils.SHORT_BYTES) : 0);
  }


  /**
   * 
   * @see LogWritable#writeToLog
   */
  // line 90 "../../../../PackedOffsets.ump"
   public void writeToLog(ByteBuffer buf){
    LogUtils.writeInt(buf, size);
	if (data != null) {
	    LogUtils.writeInt(buf, data.length);
	    for (int i = 0; i < data.length; i += 1) {
		LogUtils.writeShort(buf, data[i]);
	    }
	} else {
	    LogUtils.writeInt(buf, 0);
	}
  }


  /**
   * 
   * @see LogReadable#readFromLog
   */
  // line 105 "../../../../PackedOffsets.ump"
   public void readFromLog(ByteBuffer buf, byte entryTypeVersion){
    size = LogUtils.readInt(buf);
	int len = LogUtils.readInt(buf);
	if (len > 0) {
	    data = new short[len];
	    for (int i = 0; i < len; i += 1) {
		data[i] = LogUtils.readShort(buf);
	    }
	}
  }


  /**
   * 
   * @see LogReadable#dumpLog
   */
  // line 119 "../../../../PackedOffsets.ump"
   public void dumpLog(StringBuffer buf, boolean verbose){
    if (size > 0) {
	    Iterator i = iterator();
	    buf.append("<offsets size=\"");
	    buf.append(size);
	    buf.append("\">");
	    while (i.hasNext()) {
		buf.append("0x");
		buf.append(Long.toHexString(i.next()));
		buf.append(' ');
	    }
	    buf.append("</offsets>");
	} else {
	    buf.append("<offsets size=\"0\"/>");
	}
  }


  /**
   * 
   * Never called.
   * @see LogReadable#getTransactionId
   */
  // line 140 "../../../../PackedOffsets.ump"
   public long getTransactionId(){
    return -1;
  }


  /**
   * 
   * Never called.
   * @see LogReadable#logEntryIsTransactional
   */
  // line 148 "../../../../PackedOffsets.ump"
   public boolean logEntryIsTransactional(){
    return false;
  }

  // line 152 "../../../../PackedOffsets.ump"
   public String toString(){
    StringBuffer buf = new StringBuffer();
	dumpLog(buf, true);
	return buf.toString();
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 4 "../../../../PackedOffsets_static.ump"
  public class Iterator
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public Iterator()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
  
    /**
     * private Iterator(){
     * }
     */
    // line 10 "../../../../PackedOffsets_static.ump"
    public boolean hasNext(){
      return data != null && index < data.length;
    }
  
    // line 13 "../../../../PackedOffsets_static.ump"
    public long next(){
      long val=priorVal;
          for (int shift=0; ; shift+=15) {
            long s=data[index++];
            if (s < 0) {
              val+=(-1 - s) << shift;
            }
     else {
              val+=s << shift;
              break;
            }
          }
          priorVal=val;
          return val;
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 5 "../../../../PackedOffsets_static.ump"
    private int index ;
  // line 6 "../../../../PackedOffsets_static.ump"
    private long priorVal ;
  
    
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 12 "../../../../PackedOffsets.ump"
  private short[] data ;
// line 14 "../../../../PackedOffsets.ump"
  private int size ;

  
}