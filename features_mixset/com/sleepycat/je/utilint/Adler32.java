/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.utilint;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.dbi.EnvironmentImpl;
import java.util.zip.Checksum;
import com.sleepycat.bind.serial.*;

// line 3 "../../../../Adler32.ump"
public class Adler32 implements Checksum
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Adler32()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 16 "../../../../Adler32.ump"
   public static  Checksum makeChecksum(){
    if (EnvironmentImpl.JAVA5_AVAILABLE) {
	    return new java.util.zip.Adler32();
	} else {
	    return new Adler32();
	}
  }


  /**
   * 
   * Update current Adler-32 checksum given the specified byte.
   */
  // line 27 "../../../../Adler32.ump"
   public void update(int b){
    long s1 = adler & 0xffff;
	long s2 = (adler >> 16) & 0xffff;
	s1 = (s1 + (b & 0xff)) % BASE;
	s2 = (s1 + s2) % BASE;
	adler = (s2 << 16) | s1;
  }


  /**
   * 
   * Update current Adler-32 checksum given the specified byte array.
   */
  // line 38 "../../../../Adler32.ump"
   public void update(byte [] b, int off, int len){
    long s1 = adler & 0xffff;
	long s2 = (adler >> 16) & 0xffff;
	while (len > 0) {
	    int k = len < NMAX ? len : NMAX;
	    len -= k;
	    while (k-- > 0) {
		s1 += (b[off++] & 0xff);
		s2 += s1;
	    }
	    s1 %= BASE;
	    s2 %= BASE;
	}
	adler = (s2 << 16) | s1;
  }


  /**
   * 
   * Reset Adler-32 checksum to initial value.
   */
  // line 57 "../../../../Adler32.ump"
   public void reset(){
    adler = 1;
  }


  /**
   * 
   * Returns current checksum value.
   */
  // line 64 "../../../../Adler32.ump"
   public long getValue(){
    return adler;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 9 "../../../../Adler32.ump"
  private long adler = 1 ;
// line 11 "../../../../Adler32.ump"
  private static final int BASE = 65521 ;
// line 13 "../../../../Adler32.ump"
  private static final int NMAX = 5552 ;

  
}