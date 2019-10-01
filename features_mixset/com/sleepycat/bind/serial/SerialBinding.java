/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.bind.serial;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.util.RuntimeExceptionWrapper;
import com.sleepycat.util.FastOutputStream;
import com.sleepycat.util.FastInputStream;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.bind.EntryBinding;
import java.io.IOException;
import com.sleepycat.bind.*;

// line 3 "../../../../SerialBinding.ump"
public class SerialBinding extends SerialBase implements EntryBinding
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SerialBinding()
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
   * Creates a serial binding.
   * @param classCatalogis the catalog to hold shared class information and for adatabase should be a  {@link StoredClassCatalog}.
   * @param baseClassis the base class for serialized objects stored using thisbinding -- all objects using this binding must be an instance of this class.
   */
  // line 24 "../../../../SerialBinding.ump"
   public  SerialBinding(ClassCatalog classCatalog, Class baseClass){
    if (classCatalog == null) {
	    throw new NullPointerException("classCatalog must be non-null");
	}
	this.classCatalog = classCatalog;
	this.baseClass = baseClass;
  }


  /**
   * 
   * Returns the class loader to be used during deserialization, or null if a default class loader should be used. The default implementation of this method returns null. <p> This method may be overriden to return a dynamically determined class loader. For example, <code>Thread.currentThread().getContextClassLoader()</code> could be called to use the context class loader for the curren thread. Or <code>getBaseClass().getClassLoader()</code> could be called to use the class loader for the base class, assuming that a base class has been specified. </p> <p> If this method returns null, a default class loader will be used as determined by the <code>java.io.ObjectInputStream.resolveClass</code> method. </p>
   */
  // line 43 "../../../../SerialBinding.ump"
   public ClassLoader getClassLoader(){
    return null;
  }


  /**
   * 
   * Deserialize an object from an entry buffer. May only be called for data that was serialized using  {@link #objectToEntry}, since the fixed serialization header is assumed to not be included in the input data. {@link SerialInput} is used to deserialize the object.
   * @param entryis the input serialized entry.
   * @return the output deserialized object.
   */
  // line 52 "../../../../SerialBinding.ump"
   public Object entryToObject(DatabaseEntry entry){
    int length = entry.getSize();
	byte[] hdr = SerialOutput.getStreamHeader();
	byte[] bufWithHeader = new byte[length + hdr.length];
	System.arraycopy(hdr, 0, bufWithHeader, 0, hdr.length);
	System.arraycopy(entry.getData(), entry.getOffset(), bufWithHeader, hdr.length, length);
	try {
	    SerialInput jin = new SerialInput(new FastInputStream(bufWithHeader, 0, bufWithHeader.length), classCatalog,
		    getClassLoader());
	    return jin.readObject();
	} catch (IOException e) {
	    throw new RuntimeExceptionWrapper(e);
	} catch (ClassNotFoundException e) {
	    throw new RuntimeExceptionWrapper(e);
	}
  }


  /**
   * 
   * Serialize an object into an entry buffer. The fixed serialization header is not included in the output data to save space, and therefore to deserialize the data the complementary  {@link #entryToObject} method mustbe used.  {@link SerialOutput} is used to serialize the object.<p> Note that this method sets the DatabaseEntry offset property to a non-zero value and the size property to a value less than the length of the byte array. </p>
   * @param objectis the input deserialized object.
   * @param entryis the output serialized entry.
   * @throws IllegalArgumentExceptionif the object is not an instance of the base class for thisbinding.
   */
  // line 75 "../../../../SerialBinding.ump"
   public void objectToEntry(Object object, DatabaseEntry entry){
    if (baseClass != null && !baseClass.isInstance(object)) {
	    throw new IllegalArgumentException("Data object class (" + object.getClass()
		    + ") not an instance of binding's base class (" + baseClass + ')');
	}
	FastOutputStream fo = getSerialOutput(object);
	try {
	    SerialOutput jos = new SerialOutput(fo, classCatalog);
	    jos.writeObject(object);
	} catch (IOException e) {
	    throw new RuntimeExceptionWrapper(e);
	}
	byte[] hdr = SerialOutput.getStreamHeader();
	entry.setData(fo.getBufferBytes(), hdr.length, fo.getBufferLength() - hdr.length);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 14 "../../../../SerialBinding.ump"
  private ClassCatalog classCatalog ;
// line 16 "../../../../SerialBinding.ump"
  private Class baseClass ;

// line 35 "../../../../SerialBinding.ump"
  public final Class getBaseClass () 
  {
    return baseClass;
  }

  
}