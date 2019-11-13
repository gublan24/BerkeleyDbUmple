/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.bind.serial;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.util.UtfOps;
import com.sleepycat.util.RuntimeExceptionWrapper;
import com.sleepycat.je.OperationStatus;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.Database;
import com.sleepycat.je.CursorConfig;
import com.sleepycat.je.Cursor;
import com.sleepycat.compat.DbCompat;
import java.util.HashMap;
import java.math.BigInteger;
import java.io.Serializable;
import java.io.ObjectStreamClass;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;

// line 3 "../../../../StoredClassCatalog.ump"
// line 5 "../../../../StoredClassCatalog_static.ump"
public class StoredClassCatalog implements ClassCatalog
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public StoredClassCatalog()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Creates a catalog based on a given database. To save resources, only a single catalog object should be used for each unique catalog database.
   * @param databasean open database to use as the class catalog. It must be aBTREE database and must not allow duplicates.
   * @throws DatabaseExceptionif an error occurs accessing the database.
   * @throws IllegalArgumentExceptionif the database is not a BTREE database or if it configuredto allow duplicates.
   */
  // line 55 "../../../../StoredClassCatalog.ump"
   public  StoredClassCatalog(Database database) throws DatabaseException,IllegalArgumentException{
    db = database;
	DatabaseConfig dbConfig = db.getConfig();
	EnvironmentConfig envConfig = db.getEnvironment().getConfig();
	writeLockMode = hook_getLockMode(envConfig);
	cdbMode = DbCompat.getInitializeCDB(envConfig);
	if (!DbCompat.isTypeBtree(dbConfig)) {
	    throw new IllegalArgumentException("The class catalog must be a BTREE database.");
	}
	if (DbCompat.getSortedDuplicates(dbConfig) || DbCompat.getUnsortedDuplicates(dbConfig)) {
	    throw new IllegalArgumentException("The class catalog database must not allow duplicates.");
	}
	classMap = new HashMap();
	formatMap = new HashMap();
	DatabaseEntry key = new DatabaseEntry(LAST_CLASS_ID_KEY);
	DatabaseEntry data = new DatabaseEntry();
	if (dbConfig.getReadOnly()) {
	    OperationStatus status = db.get(null, key, data, null);
	    if (status != OperationStatus.SUCCESS) {
		throw new IllegalStateException("A read-only catalog database may not be empty");
	    }
	} else {
	    data.setData(new byte[1]);
	    db.putNoOverwrite(null, key, data);
	}
  }

  // line 82 "../../../../StoredClassCatalog.ump"
   private LockMode hook_getLockMode(EnvironmentConfig envConfig) throws DatabaseException{
    return (DbCompat.getInitializeLocking(envConfig)) ? LockMode.RMW : LockMode.DEFAULT;
  }

  // line 86 "../../../../StoredClassCatalog.ump"
   public synchronized  void close() throws DatabaseException{
    if (db != null) {
	    db.close();
	}
	db = null;
	formatMap = null;
	classMap = null;
  }

  // line 96 "../../../../StoredClassCatalog.ump"
   public synchronized  byte[] getClassID(ObjectStreamClass classFormat) throws DatabaseException,ClassNotFoundException{
    ClassInfo classInfo = getClassInfo(classFormat);
	return classInfo.getClassID();
  }

  // line 102 "../../../../StoredClassCatalog.ump"
   public synchronized  ObjectStreamClass getClassFormat(byte [] classID) throws DatabaseException,ClassNotFoundException{
    return getClassFormat(classID, new DatabaseEntry());
  }


  /**
   * 
   * Internal function for getting the class format. Allows passing the DatabaseEntry object for the data, so the bytes of the class format can be examined afterwards.
   */
  // line 110 "../../../../StoredClassCatalog.ump"
   private ObjectStreamClass getClassFormat(byte [] classID, DatabaseEntry data) throws DatabaseException,ClassNotFoundException{
    BigInteger classIDObj = new BigInteger(classID);
	ObjectStreamClass classFormat = (ObjectStreamClass) formatMap.get(classIDObj);
	if (classFormat == null) {
	    byte[] keyBytes = new byte[classID.length + 1];
	    keyBytes[0] = REC_CLASS_FORMAT;
	    System.arraycopy(classID, 0, keyBytes, 1, classID.length);
	    DatabaseEntry key = new DatabaseEntry(keyBytes);
	    OperationStatus status = db.get(null, key, data, LockMode.DEFAULT);
	    if (status != OperationStatus.SUCCESS) {
		throw new ClassNotFoundException("Catalog class ID not found");
	    }
	    try {
		ObjectInputStream ois = new ObjectInputStream(
			new ByteArrayInputStream(data.getData(), data.getOffset(), data.getSize()));
		classFormat = (ObjectStreamClass) ois.readObject();
	    } catch (IOException e) {
		throw new RuntimeExceptionWrapper(e);
	    }
	    formatMap.put(classIDObj, classFormat);
	}
	return classFormat;
  }


  /**
   * 
   * Get the ClassInfo for a given class name, adding it and its ObjectStreamClass to the database if they are not already present, and caching both of them using the class info and class format maps. When a class is first loaded from the database, the stored ObjectStreamClass is compared to the current ObjectStreamClass loaded by the Java class loader; if they are different, a new class ID is assigned for the current format.
   */
  // line 137 "../../../../StoredClassCatalog.ump"
   private ClassInfo getClassInfo(ObjectStreamClass classFormat) throws DatabaseException,ClassNotFoundException{
    String className = classFormat.getName();
	ClassInfo classInfo = (ClassInfo) classMap.get(className);
	if (classInfo != null) {
	    return classInfo;
	} else {
	    char[] nameChars = className.toCharArray();
	    byte[] keyBytes = new byte[1 + UtfOps.getByteLength(nameChars)];
	    keyBytes[0] = REC_CLASS_INFO;
	    UtfOps.charsToBytes(nameChars, 0, keyBytes, 1, nameChars.length);
	    DatabaseEntry key = new DatabaseEntry(keyBytes);
	    DatabaseEntry data = new DatabaseEntry();
	    OperationStatus status = db.get(null, key, data, LockMode.DEFAULT);
	    if (status != OperationStatus.SUCCESS) {
		classInfo = putClassInfo(new ClassInfo(), className, key, classFormat);
	    } else {
		classInfo = new ClassInfo(data);
		DatabaseEntry formatData = new DatabaseEntry();
		ObjectStreamClass storedClassFormat = getClassFormat(classInfo.getClassID(), formatData);
		if (!areClassFormatsEqual(storedClassFormat, getBytes(formatData), classFormat)) {
		    classInfo = putClassInfo(classInfo, className, key, classFormat);
		}
		classInfo.setClassFormat(classFormat);
		classMap.put(className, classInfo);
	    }
	}
	return classInfo;
  }


  /**
   * 
   * Assign a new class ID (increment the current ID record), write the ObjectStreamClass record for this new ID, and update the ClassInfo record with the new ID also. The ClassInfo passed as an argument is the one to be updated.
   */
  // line 170 "../../../../StoredClassCatalog.ump"
   private ClassInfo putClassInfo(ClassInfo classInfo, String className, DatabaseEntry classKey, ObjectStreamClass classFormat) throws DatabaseException,ClassNotFoundException{
    CursorConfig cursorConfig = null;
	if (cdbMode) {
	    cursorConfig = new CursorConfig();
	    DbCompat.setWriteCursor(cursorConfig, true);
	}
	Cursor cursor = null;
	try {
	    cursor = db.openCursor(null, cursorConfig);
	    DatabaseEntry key = new DatabaseEntry(LAST_CLASS_ID_KEY);
	    DatabaseEntry data = new DatabaseEntry();
	    OperationStatus status = cursor.getSearchKey(key, data, writeLockMode);
	    if (status != OperationStatus.SUCCESS) {
		throw new IllegalStateException("Class ID not initialized");
	    }
	    byte[] idBytes = getBytes(data);
	    idBytes = incrementID(idBytes);
	    data.setData(idBytes);
	    cursor.put(key, data);
	    byte[] keyBytes = new byte[1 + idBytes.length];
	    keyBytes[0] = REC_CLASS_FORMAT;
	    System.arraycopy(idBytes, 0, keyBytes, 1, idBytes.length);
	    key.setData(keyBytes);
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    ObjectOutputStream oos;
	    try {
		oos = new ObjectOutputStream(baos);
		oos.writeObject(classFormat);
	    } catch (IOException e) {
		throw new RuntimeExceptionWrapper(e);
	    }
	    data.setData(baos.toByteArray());
	    cursor.put(key, data);
	    classInfo.setClassID(idBytes);
	    classInfo.toDbt(data);
	    cursor.put(classKey, data);
	    classInfo.setClassFormat(classFormat);
	    classMap.put(className, classInfo);
	    formatMap.put(new BigInteger(idBytes), classFormat);
	    return classInfo;
	} finally {
	    if (cursor != null) {
		cursor.close();
	    }
	    hook_commitTransaction();
	}
  }

  // line 218 "../../../../StoredClassCatalog.ump"
   private void hook_commitTransaction() throws DatabaseException{
    
  }

  // line 221 "../../../../StoredClassCatalog.ump"
   private static  byte[] incrementID(byte [] key){
    BigInteger id = new BigInteger(key);
	id = id.add(BigInteger.valueOf(1));
	return id.toByteArray();
  }


  /**
   * 
   * Return whether two class formats are equal. This determines whether a new class format is needed for an object being serialized. Formats must be identical in all respects, or a new format is needed.
   */
  // line 231 "../../../../StoredClassCatalog.ump"
   private static  boolean areClassFormatsEqual(ObjectStreamClass format1, byte [] format1Bytes, ObjectStreamClass format2){
    try {
	    if (format1Bytes == null) {
		format1Bytes = getObjectBytes(format1);
	    }
	    byte[] format2Bytes = getObjectBytes(format2);
	    return java.util.Arrays.equals(format2Bytes, format1Bytes);
	} catch (IOException e) {
	    return false;
	}
  }

  // line 243 "../../../../StoredClassCatalog.ump"
   private static  byte[] getBytes(DatabaseEntry dbt){
    byte[] b = dbt.getData();
	if (b == null) {
	    return null;
	}
	if (dbt.getOffset() == 0 && b.length == dbt.getSize()) {
	    return b;
	}
	int len = dbt.getSize();
	if (len == 0) {
	    return ZERO_LENGTH_BYTE_ARRAY;
	} else {
	    byte[] t = new byte[len];
	    System.arraycopy(b, dbt.getOffset(), t, 0, t.length);
	    return t;
	}
  }

  // line 261 "../../../../StoredClassCatalog.ump"
   private static  byte[] getObjectBytes(Object o) throws IOException{
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	ObjectOutputStream oos = new ObjectOutputStream(baos);
	oos.writeObject(o);
	return baos.toByteArray();
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  
  
  // line 6 "../../../../StoredClassCatalog_static.ump"
  public static class ClassInfo implements Serializable
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public ClassInfo()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 12 "../../../../StoredClassCatalog_static.ump"
    public  ClassInfo(){
      
    }
  
    // line 14 "../../../../StoredClassCatalog_static.ump"
    public  ClassInfo(DatabaseEntry dbt){
      byte[] data=dbt.getData();
          int len=data[0];
          classID=new byte[len];
          System.arraycopy(data,1,classID,0,len);
    }
  
    // line 20 "../../../../StoredClassCatalog_static.ump"
    public void toDbt(DatabaseEntry dbt){
      byte[] data=new byte[1 + classID.length];
          data[0]=(byte)classID.length;
          System.arraycopy(classID,0,data,1,classID.length);
          dbt.setData(data);
    }
  
    // line 26 "../../../../StoredClassCatalog_static.ump"
    public void setClassID(byte [] classID){
      this.classID=classID;
    }
  
    // line 29 "../../../../StoredClassCatalog_static.ump"
    public byte[] getClassID(){
      return classID;
    }
  
    // line 32 "../../../../StoredClassCatalog_static.ump"
    public ObjectStreamClass getClassFormat(){
      return classFormat;
    }
  
    // line 35 "../../../../StoredClassCatalog_static.ump"
    public void setClassFormat(ObjectStreamClass classFormat){
      this.classFormat=classFormat;
    }
    
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------
    
    // line 9 "../../../../StoredClassCatalog_static.ump"
    private byte[] classID ;
  // line 10 "../../../../StoredClassCatalog_static.ump"
    private transient ObjectStreamClass classFormat ;
  
    
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 28 "../../../../StoredClassCatalog.ump"
  private static final byte REC_LAST_CLASS_ID = (byte) 0 ;
// line 30 "../../../../StoredClassCatalog.ump"
  private static final byte REC_CLASS_FORMAT = (byte) 1 ;
// line 32 "../../../../StoredClassCatalog.ump"
  private static final byte REC_CLASS_INFO = (byte) 2 ;
// line 34 "../../../../StoredClassCatalog.ump"
  private static final byte[] LAST_CLASS_ID_KEY = {REC_LAST_CLASS_ID};
// line 36 "../../../../StoredClassCatalog.ump"
  private Database db ;
// line 38 "../../../../StoredClassCatalog.ump"
  private HashMap classMap ;
// line 40 "../../../../StoredClassCatalog.ump"
  private HashMap formatMap ;
// line 42 "../../../../StoredClassCatalog.ump"
  private LockMode writeLockMode ;
// line 44 "../../../../StoredClassCatalog.ump"
  private boolean cdbMode ;
// line 46 "../../../../StoredClassCatalog.ump"
  private static byte[] ZERO_LENGTH_BYTE_ARRAY = new byte[0] ;

  
}