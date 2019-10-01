/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.cleaner;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.tree.LN;
import com.sleepycat.je.dbi.MemoryBudget;
import com.sleepycat.je.dbi.DatabaseId;

// line 3 "../../../../LNInfo.ump"
public class LNInfo
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LNInfo()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 18 "../../../../LNInfo.ump"
   public  LNInfo(LN ln, DatabaseId dbId, byte [] key, byte [] dupKey){
    this.ln = ln;
	this.dbId = dbId;
	this.key = key;
	this.dupKey = dupKey;
  }

  // line 25 "../../../../LNInfo.ump"
  public LN getLN(){
    return ln;
  }

  // line 29 "../../../../LNInfo.ump"
  public DatabaseId getDbId(){
    return dbId;
  }

  // line 33 "../../../../LNInfo.ump"
  public byte[] getKey(){
    return key;
  }

  // line 37 "../../../../LNInfo.ump"
  public byte[] getDupKey(){
    return dupKey;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 9 "../../../../LNInfo.ump"
  private LN ln ;
// line 11 "../../../../LNInfo.ump"
  private DatabaseId dbId ;
// line 13 "../../../../LNInfo.ump"
  private byte[] key ;
// line 15 "../../../../LNInfo.ump"
  private byte[] dupKey ;

  
}