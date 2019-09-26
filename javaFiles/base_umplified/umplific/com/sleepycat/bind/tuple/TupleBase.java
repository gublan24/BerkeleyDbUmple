/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.bind.tuple;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.DatabaseEntry;

// line 3 "../../../../TupleBase.ump"
public class TupleBase
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TupleBase()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Initializes the initial output buffer size to zero. <p> Unless  {@link #setTupleBufferSize} is called, the default {@link com.sleepycat.util.FastOutputStream#DEFAULT_INIT_SIZE} size will be used.</p>
   */
  // line 13 "../../../../TupleBase.ump"
   public  TupleBase(){
    outputBufferSize = 0;
  }


  /**
   * 
   * Sets the initial byte size of the output buffer that is allocated by the default implementation of  {@link #getTupleOutput}. <p> If this property is zero (the default), the default  {@link com.sleepycat.util.FastOutputStream#DEFAULT_INIT_SIZE} size is used.</p>
   * @param byteSizethe initial byte size of the output buffer, or zero to use thedefault size.
   */
  // line 21 "../../../../TupleBase.ump"
   public void setTupleBufferSize(int byteSize){
    outputBufferSize = byteSize;
  }


  /**
   * 
   * Returns the initial byte size of the output buffer.
   * @return the initial byte size of the output buffer.
   * @see #setTupleBufferSize
   */
  // line 30 "../../../../TupleBase.ump"
   public int getTupleBufferSize(){
    return outputBufferSize;
  }


  /**
   * 
   * Returns an empty TupleOutput instance that will be used by the tuple binding or key creator. <p> The default implementation of this method creates a new TupleOutput with an initial buffer size that can be changed using the  {@link #setTupleBufferSize} method.</p> <p> This method may be overridden to return a TupleOutput instance. For example, an instance per thread could be created and returned by this method. If a TupleOutput instance is reused, be sure to call its {@link com.sleepycat.util.FastOutputStream#reset} method before each use.</p>
   * @param objectis the object to be written to the tuple output, and may beused by subclasses to determine the size of the output buffer.
   * @return an empty TupleOutput instance.
   * @see #setTupleBufferSize
   */
  // line 40 "../../../../TupleBase.ump"
   protected TupleOutput getTupleOutput(Object object){
    int byteSize = getTupleBufferSize();
	if (byteSize != 0) {
	    return new TupleOutput(new byte[byteSize]);
	} else {
	    return new TupleOutput();
	}
  }


  /**
   * 
   * Utility method to set the data in a entry buffer to the data in a tuple output object.
   * @param outputis the source tuple output object.
   * @param entryis the destination entry buffer.
   */
  // line 54 "../../../../TupleBase.ump"
   public static  void outputToEntry(TupleOutput output, DatabaseEntry entry){
    entry.setData(output.getBufferBytes(), output.getBufferOffset(), output.getBufferLength());
  }


  /**
   * 
   * Utility method to set the data in a entry buffer to the data in a tuple input object.
   * @param inputis the source tuple input object.
   * @param entryis the destination entry buffer.
   */
  // line 63 "../../../../TupleBase.ump"
   public static  void inputToEntry(TupleInput input, DatabaseEntry entry){
    entry.setData(input.getBufferBytes(), input.getBufferOffset(), input.getBufferLength());
  }


  /**
   * 
   * Utility method to create a new tuple input object for reading the data from a given buffer. If an existing input is reused, it is reset before returning it.
   * @param entryis the source entry buffer.
   * @return the new tuple input object.
   */
  // line 72 "../../../../TupleBase.ump"
   public static  TupleInput entryToInput(DatabaseEntry entry){
    return new TupleInput(entry.getData(), entry.getOffset(), entry.getSize());
  }


  /**
   * 
   * Utility method for use by bindings to create a tuple output object.
   * @return a new tuple output object.
   * @deprecated replaced by {@link #getTupleOutput}
   */
  // line 81 "../../../../TupleBase.ump"
   public static  TupleOutput newOutput(){
    return new TupleOutput();
  }


  /**
   * 
   * Utility method for use by bindings to create a tuple output object with a specific starting size.
   * @return a new tuple output object.
   * @deprecated replaced by {@link #getTupleOutput}
   */
  // line 90 "../../../../TupleBase.ump"
   public static  TupleOutput newOutput(byte [] buffer){
    return new TupleOutput(buffer);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 7 "../../../../TupleBase.ump"
  private int outputBufferSize ;

  
}