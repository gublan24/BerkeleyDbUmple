/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.bind.tuple;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.util.UtfOps;
import com.sleepycat.util.FastOutputStream;
import com.sleepycat.util.*;

// line 3 "../../../../TupleOutput.ump"
public class TupleOutput extends FastOutputStream
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TupleOutput()
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
   * Creates a tuple output object for writing a byte array of tuple data.
   */
  // line 18 "../../../../TupleOutput.ump"
   public  TupleOutput(){
    super();
  }


  /**
   * 
   * Creates a tuple output object for writing a byte array of tuple data, using a given buffer. A new buffer will be allocated only if the number of bytes needed is greater than the length of this buffer. A reference to the byte array will be kept by this object and therefore the byte array should not be modified while this object is in use.
   * @param bufferis the byte array to use as the buffer.
   */
  // line 26 "../../../../TupleOutput.ump"
   public  TupleOutput(byte [] buffer){
    super(buffer);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 12 "../../../../TupleOutput.ump"
  static final int NULL_STRING_UTF_VALUE = ((byte) 0xFF) ;

// line 35 "../../../../TupleOutput.ump"
  public final TupleOutput writeBytes (String val) 
  {
    writeBytes(val.toCharArray());
	return this;
  }

// line 46 "../../../../TupleOutput.ump"
  public final TupleOutput writeChars (String val) 
  {
    writeChars(val.toCharArray());
	return this;
  }

// line 56 "../../../../TupleOutput.ump"
  public final TupleOutput writeString (String val) 
  {
    if (val != null) {
	    writeString(val.toCharArray());
	} else {
	    writeFast(NULL_STRING_UTF_VALUE);
	}
	writeFast(0);
	return this;
  }

// line 71 "../../../../TupleOutput.ump"
  public final TupleOutput writeChar (int val) 
  {
    writeFast((byte) (val >>> 8));
	writeFast((byte) val);
	return this;
  }

// line 82 "../../../../TupleOutput.ump"
  public final TupleOutput writeBoolean (boolean val) 
  {
    writeFast(val ? (byte) 1 : (byte) 0);
	return this;
  }

// line 92 "../../../../TupleOutput.ump"
  public final TupleOutput writeByte (int val) 
  {
    writeUnsignedByte(val ^ 0x80);
	return this;
  }

// line 102 "../../../../TupleOutput.ump"
  public final TupleOutput writeShort (int val) 
  {
    writeUnsignedShort(val ^ 0x8000);
	return this;
  }

// line 112 "../../../../TupleOutput.ump"
  public final TupleOutput writeInt (int val) 
  {
    writeUnsignedInt(val ^ 0x80000000);
	return this;
  }

// line 122 "../../../../TupleOutput.ump"
  public final TupleOutput writeLong (long val) 
  {
    writeUnsignedLong(val ^ 0x8000000000000000L);
	return this;
  }

// line 132 "../../../../TupleOutput.ump"
  public final TupleOutput writeFloat (float val) 
  {
    writeUnsignedInt(Float.floatToIntBits(val));
	return this;
  }

// line 142 "../../../../TupleOutput.ump"
  public final TupleOutput writeDouble (double val) 
  {
    writeUnsignedLong(Double.doubleToLongBits(val));
	return this;
  }

// line 153 "../../../../TupleOutput.ump"
  public final TupleOutput writeBytes (char[] chars) 
  {
    for (int i = 0; i < chars.length; i++) {
	    writeFast((byte) chars[i]);
	}
	return this;
  }

// line 166 "../../../../TupleOutput.ump"
  public final TupleOutput writeChars (char[] chars) 
  {
    for (int i = 0; i < chars.length; i++) {
	    writeFast((byte) (chars[i] >>> 8));
	    writeFast((byte) chars[i]);
	}
	return this;
  }

// line 180 "../../../../TupleOutput.ump"
  public final TupleOutput writeString (char[] chars) 
  {
    if (chars.length == 0)
	    return this;
	int utfLength = UtfOps.getByteLength(chars);
	makeSpace(utfLength);
	UtfOps.charsToBytes(chars, 0, getBufferBytes(), getBufferLength(), chars.length);
	addSize(utfLength);
	return this;
  }

// line 195 "../../../../TupleOutput.ump"
  public final TupleOutput writeUnsignedByte (int val) 
  {
    writeFast(val);
	return this;
  }

// line 205 "../../../../TupleOutput.ump"
  public final TupleOutput writeUnsignedShort (int val) 
  {
    writeFast((byte) (val >>> 8));
	writeFast((byte) val);
	return this;
  }

// line 216 "../../../../TupleOutput.ump"
  public final TupleOutput writeUnsignedInt (long val) 
  {
    writeFast((byte) (val >>> 24));
	writeFast((byte) (val >>> 16));
	writeFast((byte) (val >>> 8));
	writeFast((byte) val);
	return this;
  }

// line 227 "../../../../TupleOutput.ump"
  private final TupleOutput writeUnsignedLong (long val) 
  {
    writeFast((byte) (val >>> 56));
	writeFast((byte) (val >>> 48));
	writeFast((byte) (val >>> 40));
	writeFast((byte) (val >>> 32));
	writeFast((byte) (val >>> 24));
	writeFast((byte) (val >>> 16));
	writeFast((byte) (val >>> 8));
	writeFast((byte) val);
	return this;
  }

  
}