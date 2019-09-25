/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.util;
import de.ovgu.cide.jakutil.*;
import java.io.InputStream;
import java.io.IOException;
import com.sleepycat.bind.serial.*;

// line 3 "../../../FastInputStream.ump"
public class FastInputStream implements InputStream
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public FastInputStream()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Creates an input stream.
   * @param buffer the data to read.
   */
  // line 22 "../../../FastInputStream.ump"
   public  FastInputStream(byte [] buffer){
    buf = buffer;
	len = buffer.length;
  }


  /**
   * 
   * Creates an input stream.
   * @param buffer the data to read.
   * @param offset the byte offset at which to begin reading.
   * @param length the number of bytes to read.
   */
  // line 33 "../../../FastInputStream.ump"
   public  FastInputStream(byte [] buffer, int offset, int length){
    buf = buffer;
	off = offset;
	len = length;
  }

  // line 39 "../../../FastInputStream.ump"
   public int available(){
    return len - off;
  }

  // line 43 "../../../FastInputStream.ump"
   public boolean markSupported(){
    return true;
  }

  // line 47 "../../../FastInputStream.ump"
   public void mark(int pos){
    mark = pos;
  }

  // line 51 "../../../FastInputStream.ump"
   public void reset(){
    off = mark;
  }

  // line 55 "../../../FastInputStream.ump"
   public long skip(long count){
    int myCount = (int) count;
	if (myCount + off > len) {
	    myCount = len - off;
	}
	off += myCount;
	return myCount;
  }

  // line 64 "../../../FastInputStream.ump"
   public int read() throws IOException{
    return readFast();
  }

  // line 68 "../../../FastInputStream.ump"
   public int read(byte [] toBuf) throws IOException{
    return readFast(toBuf, 0, toBuf.length);
  }

  // line 72 "../../../FastInputStream.ump"
   public int read(byte [] toBuf, int offset, int length) throws IOException{
    return readFast(toBuf, offset, length);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 9 "../../../FastInputStream.ump"
  protected int len ;
// line 11 "../../../FastInputStream.ump"
  protected int off ;
// line 13 "../../../FastInputStream.ump"
  protected int mark ;
// line 15 "../../../FastInputStream.ump"
  protected byte[] buf ;

// line 79 "../../../FastInputStream.ump"
  public final int readFast () 
  {
    return (off < len) ? (buf[off++] & 0xff) : (-1);
  }

// line 87 "../../../FastInputStream.ump"
  public final int readFast (byte[] toBuf) 
  {
    return readFast(toBuf, 0, toBuf.length);
  }

// line 95 "../../../FastInputStream.ump"
  public final int readFast (byte[] toBuf, int offset, int length) 
  {
    int avail = len - off;
	if (avail <= 0) {
	    return -1;
	}
	if (length > avail) {
	    length = avail;
	}
	System.arraycopy(buf, off, toBuf, offset, length);
	off += length;
	return length;
  }

// line 112 "../../../FastInputStream.ump"
  public final byte[] getBufferBytes () 
  {
    return buf;
  }

// line 120 "../../../FastInputStream.ump"
  public final int getBufferOffset () 
  {
    return off;
  }

// line 128 "../../../FastInputStream.ump"
  public final int getBufferLength () 
  {
    return len;
  }

  
}