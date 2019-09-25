/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.util;
import de.ovgu.cide.jakutil.*;
import java.io.UnsupportedEncodingException;
import java.io.OutputStream;
import java.io.IOException;

// line 3 "../../../FastOutputStream.ump"
public class FastOutputStream
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public FastOutputStream()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Creates an output stream with default sizes.
   */
  // line 32 "../../../FastOutputStream.ump"
   public  FastOutputStream(){
    initBuffer(DEFAULT_INIT_SIZE, DEFAULT_BUMP_SIZE);
  }


  /**
   * 
   * Creates an output stream with a default bump size and a given initial size.
   * @param initialSize the initial size of the buffer.
   */
  // line 40 "../../../FastOutputStream.ump"
   public  FastOutputStream(int initialSize){
    initBuffer(initialSize, DEFAULT_BUMP_SIZE);
  }


  /**
   * 
   * Creates an output stream with a given bump size and initial size.
   * @param initialSize the initial size of the buffer.
   * @param bumpSize the amount to increment the buffer.
   */
  // line 49 "../../../FastOutputStream.ump"
   public  FastOutputStream(int initialSize, int bumpSize){
    initBuffer(initialSize, bumpSize);
  }


  /**
   * 
   * Creates an output stream with a given initial buffer and a default bump size.
   * @param buffer the initial buffer; will be owned by this object.
   */
  // line 57 "../../../FastOutputStream.ump"
   public  FastOutputStream(byte [] buffer){
    buf = buffer;
	bumpLen = DEFAULT_BUMP_SIZE;
  }


  /**
   * 
   * Creates an output stream with a given initial buffer and a given bump size.
   * @param buffer the initial buffer; will be owned by this object.
   * @param bumpSize the amount to increment the buffer.  If zero (thedefault), the current buffer size will be doubled when the buffer is full.
   */
  // line 67 "../../../FastOutputStream.ump"
   public  FastOutputStream(byte [] buffer, int bumpSize){
    buf = buffer;
	bumpLen = bumpSize;
  }

  // line 72 "../../../FastOutputStream.ump"
   private void initBuffer(int bufferSize, int bumpLen){
    buf = new byte[bufferSize];
	this.bumpLen = bumpLen;
  }

  // line 77 "../../../FastOutputStream.ump"
   public int size(){
    return len;
  }

  // line 81 "../../../FastOutputStream.ump"
   public void reset(){
    len = 0;
  }

  // line 85 "../../../FastOutputStream.ump"
   public void write(int b) throws IOException{
    writeFast(b);
  }

  // line 89 "../../../FastOutputStream.ump"
   public void write(byte [] fromBuf) throws IOException{
    writeFast(fromBuf);
  }

  // line 93 "../../../FastOutputStream.ump"
   public void write(byte [] fromBuf, int offset, int length) throws IOException{
    writeFast(fromBuf, offset, length);
  }

  // line 97 "../../../FastOutputStream.ump"
   public void writeTo(OutputStream out) throws IOException{
    out.write(buf, 0, len);
  }

  // line 101 "../../../FastOutputStream.ump"
   public String toString(){
    return new String(buf, 0, len);
  }

  // line 105 "../../../FastOutputStream.ump"
   public String toString(String encoding) throws UnsupportedEncodingException{
    return new String(buf, 0, len, encoding);
  }

  // line 109 "../../../FastOutputStream.ump"
   public byte[] toByteArray(){
    if (len == 0) {
	    return ZERO_LENGTH_BYTE_ARRAY;
	} else {
	    byte[] toBuf = new byte[len];
	    System.arraycopy(buf, 0, toBuf, 0, len);
	    return toBuf;
	}
  }


  /**
   * 
   * Returns the buffer owned by this object.
   * @return the buffer.
   */
  // line 157 "../../../FastOutputStream.ump"
   public byte[] getBufferBytes(){
    return buf;
  }


  /**
   * 
   * Returns the offset of the internal buffer.
   * @return always zero currently.
   */
  // line 165 "../../../FastOutputStream.ump"
   public int getBufferOffset(){
    return 0;
  }


  /**
   * 
   * Returns the length used in the internal buffer, i.e., the offset at which data will be written next.
   * @return the buffer length.
   */
  // line 173 "../../../FastOutputStream.ump"
   public int getBufferLength(){
    return len;
  }


  /**
   * 
   * Ensure that at least the given number of bytes are available in the internal buffer.
   * @param sizeNeeded the number of bytes desired.
   */
  // line 181 "../../../FastOutputStream.ump"
   public void makeSpace(int sizeNeeded){
    int needed = len + sizeNeeded - buf.length;
	if (needed > 0)
	    bump(needed);
  }


  /**
   * 
   * Skip the given number of bytes in the buffer.
   * @param sizeAdded number of bytes to skip.
   */
  // line 191 "../../../FastOutputStream.ump"
   public void addSize(int sizeAdded){
    len += sizeAdded;
  }

  // line 195 "../../../FastOutputStream.ump"
   private void bump(int needed){
    int bump = (bumpLen > 0) ? bumpLen : buf.length;
	byte[] toBuf = new byte[buf.length + needed + bump];
	System.arraycopy(buf, 0, toBuf, 0, len);
	buf = toBuf;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 13 "../../../FastOutputStream.ump"
  public static final int DEFAULT_INIT_SIZE = 100 ;
// line 18 "../../../FastOutputStream.ump"
  public static final int DEFAULT_BUMP_SIZE = 0 ;
// line 20 "../../../FastOutputStream.ump"
  private int len ;
// line 22 "../../../FastOutputStream.ump"
  private int bumpLen ;
// line 24 "../../../FastOutputStream.ump"
  private byte[] buf ;
// line 26 "../../../FastOutputStream.ump"
  private static byte[] ZERO_LENGTH_BYTE_ARRAY = new byte[0] ;

// line 122 "../../../FastOutputStream.ump"
  public final void writeFast (int b) 
  {
    if (len + 1 > buf.length)
	    bump(1);
	buf[len++] = (byte) b;
  }

// line 132 "../../../FastOutputStream.ump"
  public final void writeFast (byte[] fromBuf) 
  {
    int needed = len + fromBuf.length - buf.length;
	if (needed > 0)
	    bump(needed);
	System.arraycopy(fromBuf, 0, buf, len, fromBuf.length);
	len += fromBuf.length;
  }

// line 144 "../../../FastOutputStream.ump"
  public final void writeFast (byte[] fromBuf, int offset, int length) 
  {
    int needed = len + length - buf.length;
	if (needed > 0)
	    bump(needed);
	System.arraycopy(fromBuf, offset, buf, len, length);
	len += length;
  }

  
}