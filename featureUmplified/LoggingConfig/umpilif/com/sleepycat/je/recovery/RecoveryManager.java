/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.recovery;

// line 3 "../../../../RecoveryManager.ump"
public class RecoveryManager
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public RecoveryManager()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 6 "../../../../RecoveryManager.ump"
   protected void hook558() throws DatabaseException,IOException{
    Tracer.trace(Level.CONFIG, env, "Recovery checkpoint search, " + info);
	original();
  }

  // line 11 "../../../../RecoveryManager.ump"
   protected void hook559() throws DatabaseException,IOException{
    Tracer.trace(Level.CONFIG, env, "Recovery underway, found end of log");
	original();
  }

  // line 16 "../../../../RecoveryManager.ump"
   protected void hook560() throws DatabaseException,IOException{
    Tracer.trace(Level.CONFIG, env, "Recovery w/no files.");
	original();
  }

  // line 21 "../../../../RecoveryManager.ump"
   protected void hook561(long start, long end) throws IOException,DatabaseException{
    Tracer.trace(Level.CONFIG, env, passEndHeader(10, start, end) + info.toString());
	original(start, end);
  }

  // line 26 "../../../../RecoveryManager.ump"
   protected void hook562(long start, long end) throws IOException,DatabaseException{
    Tracer.trace(Level.CONFIG, env, passEndHeader(9, start, end) + info.toString());
	Tracer.trace(Level.CONFIG, env, passStartHeader(10) + "redo LNs");
	original(start, end);
  }

  // line 32 "../../../../RecoveryManager.ump"
   protected void hook563() throws IOException,DatabaseException{
    Tracer.trace(Level.CONFIG, env, passStartHeader(9) + "undo LNs");
	original();
  }

  // line 37 "../../../../RecoveryManager.ump"
   protected void hook564(long start, long end) throws IOException,DatabaseException{
    Tracer.trace(Level.CONFIG, env, passEndHeader(8, start, end) + info.toString());
	original(start, end);
  }

  // line 42 "../../../../RecoveryManager.ump"
   protected void hook565(long start, long end) throws IOException,DatabaseException{
    Tracer.trace(Level.CONFIG, env, passEndHeader(7, start, end) + info.toString());
	Tracer.trace(Level.CONFIG, env, passStartHeader(8) + "read dup BINDeltas");
	original(start, end);
  }

  // line 48 "../../../../RecoveryManager.ump"
   protected void hook566(long start, long end) throws IOException,DatabaseException{
    Tracer.trace(Level.CONFIG, env, passEndHeader(6, start, end) + info.toString());
	Tracer.trace(Level.CONFIG, env, passStartHeader(7) + "read dup INs");
	original(start, end);
  }

  // line 54 "../../../../RecoveryManager.ump"
   protected void hook567(long start, long end) throws IOException,DatabaseException{
    Tracer.trace(Level.CONFIG, env, passEndHeader(5, start, end) + info.toString());
	Tracer.trace(Level.CONFIG, env, passStartHeader(6) + "read BINDeltas");
	original(start, end);
  }

  // line 60 "../../../../RecoveryManager.ump"
   protected void hook568(long start, long end) throws IOException,DatabaseException{
    Tracer.trace(Level.CONFIG, env, passEndHeader(4, start, end) + info.toString());
	Tracer.trace(Level.CONFIG, env, passStartHeader(5) + "read other INs");
	original(start, end);
  }

  // line 66 "../../../../RecoveryManager.ump"
   protected void hook569(long start, long end) throws IOException,DatabaseException{
    Tracer.trace(Level.CONFIG, env, passEndHeader(3, start, end) + info.toString());
	Tracer.trace(Level.CONFIG, env, passStartHeader(4) + "redo map LNs");
	original(start, end);
  }

  // line 72 "../../../../RecoveryManager.ump"
   protected void hook570(long start, long end) throws IOException,DatabaseException{
    Tracer.trace(Level.CONFIG, env, passEndHeader(2, start, end) + info.toString());
	Tracer.trace(Level.CONFIG, env, passStartHeader(3) + "undo map LNs");
	original(start, end);
  }

  // line 78 "../../../../RecoveryManager.ump"
   protected void hook571(long start, long end) throws IOException,DatabaseException{
    Tracer.trace(Level.CONFIG, env, passEndHeader(1, start, end) + info.toString());
	Tracer.trace(Level.CONFIG, env, passStartHeader(2) + "read map BINDeltas");
	original(start, end);
  }

  // line 84 "../../../../RecoveryManager.ump"
   protected void hook572() throws IOException,DatabaseException{
    Tracer.trace(Level.CONFIG, env, passStartHeader(1) + "read map INs");
	original();
  }

}