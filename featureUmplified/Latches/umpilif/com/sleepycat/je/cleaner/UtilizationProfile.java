/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.cleaner;

// line 3 "../../../../UtilizationProfile.ump"
// line 3 "../../../../UtilizationProfile_inner.ump"
public class UtilizationProfile
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public UtilizationProfile()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 6 "../../../../UtilizationProfile.ump"
   protected void hook178(CursorImpl cursor) throws DatabaseException{
    cursor.releaseBINs();
	original(cursor);
  }

  // line 11 "../../../../UtilizationProfile.ump"
   protected void hook179(CursorImpl cursor) throws DatabaseException{
    if (cursor != null) {
	    cursor.releaseBINs();
	    cursor.close();
	}
	original(cursor);
  }

  // line 19 "../../../../UtilizationProfile.ump"
   protected void hook180(long lsn, LNLogEntry entry, DatabaseImpl db, BIN bin) throws DatabaseException{
    try {
	    original(lsn, entry, db, bin);
	} finally {
	    if (bin != null) {
		bin.releaseLatch();
	    }
	}
  }
  /*PLEASE DO NOT EDIT THIS CODE*/
  /*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
  
  package com.sleepycat.je.cleaner;
  
  @MethodObject
  // line 4 "../../../../UtilizationProfile_inner.ump"
  public static class UtilizationProfile_populateCache
  {
  
    //------------------------
    // MEMBER VARIABLES
    //------------------------
  
    //------------------------
    // CONSTRUCTOR
    //------------------------
  
    public UtilizationProfile_populateCache()
    {}
  
    //------------------------
    // INTERFACE
    //------------------------
  
    public void delete()
    {}
  
    // line 6 "../../../../UtilizationProfile_inner.ump"
     protected void hook181() throws DatabaseException{
      cursor.releaseBIN();
          original();
    }
  
    // line 10 "../../../../UtilizationProfile_inner.ump"
     protected void hook182() throws DatabaseException{
      cursor.latchBIN();
          original();
    }
  
    // line 14 "../../../../UtilizationProfile_inner.ump"
     protected void hook183() throws DatabaseException{
      cursor.releaseBIN();
          original();
    }
  
    // line 18 "../../../../UtilizationProfile_inner.ump"
     protected void hook184() throws DatabaseException{
      cursor.latchBIN();
          original();
    }
  
    // line 22 "../../../../UtilizationProfile_inner.ump"
     protected void hook185() throws DatabaseException{
      cursor.releaseBINs();
          original();
    }
  
  }
}