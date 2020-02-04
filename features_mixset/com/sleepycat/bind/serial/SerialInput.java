/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.bind.serial;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.util.RuntimeExceptionWrapper;
import com.sleepycat.je.DatabaseException;
import java.io.ObjectStreamClass;
import java.io.ObjectInputStream;
import java.io.InputStream;
import java.io.IOException;

// line 3 "../../../../SerialInput.ump"
public class SerialInput extends ObjectInputStream
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SerialInput()
  {
    super();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Creates a serial input stream.
   * @param inis the input stream from which compact serialized objects willbe read.
   * @param classCatalogis the catalog containing the class descriptions for theserialized objects.
   */
  // line 23 "../../../../SerialInput.ump"
   public  SerialInput(InputStream in, ClassCatalog classCatalog) throws IOException{
    this(in, classCatalog, null);
  }


  /**
   * 
   * Creates a serial input stream.
   * @param inis the input stream from which compact serialized objects willbe read.
   * @param classCatalogis the catalog containing the class descriptions for theserialized objects.
   * @param classLoaderis the class loader to use, or null if a default class loadershould be used.
   */
  // line 33 "../../../../SerialInput.ump"
   public  SerialInput(InputStream in, ClassCatalog classCatalog, ClassLoader classLoader) throws IOException{
    super(in);
	this.classCatalog = classCatalog;
	this.classLoader = classLoader;
  }

  // line 39 "../../../../SerialInput.ump"
   protected ObjectStreamClass readClassDescriptor() throws IOException,ClassNotFoundException{
    try {
	    byte len = readByte();
	    byte[] id = new byte[len];
	    readFully(id);
	    return classCatalog.getClassFormat(id);
	} catch (DatabaseException e) {
	    throw new RuntimeExceptionWrapper(e);
	}
  }

  // line 50 "../../../../SerialInput.ump"
   protected Class resolveClass(ObjectStreamClass desc) throws IOException,ClassNotFoundException{
    if (classLoader != null) {
	    try {
		return Class.forName(desc.getName(), false, classLoader);
	    } catch (ClassNotFoundException e) {
		return super.resolveClass(desc);
	    }
	} else {
	    return super.resolveClass(desc);
	}
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 13 "../../../../SerialInput.ump"
  private ClassCatalog classCatalog ;
// line 15 "../../../../SerialInput.ump"
  private ClassLoader classLoader ;

  
}