/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.bind.tuple;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.util.UtfOps;
import com.sleepycat.util.FastInputStream;
import com.sleepycat.util.*;

// line 3 "../../../../TupleInput.ump"
public class TupleInput extends FastInputStream
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TupleInput()
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


  /**
   * 
   * Creates a tuple input object for reading a byte array of tuple data. A reference to the byte array will be kept by this object (it will not be copied) and therefore the byte array should not be modified while this object is in use.
   * @param bufferis the byte array to be read and should contain data in tupleformat.
   */
  // line 14 "../../../../TupleInput.ump"
   public  TupleInput(byte [] buffer){
    super(buffer);
  }


  /**
   * 
   * Creates a tuple input object for reading a byte array of tuple data at a given offset for a given length. A reference to the byte array will be kept by this object (it will not be copied) and therefore the byte array should not be modified while this object is in use.
   * @param bufferis the byte array to be read and should contain data in tupleformat.
   * @param offsetis the byte offset at which to begin reading.
   * @param lengthis the number of bytes to be read.
   */
  // line 24 "../../../../TupleInput.ump"
   public  TupleInput(byte [] buffer, int offset, int length){
    super(buffer, offset, length);
  }


  /**
   * 
   * Creates a tuple input object from the data contained in a tuple output object. A reference to the tuple output's byte array will be kept by this object (it will not be copied) and therefore the tuple output object should not be modified while this object is in use.
   * @param outputis the tuple output object containing the data to be read.
   */
  // line 32 "../../../../TupleInput.ump"
   public  TupleInput(TupleOutput output){
    super(output.getBufferBytes(), output.getBufferOffset(), output.getBufferLength());
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 41 "../../../../TupleInput.ump"
  public final String readString () throws IndexOutOfBoundsException, IllegalArgumentException 
  {
    byte[] buf = getBufferBytes();
	int off = getBufferOffset();
	if (available() >= 2 && buf[off] == TupleOutput.NULL_STRING_UTF_VALUE && buf[off + 1] == 0) {
	    skip(2);
	    return null;
	} else {
	    int byteLen = UtfOps.getZeroTerminatedByteLength(buf, off);
	    skip(byteLen + 1);
	    return UtfOps.bytesToString(buf, off, byteLen);
	}
  }

// line 59 "../../../../TupleInput.ump"
  public final char readChar () throws IndexOutOfBoundsException 
  {
    return (char) readUnsignedShort();
  }

// line 68 "../../../../TupleInput.ump"
  public final boolean readBoolean () throws IndexOutOfBoundsException 
  {
    int c = readFast();
	if (c < 0) {
	    throw new IndexOutOfBoundsException();
	}
	return (c != 0);
  }

// line 81 "../../../../TupleInput.ump"
  public final byte readByte () throws IndexOutOfBoundsException 
  {
    return (byte) (readUnsignedByte() ^ 0x80);
  }

// line 90 "../../../../TupleInput.ump"
  public final short readShort () throws IndexOutOfBoundsException 
  {
    return (short) (readUnsignedShort() ^ 0x8000);
  }

// line 99 "../../../../TupleInput.ump"
  public final int readInt () throws IndexOutOfBoundsException 
  {
    return (int) (readUnsignedInt() ^ 0x80000000);
  }

// line 108 "../../../../TupleInput.ump"
  public final long readLong () throws IndexOutOfBoundsException 
  {
    return readUnsignedLong() ^ 0x8000000000000000L;
  }

// line 117 "../../../../TupleInput.ump"
  public final float readFloat () throws IndexOutOfBoundsException 
  {
    return Float.intBitsToFloat((int) readUnsignedInt());
  }

// line 126 "../../../../TupleInput.ump"
  public final double readDouble () throws IndexOutOfBoundsException 
  {
    return Double.longBitsToDouble(readUnsignedLong());
  }

// line 135 "../../../../TupleInput.ump"
  public final int readUnsignedByte () throws IndexOutOfBoundsException 
  {
    int c = readFast();
	if (c < 0) {
	    throw new IndexOutOfBoundsException();
	}
	return c;
  }

// line 148 "../../../../TupleInput.ump"
  public final int readUnsignedShort () throws IndexOutOfBoundsException 
  {
    int c1 = readFast();
	int c2 = readFast();
	if ((c1 | c2) < 0) {
	    throw new IndexOutOfBoundsException();
	}
	return ((c1 << 8) | c2);
  }

// line 162 "../../../../TupleInput.ump"
  public final long readUnsignedInt () throws IndexOutOfBoundsException 
  {
    long c1 = readFast();
	long c2 = readFast();
	long c3 = readFast();
	long c4 = readFast();
	if ((c1 | c2 | c3 | c4) < 0) {
	    throw new IndexOutOfBoundsException();
	}
	return ((c1 << 24) | (c2 << 16) | (c3 << 8) | c4);
  }

// line 176 "../../../../TupleInput.ump"
  private final long readUnsignedLong () throws IndexOutOfBoundsException 
  {
    long c1 = readFast();
	long c2 = readFast();
	long c3 = readFast();
	long c4 = readFast();
	long c5 = readFast();
	long c6 = readFast();
	long c7 = readFast();
	long c8 = readFast();
	if ((c1 | c2 | c3 | c4 | c5 | c6 | c7 | c8) < 0) {
	    throw new IndexOutOfBoundsException();
	}
	return ((c1 << 56) | (c2 << 48) | (c3 << 40) | (c4 << 32) | (c5 << 24) | (c6 << 16) | (c7 << 8) | c8);
  }

// line 197 "../../../../TupleInput.ump"
  public final String readBytes (int length) throws IndexOutOfBoundsException 
  {
    StringBuffer buf = new StringBuffer(length);
	for (int i = 0; i < length; i++) {
	    int c = readFast();
	    if (c < 0) {
		throw new IndexOutOfBoundsException();
	    }
	    buf.append((char) c);
	}
	return buf.toString();
  }

// line 215 "../../../../TupleInput.ump"
  public final String readChars (int length) throws IndexOutOfBoundsException 
  {
    StringBuffer buf = new StringBuffer(length);
	for (int i = 0; i < length; i++) {
	    buf.append(readChar());
	}
	return buf.toString();
  }

// line 228 "../../../../TupleInput.ump"
  public final void readBytes (char[] chars) throws IndexOutOfBoundsException 
  {
    for (int i = 0; i < chars.length; i++) {
	    int c = readFast();
	    if (c < 0) {
		throw new IndexOutOfBoundsException();
	    }
	    chars[i] = (char) c;
	}
  }

// line 243 "../../../../TupleInput.ump"
  public final void readChars (char[] chars) throws IndexOutOfBoundsException 
  {
    for (int i = 0; i < chars.length; i++) {
	    chars[i] = readChar();
	}
  }

// line 256 "../../../../TupleInput.ump"
  public final String readString (int length) throws IndexOutOfBoundsException, IllegalArgumentException 
  {
    char[] chars = new char[length];
	readString(chars);
	return new String(chars);
  }

// line 268 "../../../../TupleInput.ump"
  public final void readString (char[] chars) throws IndexOutOfBoundsException, IllegalArgumentException 
  {
    byte[] buf = getBufferBytes();
	off = UtfOps.bytesToChars(buf, off, chars, 0, chars.length, false);
  }

  
}