/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.utilint;
import de.ovgu.cide.jakutil.*;
import java.lang.reflect.Method;

// line 3 "../../../../JarMain.ump"
public class JarMain
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public JarMain()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 12 "../../../../JarMain.ump"
   public static  void main(String [] args){
    Thread.currentThread().setUncaughtExceptionHandler(new com.sleepycat.je.util.DbRecover.UmpleExceptionHandler());
    Thread.setDefaultUncaughtExceptionHandler(new com.sleepycat.je.util.DbRecover.UmpleExceptionHandler());
    try {
	    if (args.length < 1) {
		usage("Missing utility name");
	    }
	    Class cls = Class.forName(PREFIX + args[0]);
	    Method mainMethod = cls.getMethod("main", new Class[] { String[].class });
	    String[] mainArgs = new String[args.length - 1];
	    System.arraycopy(args, 1, mainArgs, 0, mainArgs.length);
	    mainMethod.invoke(null, new Object[] { mainArgs });
	} catch (Throwable e) {
	    usage(e.toString());
	}
  }

  // line 27 "../../../../JarMain.ump"
   private static  void usage(String msg){
    System.err.println(msg);
	System.err.println(USAGE);
	System.exit(-1);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 7 "../../../../JarMain.ump"
  private static final String USAGE = "usage: java <utility> [options...]" ;
// line 9 "../../../../JarMain.ump"
  private static final String PREFIX = "com.sleepycat.je.util." ;

  
}