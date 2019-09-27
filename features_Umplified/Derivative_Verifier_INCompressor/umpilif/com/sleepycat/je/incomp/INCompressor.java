/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.incomp;

// line 3 "../../../../INCompressor.ump"
public class INCompressor
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public INCompressor()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 27 "../../../../INCompressor.ump"
   protected void hook390(BIN bin) throws DatabaseException{
    
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 5 "../../../../INCompressor.ump"
  public synchronized void verifyCursors () throws DatabaseException 
  {
    if (env.isClosed()) {
	    return;
	}
	List queueSnapshot = null;
	synchronized (binRefQueueSync) {
	    queueSnapshot = new ArrayList(binRefQueue.values());
	}
	Map dbCache = new HashMap();
	Iterator it = queueSnapshot.iterator();
	while (it.hasNext()) {
	    BINReference binRef = (BINReference) it.next();
	    DatabaseImpl db = env.getDbMapTree().getDb(binRef.getDatabaseId(), lockTimeout, dbCache);
	    BIN bin = searchForBIN(db, binRef);
	    if (bin != null) {
		bin.verifyCursors();
		this.hook390(bin);
	    }
	}
  }

  
}