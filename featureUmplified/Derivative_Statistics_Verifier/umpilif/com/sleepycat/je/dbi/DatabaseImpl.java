/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.dbi;

// line 3 "../../../../DatabaseImpl.ump"
public class DatabaseImpl
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DatabaseImpl()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 6 "../../../../DatabaseImpl.ump"
   public boolean verify(VerifyConfig config, DatabaseStats emptyStats) throws DatabaseException{
    if (tree == null) {
	    return true;
	}
	PrintStream out = config.getShowProgressStream();
	if (out == null) {
	    out = System.err;
	}
	StatsAccumulator statsAcc = new StatsAccumulator(out, config.getShowProgressInterval(), emptyStats) {
	    void verifyNode(Node node) {
		try {
		    node.verify(null);
		} catch (DatabaseException INE) {
		    progressStream.println(INE);
		}
	    }
	};
	boolean ok = walkDatabaseTree(statsAcc, out, config.getPrintInfo());
	statsAcc.copyToStats(emptyStats);
	return ok;
  }

}