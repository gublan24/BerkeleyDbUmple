/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.utilint;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.dbi.EnvironmentImpl;
import com.sleepycat.je.config.EnvironmentParams;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.DatabaseException;
import java.io.File;

// line 3 "../../../../CmdUtil.ump"
// line 2 "../../../../loggingBase_CmdUtil.ump"
// line 3 "../../../../loggingConsoleHandler_CmdUtil.ump"
public class CmdUtil
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CmdUtil()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * private static final String printableChars = "!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ"
   * + "[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
   */
  // line 16 "../../../../CmdUtil.ump"
   public static  String getArg(String [] argv, int whichArg) throws IllegalArgumentException{
    if (whichArg < argv.length) {
	    return argv[whichArg];
	} else {
	    throw new IllegalArgumentException();
	}
  }


  /**
   * 
   * Parse a string into a long. If the string starts with 0x, this is a hex number, else it's decimal.
   */
  // line 27 "../../../../CmdUtil.ump"
   public static  long readLongNumber(String longVal){
    if (longVal.startsWith("0x")) {
	    return Long.parseLong(longVal.substring(2), 16);
	} else {
	    return Long.parseLong(longVal);
	}
  }

  // line 35 "../../../../CmdUtil.ump"
   public static  void formatEntry(StringBuffer sb, byte [] entryData, boolean formatUsingPrintable){
    for (int i = 0; i < entryData.length; i++) {
	    int b = entryData[i] & 0xff;
	    if (formatUsingPrintable) {
		if (isPrint(b)) {
		    if (b == 0134) {
			sb.append('\\');
		    }
		    sb.append(printableChars.charAt(b - 33));
		} else {
		    sb.append('\\');
		    String hex = Integer.toHexString(b);
		    if (b < 16) {
			sb.append('0');
		    }
		    sb.append(hex);
		}
	    } else {
		String hex = Integer.toHexString(b);
		if (b < 16) {
		    sb.append('0');
		}
		sb.append(hex);
	    }
	}
  }

  // line 62 "../../../../CmdUtil.ump"
   private static  boolean isPrint(int b){
    return (b < 0177) && (040 < b);
  }


  /**
   * 
   * Create an environment suitable for utilities. Utilities should in general send trace output to the console and not to the db log.
   */
  // line 69 "../../../../CmdUtil.ump"
   public static  EnvironmentImpl makeUtilityEnvironment(File envHome, boolean readOnly) throws DatabaseException{
    EnvironmentConfig config = new EnvironmentConfig();
	config.setReadOnly(readOnly);
	hook853(config);
	Label854:
config.setConfigParam(EnvironmentParams.JE_LOGGING_CONSOLE.getName(), "true");
//	original(config);

	Label855:
config.setConfigParam(EnvironmentParams.JE_LOGGING_LEVEL.getName(), "SEVERE");
	//original(config);

	config.setConfigParam(EnvironmentParams.ENV_RECOVERY.getName(), "false");
	EnvironmentImpl envImpl = new EnvironmentImpl(envHome, config);
	return envImpl;
  }


  /**
   * 
   * Returns a description of the java command for running a utility, without arguments.  For utilities the last name of the class name can be specified when "-jar je.jar" is used.
   */
  // line 83 "../../../../CmdUtil.ump"
   public static  String getJavaCommand(Class cls){
    String clsName = cls.getName();
	String lastName = clsName.substring(clsName.lastIndexOf('.') + 1);
	return "java { " + cls.getName() + " | -jar je.jar " + lastName + " }";
  }

  // line 89 "../../../../CmdUtil.ump"
   protected static  void hook853(EnvironmentConfig config) throws DatabaseException{
    
  }

  // line 92 "../../../../CmdUtil.ump"
   protected static  void hook854(EnvironmentConfig config) throws DatabaseException{
    
  }

  // line 95 "../../../../CmdUtil.ump"
   protected static  void hook855(EnvironmentConfig config) throws DatabaseException{
    
  }

}