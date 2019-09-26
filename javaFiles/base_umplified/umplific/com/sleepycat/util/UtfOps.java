/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.util;
import de.ovgu.cide.jakutil.*;

// line 3 "../../../UtfOps.ump"
public class UtfOps
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public UtfOps()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Returns the byte length of a null terminated UTF string, not including the terminator.
   * @param bytes the data containing the UTF string.
   * @param offset the beginning of the string the measure.
   * @throws IndexOutOfBoundsException if no zero terminator is found.
   * @return the number of bytes.
   */
  // line 18 "../../../UtfOps.ump"
   public static  int getZeroTerminatedByteLength(byte [] bytes, int offset) throws IndexOutOfBoundsException{
    int len = 0;
	while (bytes[offset++] != 0) {
	    len++;
	}
	return len;
  }


  /**
   * 
   * Returns the byte length of the UTF string that would be created by converting the given characters to UTF.
   * @param chars the characters that would be converted.
   * @return the byte length of the equivalent UTF data.
   */
  // line 31 "../../../UtfOps.ump"
   public static  int getByteLength(char [] chars){
    return getByteLength(chars, 0, chars.length);
  }


  /**
   * 
   * Returns the byte length of the UTF string that would be created by converting the given characters to UTF.
   * @param chars the characters that would be converted.
   * @param offset the first character to be converted.
   * @param length the number of characters to be converted.
   * @return the byte length of the equivalent UTF data.
   */
  // line 42 "../../../UtfOps.ump"
   public static  int getByteLength(char [] chars, int offset, int length){
    int len = 0;
	length += offset;
	for (int i = offset; i < length; i++) {
	    int c = chars[i];
	    if ((c >= 0x0001) && (c <= 0x007F)) {
		len++;
	    } else if (c > 0x07FF) {
		len += 3;
	    } else {
		len += 2;
	    }
	}
	return len;
  }


  /**
   * 
   * Returns the number of characters represented by the given UTF string.
   * @param bytes the UTF string.
   * @return the number of characters.
   * @throws IndexOutOfBoundsException if a UTF character sequence at the endof the data is not complete.
   * @throws IllegalArgumentException if an illegal UTF sequence isencountered.
   */
  // line 65 "../../../UtfOps.ump"
   public static  int getCharLength(byte [] bytes) throws IllegalArgumentException,IndexOutOfBoundsException{
    return getCharLength(bytes, 0, bytes.length);
  }


  /**
   * 
   * Returns the number of characters represented by the given UTF string.
   * @param bytes the data containing the UTF string.
   * @param offset the first byte to be converted.
   * @param length the number of byte to be converted.
   * @throws IndexOutOfBoundsException if a UTF character sequence at the endof the data is not complete.
   * @throws IllegalArgumentException if an illegal UTF sequence isencountered.
   */
  // line 78 "../../../UtfOps.ump"
   public static  int getCharLength(byte [] bytes, int offset, int length) throws IllegalArgumentException,IndexOutOfBoundsException{
    int charCount = 0;
	length += offset;
	while (offset < length) {
	    switch ((bytes[offset] & 0xff) >> 4) {
	    case 0:
	    case 1:
	    case 2:
	    case 3:
	    case 4:
	    case 5:
	    case 6:
	    case 7:
		offset++;
		break;
	    case 12:
	    case 13:
		offset += 2;
		break;
	    case 14:
		offset += 3;
		break;
	    default:
		throw new IllegalArgumentException();
	    }
	    charCount++;
	}
	return charCount;
  }


  /**
   * 
   * Converts byte arrays into character arrays.
   * @param bytes the source byte data to convert
   * @param byteOffset the offset into the byte array at whichto start the conversion
   * @param chars the destination array
   * @param charOffset the offset into chars at which to begin the copy
   * @param len the amount of information to copy into chars
   * @param isByteLen if true then len is a measure of bytes, otherwiselen is a measure of characters
   * @throws IndexOutOfBoundsException if a UTF character sequence at the endof the data is not complete.
   * @throws IllegalArgumentException if an illegal UTF sequence isencountered.
   */
  // line 120 "../../../UtfOps.ump"
   public static  int bytesToChars(byte [] bytes, int byteOffset, char [] chars, int charOffset, int len, boolean isByteLen) throws IllegalArgumentException,IndexOutOfBoundsException{
    int char1, char2, char3;
	len += isByteLen ? byteOffset : charOffset;
	while ((isByteLen ? byteOffset : charOffset) < len) {
	    char1 = bytes[byteOffset++] & 0xff;
	    switch ((char1 & 0xff) >> 4) {
	    case 0:
	    case 1:
	    case 2:
	    case 3:
	    case 4:
	    case 5:
	    case 6:
	    case 7:
		chars[charOffset++] = (char) char1;
		break;
	    case 12:
	    case 13:
		char2 = bytes[byteOffset++];
		if ((char2 & 0xC0) != 0x80) {
		    throw new IllegalArgumentException();
		}
		chars[charOffset++] = (char) (((char1 & 0x1F) << 6) | (char2 & 0x3F));
		break;
	    case 14:
		char2 = bytes[byteOffset++];
		char3 = bytes[byteOffset++];
		if (((char2 & 0xC0) != 0x80) || ((char3 & 0xC0) != 0x80))
		    throw new IllegalArgumentException();
		chars[charOffset++] = (char) (((char1 & 0x0F) << 12) | ((char2 & 0x3F) << 6) | ((char3 & 0x3F) << 0));
		break;
	    default:
		throw new IllegalArgumentException();
	    }
	}
	return byteOffset;
  }


  /**
   * 
   * Converts character arrays into byte arrays.
   * @param chars the source character data to convert
   * @param charOffset the offset into the character array at whichto start the conversion
   * @param bytes the destination array
   * @param byteOffset the offset into bytes at which to begin the copy
   * @param charLength the length of characters to copy into bytes
   */
  // line 166 "../../../UtfOps.ump"
   public static  void charsToBytes(char [] chars, int charOffset, byte [] bytes, int byteOffset, int charLength){
    charLength += charOffset;
	for (int i = charOffset; i < charLength; i++) {
	    int c = chars[i];
	    if ((c >= 0x0001) && (c <= 0x007F)) {
		bytes[byteOffset++] = (byte) c;
	    } else if (c > 0x07FF) {
		bytes[byteOffset++] = (byte) (0xE0 | ((c >> 12) & 0x0F));
		bytes[byteOffset++] = (byte) (0x80 | ((c >> 6) & 0x3F));
		bytes[byteOffset++] = (byte) (0x80 | ((c >> 0) & 0x3F));
	    } else {
		bytes[byteOffset++] = (byte) (0xC0 | ((c >> 6) & 0x1F));
		bytes[byteOffset++] = (byte) (0x80 | ((c >> 0) & 0x3F));
	    }
	}
  }


  /**
   * 
   * Converts byte arrays into strings.
   * @param bytes the source byte data to convert
   * @param offset the offset into the byte array at whichto start the conversion
   * @param length the number of bytes to be converted.
   * @return the string.
   * @throws IndexOutOfBoundsException if a UTF character sequence at the endof the data is not complete.
   * @throws IllegalArgumentException if an illegal UTF sequence isencountered.
   */
  // line 193 "../../../UtfOps.ump"
   public static  String bytesToString(byte [] bytes, int offset, int length) throws IllegalArgumentException,IndexOutOfBoundsException{
    if (length == 0)
	    return EMPTY_STRING;
	int charLen = UtfOps.getCharLength(bytes, offset, length);
	char[] chars = new char[charLen];
	UtfOps.bytesToChars(bytes, offset, chars, 0, length, true);
	return new String(chars, 0, charLen);
  }


  /**
   * 
   * Converts strings to byte arrays.
   * @param string the string to convert.
   * @return the UTF byte array.
   */
  // line 207 "../../../UtfOps.ump"
   public static  byte[] stringToBytes(String string){
    if (string.length() == 0)
	    return EMPTY_BYTES;
	char[] chars = string.toCharArray();
	byte[] bytes = new byte[UtfOps.getByteLength(chars)];
	UtfOps.charsToBytes(chars, 0, bytes, 0, chars.length);
	return bytes;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 6 "../../../UtfOps.ump"
  private static byte[] EMPTY_BYTES = {};
// line 8 "../../../UtfOps.ump"
  private static String EMPTY_STRING = "" ;

  
}