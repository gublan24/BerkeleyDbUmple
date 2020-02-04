/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.bind.serial;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.util.RuntimeExceptionWrapper;
import com.sleepycat.je.DatabaseException;
import java.io.OutputStream;
import java.io.ObjectStreamConstants;
import java.io.ObjectStreamClass;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;

// line 3 "../../../../SerialOutput.ump"
public class SerialOutput extends ObjectOutputStream
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SerialOutput()
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
   * Creates a serial output stream.
   * @param outis the output stream to which the compact serialized objectswill be written.
   * @param classCatalogis the catalog to which the class descriptions for theserialized objects will be written.
   */
  // line 25 "../../../../SerialOutput.ump"
   public  SerialOutput(OutputStream out, ClassCatalog classCatalog) throws IOException{
    super(out);
	this.classCatalog = classCatalog;
	useProtocolVersion(ObjectStreamConstants.PROTOCOL_VERSION_2);
  }

  // line 31 "../../../../SerialOutput.ump"
   protected void writeClassDescriptor(ObjectStreamClass classdesc) throws IOException{
    try {
	    byte[] id = classCatalog.getClassID(classdesc);
	    writeByte(id.length);
	    write(id);
	} catch (DatabaseException e) {
	    throw new RuntimeExceptionWrapper(e);
	} catch (ClassNotFoundException e) {
	    throw new RuntimeExceptionWrapper(e);
	}
  }


  /**
   * 
   * Returns the fixed stream header used for all serialized streams in PROTOCOL_VERSION_2 format. To save space this header can be removed and serialized streams before storage and inserted before deserializing. {@link SerialOutput} always uses PROTOCOL_VERSION_2 serialization formatto guarantee that this header is fixed.  {@link SerialBinding} removesthis header from serialized streams automatically.
   * @return the fixed stream header.
   */
  // line 47 "../../../../SerialOutput.ump"
   public static  byte[] getStreamHeader(){
    return STREAM_HEADER;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 15 "../../../../SerialOutput.ump"
  private   static byte[] STREAM_HEADER ;
// line 17 "../../../../SerialOutput.ump"
  private ClassCatalog classCatalog ;

  
}