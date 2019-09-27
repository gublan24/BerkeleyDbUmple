/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.bind.serial;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.util.FastOutputStream;

// line 3 "../../../../SerialBase.ump"
public class SerialBase
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SerialBase()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Initializes the initial output buffer size to zero. <p> Unless  {@link #setSerialBufferSize} is called, the default {@link FastOutputStream#DEFAULT_INIT_SIZE} size will be used.</p>
   */
  // line 13 "../../../../SerialBase.ump"
   public  SerialBase(){
    outputBufferSize = 0;
  }


  /**
   * 
   * Sets the initial byte size of the output buffer that is allocated by the default implementation of  {@link #getSerialOutput}. <p> If this property is zero (the default), the default  {@link FastOutputStream#DEFAULT_INIT_SIZE} size is used.</p>
   * @param byteSizethe initial byte size of the output buffer, or zero to use thedefault size.
   */
  // line 21 "../../../../SerialBase.ump"
   public void setSerialBufferSize(int byteSize){
    outputBufferSize = byteSize;
  }


  /**
   * 
   * Returns the initial byte size of the output buffer.
   * @return the initial byte size of the output buffer.
   * @see #setSerialBufferSize
   */
  // line 30 "../../../../SerialBase.ump"
   public int getSerialBufferSize(){
    return outputBufferSize;
  }


  /**
   * 
   * Returns an empty SerialOutput instance that will be used by the serial binding or key creator. <p> The default implementation of this method creates a new SerialOutput with an initial buffer size that can be changed using the  {@link #setSerialBufferSize} method.</p> <p> This method may be overridden to return a FastOutputStream instance. For example, an instance per thread could be created and returned by this method. If a FastOutputStream instance is reused, be sure to call its {@link FastOutputStream#reset} method before each use.</p>
   * @param objectis the object to be written to the serial output, and may beused by subclasses to determine the size of the output buffer.
   * @return an empty FastOutputStream instance.
   * @see #setSerialBufferSize
   */
  // line 40 "../../../../SerialBase.ump"
   protected FastOutputStream getSerialOutput(Object object){
    int byteSize = getSerialBufferSize();
	if (byteSize != 0) {
	    return new FastOutputStream(byteSize);
	} else {
	    return new FastOutputStream();
	}
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 7 "../../../../SerialBase.ump"
  private int outputBufferSize ;

  
}