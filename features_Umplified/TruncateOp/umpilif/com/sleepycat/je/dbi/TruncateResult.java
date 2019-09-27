/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;
import de.ovgu.cide.jakutil.*;

// line 3 "../../../../TruncateResult.ump"
public class TruncateResult
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TruncateResult()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 11 "../../../../TruncateResult.ump"
  public  TruncateResult(DatabaseImpl db, int count){
    this.db = db;
	this.count = count;
  }

  // line 16 "../../../../TruncateResult.ump"
   public DatabaseImpl getDatabase(){
    return db;
  }

  // line 20 "../../../../TruncateResult.ump"
   public int getRecordCount(){
    return count;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 6 "../../../../TruncateResult.ump"
  private DatabaseImpl db ;
// line 8 "../../../../TruncateResult.ump"
  private int count ;

  
}