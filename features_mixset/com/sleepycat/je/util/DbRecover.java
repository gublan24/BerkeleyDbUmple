/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.util;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.utilint.CmdUtil;
import com.sleepycat.je.dbi.EnvironmentImpl;
import java.io.File;

// line 3 "../../../../DbRecover.ump"
public class DbRecover
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DbRecover()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 10 "../../../../DbRecover.ump"
   public static  void main(String [] argv){
    Thread.currentThread().setUncaughtExceptionHandler(new UmpleExceptionHandler());
    Thread.setDefaultUncaughtExceptionHandler(new UmpleExceptionHandler());
    try {
	    int whichArg = 0;
	    boolean seenFile = false;
	    boolean seenOffset = false;
	    long truncateFileNum = -1;
	    long truncateOffset = -1;
	    File envHome = new File(".");
	    while (whichArg < argv.length) {
		String nextArg = argv[whichArg];
		if (nextArg.equals("-h")) {
		    whichArg++;
		    envHome = new File(CmdUtil.getArg(argv, whichArg));
		} else if (nextArg.equals("-f")) {
		    whichArg++;
		    truncateFileNum = CmdUtil.readLongNumber(CmdUtil.getArg(argv, whichArg));
		    seenFile = true;
		} else if (nextArg.equals("-o")) {
		    whichArg++;
		    truncateOffset = CmdUtil.readLongNumber(CmdUtil.getArg(argv, whichArg));
		    seenOffset = true;
		} else {
		    throw new IllegalArgumentException(nextArg + " is not a supported option.");
		}
		whichArg++;
	    }
	    if ((!seenFile) || (!seenOffset)) {
		usage();
		System.exit(1);
	    }
	    EnvironmentImpl env = CmdUtil.makeUtilityEnvironment(envHome, false);
	    env.getFileManager().truncateLog(truncateFileNum, truncateOffset);
	    env.close();
	} catch (Exception e) {
	    e.printStackTrace();
	    System.out.println(e.getMessage());
	    usage();
	    System.exit(1);
	}
  }

  // line 51 "../../../../DbRecover.ump"
   private static  void usage(){
    System.out.println("Usage: " + CmdUtil.getJavaCommand(DbRecover.class));
	System.out.println("                 -h <environment home>");
	System.out.println("(optional)");
	System.out.println("                 -f <file number, in hex>");
	System.out.println("                 -o <offset, in hex>");
	System.out.println("Log file is truncated at position starting at"
		+ " and inclusive of the offset. Beware, not " + " for general purpose use yet!");
  }

  public static class UmpleExceptionHandler implements Thread.UncaughtExceptionHandler
  {
    public void uncaughtException(Thread t, Throwable e)
    {
      translate(e);
      if(e.getCause()!=null)
      {
        translate(e.getCause());
      }
      e.printStackTrace();
    }
    public void translate(Throwable e)
    {
      java.util.List<StackTraceElement> result = new java.util.ArrayList<StackTraceElement>();
      StackTraceElement[] elements = e.getStackTrace();
      try
      {
        for(StackTraceElement element:elements)
        {
          String className = element.getClassName();
          String methodName = element.getMethodName();
          boolean methodFound = false;
          int index = className.lastIndexOf('.')+1;
          try {
            java.lang.reflect.Method query = this.getClass().getMethod(className.substring(index)+"_"+methodName,new Class[]{});
            UmpleSourceData sourceInformation = (UmpleSourceData)query.invoke(this,new Object[]{});
            for(int i=0;i<sourceInformation.size();++i)
            {
              // To compensate for any offsets caused by injected code we need to loop through the other references to this function
              //  and adjust the start / length of the function.
              int functionStart = sourceInformation.getJavaLine(i) + (("main".equals(methodName))?3:1);
              int functionEnd = functionStart + sourceInformation.getLength(i);
              int afterInjectionLines = 0;
              //  We can leverage the fact that all inject statements are added to the uncaught exception list 
              //   before the functions that they are within
              for (int j = 0; j < i; j++) {
                if (sourceInformation.getJavaLine(j) - 1 >= functionStart &&
                    sourceInformation.getJavaLine(j) - 1 <= functionEnd &&
                    sourceInformation.getJavaLine(j) - 1 <= element.getLineNumber()) {
                    // A before injection, +2 for the comments surrounding the injected code
                    if (sourceInformation.getJavaLine(j) - 1 == functionStart) {
                        functionStart += sourceInformation.getLength(j) + 2;
                        functionEnd += sourceInformation.getLength(j) + 2;
                    } else {
                        // An after injection
                        afterInjectionLines += sourceInformation.getLength(j) + 2;
                        functionEnd += sourceInformation.getLength(j) + 2;
                    }
                }
              }
              int distanceFromStart = element.getLineNumber() - functionStart - afterInjectionLines;
              if(distanceFromStart>=0&&distanceFromStart<=sourceInformation.getLength(i))
              {
                result.add(new StackTraceElement(element.getClassName(),element.getMethodName(),sourceInformation.getFileName(i),sourceInformation.getUmpleLine(i)+distanceFromStart));
                methodFound = true;
                break;
              }
            }
          }
          catch (Exception e2){}
          if(!methodFound)
          {
            result.add(element);
          }
        }
      }
      catch (Exception e1)
      {
        e1.printStackTrace();
      }
      e.setStackTrace(result.toArray(new StackTraceElement[0]));
    }
  //The following methods Map Java lines back to their original Umple file / line    
    public UmpleSourceData Tracer_getLogType(){ return new UmpleSourceData().setFileNames("LoggingDbLogHandler_Tracer.ump").setUmpleLines(9).setJavaLines(135).setLengths(1);}
    public UmpleSourceData Tracer_readFromLog(){ return new UmpleSourceData().setFileNames("LoggingDbLogHandler_Tracer.ump").setUmpleLines(51).setJavaLines(196).setLengths(2);}
    public UmpleSourceData Tracer_getLogSize(){ return new UmpleSourceData().setFileNames("LoggingDbLogHandler_Tracer.ump").setUmpleLines(36).setJavaLines(175).setLengths(1);}
    public UmpleSourceData Tracer_Tracer(){ return new UmpleSourceData().setFileNames("Tracer.ump","loggingBase_Tracer.ump").setUmpleLines(27, 8).setJavaLines(52, 120).setLengths(2, 1);}
    public UmpleSourceData Tracer_getMessage(){ return new UmpleSourceData().setFileNames("loggingBase_Tracer.ump").setUmpleLines(11).setJavaLines(125).setLengths(1);}
    public UmpleSourceData Tracer_parseLevel(){ return new UmpleSourceData().setFileNames("Tracer.ump").setUmpleLines(41).setJavaLines(76).setLengths(9);}
    public UmpleSourceData Tracer_getCurrentTimestamp(){ return new UmpleSourceData().setFileNames("Tracer.ump").setUmpleLines(56).setJavaLines(94).setLengths(2);}
    public UmpleSourceData Tracer_postLogWork(){ return new UmpleSourceData().setFileNames("LoggingDbLogHandler_Tracer.ump").setUmpleLines(30).setJavaLines(165).setLengths(1);}
    public UmpleSourceData Tracer_trace(){ return new UmpleSourceData().setFileNames("loggingBase_Tracer.ump","Tracer.ump","Derivative_LoggingSevere_LoggingBase_Tracer.ump","LoggingSevere_Tracer.ump").setUmpleLines(20, 35, 9, 9).setJavaLines(65, 63, 276, 274).setLengths(1, 1, 2, 1);}
    public UmpleSourceData Tracer_writeToLog(){ return new UmpleSourceData().setFileNames("LoggingDbLogHandler_Tracer.ump").setUmpleLines(43).setJavaLines(185).setLengths(2);}
    public UmpleSourceData Tracer_logEntryIsTransactional(){ return new UmpleSourceData().setFileNames("LoggingDbLogHandler_Tracer.ump").setUmpleLines(72).setJavaLines(223).setLengths(1);}
    public UmpleSourceData Tracer_getStackTrace(){ return new UmpleSourceData().setFileNames("Tracer.ump").setUmpleLines(64).setJavaLines(105).setLengths(6);}
    public UmpleSourceData Tracer_getTransactionId(){ return new UmpleSourceData().setFileNames("LoggingDbLogHandler_Tracer.ump").setUmpleLines(79).setJavaLines(233).setLengths(1);}
    public UmpleSourceData Tracer_hashCode(){ return new UmpleSourceData().setFileNames("LoggingDbLogHandler_Tracer.ump").setUmpleLines(90).setJavaLines(248).setLengths(1);}
    public UmpleSourceData Tracer_countAsObsoleteWhenLogged(){ return new UmpleSourceData().setFileNames("LoggingDbLogHandler_Tracer.ump").setUmpleLines(23).setJavaLines(155).setLengths(1);}
    public UmpleSourceData Tracer_equals(){ return new UmpleSourceData().setFileNames("LoggingDbLogHandler_Tracer.ump").setUmpleLines(97).setJavaLines(258).setLengths(7);}
    public UmpleSourceData Tracer_dumpLog(){ return new UmpleSourceData().setFileNames("LoggingDbLogHandler_Tracer.ump").setUmpleLines(59).setJavaLines(207).setLengths(7);}
    public UmpleSourceData Tracer_toString(){ return new UmpleSourceData().setFileNames("LoggingDbLogHandler_Tracer.ump").setUmpleLines(83).setJavaLines(238).setLengths(1);}
    public UmpleSourceData Tracer_marshallOutsideWriteLatch(){ return new UmpleSourceData().setFileNames("LoggingDbLogHandler_Tracer.ump").setUmpleLines(16).setJavaLines(145).setLengths(1);}
    public UmpleSourceData FastInputStream_read(){ return new UmpleSourceData().setFileNames("FastInputStream.ump","FastInputStream.ump","FastInputStream.ump").setUmpleLines(63, 67, 71).setJavaLines(89, 94, 99).setLengths(1, 1, 1);}
    public UmpleSourceData FastInputStream_markSupported(){ return new UmpleSourceData().setFileNames("FastInputStream.ump").setUmpleLines(42).setJavaLines(64).setLengths(1);}
    public UmpleSourceData FastInputStream_available(){ return new UmpleSourceData().setFileNames("FastInputStream.ump").setUmpleLines(38).setJavaLines(59).setLengths(1);}
    public UmpleSourceData FastInputStream_reset(){ return new UmpleSourceData().setFileNames("FastInputStream.ump").setUmpleLines(50).setJavaLines(74).setLengths(1);}
    public UmpleSourceData FastInputStream_skip(){ return new UmpleSourceData().setFileNames("FastInputStream.ump").setUmpleLines(54).setJavaLines(79).setLengths(6);}
    public UmpleSourceData FastInputStream_FastInputStream(){ return new UmpleSourceData().setFileNames("FastInputStream.ump","FastInputStream.ump").setUmpleLines(21, 32).setJavaLines(38, 52).setLengths(2, 3);}
    public UmpleSourceData FastInputStream_mark(){ return new UmpleSourceData().setFileNames("FastInputStream.ump").setUmpleLines(46).setJavaLines(69).setLengths(1);}
    public UmpleSourceData UtfOps_getCharLength(){ return new UmpleSourceData().setFileNames("UtfOps.ump","UtfOps.ump").setUmpleLines(64, 77).setJavaLines(94, 109).setLengths(1, 27);}
    public UmpleSourceData UtfOps_getByteLength(){ return new UmpleSourceData().setFileNames("UtfOps.ump","UtfOps.ump").setUmpleLines(30, 41).setJavaLines(54, 68).setLengths(1, 13);}
    public UmpleSourceData UtfOps_charsToBytes(){ return new UmpleSourceData().setFileNames("UtfOps.ump").setUmpleLines(165).setJavaLines(202).setLengths(14);}
    public UmpleSourceData UtfOps_bytesToChars(){ return new UmpleSourceData().setFileNames("UtfOps.ump").setUmpleLines(119).setJavaLines(153).setLengths(35);}
    public UmpleSourceData UtfOps_stringToBytes(){ return new UmpleSourceData().setFileNames("UtfOps.ump").setUmpleLines(206).setJavaLines(248).setLengths(6);}
    public UmpleSourceData UtfOps_getZeroTerminatedByteLength(){ return new UmpleSourceData().setFileNames("UtfOps.ump").setUmpleLines(17).setJavaLines(38).setLengths(5);}
    public UmpleSourceData UtfOps_bytesToString(){ return new UmpleSourceData().setFileNames("UtfOps.ump").setUmpleLines(192).setJavaLines(231).setLengths(6);}
    public UmpleSourceData IOExceptionWrapper_IOExceptionWrapper(){ return new UmpleSourceData().setFileNames("IOExceptionWrapper.ump").setUmpleLines(11).setJavaLines(31).setLengths(2);}
    public UmpleSourceData IOExceptionWrapper_getDetail(){ return new UmpleSourceData().setFileNames("IOExceptionWrapper.ump").setUmpleLines(19).setJavaLines(42).setLengths(1);}
    public UmpleSourceData IOExceptionWrapper_getCause(){ return new UmpleSourceData().setFileNames("IOExceptionWrapper.ump").setUmpleLines(23).setJavaLines(47).setLengths(1);}
    public UmpleSourceData FastOutputStream_initBuffer(){ return new UmpleSourceData().setFileNames("FastOutputStream.ump").setUmpleLines(71).setJavaLines(91).setLengths(2);}
    public UmpleSourceData FastOutputStream_addSize(){ return new UmpleSourceData().setFileNames("FastOutputStream.ump").setUmpleLines(190).setJavaLines(200).setLengths(1);}
    public UmpleSourceData FastOutputStream_getBufferLength(){ return new UmpleSourceData().setFileNames("FastOutputStream.ump").setUmpleLines(172).setJavaLines(176).setLengths(1);}
    public UmpleSourceData FastOutputStream_FastOutputStream(){ return new UmpleSourceData().setFileNames("FastOutputStream.ump","FastOutputStream.ump","FastOutputStream.ump","FastOutputStream.ump","FastOutputStream.ump").setUmpleLines(31, 39, 48, 56, 66).setJavaLines(38, 49, 61, 72, 85).setLengths(1, 1, 1, 2, 2);}
    public UmpleSourceData FastOutputStream_writeTo(){ return new UmpleSourceData().setFileNames("FastOutputStream.ump").setUmpleLines(96).setJavaLines(122).setLengths(1);}
    public UmpleSourceData FastOutputStream_getBufferOffset(){ return new UmpleSourceData().setFileNames("FastOutputStream.ump").setUmpleLines(164).setJavaLines(165).setLengths(1);}
    public UmpleSourceData FastOutputStream_size(){ return new UmpleSourceData().setFileNames("FastOutputStream.ump").setUmpleLines(76).setJavaLines(97).setLengths(1);}
    public UmpleSourceData FastOutputStream_toByteArray(){ return new UmpleSourceData().setFileNames("FastOutputStream.ump").setUmpleLines(108).setJavaLines(137).setLengths(7);}
    public UmpleSourceData FastOutputStream_makeSpace(){ return new UmpleSourceData().setFileNames("FastOutputStream.ump").setUmpleLines(180).setJavaLines(187).setLengths(3);}
    public UmpleSourceData FastOutputStream_bump(){ return new UmpleSourceData().setFileNames("FastOutputStream.ump").setUmpleLines(194).setJavaLines(205).setLengths(4);}
    public UmpleSourceData FastOutputStream_reset(){ return new UmpleSourceData().setFileNames("FastOutputStream.ump").setUmpleLines(80).setJavaLines(102).setLengths(1);}
    public UmpleSourceData FastOutputStream_toString(){ return new UmpleSourceData().setFileNames("FastOutputStream.ump","FastOutputStream.ump").setUmpleLines(100, 104).setJavaLines(127, 132).setLengths(1, 1);}
    public UmpleSourceData FastOutputStream_write(){ return new UmpleSourceData().setFileNames("FastOutputStream.ump","FastOutputStream.ump","FastOutputStream.ump").setUmpleLines(84, 88, 92).setJavaLines(107, 112, 117).setLengths(1, 1, 1);}
    public UmpleSourceData FastOutputStream_getBufferBytes(){ return new UmpleSourceData().setFileNames("FastOutputStream.ump").setUmpleLines(156).setJavaLines(154).setLengths(1);}
    public UmpleSourceData RuntimeExceptionWrapper_RuntimeExceptionWrapper(){ return new UmpleSourceData().setFileNames("RuntimeExceptionWrapper.ump").setUmpleLines(10).setJavaLines(30).setLengths(2);}
    public UmpleSourceData RuntimeExceptionWrapper_getDetail(){ return new UmpleSourceData().setFileNames("RuntimeExceptionWrapper.ump").setUmpleLines(18).setJavaLines(41).setLengths(1);}
    public UmpleSourceData RuntimeExceptionWrapper_getCause(){ return new UmpleSourceData().setFileNames("RuntimeExceptionWrapper.ump").setUmpleLines(22).setJavaLines(46).setLengths(1);}
    public UmpleSourceData ExceptionUnwrapper_unwrapAny(){ return new UmpleSourceData().setFileNames("ExceptionUnwrapper.ump").setUmpleLines(29).setJavaLines(57).setLengths(12);}
    public UmpleSourceData ExceptionUnwrapper_unwrap(){ return new UmpleSourceData().setFileNames("ExceptionUnwrapper.ump").setUmpleLines(13).setJavaLines(38).setLengths(8);}
    public UmpleSourceData BooleanConfigParam_BooleanConfigParam(){ return new UmpleSourceData().setFileNames("BooleanConfigParam.ump").setUmpleLines(14).setJavaLines(40).setLengths(1);}
    public UmpleSourceData BooleanConfigParam_validateValue(){ return new UmpleSourceData().setFileNames("BooleanConfigParam.ump").setUmpleLines(21).setJavaLines(50).setLengths(4);}
    public UmpleSourceData ShortConfigParam_ShortConfigParam(){ return new UmpleSourceData().setFileNames("ShortConfigParam.ump").setUmpleLines(14).setJavaLines(33).setLengths(3);}
    public UmpleSourceData ShortConfigParam_getExtraDescription(){ return new UmpleSourceData().setFileNames("ShortConfigParam.ump").setUmpleLines(45).setJavaLines(67).setLengths(11);}
    public UmpleSourceData ShortConfigParam_validateValue(){ return new UmpleSourceData().setFileNames("ShortConfigParam.ump").setUmpleLines(37).setJavaLines(58).setLengths(5);}
    public UmpleSourceData ShortConfigParam_validate(){ return new UmpleSourceData().setFileNames("ShortConfigParam.ump").setUmpleLines(20).setJavaLines(40).setLengths(14);}
    public UmpleSourceData LongConfigParam_LongConfigParam(){ return new UmpleSourceData().setFileNames("LongConfigParam.ump").setUmpleLines(14).setJavaLines(33).setLengths(3);}
    public UmpleSourceData LongConfigParam_getExtraDescription(){ return new UmpleSourceData().setFileNames("LongConfigParam.ump").setUmpleLines(45).setJavaLines(67).setLengths(11);}
    public UmpleSourceData LongConfigParam_validateValue(){ return new UmpleSourceData().setFileNames("LongConfigParam.ump").setUmpleLines(37).setJavaLines(58).setLengths(5);}
    public UmpleSourceData LongConfigParam_validate(){ return new UmpleSourceData().setFileNames("LongConfigParam.ump").setUmpleLines(20).setJavaLines(40).setLengths(14);}
    public UmpleSourceData IntConfigParam_getExtraDescription(){ return new UmpleSourceData().setFileNames("IntConfigParam.ump").setUmpleLines(45).setJavaLines(67).setLengths(11);}
    public UmpleSourceData IntConfigParam_validateValue(){ return new UmpleSourceData().setFileNames("IntConfigParam.ump").setUmpleLines(37).setJavaLines(58).setLengths(5);}
    public UmpleSourceData IntConfigParam_IntConfigParam(){ return new UmpleSourceData().setFileNames("IntConfigParam.ump").setUmpleLines(14).setJavaLines(33).setLengths(3);}
    public UmpleSourceData IntConfigParam_validate(){ return new UmpleSourceData().setFileNames("IntConfigParam.ump").setUmpleLines(20).setJavaLines(40).setLengths(14);}
    public UmpleSourceData ConfigParam_ConfigParam(){ return new UmpleSourceData().setFileNames("ConfigParam.ump").setUmpleLines(18).setJavaLines(57).setLengths(7);}
    public UmpleSourceData ConfigParam_isMutable(){ return new UmpleSourceData().setFileNames("ConfigParam.ump").setUmpleLines(44).setJavaLines(83).setLengths(1);}
    public UmpleSourceData ConfigParam_getExtraDescription(){ return new UmpleSourceData().setFileNames("ConfigParam.ump").setUmpleLines(36).setJavaLines(73).setLengths(1);}
    public UmpleSourceData ConfigParam_validateValue(){ return new UmpleSourceData().setFileNames("ConfigParam.ump").setUmpleLines(62).setJavaLines(106).setLengths(1);}
    public UmpleSourceData ConfigParam_getDefault(){ return new UmpleSourceData().setFileNames("ConfigParam.ump").setUmpleLines(40).setJavaLines(78).setLengths(1);}
    public UmpleSourceData ConfigParam_toString(){ return new UmpleSourceData().setFileNames("ConfigParam.ump").setUmpleLines(65).setJavaLines(111).setLengths(1);}
    public UmpleSourceData ConfigParam_getDescription(){ return new UmpleSourceData().setFileNames("ConfigParam.ump").setUmpleLines(32).setJavaLines(68).setLengths(1);}
    public UmpleSourceData ConfigParam_validateName(){ return new UmpleSourceData().setFileNames("ConfigParam.ump").setUmpleLines(56).setJavaLines(99).setLengths(3);}
    public UmpleSourceData ConfigParam_validate(){ return new UmpleSourceData().setFileNames("ConfigParam.ump").setUmpleLines(51).setJavaLines(93).setLengths(2);}
    public UmpleSourceData ForeignKeyTrigger_triggerAdded(){ return new UmpleSourceData().setFileNames("ForeignKeyTrigger.ump").setUmpleLines(14).setJavaLines(35).setLengths(1);}
    public UmpleSourceData ForeignKeyTrigger_databaseUpdated(){ return new UmpleSourceData().setFileNames("ForeignKeyTrigger.ump").setUmpleLines(22).setJavaLines(45).setLengths(3);}
    public UmpleSourceData ForeignKeyTrigger_ForeignKeyTrigger(){ return new UmpleSourceData().setFileNames("ForeignKeyTrigger.ump").setUmpleLines(10).setJavaLines(30).setLengths(1);}
    public UmpleSourceData ForeignKeyTrigger_triggerRemoved(){ return new UmpleSourceData().setFileNames("ForeignKeyTrigger.ump").setUmpleLines(17).setJavaLines(40).setLengths(1);}
    public UmpleSourceData OperationStatus_OperationStatus(){ return new UmpleSourceData().setFileNames("OperationStatus.ump").setUmpleLines(28).setJavaLines(29).setLengths(1);}
    public UmpleSourceData OperationStatus_toString(){ return new UmpleSourceData().setFileNames("OperationStatus.ump").setUmpleLines(32).setJavaLines(34).setLengths(1);}
    public UmpleSourceData Environment_checkpoint(){ return new UmpleSourceData().setFileNames("Environment.ump").setUmpleLines(385).setJavaLines(437).setLengths(4);}
    public UmpleSourceData Environment_addReferringHandle(){ return new UmpleSourceData().setFileNames("Environment.ump","Environment.ump").setUmpleLines(472, 479).setJavaLines(553, 563).setLengths(1, 1);}
    public UmpleSourceData Environment_removeDatabase(){ return new UmpleSourceData().setFileNames("DeleteOp_Environment.ump").setUmpleLines(8).setJavaLines(707).setLengths(14);}
    public UmpleSourceData Environment_getDatabaseNames(){ return new UmpleSourceData().setFileNames("Environment.ump").setUmpleLines(452).setJavaLines(526).setLengths(3);}
    public UmpleSourceData Environment_getStats(){ return new UmpleSourceData().setFileNames("Statistics_Environment.ump").setUmpleLines(8).setJavaLines(742).setLengths(6);}
    public UmpleSourceData Environment_validateDbConfigAgainstEnv(){ return new UmpleSourceData().setFileNames("Environment.ump").setUmpleLines(283).setJavaLines(321).setLengths(8);}
    public UmpleSourceData Environment_removeReferringHandle(){ return new UmpleSourceData().setFileNames("Environment.ump","Environment.ump").setUmpleLines(486, 493).setJavaLines(573, 583).setLengths(1, 1);}
    public UmpleSourceData Environment_getThreadTransaction(){ return new UmpleSourceData().setFileNames("Environment.ump").setUmpleLines(461).setJavaLines(538).setLengths(1);}
    public UmpleSourceData Environment_applyFileConfig(){ return new UmpleSourceData().setFileNames("Environment.ump").setUmpleLines(89).setJavaLines(115).setLengths(24);}
    public UmpleSourceData Environment_hook58(){ return new UmpleSourceData().setFileNames("Environment.ump").setUmpleLines(521).setJavaLines(619).setLengths(1);}
    public UmpleSourceData Environment_hook59(){ return new UmpleSourceData().setFileNames("Environment.ump").setUmpleLines(524).setJavaLines(624).setLengths(1);}
    public UmpleSourceData Environment_getTransactionStats(){ return new UmpleSourceData().setFileNames("Statistics_Environment.ump").setUmpleLines(30).setJavaLines(770).setLengths(4);}
    public UmpleSourceData Environment_getHome(){ return new UmpleSourceData().setFileNames("Environment.ump").setUmpleLines(297).setJavaLines(338).setLengths(2);}
    public UmpleSourceData Environment_getEnvironmentImpl(){ return new UmpleSourceData().setFileNames("Environment.ump").setUmpleLines(500).setJavaLines(593).setLengths(1);}
    public UmpleSourceData Environment_setMutableConfig(){ return new UmpleSourceData().setFileNames("Environment.ump").setUmpleLines(426).setJavaLines(490).setLengths(4);}
    public UmpleSourceData Environment_verify(){ return new UmpleSourceData().setFileNames("Derivative_Statistics_Verifier_Environment.ump").setUmpleLines(8).setJavaLines(783).setLengths(4);}
    public UmpleSourceData Environment_openDatabase(){ return new UmpleSourceData().setFileNames("Environment.ump").setUmpleLines(212).setJavaLines(243).setLengths(6);}
    public UmpleSourceData Environment_close(){ return new UmpleSourceData().setFileNames("Environment.ump").setUmpleLines(119).setJavaLines(148).setLengths(86);}
    public UmpleSourceData Environment_truncateDatabase(){ return new UmpleSourceData().setFileNames("Truncate_Environment.ump").setUmpleLines(8).setJavaLines(682).setLengths(16);}
    public UmpleSourceData Environment_checkEnv(){ return new UmpleSourceData().setFileNames("Environment.ump").setUmpleLines(513).setJavaLines(610).setLengths(5);}
    public UmpleSourceData Environment_evictMemory(){ return new UmpleSourceData().setFileNames("Evictor_Environment.ump").setUmpleLines(8).setJavaLines(646).setLengths(3);}
    public UmpleSourceData Environment_cleanLog(){ return new UmpleSourceData().setFileNames("Environment.ump").setUmpleLines(406).setJavaLines(464).setLengths(3);}
    public UmpleSourceData Environment_renameDatabase(){ return new UmpleSourceData().setFileNames("RenameOp_Environment.ump").setUmpleLines(8).setJavaLines(658).setLengths(15);}
    public UmpleSourceData Environment_upgrade(){ return new UmpleSourceData().setFileNames("Environment.ump").setUmpleLines(446).setJavaLines(516).setLengths(1);}
    public UmpleSourceData Environment_compress(){ return new UmpleSourceData().setFileNames("INCompressor_Environment.ump").setUmpleLines(8).setJavaLines(730).setLengths(3);}
    public UmpleSourceData Environment_getMutableConfig(){ return new UmpleSourceData().setFileNames("Environment.ump").setUmpleLines(436).setJavaLines(503).setLengths(4);}
    public UmpleSourceData Environment_getLockStats(){ return new UmpleSourceData().setFileNames("Statistics_Environment.ump").setUmpleLines(20).setJavaLines(757).setLengths(4);}
    public UmpleSourceData Environment_checkHandleIsValid(){ return new UmpleSourceData().setFileNames("Environment.ump").setUmpleLines(504).setJavaLines(598).setLengths(3);}
    public UmpleSourceData Environment_openDb(){ return new UmpleSourceData().setFileNames("Environment.ump").setUmpleLines(235).setJavaLines(268).setLengths(49);}
    public UmpleSourceData Environment_sync(){ return new UmpleSourceData().setFileNames("Environment.ump").setUmpleLines(395).setJavaLines(450).setLengths(5);}
    public UmpleSourceData Environment_getConfig(){ return new UmpleSourceData().setFileNames("Environment.ump").setUmpleLines(415).setJavaLines(476).setLengths(5);}
    public UmpleSourceData Environment_getDefaultTxnConfig(){ return new UmpleSourceData().setFileNames("Environment.ump").setUmpleLines(305).setJavaLines(349).setLengths(1);}
    public UmpleSourceData Environment_openSecondaryDatabase(){ return new UmpleSourceData().setFileNames("Environment.ump").setUmpleLines(225).setJavaLines(258).setLengths(6);}
    public UmpleSourceData Environment_getMemoryUsage(){ return new UmpleSourceData().setFileNames("MemoryBudget_Environment.ump").setUmpleLines(8).setJavaLines(634).setLengths(3);}
    public UmpleSourceData Environment_copyToHandleConfig(){ return new UmpleSourceData().setFileNames("Environment.ump").setUmpleLines(313).setJavaLines(359).setLengths(14);}
    public UmpleSourceData Environment_beginTransaction(){ return new UmpleSourceData().setFileNames("Environment.ump").setUmpleLines(333).setJavaLines(382).setLengths(46);}
    public UmpleSourceData Environment_Environment(){ return new UmpleSourceData().setFileNames("Environment.ump","Environment.ump").setUmpleLines(42, 69).setJavaLines(62, 92).setLengths(21, 14);}
    public UmpleSourceData Environment_setThreadTransaction(){ return new UmpleSourceData().setFileNames("Environment.ump").setUmpleLines(468).setJavaLines(548).setLengths(1);}
    public UmpleSourceData JoinCursor_getSortedCursors(){ return new UmpleSourceData().setFileNames("JoinCursor.ump").setUmpleLines(113).setJavaLines(89).setLengths(1);}
    public UmpleSourceData JoinCursor_getDatabase(){ return new UmpleSourceData().setFileNames("JoinCursor.ump").setUmpleLines(120).setJavaLines(99).setLengths(1);}
    public UmpleSourceData JoinCursor_retrieveNext(){ return new UmpleSourceData().setFileNames("JoinCursor.ump").setUmpleLines(156).setJavaLines(152).setLengths(38);}
    public UmpleSourceData JoinCursor_getNext(){ return new UmpleSourceData().setFileNames("JoinCursor.ump","JoinCursor.ump").setUmpleLines(134, 144).setJavaLines(119, 135).setLengths(7, 8);}
    public UmpleSourceData JoinCursor_close(){ return new UmpleSourceData().setFileNames("JoinCursor.ump","JoinCursor.ump").setUmpleLines(71, 82).setJavaLines(41, 55).setLengths(4, 25);}
    public UmpleSourceData JoinCursor_getConfig(){ return new UmpleSourceData().setFileNames("JoinCursor.ump").setUmpleLines(127).setJavaLines(109).setLengths(1);}
    public UmpleSourceData SecondaryTrigger_triggerAdded(){ return new UmpleSourceData().setFileNames("SecondaryTrigger.ump").setUmpleLines(18).setJavaLines(35).setLengths(1);}
    public UmpleSourceData SecondaryTrigger_databaseUpdated(){ return new UmpleSourceData().setFileNames("SecondaryTrigger.ump").setUmpleLines(26).setJavaLines(45).setLengths(1);}
    public UmpleSourceData SecondaryTrigger_SecondaryTrigger(){ return new UmpleSourceData().setFileNames("SecondaryTrigger.ump").setUmpleLines(10).setJavaLines(30).setLengths(1);}
    public UmpleSourceData SecondaryTrigger_triggerRemoved(){ return new UmpleSourceData().setFileNames("SecondaryTrigger.ump").setUmpleLines(21).setJavaLines(40).setLengths(1);}
    public UmpleSourceData DatabaseException_DatabaseException(){ return new UmpleSourceData().setFileNames("DatabaseException.ump","DatabaseException.ump","DatabaseException.ump","DatabaseException.ump").setUmpleLines(7, 11, 15, 19).setJavaLines(30, 35, 40, 45).setLengths(1, 1, 1, 1);}
    public UmpleSourceData CursorConfig_setDirtyRead(){ return new UmpleSourceData().setFileNames("CursorConfig.ump").setUmpleLines(56).setJavaLines(66).setLengths(1);}
    public UmpleSourceData CursorConfig_getReadCommitted(){ return new UmpleSourceData().setFileNames("CursorConfig.ump").setUmpleLines(78).setJavaLines(97).setLengths(1);}
    public UmpleSourceData CursorConfig_getReadUncommitted(){ return new UmpleSourceData().setFileNames("CursorConfig.ump").setUmpleLines(48).setJavaLines(55).setLengths(1);}
    public UmpleSourceData CursorConfig_setReadCommitted(){ return new UmpleSourceData().setFileNames("CursorConfig.ump").setUmpleLines(71).setJavaLines(87).setLengths(1);}
    public UmpleSourceData CursorConfig_CursorConfig(){ return new UmpleSourceData().setFileNames("CursorConfig.ump").setUmpleLines(35).setJavaLines(35).setLengths(1);}
    public UmpleSourceData CursorConfig_cloneConfig(){ return new UmpleSourceData().setFileNames("CursorConfig.ump").setUmpleLines(85).setJavaLines(107).setLengths(5);}
    public UmpleSourceData CursorConfig_setReadUncommitted(){ return new UmpleSourceData().setFileNames("CursorConfig.ump").setUmpleLines(41).setJavaLines(45).setLengths(1);}
    public UmpleSourceData CursorConfig_getDirtyRead(){ return new UmpleSourceData().setFileNames("CursorConfig.ump").setUmpleLines(64).setJavaLines(77).setLengths(1);}
    public UmpleSourceData OffsetList_add(){ return new UmpleSourceData().setFileNames("OffsetList.ump","OffsetList_static.ump").setUmpleLines(23, 15).setJavaLines(42, 39).setLengths(7, 12);}
    public UmpleSourceData Segment_next(){ return new UmpleSourceData().setFileNames("OffsetList_static.ump").setUmpleLines(38).setJavaLines(70).setLengths(1);}
    public UmpleSourceData OffsetList_contains(){ return new UmpleSourceData().setFileNames("OffsetList.ump").setUmpleLines(83).setJavaLines(112).setLengths(8);}
    public UmpleSourceData OffsetList_OffsetList(){ return new UmpleSourceData().setFileNames("OffsetList.ump").setUmpleLines(15).setJavaLines(31).setLengths(2);}
    public UmpleSourceData OffsetList_size(){ return new UmpleSourceData().setFileNames("OffsetList.ump","OffsetList_static.ump").setUmpleLines(33, 50).setJavaLines(53, 90).setLengths(1, 1);}
    public UmpleSourceData OffsetList_merge(){ return new UmpleSourceData().setFileNames("OffsetList.ump").setUmpleLines(40).setJavaLines(63).setLengths(19);}
    public UmpleSourceData Segment_get(){ return new UmpleSourceData().setFileNames("OffsetList_static.ump").setUmpleLines(32).setJavaLines(60).setLengths(1);}
    public UmpleSourceData OffsetList_toArray(){ return new UmpleSourceData().setFileNames("OffsetList.ump").setUmpleLines(65).setJavaLines(91).setLengths(12);}
    public UmpleSourceData Segment_setNext(){ return new UmpleSourceData().setFileNames("OffsetList_static.ump").setUmpleLines(44).setJavaLines(80).setLengths(1);}
    public UmpleSourceData Segment_Segment(){ return new UmpleSourceData().setFileNames("OffsetList_static.ump").setUmpleLines(8).setJavaLines(28).setLengths(1);}
    public UmpleSourceData LNInfo_getDbId(){ return new UmpleSourceData().setFileNames("LNInfo.ump").setUmpleLines(28).setJavaLines(46).setLengths(1);}
    public UmpleSourceData LNInfo_getKey(){ return new UmpleSourceData().setFileNames("LNInfo.ump").setUmpleLines(32).setJavaLines(51).setLengths(1);}
    public UmpleSourceData LNInfo_getLN(){ return new UmpleSourceData().setFileNames("LNInfo.ump").setUmpleLines(24).setJavaLines(41).setLengths(1);}
    public UmpleSourceData LNInfo_getDupKey(){ return new UmpleSourceData().setFileNames("LNInfo.ump").setUmpleLines(36).setJavaLines(56).setLengths(1);}
    public UmpleSourceData LNInfo_getMemorySize(){ return new UmpleSourceData().setFileNames("MemoryBudget_LNInfo.ump").setUmpleLines(5).setJavaLines(61).setLengths(11);}
    public UmpleSourceData LNInfo_LNInfo(){ return new UmpleSourceData().setFileNames("LNInfo.ump").setUmpleLines(17).setJavaLines(33).setLengths(4);}
    public UmpleSourceData UtilizationTracker_countNewLogEntry(){ return new UmpleSourceData().setFileNames("UtilizationTracker.ump").setUmpleLines(80).setJavaLines(113).setLengths(14);}
    public UmpleSourceData UtilizationTracker_activateCleaner(){ return new UmpleSourceData().setFileNames("UtilizationTracker.ump").setUmpleLines(51).setJavaLines(75).setLengths(2);}
    public UmpleSourceData UtilizationTracker_addSummary(){ return new UmpleSourceData().setFileNames("UtilizationTracker.ump").setUmpleLines(130).setJavaLines(175).setLengths(2);}
    public UmpleSourceData UtilizationTracker_takeSnapshot(){ return new UmpleSourceData().setFileNames("UtilizationTracker.ump").setUmpleLines(177).setJavaLines(234).setLengths(3);}
    public UmpleSourceData UtilizationTracker_getEnvironment(){ return new UmpleSourceData().setFileNames("UtilizationTracker.ump").setUmpleLines(44).setJavaLines(65).setLengths(1);}
    public UmpleSourceData UtilizationTracker_countOneNode(){ return new UmpleSourceData().setFileNames("UtilizationTracker.ump").setUmpleLines(117).setJavaLines(159).setLengths(7);}
    public UmpleSourceData UtilizationTracker_countObsoleteNode(){ return new UmpleSourceData().setFileNames("UtilizationTracker.ump").setUmpleLines(100).setJavaLines(136).setLengths(3);}
    public UmpleSourceData UtilizationTracker_getFile(){ return new UmpleSourceData().setFileNames("UtilizationTracker.ump").setUmpleLines(147).setJavaLines(198).setLengths(14);}
    public UmpleSourceData UtilizationTracker_evictMemory_execute(){ return new UmpleSourceData().setFileNames("Evictor_UtilizationTracker_inner.ump").setUmpleLines(8).setJavaLines(35).setLengths(46);}
    public UmpleSourceData UtilizationTracker_resetFile(){ return new UmpleSourceData().setFileNames("UtilizationTracker.ump").setUmpleLines(167).setJavaLines(221).setLengths(4);}
    public UmpleSourceData UtilizationTracker_getTrackedFiles(){ return new UmpleSourceData().setFileNames("UtilizationTracker.ump").setUmpleLines(59).setJavaLines(86).setLengths(1);}
    public UmpleSourceData UtilizationTracker_getTrackedFile(){ return new UmpleSourceData().setFileNames("UtilizationTracker.ump").setUmpleLines(67).setJavaLines(97).setLengths(7);}
    public UmpleSourceData UtilizationTracker_countObsoleteNodeInexact(){ return new UmpleSourceData().setFileNames("UtilizationTracker.ump").setUmpleLines(109).setJavaLines(148).setLengths(2);}
    public UmpleSourceData UtilizationTracker_inArray(){ return new UmpleSourceData().setFileNames("UtilizationTracker.ump").setUmpleLines(186).setJavaLines(246).setLengths(6);}
    public UmpleSourceData UtilizationTracker_evictMemory_UtilizationTracker_evictMemory(){ return new UmpleSourceData().setFileNames("Evictor_UtilizationTracker_inner.ump").setUmpleLines(5).setJavaLines(30).setLengths(1);}
    public UmpleSourceData UtilizationTracker_UtilizationTracker(){ return new UmpleSourceData().setFileNames("UtilizationTracker.ump","UtilizationTracker.ump").setUmpleLines(28, 35).setJavaLines(45, 55).setLengths(1, 6);}
    public UmpleSourceData UtilizationTracker_getUnflushableTrackedSummary(){ return new UmpleSourceData().setFileNames("UtilizationTracker.ump").setUmpleLines(138).setJavaLines(186).setLengths(3);}
    public UmpleSourceData UtilizationTracker_evictMemory(){ return new UmpleSourceData().setFileNames("Evictor_UtilizationTracker.ump").setUmpleLines(8).setJavaLines(261).setLengths(1);}
    public UmpleSourceData FileSummary_add(){ return new UmpleSourceData().setFileNames("FileSummary.ump").setUmpleLines(114).setJavaLines(148).setLengths(8);}
    public UmpleSourceData FileSummary_readFromLog(){ return new UmpleSourceData().setFileNames("FileSummary.ump").setUmpleLines(149).setJavaLines(192).setLengths(11);}
    public UmpleSourceData FileSummary_getEntriesCounted(){ return new UmpleSourceData().setFileNames("FileSummary.ump").setUmpleLines(86).setJavaLines(111).setLengths(1);}
    public UmpleSourceData FileSummary_getLogSize(){ return new UmpleSourceData().setFileNames("FileSummary.ump").setUmpleLines(128).setJavaLines(165).setLengths(1);}
    public UmpleSourceData FileSummary_isEmpty(){ return new UmpleSourceData().setFileNames("FileSummary.ump").setUmpleLines(37).setJavaLines(50).setLengths(1);}
    public UmpleSourceData FileSummary_getObsoleteINSize(){ return new UmpleSourceData().setFileNames("FileSummary.ump").setUmpleLines(57).setJavaLines(76).setLengths(7);}
    public UmpleSourceData FileSummary_getObsoleteLNSize(){ return new UmpleSourceData().setFileNames("FileSummary.ump").setUmpleLines(44).setJavaLines(60).setLengths(7);}
    public UmpleSourceData FileSummary_writeToLog(){ return new UmpleSourceData().setFileNames("FileSummary.ump").setUmpleLines(135).setJavaLines(175).setLengths(8);}
    public UmpleSourceData FileSummary_logEntryIsTransactional(){ return new UmpleSourceData().setFileNames("FileSummary.ump").setUmpleLines(198).setJavaLines(250).setLengths(1);}
    public UmpleSourceData FileSummary_getTransactionId(){ return new UmpleSourceData().setFileNames("FileSummary.ump").setUmpleLines(190).setJavaLines(239).setLengths(1);}
    public UmpleSourceData FileSummary_FileSummary(){ return new UmpleSourceData().setFileNames("FileSummary.ump").setUmpleLines(31).setJavaLines(40).setLengths(1);}
    public UmpleSourceData FileSummary_reset(){ return new UmpleSourceData().setFileNames("FileSummary.ump").setUmpleLines(100).setJavaLines(131).setLengths(8);}
    public UmpleSourceData FileSummary_dumpLog(){ return new UmpleSourceData().setFileNames("FileSummary.ump").setUmpleLines(166).setJavaLines(212).setLengths(17);}
    public UmpleSourceData FileSummary_toString(){ return new UmpleSourceData().setFileNames("FileSummary.ump").setUmpleLines(202).setJavaLines(255).setLengths(3);}
    public UmpleSourceData FileSummary_getObsoleteSize(){ return new UmpleSourceData().setFileNames("FileSummary.ump").setUmpleLines(70).setJavaLines(92).setLengths(10);}
    public UmpleSourceData FileSummary_getNonObsoleteCount(){ return new UmpleSourceData().setFileNames("FileSummary.ump").setUmpleLines(93).setJavaLines(121).setLengths(1);}
    public UmpleSourceData FileSelector_selectFileForCleaning(){ return new UmpleSourceData().setFileNames("FileSelector.ump").setUmpleLines(55).setJavaLines(66).setLengths(30);}
    public UmpleSourceData FileSelector_removePendingDB(){ return new UmpleSourceData().setFileNames("DeleteOp_FileSelector.ump").setUmpleLines(32).setJavaLines(325).setLengths(2);}
    public UmpleSourceData FileSelector_getFilesAtCheckpointStart(){ return new UmpleSourceData().setFileNames("FileSelector.ump").setUmpleLines(139).setJavaLines(171).setLengths(9);}
    public UmpleSourceData FileSelector_addPendingLN(){ return new UmpleSourceData().setFileNames("FileSelector.ump").setUmpleLines(174).setJavaLines(215).setLengths(4);}
    public UmpleSourceData FileSelector_removePendingLN(){ return new UmpleSourceData().setFileNames("FileSelector.ump").setUmpleLines(197).setJavaLines(244).setLengths(2);}
    public UmpleSourceData FileSelector_FileSelector(){ return new UmpleSourceData().setFileNames("FileSelector.ump").setUmpleLines(34).setJavaLines(40).setLengths(13);}
    public UmpleSourceData FileSelector_getMustBeCleanedFiles(){ return new UmpleSourceData().setFileNames("FileSelector.ump").setUmpleLines(123).setJavaLines(149).setLengths(3);}
    public UmpleSourceData FileSelector_updateFilesAtCheckpointEnd(){ return new UmpleSourceData().setFileNames("FileSelector.ump").setUmpleLines(151).setJavaLines(189).setLengths(17);}
    public UmpleSourceData FileSelector_addPendingDB(){ return new UmpleSourceData().setFileNames("DeleteOp_FileSelector.ump").setUmpleLines(10).setJavaLines(297).setLengths(3);}
    public UmpleSourceData FileSelector_getPendingDBs(){ return new UmpleSourceData().setFileNames("DeleteOp_FileSelector.ump").setUmpleLines(19).setJavaLines(309).setLengths(7);}
    public UmpleSourceData FileSelector_putBackFileForCleaning(){ return new UmpleSourceData().setFileNames("FileSelector.ump").setUmpleLines(100).setJavaLines(117).setLengths(2);}
    public UmpleSourceData FileSelector_addCleanedFile(){ return new UmpleSourceData().setFileNames("FileSelector.ump").setUmpleLines(108).setJavaLines(128).setLengths(2);}
    public UmpleSourceData FileSelector_getPendingLNs(){ return new UmpleSourceData().setFileNames("FileSelector.ump").setUmpleLines(184).setJavaLines(228).setLengths(7);}
    public UmpleSourceData FileSelector_copySafeToDeleteFiles(){ return new UmpleSourceData().setFileNames("FileSelector.ump").setUmpleLines(205).setJavaLines(255).setLengths(5);}
    public UmpleSourceData FileSelector_removeDeletedFile(){ return new UmpleSourceData().setFileNames("FileSelector.ump").setUmpleLines(216).setJavaLines(269).setLengths(1);}
    public UmpleSourceData FileSelector_getLowUtilizationFiles(){ return new UmpleSourceData().setFileNames("FileSelector.ump").setUmpleLines(116).setJavaLines(139).setLengths(1);}
    public UmpleSourceData FileSelector_isFileCleaningInProgress(){ return new UmpleSourceData().setFileNames("FileSelector.ump").setUmpleLines(91).setJavaLines(105).setLengths(3);}
    public UmpleSourceData FileSelector_updateProcessedFiles(){ return new UmpleSourceData().setFileNames("FileSelector.ump").setUmpleLines(223).setJavaLines(279).setLengths(9);}
    public UmpleSourceData FileSelector_getBacklog(){ return new UmpleSourceData().setFileNames("FileSelector.ump").setUmpleLines(132).setJavaLines(161).setLengths(1);}
    public UmpleSourceData FileProcessor_processFile(){ return new UmpleSourceData().setFileNames("FileProcessor.ump").setUmpleLines(169).setJavaLines(238).setLengths(1);}
    public UmpleSourceData FileProcessor_FileProcessor(){ return new UmpleSourceData().setFileNames("FileProcessor.ump").setUmpleLines(59).setJavaLines(88).setLengths(5);}
    public UmpleSourceData FileProcessor_isRoot(){ return new UmpleSourceData().setFileNames("FileProcessor.ump").setUmpleLines(348).setJavaLines(467).setLengths(2);}
    public UmpleSourceData FileProcessor_doClean(){ return new UmpleSourceData().setFileNames("FileProcessor.ump").setUmpleLines(103).setJavaLines(144).setLengths(84);}
    public UmpleSourceData FileProcessor_accumulatePerRunCounters(){ return new UmpleSourceData().setFileNames("Statistics_FileProcessor.ump").setUmpleLines(53).setJavaLines(512).setLengths(12);}
    public UmpleSourceData FileProcessor_resetPerRunCounters(){ return new UmpleSourceData().setFileNames("Statistics_FileProcessor.ump").setUmpleLines(34).setJavaLines(490).setLengths(13);}
    public UmpleSourceData FileProcessor_processLN(){ return new UmpleSourceData().setFileNames("FileProcessor.ump").setUmpleLines(179).setJavaLines(250).setLengths(1);}
    public UmpleSourceData FileProcessor_addToQueue(){ return new UmpleSourceData().setFileNames("FileProcessor.ump").setUmpleLines(84).setJavaLines(120).setLengths(1);}
    public UmpleSourceData FileProcessor_processFile_hook162(){ return new UmpleSourceData().setFileNames("FileProcessor_static.ump").setUmpleLines(195).setJavaLines(271).setLengths(1);}
    public UmpleSourceData RootDoWork_doWork(){ return new UmpleSourceData().setFileNames("FileProcessor_static.ump").setUmpleLines(14).setJavaLines(36).setLengths(11);}
    public UmpleSourceData FileProcessor_processFile_hook161(){ return new UmpleSourceData().setFileNames("FileProcessor_static.ump").setUmpleLines(193).setJavaLines(266).setLengths(1);}
    public UmpleSourceData FileProcessor_findINInTree(){ return new UmpleSourceData().setFileNames("FileProcessor.ump").setUmpleLines(303).setJavaLines(415).setLengths(43);}
    public UmpleSourceData RootDoWork_RootDoWork(){ return new UmpleSourceData().setFileNames("FileProcessor_static.ump").setUmpleLines(9).setJavaLines(29).setLengths(3);}
    public UmpleSourceData FileProcessor_processFile_FileProcessor_processFile(){ return new UmpleSourceData().setFileNames("FileProcessor_static.ump").setUmpleLines(29).setJavaLines(37).setLengths(2);}
    public UmpleSourceData FileProcessor_processIN(){ return new UmpleSourceData().setFileNames("FileProcessor.ump").setUmpleLines(259).setJavaLines(347).setLengths(59);}
    public UmpleSourceData FileProcessor_processFoundLN(){ return new UmpleSourceData().setFileNames("FileProcessor.ump").setUmpleLines(193).setJavaLines(266).setLengths(72);}
    public UmpleSourceData FileProcessor_processFile_execute(){ return new UmpleSourceData().setFileNames("FileProcessor_static.ump","Statistics_FileProcessor_inner.ump","FileProcessor_static.ump").setUmpleLines(33, 7, 208).setJavaLines(43, 48, 46).setLengths(174, 6, 112);}
    public UmpleSourceData FileProcessor_onWakeup(){ return new UmpleSourceData().setFileNames("FileProcessor.ump").setUmpleLines(91).setJavaLines(130).setLengths(1);}
    public UmpleSourceData FileProcessor_processFile_hook156(){ return new UmpleSourceData().setFileNames("FileProcessor_static.ump").setUmpleLines(191).setJavaLines(261).setLengths(1);}
    public UmpleSourceData FileProcessor_nDeadlockRetries(){ return new UmpleSourceData().setFileNames("FileProcessor.ump").setUmpleLines(77).setJavaLines(110).setLengths(1);}
    public UmpleSourceData FileProcessor_processFile_hook155(){ return new UmpleSourceData().setFileNames("FileProcessor_static.ump").setUmpleLines(189).setJavaLines(256).setLengths(1);}
    public UmpleSourceData FileProcessor_processFile_hook154(){ return new UmpleSourceData().setFileNames("FileProcessor_static.ump").setUmpleLines(187).setJavaLines(251).setLengths(1);}
    public UmpleSourceData FileProcessor_processFile_hook130(){ return new UmpleSourceData().setFileNames("FileProcessor_static.ump").setUmpleLines(174).setJavaLines(233).setLengths(3);}
    public UmpleSourceData FileProcessor_processLN_FileProcessor_processLN(){ return new UmpleSourceData().setFileNames("FileProcessor_static.ump").setUmpleLines(199).setJavaLines(35).setLengths(7);}
    public UmpleSourceData FileProcessor_clearEnv(){ return new UmpleSourceData().setFileNames("FileProcessor.ump").setUmpleLines(67).setJavaLines(97).setLengths(4);}
    public UmpleSourceData FileProcessor_toString(){ return new UmpleSourceData().setFileNames("FileProcessor.ump").setUmpleLines(356).setJavaLines(478).setLengths(3);}
    public UmpleSourceData UtilizationProfile_countAndLogSummaries(){ return new UmpleSourceData().setFileNames("UtilizationProfile.ump").setUmpleLines(316).setJavaLines(411).setLengths(11);}
    public UmpleSourceData UtilizationProfile_getObsoleteDetail(){ return new UmpleSourceData().setFileNames("UtilizationProfile.ump").setUmpleLines(435).setJavaLines(556).setLengths(81);}
    public UmpleSourceData UtilizationProfile_getFileSummaryMap(){ return new UmpleSourceData().setFileNames("UtilizationProfile.ump").setUmpleLines(333).setJavaLines(431).setLengths(22);}
    public UmpleSourceData UtilizationProfile_clearCache(){ return new UmpleSourceData().setFileNames("UtilizationProfile.ump").setUmpleLines(361).setJavaLines(462).setLengths(1);}
    public UmpleSourceData UtilizationProfile_populateCache(){ return new UmpleSourceData().setFileNames("UtilizationProfile.ump").setUmpleLines(510).setJavaLines(646).setLengths(1);}
    public UmpleSourceData UtilizationProfile_getBestFileForCleaning(){ return new UmpleSourceData().setFileNames("UtilizationProfile.ump").setUmpleLines(145).setJavaLines(222).setLengths(49);}
    public UmpleSourceData UtilizationProfile_parseForceCleanFiles(){ return new UmpleSourceData().setFileNames("UtilizationProfile.ump").setUmpleLines(264).setJavaLines(356).setLengths(46);}
    public UmpleSourceData UtilizationProfile_insertFileSummary(){ return new UmpleSourceData().setFileNames("UtilizationProfile.ump").setUmpleLines(566).setJavaLines(710).setLengths(32);}
    public UmpleSourceData UtilizationProfile_hook189(){ return new UmpleSourceData().setFileNames("UtilizationProfile.ump").setUmpleLines(729).setJavaLines(904).setLengths(1);}
    public UmpleSourceData UtilizationProfile_removeFile_UtilizationProfile_removeFile(){ return new UmpleSourceData().setFileNames("UtilizationProfile_static.ump").setUmpleLines(17).setJavaLines(30).setLengths(2);}
    public UmpleSourceData UtilizationProfile_getCheapestFileToClean(){ return new UmpleSourceData().setFileNames("UtilizationProfile.ump").setUmpleLines(119).setJavaLines(194).setLengths(16);}
    public UmpleSourceData UtilizationProfile_removeFile(){ return new UmpleSourceData().setFileNames("UtilizationProfile.ump").setUmpleLines(368).setJavaLines(472).setLengths(1);}
    public UmpleSourceData UtilizationProfile_deleteFileSummary(){ return new UmpleSourceData().setFileNames("UtilizationProfile.ump").setUmpleLines(375).setJavaLines(482).setLengths(42);}
    public UmpleSourceData UtilizationProfile_openFileSummaryDatabase(){ return new UmpleSourceData().setFileNames("UtilizationProfile.ump").setUmpleLines(537).setJavaLines(678).setLengths(23);}
    public UmpleSourceData UtilizationProfile_flushFileSummary(){ return new UmpleSourceData().setFileNames("UtilizationProfile.ump").setUmpleLines(417).setJavaLines(533).setLengths(3);}
    public UmpleSourceData UtilizationProfile_putFileSummary_UtilizationProfile_putFileSummary(){ return new UmpleSourceData().setFileNames("UtilizationProfile_static.ump").setUmpleLines(38).setJavaLines(30).setLengths(2);}
    public UmpleSourceData UtilizationProfile_isForceCleanFile(){ return new UmpleSourceData().setFileNames("UtilizationProfile.ump").setUmpleLines(248).setJavaLines(337).setLengths(10);}
    public UmpleSourceData UtilizationProfile_getFileSummary(){ return new UmpleSourceData().setFileNames("UtilizationProfile.ump").setUmpleLines(225).setJavaLines(311).setLengths(17);}
    public UmpleSourceData UtilizationProfile_getFirstFSLN(){ return new UmpleSourceData().setFileNames("UtilizationProfile.ump").setUmpleLines(518).setJavaLines(656).setLengths(13);}
    public UmpleSourceData UtilizationProfile_populateCache_UtilizationProfile_populateCache(){ return new UmpleSourceData().setFileNames("UtilizationProfile_static.ump").setUmpleLines(90).setJavaLines(34).setLengths(1);}
    public UmpleSourceData UtilizationProfile_isRMWFixEnabled(){ return new UmpleSourceData().setFileNames("UtilizationProfile.ump").setUmpleLines(104).setJavaLines(173).setLengths(1);}
    public UmpleSourceData UtilizationProfile_verifyFileSummaryDatabase(){ return new UmpleSourceData().setFileNames("UtilizationProfile.ump").setUmpleLines(593).setJavaLines(752).setLengths(47);}
    public UmpleSourceData UtilizationProfile_envConfigUpdate(){ return new UmpleSourceData().setFileNames("UtilizationProfile.ump").setUmpleLines(94).setJavaLines(160).setLengths(3);}
    public UmpleSourceData UtilizationProfile_getNumberOfFiles(){ return new UmpleSourceData().setFileNames("UtilizationProfile.ump").setUmpleLines(111).setJavaLines(183).setLengths(2);}
    public UmpleSourceData UtilizationProfile_utilization(){ return new UmpleSourceData().setFileNames("UtilizationProfile.ump").setUmpleLines(200).setJavaLines(280).setLengths(5);}
    public UmpleSourceData UtilizationProfile_clearCache_execute(){ return new UmpleSourceData().setFileNames("MemoryBudget_UtilizationProfile_inner.ump","UtilizationProfile_static.ump","UtilizationProfile_static.ump","UtilizationProfile_static.ump","UtilizationProfile_static.ump").setUmpleLines(32, 8, 21, 42, 93).setJavaLines(37, 35, 36, 36, 39).setLengths(4, 2, 12, 36, 107);}
    public UmpleSourceData UtilizationProfile_estimateUPObsoleteSize(){ return new UmpleSourceData().setFileNames("UtilizationProfile.ump").setUmpleLines(211).setJavaLines(294).setLengths(8);}
    public UmpleSourceData UtilizationProfile_verifyLsnIsObsolete(){ return new UmpleSourceData().setFileNames("UtilizationProfile.ump").setUmpleLines(637).setJavaLines(803).setLengths(50);}
    public UmpleSourceData UtilizationProfile_hook190(){ return new UmpleSourceData().setFileNames("UtilizationProfile.ump").setUmpleLines(732).setJavaLines(909).setLengths(1);}
    public UmpleSourceData UtilizationProfile_clearCache_UtilizationProfile_clearCache(){ return new UmpleSourceData().setFileNames("UtilizationProfile_static.ump").setUmpleLines(5).setJavaLines(30).setLengths(1);}
    public UmpleSourceData UtilizationProfile_UtilizationProfile(){ return new UmpleSourceData().setFileNames("UtilizationProfile.ump").setUmpleLines(81).setJavaLines(144).setLengths(7);}
    public UmpleSourceData UtilizationProfile_putFileSummary(){ return new UmpleSourceData().setFileNames("UtilizationProfile.ump").setUmpleLines(426).setJavaLines(545).setLengths(1);}
    public UmpleSourceData Cleaner_shouldMigrateLN(){ return new UmpleSourceData().setFileNames("Cleaner.ump").setUmpleLines(423).setJavaLines(982).setLengths(24);}
    public UmpleSourceData Cleaner_doClean(){ return new UmpleSourceData().setFileNames("Cleaner.ump").setUmpleLines(190).setJavaLines(716).setLengths(2);}
    public UmpleSourceData Cleaner_updateReadOnlyFileCollections(){ return new UmpleSourceData().setFileNames("Statistics_Cleaner.ump","Cleaner.ump").setUmpleLines(106, 267).setJavaLines(831, 827).setLengths(2, 2);}
    public UmpleSourceData Cleaner_getLNMainKey(){ return new UmpleSourceData().setFileNames("Cleaner.ump").setUmpleLines(600).setJavaLines(1214).setLengths(5);}
    public UmpleSourceData Cleaner_updateFilesAtCheckpointEnd(){ return new UmpleSourceData().setFileNames("Cleaner.ump").setUmpleLines(259).setJavaLines(816).setLengths(2);}
    public UmpleSourceData Cleaner_getUtilizationTracker(){ return new UmpleSourceData().setFileNames("Cleaner.ump").setUmpleLines(159).setJavaLines(679).setLengths(1);}
    public UmpleSourceData Cleaner_getUtilizationProfile(){ return new UmpleSourceData().setFileNames("Cleaner.ump").setUmpleLines(163).setJavaLines(684).setLengths(1);}
    public UmpleSourceData Cleaner_traceFileNotDeleted(){ return new UmpleSourceData().setFileNames("Cleaner.ump").setUmpleLines(245).setJavaLines(791).setLengths(5);}
    public UmpleSourceData Cleaner_trace(){ return new UmpleSourceData().setFileNames("LoggingCleaner_Cleaner.ump").setUmpleLines(11).setJavaLines(1393).setLengths(1);}
    public UmpleSourceData Cleaner_processPendingLN(){ return new UmpleSourceData().setFileNames("Cleaner.ump").setUmpleLines(283).setJavaLines(852).setLengths(95);}
    public UmpleSourceData Cleaner_migrateLN(){ return new UmpleSourceData().setFileNames("Cleaner.ump").setUmpleLines(445).setJavaLines(1015).setLengths(105);}
    public UmpleSourceData Cleaner_areThreadsRunning(){ return new UmpleSourceData().setFileNames("Cleaner.ump").setUmpleLines(175).setJavaLines(698).setLengths(6);}
    public UmpleSourceData Cleaner_Cleaner(){ return new UmpleSourceData().setFileNames("Cleaner.ump").setUmpleLines(100).setJavaLines(610).setLengths(10);}
    public UmpleSourceData Cleaner_trace_Cleaner_trace(){ return new UmpleSourceData().setFileNames("LoggingCleaner_Cleaner_inner.ump").setUmpleLines(5).setJavaLines(28).setLengths(8);}
    public UmpleSourceData Cleaner_getFilesAtCheckpointStart(){ return new UmpleSourceData().setFileNames("Cleaner.ump").setUmpleLines(251).setJavaLines(805).setLengths(2);}
    public UmpleSourceData Cleaner_wakeup(){ return new UmpleSourceData().setFileNames("Cleaner.ump").setUmpleLines(167).setJavaLines(689).setLengths(5);}
    public UmpleSourceData Cleaner_envConfigUpdate(){ return new UmpleSourceData().setFileNames("Cleaner.ump").setUmpleLines(116).setJavaLines(629).setLengths(46);}
    public UmpleSourceData Cleaner_processPending(){ return new UmpleSourceData().setFileNames("Cleaner.ump").setUmpleLines(275).setJavaLines(842).setLengths(1);}
    public UmpleSourceData Cleaner_addPendingDB(){ return new UmpleSourceData().setFileNames("DeleteOp_Cleaner.ump").setUmpleLines(8).setJavaLines(1280).setLengths(10);}
    public UmpleSourceData Cleaner_hook88(){ return new UmpleSourceData().setFileNames("Cleaner.ump").setUmpleLines(621).setJavaLines(1240).setLengths(2);}
    public UmpleSourceData Cleaner_hook89(){ return new UmpleSourceData().setFileNames("Cleaner.ump").setUmpleLines(623).setJavaLines(1246).setLengths(2);}
    public UmpleSourceData Cleaner_processPending_execute(){ return new UmpleSourceData().setFileNames("DeleteOp_Cleaner_inner.ump","Cleaner_static.ump","LoggingCleaner_Cleaner_inner.ump").setUmpleLines(6, 8, 15).setJavaLines(55, 37, 40).setLengths(11, 16, 1);}
    public UmpleSourceData Cleaner_runOrPause(){ return new UmpleSourceData().setFileNames("CleanerDaemon_Cleaner.ump").setUmpleLines(6).setJavaLines(1294).setLengths(7);}
    public UmpleSourceData Cleaner_processPending_hook114(){ return new UmpleSourceData().setFileNames("Cleaner_static.ump").setUmpleLines(39).setJavaLines(70).setLengths(1);}
    public UmpleSourceData Cleaner_deleteSafeToDeleteFiles(){ return new UmpleSourceData().setFileNames("Cleaner.ump").setUmpleLines(198).setJavaLines(727).setLengths(60);}
    public UmpleSourceData Cleaner_isEvictable(){ return new UmpleSourceData().setFileNames("Evictor_Cleaner.ump").setUmpleLines(8).setJavaLines(1257).setLengths(14);}
    public UmpleSourceData Cleaner_getNWakeupRequests(){ return new UmpleSourceData().setFileNames("CleanerDaemon_Cleaner.ump").setUmpleLines(34).setJavaLines(1325).setLengths(7);}
    public UmpleSourceData Cleaner_migrateDupCountLN(){ return new UmpleSourceData().setFileNames("Cleaner.ump").setUmpleLines(531).setJavaLines(1129).setLengths(76);}
    public UmpleSourceData Cleaner_loadStats(){ return new UmpleSourceData().setFileNames("Statistics_Cleaner.ump").setUmpleLines(50).setJavaLines(1341).setLengths(43);}
    public UmpleSourceData Cleaner_processPending_Cleaner_processPending(){ return new UmpleSourceData().setFileNames("Cleaner_static.ump").setUmpleLines(5).setJavaLines(32).setLengths(1);}
    public UmpleSourceData Cleaner_getLNDupKey(){ return new UmpleSourceData().setFileNames("Cleaner.ump").setUmpleLines(611).setJavaLines(1228).setLengths(8);}
    public UmpleSourceData Cleaner_lazyMigrateDupCountLN(){ return new UmpleSourceData().setFileNames("Cleaner.ump").setUmpleLines(402).setJavaLines(959).setLengths(8);}
    public UmpleSourceData Cleaner_requestShutdown(){ return new UmpleSourceData().setFileNames("CleanerDaemon_Cleaner.ump").setUmpleLines(16).setJavaLines(1305).setLengths(5);}
    public UmpleSourceData Cleaner_shutdown(){ return new UmpleSourceData().setFileNames("CleanerDaemon_Cleaner.ump").setUmpleLines(24).setJavaLines(1314).setLengths(7);}
    public UmpleSourceData TrackedFileSummary_TrackedFileSummary(){ return new UmpleSourceData().setFileNames("TrackedFileSummary.ump").setUmpleLines(21).setJavaLines(42).setLengths(3);}
    public UmpleSourceData TrackedFileSummary_getMemorySize(){ return new UmpleSourceData().setFileNames("MemoryBudget_TrackedFileSummary.ump").setUmpleLines(10).setJavaLines(164).setLengths(1);}
    public UmpleSourceData TrackedFileSummary_trackObsolete(){ return new UmpleSourceData().setFileNames("TrackedFileSummary.ump").setUmpleLines(62).setJavaLines(103).setLengths(1);}
    public UmpleSourceData TrackedFileSummary_trackObsolete_execute(){ return new UmpleSourceData().setFileNames("MemoryBudget_TrackedFileSummary_inner.ump","MemoryBudget_TrackedFileSummary_inner.ump","TrackedFileSummary_static.ump").setUmpleLines(6, 6, 9).setJavaLines(39, 67, 36).setLengths(4, 4, 23);}
    public UmpleSourceData TrackedFileSummary_addTrackedSummary(){ return new UmpleSourceData().setFileNames("TrackedFileSummary.ump").setUmpleLines(69).setJavaLines(113).setLengths(14);}
    public UmpleSourceData TrackedFileSummary_updateMemoryBudget(){ return new UmpleSourceData().setFileNames("MemoryBudget_TrackedFileSummary.ump").setUmpleLines(14).setJavaLines(169).setLengths(2);}
    public UmpleSourceData TrackedFileSummary_getAllowFlush(){ return new UmpleSourceData().setFileNames("TrackedFileSummary.ump").setUmpleLines(30).setJavaLines(54).setLengths(1);}
    public UmpleSourceData TrackedFileSummary_setAllowFlush(){ return new UmpleSourceData().setFileNames("TrackedFileSummary.ump").setUmpleLines(37).setJavaLines(64).setLengths(1);}
    public UmpleSourceData TrackedFileSummary_containsObsoleteOffset(){ return new UmpleSourceData().setFileNames("TrackedFileSummary.ump").setUmpleLines(97).setJavaLines(150).setLengths(5);}
    public UmpleSourceData TrackedFileSummary_trackObsolete_TrackedFileSummary_trackObsolete(){ return new UmpleSourceData().setFileNames("TrackedFileSummary_static.ump").setUmpleLines(5).setJavaLines(30).setLengths(2);}
    public UmpleSourceData TrackedFileSummary_reset(){ return new UmpleSourceData().setFileNames("TrackedFileSummary.ump").setUmpleLines(51).setJavaLines(84).setLengths(10);}
    public UmpleSourceData TrackedFileSummary_getObsoleteOffsets(){ return new UmpleSourceData().setFileNames("TrackedFileSummary.ump").setUmpleLines(86).setJavaLines(136).setLengths(5);}
    public UmpleSourceData TrackedFileSummary_getFileNumber(){ return new UmpleSourceData().setFileNames("TrackedFileSummary.ump").setUmpleLines(44).setJavaLines(74).setLengths(1);}
    public UmpleSourceData PackedOffsets_PackedOffsets(){ return new UmpleSourceData().setFileNames("PackedOffsets.ump").setUmpleLines(19).setJavaLines(41).setLengths(1);}
    public UmpleSourceData Iterator_next(){ return new UmpleSourceData().setFileNames("PackedOffsets_static.ump").setUmpleLines(12).setJavaLines(38).setLengths(13);}
    public UmpleSourceData PackedOffsets_readFromLog(){ return new UmpleSourceData().setFileNames("PackedOffsets.ump").setUmpleLines(104).setJavaLines(148).setLengths(8);}
    public UmpleSourceData PackedOffsets_getLogSize(){ return new UmpleSourceData().setFileNames("PackedOffsets.ump").setUmpleLines(82).setJavaLines(120).setLengths(1);}
    public UmpleSourceData Iterator_hasNext(){ return new UmpleSourceData().setFileNames("PackedOffsets_static.ump").setUmpleLines(9).setJavaLines(33).setLengths(1);}
    public UmpleSourceData PackedOffsets_pack(){ return new UmpleSourceData().setFileNames("PackedOffsets.ump").setUmpleLines(32).setJavaLines(61).setLengths(12);}
    public UmpleSourceData PackedOffsets_iterator(){ return new UmpleSourceData().setFileNames("PackedOffsets.ump").setUmpleLines(25).setJavaLines(51).setLengths(1);}
    public UmpleSourceData PackedOffsets_writeToLog(){ return new UmpleSourceData().setFileNames("PackedOffsets.ump").setUmpleLines(89).setJavaLines(130).setLengths(9);}
    public UmpleSourceData PackedOffsets_logEntryIsTransactional(){ return new UmpleSourceData().setFileNames("PackedOffsets.ump").setUmpleLines(147).setJavaLines(200).setLengths(1);}
    public UmpleSourceData PackedOffsets_getTransactionId(){ return new UmpleSourceData().setFileNames("PackedOffsets.ump").setUmpleLines(139).setJavaLines(189).setLengths(1);}
    public UmpleSourceData PackedOffsets_toArray(){ return new UmpleSourceData().setFileNames("PackedOffsets.ump").setUmpleLines(50).setJavaLines(82).setLengths(8);}
    public UmpleSourceData Iterator_Iterator(){ return new UmpleSourceData().setFileNames("PackedOffsets_static.ump").setUmpleLines(7).setJavaLines(28).setLengths(1);}
    public UmpleSourceData PackedOffsets_dumpLog(){ return new UmpleSourceData().setFileNames("PackedOffsets.ump").setUmpleLines(118).setJavaLines(165).setLengths(14);}
    public UmpleSourceData PackedOffsets_toString(){ return new UmpleSourceData().setFileNames("PackedOffsets.ump").setUmpleLines(151).setJavaLines(205).setLengths(3);}
    public UmpleSourceData PackedOffsets_append(){ return new UmpleSourceData().setFileNames("PackedOffsets.ump").setUmpleLines(64).setJavaLines(99).setLengths(12);}
    public UmpleSourceData DatabaseUtil_checkForNullParam(){ return new UmpleSourceData().setFileNames("DatabaseUtil.ump").setUmpleLines(9).setJavaLines(34).setLengths(3);}
    public UmpleSourceData DatabaseUtil_checkForPartialKey(){ return new UmpleSourceData().setFileNames("DatabaseUtil.ump").setUmpleLines(32).setJavaLines(63).setLengths(3);}
    public UmpleSourceData DatabaseUtil_checkForNullDbt(){ return new UmpleSourceData().setFileNames("DatabaseUtil.ump").setUmpleLines(18).setJavaLines(46).setLengths(8);}
    public UmpleSourceData Sequence_copyEntry(){ return new UmpleSourceData().setFileNames("Sequence.ump").setUmpleLines(271).setJavaLines(303).setLengths(9);}
    public UmpleSourceData Sequence_getKey(){ return new UmpleSourceData().setFileNames("Sequence.ump").setUmpleLines(210).setJavaLines(230).setLengths(1);}
    public UmpleSourceData Sequence_getStats(){ return new UmpleSourceData().setFileNames("Statistics_Sequence.ump").setUmpleLines(12).setJavaLines(321).setLengths(18);}
    public UmpleSourceData Sequence_getDatabase(){ return new UmpleSourceData().setFileNames("Sequence.ump").setUmpleLines(203).setJavaLines(220).setLengths(1);}
    public UmpleSourceData Sequence_readData(){ return new UmpleSourceData().setFileNames("Sequence.ump").setUmpleLines(226).setJavaLines(252).setLengths(15);}
    public UmpleSourceData Sequence_get(){ return new UmpleSourceData().setFileNames("Sequence.ump").setUmpleLines(128).setJavaLines(131).setLengths(80);}
    public UmpleSourceData Sequence_readDataRequired(){ return new UmpleSourceData().setFileNames("Sequence.ump").setUmpleLines(217).setJavaLines(240).setLengths(3);}
    public UmpleSourceData Sequence_makeData(){ return new UmpleSourceData().setFileNames("Sequence.ump").setUmpleLines(247).setJavaLines(276).setLengths(18);}
    public UmpleSourceData Sequence_Sequence(){ return new UmpleSourceData().setFileNames("Sequence.ump").setUmpleLines(50).setJavaLines(44).setLengths(68);}
    public UmpleSourceData Sequence_close(){ return new UmpleSourceData().setFileNames("Sequence.ump").setUmpleLines(122).setJavaLines(121).setLengths(1);}
    public UmpleSourceData JEVersion_getMinor(){ return new UmpleSourceData().setFileNames("JEVersion.ump").setUmpleLines(43).setJavaLines(62).setLengths(1);}
    public UmpleSourceData JEVersion_getPatch(){ return new UmpleSourceData().setFileNames("JEVersion.ump").setUmpleLines(50).setJavaLines(72).setLengths(1);}
    public UmpleSourceData JEVersion_getMajor(){ return new UmpleSourceData().setFileNames("JEVersion.ump").setUmpleLines(36).setJavaLines(52).setLengths(1);}
    public UmpleSourceData JEVersion_getNumericVersionString(){ return new UmpleSourceData().setFileNames("JEVersion.ump").setUmpleLines(57).setJavaLines(82).setLengths(5);}
    public UmpleSourceData JEVersion_toString(){ return new UmpleSourceData().setFileNames("JEVersion.ump").setUmpleLines(29).setJavaLines(42).setLengths(1);}
    public UmpleSourceData JEVersion_JEVersion(){ return new UmpleSourceData().setFileNames("JEVersion.ump").setUmpleLines(19).setJavaLines(29).setLengths(4);}
    public UmpleSourceData JEVersion_getVersionString(){ return new UmpleSourceData().setFileNames("JEVersion.ump").setUmpleLines(68).setJavaLines(96).setLengths(9);}
    public UmpleSourceData SecondaryCursor_getSearchBothRange(){ return new UmpleSourceData().setFileNames("SecondaryCursor.ump","SecondaryCursor.ump").setUmpleLines(351, 359).setJavaLines(493, 503).setLengths(1, 9);}
    public UmpleSourceData SecondaryCursor_putNoOverwrite(){ return new UmpleSourceData().setFileNames("SecondaryCursor.ump").setUmpleLines(83).setJavaLines(132).setLengths(1);}
    public UmpleSourceData SecondaryCursor_getPrevDup(){ return new UmpleSourceData().setFileNames("SecondaryCursor.ump","SecondaryCursor.ump").setUmpleLines(246, 254).setJavaLines(353, 363).setLengths(1, 7);}
    public UmpleSourceData SecondaryCursor_SecondaryCursor(){ return new UmpleSourceData().setFileNames("SecondaryCursor.ump","SecondaryCursor.ump").setUmpleLines(20, 29).setJavaLines(45, 57).setLengths(3, 3);}
    public UmpleSourceData SecondaryCursor_getPrevNoDup(){ return new UmpleSourceData().setFileNames("SecondaryCursor.ump","SecondaryCursor.ump").setUmpleLines(265, 273).setJavaLines(379, 389).setLengths(1, 11);}
    public UmpleSourceData SecondaryCursor_readPrimaryAfterGet(){ return new UmpleSourceData().setFileNames("SecondaryCursor.ump").setUmpleLines(437).setJavaLines(594).setLengths(38);}
    public UmpleSourceData SecondaryCursor_hook74(){ return new UmpleSourceData().setFileNames("SecondaryCursor.ump").setUmpleLines(514).setJavaLines(693).setLengths(1);}
    public UmpleSourceData SecondaryCursor_hook75(){ return new UmpleSourceData().setFileNames("SecondaryCursor.ump").setUmpleLines(517).setJavaLines(698).setLengths(1);}
    public UmpleSourceData SecondaryCursor_hook76(){ return new UmpleSourceData().setFileNames("SecondaryCursor.ump").setUmpleLines(520).setJavaLines(703).setLengths(1);}
    public UmpleSourceData SecondaryCursor_getSearchKey(){ return new UmpleSourceData().setFileNames("SecondaryCursor.ump","SecondaryCursor.ump").setUmpleLines(288, 296).setJavaLines(409, 419).setLengths(1, 9);}
    public UmpleSourceData SecondaryCursor_hook77(){ return new UmpleSourceData().setFileNames("SecondaryCursor.ump").setUmpleLines(523).setJavaLines(708).setLengths(1);}
    public UmpleSourceData SecondaryCursor_hook78(){ return new UmpleSourceData().setFileNames("SecondaryCursor.ump").setUmpleLines(526).setJavaLines(713).setLengths(1);}
    public UmpleSourceData SecondaryCursor_delete(){ return new UmpleSourceData().setFileNames("SecondaryCursor.ump").setUmpleLines(60).setJavaLines(100).setLengths(13);}
    public UmpleSourceData SecondaryCursor_getLast(){ return new UmpleSourceData().setFileNames("SecondaryCursor.ump","SecondaryCursor.ump").setUmpleLines(141, 149).setJavaLines(214, 224).setLengths(1, 7);}
    public UmpleSourceData SecondaryCursor_put(){ return new UmpleSourceData().setFileNames("SecondaryCursor.ump").setUmpleLines(76).setJavaLines(122).setLengths(1);}
    public UmpleSourceData SecondaryCursor_search(){ return new UmpleSourceData().setFileNames("SecondaryCursor.ump").setUmpleLines(384).setJavaLines(535).setLengths(10);}
    public UmpleSourceData SecondaryCursor_getCurrentInternal(){ return new UmpleSourceData().setFileNames("SecondaryCursor.ump").setUmpleLines(372).setJavaLines(521).setLengths(5);}
    public UmpleSourceData SecondaryCursor_getFirst(){ return new UmpleSourceData().setFileNames("SecondaryCursor.ump","SecondaryCursor.ump").setUmpleLines(123, 131).setJavaLines(188, 198).setLengths(1, 7);}
    public UmpleSourceData SecondaryCursor_retrieveNext(){ return new UmpleSourceData().setFileNames("SecondaryCursor.ump").setUmpleLines(418).setJavaLines(573).setLengths(10);}
    public UmpleSourceData SecondaryCursor_getPrimaryDatabase(){ return new UmpleSourceData().setFileNames("SecondaryCursor.ump").setUmpleLines(38).setJavaLines(69).setLengths(1);}
    public UmpleSourceData SecondaryCursor_getNextNoDup(){ return new UmpleSourceData().setFileNames("SecondaryCursor.ump","SecondaryCursor.ump").setUmpleLines(201, 209).setJavaLines(293, 303).setLengths(1, 11);}
    public UmpleSourceData SecondaryCursor_getSearchBoth(){ return new UmpleSourceData().setFileNames("SecondaryCursor.ump","SecondaryCursor.ump").setUmpleLines(330, 338).setJavaLines(465, 475).setLengths(1, 9);}
    public UmpleSourceData SecondaryCursor_putNoDupData(){ return new UmpleSourceData().setFileNames("SecondaryCursor.ump").setUmpleLines(90).setJavaLines(142).setLengths(1);}
    public UmpleSourceData SecondaryCursor_getSearchKeyRange(){ return new UmpleSourceData().setFileNames("SecondaryCursor.ump","SecondaryCursor.ump").setUmpleLines(309, 317).setJavaLines(437, 447).setLengths(1, 9);}
    public UmpleSourceData SecondaryCursor_hook65(){ return new UmpleSourceData().setFileNames("SecondaryCursor.ump").setUmpleLines(487).setJavaLines(648).setLengths(1);}
    public UmpleSourceData SecondaryCursor_getNext(){ return new UmpleSourceData().setFileNames("SecondaryCursor.ump","SecondaryCursor.ump").setUmpleLines(159, 167).setJavaLines(240, 250).setLengths(1, 11);}
    public UmpleSourceData SecondaryCursor_hook66(){ return new UmpleSourceData().setFileNames("SecondaryCursor.ump").setUmpleLines(490).setJavaLines(653).setLengths(1);}
    public UmpleSourceData SecondaryCursor_checkArgsNoValRequired(){ return new UmpleSourceData().setFileNames("SecondaryCursor.ump").setUmpleLines(481).setJavaLines(641).setLengths(3);}
    public UmpleSourceData SecondaryCursor_hook67(){ return new UmpleSourceData().setFileNames("SecondaryCursor.ump").setUmpleLines(493).setJavaLines(658).setLengths(1);}
    public UmpleSourceData SecondaryCursor_getPrev(){ return new UmpleSourceData().setFileNames("SecondaryCursor.ump","SecondaryCursor.ump").setUmpleLines(223, 231).setJavaLines(323, 333).setLengths(1, 11);}
    public UmpleSourceData SecondaryCursor_hook68(){ return new UmpleSourceData().setFileNames("SecondaryCursor.ump").setUmpleLines(496).setJavaLines(663).setLengths(1);}
    public UmpleSourceData SecondaryCursor_hook69(){ return new UmpleSourceData().setFileNames("SecondaryCursor.ump").setUmpleLines(499).setJavaLines(668).setLengths(1);}
    public UmpleSourceData SecondaryCursor_getNextDup(){ return new UmpleSourceData().setFileNames("SecondaryCursor.ump","SecondaryCursor.ump").setUmpleLines(182, 190).setJavaLines(270, 280).setLengths(1, 4);}
    public UmpleSourceData SecondaryCursor_hook70(){ return new UmpleSourceData().setFileNames("SecondaryCursor.ump").setUmpleLines(502).setJavaLines(673).setLengths(1);}
    public UmpleSourceData SecondaryCursor_hook71(){ return new UmpleSourceData().setFileNames("SecondaryCursor.ump").setUmpleLines(505).setJavaLines(678).setLengths(1);}
    public UmpleSourceData SecondaryCursor_hook72(){ return new UmpleSourceData().setFileNames("SecondaryCursor.ump").setUmpleLines(508).setJavaLines(683).setLengths(1);}
    public UmpleSourceData SecondaryCursor_dupSecondary(){ return new UmpleSourceData().setFileNames("SecondaryCursor.ump").setUmpleLines(53).setJavaLines(90).setLengths(1);}
    public UmpleSourceData SecondaryCursor_hook73(){ return new UmpleSourceData().setFileNames("SecondaryCursor.ump").setUmpleLines(511).setJavaLines(688).setLengths(1);}
    public UmpleSourceData SecondaryCursor_getCurrent(){ return new UmpleSourceData().setFileNames("SecondaryCursor.ump","SecondaryCursor.ump").setUmpleLines(105, 113).setJavaLines(162, 172).setLengths(1, 7);}
    public UmpleSourceData SecondaryCursor_position(){ return new UmpleSourceData().setFileNames("SecondaryCursor.ump").setUmpleLines(401).setJavaLines(554).setLengths(10);}
    public UmpleSourceData SecondaryCursor_dup(){ return new UmpleSourceData().setFileNames("SecondaryCursor.ump").setUmpleLines(45).setJavaLines(79).setLengths(2);}
    public UmpleSourceData SecondaryCursor_putCurrent(){ return new UmpleSourceData().setFileNames("SecondaryCursor.ump").setUmpleLines(97).setJavaLines(152).setLengths(1);}
    public UmpleSourceData EnvironmentConfig_setCheckpointUP(){ return new UmpleSourceData().setFileNames("EnvironmentConfig.ump").setUmpleLines(201).setJavaLines(273).setLengths(1);}
    public UmpleSourceData EnvironmentConfig_setConfigParam(){ return new UmpleSourceData().setFileNames("EnvironmentConfig.ump").setUmpleLines(176).setJavaLines(239).setLengths(5);}
    public UmpleSourceData EnvironmentConfig_getCheckpointUP(){ return new UmpleSourceData().setFileNames("EnvironmentConfig.ump").setUmpleLines(208).setJavaLines(283).setLengths(1);}
    public UmpleSourceData EnvironmentConfig_getAllowCreate(){ return new UmpleSourceData().setFileNames("EnvironmentConfig.ump").setUmpleLines(53).setJavaLines(71).setLengths(1);}
    public UmpleSourceData EnvironmentConfig_getTransactional(){ return new UmpleSourceData().setFileNames("EnvironmentConfig.ump").setUmpleLines(103).setJavaLines(139).setLengths(2);}
    public UmpleSourceData EnvironmentConfig_getLockTimeout(){ return new UmpleSourceData().setFileNames("EnvironmentConfig.ump").setUmpleLines(67).setJavaLines(91).setLengths(8);}
    public UmpleSourceData EnvironmentConfig_EnvironmentConfig(){ return new UmpleSourceData().setFileNames("EnvironmentConfig.ump","EnvironmentConfig.ump").setUmpleLines(32, 39).setJavaLines(41, 51).setLengths(1, 1);}
    public UmpleSourceData EnvironmentConfig_getTxnTimeout(){ return new UmpleSourceData().setFileNames("EnvironmentConfig.ump").setUmpleLines(133).setJavaLines(181).setLengths(8);}
    public UmpleSourceData EnvironmentConfig_setTransactional(){ return new UmpleSourceData().setFileNames("EnvironmentConfig.ump").setUmpleLines(96).setJavaLines(129).setLengths(1);}
    public UmpleSourceData EnvironmentConfig_setCreateUP(){ return new UmpleSourceData().setFileNames("EnvironmentConfig.ump").setUmpleLines(187).setJavaLines(253).setLengths(1);}
    public UmpleSourceData EnvironmentConfig_cloneConfig(){ return new UmpleSourceData().setFileNames("EnvironmentConfig.ump").setUmpleLines(215).setJavaLines(293).setLengths(5);}
    public UmpleSourceData EnvironmentConfig_setTxnTimeout(){ return new UmpleSourceData().setFileNames("EnvironmentConfig.ump").setUmpleLines(126).setJavaLines(171).setLengths(1);}
    public UmpleSourceData EnvironmentConfig_setAllowCreate(){ return new UmpleSourceData().setFileNames("EnvironmentConfig.ump").setUmpleLines(46).setJavaLines(61).setLengths(1);}
    public UmpleSourceData EnvironmentConfig_setTxnSerializableIsolation(){ return new UmpleSourceData().setFileNames("EnvironmentConfig.ump").setUmpleLines(147).setJavaLines(198).setLengths(1);}
    public UmpleSourceData EnvironmentConfig_getTxnSerializableIsolation(){ return new UmpleSourceData().setFileNames("EnvironmentConfig.ump").setUmpleLines(154).setJavaLines(208).setLengths(2);}
    public UmpleSourceData EnvironmentConfig_setTxnReadCommitted(){ return new UmpleSourceData().setFileNames("EnvironmentConfig.ump").setUmpleLines(162).setJavaLines(219).setLengths(1);}
    public UmpleSourceData EnvironmentConfig_getCreateUP(){ return new UmpleSourceData().setFileNames("EnvironmentConfig.ump").setUmpleLines(194).setJavaLines(263).setLengths(1);}
    public UmpleSourceData EnvironmentConfig_getReadOnly(){ return new UmpleSourceData().setFileNames("EnvironmentConfig.ump").setUmpleLines(88).setJavaLines(118).setLengths(2);}
    public UmpleSourceData EnvironmentConfig_toString(){ return new UmpleSourceData().setFileNames("EnvironmentConfig.ump").setUmpleLines(223).setJavaLines(302).setLengths(1);}
    public UmpleSourceData EnvironmentConfig_setLocking(){ return new UmpleSourceData().setFileNames("EnvironmentConfig.ump").setUmpleLines(111).setJavaLines(150).setLengths(1);}
    public UmpleSourceData EnvironmentConfig_getLocking(){ return new UmpleSourceData().setFileNames("EnvironmentConfig.ump").setUmpleLines(118).setJavaLines(160).setLengths(2);}
    public UmpleSourceData EnvironmentConfig_getTxnReadCommitted(){ return new UmpleSourceData().setFileNames("EnvironmentConfig.ump").setUmpleLines(169).setJavaLines(229).setLengths(1);}
    public UmpleSourceData EnvironmentConfig_setReadOnly(){ return new UmpleSourceData().setFileNames("EnvironmentConfig.ump").setUmpleLines(81).setJavaLines(108).setLengths(1);}
    public UmpleSourceData EnvironmentConfig_setLockTimeout(){ return new UmpleSourceData().setFileNames("EnvironmentConfig.ump").setUmpleLines(60).setJavaLines(81).setLengths(1);}
    public UmpleSourceData XAEnvironment_rollback(){ return new UmpleSourceData().setFileNames("XAEnvironment.ump").setUmpleLines(190).setJavaLines(243).setLengths(17);}
    public UmpleSourceData XAEnvironment_prepare(){ return new UmpleSourceData().setFileNames("XAEnvironment.ump").setUmpleLines(141).setJavaLines(188).setLengths(18);}
    public UmpleSourceData XAEnvironment_getTransactionTimeout(){ return new UmpleSourceData().setFileNames("XAEnvironment.ump").setUmpleLines(213).setJavaLines(269).setLengths(6);}
    public UmpleSourceData XAEnvironment_recover(){ return new UmpleSourceData().setFileNames("XAEnvironment.ump").setUmpleLines(165).setJavaLines(215).setLengths(19);}
    public UmpleSourceData XAEnvironment_setTransactionTimeout(){ return new UmpleSourceData().setFileNames("XAEnvironment.ump").setUmpleLines(225).setJavaLines(284).setLengths(1);}
    public UmpleSourceData XAEnvironment_commit(){ return new UmpleSourceData().setFileNames("XAEnvironment.ump").setUmpleLines(44).setJavaLines(79).setLengths(23);}
    public UmpleSourceData XAEnvironment_start(){ return new UmpleSourceData().setFileNames("XAEnvironment.ump").setUmpleLines(232).setJavaLines(294).setLengths(50);}
    public UmpleSourceData XAEnvironment_getXATransaction(){ return new UmpleSourceData().setFileNames("XAEnvironment.ump").setUmpleLines(26).setJavaLines(55).setLengths(5);}
    public UmpleSourceData XAEnvironment_forget(){ return new UmpleSourceData().setFileNames("XAEnvironment.ump").setUmpleLines(110).setJavaLines(151).setLengths(4);}
    public UmpleSourceData XAEnvironment_throwNewXAException(){ return new UmpleSourceData().setFileNames("XAEnvironment.ump").setUmpleLines(285).setJavaLines(348).setLengths(3);}
    public UmpleSourceData XAEnvironment_setXATransaction(){ return new UmpleSourceData().setFileNames("XAEnvironment.ump").setUmpleLines(37).setJavaLines(69).setLengths(1);}
    public UmpleSourceData XAEnvironment_XAEnvironment(){ return new UmpleSourceData().setFileNames("XAEnvironment.ump").setUmpleLines(19).setJavaLines(45).setLengths(1);}
    public UmpleSourceData XAEnvironment_end(){ return new UmpleSourceData().setFileNames("XAEnvironment.ump").setUmpleLines(73).setJavaLines(111).setLengths(31);}
    public UmpleSourceData XAEnvironment_isSameRM(){ return new UmpleSourceData().setFileNames("XAEnvironment.ump").setUmpleLines(120).setJavaLines(164).setLengths(15);}
    public UmpleSourceData LockNotGrantedException_LockNotGrantedException(){ return new UmpleSourceData().setFileNames("LockNotGrantedException.ump","LockNotGrantedException.ump","LockNotGrantedException.ump","LockNotGrantedException.ump").setUmpleLines(7, 11, 15, 19).setJavaLines(33, 38, 43, 48).setLengths(1, 1, 1, 1);}
    public UmpleSourceData JoinConfig_setNoSort(){ return new UmpleSourceData().setFileNames("JoinConfig.ump").setUmpleLines(20).setJavaLines(45).setLengths(1);}
    public UmpleSourceData JoinConfig_JoinConfig(){ return new UmpleSourceData().setFileNames("JoinConfig.ump").setUmpleLines(14).setJavaLines(35).setLengths(1);}
    public UmpleSourceData JoinConfig_getNoSort(){ return new UmpleSourceData().setFileNames("JoinConfig.ump").setUmpleLines(27).setJavaLines(55).setLengths(1);}
    public UmpleSourceData JoinConfig_cloneConfig(){ return new UmpleSourceData().setFileNames("JoinConfig.ump").setUmpleLines(34).setJavaLines(65).setLengths(5);}
    public UmpleSourceData CheckpointConfig_setForce(){ return new UmpleSourceData().setFileNames("CheckpointConfig.ump").setUmpleLines(21).setJavaLines(46).setLengths(1);}
    public UmpleSourceData CheckpointConfig_CheckpointConfig(){ return new UmpleSourceData().setFileNames("CheckpointConfig.ump").setUmpleLines(15).setJavaLines(36).setLengths(1);}
    public UmpleSourceData CheckpointConfig_getForce(){ return new UmpleSourceData().setFileNames("CheckpointConfig.ump").setUmpleLines(28).setJavaLines(56).setLengths(1);}
    public UmpleSourceData CheckpointConfig_getMinimizeRecoveryTime(){ return new UmpleSourceData().setFileNames("CheckpointConfig.ump").setUmpleLines(42).setJavaLines(76).setLengths(1);}
    public UmpleSourceData CheckpointConfig_setMinutes(){ return new UmpleSourceData().setFileNames("CPTime_CheckpointConfig.ump").setUmpleLines(10).setJavaLines(86).setLengths(1);}
    public UmpleSourceData CheckpointConfig_setMinimizeRecoveryTime(){ return new UmpleSourceData().setFileNames("CheckpointConfig.ump").setUmpleLines(35).setJavaLines(66).setLengths(1);}
    public UmpleSourceData CheckpointConfig_getKBytes(){ return new UmpleSourceData().setFileNames("CPBytes_CheckpointConfig.ump").setUmpleLines(17).setJavaLines(116).setLengths(1);}
    public UmpleSourceData CheckpointConfig_getMinutes(){ return new UmpleSourceData().setFileNames("CPTime_CheckpointConfig.ump").setUmpleLines(17).setJavaLines(96).setLengths(1);}
    public UmpleSourceData CheckpointConfig_setKBytes(){ return new UmpleSourceData().setFileNames("CPBytes_CheckpointConfig.ump").setUmpleLines(10).setJavaLines(106).setLengths(1);}
    public UmpleSourceData PreloadStats_getNDBINsLoaded(){ return new UmpleSourceData().setFileNames("Statistics_PreloadStats.ump").setUmpleLines(69).setJavaLines(103).setLengths(1);}
    public UmpleSourceData PreloadStats_getNDINsLoaded(){ return new UmpleSourceData().setFileNames("Statistics_PreloadStats.ump").setUmpleLines(62).setJavaLines(93).setLengths(1);}
    public UmpleSourceData PreloadStats_setNBINsLoaded(){ return new UmpleSourceData().setFileNames("Statistics_PreloadStats.ump").setUmpleLines(97).setJavaLines(143).setLengths(1);}
    public UmpleSourceData PreloadStats_PreloadStats(){ return new UmpleSourceData().setFileNames("PreloadStats.ump").setUmpleLines(11).setJavaLines(37).setLengths(1);}
    public UmpleSourceData PreloadStats_getStatus(){ return new UmpleSourceData().setFileNames("Statistics_PreloadStats.ump").setUmpleLines(83).setJavaLines(123).setLengths(1);}
    public UmpleSourceData PreloadStats_setNLNsLoaded(){ return new UmpleSourceData().setFileNames("Statistics_PreloadStats.ump").setUmpleLines(104).setJavaLines(153).setLengths(1);}
    public UmpleSourceData PreloadStats_setNDBINsLoaded(){ return new UmpleSourceData().setFileNames("Statistics_PreloadStats.ump").setUmpleLines(118).setJavaLines(173).setLengths(1);}
    public UmpleSourceData PreloadStats_getNINsLoaded(){ return new UmpleSourceData().setFileNames("Statistics_PreloadStats.ump").setUmpleLines(41).setJavaLines(63).setLengths(1);}
    public UmpleSourceData PreloadStats_setStatus(){ return new UmpleSourceData().setFileNames("Statistics_PreloadStats.ump").setUmpleLines(132).setJavaLines(193).setLengths(1);}
    public UmpleSourceData PreloadStats_getNDupCountLNsLoaded(){ return new UmpleSourceData().setFileNames("Statistics_PreloadStats.ump").setUmpleLines(76).setJavaLines(113).setLengths(1);}
    public UmpleSourceData PreloadStats_setNDupCountLNsLoaded(){ return new UmpleSourceData().setFileNames("Statistics_PreloadStats.ump").setUmpleLines(125).setJavaLines(183).setLengths(1);}
    public UmpleSourceData PreloadStats_setNDINsLoaded(){ return new UmpleSourceData().setFileNames("Statistics_PreloadStats.ump").setUmpleLines(111).setJavaLines(163).setLengths(1);}
    public UmpleSourceData PreloadStats_reset(){ return new UmpleSourceData().setFileNames("Statistics_PreloadStats.ump").setUmpleLines(28).setJavaLines(47).setLengths(7);}
    public UmpleSourceData PreloadStats_getNBINsLoaded(){ return new UmpleSourceData().setFileNames("Statistics_PreloadStats.ump").setUmpleLines(48).setJavaLines(73).setLengths(1);}
    public UmpleSourceData PreloadStats_toString(){ return new UmpleSourceData().setFileNames("Statistics_PreloadStats.ump").setUmpleLines(139).setJavaLines(203).setLengths(9);}
    public UmpleSourceData PreloadStats_getNLNsLoaded(){ return new UmpleSourceData().setFileNames("Statistics_PreloadStats.ump").setUmpleLines(55).setJavaLines(83).setLengths(1);}
    public UmpleSourceData PreloadStats_setNINsLoaded(){ return new UmpleSourceData().setFileNames("Statistics_PreloadStats.ump").setUmpleLines(90).setJavaLines(133).setLengths(1);}
    public UmpleSourceData DatabaseEntry_getPartial(){ return new UmpleSourceData().setFileNames("DatabaseEntry.ump").setUmpleLines(120).setJavaLines(168).setLengths(1);}
    public UmpleSourceData DatabaseEntry_setPartial(){ return new UmpleSourceData().setFileNames("DatabaseEntry.ump","DatabaseEntry.ump").setUmpleLines(83, 127).setJavaLines(116, 178).setLengths(3, 1);}
    public UmpleSourceData DatabaseEntry_setData(){ return new UmpleSourceData().setFileNames("DatabaseEntry.ump","DatabaseEntry.ump").setUmpleLines(65, 74).setJavaLines(92, 104).setLengths(3, 3);}
    public UmpleSourceData DatabaseEntry_getOffset(){ return new UmpleSourceData().setFileNames("DatabaseEntry.ump").setUmpleLines(134).setJavaLines(188).setLengths(1);}
    public UmpleSourceData DatabaseEntry_dumpData(){ return new UmpleSourceData().setFileNames("DatabaseEntry.ump").setUmpleLines(162).setJavaLines(228).setLengths(1);}
    public UmpleSourceData DatabaseEntry_getPartialLength(){ return new UmpleSourceData().setFileNames("DatabaseEntry.ump").setUmpleLines(92).setJavaLines(128).setLengths(1);}
    public UmpleSourceData DatabaseEntry_setPartialLength(){ return new UmpleSourceData().setFileNames("DatabaseEntry.ump").setUmpleLines(99).setJavaLines(138).setLengths(1);}
    public UmpleSourceData DatabaseEntry_setPartialOffset(){ return new UmpleSourceData().setFileNames("DatabaseEntry.ump").setUmpleLines(113).setJavaLines(158).setLengths(1);}
    public UmpleSourceData DatabaseEntry_DatabaseEntry(){ return new UmpleSourceData().setFileNames("DatabaseEntry.ump","DatabaseEntry.ump","DatabaseEntry.ump").setUmpleLines(33, 39, 49).setJavaLines(47, 57, 70).setLengths(1, 4, 3);}
    public UmpleSourceData DatabaseEntry_getSize(){ return new UmpleSourceData().setFileNames("DatabaseEntry.ump").setUmpleLines(148).setJavaLines(208).setLengths(1);}
    public UmpleSourceData DatabaseEntry_setSize(){ return new UmpleSourceData().setFileNames("DatabaseEntry.ump").setUmpleLines(155).setJavaLines(218).setLengths(1);}
    public UmpleSourceData DatabaseEntry_setOffset(){ return new UmpleSourceData().setFileNames("DatabaseEntry.ump").setUmpleLines(141).setJavaLines(198).setLengths(1);}
    public UmpleSourceData DatabaseEntry_hashCode(){ return new UmpleSourceData().setFileNames("DatabaseEntry.ump").setUmpleLines(199).setJavaLines(271).setLengths(7);}
    public UmpleSourceData DatabaseEntry_equals(){ return new UmpleSourceData().setFileNames("DatabaseEntry.ump").setUmpleLines(169).setJavaLines(238).setLengths(24);}
    public UmpleSourceData DatabaseEntry_toString(){ return new UmpleSourceData().setFileNames("DatabaseEntry.ump").setUmpleLines(19).setJavaLines(30).setLengths(8);}
    public UmpleSourceData DatabaseEntry_getData(){ return new UmpleSourceData().setFileNames("DatabaseEntry.ump").setUmpleLines(58).setJavaLines(82).setLengths(1);}
    public UmpleSourceData DatabaseEntry_getPartialOffset(){ return new UmpleSourceData().setFileNames("DatabaseEntry.ump").setUmpleLines(106).setJavaLines(148).setLengths(1);}
    public UmpleSourceData DIN_getDupCountLN(){ return new UmpleSourceData().setFileNames("DIN.ump").setUmpleLines(101).setJavaLines(139).setLengths(1);}
    public UmpleSourceData DIN_getLogType(){ return new UmpleSourceData().setFileNames("DIN.ump").setUmpleLines(191).setJavaLines(249).setLengths(1);}
    public UmpleSourceData DIN_readFromLog(){ return new UmpleSourceData().setFileNames("DIN.ump").setUmpleLines(236).setJavaLines(305).setLengths(8);}
    public UmpleSourceData DIN_updateDupCountLN_DIN_updateDupCountLN(){ return new UmpleSourceData().setFileNames("DIN_static.ump").setUmpleLines(5).setJavaLines(30).setLengths(2);}
    public UmpleSourceData DIN_getLogSize(){ return new UmpleSourceData().setFileNames("DIN.ump").setUmpleLines(210).setJavaLines(273).setLengths(7);}
    public UmpleSourceData DIN_matchLNByNodeId(){ return new UmpleSourceData().setFileNames("DIN.ump").setUmpleLines(171).setJavaLines(225).setLengths(10);}
    public UmpleSourceData DIN_accumulateStats(){ return new UmpleSourceData().setFileNames("DIN.ump").setUmpleLines(184).setJavaLines(239).setLengths(1);}
    public UmpleSourceData DIN_updateDupCountLNRefAndNullTarget(){ return new UmpleSourceData().setFileNames("DIN.ump").setUmpleLines(122).setJavaLines(173).setLengths(1);}
    public UmpleSourceData DIN_computeOverhead(){ return new UmpleSourceData().setFileNames("MemoryBudget_DIN.ump").setUmpleLines(16).setJavaLines(399).setLengths(1);}
    public UmpleSourceData DIN_writeToLog(){ return new UmpleSourceData().setFileNames("DIN.ump").setUmpleLines(223).setJavaLines(289).setLengths(7);}
    public UmpleSourceData DIN_updateDupCountLNRef(){ return new UmpleSourceData().setFileNames("DIN.ump").setUmpleLines(129).setJavaLines(183).setLengths(2);}
    public UmpleSourceData DIN_getDupTreeKey(){ return new UmpleSourceData().setFileNames("DIN.ump").setUmpleLines(86).setJavaLines(119).setLengths(1);}
    public UmpleSourceData DIN_isDbRoot(){ return new UmpleSourceData().setFileNames("DIN.ump").setUmpleLines(141).setJavaLines(199).setLengths(1);}
    public UmpleSourceData DIN_logInternal(){ return new UmpleSourceData().setFileNames("DIN.ump").setUmpleLines(199).setJavaLines(259).setLengths(5);}
    public UmpleSourceData DIN_shortClassName(){ return new UmpleSourceData().setFileNames("DIN.ump").setUmpleLines(301).setJavaLines(380).setLengths(1);}
    public UmpleSourceData DIN_computeMemorySize(){ return new UmpleSourceData().setFileNames("MemoryBudget_DIN.ump").setUmpleLines(8).setJavaLines(390).setLengths(5);}
    public UmpleSourceData DIN_getChildKey(){ return new UmpleSourceData().setFileNames("DIN.ump").setUmpleLines(75).setJavaLines(104).setLengths(1);}
    public UmpleSourceData DIN_getMainTreeKey(){ return new UmpleSourceData().setFileNames("DIN.ump").setUmpleLines(93).setJavaLines(129).setLengths(1);}
    public UmpleSourceData DIN_updateDupCountLNRefAndNullTarget_DIN_updateDupCountLNRefAndNullTarget(){ return new UmpleSourceData().setFileNames("DIN_static.ump").setUmpleLines(18).setJavaLines(30).setLengths(2);}
    public UmpleSourceData DIN_incrementDuplicateCount(){ return new UmpleSourceData().setFileNames("DIN.ump").setUmpleLines(156).setJavaLines(209).setLengths(12);}
    public UmpleSourceData DIN_createNewInstance(){ return new UmpleSourceData().setFileNames("DIN.ump").setUmpleLines(61).setJavaLines(84).setLengths(1);}
    public UmpleSourceData DIN_getDupCountLNRef(){ return new UmpleSourceData().setFileNames("DIN.ump").setUmpleLines(97).setJavaLines(134).setLengths(1);}
    public UmpleSourceData DIN_DIN(){ return new UmpleSourceData().setFileNames("DIN.ump","DIN.ump").setUmpleLines(38, 48).setJavaLines(55, 67).setLengths(3, 3);}
    public UmpleSourceData DIN_endTag(){ return new UmpleSourceData().setFileNames("DIN.ump").setUmpleLines(262).setJavaLines(336).setLengths(1);}
    public UmpleSourceData DIN_dumpLogAdditional(){ return new UmpleSourceData().setFileNames("DIN.ump").setUmpleLines(250).setJavaLines(322).setLengths(5);}
    public UmpleSourceData DIN_generateLevel(){ return new UmpleSourceData().setFileNames("DIN.ump").setUmpleLines(54).setJavaLines(74).setLengths(1);}
    public UmpleSourceData DIN_updateDupCountLN_execute(){ return new UmpleSourceData().setFileNames("MemoryBudget_DIN_inner.ump","MemoryBudget_DIN_inner.ump","DIN_static.ump","MemoryBudget_DIN_inner.ump","DIN_static.ump").setUmpleLines(17, 21, 9, 6, 22).setJavaLines(38, 42, 36, 46, 36).setLengths(1, 2, 1, 3, 8);}
    public UmpleSourceData DIN_dumpString(){ return new UmpleSourceData().setFileNames("DIN.ump").setUmpleLines(270).setJavaLines(347).setLengths(24);}
    public UmpleSourceData DIN_selectKey(){ return new UmpleSourceData().setFileNames("DIN.ump").setUmpleLines(79).setJavaLines(109).setLengths(1);}
    public UmpleSourceData DIN_getMemoryOverhead(){ return new UmpleSourceData().setFileNames("MemoryBudget_DIN.ump").setUmpleLines(20).setJavaLines(404).setLengths(1);}
    public UmpleSourceData DIN_getDupKey(){ return new UmpleSourceData().setFileNames("DIN.ump").setUmpleLines(68).setJavaLines(94).setLengths(1);}
    public UmpleSourceData DIN_beginTag(){ return new UmpleSourceData().setFileNames("DIN.ump").setUmpleLines(258).setJavaLines(331).setLengths(1);}
    public UmpleSourceData DIN_containsDuplicates(){ return new UmpleSourceData().setFileNames("DIN.ump").setUmpleLines(137).setJavaLines(194).setLengths(1);}
    public UmpleSourceData DIN_toString(){ return new UmpleSourceData().setFileNames("DIN.ump").setUmpleLines(297).setJavaLines(375).setLengths(1);}
    public UmpleSourceData DIN_setDupCountLN(){ return new UmpleSourceData().setFileNames("MemoryBudget_DIN.ump","DIN.ump").setUmpleLines(35, 108).setJavaLines(151, 149).setLengths(2, 1);}
    public UmpleSourceData DIN_updateDupCountLN(){ return new UmpleSourceData().setFileNames("DIN.ump").setUmpleLines(115).setJavaLines(163).setLengths(1);}
    public UmpleSourceData LN_addToDirtyMap(){ return new UmpleSourceData().setFileNames("LN.ump").setUmpleLines(173).setJavaLines(222).setLengths(1);}
    public UmpleSourceData LN_LN(){ return new UmpleSourceData().setFileNames("LN.ump","LN.ump","LN.ump").setUmpleLines(39, 47, 59).setJavaLines(62, 73, 88).setLengths(2, 6, 9);}
    public UmpleSourceData LN_getLogType(){ return new UmpleSourceData().setFileNames("LN.ump").setUmpleLines(295).setJavaLines(372).setLengths(1);}
    public UmpleSourceData LN_readFromLog(){ return new UmpleSourceData().setFileNames("LN.ump").setUmpleLines(326).setJavaLines(412).setLengths(5);}
    public UmpleSourceData LN_log(){ return new UmpleSourceData().setFileNames("LN.ump","LN.ump").setUmpleLines(239, 252).setJavaLines(305, 320).setLengths(1, 23);}
    public UmpleSourceData LN_getLogSize(){ return new UmpleSourceData().setFileNames("LN.ump").setUmpleLines(302).setJavaLines(382).setLengths(6);}
    public UmpleSourceData LN_isSoughtNode(){ return new UmpleSourceData().setFileNames("LN.ump").setUmpleLines(110).setJavaLines(149).setLengths(1);}
    public UmpleSourceData LN_makeDeleted(){ return new UmpleSourceData().setFileNames("LN.ump").setUmpleLines(99).setJavaLines(134).setLengths(1);}
    public UmpleSourceData LN_delete(){ return new UmpleSourceData().setFileNames("LN.ump").setUmpleLines(125).setJavaLines(169).setLengths(31);}
    public UmpleSourceData LN_writeToLog(){ return new UmpleSourceData().setFileNames("LN.ump").setUmpleLines(314).setJavaLines(397).setLengths(6);}
    public UmpleSourceData LN_logEntryIsTransactional(){ return new UmpleSourceData().setFileNames("LN.ump").setUmpleLines(353).setJavaLines(445).setLengths(1);}
    public UmpleSourceData LN_isDeleted(){ return new UmpleSourceData().setFileNames("LN.ump").setUmpleLines(95).setJavaLines(129).setLengths(1);}
    public UmpleSourceData LN_canBeAncestor(){ return new UmpleSourceData().setFileNames("LN.ump").setUmpleLines(117).setJavaLines(159).setLengths(1);}
    public UmpleSourceData LN_getData(){ return new UmpleSourceData().setFileNames("LN.ump").setUmpleLines(84).setJavaLines(116).setLengths(1);}
    public UmpleSourceData LN_getTransactionalLogType(){ return new UmpleSourceData().setFileNames("LN.ump").setUmpleLines(281).setJavaLines(352).setLengths(1);}
    public UmpleSourceData LN_init(){ return new UmpleSourceData().setFileNames("LN.ump","LN.ump").setUmpleLines(71, 80).setJavaLines(101, 111).setLengths(6, 1);}
    public UmpleSourceData LN_isValidForDelete(){ return new UmpleSourceData().setFileNames("LN.ump").setUmpleLines(103).setJavaLines(139).setLengths(1);}
    public UmpleSourceData LN_endTag(){ return new UmpleSourceData().setFileNames("LN.ump").setUmpleLines(192).setJavaLines(252).setLengths(1);}
    public UmpleSourceData LN_accountForSubtreeRemoval(){ return new UmpleSourceData().setFileNames("LN.ump").setUmpleLines(185).setJavaLines(242).setLengths(1);}
    public UmpleSourceData LN_dumpLogAdditional(){ return new UmpleSourceData().setFileNames("LN.ump").setUmpleLines(365).setJavaLines(461).setLengths(1);}
    public UmpleSourceData LN_dumpString(){ return new UmpleSourceData().setFileNames("LN.ump").setUmpleLines(196).setJavaLines(257).setLengths(20);}
    public UmpleSourceData LN_copyData(){ return new UmpleSourceData().setFileNames("LN.ump").setUmpleLines(88).setJavaLines(121).setLengths(4);}
    public UmpleSourceData LN_modify(){ return new UmpleSourceData().setFileNames("LN.ump").setUmpleLines(163).setJavaLines(209).setLengths(4);}
    public UmpleSourceData LN_getMemorySizeIncludedByParent(){ return new UmpleSourceData().setFileNames("MemoryBudget_LN.ump").setUmpleLines(8).setJavaLines(471).setLengths(5);}
    public UmpleSourceData LN_rebuildINList(){ return new UmpleSourceData().setFileNames("LN.ump").setUmpleLines(179).setJavaLines(232).setLengths(1);}
    public UmpleSourceData LN_getTransactionId(){ return new UmpleSourceData().setFileNames("LN.ump").setUmpleLines(361).setJavaLines(456).setLengths(1);}
    public UmpleSourceData LN_beginTag(){ return new UmpleSourceData().setFileNames("LN.ump").setUmpleLines(188).setJavaLines(247).setLengths(1);}
    public UmpleSourceData LN_countAsObsoleteWhenLogged(){ return new UmpleSourceData().setFileNames("LN.ump").setUmpleLines(288).setJavaLines(362).setLengths(1);}
    public UmpleSourceData LN_logProvisional(){ return new UmpleSourceData().setFileNames("LN.ump").setUmpleLines(226).setJavaLines(290).setLengths(1);}
    public UmpleSourceData LN_dumpLog(){ return new UmpleSourceData().setFileNames("LN.ump").setUmpleLines(337).setJavaLines(426).setLengths(9);}
    public UmpleSourceData Tree_findBinForInsert(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(1378).setJavaLines(1709).setLengths(82);}
    public UmpleSourceData Tree_traceSplitRoot_Tree_traceSplitRoot(){ return new UmpleSourceData().setFileNames("LoggingFine_Tree_inner.ump").setUmpleLines(37).setJavaLines(29).setLengths(7);}
    public UmpleSourceData Tree_traceMutate_Tree_traceMutate(){ return new UmpleSourceData().setFileNames("LoggingFine_Tree_inner.ump").setUmpleLines(5).setJavaLines(29).setLengths(12);}
    public UmpleSourceData Tree_splitRoot(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(755).setJavaLines(942).setLengths(37);}
    public UmpleSourceData Tree_searchSubTreeUntilSplit(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(966).setJavaLines(1198).setLengths(63);}
    public UmpleSourceData Tree_getLogSize(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(1457).setJavaLines(1820).setLengths(5);}
    public UmpleSourceData Tree_validateDelete(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(1553).setJavaLines(1939).setLengths(10);}
    public UmpleSourceData RootChildReference_setLsn(){ return new UmpleSourceData().setFileNames("Tree_static.ump").setUmpleLines(35).setJavaLines(78).setLengths(5);}
    public UmpleSourceData Tree_withRootLatchedExclusive(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(160).setJavaLines(199).setLengths(14);}
    public UmpleSourceData Tree_createDuplicateTree(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(1303).setJavaLines(1614).setLengths(72);}
    public UmpleSourceData Tree_getParentINForChildIN(){ return new UmpleSourceData().setFileNames("Tree.ump","Tree.ump","Tree.ump").setUmpleLines(416, 429, 453).setJavaLines(515, 530, 561).setLengths(1, 16, 24);}
    public UmpleSourceData Tree_getRootIN(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(1018).setJavaLines(1280).setLengths(21);}
    public UmpleSourceData Tree_logEntryIsTransactional(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(1500).setJavaLines(1875).setLengths(1);}
    public UmpleSourceData Tree_setWaitHook(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(1602).setJavaLines(1992).setLengths(1);}
    public UmpleSourceData RootChildReference_clearTarget(){ return new UmpleSourceData().setFileNames("Tree_static.ump").setUmpleLines(31).setJavaLines(69).setLengths(5);}
    public UmpleSourceData RootChildReference_RootChildReference(){ return new UmpleSourceData().setFileNames("Tree_static.ump","Tree_static.ump","Tree_static.ump").setUmpleLines(14, 17, 20).setJavaLines(33, 38, 43).setLengths(1, 1, 1);}
    public UmpleSourceData Tree_Tree(){ return new UmpleSourceData().setFileNames("Tree.ump","Tree.ump").setUmpleLines(74, 82).setJavaLines(79, 90).setLengths(2, 3);}
    public UmpleSourceData Tree_searchDupTreeForDupCountLNParent(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(585).setJavaLines(716).setLengths(8);}
    public UmpleSourceData Tree_init(){ return new UmpleSourceData().setFileNames("Latches_Tree.ump","Tree.ump").setUmpleLines(23, 91).setJavaLines(104, 102).setLengths(2, 3);}
    public UmpleSourceData Tree_setRoot(){ return new UmpleSourceData().setFileNames("Latches_Tree.ump","Tree.ump").setUmpleLines(31, 118).setJavaLines(144, 142).setLengths(2, 1);}
    public UmpleSourceData Tree_searchSplitsAllowed_execute(){ return new UmpleSourceData().setFileNames("Tree_static.ump","Tree_static.ump","Derivative_LoggingFine_LoggingBase_Tree_inner.ump","LoggingFine_Tree_inner.ump","Derivative_LoggingFine_LoggingBase_Tree_inner.ump","LoggingFine_Tree_inner.ump","Derivative_LoggingFiner_LoggingBase_Tree_inner.ump","LoggingFiner_Tree_inner.ump","Derivative_LoggingFiner_LoggingBase_Tree_inner.ump","LoggingFiner_Tree_inner.ump").setUmpleLines(61, 152, 6, 19, 37, 46, 6, 14, 25, 36).setJavaLines(38, 36, 47, 45, 42, 40, 42, 40, 42, 40).setLengths(69, 123, 27, 1, 11, 1, 15, 1, 15, 1);}
    public UmpleSourceData Tree_searchSubTree(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(824).setJavaLines(1023).setLengths(82);}
    public UmpleSourceData Tree_searchSubTreeSplitsAllowed(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(945).setJavaLines(1175).setLengths(14);}
    public UmpleSourceData Tree_forceSplit(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(1011).setJavaLines(1270).setLengths(1);}
    public UmpleSourceData Tree_rebuildINList(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(1514).setJavaLines(1895).setLengths(8);}
    public UmpleSourceData Tree_setCkptHook(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(1610).setJavaLines(2002).setLengths(1);}
    public UmpleSourceData Tree_logTreeRemoval(){ return new UmpleSourceData().setFileNames("Latches_Tree.ump","Tree.ump").setUmpleLines(66, 241).setJavaLines(300, 298).setLengths(2, 9);}
    public UmpleSourceData Tree_cascadeUpdates(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(260).setJavaLines(324).setLengths(29);}
    public UmpleSourceData Tree_getNextBin(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(649).setJavaLines(795).setLengths(1);}
    public UmpleSourceData Tree_searchSplitsAllowed(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(811).setJavaLines(1008).setLengths(1);}
    public UmpleSourceData Tree_insertDuplicate(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(1138).setJavaLines(1414).setLengths(150);}
    public UmpleSourceData Tree_getTreeStatsAccumulator(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(148).setJavaLines(185).setLengths(5);}
    public UmpleSourceData Tree_searchDeletableSubTree(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(895).setJavaLines(1114).setLengths(52);}
    public UmpleSourceData Tree_getRootLsn(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(133).setJavaLines(166).setLengths(5);}
    public UmpleSourceData Tree_getFirstNode(){ return new UmpleSourceData().setFileNames("Tree.ump","Tree.ump").setUmpleLines(374, 390).setJavaLines(456, 478).setLengths(1, 9);}
    public UmpleSourceData Tree_readFromLog(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(1478).setJavaLines(1847).setLengths(5);}
    public UmpleSourceData Tree_setDatabase(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(100).setJavaLines(118).setLengths(5);}
    public UmpleSourceData Tree_getTreeStats(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(144).setJavaLines(180).setLengths(1);}
    public UmpleSourceData Tree_insert(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(1046).setJavaLines(1315).setLengths(89);}
    public UmpleSourceData Tree_deleteDup(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(297).setJavaLines(366).setLengths(20);}
    public UmpleSourceData Tree_traceSplitRoot(){ return new UmpleSourceData().setFileNames("LoggingFine_Tree.ump").setUmpleLines(9).setJavaLines(2021).setLengths(1);}
    public UmpleSourceData Tree_getPrevBin(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(659).setJavaLines(808).setLengths(1);}
    public UmpleSourceData Tree_delete(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(192).setJavaLines(238).setLengths(50);}
    public UmpleSourceData SplitInfo_SplitInfo(){ return new UmpleSourceData().setFileNames("Tree_static.ump").setUmpleLines(48).setJavaLines(76).setLengths(3);}
    public UmpleSourceData Tree_getNextBinInternal(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(666).setJavaLines(818).setLengths(115);}
    public UmpleSourceData Tree_deleteDupSubtree(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(322).setJavaLines(396).setLengths(50);}
    public UmpleSourceData Tree_search(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(799).setJavaLines(993).setLengths(6);}
    public UmpleSourceData Tree_writeToLog(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(1468).setJavaLines(1834).setLengths(4);}
    public UmpleSourceData Tree_traceMutate(){ return new UmpleSourceData().setFileNames("LoggingFine_Tree.ump").setUmpleLines(17).setJavaLines(2031).setLengths(2);}
    public UmpleSourceData Tree_forceSplit_Tree_forceSplit(){ return new UmpleSourceData().setFileNames("Tree_static.ump").setUmpleLines(147).setJavaLines(29).setLengths(3);}
    public UmpleSourceData Tree_withRootLatchedShared(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(173).setJavaLines(217).setLengths(10);}
    public UmpleSourceData RootChildReference_setTarget(){ return new UmpleSourceData().setFileNames("Tree_static.ump").setUmpleLines(27).setJavaLines(60).setLengths(5);}
    public UmpleSourceData Tree_dump(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(1525).setJavaLines(1907).setLengths(1);}
    public UmpleSourceData SearchType_SearchType(){ return new UmpleSourceData().setFileNames("Tree_static.ump").setUmpleLines(9).setJavaLines(28).setLengths(1);}
    public UmpleSourceData Tree_traceInsert(){ return new UmpleSourceData().setFileNames("LoggingFiner_Tree.ump").setUmpleLines(8).setJavaLines(2042).setLengths(1);}
    public UmpleSourceData Tree_releaseNodeLadderLatches(){ return new UmpleSourceData().setFileNames("Latches_Tree.ump").setUmpleLines(11).setJavaLines(2007).setLengths(5);}
    public UmpleSourceData Tree_setTreeStatsAccumulator(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(156).setJavaLines(194).setLengths(1);}
    public UmpleSourceData Tree_maybeSplitDuplicateRoot(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(1272).setJavaLines(1576).setLengths(23);}
    public UmpleSourceData Tree_validateINList(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(1569).setJavaLines(1958).setLengths(30);}
    public UmpleSourceData Tree_searchDupTreeByNodeId(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(565).setJavaLines(689).setLengths(18);}
    public UmpleSourceData Tree_validateInsertArgs(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(1366).setJavaLines(1695).setLengths(4);}
    public UmpleSourceData Tree_accountForSubtreeRemoval(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(1439).setJavaLines(1795).setLengths(16);}
    public UmpleSourceData Tree_setSearchHook(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(1606).setJavaLines(1997).setLengths(1);}
    public UmpleSourceData Tree_getParentBINForChildLN(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(490).setJavaLines(602).setLengths(78);}
    public UmpleSourceData Tree_dumpString(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(1529).setJavaLines(1912).setLengths(18);}
    public UmpleSourceData Tree_traceInsert_Tree_traceInsert(){ return new UmpleSourceData().setFileNames("LoggingFiner_Tree_inner.ump").setUmpleLines(27).setJavaLines(29).setLengths(7);}
    public UmpleSourceData RootChildReference_fetchTarget(){ return new UmpleSourceData().setFileNames("Tree_static.ump").setUmpleLines(23).setJavaLines(48).setLengths(8);}
    public UmpleSourceData Tree_getDatabase(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(111).setJavaLines(132).setLengths(1);}
    public UmpleSourceData Tree_traceInsertDuplicate_Tree_traceInsertDuplicate(){ return new UmpleSourceData().setFileNames("LoggingFiner_Tree_inner.ump").setUmpleLines(5).setJavaLines(29).setLengths(7);}
    public UmpleSourceData Tree_makeRootChildReference(){ return new UmpleSourceData().setFileNames("Tree.ump","Tree.ump").setUmpleLines(122, 126).setJavaLines(151, 156).setLengths(1, 1);}
    public UmpleSourceData Tree_getLastNode(){ return new UmpleSourceData().setFileNames("Tree.ump","Tree.ump").setUmpleLines(382, 403).setJavaLines(467, 497).setLengths(1, 9);}
    public UmpleSourceData Tree_getTransactionId(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(1507).setJavaLines(1885).setLengths(1);}
    public UmpleSourceData Tree_searchDupTreeForDBIN(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(601).setJavaLines(733).setLengths(50);}
    public UmpleSourceData Tree_traceInsertDuplicate(){ return new UmpleSourceData().setFileNames("LoggingFiner_Tree.ump").setUmpleLines(16).setJavaLines(2052).setLengths(1);}
    public UmpleSourceData Tree_searchSplitsAllowed_Tree_searchSplitsAllowed(){ return new UmpleSourceData().setFileNames("Tree_static.ump").setUmpleLines(55).setJavaLines(30).setLengths(4);}
    public UmpleSourceData Tree_dumpLog(){ return new UmpleSourceData().setFileNames("Tree.ump").setUmpleLines(1489).setJavaLines(1861).setLengths(5);}
    public UmpleSourceData MapLN_getTransactionalLogType(){ return new UmpleSourceData().setFileNames("MapLN.ump").setUmpleLines(85).setJavaLines(121).setLengths(1);}
    public UmpleSourceData MapLN_postFetchInit(){ return new UmpleSourceData().setFileNames("MapLN.ump").setUmpleLines(54).setJavaLines(83).setLengths(1);}
    public UmpleSourceData MapLN_getLogType(){ return new UmpleSourceData().setFileNames("MapLN.ump").setUmpleLines(92).setJavaLines(131).setLengths(1);}
    public UmpleSourceData MapLN_readFromLog(){ return new UmpleSourceData().setFileNames("MapLN.ump").setUmpleLines(115).setJavaLines(163).setLengths(3);}
    public UmpleSourceData MapLN_getLogSize(){ return new UmpleSourceData().setFileNames("MapLN.ump").setUmpleLines(99).setJavaLines(141).setLengths(1);}
    public UmpleSourceData MapLN_endTag(){ return new UmpleSourceData().setFileNames("MapLN.ump").setUmpleLines(66).setJavaLines(98).setLengths(1);}
    public UmpleSourceData MapLN_dumpLogAdditional(){ return new UmpleSourceData().setFileNames("MapLN.ump").setUmpleLines(124).setJavaLines(175).setLengths(1);}
    public UmpleSourceData MapLN_makeDeleted(){ return new UmpleSourceData().setFileNames("MapLN.ump").setUmpleLines(42).setJavaLines(67).setLengths(2);}
    public UmpleSourceData MapLN_dumpString(){ return new UmpleSourceData().setFileNames("MapLN.ump").setUmpleLines(70).setJavaLines(103).setLengths(9);}
    public UmpleSourceData MapLN_getDatabase(){ return new UmpleSourceData().setFileNames("MapLN.ump").setUmpleLines(47).setJavaLines(73).setLengths(1);}
    public UmpleSourceData MapLN_writeToLog(){ return new UmpleSourceData().setFileNames("MapLN.ump").setUmpleLines(106).setJavaLines(151).setLengths(3);}
    public UmpleSourceData MapLN_MapLN(){ return new UmpleSourceData().setFileNames("MapLN.ump","MapLN.ump").setUmpleLines(24, 33).setJavaLines(44, 56).setLengths(3, 2);}
    public UmpleSourceData MapLN_isDeleted(){ return new UmpleSourceData().setFileNames("MapLN.ump").setUmpleLines(38).setJavaLines(62).setLengths(1);}
    public UmpleSourceData MapLN_beginTag(){ return new UmpleSourceData().setFileNames("MapLN.ump").setUmpleLines(62).setJavaLines(93).setLengths(1);}
    public UmpleSourceData MapLN_toString(){ return new UmpleSourceData().setFileNames("MapLN.ump").setUmpleLines(58).setJavaLines(88).setLengths(1);}
    public UmpleSourceData NodeNotEmptyException_NodeNotEmptyException(){ return new UmpleSourceData().setFileNames("NodeNotEmptyException.ump").setUmpleLines(9).setJavaLines(30).setLengths(1);}
    public UmpleSourceData FileSummaryLN_getTransactionalLogType(){ return new UmpleSourceData().setFileNames("FileSummaryLN.ump").setUmpleLines(179).setJavaLines(233).setLengths(2);}
    public UmpleSourceData FileSummaryLN_postFetchInit(){ return new UmpleSourceData().setFileNames("FileSummaryLN.ump").setUmpleLines(134).setJavaLines(178).setLengths(4);}
    public UmpleSourceData FileSummaryLN_getLogType(){ return new UmpleSourceData().setFileNames("FileSummaryLN.ump").setUmpleLines(187).setJavaLines(244).setLengths(1);}
    public UmpleSourceData FileSummaryLN_readFromLog(){ return new UmpleSourceData().setFileNames("FileSummaryLN.ump").setUmpleLines(239).setJavaLines(311).setLengths(8);}
    public UmpleSourceData FileSummaryLN_getTrackedSummary(){ return new UmpleSourceData().setFileNames("FileSummaryLN.ump").setUmpleLines(62).setJavaLines(85).setLengths(1);}
    public UmpleSourceData FileSummaryLN_getLogSize(){ return new UmpleSourceData().setFileNames("FileSummaryLN.ump").setUmpleLines(208).setJavaLines(274).setLengths(7);}
    public UmpleSourceData FileSummaryLN_makeFullKey(){ return new UmpleSourceData().setFileNames("FileSummaryLN.ump").setUmpleLines(122).setJavaLines(163).setLengths(6);}
    public UmpleSourceData FileSummaryLN_getOffsets(){ return new UmpleSourceData().setFileNames("FileSummaryLN.ump").setUmpleLines(253).setJavaLines(328).setLengths(7);}
    public UmpleSourceData FileSummaryLN_endTag(){ return new UmpleSourceData().setFileNames("FileSummaryLN.ump").setUmpleLines(149).setJavaLines(196).setLengths(1);}
    public UmpleSourceData FileSummaryLN_dumpLogAdditional(){ return new UmpleSourceData().setFileNames("FileSummaryLN.ump").setUmpleLines(167).setJavaLines(218).setLengths(6);}
    public UmpleSourceData FileSummaryLN_hasStringKey(){ return new UmpleSourceData().setFileNames("FileSummaryLN.ump").setUmpleLines(83).setJavaLines(115).setLengths(5);}
    public UmpleSourceData FileSummaryLN_dumpString(){ return new UmpleSourceData().setFileNames("FileSummaryLN.ump").setUmpleLines(153).setJavaLines(201).setLengths(8);}
    public UmpleSourceData FileSummaryLN_setTrackedSummary(){ return new UmpleSourceData().setFileNames("FileSummaryLN.ump").setUmpleLines(54).setJavaLines(74).setLengths(2);}
    public UmpleSourceData FileSummaryLN_writeToLog(){ return new UmpleSourceData().setFileNames("FileSummaryLN.ump").setUmpleLines(221).setJavaLines(290).setLengths(12);}
    public UmpleSourceData FileSummaryLN_makePartialKey(){ return new UmpleSourceData().setFileNames("FileSummaryLN.ump").setUmpleLines(111).setJavaLines(149).setLengths(4);}
    public UmpleSourceData FileSummaryLN_getBaseSummary(){ return new UmpleSourceData().setFileNames("FileSummaryLN.ump").setUmpleLines(69).setJavaLines(95).setLengths(1);}
    public UmpleSourceData FileSummaryLN_beginTag(){ return new UmpleSourceData().setFileNames("FileSummaryLN.ump").setUmpleLines(145).setJavaLines(191).setLengths(1);}
    public UmpleSourceData FileSummaryLN_countAsObsoleteWhenLogged(){ return new UmpleSourceData().setFileNames("FileSummaryLN.ump").setUmpleLines(201).setJavaLines(264).setLengths(1);}
    public UmpleSourceData FileSummaryLN_toString(){ return new UmpleSourceData().setFileNames("FileSummaryLN.ump").setUmpleLines(141).setJavaLines(186).setLengths(1);}
    public UmpleSourceData FileSummaryLN_getObsoleteOffsets(){ return new UmpleSourceData().setFileNames("FileSummaryLN.ump").setUmpleLines(76).setJavaLines(105).setLengths(1);}
    public UmpleSourceData FileSummaryLN_marshallOutsideWriteLatch(){ return new UmpleSourceData().setFileNames("FileSummaryLN.ump").setUmpleLines(194).setJavaLines(254).setLengths(1);}
    public UmpleSourceData FileSummaryLN_getFileNumber(){ return new UmpleSourceData().setFileNames("FileSummaryLN.ump").setUmpleLines(94).setJavaLines(129).setLengths(11);}
    public UmpleSourceData FileSummaryLN_FileSummaryLN(){ return new UmpleSourceData().setFileNames("FileSummaryLN.ump","FileSummaryLN.ump").setUmpleLines(35, 46).setJavaLines(49, 63).setLengths(5, 2);}
    public UmpleSourceData IN_getFileNumberOffset(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(559).setJavaLines(647).setLengths(1);}
    public UmpleSourceData IN_getLogSize(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(1277).setJavaLines(1434).setLengths(16);}
    public UmpleSourceData IN_getDatabaseId(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(363).setJavaLines(414).setLengths(1);}
    public UmpleSourceData IN_setLsn(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(462).setJavaLines(540).setLengths(1);}
    public UmpleSourceData IN_accumulateStats(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(1199).setJavaLines(1331).setLengths(1);}
    public UmpleSourceData IN_findEntry(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(807).setJavaLines(973).setLengths(39);}
    public UmpleSourceData IN_updateEntry3_hook643(){ return new UmpleSourceData().setFileNames("IN_static.ump").setUmpleLines(122).setJavaLines(57).setLengths(1);}
    public UmpleSourceData IN_updateEntry2_hook642(){ return new UmpleSourceData().setFileNames("IN_static.ump").setUmpleLines(96).setJavaLines(55).setLengths(1);}
    public UmpleSourceData IN_setEntry_hook640(){ return new UmpleSourceData().setFileNames("IN_static.ump").setUmpleLines(56).setJavaLines(68).setLengths(1);}
    public UmpleSourceData IN_setFileNumberOffset(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(555).setJavaLines(642).setLengths(1);}
    public UmpleSourceData IN_getTarget(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(430).setJavaLines(499).setLengths(1);}
    public UmpleSourceData IN_setKnownDeleted(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(620).setJavaLines(727).setLengths(2);}
    public UmpleSourceData IN_getKeyComparator(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(968).setJavaLines(1172).setLengths(1);}
    public UmpleSourceData IN_getIdentifierKey(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(304).setJavaLines(332).setLengths(1);}
    public UmpleSourceData IN_updateEntryCompareKey_IN_updateEntryCompareKey(){ return new UmpleSourceData().setFileNames("IN_static.ump").setUmpleLines(126).setJavaLines(30).setLengths(5);}
    public UmpleSourceData IN_isEntryPendingDeleted(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(590).setJavaLines(685).setLengths(1);}
    public UmpleSourceData IN_isDbRoot(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(292).setJavaLines(316).setLengths(1);}
    public UmpleSourceData IN_postRecoveryInit(){ return new UmpleSourceData().setFileNames("MemoryBudget_IN.ump","IN.ump").setUmpleLines(164, 212).setJavaLines(211, 207).setLengths(2, 2);}
    public UmpleSourceData IN_getLsn(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(445).setJavaLines(520).setLengths(11);}
    public UmpleSourceData IN_computeMemorySize(){ return new UmpleSourceData().setFileNames("MemoryBudget_IN.ump").setUmpleLines(56).setJavaLines(1854).setLengths(10);}
    public UmpleSourceData IN_setFileOffset(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(563).setJavaLines(652).setLengths(1);}
    public UmpleSourceData IN_validateSubtreeBeforeDelete_IN_validateSubtreeBeforeDelete(){ return new UmpleSourceData().setFileNames("IN_static.ump").setUmpleLines(248).setJavaLines(29).setLengths(2);}
    public UmpleSourceData IN_getMainTreeKey(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(345).setJavaLines(389).setLengths(1);}
    public UmpleSourceData IN_isEvictionProhibited(){ return new UmpleSourceData().setFileNames("Evictor_IN.ump").setUmpleLines(39).setJavaLines(1987).setLengths(1);}
    public UmpleSourceData IN_hasNonLNChildren(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(1183).setJavaLines(1311).setLengths(1);}
    public UmpleSourceData IN_getFileOffset(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(567).setJavaLines(657).setLengths(1);}
    public UmpleSourceData IN_init(){ return new UmpleSourceData().setFileNames("MemoryBudget_IN.ump","IN.ump").setUmpleLines(151, 135).setJavaLines(125, 108).setLengths(2, 15);}
    public UmpleSourceData IN_postFetchInit(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(200).setJavaLines(189).setLengths(9);}
    public UmpleSourceData IN_IN(){ return new UmpleSourceData().setFileNames("IN.ump","IN.ump").setUmpleLines(119, 127).setJavaLines(86, 97).setLengths(2, 2);}
    public UmpleSourceData IN_compress(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(902).setJavaLines(1085).setLengths(1);}
    public UmpleSourceData IN_setLsn_hook639(){ return new UmpleSourceData().setFileNames("IN_static.ump").setUmpleLines(20).setJavaLines(52).setLengths(1);}
    public UmpleSourceData IN_get3ByteInt(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(577).setJavaLines(669).setLengths(7);}
    public UmpleSourceData IN_endTag(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(1443).setJavaLines(1625).setLengths(1);}
    public UmpleSourceData IN_getInMemorySize(){ return new UmpleSourceData().setFileNames("MemoryBudget_IN.ump").setUmpleLines(34).setJavaLines(1827).setLengths(1);}
    public UmpleSourceData IN_trackProvisionalObsolete_IN_trackProvisionalObsolete(){ return new UmpleSourceData().setFileNames("IN_static.ump").setUmpleLines(513).setJavaLines(30).setLengths(4);}
    public UmpleSourceData IN_getAccumulatedDelta(){ return new UmpleSourceData().setFileNames("MemoryBudget_IN.ump").setUmpleLines(129).setJavaLines(1936).setLengths(1);}
    public UmpleSourceData IN_setLsn_execute(){ return new UmpleSourceData().setFileNames("MemoryBudget_IN_inner.ump","IN_static.ump","MemoryBudget_IN_inner.ump","IN_static.ump","MemoryBudget_IN_inner.ump","MemoryBudget_IN_inner.ump","IN_static.ump","MemoryBudget_IN_inner.ump","IN_static.ump","MemoryBudget_IN_inner.ump","IN_static.ump","MemoryBudget_IN_inner.ump","IN_static.ump","IN_static.ump","IN_static.ump","IN_static.ump","IN_static.ump","Latches_IN_inner.ump","IN_static.ump","IN_static.ump","MemoryBudget_IN_inner.ump","IN_static.ump","IN_static.ump","Derivative_LoggingFine_LoggingBase_IN_inner.ump","LoggingFine_IN_inner.ump","Derivative_LoggingFinest_LoggingBase_IN_inner.ump","LoggingFinest_IN_inner.ump").setUmpleLines(85, 10, 127, 32, 74, 79, 67, 95, 83, 116, 107, 63, 133, 162, 213, 252, 297, 63, 421, 466, 24, 519, 570, 6, 17, 6, 10).setJavaLines(39, 37, 42, 40, 39, 44, 37, 40, 38, 41, 39, 41, 39, 36, 38, 35, 42, 37, 35, 34, 82, 38, 36, 45, 43, 38, 36).setLengths(2, 7, 2, 20, 2, 2, 1, 2, 9, 2, 10, 2, 14, 42, 29, 24, 101, 2, 39, 35, 4, 42, 14, 25, 1, 10, 1);}
    public UmpleSourceData IN_setPendingDeleted(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(597).setJavaLines(695).setLengths(2);}
    public UmpleSourceData IN_getEqualityKey(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(153).setJavaLines(131).setLengths(3);}
    public UmpleSourceData IN_getMemoryOverhead(){ return new UmpleSourceData().setFileNames("MemoryBudget_IN.ump").setUmpleLines(86).setJavaLines(1888).setLengths(1);}
    public UmpleSourceData IN_deleteEntry(){ return new UmpleSourceData().setFileNames("IN.ump","IN.ump").setUmpleLines(875, 892).setJavaLines(1050, 1070).setLengths(8, 1);}
    public UmpleSourceData IN_initMemorySize(){ return new UmpleSourceData().setFileNames("MemoryBudget_IN.ump").setUmpleLines(12).setJavaLines(1798).setLengths(1);}
    public UmpleSourceData IN_adjustCursors(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(959).setJavaLines(1157).setLengths(1);}
    public UmpleSourceData IN_traceDelete_IN_traceDelete(){ return new UmpleSourceData().setFileNames("LoggingFinest_IN_inner.ump").setUmpleLines(5).setJavaLines(29).setLengths(3);}
    public UmpleSourceData IN_isStateKnownDeleted(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(650).setJavaLines(769).setLengths(1);}
    public UmpleSourceData IN_hasResidentChildren(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(1190).setJavaLines(1321).setLengths(6);}
    public UmpleSourceData IN_getLogType(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(1270).setJavaLines(1424).setLengths(1);}
    public UmpleSourceData IN_readFromLog(){ return new UmpleSourceData().setFileNames("Latches_IN.ump","IN.ump").setUmpleLines(126, 1332).setJavaLines(1543, 1495).setLengths(2, 46);}
    public UmpleSourceData IN_log(){ return new UmpleSourceData().setFileNames("IN.ump","IN.ump","IN.ump").setUmpleLines(1206, 1213, 1230).setJavaLines(1341, 1351, 1373).setLengths(1, 1, 1);}
    public UmpleSourceData IN_adjustCursorsForInsert(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(962).setJavaLines(1162).setLengths(1);}
    public UmpleSourceData IN_updateEntry(){ return new UmpleSourceData().setFileNames("IN.ump","IN.ump","IN.ump","IN.ump","IN.ump","MemoryBudget_IN.ump","IN.ump").setUmpleLines(727, 734, 741, 748, 756, 177, 765).setJavaLines(865, 875, 885, 895, 906, 920, 918).setLengths(1, 1, 1, 2, 3, 2, 2);}
    public UmpleSourceData IN_getDirty(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(280).setJavaLines(301).setLengths(1);}
    public UmpleSourceData IN_splitSpecial(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(948).setJavaLines(1145).setLengths(8);}
    public UmpleSourceData IN_setDirty(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(284).setJavaLines(306).setLengths(1);}
    public UmpleSourceData IN_getNEntries(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(643).setJavaLines(759).setLengths(1);}
    public UmpleSourceData IN_getEvictionType(){ return new UmpleSourceData().setFileNames("Evictor_IN.ump").setUmpleLines(28).setJavaLines(1973).setLengths(5);}
    public UmpleSourceData IN_insertEntry(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(855).setJavaLines(1024).setLengths(1);}
    public UmpleSourceData IN_computeLsnOverhead(){ return new UmpleSourceData().setFileNames("MemoryBudget_IN.ump").setUmpleLines(73).setJavaLines(1873).setLengths(5);}
    public UmpleSourceData IN_writeToLog(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(1299).setJavaLines(1459).setLengths(27);}
    public UmpleSourceData IN_isDirty(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(636).setJavaLines(749).setLengths(1);}
    public UmpleSourceData IN_setInListResident(){ return new UmpleSourceData().setFileNames("MemoryBudget_IN.ump").setUmpleLines(133).setJavaLines(1941).setLengths(1);}
    public UmpleSourceData IN_getDupTreeKey(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(338).setJavaLines(379).setLengths(1);}
    public UmpleSourceData IN_shiftEntriesRight(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(976).setJavaLines(1183).setLengths(5);}
    public UmpleSourceData IN_setTarget(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(437).setJavaLines(509).setLengths(1);}
    public UmpleSourceData IN_verify(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(1001).setJavaLines(1214).setLengths(1);}
    public UmpleSourceData IN_latch(){ return new UmpleSourceData().setFileNames("Latches_IN.ump","IN.ump","Latches_IN.ump").setUmpleLines(55, 234, 13).setJavaLines(247, 242, 2007).setLengths(2, 3, 1);}
    public UmpleSourceData IN_adjustFileNumbers(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(532).setJavaLines(618).setLengths(20);}
    public UmpleSourceData IN_flushProvisionalObsolete(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(1263).setJavaLines(1414).setLengths(1);}
    public UmpleSourceData IN_getEntryLsnLongArray(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(466).setJavaLines(545).setLengths(1);}
    public UmpleSourceData IN_accountForSubtreeRemoval(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(1021).setJavaLines(1244).setLengths(14);}
    public UmpleSourceData IN_isValidForDelete_IN_isValidForDelete(){ return new UmpleSourceData().setFileNames("IN_static.ump").setUmpleLines(463).setJavaLines(29).setLengths(1);}
    public UmpleSourceData IN_flushProvisionalObsolete_hook656(){ return new UmpleSourceData().setFileNames("IN_static.ump").setUmpleLines(585).setJavaLines(59).setLengths(1);}
    public UmpleSourceData IN_flushProvisionalObsolete_hook655(){ return new UmpleSourceData().setFileNames("IN_static.ump").setUmpleLines(583).setJavaLines(54).setLengths(1);}
    public UmpleSourceData IN_computeArraysOverhead(){ return new UmpleSourceData().setFileNames("MemoryBudget_IN.ump").setUmpleLines(81).setJavaLines(1882).setLengths(2);}
    public UmpleSourceData IN_trackProvisionalObsolete_hook653(){ return new UmpleSourceData().setFileNames("IN_static.ump").setUmpleLines(560).setJavaLines(100).setLengths(1);}
    public UmpleSourceData IN_trackProvisionalObsolete_hook651(){ return new UmpleSourceData().setFileNames("IN_static.ump").setUmpleLines(556).setJavaLines(90).setLengths(1);}
    public UmpleSourceData IN_getLevel(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(268).setJavaLines(287).setLengths(1);}
    public UmpleSourceData IN_fetchTarget(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(672).setJavaLines(800).setLengths(29);}
    public UmpleSourceData IN_isCompressible(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(906).setJavaLines(1090).setLengths(1);}
    public UmpleSourceData IN_getDatabase(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(352).setJavaLines(399).setLengths(1);}
    public UmpleSourceData IN_selectKey(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(324).setJavaLines(359).setLengths(1);}
    public UmpleSourceData IN_dumpKeys(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(1447).setJavaLines(1630).setLengths(3);}
    public UmpleSourceData IN_equals(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(159).setJavaLines(138).setLengths(5);}
    public UmpleSourceData IN_latchNoWait(){ return new UmpleSourceData().setFileNames("IN.ump","Latches_IN.ump").setUmpleLines(243, 20).setJavaLines(258, 2017).setLengths(10, 1);}
    public UmpleSourceData IN_getGeneration(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(256).setJavaLines(272).setLengths(1);}
    public UmpleSourceData IN_initEntryLsn(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(474).setJavaLines(555).setLengths(3);}
    public UmpleSourceData IN_updateEntry_IN_updateEntry(){ return new UmpleSourceData().setFileNames("IN_static.ump").setUmpleLines(62).setJavaLines(30).setLengths(3);}
    public UmpleSourceData IN_dumpLog(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(1384).setJavaLines(1554).setLengths(32);}
    public UmpleSourceData IN_toString(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(1542).setJavaLines(1732).setLengths(1);}
    public UmpleSourceData IN_makeFetchErrorMsg(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(701).setJavaLines(833).setLengths(13);}
    public UmpleSourceData IN_verify_IN_verify(){ return new UmpleSourceData().setFileNames("IN_static.ump").setUmpleLines(417).setJavaLines(29).setLengths(2);}
    public UmpleSourceData IN_updateEntry2_IN_updateEntry2(){ return new UmpleSourceData().setFileNames("IN_static.ump").setUmpleLines(77).setJavaLines(30).setLengths(4);}
    public UmpleSourceData IN_isRoot(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(288).setJavaLines(311).setLengths(1);}
    public UmpleSourceData IN_isEntryKnownDeleted(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(613).setJavaLines(717).setLengths(1);}
    public UmpleSourceData IN_updateMemorySize(){ return new UmpleSourceData().setFileNames("MemoryBudget_IN.ump","MemoryBudget_IN.ump","MemoryBudget_IN.ump").setUmpleLines(90, 101, 106).setJavaLines(1893, 1905, 1911).setLengths(8, 2, 8);}
    public UmpleSourceData IN_updateEntry3_IN_updateEntry3(){ return new UmpleSourceData().setFileNames("IN_static.ump").setUmpleLines(100).setJavaLines(30).setLengths(5);}
    public UmpleSourceData IN_setGeneration(){ return new UmpleSourceData().setFileNames("IN.ump","IN.ump").setUmpleLines(260, 264).setJavaLines(277, 282).setLengths(1, 1);}
    public UmpleSourceData IN_compareTo(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(174).setJavaLines(157).setLengths(13);}
    public UmpleSourceData IN_insertEntry1_IN_insertEntry1(){ return new UmpleSourceData().setFileNames("IN_static.ump").setUmpleLines(158).setJavaLines(30).setLengths(2);}
    public UmpleSourceData IN_setLastFullLsn(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(220).setJavaLines(222).setLengths(1);}
    public UmpleSourceData IN_splitInternal_IN_splitInternal(){ return new UmpleSourceData().setFileNames("IN_static.ump").setUmpleLines(290).setJavaLines(33).setLengths(5);}
    public UmpleSourceData IN_logEntryIsTransactional(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(1422).setJavaLines(1595).setLengths(1);}
    public UmpleSourceData IN_getEntryLsnByteArray(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(470).setJavaLines(550).setLengths(1);}
    public UmpleSourceData IN_split(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(936).setJavaLines(1130).setLengths(1);}
    public UmpleSourceData IN_getMigrate(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(408).setJavaLines(470).setLengths(1);}
    public UmpleSourceData IN_logInternal(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(1238).setJavaLines(1383).setLengths(12);}
    public UmpleSourceData IN_getEntryInMemorySize(){ return new UmpleSourceData().setFileNames("MemoryBudget_IN.ump","MemoryBudget_IN.ump").setUmpleLines(38, 42).setJavaLines(1832, 1837).setLengths(1, 8);}
    public UmpleSourceData IN_entryZeroKeyComparesLow(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(928).setJavaLines(1119).setLengths(1);}
    public UmpleSourceData IN_setIdentifierKey(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(312).setJavaLines(343).setLengths(2);}
    public UmpleSourceData IN_setKey(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(400).setJavaLines(459).setLengths(2);}
    public UmpleSourceData IN_getKey(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(393).setJavaLines(449).setLengths(1);}
    public UmpleSourceData IN_getChildEvictionType(){ return new UmpleSourceData().setFileNames("Evictor_IN.ump").setUmpleLines(46).setJavaLines(1997).setLengths(1);}
    public UmpleSourceData IN_insertEntry1(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(865).setJavaLines(1037).setLengths(1);}
    public UmpleSourceData IN_createNewInstance(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(193).setJavaLines(179).setLengths(1);}
    public UmpleSourceData IN_releaseLatchIfOwner(){ return new UmpleSourceData().setFileNames("Latches_IN.ump").setUmpleLines(34).setJavaLines(2037).setLengths(1);}
    public UmpleSourceData IN_deleteEntry_IN_deleteEntry(){ return new UmpleSourceData().setFileNames("IN_static.ump").setUmpleLines(208).setJavaLines(31).setLengths(3);}
    public UmpleSourceData IN_isValidForDelete(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(1041).setJavaLines(1267).setLengths(1);}
    public UmpleSourceData IN_traceDelete(){ return new UmpleSourceData().setFileNames("LoggingFinest_IN.ump").setUmpleLines(8).setJavaLines(2068).setLengths(1);}
    public UmpleSourceData IN_verifyMemorySize(){ return new UmpleSourceData().setFileNames("MemoryBudget_IN.ump").setUmpleLines(16).setJavaLines(1803).setLengths(15);}
    public UmpleSourceData IN_validateSubtreeBeforeDelete(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(910).setJavaLines(1095).setLengths(1);}
    public UmpleSourceData IN_flushProvisionalObsolete_IN_flushProvisionalObsolete(){ return new UmpleSourceData().setFileNames("IN_static.ump").setUmpleLines(566).setJavaLines(30).setLengths(2);}
    public UmpleSourceData IN_rebuildINList(){ return new UmpleSourceData().setFileNames("MemoryBudget_IN.ump","IN.ump").setUmpleLines(185, 1008).setJavaLines(1226, 1224).setLengths(2, 7);}
    public UmpleSourceData IN_getDupKey(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(331).setJavaLines(369).setLengths(1);}
    public UmpleSourceData IN_dumpDeletedState(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(1535).setJavaLines(1724).setLengths(4);}
    public UmpleSourceData IN_setProhibitNextDelta(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(899).setJavaLines(1080).setLengths(1);}
    public UmpleSourceData IN_traceSplit_IN_traceSplit(){ return new UmpleSourceData().setFileNames("LoggingFine_IN_inner.ump").setUmpleLines(5).setJavaLines(29).setLengths(10);}
    public UmpleSourceData IN_setMigrate(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(415).setJavaLines(480).setLengths(5);}
    public UmpleSourceData IN_releaseLatch(){ return new UmpleSourceData().setFileNames("Latches_IN.ump").setUmpleLines(27).setJavaLines(2027).setLengths(1);}
    public UmpleSourceData IN_descendOnParentSearch(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(1149).setJavaLines(1272).setLengths(14);}
    public UmpleSourceData IN_clearEntry(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(383).setJavaLines(436).setLengths(4);}
    public UmpleSourceData IN_shiftEntriesLeft(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(988).setJavaLines(1198).setLengths(7);}
    public UmpleSourceData IN_setDatabase(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(359).setJavaLines(409).setLengths(1);}
    public UmpleSourceData IN_isSoughtNode(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(1166).setJavaLines(1290).setLengths(7);}
    public UmpleSourceData IN_put3ByteInt(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(571).setJavaLines(662).setLengths(3);}
    public UmpleSourceData IN_hook627(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(1581).setJavaLines(1788).setLengths(1);}
    public UmpleSourceData IN_hook626(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(1578).setJavaLines(1783).setLengths(1);}
    public UmpleSourceData IN_setEntryInternal(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(367).setJavaLines(419).setLengths(13);}
    public UmpleSourceData IN_hook625(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(1575).setJavaLines(1778).setLengths(1);}
    public UmpleSourceData IN_clearKnownDeleted(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(628).setJavaLines(738).setLengths(2);}
    public UmpleSourceData IN_hook624(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(1572).setJavaLines(1773).setLengths(1);}
    public UmpleSourceData IN_hook623(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(1569).setJavaLines(1768).setLengths(1);}
    public UmpleSourceData IN_computeOverhead(){ return new UmpleSourceData().setFileNames("MemoryBudget_IN.ump").setUmpleLines(69).setJavaLines(1868).setLengths(1);}
    public UmpleSourceData IN_changeMemorySize(){ return new UmpleSourceData().setFileNames("MemoryBudget_IN.ump").setUmpleLines(117).setJavaLines(1923).setLengths(9);}
    public UmpleSourceData IN_setLsnElement(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(480).setJavaLines(562).setLengths(35);}
    public UmpleSourceData IN_trackProvisionalObsolete(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(1256).setJavaLines(1404).setLengths(1);}
    public UmpleSourceData IN_hook622(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(1566).setJavaLines(1763).setLengths(1);}
    public UmpleSourceData IN_hook621(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(1563).setJavaLines(1758).setLengths(1);}
    public UmpleSourceData IN_updateEntryCompareKey(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(773).setJavaLines(933).setLengths(1);}
    public UmpleSourceData IN_hook620(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(1560).setJavaLines(1753).setLengths(1);}
    public UmpleSourceData IN_setLsn_IN_setLsn(){ return new UmpleSourceData().setFileNames("IN_static.ump").setUmpleLines(5).setJavaLines(30).setLengths(3);}
    public UmpleSourceData IN_isStatePendingDeleted(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(657).setJavaLines(779).setLengths(1);}
    public UmpleSourceData IN_hashCode(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(167).setJavaLines(147).setLengths(1);}
    public UmpleSourceData IN_setIsRoot(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(296).setJavaLines(321).setLengths(2);}
    public UmpleSourceData IN_shortClassName(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(1546).setJavaLines(1737).setLengths(1);}
    public UmpleSourceData IN_canBeAncestor(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(1176).setJavaLines(1301).setLengths(1);}
    public UmpleSourceData IN_isKeyInBounds(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(780).setJavaLines(943).setLengths(17);}
    public UmpleSourceData IN_getChildKey(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(320).setJavaLines(354).setLengths(1);}
    public UmpleSourceData IN_getMaxEntries(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(664).setJavaLines(789).setLengths(1);}
    public UmpleSourceData IN_setEntry(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(720).setJavaLines(855).setLengths(1);}
    public UmpleSourceData IN_setEntry_IN_setEntry(){ return new UmpleSourceData().setFileNames("IN_static.ump").setUmpleLines(24).setJavaLines(30).setLengths(6);}
    public UmpleSourceData IN_mutateToLongArray(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(518).setJavaLines(601).setLengths(8);}
    public UmpleSourceData IN_getLastFullVersion(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(227).setJavaLines(232).setLengths(1);}
    public UmpleSourceData IN_hook615(){ return new UmpleSourceData().setFileNames("MemoryBudget_IN.ump").setUmpleLines(137).setJavaLines(1946).setLengths(1);}
    public UmpleSourceData IN_needsSplitting(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(917).setJavaLines(1105).setLengths(5);}
    public UmpleSourceData IN_dumpLogAdditional(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(1436).setJavaLines(1615).setLengths(1);}
    public UmpleSourceData IN_generateLevel(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(272).setJavaLines(292).setLengths(5);}
    public UmpleSourceData IN_dumpString(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(1457).setJavaLines(1643).setLengths(72);}
    public UmpleSourceData IN_traceSplit(){ return new UmpleSourceData().setFileNames("LoggingFine_IN.ump").setUmpleLines(9).setJavaLines(2057).setLengths(2);}
    public UmpleSourceData IN_isEvictable(){ return new UmpleSourceData().setFileNames("Evictor_IN.ump").setUmpleLines(14).setJavaLines(1956).setLengths(7);}
    public UmpleSourceData IN_getState(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(423).setJavaLines(489).setLengths(1);}
    public UmpleSourceData IN_clearPendingDeleted(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(605).setJavaLines(706).setLengths(2);}
    public UmpleSourceData IN_getTransactionId(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(1429).setJavaLines(1605).setLengths(1);}
    public UmpleSourceData IN_isLatchOwner(){ return new UmpleSourceData().setFileNames("Latches_IN.ump").setUmpleLines(41).setJavaLines(2047).setLengths(1);}
    public UmpleSourceData IN_beginTag(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(1439).setJavaLines(1620).setLengths(1);}
    public UmpleSourceData IN_logProvisional(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(1222).setJavaLines(1363).setLengths(1);}
    public UmpleSourceData IN_splitInternal(){ return new UmpleSourceData().setFileNames("IN.ump").setUmpleLines(940).setJavaLines(1135).setLengths(1);}
    public UmpleSourceData Key_makeKey(){ return new UmpleSourceData().setFileNames("Key.ump").setUmpleLines(29).setJavaLines(47).setLengths(8);}
    public UmpleSourceData Key_getKey(){ return new UmpleSourceData().setFileNames("Key.ump").setUmpleLines(43).setJavaLines(64).setLengths(1);}
    public UmpleSourceData Key_compareUnsignedBytes(){ return new UmpleSourceData().setFileNames("Key.ump").setUmpleLines(90).setJavaLines(126).setLengths(13);}
    public UmpleSourceData Key_hashCode(){ return new UmpleSourceData().setFileNames("Key.ump").setUmpleLines(68).setJavaLines(98).setLengths(5);}
    public UmpleSourceData Key_equals(){ return new UmpleSourceData().setFileNames("Key.ump").setUmpleLines(61).setJavaLines(88).setLengths(1);}
    public UmpleSourceData Key_getNoFormatString(){ return new UmpleSourceData().setFileNames("Key.ump").setUmpleLines(132).setJavaLines(172).setLengths(1);}
    public UmpleSourceData Key_compareTo(){ return new UmpleSourceData().setFileNames("Key.ump").setUmpleLines(50).setJavaLines(74).setLengths(5);}
    public UmpleSourceData Key_Key(){ return new UmpleSourceData().setFileNames("Key.ump").setUmpleLines(20).setJavaLines(37).setLengths(6);}
    public UmpleSourceData Key_compareKeys(){ return new UmpleSourceData().setFileNames("Key.ump").setUmpleLines(79).setJavaLines(112).setLengths(5);}
    public UmpleSourceData Key_dumpString(){ return new UmpleSourceData().setFileNames("Key.ump").setUmpleLines(106).setJavaLines(143).setLengths(20);}
    public UmpleSourceData TreeIterator_next(){ return new UmpleSourceData().setFileNames("TreeIterator.ump").setUmpleLines(36).setJavaLines(79).setLengths(23);}
    public UmpleSourceData TreeIterator_hasNext(){ return new UmpleSourceData().setFileNames("TreeIterator.ump").setUmpleLines(23).setJavaLines(52).setLengths(23);}
    public UmpleSourceData TreeIterator_TreeIterator(){ return new UmpleSourceData().setFileNames("TreeIterator.ump").setUmpleLines(16).setJavaLines(39).setLengths(9);}
    public UmpleSourceData TreeIterator_remove(){ return new UmpleSourceData().setFileNames("TreeIterator.ump").setUmpleLines(51).setJavaLines(106).setLengths(1);}
    public UmpleSourceData TreeIterator_advance(){ return new UmpleSourceData().setFileNames("TreeIterator.ump").setUmpleLines(55).setJavaLines(111).setLengths(7);}
    public UmpleSourceData ChildReference_readFromLog(){ return new UmpleSourceData().setFileNames("ChildReference.ump").setUmpleLines(213).setJavaLines(280).setLengths(4);}
    public UmpleSourceData ChildReference_getLogSize(){ return new UmpleSourceData().setFileNames("ChildReference.ump").setUmpleLines(195).setJavaLines(256).setLengths(1);}
    public UmpleSourceData ChildReference_setLsn(){ return new UmpleSourceData().setFileNames("ChildReference.ump").setUmpleLines(148).setJavaLines(191).setLengths(2);}
    public UmpleSourceData ChildReference_isKnownDeleted(){ return new UmpleSourceData().setFileNames("ChildReference.ump").setUmpleLines(163).setJavaLines(212).setLengths(1);}
    public UmpleSourceData ChildReference_getTarget(){ return new UmpleSourceData().setFileNames("ChildReference.ump").setUmpleLines(118).setJavaLines(149).setLengths(1);}
    public UmpleSourceData ChildReference_writeToLog(){ return new UmpleSourceData().setFileNames("ChildReference.ump").setUmpleLines(202).setJavaLines(266).setLengths(5);}
    public UmpleSourceData ChildReference_logEntryIsTransactional(){ return new UmpleSourceData().setFileNames("ChildReference.ump").setUmpleLines(235).setJavaLines(308).setLengths(1);}
    public UmpleSourceData ChildReference_isDirty(){ return new UmpleSourceData().setFileNames("ChildReference.ump").setUmpleLines(170).setJavaLines(222).setLengths(1);}
    public UmpleSourceData ChildReference_clearTarget(){ return new UmpleSourceData().setFileNames("ChildReference.ump").setUmpleLines(132).setJavaLines(169).setLengths(1);}
    public UmpleSourceData ChildReference_getMigrate(){ return new UmpleSourceData().setFileNames("ChildReference.ump").setUmpleLines(177).setJavaLines(232).setLengths(1);}
    public UmpleSourceData ChildReference_setTarget(){ return new UmpleSourceData().setFileNames("ChildReference.ump").setUmpleLines(125).setJavaLines(159).setLengths(1);}
    public UmpleSourceData ChildReference_getLsn(){ return new UmpleSourceData().setFileNames("ChildReference.ump").setUmpleLines(140).setJavaLines(180).setLengths(1);}
    public UmpleSourceData ChildReference_setKey(){ return new UmpleSourceData().setFileNames("ChildReference.ump").setUmpleLines(74).setJavaLines(93).setLengths(2);}
    public UmpleSourceData ChildReference_isPendingDeleted(){ return new UmpleSourceData().setFileNames("ChildReference.ump").setUmpleLines(156).setJavaLines(202).setLengths(1);}
    public UmpleSourceData ChildReference_init(){ return new UmpleSourceData().setFileNames("ChildReference.ump").setUmpleLines(57).setJavaLines(70).setLengths(4);}
    public UmpleSourceData ChildReference_getKey(){ return new UmpleSourceData().setFileNames("ChildReference.ump").setUmpleLines(67).setJavaLines(83).setLengths(1);}
    public UmpleSourceData ChildReference_hook613(){ return new UmpleSourceData().setFileNames("ChildReference.ump").setUmpleLines(282).setJavaLines(361).setLengths(1);}
    public UmpleSourceData ChildReference_dumpString(){ return new UmpleSourceData().setFileNames("ChildReference.ump").setUmpleLines(246).setJavaLines(323).setLengths(29);}
    public UmpleSourceData ChildReference_fetchTarget(){ return new UmpleSourceData().setFileNames("ChildReference.ump").setUmpleLines(85).setJavaLines(107).setLengths(28);}
    public UmpleSourceData ChildReference_ChildReference(){ return new UmpleSourceData().setFileNames("ChildReference.ump","ChildReference.ump","ChildReference.ump").setUmpleLines(39, 46, 53).setJavaLines(45, 55, 65).setLengths(1, 1, 1);}
    public UmpleSourceData ChildReference_getState(){ return new UmpleSourceData().setFileNames("ChildReference.ump").setUmpleLines(111).setJavaLines(139).setLengths(1);}
    public UmpleSourceData ChildReference_getTransactionId(){ return new UmpleSourceData().setFileNames("ChildReference.ump").setUmpleLines(242).setJavaLines(318).setLengths(1);}
    public UmpleSourceData ChildReference_setMigrate(){ return new UmpleSourceData().setFileNames("ChildReference.ump").setUmpleLines(184).setJavaLines(242).setLengths(5);}
    public UmpleSourceData ChildReference_dumpLog(){ return new UmpleSourceData().setFileNames("ChildReference.ump").setUmpleLines(223).setJavaLines(293).setLengths(6);}
    public UmpleSourceData ChildReference_toString(){ return new UmpleSourceData().setFileNames("ChildReference.ump").setUmpleLines(278).setJavaLines(356).setLengths(1);}
    public UmpleSourceData BIN_evictInternal(){ return new UmpleSourceData().setFileNames("Evictor_BIN.ump").setUmpleLines(56).setJavaLines(840).setLengths(7);}
    public UmpleSourceData BIN_evictLNs(){ return new UmpleSourceData().setFileNames("Derivative_Latches_Evictor_BIN.ump","Evictor_BIN.ump").setUmpleLines(10, 29).setJavaLines(797, 795).setLengths(2, 15);}
    public UmpleSourceData BIN_hook608(){ return new UmpleSourceData().setFileNames("BIN.ump").setUmpleLines(575).setJavaLines(741).setLengths(1);}
    public UmpleSourceData BIN_hook606(){ return new UmpleSourceData().setFileNames("BIN.ump").setUmpleLines(547).setJavaLines(709).setLengths(1);}
    public UmpleSourceData BIN_accumulateStats(){ return new UmpleSourceData().setFileNames("BIN.ump").setUmpleLines(467).setJavaLines(608).setLengths(1);}
    public UmpleSourceData BIN_hook605(){ return new UmpleSourceData().setFileNames("BIN.ump").setUmpleLines(544).setJavaLines(704).setLengths(1);}
    public UmpleSourceData BIN_hook604(){ return new UmpleSourceData().setFileNames("BIN.ump").setUmpleLines(541).setJavaLines(699).setLengths(1);}
    public UmpleSourceData BIN_setKnownDeleted(){ return new UmpleSourceData().setFileNames("BIN.ump").setUmpleLines(165).setJavaLines(230).setLengths(9);}
    public UmpleSourceData BIN_getCursorSet(){ return new UmpleSourceData().setFileNames("BIN.ump").setUmpleLines(193).setJavaLines(268).setLengths(1);}
    public UmpleSourceData BIN_getKeyComparator(){ return new UmpleSourceData().setFileNames("BIN.ump").setUmpleLines(474).setJavaLines(618).setLengths(1);}
    public UmpleSourceData BIN_getCursorBINToBeRemoved(){ return new UmpleSourceData().setFileNames("BIN.ump").setUmpleLines(227).setJavaLines(323).setLengths(1);}
    public UmpleSourceData BIN_logInternal(){ return new UmpleSourceData().setFileNames("BIN.ump").setUmpleLines(501).setJavaLines(653).setLengths(21);}
    public UmpleSourceData BIN_removeCursor(){ return new UmpleSourceData().setFileNames("Latches_BIN.ump","BIN.ump").setUmpleLines(40, 209).setJavaLines(296, 294).setLengths(2, 1);}
    public UmpleSourceData BIN_addCursor(){ return new UmpleSourceData().setFileNames("Latches_BIN.ump","BIN.ump").setUmpleLines(31, 201).setJavaLines(281, 279).setLengths(2, 1);}
    public UmpleSourceData BIN_isEvictionProhibited(){ return new UmpleSourceData().setFileNames("BIN.ump").setUmpleLines(135).setJavaLines(191).setLengths(1);}
    public UmpleSourceData BIN_entryZeroKeyComparesLow(){ return new UmpleSourceData().setFileNames("BIN.ump").setUmpleLines(157).setJavaLines(219).setLengths(1);}
    public UmpleSourceData BIN_hasNonLNChildren(){ return new UmpleSourceData().setFileNames("BIN.ump").setUmpleLines(142).setJavaLines(201).setLengths(9);}
    public UmpleSourceData BIN_getChildEvictionType(){ return new UmpleSourceData().setFileNames("Evictor_BIN.ump").setUmpleLines(8).setJavaLines(771).setLengths(14);}
    public UmpleSourceData BIN_createNewInstance(){ return new UmpleSourceData().setFileNames("BIN.ump").setUmpleLines(61).setJavaLines(89).setLengths(1);}
    public UmpleSourceData BIN_compress(){ return new UmpleSourceData().setFileNames("BIN.ump").setUmpleLines(345).setJavaLines(471).setLengths(75);}
    public UmpleSourceData BIN_isValidForDelete(){ return new UmpleSourceData().setFileNames("BIN.ump").setUmpleLines(431).setJavaLines(565).setLengths(39);}
    public UmpleSourceData BIN_createReference(){ return new UmpleSourceData().setFileNames("BIN.ump").setUmpleLines(54).setJavaLines(79).setLengths(1);}
    public UmpleSourceData BIN_setCursorIndex(){ return new UmpleSourceData().setFileNames("BIN.ump").setUmpleLines(239).setJavaLines(338).setLengths(1);}
    public UmpleSourceData BIN_nCursors(){ return new UmpleSourceData().setFileNames("BIN.ump").setUmpleLines(216).setJavaLines(308).setLengths(1);}
    public UmpleSourceData BIN_endTag(){ return new UmpleSourceData().setFileNames("BIN.ump").setUmpleLines(482).setJavaLines(628).setLengths(1);}
    public UmpleSourceData BIN_evictLN(){ return new UmpleSourceData().setFileNames("Evictor_BIN.ump").setUmpleLines(46).setJavaLines(823).setLengths(8);}
    public UmpleSourceData BIN_verifyCursors(){ return new UmpleSourceData().setFileNames("Verifier_BIN.ump").setUmpleLines(8).setJavaLines(856).setLengths(10);}
    public UmpleSourceData BIN_validateSubtreeBeforeDelete(){ return new UmpleSourceData().setFileNames("BIN.ump").setUmpleLines(424).setJavaLines(555).setLengths(1);}
    public UmpleSourceData BIN_getMemoryOverhead(){ return new UmpleSourceData().setFileNames("MemoryBudget_BIN.ump").setUmpleLines(9).setJavaLines(761).setLengths(1);}
    public UmpleSourceData BIN_setProhibitNextDelta(){ return new UmpleSourceData().setFileNames("BIN.ump").setUmpleLines(90).setJavaLines(130).setLengths(1);}
    public UmpleSourceData BIN_setCursorBIN(){ return new UmpleSourceData().setFileNames("BIN.ump").setUmpleLines(235).setJavaLines(333).setLengths(1);}
    public UmpleSourceData BIN_adjustCursors(){ return new UmpleSourceData().setFileNames("Latches_BIN.ump","BIN.ump").setUmpleLines(50, 266).setJavaLines(372, 370).setLengths(3, 29);}
    public UmpleSourceData BIN_descendOnParentSearch(){ return new UmpleSourceData().setFileNames("BIN.ump").setUmpleLines(95).setJavaLines(135).setLengths(42);}
    public UmpleSourceData BIN_getLastDeltaVersion(){ return new UmpleSourceData().setFileNames("BIN.ump").setUmpleLines(82).setJavaLines(119).setLengths(1);}
    public UmpleSourceData BIN_getLogType(){ return new UmpleSourceData().setFileNames("BIN.ump").setUmpleLines(489).setJavaLines(638).setLengths(1);}
    public UmpleSourceData BIN_adjustCursorsForInsert(){ return new UmpleSourceData().setFileNames("Latches_BIN.ump","BIN.ump").setUmpleLines(60, 302).setJavaLines(416, 414).setLengths(2, 12);}
    public UmpleSourceData BIN_clearKnownDeleted(){ return new UmpleSourceData().setFileNames("BIN.ump").setUmpleLines(188).setJavaLines(262).setLengths(2);}
    public UmpleSourceData BIN_doDeltaLog(){ return new UmpleSourceData().setFileNames("BIN.ump").setUmpleLines(529).setJavaLines(684).setLengths(6);}
    public UmpleSourceData BIN_computeOverhead(){ return new UmpleSourceData().setFileNames("MemoryBudget_BIN.ump").setUmpleLines(5).setJavaLines(756).setLengths(1);}
    public UmpleSourceData BIN_splitSpecial(){ return new UmpleSourceData().setFileNames("BIN.ump").setUmpleLines(247).setJavaLines(348).setLengths(11);}
    public UmpleSourceData BIN_setKnownDeletedLeaveTarget(){ return new UmpleSourceData().setFileNames("BIN.ump").setUmpleLines(178).setJavaLines(249).setLengths(3);}
    public UmpleSourceData BIN_shortClassName(){ return new UmpleSourceData().setFileNames("BIN.ump").setUmpleLines(493).setJavaLines(643).setLengths(1);}
    public UmpleSourceData BIN_canBeAncestor(){ return new UmpleSourceData().setFileNames("BIN.ump").setUmpleLines(128).setJavaLines(181).setLengths(1);}
    public UmpleSourceData BIN_getChildKey(){ return new UmpleSourceData().setFileNames("BIN.ump").setUmpleLines(68).setJavaLines(99).setLengths(1);}
    public UmpleSourceData BIN_getBINDeltaType(){ return new UmpleSourceData().setFileNames("BIN.ump").setUmpleLines(75).setJavaLines(109).setLengths(1);}
    public UmpleSourceData BIN_BIN(){ return new UmpleSourceData().setFileNames("BIN.ump","BIN.ump").setUmpleLines(38, 44).setJavaLines(59, 66).setLengths(3, 4);}
    public UmpleSourceData BIN_getCursorIndex(){ return new UmpleSourceData().setFileNames("BIN.ump").setUmpleLines(231).setJavaLines(328).setLengths(1);}
    public UmpleSourceData BIN_hook610(){ return new UmpleSourceData().setFileNames("BIN.ump").setUmpleLines(581).setJavaLines(751).setLengths(1);}
    public UmpleSourceData BIN_isCompressible(){ return new UmpleSourceData().setFileNames("BIN.ump").setUmpleLines(420).setJavaLines(550).setLengths(1);}
    public UmpleSourceData BIN_beginTag(){ return new UmpleSourceData().setFileNames("BIN.ump").setUmpleLines(478).setJavaLines(623).setLengths(1);}
    public UmpleSourceData BIN_getCursorBIN(){ return new UmpleSourceData().setFileNames("BIN.ump").setUmpleLines(223).setJavaLines(318).setLengths(1);}
    public UmpleSourceData BIN_adjustCursorsForMutation(){ return new UmpleSourceData().setFileNames("Latches_BIN.ump","BIN.ump").setUmpleLines(72, 324).setJavaLines(445, 443).setLengths(2, 12);}
    public UmpleSourceData DBIN_getLogType(){ return new UmpleSourceData().setFileNames("DBIN.ump").setUmpleLines(191).setJavaLines(248).setLengths(1);}
    public UmpleSourceData DBIN_readFromLog(){ return new UmpleSourceData().setFileNames("DBIN.ump").setUmpleLines(215).setJavaLines(281).setLengths(2);}
    public UmpleSourceData DBIN_getLogSize(){ return new UmpleSourceData().setFileNames("DBIN.ump").setUmpleLines(198).setJavaLines(258).setLengths(3);}
    public UmpleSourceData DBIN_matchLNByNodeId(){ return new UmpleSourceData().setFileNames("DBIN.ump").setUmpleLines(140).setJavaLines(188).setLengths(13);}
    public UmpleSourceData DBIN_accumulateStats(){ return new UmpleSourceData().setFileNames("DBIN.ump").setUmpleLines(156).setJavaLines(205).setLengths(1);}
    public UmpleSourceData DBIN_computeOverhead(){ return new UmpleSourceData().setFileNames("MemoryBudget_DBIN.ump").setUmpleLines(13).setJavaLines(314).setLengths(1);}
    public UmpleSourceData DBIN_writeToLog(){ return new UmpleSourceData().setFileNames("DBIN.ump").setUmpleLines(207).setJavaLines(270).setLengths(2);}
    public UmpleSourceData DBIN_getDupTreeKey(){ return new UmpleSourceData().setFileNames("DBIN.ump").setUmpleLines(77).setJavaLines(103).setLengths(1);}
    public UmpleSourceData DBIN_getCursorBINToBeRemoved(){ return new UmpleSourceData().setFileNames("DBIN.ump").setUmpleLines(124).setJavaLines(168).setLengths(1);}
    public UmpleSourceData DBIN_shortClassName(){ return new UmpleSourceData().setFileNames("DBIN.ump").setUmpleLines(228).setJavaLines(298).setLengths(1);}
    public UmpleSourceData DBIN_computeMemorySize(){ return new UmpleSourceData().setFileNames("MemoryBudget_DBIN.ump").setUmpleLines(8).setJavaLines(308).setLengths(2);}
    public UmpleSourceData DBIN_canBeAncestor(){ return new UmpleSourceData().setFileNames("DBIN.ump").setUmpleLines(106).setJavaLines(143).setLengths(1);}
    public UmpleSourceData DBIN_getChildKey(){ return new UmpleSourceData().setFileNames("DBIN.ump").setUmpleLines(66).setJavaLines(88).setLengths(1);}
    public UmpleSourceData DBIN_getMainTreeKey(){ return new UmpleSourceData().setFileNames("DBIN.ump").setUmpleLines(84).setJavaLines(113).setLengths(1);}
    public UmpleSourceData DBIN_hasNonLNChildren(){ return new UmpleSourceData().setFileNames("DBIN.ump").setUmpleLines(113).setJavaLines(153).setLengths(1);}
    public UmpleSourceData DBIN_getBINDeltaType(){ return new UmpleSourceData().setFileNames("DBIN.ump").setUmpleLines(98).setJavaLines(133).setLengths(1);}
    public UmpleSourceData DBIN_createNewInstance(){ return new UmpleSourceData().setFileNames("DBIN.ump").setUmpleLines(41).setJavaLines(63).setLengths(1);}
    public UmpleSourceData DBIN_createReference(){ return new UmpleSourceData().setFileNames("DBIN.ump").setUmpleLines(102).setJavaLines(138).setLengths(1);}
    public UmpleSourceData DBIN_setCursorIndex(){ return new UmpleSourceData().setFileNames("DBIN.ump").setUmpleLines(136).setJavaLines(183).setLengths(1);}
    public UmpleSourceData DBIN_endTag(){ return new UmpleSourceData().setFileNames("DBIN.ump").setUmpleLines(164).setJavaLines(215).setLengths(1);}
    public UmpleSourceData DBIN_getCursorIndex(){ return new UmpleSourceData().setFileNames("DBIN.ump").setUmpleLines(128).setJavaLines(173).setLengths(1);}
    public UmpleSourceData DBIN_dumpLogAdditional(){ return new UmpleSourceData().setFileNames("DBIN.ump").setUmpleLines(223).setJavaLines(292).setLengths(2);}
    public UmpleSourceData DBIN_generateLevel(){ return new UmpleSourceData().setFileNames("DBIN.ump").setUmpleLines(45).setJavaLines(68).setLengths(1);}
    public UmpleSourceData DBIN_dumpString(){ return new UmpleSourceData().setFileNames("DBIN.ump").setUmpleLines(172).setJavaLines(226).setLengths(13);}
    public UmpleSourceData DBIN_selectKey(){ return new UmpleSourceData().setFileNames("DBIN.ump").setUmpleLines(70).setJavaLines(93).setLengths(1);}
    public UmpleSourceData DBIN_getMemoryOverhead(){ return new UmpleSourceData().setFileNames("MemoryBudget_DBIN.ump").setUmpleLines(17).setJavaLines(319).setLengths(1);}
    public UmpleSourceData DBIN_getDupKey(){ return new UmpleSourceData().setFileNames("DBIN.ump").setUmpleLines(59).setJavaLines(78).setLengths(1);}
    public UmpleSourceData DBIN_beginTag(){ return new UmpleSourceData().setFileNames("DBIN.ump").setUmpleLines(160).setJavaLines(210).setLengths(1);}
    public UmpleSourceData DBIN_containsDuplicates(){ return new UmpleSourceData().setFileNames("DBIN.ump").setUmpleLines(91).setJavaLines(123).setLengths(1);}
    public UmpleSourceData DBIN_getCursorBIN(){ return new UmpleSourceData().setFileNames("DBIN.ump").setUmpleLines(120).setJavaLines(163).setLengths(1);}
    public UmpleSourceData DBIN_setCursorBIN(){ return new UmpleSourceData().setFileNames("DBIN.ump").setUmpleLines(132).setJavaLines(178).setLengths(1);}
    public UmpleSourceData DBIN_DBIN(){ return new UmpleSourceData().setFileNames("DBIN.ump","DBIN.ump").setUmpleLines(29, 33).setJavaLines(47, 52).setLengths(1, 2);}
    public UmpleSourceData DeltaInfo_getKey(){ return new UmpleSourceData().setFileNames("DeltaInfo.ump").setUmpleLines(71).setJavaLines(104).setLengths(1);}
    public UmpleSourceData DeltaInfo_writeToLog(){ return new UmpleSourceData().setFileNames("DeltaInfo.ump").setUmpleLines(36).setJavaLines(58).setLengths(3);}
    public UmpleSourceData DeltaInfo_logEntryIsTransactional(){ return new UmpleSourceData().setFileNames("DeltaInfo.ump").setUmpleLines(57).setJavaLines(84).setLengths(1);}
    public UmpleSourceData DeltaInfo_getState(){ return new UmpleSourceData().setFileNames("DeltaInfo.ump").setUmpleLines(78).setJavaLines(114).setLengths(1);}
    public UmpleSourceData DeltaInfo_readFromLog(){ return new UmpleSourceData().setFileNames("DeltaInfo.ump").setUmpleLines(42).setJavaLines(65).setLengths(3);}
    public UmpleSourceData DeltaInfo_getTransactionId(){ return new UmpleSourceData().setFileNames("DeltaInfo.ump").setUmpleLines(64).setJavaLines(94).setLengths(1);}
    public UmpleSourceData DeltaInfo_getLogSize(){ return new UmpleSourceData().setFileNames("DeltaInfo.ump").setUmpleLines(32).setJavaLines(53).setLengths(1);}
    public UmpleSourceData DeltaInfo_DeltaInfo(){ return new UmpleSourceData().setFileNames("DeltaInfo.ump","DeltaInfo.ump").setUmpleLines(19, 28).setJavaLines(36, 48).setLengths(3, 1);}
    public UmpleSourceData DeltaInfo_getLsn(){ return new UmpleSourceData().setFileNames("DeltaInfo.ump").setUmpleLines(92).setJavaLines(134).setLengths(1);}
    public UmpleSourceData DeltaInfo_dumpLog(){ return new UmpleSourceData().setFileNames("DeltaInfo.ump").setUmpleLines(48).setJavaLines(72).setLengths(3);}
    public UmpleSourceData DeltaInfo_isKnownDeleted(){ return new UmpleSourceData().setFileNames("DeltaInfo.ump").setUmpleLines(85).setJavaLines(124).setLengths(1);}
    public UmpleSourceData Node_readFromLog(){ return new UmpleSourceData().setFileNames("Node.ump").setUmpleLines(210).setJavaLines(282).setLengths(1);}
    public UmpleSourceData Node_getLogSize(){ return new UmpleSourceData().setFileNames("Node.ump").setUmpleLines(196).setJavaLines(262).setLengths(1);}
    public UmpleSourceData Node_Node(){ return new UmpleSourceData().setFileNames("Node.ump","Node.ump").setUmpleLines(35, 41).setJavaLines(53, 63).setLengths(1, 3);}
    public UmpleSourceData Node_matchLNByNodeId(){ return new UmpleSourceData().setFileNames("Node.ump").setUmpleLines(92).setJavaLines(135).setLengths(1);}
    public UmpleSourceData Node_getNextNodeId(){ return new UmpleSourceData().setFileNames("Node.ump").setUmpleLines(50).setJavaLines(75).setLengths(1);}
    public UmpleSourceData Node_writeToLog(){ return new UmpleSourceData().setFileNames("Node.ump").setUmpleLines(203).setJavaLines(272).setLengths(1);}
    public UmpleSourceData Node_setLastNodeId(){ return new UmpleSourceData().setFileNames("Node.ump").setUmpleLines(26).setJavaLines(41).setLengths(3);}
    public UmpleSourceData Node_verify(){ return new UmpleSourceData().setFileNames("Node.ump").setUmpleLines(75).setJavaLines(110).setLengths(1);}
    public UmpleSourceData Node_dump(){ return new UmpleSourceData().setFileNames("Node.ump").setUmpleLines(143).setJavaLines(191).setLengths(1);}
    public UmpleSourceData Node_postFetchInit(){ return new UmpleSourceData().setFileNames("Node.ump").setUmpleLines(64).setJavaLines(95).setLengths(1);}
    public UmpleSourceData Node_getNodeId(){ return new UmpleSourceData().setFileNames("Node.ump").setUmpleLines(67).setJavaLines(100).setLengths(1);}
    public UmpleSourceData Node_getLastId(){ return new UmpleSourceData().setFileNames("Node.ump").setUmpleLines(57).setJavaLines(85).setLengths(1);}
    public UmpleSourceData Node_endTag(){ return new UmpleSourceData().setFileNames("Node.ump").setUmpleLines(139).setJavaLines(186).setLengths(1);}
    public UmpleSourceData Node_shortDescription(){ return new UmpleSourceData().setFileNames("Node.ump").setUmpleLines(160).setJavaLines(210).setLengths(1);}
    public UmpleSourceData Node_dumpString(){ return new UmpleSourceData().setFileNames("Node.ump").setUmpleLines(147).setJavaLines(196).setLengths(10);}
    public UmpleSourceData Node_getLevel(){ return new UmpleSourceData().setFileNames("Node.ump").setUmpleLines(88).setJavaLines(130).setLengths(1);}
    public UmpleSourceData Node_getMemorySizeIncludedByParent(){ return new UmpleSourceData().setFileNames("Node.ump").setUmpleLines(124).setJavaLines(166).setLengths(1);}
    public UmpleSourceData Node_postLogWork(){ return new UmpleSourceData().setFileNames("Node.ump").setUmpleLines(190).setJavaLines(252).setLengths(1);}
    public UmpleSourceData Node_setNodeId(){ return new UmpleSourceData().setFileNames("Node.ump").setUmpleLines(71).setJavaLines(105).setLengths(1);}
    public UmpleSourceData Node_getType(){ return new UmpleSourceData().setFileNames("Node.ump").setUmpleLines(164).setJavaLines(215).setLengths(1);}
    public UmpleSourceData Node_beginTag(){ return new UmpleSourceData().setFileNames("Node.ump").setUmpleLines(135).setJavaLines(181).setLengths(1);}
    public UmpleSourceData Node_countAsObsoleteWhenLogged(){ return new UmpleSourceData().setFileNames("Node.ump").setUmpleLines(183).setJavaLines(242).setLengths(1);}
    public UmpleSourceData Node_containsDuplicates(){ return new UmpleSourceData().setFileNames("Node.ump").setUmpleLines(81).setJavaLines(120).setLengths(1);}
    public UmpleSourceData Node_toString(){ return new UmpleSourceData().setFileNames("Node.ump").setUmpleLines(131).setJavaLines(176).setLengths(1);}
    public UmpleSourceData Node_dumpLog(){ return new UmpleSourceData().setFileNames("Node.ump").setUmpleLines(217).setJavaLines(292).setLengths(3);}
    public UmpleSourceData Node_marshallOutsideWriteLatch(){ return new UmpleSourceData().setFileNames("Node.ump").setUmpleLines(176).setJavaLines(232).setLengths(1);}
    public UmpleSourceData INDeleteInfo_getDeletedIdKey(){ return new UmpleSourceData().setFileNames("INDeleteInfo.ump").setUmpleLines(41).setJavaLines(65).setLengths(1);}
    public UmpleSourceData INDeleteInfo_getLogType(){ return new UmpleSourceData().setFileNames("INDeleteInfo.ump").setUmpleLines(52).setJavaLines(80).setLengths(1);}
    public UmpleSourceData INDeleteInfo_readFromLog(){ return new UmpleSourceData().setFileNames("INDeleteInfo.ump").setUmpleLines(95).setJavaLines(142).setLengths(3);}
    public UmpleSourceData INDeleteInfo_getLogSize(){ return new UmpleSourceData().setFileNames("INDeleteInfo.ump").setUmpleLines(79).setJavaLines(120).setLengths(1);}
    public UmpleSourceData INDeleteInfo_getDeletedNodeId(){ return new UmpleSourceData().setFileNames("INDeleteInfo.ump").setUmpleLines(37).setJavaLines(60).setLengths(1);}
    public UmpleSourceData INDeleteInfo_getDatabaseId(){ return new UmpleSourceData().setFileNames("INDeleteInfo.ump").setUmpleLines(45).setJavaLines(70).setLengths(1);}
    public UmpleSourceData INDeleteInfo_INDeleteInfo(){ return new UmpleSourceData().setFileNames("INDeleteInfo.ump","INDeleteInfo.ump").setUmpleLines(24, 33).setJavaLines(43, 55).setLengths(3, 1);}
    public UmpleSourceData INDeleteInfo_postLogWork(){ return new UmpleSourceData().setFileNames("INDeleteInfo.ump").setUmpleLines(73).setJavaLines(110).setLengths(1);}
    public UmpleSourceData INDeleteInfo_writeToLog(){ return new UmpleSourceData().setFileNames("INDeleteInfo.ump").setUmpleLines(86).setJavaLines(130).setLengths(3);}
    public UmpleSourceData INDeleteInfo_logEntryIsTransactional(){ return new UmpleSourceData().setFileNames("INDeleteInfo.ump").setUmpleLines(115).setJavaLines(168).setLengths(1);}
    public UmpleSourceData INDeleteInfo_getTransactionId(){ return new UmpleSourceData().setFileNames("INDeleteInfo.ump").setUmpleLines(122).setJavaLines(178).setLengths(1);}
    public UmpleSourceData INDeleteInfo_countAsObsoleteWhenLogged(){ return new UmpleSourceData().setFileNames("INDeleteInfo.ump").setUmpleLines(66).setJavaLines(100).setLengths(1);}
    public UmpleSourceData INDeleteInfo_dumpLog(){ return new UmpleSourceData().setFileNames("INDeleteInfo.ump").setUmpleLines(104).setJavaLines(154).setLengths(5);}
    public UmpleSourceData INDeleteInfo_marshallOutsideWriteLatch(){ return new UmpleSourceData().setFileNames("INDeleteInfo.ump").setUmpleLines(59).setJavaLines(90).setLengths(1);}
    public UmpleSourceData BINReference_getKey(){ return new UmpleSourceData().setFileNames("BINReference.ump").setUmpleLines(32).setJavaLines(50).setLengths(1);}
    public UmpleSourceData BINReference_addDeletedKey(){ return new UmpleSourceData().setFileNames("BINReference.ump").setUmpleLines(40).setJavaLines(60).setLengths(4);}
    public UmpleSourceData BINReference_getNodeId(){ return new UmpleSourceData().setFileNames("BINReference.ump").setUmpleLines(24).setJavaLines(40).setLengths(1);}
    public UmpleSourceData BINReference_deletedKeysExist(){ return new UmpleSourceData().setFileNames("BINReference.ump").setUmpleLines(69).setJavaLines(93).setLengths(1);}
    public UmpleSourceData BINReference_getDatabaseId(){ return new UmpleSourceData().setFileNames("BINReference.ump").setUmpleLines(28).setJavaLines(45).setLengths(1);}
    public UmpleSourceData BINReference_hasDeletedKey(){ return new UmpleSourceData().setFileNames("BINReference.ump").setUmpleLines(65).setJavaLines(88).setLengths(1);}
    public UmpleSourceData BINReference_removeDeletedKey(){ return new UmpleSourceData().setFileNames("BINReference.ump").setUmpleLines(56).setJavaLines(78).setLengths(6);}
    public UmpleSourceData BINReference_addDeletedKeys(){ return new UmpleSourceData().setFileNames("BINReference.ump").setUmpleLines(47).setJavaLines(68).setLengths(6);}
    public UmpleSourceData BINReference_BINReference(){ return new UmpleSourceData().setFileNames("BINReference.ump").setUmpleLines(18).setJavaLines(33).setLengths(3);}
    public UmpleSourceData BINReference_hashCode(){ return new UmpleSourceData().setFileNames("BINReference.ump").setUmpleLines(94).setJavaLines(123).setLengths(1);}
    public UmpleSourceData BINReference_equals(){ return new UmpleSourceData().setFileNames("BINReference.ump").setUmpleLines(84).setJavaLines(112).setLengths(7);}
    public UmpleSourceData BINReference_toString(){ return new UmpleSourceData().setFileNames("BINReference.ump").setUmpleLines(98).setJavaLines(128).setLengths(2);}
    public UmpleSourceData BINReference_getDeletedKeyIterator(){ return new UmpleSourceData().setFileNames("BINReference.ump").setUmpleLines(73).setJavaLines(98).setLengths(5);}
    public UmpleSourceData BINReference_getData(){ return new UmpleSourceData().setFileNames("BINReference.ump").setUmpleLines(36).setJavaLines(55).setLengths(1);}
    public UmpleSourceData TreeLocation_reset(){ return new UmpleSourceData().setFileNames("TreeLocation.ump").setUmpleLines(15).setJavaLines(30).setLengths(4);}
    public UmpleSourceData TreeLocation_toString(){ return new UmpleSourceData().setFileNames("TreeLocation.ump").setUmpleLines(22).setJavaLines(38).setLengths(14);}
    public UmpleSourceData Generation_getNextGeneration(){ return new UmpleSourceData().setFileNames("Generation.ump").setUmpleLines(8).setJavaLines(29).setLengths(1);}
    public UmpleSourceData DBINReference_getKey(){ return new UmpleSourceData().setFileNames("DBINReference.ump").setUmpleLines(15).setJavaLines(40).setLengths(1);}
    public UmpleSourceData DBINReference_DBINReference(){ return new UmpleSourceData().setFileNames("DBINReference.ump").setUmpleLines(10).setJavaLines(34).setLengths(2);}
    public UmpleSourceData DBINReference_toString(){ return new UmpleSourceData().setFileNames("DBINReference.ump").setUmpleLines(23).setJavaLines(50).setLengths(1);}
    public UmpleSourceData DBINReference_getData(){ return new UmpleSourceData().setFileNames("DBINReference.ump").setUmpleLines(19).setJavaLines(45).setLengths(1);}
    public UmpleSourceData SplitRequiredException_SplitRequiredException(){ return new UmpleSourceData().setFileNames("SplitRequiredException.ump").setUmpleLines(7).setJavaLines(30).setLengths(1);}
    public UmpleSourceData NameLN_getTransactionalLogType(){ return new UmpleSourceData().setFileNames("NameLN.ump").setUmpleLines(83).setJavaLines(117).setLengths(1);}
    public UmpleSourceData NameLN_getLogType(){ return new UmpleSourceData().setFileNames("NameLN.ump").setUmpleLines(90).setJavaLines(127).setLengths(1);}
    public UmpleSourceData NameLN_readFromLog(){ return new UmpleSourceData().setFileNames("NameLN.ump").setUmpleLines(113).setJavaLines(159).setLengths(3);}
    public UmpleSourceData NameLN_getLogSize(){ return new UmpleSourceData().setFileNames("NameLN.ump").setUmpleLines(97).setJavaLines(137).setLengths(1);}
    public UmpleSourceData NameLN_endTag(){ return new UmpleSourceData().setFileNames("NameLN.ump").setUmpleLines(61).setJavaLines(91).setLengths(1);}
    public UmpleSourceData NameLN_getId(){ return new UmpleSourceData().setFileNames("NameLN.ump").setUmpleLines(45).setJavaLines(71).setLengths(1);}
    public UmpleSourceData NameLN_dumpLogAdditional(){ return new UmpleSourceData().setFileNames("NameLN.ump").setUmpleLines(122).setJavaLines(171).setLengths(1);}
    public UmpleSourceData NameLN_makeDeleted(){ return new UmpleSourceData().setFileNames("NameLN.ump").setUmpleLines(41).setJavaLines(66).setLengths(1);}
    public UmpleSourceData NameLN_NameLN(){ return new UmpleSourceData().setFileNames("NameLN.ump","NameLN.ump").setUmpleLines(23, 32).setJavaLines(43, 55).setLengths(3, 2);}
    public UmpleSourceData NameLN_dumpString(){ return new UmpleSourceData().setFileNames("NameLN.ump").setUmpleLines(65).setJavaLines(96).setLengths(12);}
    public UmpleSourceData NameLN_writeToLog(){ return new UmpleSourceData().setFileNames("NameLN.ump").setUmpleLines(104).setJavaLines(147).setLengths(3);}
    public UmpleSourceData NameLN_isDeleted(){ return new UmpleSourceData().setFileNames("NameLN.ump").setUmpleLines(37).setJavaLines(61).setLengths(1);}
    public UmpleSourceData NameLN_beginTag(){ return new UmpleSourceData().setFileNames("NameLN.ump").setUmpleLines(57).setJavaLines(86).setLengths(1);}
    public UmpleSourceData NameLN_setId(){ return new UmpleSourceData().setFileNames("NameLN.ump").setUmpleLines(49).setJavaLines(76).setLengths(1);}
    public UmpleSourceData NameLN_toString(){ return new UmpleSourceData().setFileNames("NameLN.ump").setUmpleLines(53).setJavaLines(81).setLengths(1);}
    public UmpleSourceData CursorsExistException_CursorsExistException(){ return new UmpleSourceData().setFileNames("CursorsExistException.ump").setUmpleLines(9).setJavaLines(30).setLengths(1);}
    public UmpleSourceData InconsistentNodeException_InconsistentNodeException(){ return new UmpleSourceData().setFileNames("InconsistentNodeException.ump","InconsistentNodeException.ump").setUmpleLines(8, 12).setJavaLines(35, 40).setLengths(1, 1);}
    public UmpleSourceData DuplicateEntryException_DuplicateEntryException(){ return new UmpleSourceData().setFileNames("DuplicateEntryException.ump","DuplicateEntryException.ump").setUmpleLines(8, 12).setJavaLines(35, 40).setLengths(1, 1);}
    public UmpleSourceData BINDelta_getLogType(){ return new UmpleSourceData().setFileNames("BINDelta.ump").setUmpleLines(105).setJavaLines(145).setLengths(1);}
    public UmpleSourceData BINDelta_readFromLog(){ return new UmpleSourceData().setFileNames("BINDelta.ump").setUmpleLines(126).setJavaLines(175).setLengths(8);}
    public UmpleSourceData BINDelta_getLogSize(){ return new UmpleSourceData().setFileNames("BINDelta.ump").setUmpleLines(137).setJavaLines(187).setLengths(6);}
    public UmpleSourceData BINDelta_getLastFullLsn(){ return new UmpleSourceData().setFileNames("BINDelta.ump").setUmpleLines(69).setJavaLines(99).setLengths(1);}
    public UmpleSourceData BINDelta_BINDelta(){ return new UmpleSourceData().setFileNames("BINDelta.ump","BINDelta.ump").setUmpleLines(31, 46).setJavaLines(49, 67).setLengths(9, 3);}
    public UmpleSourceData BINDelta_reconstituteBIN(){ return new UmpleSourceData().setFileNames("BINDelta.ump").setUmpleLines(76).setJavaLines(109).setLengths(32);}
    public UmpleSourceData BINDelta_getDbId(){ return new UmpleSourceData().setFileNames("BINDelta.ump").setUmpleLines(62).setJavaLines(89).setLengths(1);}
    public UmpleSourceData BINDelta_postLogWork(){ return new UmpleSourceData().setFileNames("BINDelta.ump").setUmpleLines(123).setJavaLines(170).setLengths(1);}
    public UmpleSourceData BINDelta_writeToLog(){ return new UmpleSourceData().setFileNames("BINDelta.ump").setUmpleLines(146).setJavaLines(197).setLengths(7);}
    public UmpleSourceData BINDelta_logEntryIsTransactional(){ return new UmpleSourceData().setFileNames("BINDelta.ump").setUmpleLines(171).setJavaLines(226).setLengths(1);}
    public UmpleSourceData BINDelta_getTransactionId(){ return new UmpleSourceData().setFileNames("BINDelta.ump").setUmpleLines(178).setJavaLines(236).setLengths(1);}
    public UmpleSourceData BINDelta_countAsObsoleteWhenLogged(){ return new UmpleSourceData().setFileNames("BINDelta.ump").setUmpleLines(119).setJavaLines(165).setLengths(1);}
    public UmpleSourceData BINDelta_getNumDeltas(){ return new UmpleSourceData().setFileNames("BINDelta.ump").setUmpleLines(55).setJavaLines(79).setLengths(1);}
    public UmpleSourceData BINDelta_dumpLog(){ return new UmpleSourceData().setFileNames("BINDelta.ump").setUmpleLines(156).setJavaLines(208).setLengths(9);}
    public UmpleSourceData BINDelta_marshallOutsideWriteLatch(){ return new UmpleSourceData().setFileNames("BINDelta.ump").setUmpleLines(112).setJavaLines(155).setLengths(1);}
    public UmpleSourceData SearchResult_SearchResult(){ return new UmpleSourceData().setFileNames("SearchResult.ump").setUmpleLines(16).setJavaLines(29).setLengths(5);}
    public UmpleSourceData SearchResult_toString(){ return new UmpleSourceData().setFileNames("SearchResult.ump").setUmpleLines(24).setJavaLines(38).setLengths(3);}
    public UmpleSourceData INDupDeleteInfo_getLogType(){ return new UmpleSourceData().setFileNames("INDupDeleteInfo.ump").setUmpleLines(59).setJavaLines(86).setLengths(1);}
    public UmpleSourceData INDupDeleteInfo_readFromLog(){ return new UmpleSourceData().setFileNames("INDupDeleteInfo.ump").setUmpleLines(104).setJavaLines(150).setLengths(4);}
    public UmpleSourceData INDupDeleteInfo_getLogSize(){ return new UmpleSourceData().setFileNames("INDupDeleteInfo.ump").setUmpleLines(86).setJavaLines(126).setLengths(2);}
    public UmpleSourceData INDupDeleteInfo_getDeletedNodeId(){ return new UmpleSourceData().setFileNames("INDupDeleteInfo.ump").setUmpleLines(40).setJavaLines(61).setLengths(1);}
    public UmpleSourceData INDupDeleteInfo_getDatabaseId(){ return new UmpleSourceData().setFileNames("INDupDeleteInfo.ump").setUmpleLines(52).setJavaLines(76).setLengths(1);}
    public UmpleSourceData INDupDeleteInfo_postLogWork(){ return new UmpleSourceData().setFileNames("INDupDeleteInfo.ump").setUmpleLines(80).setJavaLines(116).setLengths(1);}
    public UmpleSourceData INDupDeleteInfo_writeToLog(){ return new UmpleSourceData().setFileNames("INDupDeleteInfo.ump").setUmpleLines(94).setJavaLines(137).setLengths(4);}
    public UmpleSourceData INDupDeleteInfo_logEntryIsTransactional(){ return new UmpleSourceData().setFileNames("INDupDeleteInfo.ump").setUmpleLines(126).setJavaLines(178).setLengths(1);}
    public UmpleSourceData INDupDeleteInfo_getDeletedDupKey(){ return new UmpleSourceData().setFileNames("INDupDeleteInfo.ump").setUmpleLines(48).setJavaLines(71).setLengths(1);}
    public UmpleSourceData INDupDeleteInfo_getTransactionId(){ return new UmpleSourceData().setFileNames("INDupDeleteInfo.ump").setUmpleLines(133).setJavaLines(188).setLengths(1);}
    public UmpleSourceData INDupDeleteInfo_getDeletedMainKey(){ return new UmpleSourceData().setFileNames("INDupDeleteInfo.ump").setUmpleLines(44).setJavaLines(66).setLengths(1);}
    public UmpleSourceData INDupDeleteInfo_countAsObsoleteWhenLogged(){ return new UmpleSourceData().setFileNames("INDupDeleteInfo.ump").setUmpleLines(73).setJavaLines(106).setLengths(1);}
    public UmpleSourceData INDupDeleteInfo_INDupDeleteInfo(){ return new UmpleSourceData().setFileNames("INDupDeleteInfo.ump","INDupDeleteInfo.ump").setUmpleLines(26, 36).setJavaLines(43, 56).setLengths(4, 1);}
    public UmpleSourceData INDupDeleteInfo_dumpLog(){ return new UmpleSourceData().setFileNames("INDupDeleteInfo.ump").setUmpleLines(114).setJavaLines(163).setLengths(6);}
    public UmpleSourceData INDupDeleteInfo_marshallOutsideWriteLatch(){ return new UmpleSourceData().setFileNames("INDupDeleteInfo.ump").setUmpleLines(66).setJavaLines(96).setLengths(1);}
    public UmpleSourceData TrackingInfo_TrackingInfo(){ return new UmpleSourceData().setFileNames("TrackingInfo.ump").setUmpleLines(11).setJavaLines(30).setLengths(2);}
    public UmpleSourceData TrackingInfo_toString(){ return new UmpleSourceData().setFileNames("TrackingInfo.ump").setUmpleLines(16).setJavaLines(36).setLengths(1);}
    public UmpleSourceData TreeUtils_dumpByteArray(){ return new UmpleSourceData().setFileNames("TreeUtils.ump").setUmpleLines(16).setJavaLines(39).setLengths(14);}
    public UmpleSourceData TreeUtils_indent(){ return new UmpleSourceData().setFileNames("TreeUtils.ump").setUmpleLines(12).setJavaLines(34).setLengths(1);}
    public UmpleSourceData DupCountLN_decDupCount(){ return new UmpleSourceData().setFileNames("DupCountLN.ump").setUmpleLines(44).setJavaLines(74).setLengths(3);}
    public UmpleSourceData DupCountLN_getTransactionalLogType(){ return new UmpleSourceData().setFileNames("DupCountLN.ump").setUmpleLines(97).setJavaLines(139).setLengths(1);}
    public UmpleSourceData DupCountLN_getLogType(){ return new UmpleSourceData().setFileNames("DupCountLN.ump").setUmpleLines(104).setJavaLines(149).setLengths(1);}
    public UmpleSourceData DupCountLN_readFromLog(){ return new UmpleSourceData().setFileNames("DupCountLN.ump").setUmpleLines(126).setJavaLines(180).setLengths(2);}
    public UmpleSourceData DupCountLN_getLogSize(){ return new UmpleSourceData().setFileNames("DupCountLN.ump").setUmpleLines(111).setJavaLines(159).setLengths(1);}
    public UmpleSourceData DupCountLN_accumulateStats(){ return new UmpleSourceData().setFileNames("Statistics_DupCountLN.ump").setUmpleLines(5).setJavaLines(207).setLengths(1);}
    public UmpleSourceData DupCountLN_endTag(){ return new UmpleSourceData().setFileNames("DupCountLN.ump").setUmpleLines(73).setJavaLines(111).setLengths(1);}
    public UmpleSourceData DupCountLN_dumpLogAdditional(){ return new UmpleSourceData().setFileNames("DupCountLN.ump").setUmpleLines(134).setJavaLines(191).setLengths(2);}
    public UmpleSourceData DupCountLN_getDupCount(){ return new UmpleSourceData().setFileNames("DupCountLN.ump").setUmpleLines(34).setJavaLines(62).setLengths(1);}
    public UmpleSourceData DupCountLN_DupCountLN(){ return new UmpleSourceData().setFileNames("DupCountLN.ump","DupCountLN.ump").setUmpleLines(21, 29).setJavaLines(45, 56).setLengths(2, 2);}
    public UmpleSourceData DupCountLN_dumpString(){ return new UmpleSourceData().setFileNames("DupCountLN.ump").setUmpleLines(77).setJavaLines(116).setLengths(14);}
    public UmpleSourceData DupCountLN_incDupCount(){ return new UmpleSourceData().setFileNames("DupCountLN.ump").setUmpleLines(38).setJavaLines(67).setLengths(3);}
    public UmpleSourceData DupCountLN_getMemorySizeIncludedByParent(){ return new UmpleSourceData().setFileNames("MemoryBudget_DupCountLN.ump").setUmpleLines(8).setJavaLines(202).setLengths(1);}
    public UmpleSourceData DupCountLN_writeToLog(){ return new UmpleSourceData().setFileNames("DupCountLN.ump").setUmpleLines(118).setJavaLines(169).setLengths(2);}
    public UmpleSourceData DupCountLN_isDeleted(){ return new UmpleSourceData().setFileNames("DupCountLN.ump").setUmpleLines(61).setJavaLines(96).setLengths(1);}
    public UmpleSourceData DupCountLN_setDupCount(){ return new UmpleSourceData().setFileNames("DupCountLN.ump").setUmpleLines(50).setJavaLines(81).setLengths(1);}
    public UmpleSourceData DupCountLN_beginTag(){ return new UmpleSourceData().setFileNames("DupCountLN.ump").setUmpleLines(69).setJavaLines(106).setLengths(1);}
    public UmpleSourceData DupCountLN_containsDuplicates(){ return new UmpleSourceData().setFileNames("DupCountLN.ump").setUmpleLines(57).setJavaLines(91).setLengths(1);}
    public UmpleSourceData DupCountLN_toString(){ return new UmpleSourceData().setFileNames("DupCountLN.ump").setUmpleLines(65).setJavaLines(101).setLengths(1);}
    public UmpleSourceData PreloadStatus_PreloadStatus(){ return new UmpleSourceData().setFileNames("PreloadStatus.ump").setUmpleLines(16).setJavaLines(31).setLengths(1);}
    public UmpleSourceData PreloadStatus_toString(){ return new UmpleSourceData().setFileNames("PreloadStatus.ump").setUmpleLines(20).setJavaLines(36).setLengths(1);}
    public UmpleSourceData LockMode_toString(){ return new UmpleSourceData().setFileNames("LockMode.ump").setUmpleLines(38).setJavaLines(34).setLengths(1);}
    public UmpleSourceData LockMode_LockMode(){ return new UmpleSourceData().setFileNames("LockMode.ump").setUmpleLines(34).setJavaLines(29).setLengths(1);}
    public UmpleSourceData PreloadConfig_setMaxMillisecs(){ return new UmpleSourceData().setFileNames("PreloadConfig.ump").setUmpleLines(38).setJavaLines(65).setLengths(1);}
    public UmpleSourceData PreloadConfig_getMaxMillisecs(){ return new UmpleSourceData().setFileNames("PreloadConfig.ump").setUmpleLines(45).setJavaLines(75).setLengths(1);}
    public UmpleSourceData PreloadConfig_setMaxBytes(){ return new UmpleSourceData().setFileNames("PreloadConfig.ump").setUmpleLines(24).setJavaLines(45).setLengths(1);}
    public UmpleSourceData PreloadConfig_setLoadLNs(){ return new UmpleSourceData().setFileNames("PreloadConfig.ump").setUmpleLines(52).setJavaLines(85).setLengths(1);}
    public UmpleSourceData PreloadConfig_getLoadLNs(){ return new UmpleSourceData().setFileNames("PreloadConfig.ump").setUmpleLines(59).setJavaLines(95).setLengths(1);}
    public UmpleSourceData PreloadConfig_getMaxBytes(){ return new UmpleSourceData().setFileNames("PreloadConfig.ump").setUmpleLines(31).setJavaLines(55).setLengths(1);}
    public UmpleSourceData PreloadConfig_cloneConfig(){ return new UmpleSourceData().setFileNames("PreloadConfig.ump").setUmpleLines(66).setJavaLines(105).setLengths(5);}
    public UmpleSourceData PreloadConfig_PreloadConfig(){ return new UmpleSourceData().setFileNames("PreloadConfig.ump").setUmpleLines(18).setJavaLines(35).setLengths(1);}
    public UmpleSourceData ForeignKeyDeleteAction_toString(){ return new UmpleSourceData().setFileNames("ForeignKeyDeleteAction.ump").setUmpleLines(27).setJavaLines(34).setLengths(1);}
    public UmpleSourceData ForeignKeyDeleteAction_ForeignKeyDeleteAction(){ return new UmpleSourceData().setFileNames("ForeignKeyDeleteAction.ump").setUmpleLines(23).setJavaLines(29).setLengths(1);}
    public UmpleSourceData DbInternal_dbGetDatabaseImpl(){ return new UmpleSourceData().setFileNames("DbInternal.ump").setUmpleLines(74).setJavaLines(120).setLengths(1);}
    public UmpleSourceData DbInternal_getEnvironmentShell(){ return new UmpleSourceData().setFileNames("DbInternal.ump").setUmpleLines(202).setJavaLines(301).setLengths(9);}
    public UmpleSourceData DbInternal_dbInvalidate(){ return new UmpleSourceData().setFileNames("DbInternal.ump").setUmpleLines(15).setJavaLines(40).setLengths(1);}
    public UmpleSourceData DbInternal_setCreateUP(){ return new UmpleSourceData().setFileNames("DbInternal.ump").setUmpleLines(95).setJavaLines(150).setLengths(1);}
    public UmpleSourceData DbInternal_setLoadPropertyFile(){ return new UmpleSourceData().setFileNames("DbInternal.ump").setUmpleLines(88).setJavaLines(140).setLengths(1);}
    public UmpleSourceData DbInternal_getSortedCursors(){ return new UmpleSourceData().setFileNames("DbInternal.ump").setUmpleLines(81).setJavaLines(130).setLengths(1);}
    public UmpleSourceData DbInternal_envGetEnvironmentImpl(){ return new UmpleSourceData().setFileNames("DbInternal.ump").setUmpleLines(29).setJavaLines(60).setLengths(1);}
    public UmpleSourceData DbInternal_getCreateUP(){ return new UmpleSourceData().setFileNames("DbInternal.ump").setUmpleLines(102).setJavaLines(160).setLengths(1);}
    public UmpleSourceData DbInternal_retrieveNext(){ return new UmpleSourceData().setFileNames("DbInternal.ump").setUmpleLines(53).setJavaLines(90).setLengths(1);}
    public UmpleSourceData DbInternal_disableParameterValidation(){ return new UmpleSourceData().setFileNames("DbInternal.ump").setUmpleLines(166).setJavaLines(250).setLengths(1);}
    public UmpleSourceData DbInternal_setCheckpointUP(){ return new UmpleSourceData().setFileNames("DbInternal.ump").setUmpleLines(109).setJavaLines(170).setLengths(1);}
    public UmpleSourceData DbInternal_getCheckpointUP(){ return new UmpleSourceData().setFileNames("DbInternal.ump").setUmpleLines(116).setJavaLines(180).setLengths(1);}
    public UmpleSourceData DbInternal_setUseExistingConfig(){ return new UmpleSourceData().setFileNames("DbInternal.ump").setUmpleLines(173).setJavaLines(260).setLengths(1);}
    public UmpleSourceData DbInternal_getLocker(){ return new UmpleSourceData().setFileNames("DbInternal.ump").setUmpleLines(187).setJavaLines(280).setLengths(1);}
    public UmpleSourceData DbInternal_checkImmutablePropsForEquality(){ return new UmpleSourceData().setFileNames("DbInternal.ump").setUmpleLines(152).setJavaLines(230).setLengths(1);}
    public UmpleSourceData DbInternal_dbSetHandleLocker(){ return new UmpleSourceData().setFileNames("DbInternal.ump").setUmpleLines(22).setJavaLines(50).setLengths(1);}
    public UmpleSourceData DbInternal_newCursor(){ return new UmpleSourceData().setFileNames("DbInternal.ump").setUmpleLines(37).setJavaLines(70).setLengths(1);}
    public UmpleSourceData DbInternal_cloneConfig(){ return new UmpleSourceData().setFileNames("DbInternal.ump").setUmpleLines(137).setJavaLines(210).setLengths(1);}
    public UmpleSourceData DbInternal_advanceCursor(){ return new UmpleSourceData().setFileNames("DbInternal.ump").setUmpleLines(60).setJavaLines(100).setLengths(1);}
    public UmpleSourceData DbInternal_databaseConfigValidate(){ return new UmpleSourceData().setFileNames("DbInternal.ump").setUmpleLines(180).setJavaLines(270).setLengths(1);}
    public UmpleSourceData DbInternal_copyMutablePropsTo(){ return new UmpleSourceData().setFileNames("DbInternal.ump").setUmpleLines(159).setJavaLines(240).setLengths(1);}
    public UmpleSourceData DbInternal_getDefaultTxnConfig(){ return new UmpleSourceData().setFileNames("DbInternal.ump").setUmpleLines(194).setJavaLines(290).setLengths(1);}
    public UmpleSourceData DbInternal_setTxnReadCommitted(){ return new UmpleSourceData().setFileNames("DbInternal.ump").setUmpleLines(123).setJavaLines(190).setLengths(1);}
    public UmpleSourceData DbInternal_getCursorImpl(){ return new UmpleSourceData().setFileNames("DbInternal.ump").setUmpleLines(67).setJavaLines(110).setLengths(1);}
    public UmpleSourceData DbInternal_position(){ return new UmpleSourceData().setFileNames("DbInternal.ump").setUmpleLines(45).setJavaLines(80).setLengths(1);}
    public UmpleSourceData DbInternal_getTxnReadCommitted(){ return new UmpleSourceData().setFileNames("DbInternal.ump").setUmpleLines(130).setJavaLines(200).setLengths(1);}
    public UmpleSourceData DbInternal_cloneMutableConfig(){ return new UmpleSourceData().setFileNames("DbInternal.ump").setUmpleLines(144).setJavaLines(220).setLengths(1);}
    public UmpleSourceData TransactionConfig_setSerializableIsolation(){ return new UmpleSourceData().setFileNames("TransactionConfig.ump").setUmpleLines(135).setJavaLines(187).setLengths(1);}
    public UmpleSourceData TransactionConfig_getSerializableIsolation(){ return new UmpleSourceData().setFileNames("TransactionConfig.ump").setUmpleLines(142).setJavaLines(197).setLengths(1);}
    public UmpleSourceData TransactionConfig_getWriteNoSync(){ return new UmpleSourceData().setFileNames("TransactionConfig.ump").setUmpleLines(70).setJavaLines(95).setLengths(1);}
    public UmpleSourceData TransactionConfig_setDirtyRead(){ return new UmpleSourceData().setFileNames("TransactionConfig.ump").setUmpleLines(106).setJavaLines(146).setLengths(1);}
    public UmpleSourceData TransactionConfig_getReadCommitted(){ return new UmpleSourceData().setFileNames("TransactionConfig.ump").setUmpleLines(128).setJavaLines(177).setLengths(1);}
    public UmpleSourceData TransactionConfig_getReadUncommitted(){ return new UmpleSourceData().setFileNames("TransactionConfig.ump").setUmpleLines(98).setJavaLines(135).setLengths(1);}
    public UmpleSourceData TransactionConfig_setReadCommitted(){ return new UmpleSourceData().setFileNames("TransactionConfig.ump").setUmpleLines(121).setJavaLines(167).setLengths(1);}
    public UmpleSourceData TransactionConfig_TransactionConfig(){ return new UmpleSourceData().setFileNames("TransactionConfig.ump").setUmpleLines(29).setJavaLines(35).setLengths(1);}
    public UmpleSourceData TransactionConfig_cloneConfig(){ return new UmpleSourceData().setFileNames("TransactionConfig.ump").setUmpleLines(149).setJavaLines(207).setLengths(5);}
    public UmpleSourceData TransactionConfig_setSync(){ return new UmpleSourceData().setFileNames("TransactionConfig.ump").setUmpleLines(35).setJavaLines(45).setLengths(1);}
    public UmpleSourceData TransactionConfig_getSync(){ return new UmpleSourceData().setFileNames("TransactionConfig.ump").setUmpleLines(42).setJavaLines(55).setLengths(1);}
    public UmpleSourceData TransactionConfig_setWriteNoSync(){ return new UmpleSourceData().setFileNames("TransactionConfig.ump").setUmpleLines(63).setJavaLines(85).setLengths(1);}
    public UmpleSourceData TransactionConfig_setNoSync(){ return new UmpleSourceData().setFileNames("TransactionConfig.ump").setUmpleLines(49).setJavaLines(65).setLengths(1);}
    public UmpleSourceData TransactionConfig_getNoSync(){ return new UmpleSourceData().setFileNames("TransactionConfig.ump").setUmpleLines(56).setJavaLines(75).setLengths(1);}
    public UmpleSourceData TransactionConfig_getNoWait(){ return new UmpleSourceData().setFileNames("TransactionConfig.ump").setUmpleLines(84).setJavaLines(115).setLengths(1);}
    public UmpleSourceData TransactionConfig_setNoWait(){ return new UmpleSourceData().setFileNames("TransactionConfig.ump").setUmpleLines(77).setJavaLines(105).setLengths(1);}
    public UmpleSourceData TransactionConfig_setReadUncommitted(){ return new UmpleSourceData().setFileNames("TransactionConfig.ump").setUmpleLines(91).setJavaLines(125).setLengths(1);}
    public UmpleSourceData TransactionConfig_getDirtyRead(){ return new UmpleSourceData().setFileNames("TransactionConfig.ump").setUmpleLines(114).setJavaLines(157).setLengths(1);}
    public UmpleSourceData DbRecover_usage(){ return new UmpleSourceData().setFileNames("DbRecover.ump").setUmpleLines(50).setJavaLines(76).setLengths(7);}
    public UmpleSourceData DbRecover_main(){ return new UmpleSourceData().setFileNames("DbRecover.ump").setUmpleLines(9).setJavaLines(32).setLengths(38);}
    public UmpleSourceData DbRunAction_doEvict(){ return new UmpleSourceData().setFileNames("Evictor_DbRunAction.ump").setUmpleLines(7).setJavaLines(106).setLengths(1);}
    public UmpleSourceData DbRunAction_usage(){ return new UmpleSourceData().setFileNames("DbRunAction.ump").setUmpleLines(57).setJavaLines(97).setLengths(5);}
    public UmpleSourceData DbRunAction_main_hook847(){ return new UmpleSourceData().setFileNames("DbRunAction_static.ump").setUmpleLines(149).setJavaLines(239).setLengths(1);}
    public UmpleSourceData DbRunAction_main(){ return new UmpleSourceData().setFileNames("DbRunAction.ump").setUmpleLines(28).setJavaLines(63).setLengths(1);}
    public UmpleSourceData DbRunAction_removeAndClean(){ return new UmpleSourceData().setFileNames("DeleteOp_DbRunAction.ump").setUmpleLines(7).setJavaLines(111).setLengths(24);}
    public UmpleSourceData DbRunAction_doEvict_DbRunAction_doEvict(){ return new UmpleSourceData().setFileNames("Evictor_DbRunAction_inner.ump").setUmpleLines(5).setJavaLines(29).setLengths(1);}
    public UmpleSourceData DbRunAction_getSecs(){ return new UmpleSourceData().setFileNames("DbRunAction.ump").setUmpleLines(32).setJavaLines(70).setLengths(1);}
    public UmpleSourceData DbRunAction_preload(){ return new UmpleSourceData().setFileNames("DbRunAction.ump").setUmpleLines(36).setJavaLines(75).setLengths(18);}
    public UmpleSourceData DbRunAction_main_execute(){ return new UmpleSourceData().setFileNames("DbRunAction_static.ump","Evictor_DbRunAction_inner.ump").setUmpleLines(8, 8).setJavaLines(44, 34).setLengths(160, 17);}
    public UmpleSourceData DbRunAction_main_hook841(){ return new UmpleSourceData().setFileNames("DbRunAction_static.ump").setUmpleLines(135).setJavaLines(219).setLengths(1);}
    public UmpleSourceData DbRunAction_main_DbRunAction_main(){ return new UmpleSourceData().setFileNames("DbRunAction_static.ump").setUmpleLines(5).setJavaLines(39).setLengths(1);}
    public UmpleSourceData DbPrintLog_usage(){ return new UmpleSourceData().setFileNames("DbPrintLog.ump").setUmpleLines(103).setJavaLines(133).setLengths(11);}
    public UmpleSourceData DbPrintLog_main(){ return new UmpleSourceData().setFileNames("DbPrintLog.ump").setUmpleLines(44).setJavaLines(71).setLengths(56);}
    public UmpleSourceData DbPrintLog_dump(){ return new UmpleSourceData().setFileNames("DbPrintLog.ump").setUmpleLines(22).setJavaLines(46).setLengths(16);}
    public UmpleSourceData Cursor_getSearchBothRange(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(391).setJavaLines(549).setLengths(7);}
    public UmpleSourceData Cursor_putAllowPhantoms(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(521).setJavaLines(709).setLengths(51);}
    public UmpleSourceData Cursor_checkUpdatesAllowed(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(1031).setJavaLines(1297).setLengths(3);}
    public UmpleSourceData Cursor_getPrevNoDup(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(341).setJavaLines(479).setLengths(11);}
    public UmpleSourceData Cursor_getSearchKey(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(356).setJavaLines(499).setLengths(8);}
    public UmpleSourceData Cursor_checkForInsertion(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(886).setJavaLines(1127).setLengths(92);}
    public UmpleSourceData Cursor_Cursor(){ return new UmpleSourceData().setFileNames("Cursor.ump","Cursor.ump","Cursor.ump","Cursor.ump").setUmpleLines(56, 68, 80, 108).setJavaLines(101, 116, 131, 165).setLengths(6, 6, 4, 10);}
    public UmpleSourceData Cursor_getLast(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(265).setJavaLines(371).setLengths(7);}
    public UmpleSourceData Cursor_searchInternal(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(707).setJavaLines(920).setLengths(14);}
    public UmpleSourceData Cursor_put(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(194).setJavaLines(265).setLengths(10);}
    public UmpleSourceData Cursor_searchExactAndRangeLock(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(666).setJavaLines(876).setLengths(17);}
    public UmpleSourceData Cursor_getCurrentInternal(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(880).setJavaLines(1121).setLengths(2);}
    public UmpleSourceData Cursor_isReadUncommittedMode(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(1022).setJavaLines(1286).setLengths(2);}
    public UmpleSourceData Cursor_getFirst(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(255).setJavaLines(355).setLengths(7);}
    public UmpleSourceData Cursor_deleteNoNotify(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(440).setJavaLines(610).setLengths(36);}
    public UmpleSourceData Cursor_putInternal(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(471).setJavaLines(655).setLengths(16);}
    public UmpleSourceData Cursor_retrieveNext(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(723).setJavaLines(943).setLengths(23);}
    public UmpleSourceData Cursor_checkState(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(1056).setJavaLines(1331).setLengths(2);}
    public UmpleSourceData Cursor_getNextNoDup(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(301).setJavaLines(423).setLengths(11);}
    public UmpleSourceData Cursor_isSerializableIsolation(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(1027).setJavaLines(1292).setLengths(1);}
    public UmpleSourceData Cursor_init(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(88).setJavaLines(139).setLengths(16);}
    public UmpleSourceData Cursor_getDatabaseImpl(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(138).setJavaLines(194).setLengths(1);}
    public UmpleSourceData Cursor_endRead(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(985).setJavaLines(1244).setLengths(12);}
    public UmpleSourceData Cursor_count(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(167).setJavaLines(223).setLengths(6);}
    public UmpleSourceData Cursor_deleteInternal(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(418).setJavaLines(585).setLengths(16);}
    public UmpleSourceData Cursor_checkArgsNoValRequired(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(1040).setJavaLines(1309).setLengths(2);}
    public UmpleSourceData Cursor_getPrev(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(315).setJavaLines(443).setLengths(11);}
    public UmpleSourceData Cursor_trace2_execute(){ return new UmpleSourceData().setFileNames("Derivative_LoggingFinest_LoggingBase_Cursor_inner.ump","LoggingFinest_Cursor_inner.ump","Derivative_LoggingFinest_LoggingBase_Cursor_inner.ump","LoggingFinest_Cursor_inner.ump").setUmpleLines(26, 11, 6, 28).setJavaLines(39, 37, 41, 39).setLengths(10, 1, 16, 1);}
    public UmpleSourceData Cursor_getNextDupAndRangeLock(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(753).setJavaLines(975).setLengths(36);}
    public UmpleSourceData Cursor_getNextDup(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(290).setJavaLines(407).setLengths(7);}
    public UmpleSourceData Cursor_getCurrent(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(245).setJavaLines(339).setLengths(7);}
    public UmpleSourceData Cursor_position(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(574).setJavaLines(769).setLengths(18);}
    public UmpleSourceData Cursor_putCurrent(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(233).setJavaLines(322).setLengths(8);}
    public UmpleSourceData Cursor_putNoOverwrite(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(207).setJavaLines(284).setLengths(10);}
    public UmpleSourceData Cursor_getPrevDup(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(330).setJavaLines(463).setLengths(7);}
    public UmpleSourceData Cursor_delete(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(184).setJavaLines(249).setLengths(7);}
    public UmpleSourceData Cursor_countInternal(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(401).setJavaLines(565).setLengths(11);}
    public UmpleSourceData Cursor_searchAllowPhantoms(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(690).setJavaLines(902).setLengths(9);}
    public UmpleSourceData Cursor_search(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(630).setJavaLines(838).setLengths(29);}
    public UmpleSourceData Cursor_trace(){ return new UmpleSourceData().setFileNames("LoggingFinest_Cursor.ump","LoggingFinest_Cursor.ump").setUmpleLines(8, 15).setJavaLines(1469, 1479).setLengths(1, 1);}
    public UmpleSourceData Cursor_beginRead(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(972).setJavaLines(1228).setLengths(7);}
    public UmpleSourceData Cursor_getLockType(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(1004).setJavaLines(1265).setLengths(12);}
    public UmpleSourceData Cursor_rangeLockCurrentPosition(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(795).setJavaLines(1020).setLengths(42);}
    public UmpleSourceData Cursor_close(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(156).setJavaLines(209).setLengths(5);}
    public UmpleSourceData Cursor_checkEnv(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(1064).setJavaLines(1342).setLengths(1);}
    public UmpleSourceData Cursor_getSearchBoth(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(380).setJavaLines(533).setLengths(7);}
    public UmpleSourceData Cursor_putNoDupData(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(220).setJavaLines(303).setLengths(10);}
    public UmpleSourceData Cursor_getSearchKeyRange(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(368).setJavaLines(516).setLengths(8);}
    public UmpleSourceData Cursor_putNoNotify(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(494).setJavaLines(680).setLengths(19);}
    public UmpleSourceData Cursor_retrieveNextAllowPhantoms(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(838).setJavaLines(1071).setLengths(41);}
    public UmpleSourceData Cursor_getNext(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(275).setJavaLines(387).setLengths(11);}
    public UmpleSourceData Cursor_advanceCursor(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(1000).setJavaLines(1260).setLengths(1);}
    public UmpleSourceData Cursor_checkArgsValRequired(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(1048).setJavaLines(1320).setLengths(2);}
    public UmpleSourceData Cursor_hook25(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(1133).setJavaLines(1412).setLengths(48);}
    public UmpleSourceData Cursor_trace2_Cursor_trace2(){ return new UmpleSourceData().setFileNames("LoggingFinest_Cursor_inner.ump").setUmpleLines(5).setJavaLines(29).setLengths(4);}
    public UmpleSourceData Cursor_trace_Cursor_trace(){ return new UmpleSourceData().setFileNames("LoggingFinest_Cursor_inner.ump").setUmpleLines(20).setJavaLines(29).setLengths(6);}
    public UmpleSourceData Cursor_getDatabase(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(131).setJavaLines(184).setLengths(1);}
    public UmpleSourceData Cursor_setNonCloning(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(149).setJavaLines(199).setLengths(1);}
    public UmpleSourceData Cursor_traceCursorImpl(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(1068).setJavaLines(1347).setLengths(9);}
    public UmpleSourceData Cursor_positionAllowPhantoms(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(599).setJavaLines(796).setLengths(33);}
    public UmpleSourceData Cursor_dup(){ return new UmpleSourceData().setFileNames("Cursor.ump").setUmpleLines(176).setJavaLines(238).setLengths(2);}
    public UmpleSourceData SecondaryDatabase_putNoOverwrite(){ return new UmpleSourceData().setFileNames("SecondaryDatabase.ump").setUmpleLines(312).setJavaLines(396).setLengths(1);}
    public UmpleSourceData SecondaryDatabase_newDbcInstance(){ return new UmpleSourceData().setFileNames("SecondaryDatabase.ump").setUmpleLines(197).setJavaLines(256).setLengths(1);}
    public UmpleSourceData SecondaryDatabase_delete(){ return new UmpleSourceData().setFileNames("SecondaryDatabase.ump").setUmpleLines(204).setJavaLines(266).setLengths(27);}
    public UmpleSourceData SecondaryDatabase_put(){ return new UmpleSourceData().setFileNames("SecondaryDatabase.ump").setUmpleLines(304).setJavaLines(386).setLengths(1);}
    public UmpleSourceData SecondaryDatabase_initNew(){ return new UmpleSourceData().setFileNames("SecondaryDatabase.ump").setUmpleLines(81).setJavaLines(107).setLengths(2);}
    public UmpleSourceData SecondaryDatabase_getPrivateSecondaryConfig(){ return new UmpleSourceData().setFileNames("SecondaryDatabase.ump").setUmpleLines(183).setJavaLines(236).setLengths(1);}
    public UmpleSourceData SecondaryDatabase_notAllowedException(){ return new UmpleSourceData().setFileNames("SecondaryDatabase.ump").setUmpleLines(554).setJavaLines(658).setLengths(1);}
    public UmpleSourceData SecondaryDatabase_trace(){ return new UmpleSourceData().setFileNames("LoggingFinest_SecondaryDatabase.ump").setUmpleLines(8).setJavaLines(668).setLengths(1);}
    public UmpleSourceData SecondaryDatabase_truncate(){ return new UmpleSourceData().setFileNames("SecondaryDatabase.ump").setUmpleLines(335).setJavaLines(427).setLengths(1);}
    public UmpleSourceData SecondaryDatabase_get(){ return new UmpleSourceData().setFileNames("SecondaryDatabase.ump","SecondaryDatabase.ump").setUmpleLines(235, 243).setJavaLines(302, 312).setLengths(1, 23);}
    public UmpleSourceData SecondaryDatabase_getPrimaryDatabase(){ return new UmpleSourceData().setFileNames("SecondaryDatabase.ump").setUmpleLines(169).setJavaLines(216).setLengths(1);}
    public UmpleSourceData SecondaryDatabase_join(){ return new UmpleSourceData().setFileNames("SecondaryDatabase.ump").setUmpleLines(327).setJavaLines(416).setLengths(1);}
    public UmpleSourceData SecondaryDatabase_onForeignKeyDelete(){ return new UmpleSourceData().setFileNames("SecondaryDatabase.ump").setUmpleLines(482).setJavaLines(584).setLengths(64);}
    public UmpleSourceData SecondaryDatabase_close(){ return new UmpleSourceData().setFileNames("SecondaryDatabase.ump").setUmpleLines(140).setJavaLines(178).setLengths(8);}
    public UmpleSourceData SecondaryDatabase_getSearchBoth(){ return new UmpleSourceData().setFileNames("SecondaryDatabase.ump","SecondaryDatabase.ump").setUmpleLines(270, 278).setJavaLines(344, 354).setLengths(1, 23);}
    public UmpleSourceData SecondaryDatabase_trace_SecondaryDatabase_trace(){ return new UmpleSourceData().setFileNames("LoggingFinest_SecondaryDatabase_inner.ump").setUmpleLines(5).setJavaLines(29).setLengths(3);}
    public UmpleSourceData SecondaryDatabase_init(){ return new UmpleSourceData().setFileNames("LoggingFinest_SecondaryDatabase.ump","SecondaryDatabase.ump").setUmpleLines(16, 103).setJavaLines(136, 134).setLengths(2, 31);}
    public UmpleSourceData SecondaryDatabase_SecondaryDatabase(){ return new UmpleSourceData().setFileNames("SecondaryDatabase.ump").setUmpleLines(30).setJavaLines(54).setLengths(44);}
    public UmpleSourceData SecondaryDatabase_putNoDupData(){ return new UmpleSourceData().setFileNames("SecondaryDatabase.ump").setUmpleLines(320).setJavaLines(406).setLengths(1);}
    public UmpleSourceData SecondaryDatabase_clearPrimary(){ return new UmpleSourceData().setFileNames("SecondaryDatabase.ump").setUmpleLines(154).setJavaLines(195).setLengths(2);}
    public UmpleSourceData SecondaryDatabase_getSecondaryConfig(){ return new UmpleSourceData().setFileNames("SecondaryDatabase.ump").setUmpleLines(176).setJavaLines(226).setLengths(1);}
    public UmpleSourceData SecondaryDatabase_deleteKey(){ return new UmpleSourceData().setFileNames("SecondaryDatabase.ump").setUmpleLines(435).setJavaLines(532).setLengths(7);}
    public UmpleSourceData SecondaryDatabase_initExisting(){ return new UmpleSourceData().setFileNames("SecondaryDatabase.ump").setUmpleLines(90).setJavaLines(118).setLengths(7);}
    public UmpleSourceData SecondaryDatabase_trace_execute(){ return new UmpleSourceData().setFileNames("Derivative_LoggingFinest_LoggingBase_SecondaryDatabase_inner.ump","LoggingFinest_SecondaryDatabase_inner.ump").setUmpleLines(6, 10).setJavaLines(38, 36).setLengths(9, 1);}
    public UmpleSourceData SecondaryDatabase_openSecondaryCursor(){ return new UmpleSourceData().setFileNames("SecondaryDatabase.ump").setUmpleLines(190).setJavaLines(246).setLengths(1);}
    public UmpleSourceData SecondaryDatabase_insertKey(){ return new UmpleSourceData().setFileNames("SecondaryDatabase.ump").setUmpleLines(449).setJavaLines(548).setLengths(26);}
    public UmpleSourceData SecondaryDatabase_updateSecondary(){ return new UmpleSourceData().setFileNames("SecondaryDatabase.ump").setUmpleLines(348).setJavaLines(442).setLengths(81);}
    public UmpleSourceData SecondaryDatabase_secondaryCorruptException(){ return new UmpleSourceData().setFileNames("SecondaryDatabase.ump").setUmpleLines(549).setJavaLines(652).setLengths(2);}
    public UmpleSourceData SecondaryDatabase_clearForeignKeyTrigger(){ return new UmpleSourceData().setFileNames("SecondaryDatabase.ump").setUmpleLines(162).setJavaLines(206).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_addToCompressorQueue(){ return new UmpleSourceData().setFileNames("INCompressor_EnvironmentImpl.ump","INCompressor_EnvironmentImpl.ump","INCompressor_EnvironmentImpl.ump").setUmpleLines(18, 27, 36).setJavaLines(1100, 1112, 1124).setLengths(3, 3, 3);}
    public UmpleSourceData EnvironmentImpl_rewriteMapTreeRoot(){ return new UmpleSourceData().setFileNames("Latches_EnvironmentImpl.ump","EnvironmentImpl.ump").setUmpleLines(66, 238).setJavaLines(306, 304).setLengths(1, 11);}
    public UmpleSourceData EnvironmentImpl_shutdownDaemons(){ return new UmpleSourceData().setFileNames("CleanerDaemon_EnvironmentImpl.ump","Evictor_EnvironmentImpl.ump","EnvironmentImpl.ump").setUmpleLines(32, 44, 483).setJavaLines(640, 649, 638).setLengths(6, 2, 1);}
    public UmpleSourceData EnvironmentImpl_getMemoryBudget(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(626).setJavaLines(840).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_EnvironmentImpl(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(115).setJavaLines(124).setLengths(85);}
    public UmpleSourceData EnvironmentImpl_getUtilizationProfile(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(220).setJavaLines(273).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_invokeCleaner(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(464).setJavaLines(594).setLengths(5);}
    public UmpleSourceData EnvironmentImpl_doClose(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(339).setJavaLines(441).setLengths(86);}
    public UmpleSourceData EnvironmentImpl_getConfigManager(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(558).setJavaLines(749).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_truncate(){ return new UmpleSourceData().setFileNames("Truncate_EnvironmentImpl.ump","Truncate_EnvironmentImpl.ump").setUmpleLines(9, 16).setJavaLines(1047, 1057).setLengths(1, 1);}
    public UmpleSourceData EnvironmentImpl_shutdownINCompressor(){ return new UmpleSourceData().setFileNames("INCompressor_EnvironmentImpl.ump").setUmpleLines(66).setJavaLines(1163).setLengths(6);}
    public UmpleSourceData EnvironmentImpl_createDb(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(508).setJavaLines(683).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_dumpMapTree(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(528).setJavaLines(710).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_txnBegin(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(535).setJavaLines(720).setLengths(5);}
    public UmpleSourceData EnvironmentImpl_getLockTimeout(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(648).setJavaLines(870).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_envConfigUpdate(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(187).setJavaLines(218).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_checkImmutablePropsForEquality(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(579).setJavaLines(779).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_getLogger(){ return new UmpleSourceData().setFileNames("loggingBase_EnvironmentImpl.ump").setUmpleLines(27).setJavaLines(997).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_createDaemons_execute(){ return new UmpleSourceData().setFileNames("Evictor_EnvironmentImpl_inner.ump","EnvironmentImpl_static.ump","Derivative_LoggingFileHandler_LoggingBase_EnvironmentImpl_inner.ump","loggingBase_EnvironmentImpl_inner.ump","CheckLeaks_EnvironmentImpl_inner.ump").setUmpleLines(6, 8, 5, 9, 8).setJavaLines(40, 38, 51, 37, 34).setLengths(2, 12, 18, 14, 29);}
    public UmpleSourceData EnvironmentImpl_invokeCompressor(){ return new UmpleSourceData().setFileNames("INCompressor_EnvironmentImpl.ump").setUmpleLines(54).setJavaLines(1148).setLengths(6);}
    public UmpleSourceData EnvironmentImpl_isOpen(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(292).setJavaLines(382).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_isClosing(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(299).setJavaLines(392).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_getLogManager(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(543).setJavaLines(729).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_getRootLsn(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(253).setJavaLines(327).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_checkLeaks(){ return new UmpleSourceData().setFileNames("CheckLeaks_EnvironmentImpl.ump").setUmpleLines(8).setJavaLines(1183).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_getEvictor(){ return new UmpleSourceData().setFileNames("Evictor_EnvironmentImpl.ump").setUmpleLines(23).setJavaLines(1019).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_getDbNames(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(521).setJavaLines(700).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_getINCompressorQueueSize(){ return new UmpleSourceData().setFileNames("INCompressor_EnvironmentImpl.ump").setUmpleLines(75).setJavaLines(1173).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_getThreadLocalReferenceCount(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(435).setJavaLines(559).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_getUtilizationTracker(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(213).setJavaLines(263).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_isReadOnly(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(503).setJavaLines(678).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_alertEvictor(){ return new UmpleSourceData().setFileNames("Evictor_EnvironmentImpl.ump").setUmpleLines(27).setJavaLines(1024).setLengths(3);}
    public UmpleSourceData EnvironmentImpl_verify(){ return new UmpleSourceData().setFileNames("Derivative_Statistics_Verifier_EnvironmentImpl.ump").setUmpleLines(5).setJavaLines(1263).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_invokeCheckpoint(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(455).setJavaLines(584).setLengths(6);}
    public UmpleSourceData EnvironmentImpl_createDaemons_EnvironmentImpl_createDaemons(){ return new UmpleSourceData().setFileNames("EnvironmentImpl_static.ump").setUmpleLines(5).setJavaLines(33).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_close(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump","EnvironmentImpl.ump").setUmpleLines(327, 333).setJavaLines(427, 434).setLengths(3, 3);}
    public UmpleSourceData EnvironmentImpl_requestShutdownDaemons(){ return new UmpleSourceData().setFileNames("CleanerDaemon_EnvironmentImpl.ump","EnvironmentImpl.ump").setUmpleLines(22, 472).setJavaLines(625, 603).setLengths(4, 20);}
    public UmpleSourceData EnvironmentImpl_getCleaner(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(622).setJavaLines(835).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_getFairLatches(){ return new UmpleSourceData().setFileNames("Latches_EnvironmentImpl.ump").setUmpleLines(15).setJavaLines(1211).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_dbRemove(){ return new UmpleSourceData().setFileNames("DeleteOp_EnvironmentImpl.ump").setUmpleLines(8).setJavaLines(1067).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_getINCompressor(){ return new UmpleSourceData().setFileNames("INCompressor_EnvironmentImpl.ump").setUmpleLines(11).setJavaLines(1090).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_getDbMapTree(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(551).setJavaLines(739).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_invokeEvictor(){ return new UmpleSourceData().setFileNames("Evictor_EnvironmentImpl.ump").setUmpleLines(8).setJavaLines(1002).setLengths(3);}
    public UmpleSourceData EnvironmentImpl_loadStats(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentImpl.ump").setUmpleLines(11).setJavaLines(1193).setLengths(14);}
    public UmpleSourceData EnvironmentImpl_isClosed(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(303).setJavaLines(397).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_checkNotClosed(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(321).setJavaLines(420).setLengths(3);}
    public UmpleSourceData EnvironmentImpl_getNoComparators(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(447).setJavaLines(574).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_enableDebugLoggingToDbLog(){ return new UmpleSourceData().setFileNames("Derivative_LoggingDbLogHandler_LoggingBase_EnvironmentImpl.ump").setUmpleLines(8).setJavaLines(1231).setLengths(6);}
    public UmpleSourceData EnvironmentImpl_isNoLocking(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(495).setJavaLines(668).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_checkLeaks_EnvironmentImpl_checkLeaks(){ return new UmpleSourceData().setFileNames("CheckLeaks_EnvironmentImpl_inner.ump").setUmpleLines(5).setJavaLines(29).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_open(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(276).setJavaLines(360).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_cloneMutableConfig(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(572).setJavaLines(769).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_decThreadLocalReferenceCount(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(443).setJavaLines(569).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_getTxnManager(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(614).setJavaLines(825).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_isTransactional(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(499).setJavaLines(673).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_hook328(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(694).setJavaLines(929).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_hook327(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(691).setJavaLines(924).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_hook326(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(688).setJavaLines(919).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_forceLogFileFlip(){ return new UmpleSourceData().setFileNames("Derivative_LoggingFileHandler_LoggingBase_EnvironmentImpl.ump").setUmpleLines(8).setJavaLines(1246).setLengths(2);}
    public UmpleSourceData EnvironmentImpl_shutdownCheckpointer(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(487).setJavaLines(655).setLengths(9);}
    public UmpleSourceData EnvironmentImpl_hook324(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(681).setJavaLines(909).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_hook323(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(678).setJavaLines(904).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_checkIfInvalid(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(314).setJavaLines(412).setLengths(4);}
    public UmpleSourceData EnvironmentImpl_hook322(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(675).setJavaLines(899).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_getCheckpointer(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(618).setJavaLines(830).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_dbRename(){ return new UmpleSourceData().setFileNames("RenameOp_EnvironmentImpl.ump").setUmpleLines(8).setJavaLines(1036).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_shutdownCleaner(){ return new UmpleSourceData().setFileNames("CleanerDaemon_EnvironmentImpl.ump").setUmpleLines(8).setJavaLines(1077).setLengths(4);}
    public UmpleSourceData EnvironmentImpl_setMutableConfig(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(586).setJavaLines(789).setLengths(7);}
    public UmpleSourceData EnvironmentImpl_getFileManager(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(547).setJavaLines(734).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_readMapTreeFromLog(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(260).setJavaLines(337).setLengths(14);}
    public UmpleSourceData EnvironmentImpl_shutdownEvictor(){ return new UmpleSourceData().setFileNames("Evictor_EnvironmentImpl.ump").setUmpleLines(14).setJavaLines(1009).setLengths(6);}
    public UmpleSourceData EnvironmentImpl_initLogger_EnvironmentImpl_initLogger(){ return new UmpleSourceData().setFileNames("loggingBase_EnvironmentImpl_inner.ump").setUmpleLines(5).setJavaLines(31).setLengths(2);}
    public UmpleSourceData EnvironmentImpl_addConfigObserver(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(599).setJavaLines(805).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_getLastRecoveryInfo(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(633).setJavaLines(850).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_runOrPauseDaemons(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(201).setJavaLines(238).setLengths(16);}
    public UmpleSourceData EnvironmentImpl_getTriggerLatch(){ return new UmpleSourceData().setFileNames("Latches_EnvironmentImpl.ump").setUmpleLines(22).setJavaLines(1221).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_removeConfigObserver(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(606).setJavaLines(815).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_hook316(){ return new UmpleSourceData().setFileNames("Derivative_Statistics_MemoryBudget_EnvironmentImpl.ump").setUmpleLines(5).setJavaLines(1252).setLengths(2);}
    public UmpleSourceData EnvironmentImpl_cloneConfig(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(565).setJavaLines(759).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_incReferenceCount(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(431).setJavaLines(554).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_verifyCursors(){ return new UmpleSourceData().setFileNames("Derivative_Verifier_INCompressor_EnvironmentImpl.ump").setUmpleLines(5).setJavaLines(1258).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_getDb(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(517).setJavaLines(695).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_forceClose(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(426).setJavaLines(548).setLengths(2);}
    public UmpleSourceData EnvironmentImpl_closeLogger(){ return new UmpleSourceData().setFileNames("loggingBase_EnvironmentImpl.ump").setUmpleLines(17).setJavaLines(984).setLengths(4);}
    public UmpleSourceData EnvironmentImpl_mayNotWrite(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(310).setJavaLines(407).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_getTxnTimeout(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(644).setJavaLines(865).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_maybeForceYield(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(655).setJavaLines(880).setLengths(4);}
    public UmpleSourceData EnvironmentImpl_createDaemons(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(194).setJavaLines(228).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_closeAfterRunRecovery(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(410).setJavaLines(531).setLengths(13);}
    public UmpleSourceData EnvironmentImpl_incThreadLocalReferenceCount(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(439).setJavaLines(564).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_getEnvironmentHome(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(640).setJavaLines(860).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_logMapTreeRoot(){ return new UmpleSourceData().setFileNames("Latches_EnvironmentImpl.ump","EnvironmentImpl.ump").setUmpleLines(52, 227).setJavaLines(285, 283).setLengths(1, 9);}
    public UmpleSourceData EnvironmentImpl_lazyCompress(){ return new UmpleSourceData().setFileNames("INCompressor_EnvironmentImpl.ump").setUmpleLines(45).setJavaLines(1136).setLengths(3);}
    public UmpleSourceData EnvironmentImpl_invalidate(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(283).setJavaLines(370).setLengths(3);}
    public UmpleSourceData EnvironmentImpl_hook337(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(715).setJavaLines(964).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_hook336(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(712).setJavaLines(959).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_hook335(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(709).setJavaLines(954).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_hook334(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(706).setJavaLines(949).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_hook333(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(703).setJavaLines(944).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_hook331(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(700).setJavaLines(939).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_hook330(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(697).setJavaLines(934).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_initLogger(){ return new UmpleSourceData().setFileNames("loggingBase_EnvironmentImpl.ump").setUmpleLines(10).setJavaLines(974).setLengths(1);}
    public UmpleSourceData EnvironmentImpl_getInMemoryINs(){ return new UmpleSourceData().setFileNames("EnvironmentImpl.ump").setUmpleLines(610).setJavaLines(820).setLengths(1);}
    public UmpleSourceData SortedLSNTreeWalker_extractINsForDb(){ return new UmpleSourceData().setFileNames("SortedLSNTreeWalker.ump").setUmpleLines(59).setJavaLines(64).setLengths(1);}
    public UmpleSourceData SortedLSNTreeWalker_releaseRootIN(){ return new UmpleSourceData().setFileNames("SortedLSNTreeWalker.ump").setUmpleLines(163).setJavaLines(184).setLengths(1);}
    public UmpleSourceData SortedLSNTreeWalker_SortedLSNTreeWalker(){ return new UmpleSourceData().setFileNames("SortedLSNTreeWalker.ump").setUmpleLines(44).setJavaLines(48).setLengths(12);}
    public UmpleSourceData SortedLSNTreeWalker_addToLsnINMap(){ return new UmpleSourceData().setFileNames("SortedLSNTreeWalker.ump").setUmpleLines(166).setJavaLines(189).setLengths(1);}
    public UmpleSourceData SortedLSNTreeWalker_fetchAndProcessLSN(){ return new UmpleSourceData().setFileNames("SortedLSNTreeWalker.ump").setUmpleLines(146).setJavaLines(163).setLengths(7);}
    public UmpleSourceData SortedLSNTreeWalker_hook359(){ return new UmpleSourceData().setFileNames("SortedLSNTreeWalker.ump").setUmpleLines(173).setJavaLines(199).setLengths(1);}
    public UmpleSourceData SortedLSNTreeWalker_walkInternal(){ return new UmpleSourceData().setFileNames("SortedLSNTreeWalker.ump").setUmpleLines(70).setJavaLines(79).setLengths(24);}
    public UmpleSourceData SortedLSNTreeWalker_extractINsForDb_execute(){ return new UmpleSourceData().setFileNames("MemoryBudget_SortedLSNTreeWalker_inner.ump","SortedLSNTreeWalker_static.ump").setUmpleLines(6, 12).setJavaLines(91, 38).setLengths(3, 52);}
    public UmpleSourceData SortedLSNTreeWalker_maybeGetMoreINs(){ return new UmpleSourceData().setFileNames("SortedLSNTreeWalker.ump").setUmpleLines(92).setJavaLines(107).setLengths(18);}
    public UmpleSourceData SortedLSNTreeWalker_fetchLSN(){ return new UmpleSourceData().setFileNames("SortedLSNTreeWalker.ump").setUmpleLines(169).setJavaLines(194).setLengths(1);}
    public UmpleSourceData SortedLSNTreeWalker_extractINsForDb_SortedLSNTreeWalker_extractINsForDb(){ return new UmpleSourceData().setFileNames("SortedLSNTreeWalker_static.ump").setUmpleLines(8).setJavaLines(32).setLengths(2);}
    public UmpleSourceData SortedLSNTreeWalker_getRootIN(){ return new UmpleSourceData().setFileNames("SortedLSNTreeWalker.ump").setUmpleLines(159).setJavaLines(179).setLengths(1);}
    public UmpleSourceData SortedLSNTreeWalker_accumulateLSNs(){ return new UmpleSourceData().setFileNames("SortedLSNTreeWalker.ump").setUmpleLines(113).setJavaLines(129).setLengths(30);}
    public UmpleSourceData SortedLSNTreeWalker_walk(){ return new UmpleSourceData().setFileNames("SortedLSNTreeWalker.ump").setUmpleLines(66).setJavaLines(74).setLengths(1);}
    public UmpleSourceData PreloadLSNTreeWalker_getRootIN(){ return new UmpleSourceData().setFileNames("PreloadLSNTreeWalker.ump").setUmpleLines(27).setJavaLines(55).setLengths(1);}
    public UmpleSourceData PreloadLSNTreeWalker_releaseRootIN(){ return new UmpleSourceData().setFileNames("Latches_PreloadLSNTreeWalker.ump").setUmpleLines(6).setJavaLines(85).setLengths(1);}
    public UmpleSourceData PreloadLSNTreeWalker_addToLsnINMap(){ return new UmpleSourceData().setFileNames("PreloadLSNTreeWalker.ump").setUmpleLines(31).setJavaLines(60).setLengths(2);}
    public UmpleSourceData PreloadWithRootLatched_doWork(){ return new UmpleSourceData().setFileNames("PreloadLSNTreeWalker_static.ump").setUmpleLines(14).setJavaLines(29).setLengths(2);}
    public UmpleSourceData PreloadLSNTreeWalker_PreloadLSNTreeWalker(){ return new UmpleSourceData().setFileNames("PreloadLSNTreeWalker.ump").setUmpleLines(17).setJavaLines(43).setLengths(2);}
    public UmpleSourceData INEntry_INEntry(){ return new UmpleSourceData().setFileNames("PreloadLSNTreeWalker_static.ump").setUmpleLines(5).setJavaLines(61).setLengths(2);}
    public UmpleSourceData PreloadLSNTreeWalker_walk(){ return new UmpleSourceData().setFileNames("PreloadLSNTreeWalker.ump").setUmpleLines(22).setJavaLines(49).setLengths(2);}
    public UmpleSourceData PreloadLSNTreeWalker_fetchLSN(){ return new UmpleSourceData().setFileNames("PreloadLSNTreeWalker.ump").setUmpleLines(36).setJavaLines(66).setLengths(15);}
    public UmpleSourceData DbConfigException_DbConfigException(){ return new UmpleSourceData().setFileNames("DbConfigException.ump","DbConfigException.ump","DbConfigException.ump").setUmpleLines(8, 12, 16).setJavaLines(35, 40, 45).setLengths(1, 1, 1);}
    public UmpleSourceData DbEnvState_DbEnvState(){ return new UmpleSourceData().setFileNames("DbEnvState.ump").setUmpleLines(25).setJavaLines(30).setLengths(1);}
    public UmpleSourceData DbEnvState_checkState(){ return new UmpleSourceData().setFileNames("DbEnvState.ump").setUmpleLines(33).setJavaLines(40).setLengths(13);}
    public UmpleSourceData DbEnvState_toString(){ return new UmpleSourceData().setFileNames("DbEnvState.ump").setUmpleLines(29).setJavaLines(35).setLengths(1);}
    public UmpleSourceData DatabaseId_readFromLog(){ return new UmpleSourceData().setFileNames("DatabaseId.ump").setUmpleLines(103).setJavaLines(151).setLengths(1);}
    public UmpleSourceData DatabaseId_getLogSize(){ return new UmpleSourceData().setFileNames("DatabaseId.ump").setUmpleLines(89).setJavaLines(131).setLengths(1);}
    public UmpleSourceData DatabaseId_DatabaseId(){ return new UmpleSourceData().setFileNames("DatabaseId.ump","DatabaseId.ump").setUmpleLines(20, 27).setJavaLines(41, 51).setLengths(1, 1);}
    public UmpleSourceData DatabaseId_getId(){ return new UmpleSourceData().setFileNames("DatabaseId.ump").setUmpleLines(33).setJavaLines(61).setLengths(1);}
    public UmpleSourceData DatabaseId_compareTo(){ return new UmpleSourceData().setFileNames("DatabaseId.ump").setUmpleLines(72).setJavaLines(111).setLengths(11);}
    public UmpleSourceData DatabaseId_getBytes(){ return new UmpleSourceData().setFileNames("DatabaseId.ump").setUmpleLines(40).setJavaLines(71).setLengths(5);}
    public UmpleSourceData DatabaseId_writeToLog(){ return new UmpleSourceData().setFileNames("DatabaseId.ump").setUmpleLines(96).setJavaLines(141).setLengths(1);}
    public UmpleSourceData DatabaseId_logEntryIsTransactional(){ return new UmpleSourceData().setFileNames("DatabaseId.ump").setUmpleLines(119).setJavaLines(173).setLengths(1);}
    public UmpleSourceData DatabaseId_getTransactionId(){ return new UmpleSourceData().setFileNames("DatabaseId.ump").setUmpleLines(126).setJavaLines(183).setLengths(1);}
    public UmpleSourceData DatabaseId_hashCode(){ return new UmpleSourceData().setFileNames("DatabaseId.ump").setUmpleLines(61).setJavaLines(96).setLengths(1);}
    public UmpleSourceData DatabaseId_equals(){ return new UmpleSourceData().setFileNames("DatabaseId.ump").setUmpleLines(51).setJavaLines(85).setLengths(7);}
    public UmpleSourceData DatabaseId_toString(){ return new UmpleSourceData().setFileNames("DatabaseId.ump").setUmpleLines(65).setJavaLines(101).setLengths(1);}
    public UmpleSourceData DatabaseId_dumpLog(){ return new UmpleSourceData().setFileNames("DatabaseId.ump").setUmpleLines(110).setJavaLines(161).setLengths(3);}
    public UmpleSourceData DbConfigManager_getLong(){ return new UmpleSourceData().setFileNames("DbConfigManager.ump").setUmpleLines(93).setJavaLines(135).setLengths(10);}
    public UmpleSourceData DbConfigManager_getInt(){ return new UmpleSourceData().setFileNames("DbConfigManager.ump").setUmpleLines(75).setJavaLines(114).setLengths(10);}
    public UmpleSourceData DbConfigManager_getShort(){ return new UmpleSourceData().setFileNames("DbConfigManager.ump").setUmpleLines(59).setJavaLines(95).setLengths(8);}
    public UmpleSourceData DbConfigManager_get(){ return new UmpleSourceData().setFileNames("DbConfigManager.ump","DbConfigManager.ump").setUmpleLines(31, 40).setJavaLines(58, 70).setLengths(1, 1);}
    public UmpleSourceData DbConfigManager_DbConfigManager(){ return new UmpleSourceData().setFileNames("DbConfigManager.ump").setUmpleLines(18).setJavaLines(41).setLengths(1);}
    public UmpleSourceData DbConfigManager_getBoolean(){ return new UmpleSourceData().setFileNames("DbConfigManager.ump").setUmpleLines(49).setJavaLines(82).setLengths(2);}
    public UmpleSourceData DbConfigManager_getEnvironmentConfig(){ return new UmpleSourceData().setFileNames("DbConfigManager.ump").setUmpleLines(22).setJavaLines(46).setLengths(1);}
    public UmpleSourceData MemoryBudget_getDINOverhead(){ return new UmpleSourceData().setFileNames("MemoryBudget_MemoryBudget.ump").setUmpleLines(306).setJavaLines(275).setLengths(1);}
    public UmpleSourceData MemoryBudget_sinit(){ return new UmpleSourceData().setFileNames("MemoryBudget.ump").setUmpleLines(26).setJavaLines(51).setLengths(1);}
    public UmpleSourceData MemoryBudget_getDBINOverhead(){ return new UmpleSourceData().setFileNames("MemoryBudget_MemoryBudget.ump").setUmpleLines(310).setJavaLines(280).setLengths(1);}
    public UmpleSourceData MemoryBudget_updateMiscMemoryUsage(){ return new UmpleSourceData().setFileNames("Derivative_Evictor_MemoryBudget_MemoryBudget.ump","MemoryBudget_MemoryBudget.ump").setUmpleLines(27, 251).setJavaLines(201, 196).setLengths(4, 3);}
    public UmpleSourceData MemoryBudget_reset_MemoryBudget_reset(){ return new UmpleSourceData().setFileNames("MemoryBudget_static.ump").setUmpleLines(16).setJavaLines(33).setLengths(2);}
    public UmpleSourceData MemoryBudget_refreshTreeMemoryUsage(){ return new UmpleSourceData().setFileNames("MemoryBudget_MemoryBudget.ump").setUmpleLines(265).setJavaLines(225).setLengths(3);}
    public UmpleSourceData MemoryBudget_getCacheBudget(){ return new UmpleSourceData().setFileNames("MemoryBudget_MemoryBudget.ump").setUmpleLines(294).setJavaLines(260).setLengths(1);}
    public UmpleSourceData MemoryBudget_getCacheMemoryUsage(){ return new UmpleSourceData().setFileNames("MemoryBudget_MemoryBudget.ump").setUmpleLines(271).setJavaLines(232).setLengths(9);}
    public UmpleSourceData MemoryBudget_initCacheMemoryUsage(){ return new UmpleSourceData().setFileNames("Derivative_Latches_MemoryBudget_MemoryBudget.ump","MemoryBudget_MemoryBudget.ump").setUmpleLines(9, 208).setJavaLines(131, 126).setLengths(2, 3);}
    public UmpleSourceData MemoryBudget_getCriticalThreshold(){ return new UmpleSourceData().setFileNames("Derivative_Evictor_MemoryBudget_MemoryBudget.ump").setUmpleLines(7).setJavaLines(299).setLengths(1);}
    public UmpleSourceData MemoryBudget_getLogBufferBudget(){ return new UmpleSourceData().setFileNames("MemoryBudget.ump").setUmpleLines(69).setJavaLines(111).setLengths(1);}
    public UmpleSourceData MemoryBudget_updateLockMemoryUsage(){ return new UmpleSourceData().setFileNames("Derivative_Evictor_MemoryBudget_MemoryBudget.ump","MemoryBudget_MemoryBudget.ump").setUmpleLines(34, 257).setJavaLines(212, 209).setLengths(4, 1);}
    public UmpleSourceData MemoryBudget_accumulateNewUsage(){ return new UmpleSourceData().setFileNames("MemoryBudget_MemoryBudget.ump").setUmpleLines(261).setJavaLines(220).setLengths(1);}
    public UmpleSourceData MemoryBudget_updateTreeMemoryUsage(){ return new UmpleSourceData().setFileNames("Derivative_Evictor_MemoryBudget_MemoryBudget.ump","MemoryBudget_MemoryBudget.ump").setUmpleLines(16, 241).setJavaLines(182, 177).setLengths(4, 3);}
    public UmpleSourceData MemoryBudget_envConfigUpdate(){ return new UmpleSourceData().setFileNames("MemoryBudget.ump").setUmpleLines(41).setJavaLines(76).setLengths(5);}
    public UmpleSourceData MemoryBudget_calcTreeCacheUsage(){ return new UmpleSourceData().setFileNames("MemoryBudget_MemoryBudget.ump").setUmpleLines(217).setJavaLines(142).setLengths(25);}
    public UmpleSourceData MemoryBudget_getINOverhead(){ return new UmpleSourceData().setFileNames("MemoryBudget_MemoryBudget.ump").setUmpleLines(298).setJavaLines(265).setLengths(1);}
    public UmpleSourceData MemoryBudget_getTreeMemoryUsage(){ return new UmpleSourceData().setFileNames("MemoryBudget_MemoryBudget.ump").setUmpleLines(286).setJavaLines(250).setLengths(1);}
    public UmpleSourceData MemoryBudget_sinit_execute(){ return new UmpleSourceData().setFileNames("MemoryBudget_inner_inner.ump","MemoryBudget_inner_inner.ump","MemoryBudget_static.ump","MemoryBudget_inner_inner.ump","MemoryBudget_static.ump").setUmpleLines(7, 12, 5, 105, 20).setJavaLines(33, 41, 31, 88, 39).setLengths(2, 89, 4, 5, 47);}
    public UmpleSourceData MemoryBudget_byteArraySize(){ return new UmpleSourceData().setFileNames("MemoryBudget_MemoryBudget.ump").setUmpleLines(317).setJavaLines(290).setLengths(5);}
    public UmpleSourceData MemoryBudget_getRuntimeMaxMemory(){ return new UmpleSourceData().setFileNames("MemoryBudget.ump").setUmpleLines(59).setJavaLines(100).setLengths(7);}
    public UmpleSourceData MemoryBudget_loadStats(){ return new UmpleSourceData().setFileNames("Derivative_Statistics_MemoryBudget_MemoryBudget.ump").setUmpleLines(5).setJavaLines(304).setLengths(1);}
    public UmpleSourceData MemoryBudget_MemoryBudget(){ return new UmpleSourceData().setFileNames("MemoryBudget.ump").setUmpleLines(30).setJavaLines(56).setLengths(10);}
    public UmpleSourceData MemoryBudget_getTrackerBudget(){ return new UmpleSourceData().setFileNames("MemoryBudget_MemoryBudget.ump").setUmpleLines(290).setJavaLines(255).setLengths(1);}
    public UmpleSourceData MemoryBudget_reset(){ return new UmpleSourceData().setFileNames("MemoryBudget.ump").setUmpleLines(52).setJavaLines(90).setLengths(1);}
    public UmpleSourceData MemoryBudget_getMaxMemory(){ return new UmpleSourceData().setFileNames("MemoryBudget.ump").setUmpleLines(73).setJavaLines(116).setLengths(1);}
    public UmpleSourceData MemoryBudget_getBINOverhead(){ return new UmpleSourceData().setFileNames("MemoryBudget_MemoryBudget.ump").setUmpleLines(302).setJavaLines(270).setLengths(1);}
    public UmpleSourceData PreloadProcessor_processLSN(){ return new UmpleSourceData().setFileNames("PreloadProcessor.ump").setUmpleLines(27).setJavaLines(52).setLengths(28);}
    public UmpleSourceData PreloadProcessor_PreloadProcessor(){ return new UmpleSourceData().setFileNames("PreloadProcessor.ump").setUmpleLines(17).setJavaLines(36).setLengths(7);}
    public UmpleSourceData CursorImpl_releaseDBIN(){ return new UmpleSourceData().setFileNames("Latches_CursorImpl.ump").setUmpleLines(22).setJavaLines(2104).setLengths(3);}
    public UmpleSourceData CursorImpl_setLockerNext(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(1266).setJavaLines(1761).setLengths(1);}
    public UmpleSourceData CursorImpl_put(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(562).setJavaLines(828).setLengths(7);}
    public UmpleSourceData CursorImpl_getCurrentLNAlreadyLatched(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(752).setJavaLines(1075).setLengths(37);}
    public UmpleSourceData CursorImpl_releaseBIN(){ return new UmpleSourceData().setFileNames("Latches_CursorImpl.ump").setUmpleLines(6).setJavaLines(2085).setLengths(3);}
    public UmpleSourceData CursorImpl_removeCursor(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(331).setJavaLines(526).setLengths(2);}
    public UmpleSourceData CursorImpl_latchBIN_CursorImpl_latchBIN(){ return new UmpleSourceData().setFileNames("CursorImpl_static.ump").setUmpleLines(50).setJavaLines(29).setLengths(1);}
    public UmpleSourceData CursorImpl_lockDupCountLN(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(1130).setJavaLines(1562).setLengths(28);}
    public UmpleSourceData CursorImpl_trace_CursorImpl_trace(){ return new UmpleSourceData().setFileNames("LoggingFiner_CursorImpl_inner.ump").setUmpleLines(5).setJavaLines(29).setLengths(8);}
    public UmpleSourceData CursorImpl_getDupBIN(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(218).setJavaLines(393).setLengths(1);}
    public UmpleSourceData CursorImpl_setDupBIN(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(222).setJavaLines(398).setLengths(1);}
    public UmpleSourceData CursorImpl_latchDBIN_CursorImpl_latchDBIN(){ return new UmpleSourceData().setFileNames("CursorImpl_static.ump").setUmpleLines(78).setJavaLines(29).setLengths(1);}
    public UmpleSourceData CursorImpl_lockNextKeyForInsert_CursorImpl_lockNextKeyForInsert(){ return new UmpleSourceData().setFileNames("CursorImpl_static.ump").setUmpleLines(101).setJavaLines(29).setLengths(3);}
    public UmpleSourceData CursorImpl_getLockStats(){ return new UmpleSourceData().setFileNames("Statistics_CursorImpl.ump").setUmpleLines(5).setJavaLines(2080).setLengths(1);}
    public UmpleSourceData CursorImpl_latchDBIN(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(289).setJavaLines(473).setLengths(1);}
    public UmpleSourceData CursorImpl_latchBIN_execute(){ return new UmpleSourceData().setFileNames("CursorImpl_static.ump","CursorImpl_static.ump","CursorImpl_static.ump","CursorImpl_static.ump","CursorImpl_static.ump","Derivative_LoggingFiner_LoggingBase_CursorImpl_inner.ump","LoggingFiner_CursorImpl_inner.ump").setUmpleLines(53, 81, 106, 172, 279, 6, 15).setJavaLines(34, 34, 36, 42, 40, 43, 41).setLengths(20, 8, 27, 130, 87, 17, 1);}
    public UmpleSourceData CursorImpl_hook235(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(1597).setJavaLines(1988).setLengths(46);}
    public UmpleSourceData CursorImpl_incrementLNCount(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(112).setJavaLines(243).setLengths(4);}
    public UmpleSourceData CursorImpl_hook233(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(1538).setJavaLines(1927).setLengths(1);}
    public UmpleSourceData CursorImpl_hook232(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(1536).setJavaLines(1922).setLengths(1);}
    public UmpleSourceData CursorImpl_hook231(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(1534).setJavaLines(1917).setLengths(1);}
    public UmpleSourceData CursorImpl_dumpTree(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(368).setJavaLines(578).setLengths(1);}
    public UmpleSourceData CursorImpl_hook230(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(1532).setJavaLines(1912).setLengths(1);}
    public UmpleSourceData CursorImpl_checkAlreadyLatched(){ return new UmpleSourceData().setFileNames("Latches_CursorImpl.ump").setUmpleLines(28).setJavaLines(2111).setLengths(8);}
    public UmpleSourceData CursorImpl_updateDBin(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(325).setJavaLines(519).setLengths(3);}
    public UmpleSourceData CursorImpl_reset(){ return new UmpleSourceData().setFileNames("Derivative_Evictor_CriticalEviction_CursorImpl.ump","CursorImpl.ump").setUmpleLines(15, 389).setJavaLines(619, 608).setLengths(4, 9);}
    public UmpleSourceData CursorImpl_searchAndPosition(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(987).setJavaLines(1390).setLengths(26);}
    public UmpleSourceData CursorImpl_lockLNDeletedAllowed(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(1091).setJavaLines(1514).setLengths(36);}
    public UmpleSourceData CursorImpl_setBIN(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(202).setJavaLines(373).setLengths(1);}
    public UmpleSourceData CursorImpl_verifyCursor(){ return new UmpleSourceData().setFileNames("Verifier_CursorImpl.ump").setUmpleLines(7).setJavaLines(2073).setLengths(3);}
    public UmpleSourceData CursorImpl_assertCursorState(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(1194).setJavaLines(1660).setLengths(6);}
    public UmpleSourceData CursorImpl_positionFirstOrLast(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(917).setJavaLines(1305).setLengths(76);}
    public UmpleSourceData CursorImpl_revertLock(){ return new UmpleSourceData().setFileNames("CursorImpl.ump","CursorImpl.ump").setUmpleLines(1225, 1232).setJavaLines(1707, 1717).setLengths(1, 5);}
    public UmpleSourceData CursorImpl_statusToString(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(1284).setJavaLines(1786).setLengths(10);}
    public UmpleSourceData CursorImpl_setLockerPrev(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(1262).setJavaLines(1756).setLengths(1);}
    public UmpleSourceData CursorImpl_setTargetBin(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(237).setJavaLines(418).setLengths(13);}
    public UmpleSourceData CursorImpl_lockEofNode(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(1243).setJavaLines(1731).setLengths(1);}
    public UmpleSourceData CursorImpl_fetchCurrent_CursorImpl_fetchCurrent(){ return new UmpleSourceData().setFileNames("CursorImpl_static.ump").setUmpleLines(272).setJavaLines(31).setLengths(5);}
    public UmpleSourceData CursorImpl_getBIN(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(198).setJavaLines(368).setLengths(1);}
    public UmpleSourceData KeyChangeStatus_KeyChangeStatus(){ return new UmpleSourceData().setFileNames("CursorImpl_static.ump").setUmpleLines(44).setJavaLines(28).setLengths(2);}
    public UmpleSourceData CursorImpl_close(){ return new UmpleSourceData().setFileNames("Derivative_Evictor_CriticalEviction_CursorImpl.ump","CursorImpl.ump").setUmpleLines(26, 405).setJavaLines(642, 633).setLengths(4, 7);}
    public UmpleSourceData CursorImpl_releaseBINs(){ return new UmpleSourceData().setFileNames("Latches_CursorImpl.ump").setUmpleLines(17).setJavaLines(2098).setLengths(2);}
    public UmpleSourceData CursorImpl_checkEnv(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(1250).setJavaLines(1741).setLengths(1);}
    public UmpleSourceData CursorImpl_setTestHook(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(1313).setJavaLines(1817).setLengths(1);}
    public UmpleSourceData CursorImpl_setNonCloning(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(119).setJavaLines(251).setLengths(1);}
    public UmpleSourceData CursorImpl_isClosed(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(375).setJavaLines(588).setLengths(1);}
    public UmpleSourceData CursorImpl_getBINToBeRemoved(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(206).setJavaLines(378).setLengths(1);}
    public UmpleSourceData SearchMode_toString(){ return new UmpleSourceData().setFileNames("CursorImpl_static.ump").setUmpleLines(31).setJavaLines(35).setLengths(1);}
    public UmpleSourceData CursorImpl_lockLN(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(1072).setJavaLines(1492).setLengths(10);}
    public UmpleSourceData CursorImpl_flushDBINToBeRemoved(){ return new UmpleSourceData().setFileNames("Latches_CursorImpl.ump","CursorImpl.ump").setUmpleLines(222, 907).setJavaLines(1287, 1285).setLengths(2, 6);}
    public UmpleSourceData CursorImpl_getIndex(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(190).setJavaLines(358).setLengths(1);}
    public UmpleSourceData CursorImpl_getDupBINToBeRemoved(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(226).setJavaLines(403).setLengths(1);}
    public UmpleSourceData CursorImpl_setDupIndex(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(214).setJavaLines(388).setLengths(1);}
    public UmpleSourceData CursorImpl_evict(){ return new UmpleSourceData().setFileNames("Evictor_CursorImpl.ump").setUmpleLines(18).setJavaLines(2053).setLengths(16);}
    public UmpleSourceData CursorImpl_dumpToString(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(1297).setJavaLines(1800).setLengths(13);}
    public UmpleSourceData CursorImpl_getCurrentAlreadyLatched(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(726).setJavaLines(1032).setLengths(15);}
    public UmpleSourceData CursorImpl_addCursor(){ return new UmpleSourceData().setFileNames("CursorImpl.ump","CursorImpl.ump").setUmpleLines(297, 307).setJavaLines(483, 499).setLengths(7, 6);}
    public UmpleSourceData CursorImpl_getCurrentLN(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(739).setJavaLines(1056).setLengths(10);}
    public UmpleSourceData CursorImpl_checkCursorState(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(1206).setJavaLines(1675).setLengths(23);}
    public UmpleSourceData CursorImpl_getLockerPrev(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(1254).setJavaLines(1746).setLengths(1);}
    public UmpleSourceData CursorImpl_getNextNoDup(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(876).setJavaLines(1249).setLengths(6);}
    public UmpleSourceData CursorImpl_getLocker(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(293).setJavaLines(478).setLengths(1);}
    public UmpleSourceData CursorImpl_count(){ return new UmpleSourceData().setFileNames("Latches_CursorImpl.ump","CursorImpl.ump").setUmpleLines(73, 415).setJavaLines(652, 650).setLengths(1, 36);}
    public UmpleSourceData CursorImpl_updateBin(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(316).setJavaLines(509).setLengths(6);}
    public UmpleSourceData CursorImpl_getNextCursorId(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(96).setJavaLines(224).setLengths(1);}
    public UmpleSourceData CursorImpl_putLN(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(542).setJavaLines(803).setLengths(13);}
    public UmpleSourceData CursorImpl_CursorImpl(){ return new UmpleSourceData().setFileNames("CursorImpl.ump","CursorImpl.ump").setUmpleLines(126, 134).setJavaLines(261, 272).setLengths(1, 12);}
    public UmpleSourceData CursorImpl_setIndex(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(194).setJavaLines(363).setLengths(1);}
    public UmpleSourceData CursorImpl_cloneCursor(){ return new UmpleSourceData().setFileNames("CursorImpl.ump","Evictor_CursorImpl.ump","Evictor_CursorImpl.ump","CursorImpl.ump").setUmpleLines(152, 41, 41, 159).setJavaLines(293, 330, 346, 303).setLengths(1, 6, 6, 34);}
    public UmpleSourceData CursorImpl_lockNextKeyForInsert(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(535).setJavaLines(793).setLengths(1);}
    public UmpleSourceData CursorImpl_getCurrent(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(713).setJavaLines(1010).setLengths(13);}
    public UmpleSourceData CursorImpl_getTreeStatsAccumulator(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(104).setJavaLines(234).setLengths(5);}
    public UmpleSourceData CursorImpl_getLatchedDupRoot(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(1154).setJavaLines(1600).setLengths(20);}
    public UmpleSourceData CursorImpl_putCurrent(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(600).setJavaLines(874).setLengths(127);}
    public UmpleSourceData CursorImpl_fetchCurrent(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(1062).setJavaLines(1479).setLengths(1);}
    public UmpleSourceData CursorImpl_putNoOverwrite(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(578).setJavaLines(847).setLengths(2);}
    public UmpleSourceData CursorImpl_clearDupBIN(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(355).setJavaLines(561).setLengths(13);}
    public UmpleSourceData CursorImpl_hook229(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(1530).setJavaLines(1907).setLengths(1);}
    public UmpleSourceData CursorImpl_hook228(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(1528).setJavaLines(1902).setLengths(1);}
    public UmpleSourceData CursorImpl_hook227(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(1526).setJavaLines(1897).setLengths(1);}
    public UmpleSourceData CursorImpl_delete(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(451).setJavaLines(699).setLengths(66);}
    public UmpleSourceData CursorImpl_getNextDuplicate_CursorImpl_getNextDuplicate(){ return new UmpleSourceData().setFileNames("CursorImpl_static.ump").setUmpleLines(164).setJavaLines(32).setLengths(6);}
    public UmpleSourceData CursorImpl_setAllowEviction(){ return new UmpleSourceData().setFileNames("Evictor_CursorImpl.ump").setUmpleLines(10).setJavaLines(2043).setLengths(1);}
    public UmpleSourceData CursorImpl_trace(){ return new UmpleSourceData().setFileNames("LoggingFiner_CursorImpl.ump").setUmpleLines(8).setJavaLines(2128).setLengths(1);}
    public UmpleSourceData CursorImpl_getFirstDuplicate(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(889).setJavaLines(1264).setLengths(7);}
    public UmpleSourceData CursorImpl_hashCode(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(100).setJavaLines(229).setLengths(1);}
    public UmpleSourceData CursorImpl_getDupIndex(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(210).setJavaLines(383).setLengths(1);}
    public UmpleSourceData CursorImpl_dump(){ return new UmpleSourceData().setFileNames("CursorImpl.ump","CursorImpl.ump").setUmpleLines(1273, 1280).setJavaLines(1771, 1781).setLengths(1, 1);}
    public UmpleSourceData CursorImpl_getLockerNext(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(1258).setJavaLines(1751).setLengths(1);}
    public UmpleSourceData CursorImpl_setTreeStatsAccumulator(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(230).setJavaLines(408).setLengths(1);}
    public UmpleSourceData CursorImpl_putNoDupData(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(586).setJavaLines(858).setLengths(6);}
    public UmpleSourceData CursorImpl_getNext(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(781).setJavaLines(1116).setLengths(1);}
    public UmpleSourceData CursorImpl_advanceCursor(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(258).setJavaLines(442).setLengths(20);}
    public UmpleSourceData CursorImpl_hook213(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(1354).setJavaLines(1858).setLengths(31);}
    public UmpleSourceData CursorImpl_flushBINToBeRemoved(){ return new UmpleSourceData().setFileNames("Latches_CursorImpl.ump","CursorImpl.ump").setUmpleLines(212, 869).setJavaLines(1237, 1235).setLengths(2, 6);}
    public UmpleSourceData CursorImpl_getNextDuplicate(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(903).setJavaLines(1280).setLengths(1);}
    public UmpleSourceData CursorImpl_latchBIN(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(281).setJavaLines(466).setLengths(3);}
    public UmpleSourceData CursorImpl_isNotInitialized(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(382).setJavaLines(598).setLengths(1);}
    public UmpleSourceData CursorImpl_setDbt(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(1166).setJavaLines(1629).setLengths(22);}
    public UmpleSourceData CursorImpl_removeCursorBIN(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(336).setJavaLines(532).setLengths(8);}
    public UmpleSourceData CursorImpl_latchBINs(){ return new UmpleSourceData().setFileNames("Latches_CursorImpl.ump").setUmpleLines(12).setJavaLines(2092).setLengths(2);}
    public UmpleSourceData CursorImpl_searchAndPositionBoth(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(1012).setJavaLines(1425).setLengths(50);}
    public UmpleSourceData CursorImpl_removeCursorDBIN(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(344).setJavaLines(544).setLengths(8);}
    public UmpleSourceData CursorImpl_dup(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(519).setJavaLines(774).setLengths(10);}
    public UmpleSourceData SearchMode_SearchMode(){ return new UmpleSourceData().setFileNames("CursorImpl_static.ump").setUmpleLines(12).setJavaLines(28).setLengths(3);}
    public UmpleSourceData CursorImpl_getNextWithKeyChangeStatus(){ return new UmpleSourceData().setFileNames("CursorImpl.ump").setUmpleLines(794).setJavaLines(1131).setLengths(100);}
    public UmpleSourceData RangeRestartException_RangeRestartException(){ return new UmpleSourceData().setFileNames("RangeRestartException.ump").setUmpleLines(8).setJavaLines(35).setLengths(1);}
    public UmpleSourceData DbEnvPool_getExistingEnvironment(){ return new UmpleSourceData().setFileNames("DbEnvPool.ump").setUmpleLines(37).setJavaLines(66).setLengths(1);}
    public UmpleSourceData DbEnvPool_DbEnvPool(){ return new UmpleSourceData().setFileNames("DbEnvPool.ump").setUmpleLines(19).setJavaLines(41).setLengths(1);}
    public UmpleSourceData EnvironmentImplInfo_EnvironmentImplInfo(){ return new UmpleSourceData().setFileNames("DbEnvPool_static.ump").setUmpleLines(7).setJavaLines(28).setLengths(2);}
    public UmpleSourceData DbEnvPool_clear(){ return new UmpleSourceData().setFileNames("DbEnvPool.ump").setUmpleLines(86).setJavaLines(121).setLengths(1);}
    public UmpleSourceData DbEnvPool_getEnvironment(){ return new UmpleSourceData().setFileNames("DbEnvPool.ump","DbEnvPool.ump").setUmpleLines(33, 45).setJavaLines(61, 76).setLengths(1, 31);}
    public UmpleSourceData DbEnvPool_getInstance(){ return new UmpleSourceData().setFileNames("DbEnvPool.ump").setUmpleLines(26).setJavaLines(51).setLengths(1);}
    public UmpleSourceData DbEnvPool_remove(){ return new UmpleSourceData().setFileNames("DbEnvPool.ump").setUmpleLines(82).setJavaLines(116).setLengths(1);}
    public UmpleSourceData DbEnvPool_getEnvironmentMapKey(){ return new UmpleSourceData().setFileNames("DbEnvPool.ump").setUmpleLines(90).setJavaLines(126).setLengths(5);}
    public UmpleSourceData INList_add(){ return new UmpleSourceData().setFileNames("INList.ump").setUmpleLines(46).setJavaLines(87).setLengths(1);}
    public UmpleSourceData INList_tailSet(){ return new UmpleSourceData().setFileNames("Latches_INList.ump","INList.ump").setUmpleLines(109, 76).setJavaLines(143, 141).setLengths(2, 1);}
    public UmpleSourceData INList_INList(){ return new UmpleSourceData().setFileNames("INList.ump","INList.ump").setUmpleLines(19, 28).setJavaLines(43, 60).setLengths(8, 8);}
    public UmpleSourceData INList_addAndSetMemory_INList_addAndSetMemory(){ return new UmpleSourceData().setFileNames("INList_static.ump").setUmpleLines(37).setJavaLines(30).setLengths(3);}
    public UmpleSourceData INList_releaseMinorLatch(){ return new UmpleSourceData().setFileNames("Latches_INList.ump").setUmpleLines(52).setJavaLines(280).setLengths(1);}
    public UmpleSourceData INList_dumpAddedINsIntoMajorSet(){ return new UmpleSourceData().setFileNames("Latches_INList.ump").setUmpleLines(32).setJavaLines(257).setLengths(4);}
    public UmpleSourceData INList_releaseMajorLatchIfHeld(){ return new UmpleSourceData().setFileNames("Latches_INList.ump").setUmpleLines(21).setJavaLines(244).setLengths(3);}
    public UmpleSourceData INList_addAndSetMemory(){ return new UmpleSourceData().setFileNames("INList.ump").setUmpleLines(50).setJavaLines(92).setLengths(1);}
    public UmpleSourceData INList_clear(){ return new UmpleSourceData().setFileNames("Latches_INList.ump","MemoryBudget_INList.ump","INList.ump").setUmpleLines(131, 30, 95).setJavaLines(181, 189, 179).setLengths(4, 10, 2);}
    public UmpleSourceData INList_latchMajor(){ return new UmpleSourceData().setFileNames("Latches_INList.ump").setUmpleLines(16).setJavaLines(238).setLengths(2);}
    public UmpleSourceData INList_latchMinorAndDumpAddedINs(){ return new UmpleSourceData().setFileNames("Latches_INList.ump").setUmpleLines(39).setJavaLines(265).setLengths(6);}
    public UmpleSourceData INList_hook346(){ return new UmpleSourceData().setFileNames("INList.ump").setUmpleLines(126).setJavaLines(228).setLengths(1);}
    public UmpleSourceData INList_add_execute(){ return new UmpleSourceData().setFileNames("INList_static.ump","MemoryBudget_INList_inner.ump","INList_static.ump").setUmpleLines(9, 6, 42).setJavaLines(35, 41, 37).setLengths(48, 6, 2);}
    public UmpleSourceData INList_remove(){ return new UmpleSourceData().setFileNames("Latches_INList.ump","INList.ump").setUmpleLines(99, 68).setJavaLines(128, 126).setLengths(2, 7);}
    public UmpleSourceData INList_removeLatchAlreadyHeld(){ return new UmpleSourceData().setFileNames("Latches_INList.ump","INList.ump").setUmpleLines(78, 57).setJavaLines(104, 102).setLengths(2, 10);}
    public UmpleSourceData INList_getINs(){ return new UmpleSourceData().setFileNames("INList.ump").setUmpleLines(35).setJavaLines(72).setLengths(1);}
    public UmpleSourceData INList_latchMinor(){ return new UmpleSourceData().setFileNames("Latches_INList.ump").setUmpleLines(48).setJavaLines(275).setLengths(1);}
    public UmpleSourceData INList_getSize(){ return new UmpleSourceData().setFileNames("INList.ump").setUmpleLines(39).setJavaLines(77).setLengths(1);}
    public UmpleSourceData INList_iterator(){ return new UmpleSourceData().setFileNames("Latches_INList.ump","INList.ump").setUmpleLines(123, 88).setJavaLines(167, 165).setLengths(2, 1);}
    public UmpleSourceData INList_releaseMajorLatch(){ return new UmpleSourceData().setFileNames("Latches_INList.ump").setUmpleLines(27).setJavaLines(251).setLengths(2);}
    public UmpleSourceData INList_add_INList_add(){ return new UmpleSourceData().setFileNames("INList_static.ump").setUmpleLines(5).setJavaLines(29).setLengths(2);}
    public UmpleSourceData INList_dump(){ return new UmpleSourceData().setFileNames("INList.ump").setUmpleLines(100).setJavaLines(203).setLengths(7);}
    public UmpleSourceData INList_first(){ return new UmpleSourceData().setFileNames("Latches_INList.ump","INList.ump").setUmpleLines(114, 80).setJavaLines(152, 150).setLengths(2, 1);}
    public UmpleSourceData GetMode_GetMode(){ return new UmpleSourceData().setFileNames("GetMode.ump").setUmpleLines(22).setJavaLines(29).setLengths(2);}
    public UmpleSourceData GetMode_toString(){ return new UmpleSourceData().setFileNames("GetMode.ump").setUmpleLines(31).setJavaLines(35).setLengths(1);}
    public UmpleSourceData DatabaseImpl_addReferringHandle(){ return new UmpleSourceData().setFileNames("DatabaseImpl.ump").setUmpleLines(270).setJavaLines(332).setLengths(1);}
    public UmpleSourceData DatabaseImpl_getBinMaxDeltas(){ return new UmpleSourceData().setFileNames("DatabaseImpl.ump").setUmpleLines(526).setJavaLines(634).setLengths(1);}
    public UmpleSourceData DatabaseImpl_getName(){ return new UmpleSourceData().setFileNames("DatabaseImpl.ump").setUmpleLines(301).setJavaLines(373).setLengths(1);}
    public UmpleSourceData DatabaseImpl_getLogSize(){ return new UmpleSourceData().setFileNames("DatabaseImpl.ump").setUmpleLines(415).setJavaLines(500).setLengths(3);}
    public UmpleSourceData DatabaseImpl_isTransactional(){ return new UmpleSourceData().setFileNames("DatabaseImpl.ump").setUmpleLines(188).setJavaLines(218).setLengths(1);}
    public UmpleSourceData DatabaseImpl_serializeComparator(){ return new UmpleSourceData().setFileNames("DatabaseImpl.ump").setUmpleLines(498).setJavaLines(601).setLengths(5);}
    public UmpleSourceData HaltPreloadException_getStatus(){ return new UmpleSourceData().setFileNames("DatabaseImpl_static.ump").setUmpleLines(35).setJavaLines(35).setLengths(1);}
    public UmpleSourceData DatabaseImpl_preload(){ return new UmpleSourceData().setFileNames("DatabaseImpl.ump").setUmpleLines(388).setJavaLines(469).setLengths(1);}
    public UmpleSourceData HaltPreloadException_HaltPreloadException(){ return new UmpleSourceData().setFileNames("DatabaseImpl_static.ump").setUmpleLines(31).setJavaLines(29).setLengths(2);}
    public UmpleSourceData DatabaseImpl_logEntryIsTransactional(){ return new UmpleSourceData().setFileNames("DatabaseImpl.ump").setUmpleLines(484).setJavaLines(581).setLengths(1);}
    public UmpleSourceData DatabaseImpl_getEofNodeId(){ return new UmpleSourceData().setFileNames("DatabaseImpl.ump").setUmpleLines(181).setJavaLines(208).setLengths(1);}
    public UmpleSourceData DatabaseImpl_walkDatabaseTree(){ return new UmpleSourceData().setFileNames("DatabaseImpl.ump").setUmpleLines(316).setJavaLines(391).setLengths(40);}
    public UmpleSourceData DatabaseImpl_getSortedDuplicates(){ return new UmpleSourceData().setFileNames("DatabaseImpl.ump").setUmpleLines(202).setJavaLines(238).setLengths(1);}
    public UmpleSourceData DatabaseImpl_setId(){ return new UmpleSourceData().setFileNames("DatabaseImpl.ump").setUmpleLines(177).setJavaLines(203).setLengths(1);}
    public UmpleSourceData LNCounter_getCount(){ return new UmpleSourceData().setFileNames("DatabaseImpl_static.ump").setUmpleLines(24).setJavaLines(37).setLengths(1);}
    public UmpleSourceData DatabaseImpl_hasOpenHandles(){ return new UmpleSourceData().setFileNames("DatabaseImpl.ump").setUmpleLines(263).setJavaLines(322).setLengths(1);}
    public UmpleSourceData DatabaseImpl_isDeleteFinished(){ return new UmpleSourceData().setFileNames("DeleteOp_DatabaseImpl.ump").setUmpleLines(19).setJavaLines(644).setLengths(1);}
    public UmpleSourceData DatabaseImpl_getDbEnvironment(){ return new UmpleSourceData().setFileNames("DatabaseImpl.ump").setUmpleLines(256).setJavaLines(312).setLengths(1);}
    public UmpleSourceData DatabaseImpl_getNodeMaxDupTreeEntries(){ return new UmpleSourceData().setFileNames("DatabaseImpl.ump").setUmpleLines(210).setJavaLines(248).setLengths(1);}
    public UmpleSourceData DatabaseImpl_setTransactional(){ return new UmpleSourceData().setFileNames("DatabaseImpl.ump").setUmpleLines(195).setJavaLines(228).setLengths(1);}
    public UmpleSourceData DatabaseImpl_preload_execute(){ return new UmpleSourceData().setFileNames("DatabaseImpl_static.ump").setUmpleLines(44).setJavaLines(38).setLengths(41);}
    public UmpleSourceData DatabaseImpl_countRecords(){ return new UmpleSourceData().setFileNames("DatabaseImpl.ump").setUmpleLines(308).setJavaLines(383).setLengths(4);}
    public UmpleSourceData DatabaseImpl_DatabaseImpl(){ return new UmpleSourceData().setFileNames("DatabaseImpl.ump","DatabaseImpl.ump").setUmpleLines(94, 115).setJavaLines(109, 136).setLengths(18, 10);}
    public UmpleSourceData DatabaseImpl_findPrimaryDatabase(){ return new UmpleSourceData().setFileNames("DatabaseImpl.ump").setUmpleLines(291).setJavaLines(362).setLengths(7);}
    public UmpleSourceData DatabaseImpl_getEmptyStats(){ return new UmpleSourceData().setFileNames("Statistics_DatabaseImpl.ump").setUmpleLines(29).setJavaLines(722).setLengths(1);}
    public UmpleSourceData DatabaseImpl_deleteAndReleaseINs(){ return new UmpleSourceData().setFileNames("DeleteOp_DatabaseImpl.ump").setUmpleLines(36).setJavaLines(666).setLengths(2);}
    public UmpleSourceData DatabaseImpl_getBtreeComparator(){ return new UmpleSourceData().setFileNames("DatabaseImpl.ump").setUmpleLines(233).setJavaLines(280).setLengths(1);}
    public UmpleSourceData DatabaseImpl_removeReferringHandle(){ return new UmpleSourceData().setFileNames("DatabaseImpl.ump").setUmpleLines(277).setJavaLines(342).setLengths(1);}
    public UmpleSourceData DatabaseImpl_readFromLog(){ return new UmpleSourceData().setFileNames("DatabaseImpl.ump").setUmpleLines(437).setJavaLines(528).setLengths(23);}
    public UmpleSourceData DatabaseImpl_preload_DatabaseImpl_preload(){ return new UmpleSourceData().setFileNames("DatabaseImpl_static.ump").setUmpleLines(40).setJavaLines(32).setLengths(2);}
    public UmpleSourceData DatabaseImpl_getId(){ return new UmpleSourceData().setFileNames("DatabaseImpl.ump").setUmpleLines(173).setJavaLines(198).setLengths(1);}
    public UmpleSourceData DatabaseImpl_setEnvironmentImpl(){ return new UmpleSourceData().setFileNames("DatabaseImpl.ump").setUmpleLines(247).setJavaLines(300).setLengths(3);}
    public UmpleSourceData DatabaseImpl_getDebugName(){ return new UmpleSourceData().setFileNames("DatabaseImpl.ump").setUmpleLines(129).setJavaLines(155).setLengths(1);}
    public UmpleSourceData DatabaseImpl_instantiateComparator(){ return new UmpleSourceData().setFileNames("DatabaseImpl.ump").setUmpleLines(509).setJavaLines(615).setLengths(10);}
    public UmpleSourceData DatabaseImpl_writeToLog(){ return new UmpleSourceData().setFileNames("DatabaseImpl.ump").setUmpleLines(424).setJavaLines(512).setLengths(7);}
    public UmpleSourceData DatabaseImpl_isDeleted(){ return new UmpleSourceData().setFileNames("DeleteOp_DatabaseImpl.ump").setUmpleLines(15).setJavaLines(639).setLengths(1);}
    public UmpleSourceData DatabaseImpl_initDefaultSettings(){ return new UmpleSourceData().setFileNames("DatabaseImpl.ump").setUmpleLines(140).setJavaLines(170).setLengths(9);}
    public UmpleSourceData DatabaseImpl_verify(){ return new UmpleSourceData().setFileNames("Derivative_Statistics_Verifier_DatabaseImpl.ump").setUmpleLines(5).setJavaLines(727).setLengths(19);}
    public UmpleSourceData DatabaseImpl_setDuplicateComparator(){ return new UmpleSourceData().setFileNames("DatabaseImpl.ump").setUmpleLines(218).setJavaLines(259).setLengths(1);}
    public UmpleSourceData DatabaseImpl_checkIsDeleted(){ return new UmpleSourceData().setFileNames("DeleteOp_DatabaseImpl.ump").setUmpleLines(63).setJavaLines(695).setLengths(3);}
    public UmpleSourceData DatabaseImpl_stat(){ return new UmpleSourceData().setFileNames("Statistics_DatabaseImpl.ump").setUmpleLines(10).setJavaLines(702).setLengths(16);}
    public UmpleSourceData DatabaseImpl_printErrorRecord(){ return new UmpleSourceData().setFileNames("DatabaseImpl.ump").setUmpleLines(362).setJavaLines(440).setLengths(20);}
    public UmpleSourceData DatabaseImpl_setDebugDatabaseName(){ return new UmpleSourceData().setFileNames("DatabaseImpl.ump").setUmpleLines(125).setJavaLines(150).setLengths(1);}
    public UmpleSourceData DatabaseImpl_getNodeMaxEntries(){ return new UmpleSourceData().setFileNames("DatabaseImpl.ump").setUmpleLines(206).setJavaLines(243).setLengths(1);}
    public UmpleSourceData DatabaseImpl_getReferringHandleCount(){ return new UmpleSourceData().setFileNames("DatabaseImpl.ump").setUmpleLines(284).setJavaLines(352).setLengths(1);}
    public UmpleSourceData DatabaseImpl_releaseDeletedINs(){ return new UmpleSourceData().setFileNames("DeleteOp_DatabaseImpl.ump").setUmpleLines(41).setJavaLines(672).setLengths(19);}
    public UmpleSourceData DatabaseImpl_getDuplicateComparator(){ return new UmpleSourceData().setFileNames("DatabaseImpl.ump").setUmpleLines(240).setJavaLines(290).setLengths(1);}
    public UmpleSourceData DatabaseImpl_setBtreeComparator(){ return new UmpleSourceData().setFileNames("DatabaseImpl.ump").setUmpleLines(226).setJavaLines(270).setLengths(1);}
    public UmpleSourceData DatabaseImpl_dumpString(){ return new UmpleSourceData().setFileNames("DatabaseImpl.ump").setUmpleLines(392).setJavaLines(474).setLengths(17);}
    public UmpleSourceData DatabaseImpl_startDeleteProcessing(){ return new UmpleSourceData().setFileNames("DeleteOp_DatabaseImpl.ump").setUmpleLines(23).setJavaLines(649).setLengths(2);}
    public UmpleSourceData ObsoleteProcessor_processLSN(){ return new UmpleSourceData().setFileNames("DatabaseImpl_static.ump","DatabaseImpl_static.ump").setUmpleLines(10, 18).setJavaLines(34, 29).setLengths(2, 4);}
    public UmpleSourceData DatabaseImpl_getTransactionId(){ return new UmpleSourceData().setFileNames("DatabaseImpl.ump").setUmpleLines(491).setJavaLines(591).setLengths(1);}
    public UmpleSourceData DatabaseImpl_clone(){ return new UmpleSourceData().setFileNames("DatabaseImpl.ump").setUmpleLines(155).setJavaLines(188).setLengths(1);}
    public UmpleSourceData DatabaseImpl_setPendingDeletedHook(){ return new UmpleSourceData().setFileNames("DatabaseImpl.ump").setUmpleLines(133).setJavaLines(160).setLengths(1);}
    public UmpleSourceData DatabaseImpl_dumpLog(){ return new UmpleSourceData().setFileNames("DatabaseImpl.ump").setUmpleLines(466).setJavaLines(560).setLengths(12);}
    public UmpleSourceData DatabaseImpl_finishedINListHarvest(){ return new UmpleSourceData().setFileNames("DeleteOp_DatabaseImpl.ump").setUmpleLines(28).setJavaLines(655).setLengths(2);}
    public UmpleSourceData DatabaseImpl_getBinDeltaPercent(){ return new UmpleSourceData().setFileNames("DatabaseImpl.ump").setUmpleLines(522).setJavaLines(629).setLengths(1);}
    public UmpleSourceData ObsoleteProcessor_ObsoleteProcessor(){ return new UmpleSourceData().setFileNames("DatabaseImpl_static.ump").setUmpleLines(7).setJavaLines(29).setLengths(1);}
    public UmpleSourceData DbTree_lockNameLN(){ return new UmpleSourceData().setFileNames("DbTree.ump").setUmpleLines(218).setJavaLines(273).setLengths(42);}
    public UmpleSourceData DbTree_getLogType(){ return new UmpleSourceData().setFileNames("DbTree.ump").setUmpleLines(536).setJavaLines(652).setLengths(1);}
    public UmpleSourceData DbTree_getDbNames(){ return new UmpleSourceData().setFileNames("DbTree.ump").setUmpleLines(474).setJavaLines(581).setLengths(31);}
    public UmpleSourceData DbTree_readFromLog(){ return new UmpleSourceData().setFileNames("DbTree.ump").setUmpleLines(579).setJavaLines(714).setLengths(3);}
    public UmpleSourceData DbTree_getLogSize(){ return new UmpleSourceData().setFileNames("DbTree.ump").setUmpleLines(557).setJavaLines(682).setLengths(1);}
    public UmpleSourceData DbTree_DbTree(){ return new UmpleSourceData().setFileNames("DbTree.ump","DbTree.ump").setUmpleLines(62, 73).setJavaLines(82, 96).setLengths(5, 4);}
    public UmpleSourceData DbTree_setEnvironmentImpl(){ return new UmpleSourceData().setFileNames("DbTree.ump").setUmpleLines(112).setJavaLines(148).setLengths(3);}
    public UmpleSourceData RootLevel_RootLevel(){ return new UmpleSourceData().setFileNames("DbTree_static.ump").setUmpleLines(26).setJavaLines(29).setLengths(2);}
    public UmpleSourceData DbTree_writeToLog(){ return new UmpleSourceData().setFileNames("DbTree.ump").setUmpleLines(564).setJavaLines(692).setLengths(3);}
    public UmpleSourceData DbTree_logEntryIsTransactional(){ return new UmpleSourceData().setFileNames("DbTree.ump").setUmpleLines(603).setJavaLines(744).setLengths(1);}
    public UmpleSourceData DbTree_dbRename(){ return new UmpleSourceData().setFileNames("RenameOp_DbTree.ump").setUmpleLines(8).setJavaLines(796).setLengths(24);}
    public UmpleSourceData DbTree_truncate(){ return new UmpleSourceData().setFileNames("Truncate_DbTree.ump","Truncate_DbTree.ump").setUmpleLines(9, 62).setJavaLines(830, 889).setLengths(49, 59);}
    public UmpleSourceData DbTree_rebuildINListMapDb(){ return new UmpleSourceData().setFileNames("DbTree.ump").setUmpleLines(423).setJavaLines(521).setLengths(1);}
    public UmpleSourceData RewriteMapLN_doWork(){ return new UmpleSourceData().setFileNames("DbTree_static.ump","DbTree_static.ump").setUmpleLines(11, 30).setJavaLines(34, 35).setLengths(3, 3);}
    public UmpleSourceData DbTree_verify(){ return new UmpleSourceData().setFileNames("Derivative_Statistics_Verifier_DbTree.ump").setUmpleLines(5).setJavaLines(977).setLengths(62);}
    public UmpleSourceData DbTree_getDbName(){ return new UmpleSourceData().setFileNames("DbTree.ump").setUmpleLines(430).setJavaLines(531).setLengths(41);}
    public UmpleSourceData DbTree_createDb(){ return new UmpleSourceData().setFileNames("DbTree.ump","DbTree.ump").setUmpleLines(122, 134).setJavaLines(160, 174).setLengths(1, 43);}
    public UmpleSourceData DbTree_dump(){ return new UmpleSourceData().setFileNames("DbTree.ump").setUmpleLines(636).setJavaLines(785).setLengths(2);}
    public UmpleSourceData RootLevel_getRootLevel(){ return new UmpleSourceData().setFileNames("DbTree_static.ump").setUmpleLines(35).setJavaLines(42).setLengths(1);}
    public UmpleSourceData RewriteMapLN_RewriteMapLN(){ return new UmpleSourceData().setFileNames("DbTree_static.ump").setUmpleLines(8).setJavaLines(29).setLengths(1);}
    public UmpleSourceData DbTree_dbRemove(){ return new UmpleSourceData().setFileNames("DeleteOp_DbTree.ump").setUmpleLines(8).setJavaLines(957).setLengths(16);}
    public UmpleSourceData DbTree_getHighestLevel(){ return new UmpleSourceData().setFileNames("DbTree.ump").setUmpleLines(523).setJavaLines(636).setLengths(7);}
    public UmpleSourceData DbTree_dumpString(){ return new UmpleSourceData().setFileNames("DbTree.ump").setUmpleLines(614).setJavaLines(759).setLengths(12);}
    public UmpleSourceData DbTree_deleteMapLN(){ return new UmpleSourceData().setFileNames("DbTree.ump").setUmpleLines(254).setJavaLines(319).setLengths(20);}
    public UmpleSourceData DbTree_getDb(){ return new UmpleSourceData().setFileNames("DbTree.ump","DbTree.ump","DbTree.ump","DbTree.ump","DbTree.ump","DbTree.ump").setUmpleLines(281, 293, 331, 338, 345, 360).setJavaLines(348, 362, 409, 419, 429, 446).setLengths(1, 38, 1, 1, 7, 55);}
    public UmpleSourceData DbTree_getLastDbId(){ return new UmpleSourceData().setFileNames("DbTree.ump").setUmpleLines(83).setJavaLines(109).setLengths(1);}
    public UmpleSourceData DbTree_postLogWork(){ return new UmpleSourceData().setFileNames("DbTree.ump").setUmpleLines(573).setJavaLines(704).setLengths(1);}
    public UmpleSourceData DbTree_modifyDbRoot(){ return new UmpleSourceData().setFileNames("DbTree.ump").setUmpleLines(176).setJavaLines(227).setLengths(42);}
    public UmpleSourceData DbTree_setDebugNameForDatabaseImpl(){ return new UmpleSourceData().setFileNames("DbTree.ump").setUmpleLines(410).setJavaLines(505).setLengths(7);}
    public UmpleSourceData DbTree_getTransactionId(){ return new UmpleSourceData().setFileNames("DbTree.ump").setUmpleLines(610).setJavaLines(754).setLengths(1);}
    public UmpleSourceData DbTree_countAsObsoleteWhenLogged(){ return new UmpleSourceData().setFileNames("DbTree.ump").setUmpleLines(550).setJavaLines(672).setLengths(1);}
    public UmpleSourceData DbTree_isReservedDbName(){ return new UmpleSourceData().setFileNames("DbTree.ump").setUmpleLines(511).setJavaLines(621).setLengths(6);}
    public UmpleSourceData DbTree_dumpLog(){ return new UmpleSourceData().setFileNames("DbTree.ump").setUmpleLines(588).setJavaLines(726).setLengths(9);}
    public UmpleSourceData DbTree_toString(){ return new UmpleSourceData().setFileNames("DbTree.ump").setUmpleLines(629).setJavaLines(775).setLengths(1);}
    public UmpleSourceData DbTree_getNextDbId(){ return new UmpleSourceData().setFileNames("DbTree.ump").setUmpleLines(90).setJavaLines(119).setLengths(1);}
    public UmpleSourceData DbTree_marshallOutsideWriteLatch(){ return new UmpleSourceData().setFileNames("DbTree.ump").setUmpleLines(543).setJavaLines(662).setLengths(1);}
    public UmpleSourceData DbTree_setLastDbId(){ return new UmpleSourceData().setFileNames("DbTree.ump").setUmpleLines(97).setJavaLines(129).setLengths(1);}
    public UmpleSourceData DbTree_createLocker(){ return new UmpleSourceData().setFileNames("DbTree.ump").setUmpleLines(101).setJavaLines(134).setLengths(5);}
    public UmpleSourceData LockStats_getNRequests(){ return new UmpleSourceData().setFileNames("Statistics_LockStats.ump").setUmpleLines(78).setJavaLines(89).setLengths(1);}
    public UmpleSourceData LockStats_setNRequests(){ return new UmpleSourceData().setFileNames("Statistics_LockStats.ump").setUmpleLines(127).setJavaLines(159).setLengths(1);}
    public UmpleSourceData LockStats_getNWaiters(){ return new UmpleSourceData().setFileNames("Statistics_LockStats.ump").setUmpleLines(64).setJavaLines(69).setLengths(1);}
    public UmpleSourceData LockStats_accumulateLockTableLatchStats(){ return new UmpleSourceData().setFileNames("Derivative_Latches_Statistics_LockStats.ump").setUmpleLines(14).setJavaLines(201).setLengths(11);}
    public UmpleSourceData LockStats_getNWaits(){ return new UmpleSourceData().setFileNames("Statistics_LockStats.ump").setUmpleLines(85).setJavaLines(99).setLengths(1);}
    public UmpleSourceData LockStats_setNWaits(){ return new UmpleSourceData().setFileNames("Statistics_LockStats.ump").setUmpleLines(134).setJavaLines(169).setLengths(1);}
    public UmpleSourceData LockStats_getNReadLocks(){ return new UmpleSourceData().setFileNames("Statistics_LockStats.ump").setUmpleLines(50).setJavaLines(49).setLengths(1);}
    public UmpleSourceData LockStats_getNTotalLocks(){ return new UmpleSourceData().setFileNames("Statistics_LockStats.ump").setUmpleLines(57).setJavaLines(59).setLengths(1);}
    public UmpleSourceData LockStats_setNWriteLocks(){ return new UmpleSourceData().setFileNames("Statistics_LockStats.ump").setUmpleLines(120).setJavaLines(149).setLengths(1);}
    public UmpleSourceData LockStats_getNOwners(){ return new UmpleSourceData().setFileNames("Statistics_LockStats.ump").setUmpleLines(43).setJavaLines(39).setLengths(1);}
    public UmpleSourceData LockStats_setNWaiters(){ return new UmpleSourceData().setFileNames("Statistics_LockStats.ump").setUmpleLines(113).setJavaLines(139).setLengths(1);}
    public UmpleSourceData LockStats_getNWriteLocks(){ return new UmpleSourceData().setFileNames("Statistics_LockStats.ump").setUmpleLines(71).setJavaLines(79).setLengths(1);}
    public UmpleSourceData LockStats_setNOwners(){ return new UmpleSourceData().setFileNames("Statistics_LockStats.ump").setUmpleLines(92).setJavaLines(109).setLengths(1);}
    public UmpleSourceData LockStats_toString(){ return new UmpleSourceData().setFileNames("Statistics_LockStats.ump").setUmpleLines(141).setJavaLines(179).setLengths(13);}
    public UmpleSourceData LockStats_setNReadLocks(){ return new UmpleSourceData().setFileNames("Statistics_LockStats.ump").setUmpleLines(99).setJavaLines(119).setLengths(1);}
    public UmpleSourceData LockStats_accumulateNTotalLocks(){ return new UmpleSourceData().setFileNames("Statistics_LockStats.ump").setUmpleLines(106).setJavaLines(129).setLengths(1);}
    public UmpleSourceData TxnEnd_postLogWork(){ return new UmpleSourceData().setFileNames("TxnEnd.ump").setUmpleLines(65).setJavaLines(98).setLengths(1);}
    public UmpleSourceData TxnEnd_writeToLog(){ return new UmpleSourceData().setFileNames("TxnEnd.ump").setUmpleLines(78).setJavaLines(118).setLengths(3);}
    public UmpleSourceData TxnEnd_logEntryIsTransactional(){ return new UmpleSourceData().setFileNames("TxnEnd.ump").setUmpleLines(108).setJavaLines(157).setLengths(1);}
    public UmpleSourceData TxnEnd_TxnEnd(){ return new UmpleSourceData().setFileNames("TxnEnd.ump","TxnEnd.ump").setUmpleLines(20, 29).setJavaLines(37, 49).setLengths(3, 1);}
    public UmpleSourceData TxnEnd_readFromLog(){ return new UmpleSourceData().setFileNames("TxnEnd.ump").setUmpleLines(87).setJavaLines(130).setLengths(3);}
    public UmpleSourceData TxnEnd_getTransactionId(){ return new UmpleSourceData().setFileNames("TxnEnd.ump").setUmpleLines(115).setJavaLines(167).setLengths(1);}
    public UmpleSourceData TxnEnd_getLastLsn(){ return new UmpleSourceData().setFileNames("TxnEnd.ump").setUmpleLines(37).setJavaLines(59).setLengths(1);}
    public UmpleSourceData TxnEnd_getLogSize(){ return new UmpleSourceData().setFileNames("TxnEnd.ump").setUmpleLines(71).setJavaLines(108).setLengths(1);}
    public UmpleSourceData TxnEnd_countAsObsoleteWhenLogged(){ return new UmpleSourceData().setFileNames("TxnEnd.ump").setUmpleLines(58).setJavaLines(88).setLengths(1);}
    public UmpleSourceData TxnEnd_getId(){ return new UmpleSourceData().setFileNames("TxnEnd.ump").setUmpleLines(33).setJavaLines(54).setLengths(1);}
    public UmpleSourceData TxnEnd_dumpLog(){ return new UmpleSourceData().setFileNames("TxnEnd.ump").setUmpleLines(96).setJavaLines(142).setLengths(6);}
    public UmpleSourceData TxnEnd_marshallOutsideWriteLatch(){ return new UmpleSourceData().setFileNames("TxnEnd.ump").setUmpleLines(51).setJavaLines(78).setLengths(1);}
    public UmpleSourceData BuddyLocker_releaseNonTxnLocks(){ return new UmpleSourceData().setFileNames("BuddyLocker.ump").setUmpleLines(43).setJavaLines(81).setLengths(2);}
    public UmpleSourceData BuddyLocker_newNonTxnLocker(){ return new UmpleSourceData().setFileNames("BuddyLocker.ump").setUmpleLines(36).setJavaLines(71).setLengths(1);}
    public UmpleSourceData BuddyLocker_getBuddy(){ return new UmpleSourceData().setFileNames("BuddyLocker.ump").setUmpleLines(22).setJavaLines(51).setLengths(1);}
    public UmpleSourceData BuddyLocker_getTxnLocker(){ return new UmpleSourceData().setFileNames("BuddyLocker.ump").setUmpleLines(29).setJavaLines(61).setLengths(1);}
    public UmpleSourceData BuddyLocker_BuddyLocker(){ return new UmpleSourceData().setFileNames("BuddyLocker.ump").setUmpleLines(14).setJavaLines(40).setLengths(2);}
    public UmpleSourceData BuddyLocker_sharesLocksWith(){ return new UmpleSourceData().setFileNames("BuddyLocker.ump").setUmpleLines(51).setJavaLines(92).setLengths(5);}
    public UmpleSourceData LockUpgrade_LockUpgrade(){ return new UmpleSourceData().setFileNames("LockUpgrade.ump").setUmpleLines(27).setJavaLines(34).setLengths(3);}
    public UmpleSourceData LockUpgrade_getUpgrade(){ return new UmpleSourceData().setFileNames("LockUpgrade.ump").setUmpleLines(43).setJavaLines(56).setLengths(1);}
    public UmpleSourceData LockUpgrade_getPromotion(){ return new UmpleSourceData().setFileNames("LockUpgrade.ump").setUmpleLines(50).setJavaLines(66).setLengths(1);}
    public UmpleSourceData LockUpgrade_getIllegal(){ return new UmpleSourceData().setFileNames("LockUpgrade.ump").setUmpleLines(36).setJavaLines(46).setLengths(1);}
    public UmpleSourceData AutoTxn_setHandleLockOwner(){ return new UmpleSourceData().setFileNames("AutoTxn.ump").setUmpleLines(37).setJavaLines(71).setLengths(6);}
    public UmpleSourceData AutoTxn_AutoTxn(){ return new UmpleSourceData().setFileNames("AutoTxn.ump").setUmpleLines(11).setJavaLines(37).setLengths(1);}
    public UmpleSourceData AutoTxn_operationEnd(){ return new UmpleSourceData().setFileNames("AutoTxn.ump","AutoTxn.ump").setUmpleLines(18, 29).setJavaLines(47, 61).setLengths(5, 1);}
    public UmpleSourceData ThreadLocker_ThreadLocker(){ return new UmpleSourceData().setFileNames("ThreadLocker.ump").setUmpleLines(12).setJavaLines(40).setLengths(1);}
    public UmpleSourceData ThreadLocker_newNonTxnLocker(){ return new UmpleSourceData().setFileNames("ThreadLocker.ump").setUmpleLines(29).setJavaLines(63).setLengths(2);}
    public UmpleSourceData ThreadLocker_checkState(){ return new UmpleSourceData().setFileNames("ThreadLocker.ump").setUmpleLines(19).setJavaLines(50).setLengths(4);}
    public UmpleSourceData ThreadLocker_sharesLocksWith(){ return new UmpleSourceData().setFileNames("ThreadLocker.ump").setUmpleLines(37).setJavaLines(74).setLengths(7);}
    public UmpleSourceData LockType_getUpgrade(){ return new UmpleSourceData().setFileNames("LockType.ump").setUmpleLines(105).setJavaLines(66).setLengths(3);}
    public UmpleSourceData LockType_toString(){ return new UmpleSourceData().setFileNames("LockType.ump").setUmpleLines(111).setJavaLines(73).setLengths(1);}
    public UmpleSourceData LockType_LockType(){ return new UmpleSourceData().setFileNames("LockType.ump").setUmpleLines(68).setJavaLines(34).setLengths(3);}
    public UmpleSourceData LockType_setCausesRestart(){ return new UmpleSourceData().setFileNames("LockType.ump").setUmpleLines(84).setJavaLines(46).setLengths(1);}
    public UmpleSourceData LockType_getConflict(){ return new UmpleSourceData().setFileNames("LockType.ump").setUmpleLines(98).setJavaLines(56).setLengths(1);}
    public UmpleSourceData Lock_nWaiters(){ return new UmpleSourceData().setFileNames("Lock.ump").setUmpleLines(248).setJavaLines(324).setLengths(8);}
    public UmpleSourceData Lock_addWaiterToHeadOfList(){ return new UmpleSourceData().setFileNames("Lock.ump").setUmpleLines(62).setJavaLines(87).setLengths(12);}
    public UmpleSourceData Lock_validateRequest(){ return new UmpleSourceData().setFileNames("Lock.ump").setUmpleLines(680).setJavaLines(790).setLengths(15);}
    public UmpleSourceData Lock_release(){ return new UmpleSourceData().setFileNames("Lock.ump").setUmpleLines(330).setJavaLines(412).setLengths(57);}
    public UmpleSourceData Lock_flushWaiter(){ return new UmpleSourceData().setFileNames("Lock.ump").setUmpleLines(91).setJavaLines(125).setLengths(23);}
    public UmpleSourceData Lock_getOwnerLockInfo(){ return new UmpleSourceData().setFileNames("Lock.ump").setUmpleLines(185).setJavaLines(251).setLengths(13);}
    public UmpleSourceData Lock_hook768(){ return new UmpleSourceData().setFileNames("Lock.ump").setUmpleLines(752).setJavaLines(882).setLengths(1);}
    public UmpleSourceData Lock_hook767(){ return new UmpleSourceData().setFileNames("Lock.ump").setUmpleLines(749).setJavaLines(877).setLengths(1);}
    public UmpleSourceData Lock_hook766(){ return new UmpleSourceData().setFileNames("Lock.ump").setUmpleLines(746).setJavaLines(872).setLengths(1);}
    public UmpleSourceData Lock_hook765(){ return new UmpleSourceData().setFileNames("Lock.ump").setUmpleLines(743).setJavaLines(867).setLengths(1);}
    public UmpleSourceData Lock_transferMultiple(){ return new UmpleSourceData().setFileNames("Lock.ump").setUmpleLines(562).setJavaLines(664).setLengths(62);}
    public UmpleSourceData Lock_hook764(){ return new UmpleSourceData().setFileNames("Lock.ump").setUmpleLines(740).setJavaLines(862).setLengths(1);}
    public UmpleSourceData Lock_addWaiterToEndOfList(){ return new UmpleSourceData().setFileNames("Lock.ump").setUmpleLines(44).setJavaLines(63).setLengths(14);}
    public UmpleSourceData Lock_flushOwner(){ return new UmpleSourceData().setFileNames("Lock.ump","Lock.ump").setUmpleLines(143, 162).setJavaLines(193, 220).setLengths(18, 22);}
    public UmpleSourceData Lock_cloneLockInfo(){ return new UmpleSourceData().setFileNames("Lock.ump").setUmpleLines(631).setJavaLines(735).setLengths(16);}
    public UmpleSourceData Lock_hook763(){ return new UmpleSourceData().setFileNames("Lock.ump").setUmpleLines(737).setJavaLines(857).setLengths(1);}
    public UmpleSourceData Lock_hook762(){ return new UmpleSourceData().setFileNames("Lock.ump").setUmpleLines(734).setJavaLines(852).setLengths(1);}
    public UmpleSourceData Lock_hook761(){ return new UmpleSourceData().setFileNames("Lock.ump").setUmpleLines(731).setJavaLines(847).setLengths(1);}
    public UmpleSourceData Lock_hook760(){ return new UmpleSourceData().setFileNames("Lock.ump").setUmpleLines(728).setJavaLines(842).setLengths(1);}
    public UmpleSourceData Lock_nOwners(){ return new UmpleSourceData().setFileNames("Lock.ump").setUmpleLines(259).setJavaLines(336).setLengths(8);}
    public UmpleSourceData Lock_isOwner(){ return new UmpleSourceData().setFileNames("Lock.ump").setUmpleLines(204).setJavaLines(273).setLengths(12);}
    public UmpleSourceData Lock_isOwnedWriteLock(){ return new UmpleSourceData().setFileNames("Lock.ump").setUmpleLines(222).setJavaLines(294).setLengths(2);}
    public UmpleSourceData Lock_setNewLocker(){ return new UmpleSourceData().setFileNames("Lock.ump").setUmpleLines(552).setJavaLines(652).setLengths(3);}
    public UmpleSourceData Lock_lock(){ return new UmpleSourceData().setFileNames("Lock.ump").setUmpleLines(274).setJavaLines(353).setLengths(49);}
    public UmpleSourceData Lock_getNodeId(){ return new UmpleSourceData().setFileNames("Lock.ump").setUmpleLines(37).setJavaLines(53).setLengths(1);}
    public UmpleSourceData Lock_addOwner(){ return new UmpleSourceData().setFileNames("Lock.ump").setUmpleLines(111).setJavaLines(152).setLengths(12);}
    public UmpleSourceData Lock_isWaiter(){ return new UmpleSourceData().setFileNames("Lock.ump").setUmpleLines(230).setJavaLines(305).setLengths(15);}
    public UmpleSourceData Lock_tryLock(){ return new UmpleSourceData().setFileNames("Lock.ump").setUmpleLines(394).setJavaLines(482).setLengths(67);}
    public UmpleSourceData Lock_Lock(){ return new UmpleSourceData().setFileNames("Lock.ump","Lock.ump").setUmpleLines(30, 34).setJavaLines(43, 48).setLengths(1, 1);}
    public UmpleSourceData Lock_getOwnersClone(){ return new UmpleSourceData().setFileNames("Lock.ump").setUmpleLines(127).setJavaLines(174).setLengths(10);}
    public UmpleSourceData Lock_demote(){ return new UmpleSourceData().setFileNames("Lock.ump").setUmpleLines(496).setJavaLines(590).setLengths(7);}
    public UmpleSourceData Lock_transfer(){ return new UmpleSourceData().setFileNames("Lock.ump").setUmpleLines(510).setJavaLines(606).setLengths(42);}
    public UmpleSourceData Lock_toString(){ return new UmpleSourceData().setFileNames("Lock.ump").setUmpleLines(701).setJavaLines(814).setLengths(24);}
    public UmpleSourceData Lock_getWriteOwnerLocker(){ return new UmpleSourceData().setFileNames("Lock.ump").setUmpleLines(653).setJavaLines(760).setLengths(21);}
    public UmpleSourceData Lock_getWaitersListClone(){ return new UmpleSourceData().setFileNames("Lock.ump").setUmpleLines(77).setJavaLines(108).setLengths(8);}
    public UmpleSourceData Lock_rangeInsertConflict(){ return new UmpleSourceData().setFileNames("Lock.ump").setUmpleLines(467).setJavaLines(558).setLengths(23);}
    public UmpleSourceData TxnCommit_TxnCommit(){ return new UmpleSourceData().setFileNames("TxnCommit.ump","TxnCommit.ump").setUmpleLines(8, 15).setJavaLines(34, 44).setLengths(1, 1);}
    public UmpleSourceData TxnCommit_getLogType(){ return new UmpleSourceData().setFileNames("TxnCommit.ump").setUmpleLines(21).setJavaLines(54).setLengths(1);}
    public UmpleSourceData TxnCommit_getTagName(){ return new UmpleSourceData().setFileNames("TxnCommit.ump").setUmpleLines(25).setJavaLines(59).setLengths(1);}
    public UmpleSourceData Locker_Locker(){ return new UmpleSourceData().setFileNames("Locker.ump","Locker.ump").setUmpleLines(60, 81).setJavaLines(59, 83).setLengths(15, 1);}
    public UmpleSourceData Locker_getId(){ return new UmpleSourceData().setFileNames("Locker.ump").setUmpleLines(92).setJavaLines(100).setLengths(1);}
    public UmpleSourceData Locker_nonBlockingLock(){ return new UmpleSourceData().setFileNames("Locker.ump").setUmpleLines(184).setJavaLines(219).setLengths(1);}
    public UmpleSourceData Locker_isReadUncommittedDefault(){ return new UmpleSourceData().setFileNames("Locker.ump").setUmpleLines(128).setJavaLines(151).setLengths(1);}
    public UmpleSourceData Locker_isTimedOut(){ return new UmpleSourceData().setFileNames("Locker.ump").setUmpleLines(308).setJavaLines(389).setLengths(7);}
    public UmpleSourceData Locker_getDefaultNoWait(){ return new UmpleSourceData().setFileNames("Locker.ump").setUmpleLines(99).setJavaLines(110).setLengths(1);}
    public UmpleSourceData Locker_sharesLocksWith(){ return new UmpleSourceData().setFileNames("Locker.ump").setUmpleLines(235).setJavaLines(291).setLengths(6);}
    public UmpleSourceData Locker_dumpLockTable(){ return new UmpleSourceData().setFileNames("Locker.ump").setUmpleLines(410).setJavaLines(515).setLengths(1);}
    public UmpleSourceData Locker_isHandleLockTransferrable(){ return new UmpleSourceData().setFileNames("Locker.ump").setUmpleLines(357).setJavaLines(449).setLengths(1);}
    public UmpleSourceData Locker_transferHandleLockToHandle(){ return new UmpleSourceData().setFileNames("Locker.ump").setUmpleLines(364).setJavaLines(459).setLengths(2);}
    public UmpleSourceData Locker_demoteLock(){ return new UmpleSourceData().setFileNames("Locker.ump").setUmpleLines(198).setJavaLines(239).setLengths(1);}
    public UmpleSourceData Locker_addToHandleMaps(){ return new UmpleSourceData().setFileNames("Locker.ump").setUmpleLines(338).setJavaLines(427).setLengths(13);}
    public UmpleSourceData Locker_getWaitingFor(){ return new UmpleSourceData().setFileNames("Locker.ump").setUmpleLines(132).setJavaLines(156).setLengths(1);}
    public UmpleSourceData Locker_lock(){ return new UmpleSourceData().setFileNames("Locker.ump").setUmpleLines(169).setJavaLines(201).setLengths(6);}
    public UmpleSourceData Locker_operationEnd(){ return new UmpleSourceData().setFileNames("Locker.ump").setUmpleLines(264).setJavaLines(328).setLengths(1);}
    public UmpleSourceData Locker_getTxnStartMillis(){ return new UmpleSourceData().setFileNames("Locker.ump").setUmpleLines(322).setJavaLines(405).setLengths(1);}
    public UmpleSourceData Locker_addDeleteInfo(){ return new UmpleSourceData().setFileNames("INCompressor_Locker.ump").setUmpleLines(10).setJavaLines(532).setLengths(12);}
    public UmpleSourceData Locker_rememberHandleWriteLock(){ return new UmpleSourceData().setFileNames("Locker.ump").setUmpleLines(398).setJavaLines(498).setLengths(1);}
    public UmpleSourceData Locker_getLockTimeout(){ return new UmpleSourceData().setFileNames("Locker.ump").setUmpleLines(106).setJavaLines(120).setLengths(1);}
    public UmpleSourceData Locker_releaseLock(){ return new UmpleSourceData().setFileNames("Locker.ump").setUmpleLines(191).setJavaLines(229).setLengths(1);}
    public UmpleSourceData Locker_getTxnTimeOut(){ return new UmpleSourceData().setFileNames("Locker.ump").setUmpleLines(318).setJavaLines(400).setLengths(1);}
    public UmpleSourceData Locker_transferHandleLock(){ return new UmpleSourceData().setFileNames("Locker.ump").setUmpleLines(372).setJavaLines(469).setLengths(20);}
    public UmpleSourceData Locker_setTxnTimeout(){ return new UmpleSourceData().setFileNames("Locker.ump").setUmpleLines(120).setJavaLines(140).setLengths(2);}
    public UmpleSourceData Locker_setOnlyAbortable(){ return new UmpleSourceData().setFileNames("Locker.ump").setUmpleLines(143).setJavaLines(171).setLengths(1);}
    public UmpleSourceData Locker_setWaitingFor(){ return new UmpleSourceData().setFileNames("Locker.ump").setUmpleLines(136).setJavaLines(161).setLengths(1);}
    public UmpleSourceData Locker_unregisterHandle(){ return new UmpleSourceData().setFileNames("Locker.ump").setUmpleLines(329).setJavaLines(415).setLengths(3);}
    public UmpleSourceData Locker_toString(){ return new UmpleSourceData().setFileNames("Locker.ump").setUmpleLines(401).setJavaLines(503).setLengths(3);}
    public UmpleSourceData Locker_setLockTimeout(){ return new UmpleSourceData().setFileNames("Locker.ump").setUmpleLines(113).setJavaLines(130).setLengths(1);}
    public UmpleSourceData LockGrantType_LockGrantType(){ return new UmpleSourceData().setFileNames("LockGrantType.ump").setUmpleLines(24).setJavaLines(29).setLengths(1);}
    public UmpleSourceData LockGrantType_toString(){ return new UmpleSourceData().setFileNames("LockGrantType.ump").setUmpleLines(28).setJavaLines(34).setLengths(1);}
    public UmpleSourceData TxnPrepare_TxnPrepare(){ return new UmpleSourceData().setFileNames("TxnPrepare.ump","TxnPrepare.ump").setUmpleLines(16, 24).setJavaLines(40, 51).setLengths(2, 1);}
    public UmpleSourceData TxnPrepare_writeToLog(){ return new UmpleSourceData().setFileNames("TxnPrepare.ump").setUmpleLines(52).setJavaLines(91).setLengths(3);}
    public UmpleSourceData TxnPrepare_getLogType(){ return new UmpleSourceData().setFileNames("TxnPrepare.ump").setUmpleLines(34).setJavaLines(66).setLengths(1);}
    public UmpleSourceData TxnPrepare_readFromLog(){ return new UmpleSourceData().setFileNames("TxnPrepare.ump").setUmpleLines(61).setJavaLines(103).setLengths(3);}
    public UmpleSourceData TxnPrepare_getXid(){ return new UmpleSourceData().setFileNames("TxnPrepare.ump").setUmpleLines(27).setJavaLines(56).setLengths(1);}
    public UmpleSourceData TxnPrepare_getLogSize(){ return new UmpleSourceData().setFileNames("TxnPrepare.ump").setUmpleLines(45).setJavaLines(81).setLengths(1);}
    public UmpleSourceData TxnPrepare_getTagName(){ return new UmpleSourceData().setFileNames("TxnPrepare.ump").setUmpleLines(38).setJavaLines(71).setLengths(1);}
    public UmpleSourceData TxnPrepare_dumpLog(){ return new UmpleSourceData().setFileNames("TxnPrepare.ump").setUmpleLines(70).setJavaLines(115).setLengths(6);}
    public UmpleSourceData SyncedLockManager_nWaiters(){ return new UmpleSourceData().setFileNames("SyncedLockManager.ump").setUmpleLines(147).setJavaLines(265).setLengths(16);}
    public UmpleSourceData SyncedLockManager_releaseAndFindNotifyTargets(){ return new UmpleSourceData().setFileNames("SyncedLockManager.ump").setUmpleLines(53).setJavaLines(100).setLengths(20);}
    public UmpleSourceData SyncedLockManager_isWaiter(){ return new UmpleSourceData().setFileNames("SyncedLockManager.ump").setUmpleLines(132).setJavaLines(240).setLengths(16);}
    public UmpleSourceData SyncedLockManager_attemptLock(){ return new UmpleSourceData().setFileNames("SyncedLockManager.ump").setUmpleLines(21).setJavaLines(50).setLengths(16);}
    public UmpleSourceData SyncedLockManager_transferMultiple(){ return new UmpleSourceData().setFileNames("SyncedLockManager.ump").setUmpleLines(82).setJavaLines(149).setLengths(11);}
    public UmpleSourceData SyncedLockManager_dumpLockTable(){ return new UmpleSourceData().setFileNames("SyncedLockManager.ump").setUmpleLines(208).setJavaLines(365).setLengths(12);}
    public UmpleSourceData SyncedLockManager_makeTimeoutMsg(){ return new UmpleSourceData().setFileNames("SyncedLockManager.ump").setUmpleLines(37).setJavaLines(75).setLengths(16);}
    public UmpleSourceData SyncedLockManager_nOwners(){ return new UmpleSourceData().setFileNames("SyncedLockManager.ump").setUmpleLines(162).setJavaLines(290).setLengths(16);}
    public UmpleSourceData SyncedLockManager_SyncedLockManager(){ return new UmpleSourceData().setFileNames("SyncedLockManager.ump").setUmpleLines(13).setJavaLines(40).setLengths(1);}
    public UmpleSourceData SyncedLockManager_transfer(){ return new UmpleSourceData().setFileNames("SyncedLockManager.ump").setUmpleLines(72).setJavaLines(129).setLengths(11);}
    public UmpleSourceData SyncedLockManager_demote(){ return new UmpleSourceData().setFileNames("SyncedLockManager.ump").setUmpleLines(92).setJavaLines(169).setLengths(11);}
    public UmpleSourceData SyncedLockManager_isOwner(){ return new UmpleSourceData().setFileNames("SyncedLockManager.ump").setUmpleLines(117).setJavaLines(214).setLengths(17);}
    public UmpleSourceData SyncedLockManager_isLocked(){ return new UmpleSourceData().setFileNames("SyncedLockManager.ump").setUmpleLines(102).setJavaLines(189).setLengths(16);}
    public UmpleSourceData SyncedLockManager_validateOwnership(){ return new UmpleSourceData().setFileNames("SyncedLockManager.ump").setUmpleLines(193).setJavaLines(340).setLengths(16);}
    public UmpleSourceData SyncedLockManager_getWriteOwnerLocker(){ return new UmpleSourceData().setFileNames("SyncedLockManager.ump").setUmpleLines(177).setJavaLines(315).setLengths(16);}
    public UmpleSourceData LockerFactory_getWritableLocker(){ return new UmpleSourceData().setFileNames("LockerFactory.ump","LockerFactory.ump").setUmpleLines(18, 27).setJavaLines(42, 53).setLengths(1, 35);}
    public UmpleSourceData LockerFactory_getReadableLocker(){ return new UmpleSourceData().setFileNames("LockerFactory.ump","LockerFactory.ump","LockerFactory.ump").setUmpleLines(69, 88, 107).setJavaLines(97, 118, 139).setLengths(12, 12, 19);}
    public UmpleSourceData LockConflict_LockConflict(){ return new UmpleSourceData().setFileNames("LockConflict.ump").setUmpleLines(19).setJavaLines(34).setLengths(2);}
    public UmpleSourceData LockConflict_getAllowed(){ return new UmpleSourceData().setFileNames("LockConflict.ump").setUmpleLines(27).setJavaLines(45).setLengths(1);}
    public UmpleSourceData LockConflict_getRestart(){ return new UmpleSourceData().setFileNames("LockConflict.ump").setUmpleLines(34).setJavaLines(55).setLengths(1);}
    public UmpleSourceData ReadCommittedLocker_addDeleteInfo(){ return new UmpleSourceData().setFileNames("INCompressor_ReadCommittedLocker.ump").setUmpleLines(8).setJavaLines(165).setLengths(1);}
    public UmpleSourceData ReadCommittedLocker_createdNode(){ return new UmpleSourceData().setFileNames("ReadCommittedLocker.ump").setUmpleLines(55).setJavaLines(95).setLengths(1);}
    public UmpleSourceData ReadCommittedLocker_releaseLock(){ return new UmpleSourceData().setFileNames("ReadCommittedLocker.ump").setUmpleLines(46).setJavaLines(83).setLengths(3);}
    public UmpleSourceData ReadCommittedLocker_newNonTxnLocker(){ return new UmpleSourceData().setFileNames("ReadCommittedLocker.ump").setUmpleLines(25).setJavaLines(57).setLengths(1);}
    public UmpleSourceData ReadCommittedLocker_getAbortLsn(){ return new UmpleSourceData().setFileNames("ReadCommittedLocker.ump").setUmpleLines(62).setJavaLines(105).setLengths(1);}
    public UmpleSourceData ReadCommittedLocker_getWriteLockInfo(){ return new UmpleSourceData().setFileNames("ReadCommittedLocker.ump").setUmpleLines(69).setJavaLines(115).setLengths(1);}
    public UmpleSourceData ReadCommittedLocker_isTransactional(){ return new UmpleSourceData().setFileNames("ReadCommittedLocker.ump").setUmpleLines(90).setJavaLines(145).setLengths(1);}
    public UmpleSourceData ReadCommittedLocker_ReadCommittedLocker(){ return new UmpleSourceData().setFileNames("ReadCommittedLocker.ump").setUmpleLines(17).setJavaLines(46).setLengths(2);}
    public UmpleSourceData ReadCommittedLocker_registerCursor(){ return new UmpleSourceData().setFileNames("ReadCommittedLocker.ump").setUmpleLines(76).setJavaLines(125).setLengths(1);}
    public UmpleSourceData ReadCommittedLocker_isReadCommittedIsolation(){ return new UmpleSourceData().setFileNames("ReadCommittedLocker.ump").setUmpleLines(97).setJavaLines(155).setLengths(1);}
    public UmpleSourceData ReadCommittedLocker_lockInternal(){ return new UmpleSourceData().setFileNames("ReadCommittedLocker.ump").setUmpleLines(35).setJavaLines(69).setLengths(5);}
    public UmpleSourceData ReadCommittedLocker_unRegisterCursor(){ return new UmpleSourceData().setFileNames("ReadCommittedLocker.ump").setUmpleLines(83).setJavaLines(135).setLengths(1);}
    public UmpleSourceData TxnManager_unsetTxnForThread(){ return new UmpleSourceData().setFileNames("TxnManager.ump").setUmpleLines(161).setJavaLines(257).setLengths(2);}
    public UmpleSourceData TxnManager_hook829(){ return new UmpleSourceData().setFileNames("TxnManager.ump").setUmpleLines(250).setJavaLines(373).setLengths(1);}
    public UmpleSourceData TxnManager_txnBegin(){ return new UmpleSourceData().setFileNames("TxnManager.ump").setUmpleLines(82).setJavaLines(110).setLengths(4);}
    public UmpleSourceData TxnManager_unRegisterXATxn(){ return new UmpleSourceData().setFileNames("TxnManager.ump").setUmpleLines(134).setJavaLines(211).setLengths(16);}
    public UmpleSourceData TxnManager_hook827(){ return new UmpleSourceData().setFileNames("TxnManager.ump").setUmpleLines(245).setJavaLines(363).setLengths(1);}
    public UmpleSourceData TxnManager_getTxnForThread(){ return new UmpleSourceData().setFileNames("TxnManager.ump").setUmpleLines(169).setJavaLines(268).setLengths(1);}
    public UmpleSourceData TxnManager_hook826(){ return new UmpleSourceData().setFileNames("TxnManager.ump").setUmpleLines(243).setJavaLines(358).setLengths(1);}
    public UmpleSourceData TxnManager_hook825(){ return new UmpleSourceData().setFileNames("TxnManager.ump").setUmpleLines(241).setJavaLines(353).setLengths(1);}
    public UmpleSourceData TxnManager_setTxnForThread(){ return new UmpleSourceData().setFileNames("TxnManager.ump").setUmpleLines(153).setJavaLines(246).setLengths(2);}
    public UmpleSourceData TxnManager_XARecover(){ return new UmpleSourceData().setFileNames("TxnManager.ump").setUmpleLines(173).setJavaLines(273).setLengths(4);}
    public UmpleSourceData TxnManager_getLockManager(){ return new UmpleSourceData().setFileNames("TxnManager.ump").setUmpleLines(92).setJavaLines(123).setLengths(1);}
    public UmpleSourceData TxnManager_getLastTxnId(){ return new UmpleSourceData().setFileNames("TxnManager.ump").setUmpleLines(65).setJavaLines(87).setLengths(1);}
    public UmpleSourceData TxnManager_hook830(){ return new UmpleSourceData().setFileNames("TxnManager.ump").setUmpleLines(252).setJavaLines(378).setLengths(1);}
    public UmpleSourceData TxnManager_getFirstActiveLsn(){ return new UmpleSourceData().setFileNames("TxnManager.ump").setUmpleLines(191).setJavaLines(297).setLengths(24);}
    public UmpleSourceData TxnManager_lockStat(){ return new UmpleSourceData().setFileNames("Statistics_TxnManager.ump").setUmpleLines(58).setJavaLines(430).setLengths(1);}
    public UmpleSourceData TxnManager_TxnManager(){ return new UmpleSourceData().setFileNames("TxnManager.ump").setUmpleLines(39).setJavaLines(51).setLengths(27);}
    public UmpleSourceData TxnManager_registerXATxn(){ return new UmpleSourceData().setFileNames("TxnManager.ump").setUmpleLines(122).setJavaLines(188).setLengths(14);}
    public UmpleSourceData TxnManager_txnStat(){ return new UmpleSourceData().setFileNames("Statistics_TxnManager.ump").setUmpleLines(21).setJavaLines(388).setLengths(33);}
    public UmpleSourceData TxnManager_areOtherSerializableTransactionsActive(){ return new UmpleSourceData().setFileNames("TxnManager.ump").setUmpleLines(183).setJavaLines(286).setLengths(2);}
    public UmpleSourceData TxnManager_incTxnId(){ return new UmpleSourceData().setFileNames("TxnManager.ump").setUmpleLines(72).setJavaLines(97).setLengths(1);}
    public UmpleSourceData TxnManager_unRegisterTxn(){ return new UmpleSourceData().setFileNames("Latches_TxnManager.ump","Latches_TxnManager.ump","TxnManager.ump").setUmpleLines(42, 46, 109).setJavaLines(156, 176, 154).setLengths(1, 3, 17);}
    public UmpleSourceData TxnManager_getTxnFromXid(){ return new UmpleSourceData().setFileNames("TxnManager.ump").setUmpleLines(146).setJavaLines(236).setLengths(1);}
    public UmpleSourceData TxnManager_registerTxn(){ return new UmpleSourceData().setFileNames("Latches_TxnManager.ump","Latches_TxnManager.ump","TxnManager.ump").setUmpleLines(27, 33, 99).setJavaLines(135, 143, 133).setLengths(2, 2, 4);}
    public UmpleSourceData DummyLockManager_nWaiters(){ return new UmpleSourceData().setFileNames("DummyLockManager.ump").setUmpleLines(83).setJavaLines(139).setLengths(1);}
    public UmpleSourceData DummyLockManager_releaseAndFindNotifyTargets(){ return new UmpleSourceData().setFileNames("DummyLockManager.ump").setUmpleLines(37).setJavaLines(69).setLengths(1);}
    public UmpleSourceData DummyLockManager_isWaiter(){ return new UmpleSourceData().setFileNames("DummyLockManager.ump").setUmpleLines(76).setJavaLines(129).setLengths(1);}
    public UmpleSourceData DummyLockManager_attemptLock(){ return new UmpleSourceData().setFileNames("DummyLockManager.ump").setUmpleLines(21).setJavaLines(49).setLengths(1);}
    public UmpleSourceData DummyLockManager_transferMultiple(){ return new UmpleSourceData().setFileNames("DummyLockManager.ump").setUmpleLines(50).setJavaLines(89).setLengths(1);}
    public UmpleSourceData DummyLockManager_dumpLockTable(){ return new UmpleSourceData().setFileNames("DummyLockManager.ump").setUmpleLines(112).setJavaLines(179).setLengths(1);}
    public UmpleSourceData DummyLockManager_makeTimeoutMsg(){ return new UmpleSourceData().setFileNames("DummyLockManager.ump").setUmpleLines(29).setJavaLines(59).setLengths(1);}
    public UmpleSourceData DummyLockManager_nOwners(){ return new UmpleSourceData().setFileNames("DummyLockManager.ump").setUmpleLines(90).setJavaLines(149).setLengths(1);}
    public UmpleSourceData DummyLockManager_DummyLockManager(){ return new UmpleSourceData().setFileNames("DummyLockManager.ump").setUmpleLines(13).setJavaLines(39).setLengths(1);}
    public UmpleSourceData DummyLockManager_transfer(){ return new UmpleSourceData().setFileNames("DummyLockManager.ump").setUmpleLines(44).setJavaLines(79).setLengths(1);}
    public UmpleSourceData DummyLockManager_demote(){ return new UmpleSourceData().setFileNames("DummyLockManager.ump").setUmpleLines(56).setJavaLines(99).setLengths(1);}
    public UmpleSourceData DummyLockManager_isOwner(){ return new UmpleSourceData().setFileNames("DummyLockManager.ump").setUmpleLines(69).setJavaLines(119).setLengths(1);}
    public UmpleSourceData DummyLockManager_isLocked(){ return new UmpleSourceData().setFileNames("DummyLockManager.ump").setUmpleLines(62).setJavaLines(109).setLengths(1);}
    public UmpleSourceData DummyLockManager_validateOwnership(){ return new UmpleSourceData().setFileNames("DummyLockManager.ump").setUmpleLines(105).setJavaLines(169).setLengths(1);}
    public UmpleSourceData DummyLockManager_getWriteOwnerLocker(){ return new UmpleSourceData().setFileNames("DummyLockManager.ump").setUmpleLines(97).setJavaLines(159).setLengths(1);}
    public UmpleSourceData LockResult_setLN(){ return new UmpleSourceData().setFileNames("LockResult.ump").setUmpleLines(23).setJavaLines(42).setLengths(1);}
    public UmpleSourceData LockResult_getLockGrant(){ return new UmpleSourceData().setFileNames("LockResult.ump").setUmpleLines(27).setJavaLines(47).setLengths(1);}
    public UmpleSourceData LockResult_LockResult(){ return new UmpleSourceData().setFileNames("LockResult.ump").setUmpleLines(14).setJavaLines(31).setLengths(2);}
    public UmpleSourceData LockResult_getLN(){ return new UmpleSourceData().setFileNames("LockResult.ump").setUmpleLines(19).setJavaLines(37).setLengths(1);}
    public UmpleSourceData LockResult_setAbortLsn(){ return new UmpleSourceData().setFileNames("LockResult.ump","LockResult.ump").setUmpleLines(31, 35).setJavaLines(52, 57).setLengths(1, 1);}
    public UmpleSourceData LockResult_setAbortLsnInternal(){ return new UmpleSourceData().setFileNames("LockResult.ump").setUmpleLines(39).setJavaLines(62).setLengths(8);}
    public UmpleSourceData LockInfo_getLocker(){ return new UmpleSourceData().setFileNames("LockInfo.ump").setUmpleLines(54).setJavaLines(84).setLengths(1);}
    public UmpleSourceData LockInfo_getLockType(){ return new UmpleSourceData().setFileNames("LockInfo.ump").setUmpleLines(68).setJavaLines(104).setLengths(1);}
    public UmpleSourceData LockInfo_setLockType(){ return new UmpleSourceData().setFileNames("LockInfo.ump").setUmpleLines(61).setJavaLines(94).setLengths(1);}
    public UmpleSourceData LockInfo_setDeadlockStackTrace(){ return new UmpleSourceData().setFileNames("LockInfo.ump").setUmpleLines(22).setJavaLines(40).setLengths(1);}
    public UmpleSourceData LockInfo_getDeadlockStackTrace(){ return new UmpleSourceData().setFileNames("LockInfo.ump").setUmpleLines(29).setJavaLines(50).setLengths(1);}
    public UmpleSourceData LockInfo_setLocker(){ return new UmpleSourceData().setFileNames("LockInfo.ump").setUmpleLines(47).setJavaLines(74).setLengths(1);}
    public UmpleSourceData LockInfo_clone(){ return new UmpleSourceData().setFileNames("LockInfo.ump").setUmpleLines(72).setJavaLines(109).setLengths(1);}
    public UmpleSourceData LockInfo_LockInfo(){ return new UmpleSourceData().setFileNames("LockInfo.ump").setUmpleLines(36).setJavaLines(60).setLengths(5);}
    public UmpleSourceData LockInfo_toString(){ return new UmpleSourceData().setFileNames("LockInfo.ump").setUmpleLines(83).setJavaLines(124).setLengths(14);}
    public UmpleSourceData LockInfo_dump(){ return new UmpleSourceData().setFileNames("LockInfo.ump").setUmpleLines(79).setJavaLines(119).setLengths(1);}
    public UmpleSourceData TxnAbort_TxnAbort(){ return new UmpleSourceData().setFileNames("TxnAbort.ump","TxnAbort.ump").setUmpleLines(8, 15).setJavaLines(34, 44).setLengths(1, 1);}
    public UmpleSourceData TxnAbort_getLogType(){ return new UmpleSourceData().setFileNames("TxnAbort.ump").setUmpleLines(21).setJavaLines(54).setLengths(1);}
    public UmpleSourceData TxnAbort_getTagName(){ return new UmpleSourceData().setFileNames("TxnAbort.ump").setUmpleLines(25).setJavaLines(59).setLengths(1);}
    public UmpleSourceData LockManager_dumpToStringNoLatch(){ return new UmpleSourceData().setFileNames("LockManager.ump").setUmpleLines(527).setJavaLines(654).setLengths(10);}
    public UmpleSourceData LockManager_release(){ return new UmpleSourceData().setFileNames("LockManager.ump","LockManager.ump","LockManager.ump").setUmpleLines(226, 235, 247).setJavaLines(283, 295, 310).setLengths(1, 1, 17);}
    public UmpleSourceData LockManager_getLockTableIndex(){ return new UmpleSourceData().setFileNames("LockManager.ump","LockManager.ump").setUmpleLines(67, 71).setJavaLines(105, 110).setLengths(1, 1);}
    public UmpleSourceData LockManager_dumpToString(){ return new UmpleSourceData().setFileNames("LockManager.ump").setUmpleLines(513).setJavaLines(637).setLengths(13);}
    public UmpleSourceData LockManager_lockStat(){ return new UmpleSourceData().setFileNames("Statistics_LockManager.ump").setUmpleLines(14).setJavaLines(768).setLengths(1);}
    public UmpleSourceData LockManager_dumpLockTableInternal(){ return new UmpleSourceData().setFileNames("LockManager.ump").setUmpleLines(491).setJavaLines(597).setLengths(26);}
    public UmpleSourceData LockManager_hook780(){ return new UmpleSourceData().setFileNames("LockManager.ump").setUmpleLines(621).setJavaLines(758).setLengths(1);}
    public UmpleSourceData LockManager_isLockedInternal(){ return new UmpleSourceData().setFileNames("LockManager.ump").setUmpleLines(369).setJavaLines(455).setLengths(6);}
    public UmpleSourceData LockManager_transferInternal(){ return new UmpleSourceData().setFileNames("LockManager.ump").setUmpleLines(313).setJavaLines(385).setLengths(8);}
    public UmpleSourceData LockManager_releaseAndFindNotifyTargetsInternal(){ return new UmpleSourceData().setFileNames("LockManager.ump").setUmpleLines(278).setJavaLines(344).setLengths(25);}
    public UmpleSourceData LockManager_findDeadlock(){ return new UmpleSourceData().setFileNames("LockManager.ump").setUmpleLines(540).setJavaLines(668).setLengths(8);}
    public UmpleSourceData LockManager_findDeadlock1(){ return new UmpleSourceData().setFileNames("LockManager.ump").setUmpleLines(551).setJavaLines(680).setLengths(33);}
    public UmpleSourceData LockManager_lock(){ return new UmpleSourceData().setFileNames("LockManager.ump").setUmpleLines(86).setJavaLines(127).setLengths(66);}
    public UmpleSourceData LockManager_dump(){ return new UmpleSourceData().setFileNames("LockManager.ump").setUmpleLines(509).setJavaLines(632).setLengths(1);}
    public UmpleSourceData LockManager_getWriteOwnerLockerInternal(){ return new UmpleSourceData().setFileNames("LockManager.ump").setUmpleLines(454).setJavaLines(565).setLengths(9);}
    public UmpleSourceData LockManager_makeTimeoutMsgInternal(){ return new UmpleSourceData().setFileNames("LockManager.ump").setUmpleLines(189).setJavaLines(243).setLengths(29);}
    public UmpleSourceData LockManager_validateOwnershipInternal(){ return new UmpleSourceData().setFileNames("LockManager.ump").setUmpleLines(470).setJavaLines(578).setLengths(10);}
    public UmpleSourceData LockManager_envConfigUpdate(){ return new UmpleSourceData().setFileNames("LockManager.ump").setUmpleLines(55).setJavaLines(89).setLengths(2);}
    public UmpleSourceData LockManager_transferMultipleInternal(){ return new UmpleSourceData().setFileNames("LockManager.ump").setUmpleLines(333).setJavaLines(409).setLengths(6);}
    public UmpleSourceData LockManager_hook778(){ return new UmpleSourceData().setFileNames("LockManager.ump").setUmpleLines(615).setJavaLines(748).setLengths(1);}
    public UmpleSourceData LockManager_nWaitersInternal(){ return new UmpleSourceData().setFileNames("LockManager.ump").setUmpleLines(420).setJavaLines(521).setLengths(6);}
    public UmpleSourceData LockManager_hook777(){ return new UmpleSourceData().setFileNames("LockManager.ump").setUmpleLines(612).setJavaLines(743).setLengths(1);}
    public UmpleSourceData LockManager_hook776(){ return new UmpleSourceData().setFileNames("LockManager.ump").setUmpleLines(609).setJavaLines(738).setLengths(1);}
    public UmpleSourceData LockManager_hook775(){ return new UmpleSourceData().setFileNames("LockManager.ump").setUmpleLines(606).setJavaLines(733).setLengths(1);}
    public UmpleSourceData LockManager_lockStat_execute(){ return new UmpleSourceData().setFileNames("Statistics_LockManager_inner.ump").setUmpleLines(9).setJavaLines(35).setLengths(18);}
    public UmpleSourceData LockManager_LockManager(){ return new UmpleSourceData().setFileNames("LockManager.ump").setUmpleLines(35).setJavaLines(57).setLengths(23);}
    public UmpleSourceData LockManager_demoteInternal(){ return new UmpleSourceData().setFileNames("LockManager.ump").setUmpleLines(352).setJavaLines(433).setLengths(4);}
    public UmpleSourceData LockManager_isOwnerInternal(){ return new UmpleSourceData().setFileNames("LockManager.ump").setUmpleLines(386).setJavaLines(477).setLengths(6);}
    public UmpleSourceData LockManager_attemptLockInternal(){ return new UmpleSourceData().setFileNames("Statistics_LockManager.ump","LockManager.ump").setUmpleLines(25, 155).setJavaLines(199, 197).setLengths(2, 26);}
    public UmpleSourceData LockManager_nOwnersInternal(){ return new UmpleSourceData().setFileNames("LockManager.ump").setUmpleLines(437).setJavaLines(543).setLengths(6);}
    public UmpleSourceData LockAttemptResult_LockAttemptResult(){ return new UmpleSourceData().setFileNames("LockManager_static.ump").setUmpleLines(8).setJavaLines(76).setLengths(3);}
    public UmpleSourceData LockManager_lockStat_LockManager_lockStat(){ return new UmpleSourceData().setFileNames("Statistics_LockManager_inner.ump").setUmpleLines(5).setJavaLines(29).setLengths(2);}
    public UmpleSourceData LockManager_isWaiterInternal(){ return new UmpleSourceData().setFileNames("LockManager.ump").setUmpleLines(403).setJavaLines(499).setLengths(6);}
    public UmpleSourceData LockManager_setLockTableDump(){ return new UmpleSourceData().setFileNames("LockManager.ump").setUmpleLines(63).setJavaLines(100).setLengths(1);}
    public UmpleSourceData LockManager_checkNoLatchesHeld(){ return new UmpleSourceData().setFileNames("Latches_LockManager.ump").setUmpleLines(9).setJavaLines(773).setLengths(5);}
    public UmpleSourceData WriteLockInfo_WriteLockInfo(){ return new UmpleSourceData().setFileNames("WriteLockInfo.ump","WriteLockInfo.ump").setUmpleLines(19, 27).setJavaLines(108, 117).setLengths(5, 5);}
    public UmpleSourceData BasicLocker_BasicLocker(){ return new UmpleSourceData().setFileNames("BasicLocker.ump").setUmpleLines(24).setJavaLines(51).setLengths(1);}
    public UmpleSourceData BasicLocker_releaseNonTxnLocks(){ return new UmpleSourceData().setFileNames("BasicLocker.ump").setUmpleLines(114).setJavaLines(172).setLengths(1);}
    public UmpleSourceData BasicLocker_createdNode(){ return new UmpleSourceData().setFileNames("BasicLocker.ump").setUmpleLines(212).setJavaLines(311).setLengths(1);}
    public UmpleSourceData BasicLocker_getOwnerAbortLsn(){ return new UmpleSourceData().setFileNames("BasicLocker.ump").setUmpleLines(68).setJavaLines(108).setLengths(5);}
    public UmpleSourceData BasicLocker_collectStats(){ return new UmpleSourceData().setFileNames("Statistics_BasicLocker.ump").setUmpleLines(8).setJavaLines(338).setLengths(19);}
    public UmpleSourceData BasicLocker_newNonTxnLocker(){ return new UmpleSourceData().setFileNames("BasicLocker.ump").setUmpleLines(107).setJavaLines(162).setLengths(1);}
    public UmpleSourceData BasicLocker_generateId(){ return new UmpleSourceData().setFileNames("BasicLocker.ump").setUmpleLines(31).setJavaLines(61).setLengths(1);}
    public UmpleSourceData BasicLocker_isTransactional(){ return new UmpleSourceData().setFileNames("BasicLocker.ump").setUmpleLines(79).setJavaLines(122).setLengths(1);}
    public UmpleSourceData BasicLocker_getTxnLocker(){ return new UmpleSourceData().setFileNames("BasicLocker.ump").setUmpleLines(100).setJavaLines(152).setLengths(1);}
    public UmpleSourceData BasicLocker_addLock(){ return new UmpleSourceData().setFileNames("BasicLocker.ump").setUmpleLines(184).setJavaLines(277).setLengths(11);}
    public UmpleSourceData BasicLocker_markDeleteAtTxnEnd(){ return new UmpleSourceData().setFileNames("DeleteOp_BasicLocker.ump").setUmpleLines(5).setJavaLines(326).setLengths(3);}
    public UmpleSourceData BasicLocker_registerCursor(){ return new UmpleSourceData().setFileNames("BasicLocker.ump").setUmpleLines(158).setJavaLines(237).setLengths(1);}
    public UmpleSourceData BasicLocker_setHandleLockOwner(){ return new UmpleSourceData().setFileNames("BasicLocker.ump").setUmpleLines(146).setJavaLines(222).setLengths(6);}
    public UmpleSourceData BasicLocker_moveWriteToReadLock(){ return new UmpleSourceData().setFileNames("BasicLocker.ump").setUmpleLines(219).setJavaLines(321).setLengths(1);}
    public UmpleSourceData BasicLocker_checkState(){ return new UmpleSourceData().setFileNames("BasicLocker.ump").setUmpleLines(35).setJavaLines(66).setLengths(1);}
    public UmpleSourceData BasicLocker_getAbortLsn(){ return new UmpleSourceData().setFileNames("BasicLocker.ump").setUmpleLines(170).setJavaLines(257).setLengths(1);}
    public UmpleSourceData BasicLocker_removeLock(){ return new UmpleSourceData().setFileNames("BasicLocker.ump").setUmpleLines(201).setJavaLines(297).setLengths(5);}
    public UmpleSourceData BasicLocker_getWriteLockInfo(){ return new UmpleSourceData().setFileNames("BasicLocker.ump").setUmpleLines(177).setJavaLines(267).setLengths(1);}
    public UmpleSourceData BasicLocker_operationEnd(){ return new UmpleSourceData().setFileNames("BasicLocker.ump","INCompressor_BasicLocker.ump","BasicLocker.ump").setUmpleLines(121, 9, 128).setJavaLines(182, 206, 192).setLengths(1, 7, 12);}
    public UmpleSourceData BasicLocker_getWriteOwnerLocker(){ return new UmpleSourceData().setFileNames("BasicLocker.ump").setUmpleLines(61).setJavaLines(98).setLengths(1);}
    public UmpleSourceData BasicLocker_isReadCommittedIsolation(){ return new UmpleSourceData().setFileNames("BasicLocker.ump").setUmpleLines(93).setJavaLines(142).setLengths(1);}
    public UmpleSourceData BasicLocker_lockInternal(){ return new UmpleSourceData().setFileNames("BasicLocker.ump").setUmpleLines(43).setJavaLines(77).setLengths(12);}
    public UmpleSourceData BasicLocker_isSerializableIsolation(){ return new UmpleSourceData().setFileNames("BasicLocker.ump").setUmpleLines(86).setJavaLines(132).setLengths(1);}
    public UmpleSourceData BasicLocker_unRegisterCursor(){ return new UmpleSourceData().setFileNames("BasicLocker.ump").setUmpleLines(164).setJavaLines(247).setLengths(1);}
    public UmpleSourceData Txn_prepare(){ return new UmpleSourceData().setFileNames("Txn.ump").setUmpleLines(189).setJavaLines(205).setLengths(14);}
    public UmpleSourceData Txn_markDeleteAtTxnEnd_Txn_markDeleteAtTxnEnd(){ return new UmpleSourceData().setFileNames("DeleteOp_Txn_inner.ump").setUmpleLines(5).setJavaLines(29).setLengths(3);}
    public UmpleSourceData Txn_updateMemoryUsage(){ return new UmpleSourceData().setFileNames("MemoryBudget_Txn.ump").setUmpleLines(11).setJavaLines(962).setLengths(6);}
    public UmpleSourceData Txn_addLock_Txn_addLock(){ return new UmpleSourceData().setFileNames("Txn_static.ump").setUmpleLines(13).setJavaLines(30).setLengths(5);}
    public UmpleSourceData Txn_getLogSize(){ return new UmpleSourceData().setFileNames("Txn.ump").setUmpleLines(705).setJavaLines(864).setLengths(1);}
    public UmpleSourceData Txn_commit(){ return new UmpleSourceData().setFileNames("Txn.ump","Txn.ump","Txn.ump").setUmpleLines(206, 221, 228).setJavaLines(223, 242, 252).setLengths(3, 1, 80);}
    public UmpleSourceData Txn_isTransactional(){ return new UmpleSourceData().setFileNames("Txn.ump").setUmpleLines(531).setJavaLines(636).setLengths(1);}
    public UmpleSourceData Txn_getTxnLocker(){ return new UmpleSourceData().setFileNames("Txn.ump").setUmpleLines(552).setJavaLines(666).setLengths(1);}
    public UmpleSourceData Txn_traceCommit(){ return new UmpleSourceData().setFileNames("Txn.ump").setUmpleLines(772).setJavaLines(952).setLengths(1);}
    public UmpleSourceData Txn_setHandleLockOwner(){ return new UmpleSourceData().setFileNames("Txn.ump").setUmpleLines(584).setJavaLines(716).setLengths(17);}
    public UmpleSourceData Txn_logEntryIsTransactional(){ return new UmpleSourceData().setFileNames("Txn.ump").setUmpleLines(746).setJavaLines(920).setLengths(1);}
    public UmpleSourceData Txn_addReadLock(){ return new UmpleSourceData().setFileNames("Txn.ump").setUmpleLines(439).setJavaLines(513).setLengths(15);}
    public UmpleSourceData Txn_checkState(){ return new UmpleSourceData().setFileNames("Txn.ump").setUmpleLines(677).setJavaLines(830).setLengths(12);}
    public UmpleSourceData Txn_traceCommit_Txn_traceCommit(){ return new UmpleSourceData().setFileNames("Txn_static.ump").setUmpleLines(67).setJavaLines(31).setLengths(3);}
    public UmpleSourceData DatabaseCleanupInfo_DatabaseCleanupInfo(){ return new UmpleSourceData().setFileNames("Txn_static.ump").setUmpleLines(7).setJavaLines(61).setLengths(2);}
    public UmpleSourceData Txn_isSerializableIsolation(){ return new UmpleSourceData().setFileNames("Txn.ump").setUmpleLines(538).setJavaLines(646).setLengths(1);}
    public UmpleSourceData Txn_unRegisterCursor(){ return new UmpleSourceData().setFileNames("Txn.ump").setUmpleLines(620).setJavaLines(758).setLengths(14);}
    public UmpleSourceData Txn_cleanupDatabaseImpls(){ return new UmpleSourceData().setFileNames("DeleteOp_Txn.ump").setUmpleLines(31).setJavaLines(1007).setLengths(14);}
    public UmpleSourceData Txn_init(){ return new UmpleSourceData().setFileNames("Txn.ump").setUmpleLines(100).setJavaLines(98).setLengths(20);}
    public UmpleSourceData Txn_releaseNonTxnLocks(){ return new UmpleSourceData().setFileNames("Txn.ump").setUmpleLines(566).setJavaLines(686).setLengths(1);}
    public UmpleSourceData Txn_addLogInfo(){ return new UmpleSourceData().setFileNames("Txn.ump").setUmpleLines(414).setJavaLines(481).setLengths(6);}
    public UmpleSourceData Txn_getInMemorySize(){ return new UmpleSourceData().setFileNames("Txn.ump").setUmpleLines(776).setJavaLines(957).setLengths(1);}
    public UmpleSourceData Txn_clearWriteLocks(){ return new UmpleSourceData().setFileNames("Txn.ump").setUmpleLines(386).setJavaLines(449).setLengths(8);}
    public UmpleSourceData Txn_getAccumulatedDelta(){ return new UmpleSourceData().setFileNames("MemoryBudget_Txn.ump").setUmpleLines(20).setJavaLines(972).setLengths(1);}
    public UmpleSourceData Txn_addLock_execute(){ return new UmpleSourceData().setFileNames("Txn_static.ump","loggingBase_Txn_inner.ump","Derivative_LoggingFine_LoggingBase_Txn_inner.ump","Txn_static.ump","DeleteOp_Txn_inner.ump").setUmpleLines(20, 6, 6, 72, 10).setJavaLines(39, 40, 44, 38, 36).setLengths(40, 1, 8, 1, 16);}
    public UmpleSourceData Txn_setSuspended(){ return new UmpleSourceData().setFileNames("Txn.ump").setUmpleLines(149).setJavaLines(161).setLengths(5);}
    public UmpleSourceData Txn_setDeletedDatabaseState(){ return new UmpleSourceData().setFileNames("DeleteOp_Txn.ump").setUmpleLines(16).setJavaLines(989).setLengths(9);}
    public UmpleSourceData Txn_transferHandleLockToHandleSet(){ return new UmpleSourceData().setFileNames("Txn.ump").setUmpleLines(753).setJavaLines(930).setLengths(13);}
    public UmpleSourceData Txn_abort(){ return new UmpleSourceData().setFileNames("Txn.ump","Txn.ump").setUmpleLines(212, 299).setJavaLines(230, 341).setLengths(3, 1);}
    public UmpleSourceData Txn_removeLock(){ return new UmpleSourceData().setFileNames("Txn.ump").setUmpleLines(454).setJavaLines(538).setLengths(12);}
    public UmpleSourceData Txn_getOnlyAbortable(){ return new UmpleSourceData().setFileNames("Txn.ump").setUmpleLines(670).setJavaLines(820).setLengths(1);}
    public UmpleSourceData Txn_createdNode(){ return new UmpleSourceData().setFileNames("Txn.ump").setUmpleLines(485).setJavaLines(581).setLengths(10);}
    public UmpleSourceData Txn_readFromLog(){ return new UmpleSourceData().setFileNames("Txn.ump").setUmpleLines(720).setJavaLines(885).setLengths(2);}
    public UmpleSourceData Txn_collectStats(){ return new UmpleSourceData().setFileNames("Statistics_Txn.ump").setUmpleLines(7).setJavaLines(1030).setLengths(7);}
    public UmpleSourceData Txn_generateId(){ return new UmpleSourceData().setFileNames("Txn.ump").setUmpleLines(130).setJavaLines(137).setLengths(1);}
    public UmpleSourceData Txn_Txn(){ return new UmpleSourceData().setFileNames("Txn.ump","Txn.ump","Txn.ump").setUmpleLines(89, 94, 123).setJavaLines(85, 91, 127).setLengths(2, 3, 1);}
    public UmpleSourceData Txn_markDeleteAtTxnEnd(){ return new UmpleSourceData().setFileNames("DeleteOp_Txn.ump").setUmpleLines(12).setJavaLines(984).setLengths(1);}
    public UmpleSourceData Txn_registerCursor(){ return new UmpleSourceData().setFileNames("Txn.ump").setUmpleLines(607).setJavaLines(742).setLengths(7);}
    public UmpleSourceData Txn_isHandleLockTransferrable(){ return new UmpleSourceData().setFileNames("Txn.ump").setUmpleLines(640).setJavaLines(781).setLengths(1);}
    public UmpleSourceData Txn_undo(){ return new UmpleSourceData().setFileNames("Txn.ump").setUmpleLines(351).setJavaLines(410).setLengths(35);}
    public UmpleSourceData Txn_writeToLog(){ return new UmpleSourceData().setFileNames("Txn.ump").setUmpleLines(712).setJavaLines(874).setLengths(2);}
    public UmpleSourceData Txn_getAbortLsn(){ return new UmpleSourceData().setFileNames("Txn.ump").setUmpleLines(501).setJavaLines(600).setLengths(11);}
    public UmpleSourceData Txn_getWriteLockInfo(){ return new UmpleSourceData().setFileNames("Txn.ump").setUmpleLines(518).setJavaLines(620).setLengths(7);}
    public UmpleSourceData Txn_operationEnd(){ return new UmpleSourceData().setFileNames("Txn.ump","Txn.ump").setUmpleLines(572, 578).setJavaLines(696, 706).setLengths(1, 1);}
    public UmpleSourceData Txn_isReadCommittedIsolation(){ return new UmpleSourceData().setFileNames("Txn.ump").setUmpleLines(545).setJavaLines(656).setLengths(1);}
    public UmpleSourceData Txn_close(){ return new UmpleSourceData().setFileNames("Txn.ump").setUmpleLines(694).setJavaLines(850).setLengths(5);}
    public UmpleSourceData Txn_clearReadLocks(){ return new UmpleSourceData().setFileNames("Txn.ump").setUmpleLines(397).setJavaLines(461).setLengths(11);}
    public UmpleSourceData Txn_isSuspended(){ return new UmpleSourceData().setFileNames("Txn.ump").setUmpleLines(157).setJavaLines(170).setLengths(1);}
    public UmpleSourceData Txn_getLastLsn(){ return new UmpleSourceData().setFileNames("Txn.ump").setUmpleLines(137).setJavaLines(147).setLengths(1);}
    public UmpleSourceData Txn_checkCursorsForClose(){ return new UmpleSourceData().setFileNames("Txn.ump").setUmpleLines(648).setJavaLines(792).setLengths(8);}
    public UmpleSourceData Txn_newNonTxnLocker(){ return new UmpleSourceData().setFileNames("Txn.ump").setUmpleLines(559).setJavaLines(676).setLengths(1);}
    public UmpleSourceData Txn_addLock(){ return new UmpleSourceData().setFileNames("Txn.ump").setUmpleLines(435).setJavaLines(508).setLengths(1);}
    public UmpleSourceData Txn_abortInternal(){ return new UmpleSourceData().setFileNames("Txn.ump").setUmpleLines(303).setJavaLines(346).setLengths(55);}
    public UmpleSourceData Txn_setOnlyAbortable(){ return new UmpleSourceData().setFileNames("Txn.ump").setUmpleLines(662).setJavaLines(809).setLengths(2);}
    public UmpleSourceData Txn_setPrepared(){ return new UmpleSourceData().setFileNames("Txn.ump").setUmpleLines(141).setJavaLines(152).setLengths(5);}
    public UmpleSourceData Txn_getFirstActiveLsn(){ return new UmpleSourceData().setFileNames("Txn.ump").setUmpleLines(426).setJavaLines(496).setLengths(3);}
    public UmpleSourceData Txn_moveWriteToReadLock(){ return new UmpleSourceData().setFileNames("Txn.ump").setUmpleLines(469).setJavaLines(559).setLengths(13);}
    public UmpleSourceData Txn_getTransactionId(){ return new UmpleSourceData().setFileNames("Txn.ump").setUmpleLines(739).setJavaLines(910).setLengths(1);}
    public UmpleSourceData Txn_dumpLog(){ return new UmpleSourceData().setFileNames("Txn.ump").setUmpleLines(728).setJavaLines(896).setLengths(5);}
    public UmpleSourceData Txn_lockInternal(){ return new UmpleSourceData().setFileNames("Txn.ump").setUmpleLines(167).setJavaLines(182).setLengths(19);}
    public UmpleSourceData RunRecoveryException_setAlreadyThrown(){ return new UmpleSourceData().setFileNames("RunRecoveryException.ump").setUmpleLines(36).setJavaLines(65).setLengths(1);}
    public UmpleSourceData RunRecoveryException_RunRecoveryException(){ return new UmpleSourceData().setFileNames("RunRecoveryException.ump","RunRecoveryException.ump","RunRecoveryException.ump","RunRecoveryException.ump").setUmpleLines(10, 15, 20, 25).setJavaLines(34, 40, 46, 52).setLengths(2, 2, 2, 2);}
    public UmpleSourceData RunRecoveryException_toString(){ return new UmpleSourceData().setFileNames("RunRecoveryException.ump").setUmpleLines(40).setJavaLines(70).setLengths(5);}
    public UmpleSourceData RunRecoveryException_invalidate(){ return new UmpleSourceData().setFileNames("RunRecoveryException.ump").setUmpleLines(30).setJavaLines(58).setLengths(3);}
    public UmpleSourceData TinyHashSet_add(){ return new UmpleSourceData().setFileNames("TinyHashSet.ump").setUmpleLines(40).setJavaLines(62).setLengths(12);}
    public UmpleSourceData SingleElementIterator_SingleElementIterator(){ return new UmpleSourceData().setFileNames("TinyHashSet_static.ump").setUmpleLines(9).setJavaLines(80).setLengths(3);}
    public UmpleSourceData SingleElementIterator_next(){ return new UmpleSourceData().setFileNames("TinyHashSet_static.ump").setUmpleLines(17).setJavaLines(92).setLengths(5);}
    public UmpleSourceData TinyHashSet_iterator(){ return new UmpleSourceData().setFileNames("TinyHashSet.ump").setUmpleLines(68).setJavaLines(92).setLengths(6);}
    public UmpleSourceData TinyHashSet_size(){ return new UmpleSourceData().setFileNames("TinyHashSet.ump").setUmpleLines(14).setJavaLines(34).setLengths(7);}
    public UmpleSourceData SingleElementIterator_hasNext(){ return new UmpleSourceData().setFileNames("TinyHashSet_static.ump").setUmpleLines(14).setJavaLines(87).setLengths(1);}
    public UmpleSourceData TinyHashSet_copy(){ return new UmpleSourceData().setFileNames("TinyHashSet.ump").setUmpleLines(55).setJavaLines(78).setLengths(10);}
    public UmpleSourceData TinyHashSet_remove(){ return new UmpleSourceData().setFileNames("TinyHashSet.ump","TinyHashSet_static.ump").setUmpleLines(24, 24).setJavaLines(45, 101).setLengths(13, 4);}
    public UmpleSourceData LevelOrderedINMap_putIN(){ return new UmpleSourceData().setFileNames("LevelOrderedINMap.ump").setUmpleLines(11).setJavaLines(34).setLengths(7);}
    public UmpleSourceData EventTrace_EventTrace(){ return new UmpleSourceData().setFileNames("EventTrace.ump","EventTrace.ump").setUmpleLines(20, 24).setJavaLines(30, 35).setLengths(1, 1);}
    public UmpleSourceData EventTrace_toString(){ return new UmpleSourceData().setFileNames("EventTrace.ump").setUmpleLines(28).setJavaLines(40).setLengths(1);}
    public UmpleSourceData JarMain_usage(){ return new UmpleSourceData().setFileNames("JarMain.ump").setUmpleLines(26).setJavaLines(48).setLengths(3);}
    public UmpleSourceData JarMain_main(){ return new UmpleSourceData().setFileNames("JarMain.ump").setUmpleLines(11).setJavaLines(30).setLengths(12);}
    public UmpleSourceData CmdUtil_formatEntry(){ return new UmpleSourceData().setFileNames("CmdUtil.ump").setUmpleLines(32).setJavaLines(60).setLengths(24);}
    public UmpleSourceData CmdUtil_getJavaCommand(){ return new UmpleSourceData().setFileNames("CmdUtil.ump").setUmpleLines(80).setJavaLines(124).setLengths(3);}
    public UmpleSourceData CmdUtil_getArg(){ return new UmpleSourceData().setFileNames("CmdUtil.ump").setUmpleLines(13).setJavaLines(37).setLengths(5);}
    public UmpleSourceData CmdUtil_isPrint(){ return new UmpleSourceData().setFileNames("CmdUtil.ump").setUmpleLines(59).setJavaLines(88).setLengths(1);}
    public UmpleSourceData CmdUtil_makeUtilityEnvironment(){ return new UmpleSourceData().setFileNames("CmdUtil.ump").setUmpleLines(66).setJavaLines(98).setLengths(17);}
    public UmpleSourceData CmdUtil_readLongNumber(){ return new UmpleSourceData().setFileNames("CmdUtil.ump").setUmpleLines(24).setJavaLines(51).setLengths(5);}
    public UmpleSourceData Adler32_getValue(){ return new UmpleSourceData().setFileNames("Adler32.ump").setUmpleLines(63).setJavaLines(92).setLengths(1);}
    public UmpleSourceData Adler32_makeChecksum(){ return new UmpleSourceData().setFileNames("Adler32.ump").setUmpleLines(15).setJavaLines(32).setLengths(5);}
    public UmpleSourceData Adler32_update(){ return new UmpleSourceData().setFileNames("Adler32.ump","Adler32.ump").setUmpleLines(26, 37).setJavaLines(46, 60).setLengths(5, 13);}
    public UmpleSourceData Adler32_reset(){ return new UmpleSourceData().setFileNames("Adler32.ump").setUmpleLines(56).setJavaLines(82).setLengths(1);}
    public UmpleSourceData NotImplementedYetException_NotImplementedYetException(){ return new UmpleSourceData().setFileNames("NotImplementedYetException.ump","NotImplementedYetException.ump").setUmpleLines(7, 11).setJavaLines(30, 35).setLengths(1, 1);}
    public UmpleSourceData DaemonThread_init(){ return new UmpleSourceData().setFileNames("DaemonThread.ump").setUmpleLines(42).setJavaLines(49).setLengths(8);}
    public UmpleSourceData DaemonThread_addToQueueAlreadyLatched(){ return new UmpleSourceData().setFileNames("DaemonThread.ump").setUmpleLines(114).setJavaLines(149).setLengths(1);}
    public UmpleSourceData DaemonThread_getQueueSize(){ return new UmpleSourceData().setFileNames("Latches_DaemonThread.ump","DaemonThread.ump").setUmpleLines(24, 109).setJavaLines(141, 139).setLengths(1, 2);}
    public UmpleSourceData DaemonThread_DaemonThread(){ return new UmpleSourceData().setFileNames("DaemonThread.ump","DaemonThread.ump").setUmpleLines(35, 38).setJavaLines(39, 44).setLengths(1, 1);}
    public UmpleSourceData DaemonThread_wakeup(){ return new UmpleSourceData().setFileNames("DaemonThread.ump").setUmpleLines(118).setJavaLines(154).setLengths(5);}
    public UmpleSourceData DaemonThread_isShutdownRequested(){ return new UmpleSourceData().setFileNames("DaemonThread.ump").setUmpleLines(204).setJavaLines(248).setLengths(1);}
    public UmpleSourceData DaemonThread_hook856(){ return new UmpleSourceData().setFileNames("DaemonThread.ump").setUmpleLines(222).setJavaLines(273).setLengths(1);}
    public UmpleSourceData DaemonThread_run(){ return new UmpleSourceData().setFileNames("DaemonThread.ump").setUmpleLines(126).setJavaLines(163).setLengths(66);}
    public UmpleSourceData DaemonThread_runOrPause(){ return new UmpleSourceData().setFileNames("DaemonThread.ump").setUmpleLines(60).setJavaLines(76).setLengths(12);}
    public UmpleSourceData DaemonThread_getNWakeupRequests(){ return new UmpleSourceData().setFileNames("DaemonThread.ump").setUmpleLines(218).setJavaLines(268).setLengths(1);}
    public UmpleSourceData DaemonThread_nDeadlockRetries(){ return new UmpleSourceData().setFileNames("DaemonThread.ump").setUmpleLines(192).setJavaLines(238).setLengths(1);}
    public UmpleSourceData DaemonThread_addToQueue(){ return new UmpleSourceData().setFileNames("Latches_DaemonThread.ump","Latches_DaemonThread.ump","DaemonThread.ump").setUmpleLines(15, 20, 104).setJavaLines(128, 134, 126).setLengths(2, 1, 2);}
    public UmpleSourceData DaemonThread_isRunning(){ return new UmpleSourceData().setFileNames("DaemonThread.ump").setUmpleLines(211).setJavaLines(258).setLengths(1);}
    public UmpleSourceData DaemonThread_getThread(){ return new UmpleSourceData().setFileNames("DaemonThread.ump").setUmpleLines(53).setJavaLines(66).setLengths(1);}
    public UmpleSourceData DaemonThread_toString(){ return new UmpleSourceData().setFileNames("DaemonThread.ump").setUmpleLines(98).setJavaLines(119).setLengths(3);}
    public UmpleSourceData DaemonThread_requestShutdown(){ return new UmpleSourceData().setFileNames("DaemonThread.ump").setUmpleLines(75).setJavaLines(92).setLengths(1);}
    public UmpleSourceData DaemonThread_shutdown(){ return new UmpleSourceData().setFileNames("DaemonThread.ump").setUmpleLines(82).setJavaLines(102).setLengths(13);}
    public UmpleSourceData DbLsn_getFileOffset(){ return new UmpleSourceData().setFileNames("DbLsn.ump").setUmpleLines(42).setJavaLines(68).setLengths(1);}
    public UmpleSourceData DbLsn_DbLsn(){ return new UmpleSourceData().setFileNames("DbLsn.ump").setUmpleLines(16).setJavaLines(33).setLengths(1);}
    public UmpleSourceData DbLsn_getWithCleaningDistance(){ return new UmpleSourceData().setFileNames("DbLsn.ump").setUmpleLines(109).setJavaLines(146).setLengths(20);}
    public UmpleSourceData DbLsn_getTransactionIdX(){ return new UmpleSourceData().setFileNames("DbLsn.ump").setUmpleLines(149).setJavaLines(193).setLengths(1);}
    public UmpleSourceData DbLsn_compareLong(){ return new UmpleSourceData().setFileNames("DbLsn.ump").setUmpleLines(46).setJavaLines(73).setLengths(7);}
    public UmpleSourceData DbLsn_compareTo(){ return new UmpleSourceData().setFileNames("DbLsn.ump").setUmpleLines(56).setJavaLines(84).setLengths(10);}
    public UmpleSourceData DbLsn_dumpString(){ return new UmpleSourceData().setFileNames("DbLsn.ump").setUmpleLines(78).setJavaLines(109).setLengths(4);}
    public UmpleSourceData DbLsn_longToLsn(){ return new UmpleSourceData().setFileNames("DbLsn.ump").setUmpleLines(23).setJavaLines(43).setLengths(4);}
    public UmpleSourceData DbLsn_calcDiff(){ return new UmpleSourceData().setFileNames("DbLsn.ump").setUmpleLines(132).setJavaLines(170).setLengths(4);}
    public UmpleSourceData DbLsn_getNoFormatString(){ return new UmpleSourceData().setFileNames("DbLsn.ump").setUmpleLines(74).setJavaLines(104).setLengths(1);}
    public UmpleSourceData DbLsn_makeLsn(){ return new UmpleSourceData().setFileNames("DbLsn.ump").setUmpleLines(19).setJavaLines(38).setLengths(1);}
    public UmpleSourceData DbLsn_getNoCleaningDistance(){ return new UmpleSourceData().setFileNames("DbLsn.ump").setUmpleLines(88).setJavaLines(122).setLengths(15);}
    public UmpleSourceData DbLsn_toString(){ return new UmpleSourceData().setFileNames("DbLsn.ump").setUmpleLines(69).setJavaLines(98).setLengths(2);}
    public UmpleSourceData DbLsn_logEntryIsTransactionalX(){ return new UmpleSourceData().setFileNames("DbLsn.ump").setUmpleLines(142).setJavaLines(183).setLengths(1);}
    public UmpleSourceData DbLsn_getFileNumber(){ return new UmpleSourceData().setFileNames("DbLsn.ump").setUmpleLines(34).setJavaLines(57).setLengths(1);}
    public UmpleSourceData InternalException_InternalException(){ return new UmpleSourceData().setFileNames("InternalException.ump","InternalException.ump").setUmpleLines(8, 12).setJavaLines(35, 40).setLengths(1, 1);}
    public UmpleSourceData TestHookExecute_doHookIfSet(){ return new UmpleSourceData().setFileNames("TestHookExecute.ump").setUmpleLines(6).setJavaLines(29).setLengths(4);}
    public UmpleSourceData PropUtil_microsToMillis(){ return new UmpleSourceData().setFileNames("PropUtil.ump").setUmpleLines(54).setJavaLines(87).setLengths(1);}
    public UmpleSourceData PropUtil_validateProp(){ return new UmpleSourceData().setFileNames("PropUtil.ump").setUmpleLines(45).setJavaLines(75).setLengths(3);}
    public UmpleSourceData PropUtil_getBoolean(){ return new UmpleSourceData().setFileNames("PropUtil.ump").setUmpleLines(13).setJavaLines(38).setLengths(6);}
    public UmpleSourceData PropUtil_validateProps(){ return new UmpleSourceData().setFileNames("PropUtil.ump").setUmpleLines(27).setJavaLines(54).setLengths(12);}
    public UmpleSourceData BitMap_BitMap(){ return new UmpleSourceData().setFileNames("BitMap.ump").setUmpleLines(16).setJavaLines(33).setLengths(1);}
    public UmpleSourceData BitMap_set(){ return new UmpleSourceData().setFileNames("BitMap.ump").setUmpleLines(20).setJavaLines(38).setLengths(9);}
    public UmpleSourceData BitMap_getBitSet(){ return new UmpleSourceData().setFileNames("BitMap.ump").setUmpleLines(44).setJavaLines(64).setLengths(9);}
    public UmpleSourceData BitMap_getIntIndex(){ return new UmpleSourceData().setFileNames("BitMap.ump").setUmpleLines(56).setJavaLines(77).setLengths(1);}
    public UmpleSourceData BitMap_get(){ return new UmpleSourceData().setFileNames("BitMap.ump").setUmpleLines(32).setJavaLines(51).setLengths(9);}
    public UmpleSourceData BitMap_getNumSegments(){ return new UmpleSourceData().setFileNames("BitMap.ump").setUmpleLines(60).setJavaLines(82).setLengths(1);}
    public UmpleSourceData BitMap_cardinality(){ return new UmpleSourceData().setFileNames("BitMap.ump").setUmpleLines(64).setJavaLines(87).setLengths(7);}
    public UmpleSourceData DbScavenger_DbScavenger(){ return new UmpleSourceData().setFileNames("DbScavenger.ump").setUmpleLines(55).setJavaLines(59).setLengths(6);}
    public UmpleSourceData DbScavenger_scavengeDbTree(){ return new UmpleSourceData().setFileNames("DbScavenger.ump").setUmpleLines(103).setJavaLines(112).setLengths(19);}
    public UmpleSourceData DbScavenger_processDbTreeEntry(){ return new UmpleSourceData().setFileNames("DbScavenger.ump").setUmpleLines(176).setJavaLines(188).setLengths(25);}
    public UmpleSourceData DbScavenger_scavenge(){ return new UmpleSourceData().setFileNames("DbScavenger.ump").setUmpleLines(204).setJavaLines(217).setLengths(15);}
    public UmpleSourceData DbScavenger_closeOutputStreams(){ return new UmpleSourceData().setFileNames("DbScavenger.ump").setUmpleLines(266).setJavaLines(282).setLengths(6);}
    public UmpleSourceData DbScavenger_setDumpCorruptedBounds(){ return new UmpleSourceData().setFileNames("DbScavenger.ump").setUmpleLines(67).setJavaLines(74).setLengths(1);}
    public UmpleSourceData DbScavenger_getOutputStream(){ return new UmpleSourceData().setFileNames("DbScavenger.ump").setUmpleLines(242).setJavaLines(257).setLengths(21);}
    public UmpleSourceData DbScavenger_dump(){ return new UmpleSourceData().setFileNames("DbScavenger.ump").setUmpleLines(71).setJavaLines(79).setLengths(29);}
    public UmpleSourceData DbScavenger_reportProgress(){ return new UmpleSourceData().setFileNames("DbScavenger.ump").setUmpleLines(125).setJavaLines(135).setLengths(10);}
    public UmpleSourceData DbScavenger_checkProcessEntry(){ return new UmpleSourceData().setFileNames("DbScavenger.ump").setUmpleLines(138).setJavaLines(149).setLengths(35);}
    public UmpleSourceData DbScavenger_processRegularEntry(){ return new UmpleSourceData().setFileNames("DbScavenger.ump").setUmpleLines(222).setJavaLines(236).setLengths(17);}
    public UmpleSourceData Transaction_setName(){ return new UmpleSourceData().setFileNames("Transaction.ump").setUmpleLines(100).setJavaLines(145).setLengths(1);}
    public UmpleSourceData Transaction_commitSync(){ return new UmpleSourceData().setFileNames("Transaction.ump").setUmpleLines(56).setJavaLines(85).setLengths(1);}
    public UmpleSourceData Transaction_commitNoSync(){ return new UmpleSourceData().setFileNames("Transaction.ump").setUmpleLines(63).setJavaLines(95).setLengths(1);}
    public UmpleSourceData Transaction_getName(){ return new UmpleSourceData().setFileNames("Transaction.ump").setUmpleLines(107).setJavaLines(155).setLengths(1);}
    public UmpleSourceData Transaction_getLocker(){ return new UmpleSourceData().setFileNames("Transaction.ump").setUmpleLines(143).setJavaLines(197).setLengths(5);}
    public UmpleSourceData Transaction_commitWriteNoSync(){ return new UmpleSourceData().setFileNames("Transaction.ump").setUmpleLines(70).setJavaLines(105).setLengths(1);}
    public UmpleSourceData Transaction_commit(){ return new UmpleSourceData().setFileNames("Transaction.ump").setUmpleLines(46).setJavaLines(72).setLengths(4);}
    public UmpleSourceData Transaction_getId(){ return new UmpleSourceData().setFileNames("Transaction.ump").setUmpleLines(39).setJavaLines(62).setLengths(1);}
    public UmpleSourceData Transaction_doCommit(){ return new UmpleSourceData().setFileNames("Transaction.ump").setUmpleLines(74).setJavaLines(110).setLengths(4);}
    public UmpleSourceData Transaction_setTxnTimeout(){ return new UmpleSourceData().setFileNames("Transaction.ump").setUmpleLines(84).setJavaLines(123).setLengths(2);}
    public UmpleSourceData Transaction_Transaction(){ return new UmpleSourceData().setFileNames("Transaction.ump").setUmpleLines(20).setJavaLines(37).setLengths(3);}
    public UmpleSourceData Transaction_abort(){ return new UmpleSourceData().setFileNames("Transaction.ump").setUmpleLines(29).setJavaLines(49).setLengths(4);}
    public UmpleSourceData Transaction_hashCode(){ return new UmpleSourceData().setFileNames("Transaction.ump").setUmpleLines(111).setJavaLines(160).setLengths(1);}
    public UmpleSourceData Transaction_equals(){ return new UmpleSourceData().setFileNames("Transaction.ump").setUmpleLines(115).setJavaLines(165).setLengths(10);}
    public UmpleSourceData Transaction_toString(){ return new UmpleSourceData().setFileNames("Transaction.ump").setUmpleLines(128).setJavaLines(179).setLengths(9);}
    public UmpleSourceData Transaction_getTxn(){ return new UmpleSourceData().setFileNames("Transaction.ump").setUmpleLines(151).setJavaLines(206).setLengths(1);}
    public UmpleSourceData Transaction_setLockTimeout(){ return new UmpleSourceData().setFileNames("Transaction.ump").setUmpleLines(92).setJavaLines(134).setLengths(2);}
    public UmpleSourceData Transaction_checkEnv(){ return new UmpleSourceData().setFileNames("Transaction.ump").setUmpleLines(158).setJavaLines(216).setLengths(1);}
    public UmpleSourceData SecondaryConfig_getForeignKeyNullifier(){ return new UmpleSourceData().setFileNames("SecondaryConfig.ump").setUmpleLines(112).setJavaLines(159).setLengths(1);}
    public UmpleSourceData SecondaryConfig_SecondaryConfig(){ return new UmpleSourceData().setFileNames("SecondaryConfig.ump").setUmpleLines(28).setJavaLines(38).setLengths(1);}
    public UmpleSourceData SecondaryConfig_setKeyCreator(){ return new UmpleSourceData().setFileNames("SecondaryConfig.ump").setUmpleLines(34).setJavaLines(48).setLengths(1);}
    public UmpleSourceData SecondaryConfig_equalOrBothNull(){ return new UmpleSourceData().setFileNames("SecondaryConfig.ump").setUmpleLines(165).setJavaLines(226).setLengths(1);}
    public UmpleSourceData SecondaryConfig_getMultiKeyCreator(){ return new UmpleSourceData().setFileNames("SecondaryConfig.ump").setUmpleLines(55).setJavaLines(78).setLengths(1);}
    public UmpleSourceData SecondaryConfig_setForeignKeyDatabase(){ return new UmpleSourceData().setFileNames("SecondaryConfig.ump").setUmpleLines(76).setJavaLines(108).setLengths(1);}
    public UmpleSourceData SecondaryConfig_genSecondaryConfigMismatchMessage(){ return new UmpleSourceData().setFileNames("SecondaryConfig.ump").setUmpleLines(170).setJavaLines(231).setLengths(24);}
    public UmpleSourceData SecondaryConfig_setMultiKeyCreator(){ return new UmpleSourceData().setFileNames("SecondaryConfig.ump").setUmpleLines(48).setJavaLines(68).setLengths(1);}
    public UmpleSourceData SecondaryConfig_setForeignMultiKeyNullifier(){ return new UmpleSourceData().setFileNames("SecondaryConfig.ump").setUmpleLines(119).setJavaLines(169).setLengths(1);}
    public UmpleSourceData SecondaryConfig_getForeignMultiKeyNullifier(){ return new UmpleSourceData().setFileNames("SecondaryConfig.ump").setUmpleLines(126).setJavaLines(179).setLengths(1);}
    public UmpleSourceData SecondaryConfig_getKeyCreator(){ return new UmpleSourceData().setFileNames("SecondaryConfig.ump").setUmpleLines(41).setJavaLines(58).setLengths(1);}
    public UmpleSourceData SecondaryConfig_setForeignKeyDeleteAction(){ return new UmpleSourceData().setFileNames("SecondaryConfig.ump").setUmpleLines(90).setJavaLines(128).setLengths(2);}
    public UmpleSourceData SecondaryConfig_getAllowPopulate(){ return new UmpleSourceData().setFileNames("SecondaryConfig.ump").setUmpleLines(69).setJavaLines(98).setLengths(1);}
    public UmpleSourceData SecondaryConfig_setForeignKeyNullifier(){ return new UmpleSourceData().setFileNames("SecondaryConfig.ump").setUmpleLines(105).setJavaLines(149).setLengths(1);}
    public UmpleSourceData SecondaryConfig_setAllowPopulate(){ return new UmpleSourceData().setFileNames("SecondaryConfig.ump").setUmpleLines(62).setJavaLines(88).setLengths(1);}
    public UmpleSourceData SecondaryConfig_getImmutableSecondaryKey(){ return new UmpleSourceData().setFileNames("SecondaryConfig.ump").setUmpleLines(140).setJavaLines(199).setLengths(1);}
    public UmpleSourceData SecondaryConfig_setImmutableSecondaryKey(){ return new UmpleSourceData().setFileNames("SecondaryConfig.ump").setUmpleLines(133).setJavaLines(189).setLengths(1);}
    public UmpleSourceData SecondaryConfig_getForeignKeyDatabase(){ return new UmpleSourceData().setFileNames("SecondaryConfig.ump").setUmpleLines(83).setJavaLines(118).setLengths(1);}
    public UmpleSourceData SecondaryConfig_getForeignKeyDeleteAction(){ return new UmpleSourceData().setFileNames("SecondaryConfig.ump").setUmpleLines(98).setJavaLines(139).setLengths(1);}
    public UmpleSourceData SecondaryConfig_validate(){ return new UmpleSourceData().setFileNames("SecondaryConfig.ump").setUmpleLines(144).setJavaLines(204).setLengths(18);}
    public UmpleSourceData DatabaseNotFoundException_DatabaseNotFoundException(){ return new UmpleSourceData().setFileNames("DatabaseNotFoundException.ump","DatabaseNotFoundException.ump","DatabaseNotFoundException.ump","DatabaseNotFoundException.ump").setUmpleLines(7, 11, 15, 19).setJavaLines(33, 38, 43, 48).setLengths(1, 1, 1, 1);}
    public UmpleSourceData CleanerFileReader_getIN(){ return new UmpleSourceData().setFileNames("CleanerFileReader.ump").setUmpleLines(129).setJavaLines(170).setLengths(1);}
    public UmpleSourceData CleanerFileReader_getKey(){ return new UmpleSourceData().setFileNames("CleanerFileReader.ump").setUmpleLines(149).setJavaLines(196).setLengths(1);}
    public UmpleSourceData CleanerFileReader_getLN(){ return new UmpleSourceData().setFileNames("CleanerFileReader.ump").setUmpleLines(122).setJavaLines(160).setLengths(1);}
    public UmpleSourceData CleanerFileReader_isRoot(){ return new UmpleSourceData().setFileNames("CleanerFileReader.ump").setUmpleLines(115).setJavaLines(150).setLengths(1);}
    public UmpleSourceData CleanerFileReader_getDatabaseId(){ return new UmpleSourceData().setFileNames("CleanerFileReader.ump").setUmpleLines(136).setJavaLines(180).setLengths(7);}
    public UmpleSourceData CleanerFileReader_CleanerFileReader(){ return new UmpleSourceData().setFileNames("CleanerFileReader.ump").setUmpleLines(40).setJavaLines(56).setLengths(18);}
    public UmpleSourceData CleanerFileReader_isTargetEntry(){ return new UmpleSourceData().setFileNames("CleanerFileReader.ump").setUmpleLines(78).setJavaLines(101).setLengths(9);}
    public UmpleSourceData CleanerFileReader_getDupTreeKey(){ return new UmpleSourceData().setFileNames("CleanerFileReader.ump").setUmpleLines(156).setJavaLines(206).setLengths(1);}
    public UmpleSourceData CleanerFileReader_initStartingPosition(){ return new UmpleSourceData().setFileNames("CleanerFileReader.ump").setUmpleLines(68).setJavaLines(88).setLengths(4);}
    public UmpleSourceData CleanerFileReader_processEntry(){ return new UmpleSourceData().setFileNames("CleanerFileReader.ump").setUmpleLines(93).setJavaLines(119).setLengths(2);}
    public UmpleSourceData CleanerFileReader_isLN(){ return new UmpleSourceData().setFileNames("CleanerFileReader.ump").setUmpleLines(108).setJavaLines(140).setLengths(1);}
    public UmpleSourceData CleanerFileReader_isIN(){ return new UmpleSourceData().setFileNames("CleanerFileReader.ump").setUmpleLines(101).setJavaLines(130).setLengths(1);}
    public UmpleSourceData CleanerFileReader_addTargetType(){ return new UmpleSourceData().setFileNames("CleanerFileReader.ump").setUmpleLines(61).setJavaLines(78).setLengths(1);}
    public UmpleSourceData LNFileReader_getKey(){ return new UmpleSourceData().setFileNames("LNFileReader.ump").setUmpleLines(89).setJavaLines(131).setLengths(1);}
    public UmpleSourceData LNFileReader_getTxnPrepareXid(){ return new UmpleSourceData().setFileNames("LNFileReader.ump").setUmpleLines(121).setJavaLines(176).setLengths(1);}
    public UmpleSourceData LNFileReader_getLN(){ return new UmpleSourceData().setFileNames("LNFileReader.ump").setUmpleLines(75).setJavaLines(111).setLengths(1);}
    public UmpleSourceData LNFileReader_getNodeId(){ return new UmpleSourceData().setFileNames("LNFileReader.ump").setUmpleLines(146).setJavaLines(211).setLengths(1);}
    public UmpleSourceData LNFileReader_getTxnCommitId(){ return new UmpleSourceData().setFileNames("LNFileReader.ump").setUmpleLines(139).setJavaLines(201).setLengths(1);}
    public UmpleSourceData LNFileReader_getDatabaseId(){ return new UmpleSourceData().setFileNames("LNFileReader.ump").setUmpleLines(82).setJavaLines(121).setLengths(1);}
    public UmpleSourceData LNFileReader_getTxnAbortId(){ return new UmpleSourceData().setFileNames("LNFileReader.ump").setUmpleLines(132).setJavaLines(191).setLengths(1);}
    public UmpleSourceData LNFileReader_isPrepare(){ return new UmpleSourceData().setFileNames("LNFileReader.ump").setUmpleLines(107).setJavaLines(156).setLengths(1);}
    public UmpleSourceData LNFileReader_isAbort(){ return new UmpleSourceData().setFileNames("LNFileReader.ump").setUmpleLines(125).setJavaLines(181).setLengths(1);}
    public UmpleSourceData LNFileReader_LNFileReader(){ return new UmpleSourceData().setFileNames("LNFileReader.ump").setUmpleLines(35).setJavaLines(58).setLengths(2);}
    public UmpleSourceData LNFileReader_getTxnPrepareId(){ return new UmpleSourceData().setFileNames("LNFileReader.ump").setUmpleLines(114).setJavaLines(166).setLengths(1);}
    public UmpleSourceData LNFileReader_isTargetEntry(){ return new UmpleSourceData().setFileNames("LNFileReader.ump").setUmpleLines(47).setJavaLines(74).setLengths(7);}
    public UmpleSourceData LNFileReader_getDupTreeKey(){ return new UmpleSourceData().setFileNames("LNFileReader.ump").setUmpleLines(96).setJavaLines(141).setLengths(1);}
    public UmpleSourceData LNFileReader_getTxnId(){ return new UmpleSourceData().setFileNames("LNFileReader.ump").setUmpleLines(103).setJavaLines(151).setLengths(1);}
    public UmpleSourceData LNFileReader_getAbortLsn(){ return new UmpleSourceData().setFileNames("LNFileReader.ump").setUmpleLines(153).setJavaLines(221).setLengths(1);}
    public UmpleSourceData LNFileReader_getAbortKnownDeleted(){ return new UmpleSourceData().setFileNames("LNFileReader.ump").setUmpleLines(160).setJavaLines(231).setLengths(1);}
    public UmpleSourceData LNFileReader_processEntry(){ return new UmpleSourceData().setFileNames("LNFileReader.ump").setUmpleLines(60).setJavaLines(90).setLengths(2);}
    public UmpleSourceData LNFileReader_isLN(){ return new UmpleSourceData().setFileNames("LNFileReader.ump").setUmpleLines(68).setJavaLines(101).setLengths(1);}
    public UmpleSourceData LNFileReader_addTargetType(){ return new UmpleSourceData().setFileNames("LNFileReader.ump").setUmpleLines(40).setJavaLines(64).setLengths(1);}
    public UmpleSourceData LogException_LogException(){ return new UmpleSourceData().setFileNames("LogException.ump","LogException.ump").setUmpleLines(8, 12).setJavaLines(35, 40).setLengths(1, 1);}
    public UmpleSourceData LogBufferPool_getWriteBuffer(){ return new UmpleSourceData().setFileNames("LogBufferPool.ump").setUmpleLines(64).setJavaLines(97).setLengths(9);}
    public UmpleSourceData LogBufferPool_writeCompleted(){ return new UmpleSourceData().setFileNames("LogBufferPool.ump").setUmpleLines(126).setJavaLines(193).setLengths(4);}
    public UmpleSourceData LogBufferPool_hook486(){ return new UmpleSourceData().setFileNames("LogBufferPool.ump").setUmpleLines(166).setJavaLines(242).setLengths(1);}
    public UmpleSourceData LogBufferPool_hook485(){ return new UmpleSourceData().setFileNames("LogBufferPool.ump").setUmpleLines(163).setJavaLines(237).setLengths(1);}
    public UmpleSourceData LogBufferPool_writeBufferToFile(){ return new UmpleSourceData().setFileNames("LogBufferPool.ump").setUmpleLines(80).setJavaLines(116).setLengths(68);}
    public UmpleSourceData LogBufferPool_loadStats(){ return new UmpleSourceData().setFileNames("Statistics_LogBufferPool.ump","Statistics_LogBufferPool.ump").setUmpleLines(46, 11).setJavaLines(284, 282).setLengths(2, 30);}
    public UmpleSourceData LogBufferPool_reset(){ return new UmpleSourceData().setFileNames("Latches_LogBufferPool.ump","LogBufferPool.ump").setUmpleLines(18, 41).setJavaLines(84, 63).setLengths(2, 19);}
    public UmpleSourceData LogBufferPool_getReadBuffer(){ return new UmpleSourceData().setFileNames("LogBufferPool.ump").setUmpleLines(137).setJavaLines(207).setLengths(26);}
    public UmpleSourceData LogBufferPool_hook488(){ return new UmpleSourceData().setFileNames("LogBufferPool.ump").setUmpleLines(198).setJavaLines(277).setLengths(1);}
    public UmpleSourceData LogBufferPool_LogBufferPool(){ return new UmpleSourceData().setFileNames("LogBufferPool.ump").setUmpleLines(28).setJavaLines(44).setLengths(10);}
    public UmpleSourceData StatsFileReader_pad(){ return new UmpleSourceData().setFileNames("StatsFileReader.ump").setUmpleLines(140).setJavaLines(161).setLengths(7);}
    public UmpleSourceData LogEntryTypeComparator_compare(){ return new UmpleSourceData().setFileNames("StatsFileReader_static.ump").setUmpleLines(20).setJavaLines(29).setLengths(14);}
    public UmpleSourceData StatsFileReader_StatsFileReader(){ return new UmpleSourceData().setFileNames("StatsFileReader.ump").setUmpleLines(35).setJavaLines(51).setLengths(9);}
    public UmpleSourceData StatsFileReader_summarizeCheckpointInfo(){ return new UmpleSourceData().setFileNames("StatsFileReader.ump").setUmpleLines(150).setJavaLines(172).setLengths(60);}
    public UmpleSourceData CheckpointCounter_increment(){ return new UmpleSourceData().setFileNames("StatsFileReader_static.ump").setUmpleLines(48).setJavaLines(28).setLengths(35);}
    public UmpleSourceData StatsFileReader_summarize(){ return new UmpleSourceData().setFileNames("StatsFileReader.ump").setUmpleLines(87).setJavaLines(107).setLengths(50);}
    public UmpleSourceData EntryInfo_EntryInfo(){ return new UmpleSourceData().setFileNames("CleanerFileReader_static.ump","StatsFileReader_static.ump").setUmpleLines(7, 10).setJavaLines(29, 35).setLengths(2, 5);}
    public UmpleSourceData StatsFileReader_processEntry(){ return new UmpleSourceData().setFileNames("StatsFileReader.ump").setUmpleLines(50).setJavaLines(69).setLengths(34);}
    public UmpleSourceData LogResult_LogResult(){ return new UmpleSourceData().setFileNames("LogResult.ump").setUmpleLines(10).setJavaLines(78).setLengths(6);}
    public UmpleSourceData DeletedDupLNLogEntry_writeToLog(){ return new UmpleSourceData().setFileNames("DeletedDupLNLogEntry.ump").setUmpleLines(68).setJavaLines(107).setLengths(2);}
    public UmpleSourceData DeletedDupLNLogEntry_DeletedDupLNLogEntry(){ return new UmpleSourceData().setFileNames("DeletedDupLNLogEntry.ump","DeletedDupLNLogEntry.ump").setUmpleLines(20, 28).setJavaLines(46, 56).setLengths(1, 2);}
    public UmpleSourceData DeletedDupLNLogEntry_dumpEntry(){ return new UmpleSourceData().setFileNames("DeletedDupLNLogEntry.ump").setUmpleLines(51).setJavaLines(84).setLengths(3);}
    public UmpleSourceData DeletedDupLNLogEntry_getLogSize(){ return new UmpleSourceData().setFileNames("DeletedDupLNLogEntry.ump").setUmpleLines(61).setJavaLines(97).setLengths(1);}
    public UmpleSourceData DeletedDupLNLogEntry_getDupKey(){ return new UmpleSourceData().setFileNames("DeletedDupLNLogEntry.ump").setUmpleLines(76).setJavaLines(118).setLengths(1);}
    public UmpleSourceData DeletedDupLNLogEntry_readEntry(){ return new UmpleSourceData().setFileNames("DeletedDupLNLogEntry.ump").setUmpleLines(38).setJavaLines(68).setLengths(6);}
    public UmpleSourceData SingleItemLogEntry_dumpEntry(){ return new UmpleSourceData().setFileNames("SingleItemLogEntry.ump").setUmpleLines(36).setJavaLines(77).setLengths(2);}
    public UmpleSourceData SingleItemLogEntry_getTransactionId(){ return new UmpleSourceData().setFileNames("SingleItemLogEntry.ump").setUmpleLines(65).setJavaLines(118).setLengths(1);}
    public UmpleSourceData SingleItemLogEntry_getMainItem(){ return new UmpleSourceData().setFileNames("SingleItemLogEntry.ump").setUmpleLines(44).setJavaLines(88).setLengths(1);}
    public UmpleSourceData SingleItemLogEntry_clone(){ return new UmpleSourceData().setFileNames("SingleItemLogEntry.ump").setUmpleLines(51).setJavaLines(98).setLengths(1);}
    public UmpleSourceData SingleItemLogEntry_isTransactional(){ return new UmpleSourceData().setFileNames("SingleItemLogEntry.ump").setUmpleLines(58).setJavaLines(108).setLengths(1);}
    public UmpleSourceData SingleItemLogEntry_getNewInstance(){ return new UmpleSourceData().setFileNames("SingleItemLogEntry.ump").setUmpleLines(72).setJavaLines(128).setLengths(7);}
    public UmpleSourceData SingleItemLogEntry_readEntry(){ return new UmpleSourceData().setFileNames("SingleItemLogEntry.ump").setUmpleLines(22).setJavaLines(60).setLengths(8);}
    public UmpleSourceData SingleItemLogEntry_SingleItemLogEntry(){ return new UmpleSourceData().setFileNames("SingleItemLogEntry.ump").setUmpleLines(14).setJavaLines(50).setLengths(1);}
    public UmpleSourceData LNLogEntry_getKey(){ return new UmpleSourceData().setFileNames("LNLogEntry.ump").setUmpleLines(222).setJavaLines(264).setLengths(1);}
    public UmpleSourceData LNLogEntry_getUserTxn(){ return new UmpleSourceData().setFileNames("LNLogEntry.ump").setUmpleLines(250).setJavaLines(297).setLengths(5);}
    public UmpleSourceData LNLogEntry_getLogType(){ return new UmpleSourceData().setFileNames("LNLogEntry.ump").setUmpleLines(154).setJavaLines(178).setLengths(1);}
    public UmpleSourceData LNLogEntry_getLN(){ return new UmpleSourceData().setFileNames("LNLogEntry.ump").setUmpleLines(214).setJavaLines(254).setLengths(1);}
    public UmpleSourceData LNLogEntry_getNodeId(){ return new UmpleSourceData().setFileNames("LNLogEntry.ump").setUmpleLines(147).setJavaLines(168).setLengths(1);}
    public UmpleSourceData LNLogEntry_getLogSize(){ return new UmpleSourceData().setFileNames("LNLogEntry.ump").setUmpleLines(186).setJavaLines(222).setLengths(7);}
    public UmpleSourceData LNLogEntry_LNLogEntry(){ return new UmpleSourceData().setFileNames("LNLogEntry.ump","LNLogEntry.ump").setUmpleLines(39, 45).setJavaLines(40, 46).setLengths(2, 10);}
    public UmpleSourceData LNLogEntry_isTransactional(){ return new UmpleSourceData().setFileNames("LNLogEntry.ump").setUmpleLines(129).setJavaLines(144).setLengths(1);}
    public UmpleSourceData LNLogEntry_readEntry(){ return new UmpleSourceData().setFileNames("LNLogEntry.ump").setUmpleLines(62).setJavaLines(65).setLengths(28);}
    public UmpleSourceData LNLogEntry_postLogWork(){ return new UmpleSourceData().setFileNames("LNLogEntry.ump").setUmpleLines(177).setJavaLines(210).setLengths(3);}
    public UmpleSourceData LNLogEntry_getDbId(){ return new UmpleSourceData().setFileNames("LNLogEntry.ump").setUmpleLines(218).setJavaLines(259).setLengths(1);}
    public UmpleSourceData LNLogEntry_writeToLog(){ return new UmpleSourceData().setFileNames("LNLogEntry.ump").setUmpleLines(199).setJavaLines(238).setLengths(12);}
    public UmpleSourceData LNLogEntry_dumpEntry(){ return new UmpleSourceData().setFileNames("LNLogEntry.ump").setUmpleLines(96).setJavaLines(102).setLengths(13);}
    public UmpleSourceData LNLogEntry_getTransactionId(){ return new UmpleSourceData().setFileNames("LNLogEntry.ump").setUmpleLines(136).setJavaLines(154).setLengths(5);}
    public UmpleSourceData LNLogEntry_getMainItem(){ return new UmpleSourceData().setFileNames("LNLogEntry.ump").setUmpleLines(115).setJavaLines(124).setLengths(1);}
    public UmpleSourceData LNLogEntry_getDupKey(){ return new UmpleSourceData().setFileNames("LNLogEntry.ump").setUmpleLines(226).setJavaLines(269).setLengths(5);}
    public UmpleSourceData LNLogEntry_countAsObsoleteWhenLogged(){ return new UmpleSourceData().setFileNames("LNLogEntry.ump").setUmpleLines(169).setJavaLines(199).setLengths(1);}
    public UmpleSourceData LNLogEntry_getTxnId(){ return new UmpleSourceData().setFileNames("LNLogEntry.ump").setUmpleLines(242).setJavaLines(288).setLengths(5);}
    public UmpleSourceData LNLogEntry_clone(){ return new UmpleSourceData().setFileNames("LNLogEntry.ump").setUmpleLines(122).setJavaLines(134).setLengths(1);}
    public UmpleSourceData LNLogEntry_getAbortLsn(){ return new UmpleSourceData().setFileNames("LNLogEntry.ump").setUmpleLines(234).setJavaLines(278).setLengths(1);}
    public UmpleSourceData LNLogEntry_getAbortKnownDeleted(){ return new UmpleSourceData().setFileNames("LNLogEntry.ump").setUmpleLines(238).setJavaLines(283).setLengths(1);}
    public UmpleSourceData LNLogEntry_marshallOutsideWriteLatch(){ return new UmpleSourceData().setFileNames("LNLogEntry.ump").setUmpleLines(161).setJavaLines(188).setLengths(1);}
    public UmpleSourceData INLogEntry_getIN(){ return new UmpleSourceData().setFileNames("INLogEntry.ump").setUmpleLines(172).setJavaLines(228).setLengths(1);}
    public UmpleSourceData INLogEntry_getLogType(){ return new UmpleSourceData().setFileNames("INLogEntry.ump").setUmpleLines(132).setJavaLines(171).setLengths(1);}
    public UmpleSourceData INLogEntry_getNodeId(){ return new UmpleSourceData().setFileNames("INLogEntry.ump").setUmpleLines(179).setJavaLines(238).setLengths(1);}
    public UmpleSourceData INLogEntry_getLogSize(){ return new UmpleSourceData().setFileNames("INLogEntry.ump").setUmpleLines(159).setJavaLines(211).setLengths(1);}
    public UmpleSourceData INLogEntry_INLogEntry(){ return new UmpleSourceData().setFileNames("INLogEntry.ump","INLogEntry.ump").setUmpleLines(29, 36).setJavaLines(44, 54).setLengths(1, 5);}
    public UmpleSourceData INLogEntry_isTransactional(){ return new UmpleSourceData().setFileNames("INLogEntry.ump").setUmpleLines(118).setJavaLines(151).setLengths(1);}
    public UmpleSourceData INLogEntry_readEntry(){ return new UmpleSourceData().setFileNames("INLogEntry.ump").setUmpleLines(48).setJavaLines(68).setLengths(37);}
    public UmpleSourceData INLogEntry_getObsoleteLsn(){ return new UmpleSourceData().setFileNames("INLogEntry.ump").setUmpleLines(91).setJavaLines(114).setLengths(1);}
    public UmpleSourceData INLogEntry_postLogWork(){ return new UmpleSourceData().setFileNames("INLogEntry.ump").setUmpleLines(153).setJavaLines(201).setLengths(1);}
    public UmpleSourceData INLogEntry_getDbId(){ return new UmpleSourceData().setFileNames("INLogEntry.ump").setUmpleLines(186).setJavaLines(248).setLengths(1);}
    public UmpleSourceData INLogEntry_writeToLog(){ return new UmpleSourceData().setFileNames("INLogEntry.ump").setUmpleLines(166).setJavaLines(221).setLengths(3);}
    public UmpleSourceData INLogEntry_getLsnOfIN(){ return new UmpleSourceData().setFileNames("INLogEntry.ump").setUmpleLines(193).setJavaLines(258).setLengths(1);}
    public UmpleSourceData INLogEntry_dumpEntry(){ return new UmpleSourceData().setFileNames("INLogEntry.ump").setUmpleLines(98).setJavaLines(124).setLengths(3);}
    public UmpleSourceData INLogEntry_getTransactionId(){ return new UmpleSourceData().setFileNames("INLogEntry.ump").setUmpleLines(125).setJavaLines(161).setLengths(1);}
    public UmpleSourceData INLogEntry_getMainItem(){ return new UmpleSourceData().setFileNames("INLogEntry.ump").setUmpleLines(107).setJavaLines(136).setLengths(1);}
    public UmpleSourceData INLogEntry_countAsObsoleteWhenLogged(){ return new UmpleSourceData().setFileNames("INLogEntry.ump").setUmpleLines(146).setJavaLines(191).setLengths(1);}
    public UmpleSourceData INLogEntry_clone(){ return new UmpleSourceData().setFileNames("INLogEntry.ump").setUmpleLines(111).setJavaLines(141).setLengths(1);}
    public UmpleSourceData INLogEntry_marshallOutsideWriteLatch(){ return new UmpleSourceData().setFileNames("INLogEntry.ump").setUmpleLines(139).setJavaLines(181).setLengths(1);}
    public UmpleSourceData BINDeltaLogEntry_getIN(){ return new UmpleSourceData().setFileNames("BINDeltaLogEntry.ump").setUmpleLines(20).setJavaLines(48).setLengths(2);}
    public UmpleSourceData BINDeltaLogEntry_getDbId(){ return new UmpleSourceData().setFileNames("BINDeltaLogEntry.ump").setUmpleLines(25).setJavaLines(54).setLengths(2);}
    public UmpleSourceData BINDeltaLogEntry_getLsnOfIN(){ return new UmpleSourceData().setFileNames("BINDeltaLogEntry.ump").setUmpleLines(33).setJavaLines(65).setLengths(2);}
    public UmpleSourceData BINDeltaLogEntry_BINDeltaLogEntry(){ return new UmpleSourceData().setFileNames("BINDeltaLogEntry.ump").setUmpleLines(16).setJavaLines(43).setLengths(1);}
    public UmpleSourceData JEFileFilter_JEFileFilter(){ return new UmpleSourceData().setFileNames("JEFileFilter.ump").setUmpleLines(12).setJavaLines(83).setLengths(1);}
    public UmpleSourceData JEFileFilter_matches(){ return new UmpleSourceData().setFileNames("JEFileFilter.ump").setUmpleLines(16).setJavaLines(88).setLengths(6);}
    public UmpleSourceData JEFileFilter_accept(){ return new UmpleSourceData().setFileNames("JEFileFilter.ump").setUmpleLines(28).setJavaLines(103).setLengths(26);}
    public UmpleSourceData CheckpointFileReader_isCheckpointEnd(){ return new UmpleSourceData().setFileNames("CheckpointFileReader.ump").setUmpleLines(63).setJavaLines(96).setLengths(1);}
    public UmpleSourceData CheckpointFileReader_isTargetEntry(){ return new UmpleSourceData().setFileNames("CheckpointFileReader.ump").setUmpleLines(28).setJavaLines(52).setLengths(15);}
    public UmpleSourceData CheckpointFileReader_isRoot(){ return new UmpleSourceData().setFileNames("CheckpointFileReader.ump").setUmpleLines(56).setJavaLines(86).setLengths(1);}
    public UmpleSourceData CheckpointFileReader_isCheckpointStart(){ return new UmpleSourceData().setFileNames("CheckpointFileReader.ump").setUmpleLines(70).setJavaLines(106).setLengths(1);}
    public UmpleSourceData CheckpointFileReader_CheckpointFileReader(){ return new UmpleSourceData().setFileNames("CheckpointFileReader.ump").setUmpleLines(21).setJavaLines(42).setLengths(1);}
    public UmpleSourceData CheckpointFileReader_processEntry(){ return new UmpleSourceData().setFileNames("CheckpointFileReader.ump").setUmpleLines(49).setJavaLines(76).setLengths(1);}
    public UmpleSourceData SearchFileReader_getLastObject(){ return new UmpleSourceData().setFileNames("SearchFileReader.ump").setUmpleLines(45).setJavaLines(77).setLengths(1);}
    public UmpleSourceData SearchFileReader_isTargetEntry(){ return new UmpleSourceData().setFileNames("SearchFileReader.ump").setUmpleLines(30).setJavaLines(56).setLengths(1);}
    public UmpleSourceData SearchFileReader_SearchFileReader(){ return new UmpleSourceData().setFileNames("SearchFileReader.ump").setUmpleLines(21).setJavaLines(44).setLengths(3);}
    public UmpleSourceData SearchFileReader_processEntry(){ return new UmpleSourceData().setFileNames("SearchFileReader.ump").setUmpleLines(37).setJavaLines(66).setLengths(2);}
    public UmpleSourceData LogFileNotFoundException_LogFileNotFoundException(){ return new UmpleSourceData().setFileNames("LogFileNotFoundException.ump").setUmpleLines(7).setJavaLines(33).setLengths(1);}
    public UmpleSourceData FileHeader_getLogType(){ return new UmpleSourceData().setFileNames("FileHeader.ump").setUmpleLines(57).setJavaLines(82).setLengths(1);}
    public UmpleSourceData FileHeader_readFromLog(){ return new UmpleSourceData().setFileNames("FileHeader.ump").setUmpleLines(111).setJavaLines(158).setLengths(8);}
    public UmpleSourceData FileHeader_getLogSize(){ return new UmpleSourceData().setFileNames("FileHeader.ump").setUmpleLines(92).setJavaLines(133).setLengths(1);}
    public UmpleSourceData FileHeader_postLogWork(){ return new UmpleSourceData().setFileNames("FileHeader.ump").setUmpleLines(78).setJavaLines(112).setLengths(1);}
    public UmpleSourceData FileHeader_writeToLog(){ return new UmpleSourceData().setFileNames("FileHeader.ump").setUmpleLines(100).setJavaLines(144).setLengths(4);}
    public UmpleSourceData FileHeader_logEntryIsTransactional(){ return new UmpleSourceData().setFileNames("FileHeader.ump").setUmpleLines(141).setJavaLines(194).setLengths(1);}
    public UmpleSourceData FileHeader_getTransactionId(){ return new UmpleSourceData().setFileNames("FileHeader.ump").setUmpleLines(148).setJavaLines(204).setLengths(1);}
    public UmpleSourceData FileHeader_countAsObsoleteWhenLogged(){ return new UmpleSourceData().setFileNames("FileHeader.ump").setUmpleLines(71).setJavaLines(102).setLengths(1);}
    public UmpleSourceData FileHeader_dumpLog(){ return new UmpleSourceData().setFileNames("FileHeader.ump").setUmpleLines(127).setJavaLines(177).setLengths(8);}
    public UmpleSourceData FileHeader_toString(){ return new UmpleSourceData().setFileNames("FileHeader.ump").setUmpleLines(155).setJavaLines(214).setLengths(3);}
    public UmpleSourceData FileHeader_getLastEntryInPrevFileOffset(){ return new UmpleSourceData().setFileNames("FileHeader.ump").setUmpleLines(50).setJavaLines(72).setLengths(1);}
    public UmpleSourceData FileHeader_marshallOutsideWriteLatch(){ return new UmpleSourceData().setFileNames("FileHeader.ump").setUmpleLines(64).setJavaLines(92).setLengths(1);}
    public UmpleSourceData FileHeader_FileHeader(){ return new UmpleSourceData().setFileNames("FileHeader.ump","FileHeader.ump").setUmpleLines(21, 32).setJavaLines(33, 47).setLengths(5, 1);}
    public UmpleSourceData FileHeader_validate(){ return new UmpleSourceData().setFileNames("FileHeader.ump").setUmpleLines(39).setJavaLines(58).setLengths(5);}
    public UmpleSourceData FileHeader_entrySize(){ return new UmpleSourceData().setFileNames("FileHeader.ump").setUmpleLines(84).setJavaLines(122).setLengths(1);}
    public UmpleSourceData DumpFileReader_DumpFileReader(){ return new UmpleSourceData().setFileNames("DumpFileReader.ump").setUmpleLines(24).setJavaLines(45).setLengths(18);}
    public UmpleSourceData DumpFileReader_isTargetEntry(){ return new UmpleSourceData().setFileNames("DumpFileReader.ump").setUmpleLines(48).setJavaLines(72).setLengths(5);}
    public UmpleSourceData DumpFileReader_summarize(){ return new UmpleSourceData().setFileNames("DumpFileReader.ump").setUmpleLines(56).setJavaLines(81).setLengths(1);}
    public UmpleSourceData DbChecksumException_DbChecksumException(){ return new UmpleSourceData().setFileNames("DbChecksumException.ump","DbChecksumException.ump").setUmpleLines(9, 13).setJavaLines(36, 41).setLengths(1, 1);}
    public UmpleSourceData FileReader_readData(){ return new UmpleSourceData().setFileNames("FileReader.ump").setUmpleLines(238).setJavaLines(252).setLengths(28);}
    public UmpleSourceData FileReader_getLastLsn(){ return new UmpleSourceData().setFileNames("FileReader.ump").setUmpleLines(133).setJavaLines(134).setLengths(1);}
    public UmpleSourceData FileReader_setAlwaysValidateChecksum(){ return new UmpleSourceData().setFileNames("Checksum_FileReader.ump").setUmpleLines(14).setJavaLines(452).setLengths(1);}
    public UmpleSourceData FileReader_adjustReadBufferSize(){ return new UmpleSourceData().setFileNames("FileReader.ump").setUmpleLines(272).setJavaLines(289).setLengths(17);}
    public UmpleSourceData FileReader_fillReadBuffer(){ return new UmpleSourceData().setFileNames("FileReader.ump").setUmpleLines(318).setJavaLines(341).setLengths(45);}
    public UmpleSourceData FileReader_startChecksum(){ return new UmpleSourceData().setFileNames("Checksum_FileReader.ump").setUmpleLines(21).setJavaLines(462).setLengths(5);}
    public UmpleSourceData FileReader_readHeader(){ return new UmpleSourceData().setFileNames("FileReader.ump").setUmpleLines(219).setJavaLines(231).setLengths(9);}
    public UmpleSourceData FileReader_readNextEntry_hook468(){ return new UmpleSourceData().setFileNames("LoggingSevere_FileReader_inner.ump").setUmpleLines(5).setJavaLines(89).setLengths(4);}
    public UmpleSourceData FileReader_readNextEntry_execute(){ return new UmpleSourceData().setFileNames("FileReader_static.ump").setUmpleLines(10).setJavaLines(36).setLengths(49);}
    public UmpleSourceData FileReader_getNumRead(){ return new UmpleSourceData().setFileNames("FileReader.ump").setUmpleLines(122).setJavaLines(119).setLengths(1);}
    public UmpleSourceData FileReader_validateChecksum(){ return new UmpleSourceData().setFileNames("Checksum_FileReader.ump").setUmpleLines(32).setJavaLines(476).setLengths(3);}
    public UmpleSourceData FileReader_resyncReader(){ return new UmpleSourceData().setFileNames("FileReader.ump").setUmpleLines(146).setJavaLines(150).setLengths(1);}
    public UmpleSourceData FileReader_threadSafeBufferPosition(){ return new UmpleSourceData().setFileNames("FileReader.ump","FileReader.ump").setUmpleLines(385, 395).setJavaLines(425, 436).setLengths(7, 7);}
    public UmpleSourceData FileReader_getLogEntryInReadBuffer(){ return new UmpleSourceData().setFileNames("FileReader.ump").setUmpleLines(153).setJavaLines(160).setLengths(62);}
    public UmpleSourceData FileReader_isTargetEntry(){ return new UmpleSourceData().setFileNames("FileReader.ump").setUmpleLines(361).setJavaLines(395).setLengths(1);}
    public UmpleSourceData FileReader_initStartingPosition(){ return new UmpleSourceData().setFileNames("FileReader.ump").setUmpleLines(89).setJavaLines(83).setLengths(27);}
    public UmpleSourceData FileReader_readNextEntry_FileReader_readNextEntry(){ return new UmpleSourceData().setFileNames("FileReader_static.ump").setUmpleLines(7).setJavaLines(31).setLengths(1);}
    public UmpleSourceData FileReader_copyToSaveBuffer(){ return new UmpleSourceData().setFileNames("FileReader.ump").setUmpleLines(295).setJavaLines(315).setLengths(17);}
    public UmpleSourceData FileReader_threadSafeBufferFlip(){ return new UmpleSourceData().setFileNames("FileReader.ump").setUmpleLines(375).setJavaLines(414).setLengths(7);}
    public UmpleSourceData FileReader_FileReader(){ return new UmpleSourceData().setFileNames("FileReader.ump").setUmpleLines(66).setJavaLines(50).setLengths(24);}
    public UmpleSourceData FileReader_getNRepeatIteratorReads(){ return new UmpleSourceData().setFileNames("FileReader.ump").setUmpleLines(126).setJavaLines(124).setLengths(1);}
    public UmpleSourceData FileReader_readNextEntry(){ return new UmpleSourceData().setFileNames("FileReader.ump").setUmpleLines(141).setJavaLines(145).setLengths(1);}
    public UmpleSourceData FileSource_release(){ return new UmpleSourceData().setFileNames("FileSource.ump").setUmpleLines(27).setJavaLines(46).setLengths(1);}
    public UmpleSourceData FileSource_FileSource(){ return new UmpleSourceData().setFileNames("FileSource.ump").setUmpleLines(18).setJavaLines(34).setLengths(3);}
    public UmpleSourceData FileSource_getBytes(){ return new UmpleSourceData().setFileNames("FileSource.ump","FileSource.ump").setUmpleLines(33, 44).setJavaLines(56, 70).setLengths(5, 6);}
    public UmpleSourceData PrintFileReader_PrintFileReader(){ return new UmpleSourceData().setFileNames("PrintFileReader.ump").setUmpleLines(16).setJavaLines(43).setLengths(1);}
    public UmpleSourceData PrintFileReader_processEntry(){ return new UmpleSourceData().setFileNames("PrintFileReader.ump").setUmpleLines(23).setJavaLines(53).setLengths(33);}
    public UmpleSourceData SyncedLogManager_flushInternal(){ return new UmpleSourceData().setFileNames("SyncedLogManager.ump").setUmpleLines(33).setJavaLines(62).setLengths(5);}
    public UmpleSourceData SyncedLogManager_countObsoleteNodes(){ return new UmpleSourceData().setFileNames("SyncedLogManager.ump").setUmpleLines(64).setJavaLines(102).setLengths(2);}
    public UmpleSourceData SyncedLogManager_countObsoleteINs(){ return new UmpleSourceData().setFileNames("SyncedLogManager.ump").setUmpleLines(72).setJavaLines(113).setLengths(1);}
    public UmpleSourceData SyncedLogManager_hook515(){ return new UmpleSourceData().setFileNames("SyncedLogManager.ump").setUmpleLines(95).setJavaLines(139).setLengths(1);}
    public UmpleSourceData SyncedLogManager_hook514(){ return new UmpleSourceData().setFileNames("SyncedLogManager.ump").setUmpleLines(91).setJavaLines(134).setLengths(1);}
    public UmpleSourceData SyncedLogManager_SyncedLogManager(){ return new UmpleSourceData().setFileNames("SyncedLogManager.ump").setUmpleLines(17).setJavaLines(46).setLengths(1);}
    public UmpleSourceData SyncedLogManager_hook513(){ return new UmpleSourceData().setFileNames("SyncedLogManager.ump").setUmpleLines(87).setJavaLines(129).setLengths(1);}
    public UmpleSourceData SyncedLogManager_countObsoleteNode(){ return new UmpleSourceData().setFileNames("SyncedLogManager.ump").setUmpleLines(56).setJavaLines(91).setLengths(2);}
    public UmpleSourceData SyncedLogManager_hook512(){ return new UmpleSourceData().setFileNames("SyncedLogManager.ump").setUmpleLines(83).setJavaLines(124).setLengths(1);}
    public UmpleSourceData SyncedLogManager_hook511(){ return new UmpleSourceData().setFileNames("SyncedLogManager.ump").setUmpleLines(78).setJavaLines(118).setLengths(2);}
    public UmpleSourceData SyncedLogManager_logItem(){ return new UmpleSourceData().setFileNames("SyncedLogManager.ump").setUmpleLines(23).setJavaLines(51).setLengths(7);}
    public UmpleSourceData SyncedLogManager_getUnflushableTrackedSummary(){ return new UmpleSourceData().setFileNames("SyncedLogManager.ump").setUmpleLines(44).setJavaLines(76).setLengths(6);}
    public UmpleSourceData INFileReader_getIN(){ return new UmpleSourceData().setFileNames("INFileReader.ump").setUmpleLines(263).setJavaLines(261).setLengths(1);}
    public UmpleSourceData INFileReader_getDupDeletedDupKey(){ return new UmpleSourceData().setFileNames("INFileReader.ump").setUmpleLines(346).setJavaLines(377).setLengths(1);}
    public UmpleSourceData INFileReader_getMaxTxnId(){ return new UmpleSourceData().setFileNames("INFileReader.ump").setUmpleLines(297).setJavaLines(307).setLengths(1);}
    public UmpleSourceData INFileReader_getDeletedIdKey(){ return new UmpleSourceData().setFileNames("INFileReader.ump").setUmpleLines(325).setJavaLines(347).setLengths(1);}
    public UmpleSourceData INFileReader_isObsoleteLsnAlreadyCounted(){ return new UmpleSourceData().setFileNames("INFileReader.ump").setUmpleLines(253).setJavaLines(248).setLengths(4);}
    public UmpleSourceData INFileReader_getDupDeletedNodeId(){ return new UmpleSourceData().setFileNames("INFileReader.ump").setUmpleLines(332).setJavaLines(357).setLengths(1);}
    public UmpleSourceData INFileReader_getDeletedNodeId(){ return new UmpleSourceData().setFileNames("INFileReader.ump").setUmpleLines(318).setJavaLines(337).setLengths(1);}
    public UmpleSourceData INFileReader_getDatabaseId(){ return new UmpleSourceData().setFileNames("INFileReader.ump").setUmpleLines(270).setJavaLines(271).setLengths(7);}
    public UmpleSourceData INFileReader_getLogEntryType(){ return new UmpleSourceData().setFileNames("INFileReader.ump").setUmpleLines(360).setJavaLines(397).setLengths(1);}
    public UmpleSourceData INFileReader_isDupDeleteInfo(){ return new UmpleSourceData().setFileNames("INFileReader.ump").setUmpleLines(311).setJavaLines(327).setLengths(1);}
    public UmpleSourceData INFileReader_getDupDeletedMainKey(){ return new UmpleSourceData().setFileNames("INFileReader.ump").setUmpleLines(339).setJavaLines(367).setLengths(1);}
    public UmpleSourceData INFileReader_isDeleteInfo(){ return new UmpleSourceData().setFileNames("INFileReader.ump").setUmpleLines(304).setJavaLines(317).setLengths(1);}
    public UmpleSourceData INFileReader_getMaxDbId(){ return new UmpleSourceData().setFileNames("INFileReader.ump").setUmpleLines(290).setJavaLines(297).setLengths(1);}
    public UmpleSourceData INFileReader_getLsnOfIN(){ return new UmpleSourceData().setFileNames("INFileReader.ump").setUmpleLines(353).setJavaLines(387).setLengths(1);}
    public UmpleSourceData INFileReader_isTargetEntry(){ return new UmpleSourceData().setFileNames("INFileReader.ump").setUmpleLines(117).setJavaLines(106).setLengths(52);}
    public UmpleSourceData INFileReader_getMaxNodeId(){ return new UmpleSourceData().setFileNames("INFileReader.ump").setUmpleLines(283).setJavaLines(287).setLengths(1);}
    public UmpleSourceData INFileReader_processEntry(){ return new UmpleSourceData().setFileNames("INFileReader.ump").setUmpleLines(175).setJavaLines(167).setLengths(72);}
    public UmpleSourceData INFileReader_INFileReader(){ return new UmpleSourceData().setFileNames("INFileReader.ump").setUmpleLines(76).setJavaLines(59).setLengths(27);}
    public UmpleSourceData INFileReader_addTargetType(){ return new UmpleSourceData().setFileNames("INFileReader.ump").setUmpleLines(109).setJavaLines(95).setLengths(1);}
    public UmpleSourceData LastFileReader_getPrevOffset(){ return new UmpleSourceData().setFileNames("LastFileReader.ump").setUmpleLines(120).setJavaLines(161).setLengths(1);}
    public UmpleSourceData LastFileReader_attemptToMoveBadFile(){ return new UmpleSourceData().setFileNames("LastFileReader.ump").setUmpleLines(91).setJavaLines(126).setLengths(11);}
    public UmpleSourceData LastFileReader_setEndOfFile(){ return new UmpleSourceData().setFileNames("LastFileReader.ump").setUmpleLines(105).setJavaLines(141).setLengths(1);}
    public UmpleSourceData LastFileReader_getEndOfLog(){ return new UmpleSourceData().setFileNames("LastFileReader.ump").setUmpleLines(112).setJavaLines(151).setLengths(1);}
    public UmpleSourceData LastFileReader_LastFileReader(){ return new UmpleSourceData().setFileNames("LastFileReader.ump","LastFileReader.ump").setUmpleLines(32, 45).setJavaLines(51, 66).setLengths(6, 6);}
    public UmpleSourceData LastFileReader_initStartingPosition(){ return new UmpleSourceData().setFileNames("LastFileReader.ump").setUmpleLines(57).setJavaLines(81).setLengths(36);}
    public UmpleSourceData LastFileReader_getLastValidLsn(){ return new UmpleSourceData().setFileNames("LastFileReader.ump").setUmpleLines(116).setJavaLines(156).setLengths(1);}
    public UmpleSourceData LastFileReader_setTargetType(){ return new UmpleSourceData().setFileNames("LastFileReader.ump").setUmpleLines(131).setJavaLines(176).setLengths(1);}
    public UmpleSourceData LastFileReader_getLastSeen(){ return new UmpleSourceData().setFileNames("LastFileReader.ump").setUmpleLines(138).setJavaLines(186).setLengths(6);}
    public UmpleSourceData LastFileReader_getEntryType(){ return new UmpleSourceData().setFileNames("LastFileReader.ump").setUmpleLines(124).setJavaLines(166).setLengths(1);}
    public UmpleSourceData LastFileReader_processEntry(){ return new UmpleSourceData().setFileNames("LastFileReader.ump").setUmpleLines(150).setJavaLines(201).setLengths(6);}
    public UmpleSourceData LastFileReader_readNextEntry(){ return new UmpleSourceData().setFileNames("LastFileReader.ump").setUmpleLines(163).setJavaLines(217).setLengths(12);}
    public UmpleSourceData LogEntryType_getVersion(){ return new UmpleSourceData().setFileNames("LogEntryType.ump").setUmpleLines(214).setJavaLines(159).setLengths(1);}
    public UmpleSourceData LogEntryType_setProvisional(){ return new UmpleSourceData().setFileNames("LogEntryType.ump").setUmpleLines(192).setJavaLines(129).setLengths(1);}
    public UmpleSourceData LogEntryType_isProvisional(){ return new UmpleSourceData().setFileNames("LogEntryType.ump").setUmpleLines(206).setJavaLines(149).setLengths(1);}
    public UmpleSourceData LogEntryType_getTypeNum(){ return new UmpleSourceData().setFileNames("LogEntryType.ump").setUmpleLines(210).setJavaLines(154).setLengths(1);}
    public UmpleSourceData LogEntryType_equalsType(){ return new UmpleSourceData().setFileNames("LogEntryType.ump","LogEntryType.ump").setUmpleLines(232, 236).setJavaLines(184, 189).setLengths(1, 1);}
    public UmpleSourceData LogEntryType_getSharedLogEntry(){ return new UmpleSourceData().setFileNames("LogEntryType.ump").setUmpleLines(174).setJavaLines(105).setLengths(1);}
    public UmpleSourceData LogEntryType_getNewLogEntry(){ return new UmpleSourceData().setFileNames("LogEntryType.ump").setUmpleLines(181).setJavaLines(115).setLengths(5);}
    public UmpleSourceData LogEntryType_isNodeType(){ return new UmpleSourceData().setFileNames("LogEntryType.ump","LogEntryType.ump").setUmpleLines(123, 146).setJavaLines(38, 68).setLengths(1, 1);}
    public UmpleSourceData LogEntryType_clearProvisional(){ return new UmpleSourceData().setFileNames("LogEntryType.ump").setUmpleLines(199).setJavaLines(139).setLengths(1);}
    public UmpleSourceData LogEntryType_hashCode(){ return new UmpleSourceData().setFileNames("LogEntryType.ump").setUmpleLines(253).setJavaLines(210).setLengths(1);}
    public UmpleSourceData LogEntryType_findType(){ return new UmpleSourceData().setFileNames("LogEntryType.ump").setUmpleLines(153).setJavaLines(78).setLengths(4);}
    public UmpleSourceData LogEntryType_equals(){ return new UmpleSourceData().setFileNames("LogEntryType.ump").setUmpleLines(240).setJavaLines(194).setLengths(7);}
    public UmpleSourceData LogEntryType_LogEntryType(){ return new UmpleSourceData().setFileNames("LogEntryType.ump","LogEntryType.ump").setUmpleLines(130, 138).setJavaLines(48, 59).setLengths(2, 5);}
    public UmpleSourceData LogEntryType_toString(){ return new UmpleSourceData().setFileNames("LogEntryType.ump").setUmpleLines(225).setJavaLines(174).setLengths(1);}
    public UmpleSourceData LogEntryType_isValidType(){ return new UmpleSourceData().setFileNames("LogEntryType.ump").setUmpleLines(221).setJavaLines(169).setLengths(1);}
    public UmpleSourceData LogEntryType_getAllTypes(){ return new UmpleSourceData().setFileNames("LogEntryType.ump").setUmpleLines(163).setJavaLines(91).setLengths(5);}
    public UmpleSourceData FileManager_truncateLog(){ return new UmpleSourceData().setFileNames("FileManager.ump").setUmpleLines(715).setJavaLines(859).setLengths(10);}
    public UmpleSourceData FileManager_syncLogEndAndFinishFile(){ return new UmpleSourceData().setFileNames("FileManager.ump").setUmpleLines(680).setJavaLines(793).setLengths(4);}
    public UmpleSourceData FileManager_hook449(){ return new UmpleSourceData().setFileNames("FileManager.ump").setUmpleLines(784).setJavaLines(942).setLengths(2);}
    public UmpleSourceData FileManager_getCacheKeys(){ return new UmpleSourceData().setFileNames("FileHandleCache_FileManager.ump").setUmpleLines(7).setJavaLines(1080).setLengths(1);}
    public UmpleSourceData FileManager_forceNewLogFile(){ return new UmpleSourceData().setFileNames("FileManager.ump").setUmpleLines(731).setJavaLines(878).setLengths(1);}
    public UmpleSourceData FileManager_getLastFileNum(){ return new UmpleSourceData().setFileNames("FileManager.ump").setUmpleLines(198).setJavaLines(191).setLengths(1);}
    public UmpleSourceData FileManager_saveLastPosition(){ return new UmpleSourceData().setFileNames("FileManager.ump").setUmpleLines(160).setJavaLines(142).setLengths(5);}
    public UmpleSourceData FileManager_getNFSyncTimeouts(){ return new UmpleSourceData().setFileNames("Derivative_FSync_Statistics_FileManager.ump").setUmpleLines(13).setJavaLines(1124).setLengths(1);}
    public UmpleSourceData FileManager_generateRunRecoveryException(){ return new UmpleSourceData().setFileNames("FileManager.ump").setUmpleLines(757).setJavaLines(913).setLengths(25);}
    public UmpleSourceData FileManager_lockEnvironment(){ return new UmpleSourceData().setFileNames("EnvironmentLocking_FileManager.ump").setUmpleLines(16).setJavaLines(1027).setLengths(38);}
    public UmpleSourceData FileManager_getFileName(){ return new UmpleSourceData().setFileNames("FileManager.ump").setUmpleLines(351).setJavaLines(379).setLengths(1);}
    public UmpleSourceData FileManager_getPrevEntryOffset(){ return new UmpleSourceData().setFileNames("FileManager.ump").setUmpleLines(546).setJavaLines(618).setLengths(1);}
    public UmpleSourceData FileMode_FileMode(){ return new UmpleSourceData().setFileNames("FileManager_static.ump").setUmpleLines(8).setJavaLines(28).setLengths(1);}
    public UmpleSourceData FileManager_writeToFile_FileManager_writeToFile(){ return new UmpleSourceData().setFileNames("FileManager_static.ump").setUmpleLines(76).setJavaLines(32).setLengths(4);}
    public UmpleSourceData FileManager_groupSync(){ return new UmpleSourceData().setFileNames("FSync_FileManager.ump").setUmpleLines(10).setJavaLines(1100).setLengths(1);}
    public UmpleSourceData FileManager_setLastPosition(){ return new UmpleSourceData().setFileNames("FileManager.ump").setUmpleLines(151).setJavaLines(132).setLengths(6);}
    public UmpleSourceData FileManager_makeFileHandle(){ return new UmpleSourceData().setFileNames("FileManager.ump").setUmpleLines(432).setJavaLines(491).setLengths(43);}
    public UmpleSourceData FileManager_writeFileHeader(){ return new UmpleSourceData().setFileNames("FileManager.ump").setUmpleLines(506).setJavaLines(572).setLengths(25);}
    public UmpleSourceData FileManager_getFollowingFileNum(){ return new UmpleSourceData().setFileNames("FileManager.ump").setUmpleLines(229).setJavaLines(230).setLengths(26);}
    public UmpleSourceData FileManager_filesExist(){ return new UmpleSourceData().setFileNames("FileManager.ump").setUmpleLines(261).setJavaLines(265).setLengths(2);}
    public UmpleSourceData FileManager_writeToFile_execute(){ return new UmpleSourceData().setFileNames("FileManager_static.ump","NIO_FileManager_inner.ump","FileManager_static.ump").setUmpleLines(82, 17, 112).setJavaLines(40, 42, 40).setLengths(49, 2, 42);}
    public UmpleSourceData FileManager_FileManager(){ return new UmpleSourceData().setFileNames("FileManager.ump").setUmpleLines(117).setJavaLines(83).setLengths(37);}
    public UmpleSourceData FileManager_setSyncAtFileEnd(){ return new UmpleSourceData().setFileNames("FileManager.ump").setUmpleLines(179).setJavaLines(165).setLengths(1);}
    public UmpleSourceData FileManager_restoreLastPosition(){ return new UmpleSourceData().setFileNames("FileManager.ump").setUmpleLines(168).setJavaLines(151).setLengths(5);}
    public UmpleSourceData FileManager_abortCommittedTxns(){ return new UmpleSourceData().setFileNames("FileManager.ump").setUmpleLines(631).setJavaLines(736).setLengths(34);}
    public UmpleSourceData FileManager_getNFSyncs(){ return new UmpleSourceData().setFileNames("Derivative_FSync_Statistics_FileManager.ump").setUmpleLines(5).setJavaLines(1114).setLengths(1);}
    public UmpleSourceData FileManager_getNumFromName(){ return new UmpleSourceData().setFileNames("FileManager.ump").setUmpleLines(289).setJavaLines(299).setLengths(2);}
    public UmpleSourceData FileManager_readAndValidateFileHeader(){ return new UmpleSourceData().setFileNames("FileManager.ump").setUmpleLines(495).setJavaLines(559).setLengths(4);}
    public UmpleSourceData FileManager_setIncludeDeletedFiles(){ return new UmpleSourceData().setFileNames("FileManager.ump").setUmpleLines(206).setJavaLines(201).setLengths(1);}
    public UmpleSourceData FileManager_renameFile(){ return new UmpleSourceData().setFileNames("FileManager.ump").setUmpleLines(360).setJavaLines(391).setLengths(25);}
    public UmpleSourceData LogEndFileDescriptor_force(){ return new UmpleSourceData().setFileNames("FileManager_static.ump").setUmpleLines(36).setJavaLines(52).setLengths(11);}
    public UmpleSourceData FileManager_writeLogBuffer(){ return new UmpleSourceData().setFileNames("FileManager.ump").setUmpleLines(583).setJavaLines(661).setLengths(51);}
    public UmpleSourceData FileManager_releaseExclusiveLock(){ return new UmpleSourceData().setFileNames("EnvironmentLocking_FileManager.ump").setUmpleLines(57).setJavaLines(1069).setLengths(7);}
    public UmpleSourceData FileMode_getModeValue(){ return new UmpleSourceData().setFileNames("FileManager_static.ump").setUmpleLines(11).setJavaLines(33).setLengths(1);}
    public UmpleSourceData LogEndFileDescriptor_getWritableFile(){ return new UmpleSourceData().setFileNames("FileManager_static.ump").setUmpleLines(21).setJavaLines(33).setLengths(10);}
    public UmpleSourceData FileManager_closeFileInErrorCase(){ return new UmpleSourceData().setFileNames("FileManager.ump").setUmpleLines(481).setJavaLines(543).setLengths(5);}
    public UmpleSourceData FileManager_getLastUsedLsn(){ return new UmpleSourceData().setFileNames("FileManager.ump").setUmpleLines(752).setJavaLines(908).setLengths(1);}
    public UmpleSourceData FileManager_firstLogEntryOffset(){ return new UmpleSourceData().setFileNames("FileManager.ump").setUmpleLines(738).setJavaLines(888).setLengths(1);}
    public UmpleSourceData FileManager_bumpLsn(){ return new UmpleSourceData().setFileNames("FileManager.ump").setUmpleLines(555).setJavaLines(630).setLengths(21);}
    public UmpleSourceData FileManager_hook467(){ return new UmpleSourceData().setFileNames("FileManager.ump").setUmpleLines(845).setJavaLines(1017).setLengths(1);}
    public UmpleSourceData FileManager_hook465(){ return new UmpleSourceData().setFileNames("FileManager.ump").setUmpleLines(838).setJavaLines(1005).setLengths(1);}
    public UmpleSourceData FileManager_getFileHeaderPrevOffset(){ return new UmpleSourceData().setFileNames("FileManager.ump").setUmpleLines(537).setJavaLines(606).setLengths(3);}
    public UmpleSourceData FileManager_checkEnvHomePermissions(){ return new UmpleSourceData().setFileNames("FileManager.ump").setUmpleLines(703).setJavaLines(844).setLengths(6);}
    public UmpleSourceData FileManager_writeToFile(){ return new UmpleSourceData().setFileNames("FileManager.ump").setUmpleLines(620).setJavaLines(721).setLengths(1);}
    public UmpleSourceData FileManager_getFirstFileNum(){ return new UmpleSourceData().setFileNames("FileManager.ump").setUmpleLines(187).setJavaLines(176).setLengths(1);}
    public UmpleSourceData FileManager_getFullFileName(){ return new UmpleSourceData().setFileNames("FileManager.ump","FileManager.ump").setUmpleLines(337, 344).setJavaLines(359, 369).setLengths(1, 1);}
    public UmpleSourceData FileManager_listFiles(){ return new UmpleSourceData().setFileNames("FileManager.ump","FileManager.ump").setUmpleLines(299, 310).setJavaLines(312, 326).setLengths(3, 3);}
    public UmpleSourceData FileManager_close(){ return new UmpleSourceData().setFileNames("EnvironmentLocking_FileManager.ump","FileManager.ump","FileManager_static.ump").setUmpleLines(77, 696, 53).setJavaLines(821, 819, 73).setLengths(12, 1, 19);}
    public UmpleSourceData FileManager_deleteFile(){ return new UmpleSourceData().setFileNames("FileManager.ump").setUmpleLines(390).setJavaLines(426).setLengths(10);}
    public UmpleSourceData FileManager_getFileNum(){ return new UmpleSourceData().setFileNames("FileManager.ump").setUmpleLines(271).setJavaLines(278).setLengths(10);}
    public UmpleSourceData FileManager_getFullFileNames(){ return new UmpleSourceData().setFileNames("FileManager.ump").setUmpleLines(319).setJavaLines(338).setLengths(12);}
    public UmpleSourceData FileManager_clear(){ return new UmpleSourceData().setFileNames("FileHandleCache_FileManager.ump","FileManager.ump").setUmpleLines(77, 690).setJavaLines(808, 806).setLengths(1, 1);}
    public UmpleSourceData FileManager_getFileHandle(){ return new UmpleSourceData().setFileNames("FileManager.ump").setUmpleLines(406).setJavaLines(447).setLengths(40);}
    public UmpleSourceData FileManager_hook459(){ return new UmpleSourceData().setFileNames("FileManager.ump").setUmpleLines(806).setJavaLines(973).setLengths(1);}
    public UmpleSourceData FileManager_hook458(){ return new UmpleSourceData().setFileNames("FileManager.ump").setUmpleLines(804).setJavaLines(968).setLengths(1);}
    public UmpleSourceData FileManager_hook457(){ return new UmpleSourceData().setFileNames("FileManager.ump").setUmpleLines(802).setJavaLines(963).setLengths(1);}
    public UmpleSourceData FileManager_syncLogEnd(){ return new UmpleSourceData().setFileNames("FileManager.ump").setUmpleLines(669).setJavaLines(779).setLengths(5);}
    public UmpleSourceData FileManager_getAllFileNumbers(){ return new UmpleSourceData().setFileNames("FileManager.ump").setUmpleLines(214).setJavaLines(212).setLengths(6);}
    public UmpleSourceData FileManager_clearFileCache(){ return new UmpleSourceData().setFileNames("FileHandleCache_FileManager.ump").setUmpleLines(14).setJavaLines(1090).setLengths(1);}
    public UmpleSourceData FileManager_hook451(){ return new UmpleSourceData().setFileNames("Derivative_Latches_FileHandleCache_FileManager.ump").setUmpleLines(31).setJavaLines(1105).setLengths(5);}
    public UmpleSourceData FileManager_hook450(){ return new UmpleSourceData().setFileNames("FileManager.ump").setUmpleLines(788).setJavaLines(948).setLengths(2);}
    public UmpleSourceData FileManager_loadStats(){ return new UmpleSourceData().setFileNames("Derivative_FSync_Statistics_FileManager.ump").setUmpleLines(17).setJavaLines(1129).setLengths(1);}
    public UmpleSourceData FileManager_readFromFile(){ return new UmpleSourceData().setFileNames("FileManager.ump").setUmpleLines(627).setJavaLines(731).setLengths(1);}
    public UmpleSourceData FileManager_getNFSyncRequests(){ return new UmpleSourceData().setFileNames("Derivative_FSync_Statistics_FileManager.ump").setUmpleLines(9).setJavaLines(1119).setLengths(1);}
    public UmpleSourceData FileManager_readFromFile_FileManager_readFromFile(){ return new UmpleSourceData().setFileNames("FileManager_static.ump").setUmpleLines(106).setJavaLines(32).setLengths(4);}
    public UmpleSourceData FileManager_getReadOnly(){ return new UmpleSourceData().setFileNames("FileManager.ump").setUmpleLines(191).setJavaLines(181).setLengths(1);}
    public UmpleSourceData FileManager_getCurrentFileNum(){ return new UmpleSourceData().setFileNames("FileManager.ump").setUmpleLines(202).setJavaLines(196).setLengths(1);}
    public UmpleSourceData FileManager_getNextLsn(){ return new UmpleSourceData().setFileNames("FileManager.ump").setUmpleLines(745).setJavaLines(898).setLengths(1);}
    public UmpleSourceData FileHandle_FileHandle(){ return new UmpleSourceData().setFileNames("FileHandle.ump").setUmpleLines(14).setJavaLines(36).setLengths(6);}
    public UmpleSourceData FileHandle_release(){ return new UmpleSourceData().setFileNames("Latches_FileHandle.ump").setUmpleLines(17).setJavaLines(74).setLengths(1);}
    public UmpleSourceData FileHandle_latchNoWait(){ return new UmpleSourceData().setFileNames("Latches_FileHandle.ump").setUmpleLines(13).setJavaLines(69).setLengths(1);}
    public UmpleSourceData FileHandle_isOldHeaderVersion(){ return new UmpleSourceData().setFileNames("FileHandle.ump").setUmpleLines(24).setJavaLines(51).setLengths(1);}
    public UmpleSourceData FileHandle_latch(){ return new UmpleSourceData().setFileNames("Latches_FileHandle.ump").setUmpleLines(9).setJavaLines(64).setLengths(1);}
    public UmpleSourceData FileHandle_getFile(){ return new UmpleSourceData().setFileNames("FileHandle.ump").setUmpleLines(20).setJavaLines(46).setLengths(1);}
    public UmpleSourceData FileHandle_close(){ return new UmpleSourceData().setFileNames("FileHandle.ump").setUmpleLines(28).setJavaLines(56).setLengths(4);}
    public UmpleSourceData FileHandleSource_release(){ return new UmpleSourceData().setFileNames("Latches_FileHandleSource.ump").setUmpleLines(8).setJavaLines(46).setLengths(1);}
    public UmpleSourceData FileHandleSource_FileHandleSource(){ return new UmpleSourceData().setFileNames("FileHandleSource.ump").setUmpleLines(10).setJavaLines(35).setLengths(2);}
    public UmpleSourceData LogManager_writeHeader(){ return new UmpleSourceData().setFileNames("LogManager.ump").setUmpleLines(279).setJavaLines(347).setLengths(8);}
    public UmpleSourceData LogManager_countObsoleteNodeInternal(){ return new UmpleSourceData().setFileNames("LogManager.ump").setUmpleLines(369).setJavaLines(442).setLengths(1);}
    public UmpleSourceData LogManager_log(){ return new UmpleSourceData().setFileNames("LogManager.ump","LogManager.ump","LogManager.ump").setUmpleLines(115, 123, 137).setJavaLines(150, 161, 177).setLengths(1, 1, 36);}
    public UmpleSourceData LogManager_hook505(){ return new UmpleSourceData().setFileNames("LogManager.ump").setUmpleLines(433).setJavaLines(501).setLengths(1);}
    public UmpleSourceData LogManager_hook502(){ return new UmpleSourceData().setFileNames("LogManager.ump").setUmpleLines(412).setJavaLines(477).setLengths(1);}
    public UmpleSourceData LogManager_getLogSource(){ return new UmpleSourceData().setFileNames("LogManager.ump").setUmpleLines(327).setJavaLines(408).setLengths(11);}
    public UmpleSourceData LogManager_setReadHook(){ return new UmpleSourceData().setFileNames("LogManager.ump").setUmpleLines(399).setJavaLines(464).setLengths(1);}
    public UmpleSourceData LogManager_getLogEntryFromLogSource_LogManager_getLogEntryFromLogSource(){ return new UmpleSourceData().setFileNames("LogManager_static.ump").setUmpleLines(5).setJavaLines(30).setLengths(3);}
    public UmpleSourceData LogManager_marshallIntoBuffer(){ return new UmpleSourceData().setFileNames("LogManager.ump").setUmpleLines(244).setJavaLines(306).setLengths(6);}
    public UmpleSourceData LogManager_flush(){ return new UmpleSourceData().setFileNames("LogManager.ump").setUmpleLines(344).setJavaLines(428).setLengths(5);}
    public UmpleSourceData LogManager_HEADER_CONTENT_BYTES(){ return new UmpleSourceData().setFileNames("LogManager.ump").setUmpleLines(55).setJavaLines(61).setLengths(6);}
    public UmpleSourceData LogManager_resetPool(){ return new UmpleSourceData().setFileNames("LogManager.ump").setUmpleLines(87).setJavaLines(113).setLengths(1);}
    public UmpleSourceData LogManager_logInternal(){ return new UmpleSourceData().setFileNames("LogManager.ump").setUmpleLines(175).setJavaLines(222).setLengths(75);}
    public UmpleSourceData LogManager_getLogEntryFromLogSource(){ return new UmpleSourceData().setFileNames("LogManager.ump").setUmpleLines(310).setJavaLines(385).setLengths(1);}
    public UmpleSourceData LogManager_get(){ return new UmpleSourceData().setFileNames("LogManager.ump").setUmpleLines(319).setJavaLines(397).setLengths(2);}
    public UmpleSourceData LogManager_getUnflushableTrackedSummaryInternal(){ return new UmpleSourceData().setFileNames("LogManager.ump").setUmpleLines(359).setJavaLines(437).setLengths(1);}
    public UmpleSourceData LogManager_countObsoleteNodesInternal(){ return new UmpleSourceData().setFileNames("LogManager.ump").setUmpleLines(379).setJavaLines(447).setLengths(4);}
    public UmpleSourceData LogManager_getChecksumOnRead(){ return new UmpleSourceData().setFileNames("Checksum_LogManager.ump").setUmpleLines(11).setJavaLines(521).setLengths(1);}
    public UmpleSourceData LogManager_getLogEntryFromLogSource_execute(){ return new UmpleSourceData().setFileNames("LogManager_static.ump").setUmpleLines(10).setJavaLines(37).setLengths(52);}
    public UmpleSourceData LogManager_getLastLsnAtRecovery(){ return new UmpleSourceData().setFileNames("LogManager.ump").setUmpleLines(76).setJavaLines(98).setLengths(1);}
    public UmpleSourceData LogManager_addPrevOffsetAndChecksum(){ return new UmpleSourceData().setFileNames("LogManager.ump").setUmpleLines(253).setJavaLines(316).setLengths(8);}
    public UmpleSourceData LogManager_logForceFlip(){ return new UmpleSourceData().setFileNames("LogManager.ump").setUmpleLines(107).setJavaLines(139).setLengths(1);}
    public UmpleSourceData LogManager_loadStats(){ return new UmpleSourceData().setFileNames("Statistics_LogManager.ump").setUmpleLines(11).setJavaLines(506).setLengths(11);}
    public UmpleSourceData LogManager_getLogEntry(){ return new UmpleSourceData().setFileNames("LogManager.ump","LogManager.ump").setUmpleLines(295, 301).setJavaLines(366, 373).setLengths(3, 1);}
    public UmpleSourceData LogManager_setLastLsnAtRecovery(){ return new UmpleSourceData().setFileNames("LogManager.ump").setUmpleLines(80).setJavaLines(103).setLengths(1);}
    public UmpleSourceData LogManager_logForceFlush(){ return new UmpleSourceData().setFileNames("LogManager.ump").setUmpleLines(97).setJavaLines(126).setLengths(1);}
    public UmpleSourceData LogManager_putIntoBuffer(){ return new UmpleSourceData().setFileNames("LogManager.ump").setUmpleLines(268).setJavaLines(333).setLengths(2);}
    public UmpleSourceData LogManager_countObsoleteINsInternal(){ return new UmpleSourceData().setFileNames("LogManager.ump").setUmpleLines(391).setJavaLines(455).setLengths(5);}
    public UmpleSourceData LogManager_LogManager(){ return new UmpleSourceData().setFileNames("LogManager.ump").setUmpleLines(64).setJavaLines(76).setLengths(18);}
    public UmpleSourceData LogUtils_getBooleanLogSize(){ return new UmpleSourceData().setFileNames("LogUtils.ump").setUmpleLines(253).setJavaLines(336).setLengths(1);}
    public UmpleSourceData LogUtils_writeIntMSB(){ return new UmpleSourceData().setFileNames("LogUtils.ump").setUmpleLines(94).setJavaLines(129).setLengths(8);}
    public UmpleSourceData LogUtils_writeXid(){ return new UmpleSourceData().setFileNames("LogUtils.ump").setUmpleLines(280).setJavaLines(368).setLengths(15);}
    public UmpleSourceData LogUtils_getByteArrayLogSize(){ return new UmpleSourceData().setFileNames("LogUtils.ump").setUmpleLines(187).setJavaLines(243).setLengths(1);}
    public UmpleSourceData XidImpl_XidImpl(){ return new UmpleSourceData().setFileNames("LogUtils_static.ump").setUmpleLines(9).setJavaLines(29).setLengths(3);}
    public UmpleSourceData LogUtils_writeUnsignedInt(){ return new UmpleSourceData().setFileNames("LogUtils.ump").setUmpleLines(24).setJavaLines(38).setLengths(4);}
    public UmpleSourceData XidImpl_getBranchQualifier(){ return new UmpleSourceData().setFileNames("LogUtils_static.ump").setUmpleLines(20).setJavaLines(46).setLengths(1);}
    public UmpleSourceData LogUtils_readIntMSB(){ return new UmpleSourceData().setFileNames("LogUtils.ump").setUmpleLines(108).setJavaLines(146).setLengths(5);}
    public UmpleSourceData LogUtils_getIntLogSize(){ return new UmpleSourceData().setFileNames("LogUtils.ump").setUmpleLines(87).setJavaLines(119).setLengths(1);}
    public UmpleSourceData XidImpl_compareByteArrays(){ return new UmpleSourceData().setFileNames("LogUtils_static.ump").setUmpleLines(50).setJavaLines(82).setLengths(12);}
    public UmpleSourceData XidImpl_getGlobalTransactionId(){ return new UmpleSourceData().setFileNames("LogUtils_static.ump").setUmpleLines(17).setJavaLines(41).setLengths(1);}
    public UmpleSourceData LogUtils_writeLong(){ return new UmpleSourceData().setFileNames("LogUtils.ump").setUmpleLines(119).setJavaLines(160).setLengths(16);}
    public UmpleSourceData LogUtils_getLongLogSize(){ return new UmpleSourceData().setFileNames("LogUtils.ump").setUmpleLines(156).setJavaLines(203).setLengths(1);}
    public UmpleSourceData LogUtils_writeTimestamp(){ return new UmpleSourceData().setFileNames("LogUtils.ump").setUmpleLines(215).setJavaLines(283).setLengths(1);}
    public UmpleSourceData LogUtils_getStringLogSize(){ return new UmpleSourceData().setFileNames("LogUtils.ump").setUmpleLines(208).setJavaLines(273).setLengths(1);}
    public UmpleSourceData XidImpl_hashCode(){ return new UmpleSourceData().setFileNames("LogUtils_static.ump").setUmpleLines(36).setJavaLines(66).setLengths(12);}
    public UmpleSourceData LogUtils_getUnsignedInt(){ return new UmpleSourceData().setFileNames("LogUtils.ump").setUmpleLines(34).setJavaLines(51).setLengths(5);}
    public UmpleSourceData LogUtils_readInt(){ return new UmpleSourceData().setFileNames("LogUtils.ump").setUmpleLines(76).setJavaLines(105).setLengths(5);}
    public UmpleSourceData LogUtils_writeInt(){ return new UmpleSourceData().setFileNames("LogUtils.ump").setUmpleLines(62).setJavaLines(88).setLengths(8);}
    public UmpleSourceData LogUtils_readTimestamp(){ return new UmpleSourceData().setFileNames("LogUtils.ump").setUmpleLines(222).setJavaLines(293).setLengths(2);}
    public UmpleSourceData LogUtils_writeByteArray(){ return new UmpleSourceData().setFileNames("LogUtils.ump").setUmpleLines(163).setJavaLines(213).setLengths(2);}
    public UmpleSourceData LogUtils_readString(){ return new UmpleSourceData().setFileNames("LogUtils.ump").setUmpleLines(201).setJavaLines(263).setLengths(1);}
    public UmpleSourceData LogUtils_getTimestampLogSize(){ return new UmpleSourceData().setFileNames("LogUtils.ump").setUmpleLines(230).setJavaLines(304).setLengths(1);}
    public UmpleSourceData LogUtils_getXidSize(){ return new UmpleSourceData().setFileNames("LogUtils.ump").setUmpleLines(274).setJavaLines(361).setLengths(3);}
    public UmpleSourceData LogUtils_readLong(){ return new UmpleSourceData().setFileNames("LogUtils.ump").setUmpleLines(141).setJavaLines(185).setLengths(9);}
    public UmpleSourceData LogUtils_readByteArray(){ return new UmpleSourceData().setFileNames("LogUtils.ump").setUmpleLines(171).setJavaLines(224).setLengths(10);}
    public UmpleSourceData XidImpl_getFormatId(){ return new UmpleSourceData().setFileNames("LogUtils_static.ump").setUmpleLines(14).setJavaLines(36).setLengths(1);}
    public UmpleSourceData LogUtils_readShort(){ return new UmpleSourceData().setFileNames("LogUtils.ump").setUmpleLines(55).setJavaLines(78).setLengths(1);}
    public UmpleSourceData LogUtils_dumpBoolean(){ return new UmpleSourceData().setFileNames("LogUtils.ump").setUmpleLines(257).setJavaLines(341).setLengths(11);}
    public UmpleSourceData XidImpl_equals(){ return new UmpleSourceData().setFileNames("LogUtils_static.ump").setUmpleLines(23).setJavaLines(51).setLengths(11);}
    public UmpleSourceData LogUtils_readXid(){ return new UmpleSourceData().setFileNames("LogUtils.ump").setUmpleLines(298).setJavaLines(387).setLengths(14);}
    public UmpleSourceData LogUtils_writeShort(){ return new UmpleSourceData().setFileNames("LogUtils.ump").setUmpleLines(45).setJavaLines(65).setLengths(4);}
    public UmpleSourceData LogUtils_readBoolean(){ return new UmpleSourceData().setFileNames("LogUtils.ump").setUmpleLines(245).setJavaLines(325).setLengths(2);}
    public UmpleSourceData XidImpl_toString(){ return new UmpleSourceData().setFileNames("LogUtils_static.ump").setUmpleLines(64).setJavaLines(98).setLengths(18);}
    public UmpleSourceData LogUtils_writeString(){ return new UmpleSourceData().setFileNames("LogUtils.ump").setUmpleLines(194).setJavaLines(253).setLengths(1);}
    public UmpleSourceData LogUtils_writeBoolean(){ return new UmpleSourceData().setFileNames("LogUtils.ump").setUmpleLines(237).setJavaLines(314).setLengths(2);}
    public UmpleSourceData ScavengerFileReader_resyncReader(){ return new UmpleSourceData().setFileNames("ScavengerFileReader.ump").setUmpleLines(68).setJavaLines(95).setLengths(32);}
    public UmpleSourceData ScavengerFileReader_isTargetEntry(){ return new UmpleSourceData().setFileNames("ScavengerFileReader.ump").setUmpleLines(106).setJavaLines(136).setLengths(5);}
    public UmpleSourceData ScavengerFileReader_setDumpCorruptedBounds(){ return new UmpleSourceData().setFileNames("ScavengerFileReader.ump").setUmpleLines(36).setJavaLines(60).setLengths(1);}
    public UmpleSourceData ScavengerFileReader_setTargetType(){ return new UmpleSourceData().setFileNames("ScavengerFileReader.ump").setUmpleLines(43).setJavaLines(70).setLengths(1);}
    public UmpleSourceData ScavengerFileReader_ScavengerFileReader(){ return new UmpleSourceData().setFileNames("ScavengerFileReader.ump").setUmpleLines(25).setJavaLines(46).setLengths(5);}
    public UmpleSourceData ScavengerFileReader_processEntry(){ return new UmpleSourceData().setFileNames("ScavengerFileReader.ump").setUmpleLines(47).setJavaLines(75).setLengths(5);}
    public UmpleSourceData ScavengerFileReader_readNextEntry(){ return new UmpleSourceData().setFileNames("ScavengerFileReader.ump").setUmpleLines(57).setJavaLines(84).setLengths(7);}
    public UmpleSourceData LogBuffer_latchForWrite(){ return new UmpleSourceData().setFileNames("Latches_LogBuffer.ump").setUmpleLines(12).setJavaLines(232).setLengths(1);}
    public UmpleSourceData LogBuffer_getDataBuffer(){ return new UmpleSourceData().setFileNames("LogBuffer.ump").setUmpleLines(69).setJavaLines(130).setLengths(1);}
    public UmpleSourceData LogBuffer_getRewriteAllowed(){ return new UmpleSourceData().setFileNames("DiskFullErro_LogBuffer.ump").setUmpleLines(7).setJavaLines(217).setLengths(1);}
    public UmpleSourceData LogBuffer_release(){ return new UmpleSourceData().setFileNames("LogBuffer.ump").setUmpleLines(131).setJavaLines(212).setLengths(1);}
    public UmpleSourceData LogBuffer_reinit(){ return new UmpleSourceData().setFileNames("Latches_LogBuffer.ump","DiskFullErro_LogBuffer.ump","LogBuffer.ump").setUmpleLines(30, 20, 31).setJavaLines(65, 73, 63).setLengths(3, 5, 3);}
    public UmpleSourceData LogBuffer_containsLsn(){ return new UmpleSourceData().setFileNames("Latches_LogBuffer.ump","LogBuffer.ump").setUmpleLines(59, 84).setJavaLines(153, 151).setLengths(2, 14);}
    public UmpleSourceData LogBuffer_getBytes(){ return new UmpleSourceData().setFileNames("LogBuffer.ump","LogBuffer.ump").setUmpleLines(101, 118).setJavaLines(178, 198).setLengths(11, 3);}
    public UmpleSourceData LogBuffer_setRewriteAllowed(){ return new UmpleSourceData().setFileNames("DiskFullErro_LogBuffer.ump").setUmpleLines(11).setJavaLines(222).setLengths(1);}
    public UmpleSourceData LogBuffer_LogBuffer(){ return new UmpleSourceData().setFileNames("LogBuffer.ump","LogBuffer.ump").setUmpleLines(18, 25).setJavaLines(39, 56).setLengths(13, 3);}
    public UmpleSourceData LogBuffer_registerLsn(){ return new UmpleSourceData().setFileNames("Latches_LogBuffer.ump","Latches_LogBuffer.ump","LogBuffer.ump").setUmpleLines(44, 49, 48).setJavaLines(99, 109, 97).setLengths(1, 1, 7);}
    public UmpleSourceData LogBuffer_hasRoom(){ return new UmpleSourceData().setFileNames("LogBuffer.ump").setUmpleLines(62).setJavaLines(120).setLengths(1);}
    public UmpleSourceData LogBuffer_getFirstLsn(){ return new UmpleSourceData().setFileNames("LogBuffer.ump").setUmpleLines(40).setJavaLines(87).setLengths(1);}
    public UmpleSourceData LogBuffer_getCapacity(){ return new UmpleSourceData().setFileNames("LogBuffer.ump").setUmpleLines(76).setJavaLines(140).setLengths(1);}
    public UmpleSourceData DatabaseConfig_getExclusiveCreate(){ return new UmpleSourceData().setFileNames("DatabaseConfig.ump").setUmpleLines(65).setJavaLines(77).setLengths(1);}
    public UmpleSourceData DatabaseConfig_getBtreeComparator(){ return new UmpleSourceData().setFileNames("DatabaseConfig.ump").setUmpleLines(149).setJavaLines(197).setLengths(1);}
    public UmpleSourceData DatabaseConfig_getTransactional(){ return new UmpleSourceData().setFileNames("DatabaseConfig.ump").setUmpleLines(93).setJavaLines(117).setLengths(1);}
    public UmpleSourceData DatabaseConfig_getUseExistingConfig(){ return new UmpleSourceData().setFileNames("DatabaseConfig.ump").setUmpleLines(205).setJavaLines(277).setLengths(1);}
    public UmpleSourceData DatabaseConfig_setNodeMaxEntries(){ return new UmpleSourceData().setFileNames("DatabaseConfig.ump").setUmpleLines(114).setJavaLines(147).setLengths(1);}
    public UmpleSourceData DatabaseConfig_getOverrideDuplicateComparator(){ return new UmpleSourceData().setFileNames("DatabaseConfig.ump").setUmpleLines(191).setJavaLines(257).setLengths(1);}
    public UmpleSourceData DatabaseConfig_validateComparator(){ return new UmpleSourceData().setFileNames("DatabaseConfig.ump").setUmpleLines(267).setJavaLines(346).setLengths(16);}
    public UmpleSourceData DatabaseConfig_setAllowCreate(){ return new UmpleSourceData().setFileNames("DatabaseConfig.ump").setUmpleLines(44).setJavaLines(47).setLengths(1);}
    public UmpleSourceData DatabaseConfig_getSortedDuplicates(){ return new UmpleSourceData().setFileNames("DatabaseConfig.ump").setUmpleLines(79).setJavaLines(97).setLengths(1);}
    public UmpleSourceData DatabaseConfig_setNodeMaxDupTreeEntries(){ return new UmpleSourceData().setFileNames("DatabaseConfig.ump").setUmpleLines(121).setJavaLines(157).setLengths(1);}
    public UmpleSourceData DatabaseConfig_setOverrideBtreeComparator(){ return new UmpleSourceData().setFileNames("DatabaseConfig.ump").setUmpleLines(156).setJavaLines(207).setLengths(1);}
    public UmpleSourceData DatabaseConfig_setDuplicateComparator(){ return new UmpleSourceData().setFileNames("DatabaseConfig.ump").setUmpleLines(170).setJavaLines(227).setLengths(1);}
    public UmpleSourceData DatabaseConfig_DatabaseConfig(){ return new UmpleSourceData().setFileNames("DatabaseConfig.ump").setUmpleLines(38).setJavaLines(37).setLengths(1);}
    public UmpleSourceData DatabaseConfig_validate(){ return new UmpleSourceData().setFileNames("DatabaseConfig.ump").setUmpleLines(220).setJavaLines(296).setLengths(19);}
    public UmpleSourceData DatabaseConfig_setUseExistingConfig(){ return new UmpleSourceData().setFileNames("DatabaseConfig.ump").setUmpleLines(198).setJavaLines(267).setLengths(1);}
    public UmpleSourceData DatabaseConfig_getAllowCreate(){ return new UmpleSourceData().setFileNames("DatabaseConfig.ump").setUmpleLines(51).setJavaLines(57).setLengths(1);}
    public UmpleSourceData DatabaseConfig_setExclusiveCreate(){ return new UmpleSourceData().setFileNames("DatabaseConfig.ump").setUmpleLines(58).setJavaLines(67).setLengths(1);}
    public UmpleSourceData DatabaseConfig_getNodeMaxEntries(){ return new UmpleSourceData().setFileNames("DatabaseConfig.ump").setUmpleLines(128).setJavaLines(167).setLengths(1);}
    public UmpleSourceData DatabaseConfig_getNodeMaxDupTreeEntries(){ return new UmpleSourceData().setFileNames("DatabaseConfig.ump").setUmpleLines(135).setJavaLines(177).setLengths(1);}
    public UmpleSourceData DatabaseConfig_setTransactional(){ return new UmpleSourceData().setFileNames("DatabaseConfig.ump").setUmpleLines(86).setJavaLines(107).setLengths(1);}
    public UmpleSourceData DatabaseConfig_getDuplicateComparator(){ return new UmpleSourceData().setFileNames("DatabaseConfig.ump").setUmpleLines(177).setJavaLines(237).setLengths(1);}
    public UmpleSourceData DatabaseConfig_cloneConfig(){ return new UmpleSourceData().setFileNames("DatabaseConfig.ump").setUmpleLines(212).setJavaLines(287).setLengths(5);}
    public UmpleSourceData DatabaseConfig_setBtreeComparator(){ return new UmpleSourceData().setFileNames("DatabaseConfig.ump").setUmpleLines(142).setJavaLines(187).setLengths(1);}
    public UmpleSourceData DatabaseConfig_setSortedDuplicates(){ return new UmpleSourceData().setFileNames("DatabaseConfig.ump").setUmpleLines(72).setJavaLines(87).setLengths(1);}
    public UmpleSourceData DatabaseConfig_getOverrideBtreeComparator(){ return new UmpleSourceData().setFileNames("DatabaseConfig.ump").setUmpleLines(163).setJavaLines(217).setLengths(1);}
    public UmpleSourceData DatabaseConfig_getReadOnly(){ return new UmpleSourceData().setFileNames("DatabaseConfig.ump").setUmpleLines(107).setJavaLines(137).setLengths(1);}
    public UmpleSourceData DatabaseConfig_setReadOnly(){ return new UmpleSourceData().setFileNames("DatabaseConfig.ump").setUmpleLines(100).setJavaLines(127).setLengths(1);}
    public UmpleSourceData DatabaseConfig_genDatabaseConfigMismatchMessage(){ return new UmpleSourceData().setFileNames("DatabaseConfig.ump").setUmpleLines(243).setJavaLines(319).setLengths(18);}
    public UmpleSourceData DatabaseConfig_setOverrideDuplicateComparator(){ return new UmpleSourceData().setFileNames("DatabaseConfig.ump").setUmpleLines(184).setJavaLines(247).setLengths(1);}
    public UmpleSourceData EnvironmentMutableConfig_getCacheSize(){ return new UmpleSourceData().setFileNames("EnvironmentMutableConfig.ump").setUmpleLines(88).setJavaLines(113).setLengths(1);}
    public UmpleSourceData EnvironmentMutableConfig_getCachePercent(){ return new UmpleSourceData().setFileNames("EnvironmentMutableConfig.ump").setUmpleLines(102).setJavaLines(133).setLengths(6);}
    public UmpleSourceData EnvironmentMutableConfig_setConfigParam(){ return new UmpleSourceData().setFileNames("EnvironmentMutableConfig.ump").setUmpleLines(114).setJavaLines(148).setLengths(8);}
    public UmpleSourceData EnvironmentMutableConfig_setCachePercent(){ return new UmpleSourceData().setFileNames("EnvironmentMutableConfig.ump").setUmpleLines(95).setJavaLines(123).setLengths(1);}
    public UmpleSourceData EnvironmentMutableConfig_setValidateParams(){ return new UmpleSourceData().setFileNames("EnvironmentMutableConfig.ump").setUmpleLines(157).setJavaLines(201).setLengths(1);}
    public UmpleSourceData EnvironmentMutableConfig_EnvironmentMutableConfig(){ return new UmpleSourceData().setFileNames("EnvironmentMutableConfig.ump","EnvironmentMutableConfig.ump").setUmpleLines(37, 44).setJavaLines(41, 51).setLengths(1, 3);}
    public UmpleSourceData EnvironmentMutableConfig_setLoadPropertyFile(){ return new UmpleSourceData().setFileNames("EnvironmentMutableConfig.ump").setUmpleLines(270).setJavaLines(341).setLengths(1);}
    public UmpleSourceData EnvironmentMutableConfig_getNumExplicitlySetParams(){ return new UmpleSourceData().setFileNames("EnvironmentMutableConfig.ump").setUmpleLines(284).setJavaLines(361).setLengths(1);}
    public UmpleSourceData EnvironmentMutableConfig_getTxnNoSync(){ return new UmpleSourceData().setFileNames("EnvironmentMutableConfig.ump").setUmpleLines(60).setJavaLines(73).setLengths(1);}
    public UmpleSourceData EnvironmentMutableConfig_getLoadPropertyFile(){ return new UmpleSourceData().setFileNames("EnvironmentMutableConfig.ump").setUmpleLines(277).setJavaLines(351).setLengths(1);}
    public UmpleSourceData EnvironmentMutableConfig_setVal(){ return new UmpleSourceData().setFileNames("EnvironmentMutableConfig.ump").setUmpleLines(150).setJavaLines(193).setLengths(4);}
    public UmpleSourceData EnvironmentMutableConfig_setTxnNoSync(){ return new UmpleSourceData().setFileNames("EnvironmentMutableConfig.ump").setUmpleLines(53).setJavaLines(63).setLengths(1);}
    public UmpleSourceData EnvironmentMutableConfig_setCacheSize(){ return new UmpleSourceData().setFileNames("EnvironmentMutableConfig.ump").setUmpleLines(81).setJavaLines(103).setLengths(1);}
    public UmpleSourceData EnvironmentMutableConfig_getVal(){ return new UmpleSourceData().setFileNames("EnvironmentMutableConfig.ump").setUmpleLines(139).setJavaLines(179).setLengths(5);}
    public UmpleSourceData EnvironmentMutableConfig_clearImmutableProps(){ return new UmpleSourceData().setFileNames("EnvironmentMutableConfig.ump").setUmpleLines(255).setJavaLines(323).setLengths(9);}
    public UmpleSourceData EnvironmentMutableConfig_checkImmutablePropsForEquality(){ return new UmpleSourceData().setFileNames("EnvironmentMutableConfig.ump").setUmpleLines(179).setJavaLines(229).setLengths(16);}
    public UmpleSourceData EnvironmentMutableConfig_copyHandlePropsTo(){ return new UmpleSourceData().setFileNames("EnvironmentMutableConfig.ump").setUmpleLines(223).setJavaLines(282).setLengths(2);}
    public UmpleSourceData EnvironmentMutableConfig_validateProperties(){ return new UmpleSourceData().setFileNames("EnvironmentMutableConfig.ump").setUmpleLines(164).setJavaLines(211).setLengths(9);}
    public UmpleSourceData EnvironmentMutableConfig_copyMutablePropsTo(){ return new UmpleSourceData().setFileNames("EnvironmentMutableConfig.ump").setUmpleLines(231).setJavaLines(293).setLengths(11);}
    public UmpleSourceData EnvironmentMutableConfig_getConfigParam(){ return new UmpleSourceData().setFileNames("EnvironmentMutableConfig.ump").setUmpleLines(128).setJavaLines(165).setLengths(5);}
    public UmpleSourceData EnvironmentMutableConfig_setTxnWriteNoSync(){ return new UmpleSourceData().setFileNames("EnvironmentMutableConfig.ump").setUmpleLines(67).setJavaLines(83).setLengths(1);}
    public UmpleSourceData EnvironmentMutableConfig_getTxnWriteNoSync(){ return new UmpleSourceData().setFileNames("EnvironmentMutableConfig.ump").setUmpleLines(74).setJavaLines(93).setLengths(1);}
    public UmpleSourceData EnvironmentMutableConfig_fillInEnvironmentGeneratedProps(){ return new UmpleSourceData().setFileNames("EnvironmentMutableConfig.ump").setUmpleLines(248).setJavaLines(313).setLengths(1);}
    public UmpleSourceData EnvironmentMutableConfig_clone(){ return new UmpleSourceData().setFileNames("EnvironmentMutableConfig.ump").setUmpleLines(201).setJavaLines(254).setLengths(3);}
    public UmpleSourceData EnvironmentMutableConfig_toString(){ return new UmpleSourceData().setFileNames("EnvironmentMutableConfig.ump").setUmpleLines(288).setJavaLines(366).setLengths(1);}
    public UmpleSourceData EnvironmentMutableConfig_cloneMutableConfig(){ return new UmpleSourceData().setFileNames("EnvironmentMutableConfig.ump").setUmpleLines(210).setJavaLines(266).setLengths(7);}
    public UmpleSourceData RecoveryException_RecoveryException(){ return new UmpleSourceData().setFileNames("RecoveryException.ump","RecoveryException.ump").setUmpleLines(9, 13).setJavaLines(36, 41).setLengths(1, 1);}
    public UmpleSourceData RecoveryInfo_toString(){ return new UmpleSourceData().setFileNames("RecoveryInfo.ump").setUmpleLines(47).setJavaLines(30).setLengths(22);}
    public UmpleSourceData RecoveryInfo_appendLsn(){ return new UmpleSourceData().setFileNames("RecoveryInfo.ump").setUmpleLines(72).setJavaLines(56).setLengths(3);}
    public UmpleSourceData CheckpointStart_postLogWork(){ return new UmpleSourceData().setFileNames("CheckpointStart.ump").setUmpleLines(59).setJavaLines(90).setLengths(1);}
    public UmpleSourceData CheckpointStart_writeToLog(){ return new UmpleSourceData().setFileNames("CheckpointStart.ump").setUmpleLines(72).setJavaLines(110).setLengths(3);}
    public UmpleSourceData CheckpointStart_logEntryIsTransactional(){ return new UmpleSourceData().setFileNames("CheckpointStart.ump").setUmpleLines(100).setJavaLines(147).setLengths(1);}
    public UmpleSourceData CheckpointStart_getLogType(){ return new UmpleSourceData().setFileNames("CheckpointStart.ump").setUmpleLines(38).setJavaLines(60).setLengths(1);}
    public UmpleSourceData CheckpointStart_readFromLog(){ return new UmpleSourceData().setFileNames("CheckpointStart.ump").setUmpleLines(81).setJavaLines(122).setLengths(3);}
    public UmpleSourceData CheckpointStart_getTransactionId(){ return new UmpleSourceData().setFileNames("CheckpointStart.ump").setUmpleLines(107).setJavaLines(157).setLengths(1);}
    public UmpleSourceData CheckpointStart_getLogSize(){ return new UmpleSourceData().setFileNames("CheckpointStart.ump").setUmpleLines(65).setJavaLines(100).setLengths(1);}
    public UmpleSourceData CheckpointStart_countAsObsoleteWhenLogged(){ return new UmpleSourceData().setFileNames("CheckpointStart.ump").setUmpleLines(52).setJavaLines(80).setLengths(1);}
    public UmpleSourceData CheckpointStart_CheckpointStart(){ return new UmpleSourceData().setFileNames("CheckpointStart.ump","CheckpointStart.ump").setUmpleLines(21, 32).setJavaLines(38, 50).setLengths(8, 1);}
    public UmpleSourceData CheckpointStart_dumpLog(){ return new UmpleSourceData().setFileNames("CheckpointStart.ump").setUmpleLines(90).setJavaLines(134).setLengths(4);}
    public UmpleSourceData CheckpointStart_marshallOutsideWriteLatch(){ return new UmpleSourceData().setFileNames("CheckpointStart.ump").setUmpleLines(45).setJavaLines(70).setLengths(1);}
    public UmpleSourceData Checkpointer_isRunnable(){ return new UmpleSourceData().setFileNames("Checkpointer.ump").setUmpleLines(99).setJavaLines(158).setLengths(1);}
    public UmpleSourceData Checkpointer_addToDirtyMap(){ return new UmpleSourceData().setFileNames("Checkpointer.ump").setUmpleLines(280).setJavaLines(387).setLengths(7);}
    public UmpleSourceData Checkpointer_Checkpointer(){ return new UmpleSourceData().setFileNames("Checkpointer.ump").setUmpleLines(51).setJavaLines(93).setLengths(21);}
    public UmpleSourceData Checkpointer_getHighestFlushLevel(){ return new UmpleSourceData().setFileNames("Checkpointer.ump").setUmpleLines(63).setJavaLines(118).setLengths(1);}
    public UmpleSourceData Checkpointer_flushIN(){ return new UmpleSourceData().setFileNames("Checkpointer.ump").setUmpleLines(163).setJavaLines(242).setLengths(51);}
    public UmpleSourceData Checkpointer_hook527(){ return new UmpleSourceData().setFileNames("Checkpointer.ump").setUmpleLines(320).setJavaLines(426).setLengths(1);}
    public UmpleSourceData Checkpointer_doCheckpoint_hook525(){ return new UmpleSourceData().setFileNames("LoggingSevere_Checkpointer_inner.ump").setUmpleLines(5).setJavaLines(179).setLengths(7);}
    public UmpleSourceData Checkpointer_hook545(){ return new UmpleSourceData().setFileNames("Checkpointer.ump").setUmpleLines(337).setJavaLines(455).setLengths(1);}
    public UmpleSourceData Checkpointer_resetPerRunCounters(){ return new UmpleSourceData().setFileNames("Statistics_Checkpointer.ump").setUmpleLines(43).setJavaLines(517).setLengths(2);}
    public UmpleSourceData Checkpointer_isRunnable_Checkpointer_isRunnable(){ return new UmpleSourceData().setFileNames("Checkpointer_static.ump").setUmpleLines(51).setJavaLines(33).setLengths(2);}
    public UmpleSourceData CheckpointReference_CheckpointReference(){ return new UmpleSourceData().setFileNames("Checkpointer_static.ump").setUmpleLines(11).setJavaLines(184).setLengths(6);}
    public UmpleSourceData Checkpointer_getWakeupPeriod_hook541(){ return new UmpleSourceData().setFileNames("Checkpointer_static.ump").setUmpleLines(47).setJavaLines(74).setLengths(1);}
    public UmpleSourceData Checkpointer_trace(){ return new UmpleSourceData().setFileNames("LoggingConfig_Checkpointer.ump").setUmpleLines(5).setJavaLines(523).setLengths(10);}
    public UmpleSourceData CheckpointReference_hashCode(){ return new UmpleSourceData().setFileNames("Checkpointer_static.ump").setUmpleLines(26).setJavaLines(203).setLengths(1);}
    public UmpleSourceData Checkpointer_flushDirtyNodes(){ return new UmpleSourceData().setFileNames("Checkpointer.ump").setUmpleLines(132).setJavaLines(198).setLengths(25);}
    public UmpleSourceData Checkpointer_getWakeupPeriod(){ return new UmpleSourceData().setFileNames("Checkpointer.ump").setUmpleLines(71).setJavaLines(128).setLengths(1);}
    public UmpleSourceData Checkpointer_doCheckpoint(){ return new UmpleSourceData().setFileNames("Checkpointer.ump").setUmpleLines(110).setJavaLines(171).setLengths(1);}
    public UmpleSourceData Checkpointer_flushUtilizationInfo(){ return new UmpleSourceData().setFileNames("Checkpointer.ump").setUmpleLines(117).setJavaLines(181).setLengths(8);}
    public UmpleSourceData Checkpointer_logTargetAndUpdateParent(){ return new UmpleSourceData().setFileNames("Checkpointer.ump").setUmpleLines(253).setJavaLines(341).setLengths(37);}
    public UmpleSourceData Checkpointer_getWakeupPeriod_hook519(){ return new UmpleSourceData().setFileNames("Checkpointer_static.ump").setUmpleLines(44).setJavaLines(64).setLengths(1);}
    public UmpleSourceData Checkpointer_doCheckpoint_hook536(){ return new UmpleSourceData().setFileNames("Checkpointer_static.ump").setUmpleLines(194).setJavaLines(174).setLengths(1);}
    public UmpleSourceData Checkpointer_doCheckpoint_hook535(){ return new UmpleSourceData().setFileNames("Checkpointer_static.ump").setUmpleLines(193).setJavaLines(169).setLengths(1);}
    public UmpleSourceData Checkpointer_getWakeupPeriod_Checkpointer_getWakeupPeriod(){ return new UmpleSourceData().setFileNames("Checkpointer_static.ump").setUmpleLines(31).setJavaLines(31).setLengths(1);}
    public UmpleSourceData Checkpointer_doCheckpoint_hook534(){ return new UmpleSourceData().setFileNames("Checkpointer_static.ump").setUmpleLines(192).setJavaLines(164).setLengths(1);}
    public UmpleSourceData Checkpointer_checkParentChildRelationship(){ return new UmpleSourceData().setFileNames("Checkpointer.ump").setUmpleLines(214).setJavaLines(302).setLengths(23);}
    public UmpleSourceData Checkpointer_hook533(){ return new UmpleSourceData().setFileNames("Checkpointer.ump").setUmpleLines(326).setJavaLines(441).setLengths(1);}
    public UmpleSourceData Checkpointer_getWakeupPeriod_execute(){ return new UmpleSourceData().setFileNames("CPTime_Checkpointer_inner.ump","Checkpointer_static.ump","Checkpointer_static.ump","Checkpointer_static.ump","Checkpointer_static.ump").setUmpleLines(5, 34, 56, 100, 216).setJavaLines(38, 36, 39, 43, 38).setLengths(2, 20, 89, 109, 62);}
    public UmpleSourceData Checkpointer_hook532(){ return new UmpleSourceData().setFileNames("Checkpointer.ump").setUmpleLines(324).setJavaLines(436).setLengths(1);}
    public UmpleSourceData Checkpointer_doCheckpoint_Checkpointer_doCheckpoint(){ return new UmpleSourceData().setFileNames("Checkpointer_static.ump").setUmpleLines(94).setJavaLines(35).setLengths(4);}
    public UmpleSourceData Checkpointer_hook531(){ return new UmpleSourceData().setFileNames("Checkpointer.ump").setUmpleLines(322).setJavaLines(431).setLengths(1);}
    public UmpleSourceData Checkpointer_onWakeup(){ return new UmpleSourceData().setFileNames("CheckpointerDaemon_Checkpointer.ump").setUmpleLines(26).setJavaLines(482).setLengths(4);}
    public UmpleSourceData Checkpointer_selectDirtyINs(){ return new UmpleSourceData().setFileNames("Checkpointer.ump").setUmpleLines(155).setJavaLines(232).setLengths(1);}
    public UmpleSourceData Checkpointer_nDeadlockRetries(){ return new UmpleSourceData().setFileNames("CheckpointerDaemon_Checkpointer.ump").setUmpleLines(19).setJavaLines(472).setLengths(1);}
    public UmpleSourceData Checkpointer_getFirstActiveLsn(){ return new UmpleSourceData().setFileNames("Checkpointer.ump").setUmpleLines(85).setJavaLines(138).setLengths(1);}
    public UmpleSourceData Checkpointer_loadStats(){ return new UmpleSourceData().setFileNames("Statistics_Checkpointer.ump").setUmpleLines(24).setJavaLines(495).setLengths(13);}
    public UmpleSourceData CheckpointReference_equals(){ return new UmpleSourceData().setFileNames("Checkpointer_static.ump").setUmpleLines(19).setJavaLines(194).setLengths(5);}
    public UmpleSourceData Checkpointer_setFirstActiveLsn(){ return new UmpleSourceData().setFileNames("Checkpointer.ump").setUmpleLines(92).setJavaLines(148).setLengths(1);}
    public UmpleSourceData Checkpointer_selectDirtyINs_Checkpointer_selectDirtyINs(){ return new UmpleSourceData().setFileNames("Checkpointer_static.ump").setUmpleLines(211).setJavaLines(31).setLengths(3);}
    public UmpleSourceData Checkpointer_dumpParentChildInfo(){ return new UmpleSourceData().setFileNames("Checkpointer.ump").setUmpleLines(241).setJavaLines(329).setLengths(8);}
    public UmpleSourceData Checkpointer_toString(){ return new UmpleSourceData().setFileNames("CheckpointerDaemon_Checkpointer.ump").setUmpleLines(6).setJavaLines(460).setLengths(3);}
    public UmpleSourceData RecoveryManager_findEndOfLog(){ return new UmpleSourceData().setFileNames("Derivative_LoggingDbLogHandler_LoggingBase_RecoveryManager.ump","RecoveryManager.ump").setUmpleLines(16, 151).setJavaLines(209, 185).setLengths(2, 22);}
    public UmpleSourceData RecoveryManager_passStartHeader(){ return new UmpleSourceData().setFileNames("RecoveryManager.ump").setUmpleLines(953).setJavaLines(1181).setLengths(1);}
    public UmpleSourceData RecoveryManager_traceINDeleteReplay_RecoveryManager_traceINDeleteReplay(){ return new UmpleSourceData().setFileNames("LoggingRecovery_RecoveryManager_inner.ump").setUmpleLines(5).setJavaLines(29).setLengths(7);}
    public UmpleSourceData RecoveryManager_replaceOrInsertChild(){ return new UmpleSourceData().setFileNames("RecoveryManager.ump").setUmpleLines(691).setJavaLines(864).setLengths(36);}
    public UmpleSourceData RecoveryManager_redo(){ return new UmpleSourceData().setFileNames("RecoveryManager.ump").setUmpleLines(732).setJavaLines(916).setLengths(61);}
    public UmpleSourceData RecoveryManager_redoUtilizationInfo(){ return new UmpleSourceData().setFileNames("RecoveryManager.ump").setUmpleLines(892).setJavaLines(1115).setLengths(32);}
    public UmpleSourceData RecoveryManager_undoUtilizationInfo(){ return new UmpleSourceData().setFileNames("RecoveryManager.ump").setUmpleLines(931).setJavaLines(1156).setLengths(16);}
    public UmpleSourceData RecoveryManager_buildTree(){ return new UmpleSourceData().setFileNames("RecoveryManager.ump").setUmpleLines(223).setJavaLines(267).setLengths(111);}
    public UmpleSourceData RecoveryManager_undo(){ return new UmpleSourceData().setFileNames("RecoveryManager.ump").setUmpleLines(794).setJavaLines(993).setLengths(73);}
    public UmpleSourceData RecoveryManager_trace(){ return new UmpleSourceData().setFileNames("RecoveryManager.ump").setUmpleLines(969).setJavaLines(1201).setLengths(2);}
    public UmpleSourceData RecoveryManager_traceAndThrowException(){ return new UmpleSourceData().setFileNames("RecoveryManager.ump").setUmpleLines(974).setJavaLines(1207).setLengths(3);}
    public UmpleSourceData RecoveryManager_insertRecovery(){ return new UmpleSourceData().setFileNames("RecoveryManager.ump").setUmpleLines(859).setJavaLines(1080).setLengths(26);}
    public UmpleSourceData TxnNodeId_hashCode(){ return new UmpleSourceData().setFileNames("RecoveryManager_static.ump").setUmpleLines(23).setJavaLines(83).setLengths(1);}
    public UmpleSourceData RootDeleter_doWork(){ return new UmpleSourceData().setFileNames("RecoveryManager_static.ump").setUmpleLines(39).setJavaLines(57).setLengths(2);}
    public UmpleSourceData RecoveryManager_traceINDeleteReplay(){ return new UmpleSourceData().setFileNames("LoggingRecovery_RecoveryManager.ump").setUmpleLines(9).setJavaLines(1219).setLengths(1);}
    public UmpleSourceData RecoveryManager_traceRootDeletion(){ return new UmpleSourceData().setFileNames("LoggingFine_RecoveryManager.ump").setUmpleLines(8).setJavaLines(1229).setLengths(1);}
    public UmpleSourceData RecoveryManager_replayOneIN(){ return new UmpleSourceData().setFileNames("RecoveryManager.ump").setUmpleLines(380).setJavaLines(480).setLengths(18);}
    public UmpleSourceData RecoveryManager_replaceOrInsertRoot(){ return new UmpleSourceData().setFileNames("RecoveryManager.ump").setUmpleLines(633).setJavaLines(790).setLengths(19);}
    public UmpleSourceData RecoveryManager_recover(){ return new UmpleSourceData().setFileNames("RecoveryManager.ump").setUmpleLines(106).setJavaLines(119).setLengths(57);}
    public UmpleSourceData RecoveryManager_findLastCheckpoint(){ return new UmpleSourceData().setFileNames("RecoveryManager.ump").setUmpleLines(179).setJavaLines(220).setLengths(38);}
    public UmpleSourceData RecoveryManager_traceRootDeletion_RecoveryManager_traceRootDeletion(){ return new UmpleSourceData().setFileNames("LoggingFine_RecoveryManager_inner.ump").setUmpleLines(5).setJavaLines(29).setLengths(2);}
    public UmpleSourceData RecoveryManager_printTrackList(){ return new UmpleSourceData().setFileNames("RecoveryManager.ump").setUmpleLines(586).setJavaLines(727).setLengths(13);}
    public UmpleSourceData RecoveryManager_redoLNs(){ return new UmpleSourceData().setFileNames("RecoveryManager.ump").setUmpleLines(470).setJavaLines(592).setLengths(64);}
    public UmpleSourceData RecoveryManager_replaceOrInsertDuplicateRoot(){ return new UmpleSourceData().setFileNames("RecoveryManager.ump").setUmpleLines(654).setJavaLines(818).setLengths(42);}
    public UmpleSourceData RecoveryManager_replayINDelete(){ return new UmpleSourceData().setFileNames("RecoveryManager.ump").setUmpleLines(606).setJavaLines(749).setLengths(32);}
    public UmpleSourceData RecoveryManager_trace_execute(){ return new UmpleSourceData().setFileNames("loggingBase_RecoveryManager_inner.ump","RecoveryManager_static.ump","Derivative_LoggingRecovery_LoggingBase_RecoveryManager_inner.ump","LoggingRecovery_RecoveryManager_inner.ump","Derivative_LoggingFine_LoggingBase_RecoveryManager_inner.ump","LoggingFine_RecoveryManager_inner.ump").setUmpleLines(6, 60, 6, 14, 6, 9).setJavaLines(49, 47, 42, 40, 37, 35).setLengths(34, 1, 12, 1, 8, 1);}
    public UmpleSourceData RecoveryManager_undoLNs(){ return new UmpleSourceData().setFileNames("RecoveryManager.ump").setUmpleLines(401).setJavaLines(507).setLengths(73);}
    public UmpleSourceData RecoveryManager_passEndHeader(){ return new UmpleSourceData().setFileNames("RecoveryManager.ump").setUmpleLines(960).setJavaLines(1191).setLengths(1);}
    public UmpleSourceData RecoveryManager_RecoveryManager(){ return new UmpleSourceData().setFileNames("RecoveryManager.ump").setUmpleLines(90).setJavaLines(97).setLengths(12);}
    public UmpleSourceData RecoveryManager_readINsAndTrackIds(){ return new UmpleSourceData().setFileNames("RecoveryManager.ump").setUmpleLines(290).setJavaLines(382).setLengths(41);}
    public UmpleSourceData RecoveryManager_replaceOrInsert(){ return new UmpleSourceData().setFileNames("RecoveryManager.ump").setUmpleLines(561).setJavaLines(690).setLengths(28);}
    public UmpleSourceData RecoveryManager_rebuildINList(){ return new UmpleSourceData().setFileNames("RecoveryManager.ump").setUmpleLines(538).setJavaLines(665).setLengths(12);}
    public UmpleSourceData TxnNodeId_TxnNodeId(){ return new UmpleSourceData().setFileNames("RecoveryManager_static.ump").setUmpleLines(7).setJavaLines(61).setLengths(2);}
    public UmpleSourceData RecoveryManager_trace_RecoveryManager_trace(){ return new UmpleSourceData().setFileNames("RecoveryManager_static.ump").setUmpleLines(45).setJavaLines(30).setLengths(13);}
    public UmpleSourceData RootDeleter_RootDeleter(){ return new UmpleSourceData().setFileNames("RecoveryManager_static.ump").setUmpleLines(33).setJavaLines(47).setLengths(1);}
    public UmpleSourceData TxnNodeId_equals(){ return new UmpleSourceData().setFileNames("RecoveryManager_static.ump").setUmpleLines(14).setJavaLines(72).setLengths(7);}
    public UmpleSourceData TxnNodeId_toString(){ return new UmpleSourceData().setFileNames("RecoveryManager_static.ump").setUmpleLines(26).setJavaLines(88).setLengths(1);}
    public UmpleSourceData RecoveryManager_readINs(){ return new UmpleSourceData().setFileNames("RecoveryManager.ump").setUmpleLines(335).setJavaLines(432).setLengths(39);}
    public UmpleSourceData RootFlusher_stillRoot(){ return new UmpleSourceData().setFileNames("RootFlusher.ump").setUmpleLines(57).setJavaLines(82).setLengths(1);}
    public UmpleSourceData RootFlusher_doWork(){ return new UmpleSourceData().setFileNames("RootFlusher.ump").setUmpleLines(34).setJavaLines(51).setLengths(22);}
    public UmpleSourceData RootFlusher_getFlushed(){ return new UmpleSourceData().setFileNames("RootFlusher.ump").setUmpleLines(53).setJavaLines(77).setLengths(1);}
    public UmpleSourceData RootFlusher_RootFlusher(){ return new UmpleSourceData().setFileNames("RootFlusher.ump").setUmpleLines(23).setJavaLines(37).setLengths(5);}
    public UmpleSourceData CheckpointEnd_getRootLsn(){ return new UmpleSourceData().setFileNames("CheckpointEnd.ump").setUmpleLines(205).setJavaLines(242).setLengths(1);}
    public UmpleSourceData CheckpointEnd_getLastNodeId(){ return new UmpleSourceData().setFileNames("CheckpointEnd.ump").setUmpleLines(213).setJavaLines(252).setLengths(1);}
    public UmpleSourceData CheckpointEnd_getLogType(){ return new UmpleSourceData().setFileNames("CheckpointEnd.ump").setUmpleLines(72).setJavaLines(78).setLengths(1);}
    public UmpleSourceData CheckpointEnd_readFromLog(){ return new UmpleSourceData().setFileNames("CheckpointEnd.ump").setUmpleLines(130).setJavaLines(155).setLengths(12);}
    public UmpleSourceData CheckpointEnd_getLogSize(){ return new UmpleSourceData().setFileNames("CheckpointEnd.ump").setUmpleLines(99).setJavaLines(118).setLengths(7);}
    public UmpleSourceData CheckpointEnd_CheckpointEnd(){ return new UmpleSourceData().setFileNames("CheckpointEnd.ump","CheckpointEnd.ump").setUmpleLines(37, 63).setJavaLines(39, 66).setLengths(23, 3);}
    public UmpleSourceData CheckpointEnd_getId(){ return new UmpleSourceData().setFileNames("CheckpointEnd.ump").setUmpleLines(225).setJavaLines(267).setLengths(1);}
    public UmpleSourceData CheckpointEnd_getLastTxnId(){ return new UmpleSourceData().setFileNames("CheckpointEnd.ump").setUmpleLines(221).setJavaLines(262).setLengths(1);}
    public UmpleSourceData CheckpointEnd_getLastDbId(){ return new UmpleSourceData().setFileNames("CheckpointEnd.ump").setUmpleLines(217).setJavaLines(257).setLengths(1);}
    public UmpleSourceData CheckpointEnd_postLogWork(){ return new UmpleSourceData().setFileNames("CheckpointEnd.ump").setUmpleLines(93).setJavaLines(108).setLengths(1);}
    public UmpleSourceData CheckpointEnd_writeToLog(){ return new UmpleSourceData().setFileNames("CheckpointEnd.ump").setUmpleLines(112).setJavaLines(134).setLengths(12);}
    public UmpleSourceData CheckpointEnd_logEntryIsTransactional(){ return new UmpleSourceData().setFileNames("CheckpointEnd.ump").setUmpleLines(174).setJavaLines(205).setLengths(1);}
    public UmpleSourceData CheckpointEnd_getCheckpointStartLsn(){ return new UmpleSourceData().setFileNames("CheckpointEnd.ump").setUmpleLines(201).setJavaLines(237).setLengths(1);}
    public UmpleSourceData CheckpointEnd_getFirstActiveLsn(){ return new UmpleSourceData().setFileNames("CheckpointEnd.ump").setUmpleLines(209).setJavaLines(247).setLengths(1);}
    public UmpleSourceData CheckpointEnd_getTransactionId(){ return new UmpleSourceData().setFileNames("CheckpointEnd.ump").setUmpleLines(181).setJavaLines(215).setLengths(1);}
    public UmpleSourceData CheckpointEnd_countAsObsoleteWhenLogged(){ return new UmpleSourceData().setFileNames("CheckpointEnd.ump").setUmpleLines(86).setJavaLines(98).setLengths(1);}
    public UmpleSourceData CheckpointEnd_dumpLog(){ return new UmpleSourceData().setFileNames("CheckpointEnd.ump").setUmpleLines(148).setJavaLines(176).setLengths(20);}
    public UmpleSourceData CheckpointEnd_toString(){ return new UmpleSourceData().setFileNames("CheckpointEnd.ump").setUmpleLines(185).setJavaLines(220).setLengths(13);}
    public UmpleSourceData CheckpointEnd_marshallOutsideWriteLatch(){ return new UmpleSourceData().setFileNames("CheckpointEnd.ump").setUmpleLines(79).setJavaLines(88).setLengths(1);}
    public UmpleSourceData RootUpdater_getReplaced(){ return new UmpleSourceData().setFileNames("RootUpdater.ump").setUmpleLines(55).setJavaLines(74).setLengths(1);}
    public UmpleSourceData RootUpdater_doWork(){ return new UmpleSourceData().setFileNames("RootUpdater.ump").setUmpleLines(31).setJavaLines(44).setLengths(16);}
    public UmpleSourceData RootUpdater_RootUpdater(){ return new UmpleSourceData().setFileNames("RootUpdater.ump").setUmpleLines(25).setJavaLines(37).setLengths(3);}
    public UmpleSourceData RootUpdater_getInserted(){ return new UmpleSourceData().setFileNames("RootUpdater.ump").setUmpleLines(51).setJavaLines(69).setLengths(1);}
    public UmpleSourceData RootUpdater_getOriginalLsn(){ return new UmpleSourceData().setFileNames("RootUpdater.ump").setUmpleLines(59).setJavaLines(79).setLengths(1);}
    public UmpleSourceData RootUpdater_hook600(){ return new UmpleSourceData().setFileNames("RootUpdater.ump").setUmpleLines(63).setJavaLines(84).setLengths(1);}
    public UmpleSourceData RootUpdater_updateDone(){ return new UmpleSourceData().setFileNames("RootUpdater.ump").setUmpleLines(47).setJavaLines(64).setLengths(1);}
    public UmpleSourceData Database_removeTrigger(){ return new UmpleSourceData().setFileNames("Database.ump").setUmpleLines(598).setJavaLines(787).setLengths(7);}
    public UmpleSourceData Database_removeSequence(){ return new UmpleSourceData().setFileNames("Database.ump").setUmpleLines(166).setJavaLines(260).setLengths(1);}
    public UmpleSourceData Database_getSecondaryDatabases(){ return new UmpleSourceData().setFileNames("Database.ump").setUmpleLines(451).setJavaLines(600).setLengths(24);}
    public UmpleSourceData Database_getDatabaseName(){ return new UmpleSourceData().setFileNames("Database.ump").setUmpleLines(414).setJavaLines(556).setLengths(6);}
    public UmpleSourceData Database_isTransactional(){ return new UmpleSourceData().setFileNames("Database.ump").setUmpleLines(443).setJavaLines(590).setLengths(1);}
    public UmpleSourceData Database_preload(){ return new UmpleSourceData().setFileNames("Database.ump","Database.ump","Database.ump").setUmpleLines(385, 395, 406).setJavaLines(515, 529, 544).setLengths(10, 11, 8);}
    public UmpleSourceData Database_put(){ return new UmpleSourceData().setFileNames("Database.ump").setUmpleLines(292).setJavaLines(407).setLengths(11);}
    public UmpleSourceData Database_checkWritable(){ return new UmpleSourceData().setFileNames("Database.ump").setUmpleLines(543).setJavaLines(714).setLengths(3);}
    public UmpleSourceData Database_truncate(){ return new UmpleSourceData().setFileNames("Truncate_Database.ump").setUmpleLines(9).setJavaLines(847).setLengths(1);}
    public UmpleSourceData Database_putInternal(){ return new UmpleSourceData().setFileNames("Database.ump").setUmpleLines(331).setJavaLines(457).setLengths(17);}
    public UmpleSourceData Database_Database(){ return new UmpleSourceData().setFileNames("Database.ump").setUmpleLines(46).setJavaLines(119).setLengths(2);}
    public UmpleSourceData Database_removeCursor(){ return new UmpleSourceData().setFileNames("Database.ump").setUmpleLines(493).setJavaLines(648).setLengths(1);}
    public UmpleSourceData Database_checkRequiredDbState(){ return new UmpleSourceData().setFileNames("Database.ump").setUmpleLines(504).setJavaLines(663).setLengths(3);}
    public UmpleSourceData Database_hasTriggers(){ return new UmpleSourceData().setFileNames("Database.ump").setUmpleLines(552).setJavaLines(726).setLengths(1);}
    public UmpleSourceData Database_addCursor(){ return new UmpleSourceData().setFileNames("Database.ump").setUmpleLines(497).setJavaLines(653).setLengths(1);}
    public UmpleSourceData Database_join(){ return new UmpleSourceData().setFileNames("Database.ump").setUmpleLines(353).setJavaLines(482).setLengths(29);}
    public UmpleSourceData Database_init(){ return new UmpleSourceData().setFileNames("Database.ump").setUmpleLines(79).setJavaLines(157).setLengths(5);}
    public UmpleSourceData Database_checkProhibitedDbState(){ return new UmpleSourceData().setFileNames("Database.ump").setUmpleLines(513).setJavaLines(675).setLengths(3);}
    public UmpleSourceData Database_getDatabaseImpl(){ return new UmpleSourceData().setFileNames("Database.ump").setUmpleLines(482).setJavaLines(643).setLengths(1);}
    public UmpleSourceData Database_releaseTriggerListReadLock(){ return new UmpleSourceData().setFileNames("Latches_Database.ump").setUmpleLines(8).setJavaLines(899).setLengths(2);}
    public UmpleSourceData Database_deleteInternal(){ return new UmpleSourceData().setFileNames("Database.ump").setUmpleLines(211).setJavaLines(319).setLengths(30);}
    public UmpleSourceData Database_releaseTriggerListWriteLock_Database_releaseTriggerListWriteLock(){ return new UmpleSourceData().setFileNames("Database_static.ump").setUmpleLines(38).setJavaLines(29).setLengths(1);}
    public UmpleSourceData Database_initExisting(){ return new UmpleSourceData().setFileNames("Database.ump").setUmpleLines(70).setJavaLines(147).setLengths(6);}
    public UmpleSourceData Database_acquireTriggerListReadLock_execute(){ return new UmpleSourceData().setFileNames("Latches_Database_inner.ump","Database_static.ump","Latches_Database_inner.ump","Database_static.ump","Latches_Database_inner.ump","Database_static.ump","Truncate_Database_inner.ump","Derivative_LoggingFinest_LoggingBase_Database_inner.ump","LoggingFinest_Database_inner.ump","Derivative_LoggingFinest_LoggingBase_Database_inner.ump","LoggingFinest_Database_inner.ump").setUmpleLines(6, 17, 20, 29, 13, 41, 10, 6, 12, 24, 31).setJavaLines(36, 34, 36, 34, 39, 34, 37, 40, 38, 42, 40).setLengths(3, 3, 3, 3, 3, 3, 46, 13, 1, 16, 1);}
    public UmpleSourceData Database_truncateInternal(){ return new UmpleSourceData().setFileNames("Truncate_Database.ump").setUmpleLines(17).setJavaLines(858).setLengths(17);}
    public UmpleSourceData DbState_DbState(){ return new UmpleSourceData().setFileNames("Database_static.ump").setUmpleLines(6).setJavaLines(28).setLengths(1);}
    public UmpleSourceData Database_acquireTriggerListReadLock_Database_acquireTriggerListReadLock(){ return new UmpleSourceData().setFileNames("Database_static.ump").setUmpleLines(14).setJavaLines(29).setLengths(1);}
    public UmpleSourceData Database_getStats(){ return new UmpleSourceData().setFileNames("Statistics_Database.ump").setUmpleLines(5).setJavaLines(879).setLengths(11);}
    public UmpleSourceData Database_putNoOverwrite(){ return new UmpleSourceData().setFileNames("Database.ump").setUmpleLines(304).setJavaLines(422).setLengths(11);}
    public UmpleSourceData Database_openCursor(){ return new UmpleSourceData().setFileNames("Database.ump").setUmpleLines(170).setJavaLines(265).setLengths(12);}
    public UmpleSourceData Database_addTrigger(){ return new UmpleSourceData().setFileNames("Database.ump").setUmpleLines(581).setJavaLines(767).setLengths(11);}
    public UmpleSourceData Database_newDbcInstance(){ return new UmpleSourceData().setFileNames("Database.ump").setUmpleLines(185).setJavaLines(286).setLengths(1);}
    public UmpleSourceData Database_isWritable(){ return new UmpleSourceData().setFileNames("Database.ump").setUmpleLines(475).setJavaLines(633).setLengths(1);}
    public UmpleSourceData Database_delete(){ return new UmpleSourceData().setFileNames("Database.ump").setUmpleLines(189).setJavaLines(291).setLengths(19);}
    public UmpleSourceData Database_initNew(){ return new UmpleSourceData().setFileNames("Database.ump").setUmpleLines(55).setJavaLines(130).setLengths(8);}
    public UmpleSourceData Database_trace_Database_trace(){ return new UmpleSourceData().setFileNames("LoggingFinest_Database_inner.ump").setUmpleLines(22).setJavaLines(29).setLengths(7);}
    public UmpleSourceData Database_getDebugName(){ return new UmpleSourceData().setFileNames("Database.ump").setUmpleLines(423).setJavaLines(566).setLengths(5);}
    public UmpleSourceData Database_notifyTriggers(){ return new UmpleSourceData().setFileNames("Database.ump").setUmpleLines(632).setJavaLines(826).setLengths(12);}
    public UmpleSourceData Database_trace(){ return new UmpleSourceData().setFileNames("LoggingFinest_Database.ump","LoggingFinest_Database.ump").setUmpleLines(9, 16).setJavaLines(910, 920).setLengths(1, 1);}
    public UmpleSourceData Database_truncate_Database_truncate(){ return new UmpleSourceData().setFileNames("Truncate_Database_inner.ump").setUmpleLines(5).setJavaLines(30).setLengths(3);}
    public UmpleSourceData Database_get(){ return new UmpleSourceData().setFileNames("Database.ump").setUmpleLines(245).setJavaLines(353).setLengths(23);}
    public UmpleSourceData Database_verify(){ return new UmpleSourceData().setFileNames("Derivative_Statistics_Verifier_Database.ump").setUmpleLines(5).setJavaLines(925).setLengths(7);}
    public UmpleSourceData Database_validateConfigAgainstExistingDb(){ return new UmpleSourceData().setFileNames("Database.ump").setUmpleLines(91).setJavaLines(171).setLengths(24);}
    public UmpleSourceData Database_acquireTriggerListWriteLock_Database_acquireTriggerListWriteLock(){ return new UmpleSourceData().setFileNames("Database_static.ump").setUmpleLines(26).setJavaLines(29).setLengths(1);}
    public UmpleSourceData Database_close(){ return new UmpleSourceData().setFileNames("Database.ump").setUmpleLines(118).setJavaLines(199).setLengths(34);}
    public UmpleSourceData Database_acquireTriggerListWriteLock(){ return new UmpleSourceData().setFileNames("Database.ump").setUmpleLines(566).setJavaLines(746).setLengths(1);}
    public UmpleSourceData Database_checkEnv(){ return new UmpleSourceData().setFileNames("Database.ump").setUmpleLines(522).setJavaLines(687).setLengths(4);}
    public UmpleSourceData Database_getSearchBoth(){ return new UmpleSourceData().setFileNames("Database.ump").setUmpleLines(269).setJavaLines(380).setLengths(23);}
    public UmpleSourceData Database_releaseTriggerListWriteLock(){ return new UmpleSourceData().setFileNames("Database.ump").setUmpleLines(573).setJavaLines(756).setLengths(1);}
    public UmpleSourceData Database_putNoDupData(){ return new UmpleSourceData().setFileNames("Database.ump").setUmpleLines(316).setJavaLines(437).setLengths(11);}
    public UmpleSourceData Database_openSequence(){ return new UmpleSourceData().setFileNames("Database.ump").setUmpleLines(154).setJavaLines(242).setLengths(9);}
    public UmpleSourceData Database_getEnvironment(){ return new UmpleSourceData().setFileNames("Database.ump").setUmpleLines(447).setJavaLines(595).setLengths(1);}
    public UmpleSourceData Database_invalidate(){ return new UmpleSourceData().setFileNames("Database.ump").setUmpleLines(532).setJavaLines(700).setLengths(5);}
    public UmpleSourceData Database_getConfig(){ return new UmpleSourceData().setFileNames("Database.ump").setUmpleLines(431).setJavaLines(575).setLengths(6);}
    public UmpleSourceData Database_acquireTriggerListReadLock(){ return new UmpleSourceData().setFileNames("Database.ump").setUmpleLines(559).setJavaLines(736).setLengths(1);}
    public UmpleSourceData Database_removeAllTriggers(){ return new UmpleSourceData().setFileNames("Database.ump").setUmpleLines(611).setJavaLines(803).setLengths(10);}
    public UmpleSourceData DbState_toString(){ return new UmpleSourceData().setFileNames("Database_static.ump").setUmpleLines(9).setJavaLines(33).setLengths(1);}
    public UmpleSourceData Database_trace2_Database_trace2(){ return new UmpleSourceData().setFileNames("LoggingFinest_Database_inner.ump").setUmpleLines(5).setJavaLines(29).setLengths(5);}
    public UmpleSourceData DeadlockException_DeadlockException(){ return new UmpleSourceData().setFileNames("DeadlockException.ump","DeadlockException.ump","DeadlockException.ump","DeadlockException.ump").setUmpleLines(7, 11, 15, 19).setJavaLines(33, 38, 43, 48).setLengths(1, 1, 1, 1);}
    public UmpleSourceData SequenceConfig_setCacheSize(){ return new UmpleSourceData().setFileNames("SequenceConfig.ump").setUmpleLines(49).setJavaLines(64).setLengths(1);}
    public UmpleSourceData SequenceConfig_getCacheSize(){ return new UmpleSourceData().setFileNames("SequenceConfig.ump").setUmpleLines(56).setJavaLines(74).setLengths(1);}
    public UmpleSourceData SequenceConfig_getInitialValue(){ return new UmpleSourceData().setFileNames("SequenceConfig.ump").setUmpleLines(98).setJavaLines(134).setLengths(1);}
    public UmpleSourceData SequenceConfig_getExclusiveCreate(){ return new UmpleSourceData().setFileNames("SequenceConfig.ump").setUmpleLines(84).setJavaLines(114).setLengths(1);}
    public UmpleSourceData SequenceConfig_getAllowCreate(){ return new UmpleSourceData().setFileNames("SequenceConfig.ump").setUmpleLines(42).setJavaLines(54).setLengths(1);}
    public UmpleSourceData SequenceConfig_setExclusiveCreate(){ return new UmpleSourceData().setFileNames("SequenceConfig.ump").setUmpleLines(77).setJavaLines(104).setLengths(1);}
    public UmpleSourceData SequenceConfig_setInitialValue(){ return new UmpleSourceData().setFileNames("SequenceConfig.ump").setUmpleLines(91).setJavaLines(124).setLengths(1);}
    public UmpleSourceData SequenceConfig_setRange(){ return new UmpleSourceData().setFileNames("SequenceConfig.ump").setUmpleLines(119).setJavaLines(164).setLengths(2);}
    public UmpleSourceData SequenceConfig_getRangeMin(){ return new UmpleSourceData().setFileNames("SequenceConfig.ump").setUmpleLines(127).setJavaLines(175).setLengths(1);}
    public UmpleSourceData SequenceConfig_getWrap(){ return new UmpleSourceData().setFileNames("SequenceConfig.ump").setUmpleLines(148).setJavaLines(205).setLengths(1);}
    public UmpleSourceData SequenceConfig_getAutoCommitNoSync(){ return new UmpleSourceData().setFileNames("SequenceConfig.ump").setUmpleLines(112).setJavaLines(154).setLengths(1);}
    public UmpleSourceData SequenceConfig_setAutoCommitNoSync(){ return new UmpleSourceData().setFileNames("SequenceConfig.ump").setUmpleLines(105).setJavaLines(144).setLengths(1);}
    public UmpleSourceData SequenceConfig_getDecrement(){ return new UmpleSourceData().setFileNames("SequenceConfig.ump").setUmpleLines(70).setJavaLines(94).setLengths(1);}
    public UmpleSourceData SequenceConfig_getRangeMax(){ return new UmpleSourceData().setFileNames("SequenceConfig.ump").setUmpleLines(134).setJavaLines(185).setLengths(1);}
    public UmpleSourceData SequenceConfig_setAllowCreate(){ return new UmpleSourceData().setFileNames("SequenceConfig.ump").setUmpleLines(35).setJavaLines(44).setLengths(1);}
    public UmpleSourceData SequenceConfig_setDecrement(){ return new UmpleSourceData().setFileNames("SequenceConfig.ump").setUmpleLines(63).setJavaLines(84).setLengths(1);}
    public UmpleSourceData SequenceConfig_SequenceConfig(){ return new UmpleSourceData().setFileNames("SequenceConfig.ump").setUmpleLines(29).setJavaLines(34).setLengths(1);}
    public UmpleSourceData SequenceConfig_setWrap(){ return new UmpleSourceData().setFileNames("SequenceConfig.ump").setUmpleLines(141).setJavaLines(195).setLengths(1);}
    public UmpleSourceData DbCompat_isTypeQueue(){ return new UmpleSourceData().setFileNames("DbCompat.ump").setUmpleLines(65).setJavaLines(66).setLengths(1);}
    public UmpleSourceData DbCompat_setSerializableIsolation(){ return new UmpleSourceData().setFileNames("DbCompat.ump").setUmpleLines(177).setJavaLines(197).setLengths(1);}
    public UmpleSourceData DbCompat_setRenumbering(){ return new UmpleSourceData().setFileNames("DbCompat.ump").setUmpleLines(207).setJavaLines(237).setLengths(1);}
    public UmpleSourceData DbCompat_setInitializeLocking(){ return new UmpleSourceData().setFileNames("DbCompat.ump").setUmpleLines(162).setJavaLines(178).setLengths(3);}
    public UmpleSourceData DbCompat_setTypeQueue(){ return new UmpleSourceData().setFileNames("DbCompat.ump").setUmpleLines(196).setJavaLines(222).setLengths(1);}
    public UmpleSourceData DbCompat_isTypeBtree(){ return new UmpleSourceData().setFileNames("DbCompat.ump").setUmpleLines(57).setJavaLines(56).setLengths(1);}
    public UmpleSourceData DbCompat_getThreadTransaction(){ return new UmpleSourceData().setFileNames("DbCompat.ump").setUmpleLines(152).setJavaLines(166).setLengths(1);}
    public UmpleSourceData DbCompat_getUnsortedDuplicates(){ return new UmpleSourceData().setFileNames("DbCompat.ump").setUmpleLines(89).setJavaLines(96).setLengths(1);}
    public UmpleSourceData DbCompat_setTypeRecno(){ return new UmpleSourceData().setFileNames("DbCompat.ump").setUmpleLines(192).setJavaLines(217).setLengths(1);}
    public UmpleSourceData DbCompat_makeDbName(){ return new UmpleSourceData().setFileNames("DbCompat.ump").setUmpleLines(241).setJavaLines(276).setLengths(9);}
    public UmpleSourceData DbCompat_getRenumbering(){ return new UmpleSourceData().setFileNames("DbCompat.ump").setUmpleLines(81).setJavaLines(86).setLengths(1);}
    public UmpleSourceData DbCompat_isTypeHash(){ return new UmpleSourceData().setFileNames("DbCompat.ump").setUmpleLines(61).setJavaLines(61).setLengths(1);}
    public UmpleSourceData DbCompat_setLockDetectModeOldest(){ return new UmpleSourceData().setFileNames("DbCompat.ump").setUmpleLines(174).setJavaLines(192).setLengths(1);}
    public UmpleSourceData DbCompat_setRecordLength(){ return new UmpleSourceData().setFileNames("DbCompat.ump").setUmpleLines(221).setJavaLines(254).setLengths(3);}
    public UmpleSourceData DbCompat_getSortedDuplicates(){ return new UmpleSourceData().setFileNames("DbCompat.ump").setUmpleLines(85).setJavaLines(91).setLengths(1);}
    public UmpleSourceData DbCompat_setTypeHash(){ return new UmpleSourceData().setFileNames("DbCompat.ump").setUmpleLines(188).setJavaLines(212).setLengths(1);}
    public UmpleSourceData DbCompat_isTypeRecno(){ return new UmpleSourceData().setFileNames("DbCompat.ump").setUmpleLines(69).setJavaLines(71).setLengths(1);}
    public UmpleSourceData DbCompat_setInitializeCache(){ return new UmpleSourceData().setFileNames("DbCompat.ump").setUmpleLines(156).setJavaLines(171).setLengths(3);}
    public UmpleSourceData DbCompat_setInitializeCDB(){ return new UmpleSourceData().setFileNames("DbCompat.ump").setUmpleLines(168).setJavaLines(185).setLengths(3);}
    public UmpleSourceData DbCompat_getWriteCursor(){ return new UmpleSourceData().setFileNames("DbCompat.ump").setUmpleLines(100).setJavaLines(109).setLengths(1);}
    public UmpleSourceData DbCompat_getDatabaseFile(){ return new UmpleSourceData().setFileNames("DbCompat.ump").setUmpleLines(118).setJavaLines(131).setLengths(1);}
    public UmpleSourceData DbCompat_openDatabase(){ return new UmpleSourceData().setFileNames("DbCompat.ump").setUmpleLines(232).setJavaLines(266).setLengths(1);}
    public UmpleSourceData DbCompat_setReadUncommitted(){ return new UmpleSourceData().setFileNames("DbCompat.ump").setUmpleLines(204).setJavaLines(232).setLengths(1);}
    public UmpleSourceData DbCompat_getCurrentRecordNumber(){ return new UmpleSourceData().setFileNames("DbCompat.ump").setUmpleLines(123).setJavaLines(136).setLengths(1);}
    public UmpleSourceData DbCompat_getInitializeLocking(){ return new UmpleSourceData().setFileNames("DbCompat.ump").setUmpleLines(49).setJavaLines(46).setLengths(1);}
    public UmpleSourceData DbCompat_putBefore(){ return new UmpleSourceData().setFileNames("DbCompat.ump").setUmpleLines(143).setJavaLines(156).setLengths(1);}
    public UmpleSourceData DbCompat_getInitializeCDB(){ return new UmpleSourceData().setFileNames("DbCompat.ump").setUmpleLines(53).setJavaLines(51).setLengths(1);}
    public UmpleSourceData DbCompat_setUnsortedDuplicates(){ return new UmpleSourceData().setFileNames("DbCompat.ump").setUmpleLines(215).setJavaLines(247).setLengths(3);}
    public UmpleSourceData DbCompat_getReadUncommitted(){ return new UmpleSourceData().setFileNames("DbCompat.ump").setUmpleLines(77).setJavaLines(81).setLengths(1);}
    public UmpleSourceData DbCompat_cloneCursorConfig(){ return new UmpleSourceData().setFileNames("DbCompat.ump").setUmpleLines(93).setJavaLines(101).setLengths(4);}
    public UmpleSourceData DbCompat_setRecordNumber(){ return new UmpleSourceData().setFileNames("DbCompat.ump").setUmpleLines(110).setJavaLines(121).setLengths(1);}
    public UmpleSourceData DbCompat_setBtreeComparator(){ return new UmpleSourceData().setFileNames("DbCompat.ump").setUmpleLines(181).setJavaLines(202).setLengths(1);}
    public UmpleSourceData DbCompat_setSortedDuplicates(){ return new UmpleSourceData().setFileNames("DbCompat.ump").setUmpleLines(211).setJavaLines(242).setLengths(1);}
    public UmpleSourceData DbCompat_openSecondaryDatabase(){ return new UmpleSourceData().setFileNames("DbCompat.ump").setUmpleLines(237).setJavaLines(271).setLengths(1);}
    public UmpleSourceData DbCompat_getSearchRecordNumber(){ return new UmpleSourceData().setFileNames("DbCompat.ump","DbCompat.ump").setUmpleLines(128, 133).setJavaLines(141, 146).setLengths(1, 1);}
    public UmpleSourceData DbCompat_setTypeBtree(){ return new UmpleSourceData().setFileNames("DbCompat.ump").setUmpleLines(185).setJavaLines(207).setLengths(1);}
    public UmpleSourceData DbCompat_setRecordPad(){ return new UmpleSourceData().setFileNames("DbCompat.ump").setUmpleLines(227).setJavaLines(261).setLengths(1);}
    public UmpleSourceData DbCompat_getBtreeRecordNumbers(){ return new UmpleSourceData().setFileNames("DbCompat.ump").setUmpleLines(73).setJavaLines(76).setLengths(1);}
    public UmpleSourceData DbCompat_setWriteCursor(){ return new UmpleSourceData().setFileNames("DbCompat.ump").setUmpleLines(104).setJavaLines(114).setLengths(3);}
    public UmpleSourceData DbCompat_setBtreeRecordNumbers(){ return new UmpleSourceData().setFileNames("DbCompat.ump").setUmpleLines(200).setJavaLines(227).setLengths(1);}
    public UmpleSourceData DbCompat_getRecordNumber(){ return new UmpleSourceData().setFileNames("DbCompat.ump").setUmpleLines(114).setJavaLines(126).setLengths(1);}
    public UmpleSourceData DbCompat_append(){ return new UmpleSourceData().setFileNames("DbCompat.ump").setUmpleLines(148).setJavaLines(161).setLengths(1);}
    public UmpleSourceData DbCompat_putAfter(){ return new UmpleSourceData().setFileNames("DbCompat.ump").setUmpleLines(138).setJavaLines(151).setLengths(1);}
    public UmpleSourceData ByteArrayBinding_objectToEntry(){ return new UmpleSourceData().setFileNames("ByteArrayBinding.ump").setUmpleLines(27).setJavaLines(52).setLengths(2);}
    public UmpleSourceData ByteArrayBinding_entryToObject(){ return new UmpleSourceData().setFileNames("ByteArrayBinding.ump").setUmpleLines(16).setJavaLines(40).setLengths(8);}
    public UmpleSourceData ByteArrayBinding_ByteArrayBinding(){ return new UmpleSourceData().setFileNames("ByteArrayBinding.ump").setUmpleLines(13).setJavaLines(35).setLengths(1);}
    public UmpleSourceData SerialBase_getSerialOutput(){ return new UmpleSourceData().setFileNames("SerialBase.ump").setUmpleLines(39).setJavaLines(71).setLengths(6);}
    public UmpleSourceData SerialBase_getSerialBufferSize(){ return new UmpleSourceData().setFileNames("SerialBase.ump").setUmpleLines(29).setJavaLines(58).setLengths(1);}
    public UmpleSourceData SerialBase_SerialBase(){ return new UmpleSourceData().setFileNames("SerialBase.ump").setUmpleLines(12).setJavaLines(35).setLengths(1);}
    public UmpleSourceData SerialBase_setSerialBufferSize(){ return new UmpleSourceData().setFileNames("SerialBase.ump").setUmpleLines(20).setJavaLines(46).setLengths(1);}
    public UmpleSourceData SerialSerialBinding_objectToData(){ return new UmpleSourceData().setFileNames("SerialSerialBinding.ump").setUmpleLines(42).setJavaLines(69).setLengths(2);}
    public UmpleSourceData SerialSerialBinding_objectToKey(){ return new UmpleSourceData().setFileNames("SerialSerialBinding.ump").setUmpleLines(37).setJavaLines(63).setLengths(2);}
    public UmpleSourceData SerialSerialBinding_entryToObject(){ return new UmpleSourceData().setFileNames("SerialSerialBinding.ump").setUmpleLines(33).setJavaLines(58).setLengths(1);}
    public UmpleSourceData SerialSerialBinding_SerialSerialBinding(){ return new UmpleSourceData().setFileNames("SerialSerialBinding.ump","SerialSerialBinding.ump").setUmpleLines(19, 28).setJavaLines(40, 52).setLengths(1, 2);}
    public UmpleSourceData TupleSerialKeyCreator_TupleSerialKeyCreator(){ return new UmpleSourceData().setFileNames("TupleSerialKeyCreator.ump","TupleSerialKeyCreator.ump").setUmpleLines(23, 31).setJavaLines(50, 61).setLengths(1, 1);}
    public UmpleSourceData TupleSerialKeyCreator_createSecondaryKey(){ return new UmpleSourceData().setFileNames("TupleSerialKeyCreator.ump").setUmpleLines(36).setJavaLines(66).setLengths(9);}
    public UmpleSourceData TupleSerialKeyCreator_nullifyForeignKey(){ return new UmpleSourceData().setFileNames("TupleSerialKeyCreator.ump","TupleSerialKeyCreator.ump").setUmpleLines(48, 74).setJavaLines(79, 109).setLengths(8, 1);}
    public UmpleSourceData TupleSerialBinding_objectToData(){ return new UmpleSourceData().setFileNames("TupleSerialBinding.ump").setUmpleLines(42).setJavaLines(75).setLengths(2);}
    public UmpleSourceData TupleSerialBinding_TupleSerialBinding(){ return new UmpleSourceData().setFileNames("TupleSerialBinding.ump","TupleSerialBinding.ump").setUmpleLines(20, 28).setJavaLines(47, 58).setLengths(1, 1);}
    public UmpleSourceData TupleSerialBinding_objectToKey(){ return new UmpleSourceData().setFileNames("TupleSerialBinding.ump").setUmpleLines(36).setJavaLines(68).setLengths(3);}
    public UmpleSourceData TupleSerialBinding_entryToObject(){ return new UmpleSourceData().setFileNames("TupleSerialBinding.ump").setUmpleLines(32).setJavaLines(63).setLengths(1);}
    public UmpleSourceData SerialOutput_writeClassDescriptor(){ return new UmpleSourceData().setFileNames("SerialOutput.ump").setUmpleLines(30).setJavaLines(51).setLengths(9);}
    public UmpleSourceData SerialOutput_SerialOutput(){ return new UmpleSourceData().setFileNames("SerialOutput.ump").setUmpleLines(24).setJavaLines(44).setLengths(3);}
    public UmpleSourceData SerialOutput_getStreamHeader(){ return new UmpleSourceData().setFileNames("SerialOutput.ump").setUmpleLines(46).setJavaLines(70).setLengths(1);}
    public UmpleSourceData TupleSerialMarshalledKeyCreator_TupleSerialMarshalledKeyCreator(){ return new UmpleSourceData().setFileNames("TupleSerialMarshalledKeyCreator.ump").setUmpleLines(19).setJavaLines(43).setLengths(6);}
    public UmpleSourceData TupleSerialMarshalledKeyCreator_createSecondaryKey(){ return new UmpleSourceData().setFileNames("TupleSerialMarshalledKeyCreator.ump").setUmpleLines(28).setJavaLines(53).setLengths(2);}
    public UmpleSourceData TupleSerialMarshalledKeyCreator_nullifyForeignKey(){ return new UmpleSourceData().setFileNames("TupleSerialMarshalledKeyCreator.ump").setUmpleLines(33).setJavaLines(59).setLengths(2);}
    public UmpleSourceData TupleSerialMarshalledBinding_objectToData(){ return new UmpleSourceData().setFileNames("TupleSerialMarshalledBinding.ump").setUmpleLines(40).setJavaLines(74).setLengths(1);}
    public UmpleSourceData TupleSerialMarshalledBinding_objectToKey(){ return new UmpleSourceData().setFileNames("TupleSerialMarshalledBinding.ump").setUmpleLines(35).setJavaLines(68).setLengths(2);}
    public UmpleSourceData TupleSerialMarshalledBinding_TupleSerialMarshalledBinding(){ return new UmpleSourceData().setFileNames("TupleSerialMarshalledBinding.ump","TupleSerialMarshalledBinding.ump").setUmpleLines(15, 23).setJavaLines(43, 54).setLengths(1, 1);}
    public UmpleSourceData TupleSerialMarshalledBinding_entryToObject(){ return new UmpleSourceData().setFileNames("TupleSerialMarshalledBinding.ump").setUmpleLines(27).setJavaLines(59).setLengths(5);}
    public UmpleSourceData SerialInput_SerialInput(){ return new UmpleSourceData().setFileNames("SerialInput.ump","SerialInput.ump").setUmpleLines(22, 32).setJavaLines(42, 55).setLengths(1, 3);}
    public UmpleSourceData SerialInput_resolveClass(){ return new UmpleSourceData().setFileNames("SerialInput.ump").setUmpleLines(49).setJavaLines(74).setLengths(9);}
    public UmpleSourceData SerialInput_readClassDescriptor(){ return new UmpleSourceData().setFileNames("SerialInput.ump").setUmpleLines(38).setJavaLines(62).setLengths(8);}
    public UmpleSourceData SerialBinding_objectToEntry(){ return new UmpleSourceData().setFileNames("SerialBinding.ump").setUmpleLines(74).setJavaLines(99).setLengths(13);}
    public UmpleSourceData SerialBinding_getClassLoader(){ return new UmpleSourceData().setFileNames("SerialBinding.ump").setUmpleLines(42).setJavaLines(61).setLengths(1);}
    public UmpleSourceData SerialBinding_SerialBinding(){ return new UmpleSourceData().setFileNames("SerialBinding.ump").setUmpleLines(23).setJavaLines(47).setLengths(5);}
    public UmpleSourceData SerialBinding_entryToObject(){ return new UmpleSourceData().setFileNames("SerialBinding.ump").setUmpleLines(51).setJavaLines(73).setLengths(14);}
    public UmpleSourceData ClassInfo_setClassFormat(){ return new UmpleSourceData().setFileNames("StoredClassCatalog_static.ump").setUmpleLines(34).setJavaLines(64).setLengths(1);}
    public UmpleSourceData StoredClassCatalog_getClassInfo(){ return new UmpleSourceData().setFileNames("StoredClassCatalog.ump").setUmpleLines(136).setJavaLines(148).setLengths(26);}
    public UmpleSourceData StoredClassCatalog_putClassInfo(){ return new UmpleSourceData().setFileNames("StoredClassCatalog.ump").setUmpleLines(169).setJavaLines(183).setLengths(45);}
    public UmpleSourceData StoredClassCatalog_getObjectBytes(){ return new UmpleSourceData().setFileNames("StoredClassCatalog.ump").setUmpleLines(260).setJavaLines(281).setLengths(4);}
    public UmpleSourceData StoredClassCatalog_areClassFormatsEqual(){ return new UmpleSourceData().setFileNames("StoredClassCatalog.ump").setUmpleLines(230).setJavaLines(249).setLengths(9);}
    public UmpleSourceData StoredClassCatalog_hook_getLockMode(){ return new UmpleSourceData().setFileNames("StoredClassCatalog.ump").setUmpleLines(81).setJavaLines(87).setLengths(1);}
    public UmpleSourceData ClassInfo_toDbt(){ return new UmpleSourceData().setFileNames("StoredClassCatalog_static.ump").setUmpleLines(19).setJavaLines(41).setLengths(4);}
    public UmpleSourceData StoredClassCatalog_getBytes(){ return new UmpleSourceData().setFileNames("StoredClassCatalog.ump").setUmpleLines(242).setJavaLines(262).setLengths(15);}
    public UmpleSourceData ClassInfo_setClassID(){ return new UmpleSourceData().setFileNames("StoredClassCatalog_static.ump").setUmpleLines(25).setJavaLines(49).setLengths(1);}
    public UmpleSourceData StoredClassCatalog_getClassFormat(){ return new UmpleSourceData().setFileNames("StoredClassCatalog.ump","StoredClassCatalog.ump","StoredClassCatalog_static.ump").setUmpleLines(101, 109, 31).setJavaLines(108, 118, 59).setLengths(1, 21, 1);}
    public UmpleSourceData StoredClassCatalog_getClassID(){ return new UmpleSourceData().setFileNames("StoredClassCatalog.ump","StoredClassCatalog_static.ump").setUmpleLines(95, 28).setJavaLines(102, 54).setLengths(2, 1);}
    public UmpleSourceData StoredClassCatalog_hook_commitTransaction(){ return new UmpleSourceData().setFileNames("StoredClassCatalog.ump").setUmpleLines(217).setJavaLines(232).setLengths(1);}
    public UmpleSourceData StoredClassCatalog_StoredClassCatalog(){ return new UmpleSourceData().setFileNames("StoredClassCatalog.ump").setUmpleLines(54).setJavaLines(59).setLengths(24);}
    public UmpleSourceData StoredClassCatalog_incrementID(){ return new UmpleSourceData().setFileNames("StoredClassCatalog.ump").setUmpleLines(220).setJavaLines(237).setLengths(3);}
    public UmpleSourceData StoredClassCatalog_close(){ return new UmpleSourceData().setFileNames("StoredClassCatalog.ump").setUmpleLines(85).setJavaLines(92).setLengths(6);}
    public UmpleSourceData ClassInfo_ClassInfo(){ return new UmpleSourceData().setFileNames("StoredClassCatalog_static.ump","StoredClassCatalog_static.ump").setUmpleLines(11, 13).setJavaLines(28, 33).setLengths(1, 4);}
    public UmpleSourceData SerialSerialKeyCreator_SerialSerialKeyCreator(){ return new UmpleSourceData().setFileNames("SerialSerialKeyCreator.ump","SerialSerialKeyCreator.ump").setUmpleLines(26, 38).setJavaLines(44, 58).setLengths(2, 3);}
    public UmpleSourceData SerialSerialKeyCreator_createSecondaryKey(){ return new UmpleSourceData().setFileNames("SerialSerialKeyCreator.ump").setUmpleLines(45).setJavaLines(65).setLengths(9);}
    public UmpleSourceData SerialSerialKeyCreator_nullifyForeignKey(){ return new UmpleSourceData().setFileNames("SerialSerialKeyCreator.ump","SerialSerialKeyCreator.ump").setUmpleLines(57, 81).setJavaLines(78, 107).setLengths(8, 1);}
    public UmpleSourceData RecordNumberBinding_entryToRecordNumber(){ return new UmpleSourceData().setFileNames("RecordNumberBinding.ump").setUmpleLines(28).setJavaLines(58).setLengths(1);}
    public UmpleSourceData RecordNumberBinding_RecordNumberBinding(){ return new UmpleSourceData().setFileNames("RecordNumberBinding.ump").setUmpleLines(12).setJavaLines(36).setLengths(1);}
    public UmpleSourceData RecordNumberBinding_recordNumberToEntry(){ return new UmpleSourceData().setFileNames("RecordNumberBinding.ump").setUmpleLines(37).setJavaLines(70).setLengths(2);}
    public UmpleSourceData RecordNumberBinding_objectToEntry(){ return new UmpleSourceData().setFileNames("RecordNumberBinding.ump").setUmpleLines(19).setJavaLines(46).setLengths(1);}
    public UmpleSourceData RecordNumberBinding_entryToObject(){ return new UmpleSourceData().setFileNames("RecordNumberBinding.ump").setUmpleLines(15).setJavaLines(41).setLengths(1);}
    public UmpleSourceData FloatBinding_sizedOutput(){ return new UmpleSourceData().setFileNames("FloatBinding.ump").setUmpleLines(43).setJavaLines(78).setLengths(1);}
    public UmpleSourceData FloatBinding_getTupleOutput(){ return new UmpleSourceData().setFileNames("FloatBinding.ump").setUmpleLines(18).setJavaLines(44).setLengths(1);}
    public UmpleSourceData FloatBinding_entryToFloat(){ return new UmpleSourceData().setFileNames("FloatBinding.ump").setUmpleLines(27).setJavaLines(56).setLengths(1);}
    public UmpleSourceData FloatBinding_objectToEntry(){ return new UmpleSourceData().setFileNames("FloatBinding.ump").setUmpleLines(14).setJavaLines(39).setLengths(1);}
    public UmpleSourceData FloatBinding_entryToObject(){ return new UmpleSourceData().setFileNames("FloatBinding.ump").setUmpleLines(10).setJavaLines(34).setLengths(1);}
    public UmpleSourceData FloatBinding_floatToEntry(){ return new UmpleSourceData().setFileNames("FloatBinding.ump").setUmpleLines(36).setJavaLines(68).setLengths(1);}
    public UmpleSourceData DoubleBinding_sizedOutput(){ return new UmpleSourceData().setFileNames("DoubleBinding.ump").setUmpleLines(43).setJavaLines(78).setLengths(1);}
    public UmpleSourceData DoubleBinding_getTupleOutput(){ return new UmpleSourceData().setFileNames("DoubleBinding.ump").setUmpleLines(18).setJavaLines(44).setLengths(1);}
    public UmpleSourceData DoubleBinding_doubleToEntry(){ return new UmpleSourceData().setFileNames("DoubleBinding.ump").setUmpleLines(36).setJavaLines(68).setLengths(1);}
    public UmpleSourceData DoubleBinding_objectToEntry(){ return new UmpleSourceData().setFileNames("DoubleBinding.ump").setUmpleLines(14).setJavaLines(39).setLengths(1);}
    public UmpleSourceData DoubleBinding_entryToDouble(){ return new UmpleSourceData().setFileNames("DoubleBinding.ump").setUmpleLines(27).setJavaLines(56).setLengths(1);}
    public UmpleSourceData DoubleBinding_entryToObject(){ return new UmpleSourceData().setFileNames("DoubleBinding.ump").setUmpleLines(10).setJavaLines(34).setLengths(1);}
    public UmpleSourceData CharacterBinding_sizedOutput(){ return new UmpleSourceData().setFileNames("CharacterBinding.ump").setUmpleLines(43).setJavaLines(78).setLengths(1);}
    public UmpleSourceData CharacterBinding_getTupleOutput(){ return new UmpleSourceData().setFileNames("CharacterBinding.ump").setUmpleLines(18).setJavaLines(44).setLengths(1);}
    public UmpleSourceData CharacterBinding_charToEntry(){ return new UmpleSourceData().setFileNames("CharacterBinding.ump").setUmpleLines(36).setJavaLines(68).setLengths(1);}
    public UmpleSourceData CharacterBinding_entryToChar(){ return new UmpleSourceData().setFileNames("CharacterBinding.ump").setUmpleLines(27).setJavaLines(56).setLengths(1);}
    public UmpleSourceData CharacterBinding_objectToEntry(){ return new UmpleSourceData().setFileNames("CharacterBinding.ump").setUmpleLines(14).setJavaLines(39).setLengths(1);}
    public UmpleSourceData CharacterBinding_entryToObject(){ return new UmpleSourceData().setFileNames("CharacterBinding.ump").setUmpleLines(10).setJavaLines(34).setLengths(1);}
    public UmpleSourceData LongBinding_sizedOutput(){ return new UmpleSourceData().setFileNames("LongBinding.ump").setUmpleLines(43).setJavaLines(78).setLengths(1);}
    public UmpleSourceData LongBinding_getTupleOutput(){ return new UmpleSourceData().setFileNames("LongBinding.ump").setUmpleLines(18).setJavaLines(44).setLengths(1);}
    public UmpleSourceData LongBinding_longToEntry(){ return new UmpleSourceData().setFileNames("LongBinding.ump").setUmpleLines(36).setJavaLines(68).setLengths(1);}
    public UmpleSourceData LongBinding_entryToLong(){ return new UmpleSourceData().setFileNames("LongBinding.ump").setUmpleLines(27).setJavaLines(56).setLengths(1);}
    public UmpleSourceData LongBinding_objectToEntry(){ return new UmpleSourceData().setFileNames("LongBinding.ump").setUmpleLines(14).setJavaLines(39).setLengths(1);}
    public UmpleSourceData LongBinding_entryToObject(){ return new UmpleSourceData().setFileNames("LongBinding.ump").setUmpleLines(10).setJavaLines(34).setLengths(1);}
    public UmpleSourceData TupleOutput_TupleOutput(){ return new UmpleSourceData().setFileNames("TupleOutput.ump","TupleOutput.ump").setUmpleLines(17, 25).setJavaLines(41, 52).setLengths(1, 1);}
    public UmpleSourceData TupleBinding_getPrimitiveBinding(){ return new UmpleSourceData().setFileNames("TupleBinding.ump").setUmpleLines(54).setJavaLines(91).setLengths(1);}
    public UmpleSourceData TupleBinding_TupleBinding(){ return new UmpleSourceData().setFileNames("TupleBinding.ump").setUmpleLines(22).setJavaLines(49).setLengths(1);}
    public UmpleSourceData TupleBinding_objectToEntry(){ return new UmpleSourceData().setFileNames("TupleBinding.ump").setUmpleLines(29).setJavaLines(59).setLengths(3);}
    public UmpleSourceData TupleBinding_addPrimitive(){ return new UmpleSourceData().setFileNames("TupleBinding.ump").setUmpleLines(14).setJavaLines(38).setLengths(2);}
    public UmpleSourceData TupleBinding_entryToObject(){ return new UmpleSourceData().setFileNames("TupleBinding.ump").setUmpleLines(25).setJavaLines(54).setLengths(1);}
    public UmpleSourceData ByteBinding_sizedOutput(){ return new UmpleSourceData().setFileNames("ByteBinding.ump").setUmpleLines(43).setJavaLines(78).setLengths(1);}
    public UmpleSourceData ByteBinding_getTupleOutput(){ return new UmpleSourceData().setFileNames("ByteBinding.ump").setUmpleLines(18).setJavaLines(44).setLengths(1);}
    public UmpleSourceData ByteBinding_entryToByte(){ return new UmpleSourceData().setFileNames("ByteBinding.ump").setUmpleLines(27).setJavaLines(56).setLengths(1);}
    public UmpleSourceData ByteBinding_objectToEntry(){ return new UmpleSourceData().setFileNames("ByteBinding.ump").setUmpleLines(14).setJavaLines(39).setLengths(1);}
    public UmpleSourceData ByteBinding_entryToObject(){ return new UmpleSourceData().setFileNames("ByteBinding.ump").setUmpleLines(10).setJavaLines(34).setLengths(1);}
    public UmpleSourceData ByteBinding_byteToEntry(){ return new UmpleSourceData().setFileNames("ByteBinding.ump").setUmpleLines(36).setJavaLines(68).setLengths(1);}
    public UmpleSourceData TupleInputBinding_TupleInputBinding(){ return new UmpleSourceData().setFileNames("TupleInputBinding.ump").setUmpleLines(12).setJavaLines(37).setLengths(1);}
    public UmpleSourceData TupleInputBinding_objectToEntry(){ return new UmpleSourceData().setFileNames("TupleInputBinding.ump").setUmpleLines(19).setJavaLines(47).setLengths(1);}
    public UmpleSourceData TupleInputBinding_entryToObject(){ return new UmpleSourceData().setFileNames("TupleInputBinding.ump").setUmpleLines(15).setJavaLines(42).setLengths(1);}
    public UmpleSourceData IntegerBinding_sizedOutput(){ return new UmpleSourceData().setFileNames("IntegerBinding.ump").setUmpleLines(43).setJavaLines(78).setLengths(1);}
    public UmpleSourceData IntegerBinding_getTupleOutput(){ return new UmpleSourceData().setFileNames("IntegerBinding.ump").setUmpleLines(18).setJavaLines(44).setLengths(1);}
    public UmpleSourceData IntegerBinding_objectToEntry(){ return new UmpleSourceData().setFileNames("IntegerBinding.ump").setUmpleLines(14).setJavaLines(39).setLengths(1);}
    public UmpleSourceData IntegerBinding_entryToObject(){ return new UmpleSourceData().setFileNames("IntegerBinding.ump").setUmpleLines(10).setJavaLines(34).setLengths(1);}
    public UmpleSourceData IntegerBinding_intToEntry(){ return new UmpleSourceData().setFileNames("IntegerBinding.ump").setUmpleLines(36).setJavaLines(68).setLengths(1);}
    public UmpleSourceData IntegerBinding_entryToInt(){ return new UmpleSourceData().setFileNames("IntegerBinding.ump").setUmpleLines(27).setJavaLines(56).setLengths(1);}
    public UmpleSourceData TupleMarshalledBinding_TupleMarshalledBinding(){ return new UmpleSourceData().setFileNames("TupleMarshalledBinding.ump").setUmpleLines(14).setJavaLines(40).setLengths(4);}
    public UmpleSourceData TupleMarshalledBinding_objectToEntry(){ return new UmpleSourceData().setFileNames("TupleMarshalledBinding.ump").setUmpleLines(33).setJavaLines(61).setLengths(2);}
    public UmpleSourceData TupleMarshalledBinding_entryToObject(){ return new UmpleSourceData().setFileNames("TupleMarshalledBinding.ump").setUmpleLines(21).setJavaLines(48).setLengths(9);}
    public UmpleSourceData TupleTupleKeyCreator_TupleTupleKeyCreator(){ return new UmpleSourceData().setFileNames("TupleTupleKeyCreator.ump").setUmpleLines(16).setJavaLines(44).setLengths(1);}
    public UmpleSourceData TupleTupleKeyCreator_createSecondaryKey(){ return new UmpleSourceData().setFileNames("TupleTupleKeyCreator.ump").setUmpleLines(20).setJavaLines(49).setLengths(9);}
    public UmpleSourceData TupleTupleKeyCreator_nullifyForeignKey(){ return new UmpleSourceData().setFileNames("TupleTupleKeyCreator.ump","TupleTupleKeyCreator.ump").setUmpleLines(32, 58).setJavaLines(62, 92).setLengths(7, 1);}
    public UmpleSourceData ShortBinding_sizedOutput(){ return new UmpleSourceData().setFileNames("ShortBinding.ump").setUmpleLines(43).setJavaLines(78).setLengths(1);}
    public UmpleSourceData ShortBinding_getTupleOutput(){ return new UmpleSourceData().setFileNames("ShortBinding.ump").setUmpleLines(18).setJavaLines(44).setLengths(1);}
    public UmpleSourceData ShortBinding_objectToEntry(){ return new UmpleSourceData().setFileNames("ShortBinding.ump").setUmpleLines(14).setJavaLines(39).setLengths(1);}
    public UmpleSourceData ShortBinding_entryToObject(){ return new UmpleSourceData().setFileNames("ShortBinding.ump").setUmpleLines(10).setJavaLines(34).setLengths(1);}
    public UmpleSourceData ShortBinding_entryToShort(){ return new UmpleSourceData().setFileNames("ShortBinding.ump").setUmpleLines(27).setJavaLines(56).setLengths(1);}
    public UmpleSourceData ShortBinding_shortToEntry(){ return new UmpleSourceData().setFileNames("ShortBinding.ump").setUmpleLines(36).setJavaLines(68).setLengths(1);}
    public UmpleSourceData StringBinding_sizedOutput(){ return new UmpleSourceData().setFileNames("StringBinding.ump").setUmpleLines(42).setJavaLines(79).setLengths(3);}
    public UmpleSourceData StringBinding_getTupleOutput(){ return new UmpleSourceData().setFileNames("StringBinding.ump").setUmpleLines(17).setJavaLines(45).setLengths(1);}
    public UmpleSourceData StringBinding_objectToEntry(){ return new UmpleSourceData().setFileNames("StringBinding.ump").setUmpleLines(13).setJavaLines(40).setLengths(1);}
    public UmpleSourceData StringBinding_entryToString(){ return new UmpleSourceData().setFileNames("StringBinding.ump").setUmpleLines(26).setJavaLines(57).setLengths(1);}
    public UmpleSourceData StringBinding_entryToObject(){ return new UmpleSourceData().setFileNames("StringBinding.ump").setUmpleLines(9).setJavaLines(35).setLengths(1);}
    public UmpleSourceData StringBinding_stringToEntry(){ return new UmpleSourceData().setFileNames("StringBinding.ump").setUmpleLines(35).setJavaLines(69).setLengths(1);}
    public UmpleSourceData TupleInput_TupleInput(){ return new UmpleSourceData().setFileNames("TupleInput.ump","TupleInput.ump","TupleInput.ump").setUmpleLines(13, 23, 31).setJavaLines(42, 55, 66).setLengths(1, 1, 1);}
    public UmpleSourceData TupleTupleMarshalledKeyCreator_TupleTupleMarshalledKeyCreator(){ return new UmpleSourceData().setFileNames("TupleTupleMarshalledKeyCreator.ump").setUmpleLines(16).setJavaLines(40).setLengths(2);}
    public UmpleSourceData TupleTupleMarshalledKeyCreator_createSecondaryKey(){ return new UmpleSourceData().setFileNames("TupleTupleMarshalledKeyCreator.ump").setUmpleLines(21).setJavaLines(46).setLengths(2);}
    public UmpleSourceData TupleTupleMarshalledKeyCreator_nullifyForeignKey(){ return new UmpleSourceData().setFileNames("TupleTupleMarshalledKeyCreator.ump").setUmpleLines(26).setJavaLines(52).setLengths(7);}
    public UmpleSourceData TupleTupleMarshalledBinding_objectToData(){ return new UmpleSourceData().setFileNames("TupleTupleMarshalledBinding.ump").setUmpleLines(48).setJavaLines(77).setLengths(2);}
    public UmpleSourceData TupleTupleMarshalledBinding_objectToKey(){ return new UmpleSourceData().setFileNames("TupleTupleMarshalledBinding.ump").setUmpleLines(43).setJavaLines(71).setLengths(2);}
    public UmpleSourceData TupleTupleMarshalledBinding_TupleTupleMarshalledBinding(){ return new UmpleSourceData().setFileNames("TupleTupleMarshalledBinding.ump").setUmpleLines(14).setJavaLines(40).setLengths(7);}
    public UmpleSourceData TupleTupleMarshalledBinding_entryToObject(){ return new UmpleSourceData().setFileNames("TupleTupleMarshalledBinding.ump").setUmpleLines(24).setJavaLines(51).setLengths(16);}
    public UmpleSourceData BooleanBinding_sizedOutput(){ return new UmpleSourceData().setFileNames("BooleanBinding.ump").setUmpleLines(43).setJavaLines(78).setLengths(1);}
    public UmpleSourceData BooleanBinding_getTupleOutput(){ return new UmpleSourceData().setFileNames("BooleanBinding.ump").setUmpleLines(18).setJavaLines(44).setLengths(1);}
    public UmpleSourceData BooleanBinding_booleanToEntry(){ return new UmpleSourceData().setFileNames("BooleanBinding.ump").setUmpleLines(36).setJavaLines(68).setLengths(1);}
    public UmpleSourceData BooleanBinding_objectToEntry(){ return new UmpleSourceData().setFileNames("BooleanBinding.ump").setUmpleLines(14).setJavaLines(39).setLengths(1);}
    public UmpleSourceData BooleanBinding_entryToObject(){ return new UmpleSourceData().setFileNames("BooleanBinding.ump").setUmpleLines(10).setJavaLines(34).setLengths(1);}
    public UmpleSourceData BooleanBinding_entryToBoolean(){ return new UmpleSourceData().setFileNames("BooleanBinding.ump").setUmpleLines(27).setJavaLines(56).setLengths(1);}
    public UmpleSourceData TupleBase_getTupleOutput(){ return new UmpleSourceData().setFileNames("TupleBase.ump").setUmpleLines(39).setJavaLines(71).setLengths(6);}
    public UmpleSourceData TupleBase_outputToEntry(){ return new UmpleSourceData().setFileNames("TupleBase.ump").setUmpleLines(53).setJavaLines(88).setLengths(1);}
    public UmpleSourceData TupleBase_setTupleBufferSize(){ return new UmpleSourceData().setFileNames("TupleBase.ump").setUmpleLines(20).setJavaLines(46).setLengths(1);}
    public UmpleSourceData TupleBase_TupleBase(){ return new UmpleSourceData().setFileNames("TupleBase.ump").setUmpleLines(12).setJavaLines(35).setLengths(1);}
    public UmpleSourceData TupleBase_newOutput(){ return new UmpleSourceData().setFileNames("TupleBase.ump","TupleBase.ump").setUmpleLines(80, 89).setJavaLines(124, 136).setLengths(1, 1);}
    public UmpleSourceData TupleBase_getTupleBufferSize(){ return new UmpleSourceData().setFileNames("TupleBase.ump").setUmpleLines(29).setJavaLines(58).setLengths(1);}
    public UmpleSourceData TupleBase_inputToEntry(){ return new UmpleSourceData().setFileNames("TupleBase.ump").setUmpleLines(62).setJavaLines(100).setLengths(1);}
    public UmpleSourceData TupleBase_entryToInput(){ return new UmpleSourceData().setFileNames("TupleBase.ump").setUmpleLines(71).setJavaLines(112).setLengths(1);}
    public UmpleSourceData TupleTupleBinding_TupleTupleBinding(){ return new UmpleSourceData().setFileNames("TupleTupleBinding.ump").setUmpleLines(13).setJavaLines(41).setLengths(1);}
    public UmpleSourceData TupleTupleBinding_objectToData(){ return new UmpleSourceData().setFileNames("TupleTupleBinding.ump").setUmpleLines(26).setJavaLines(58).setLengths(3);}
    public UmpleSourceData TupleTupleBinding_objectToKey(){ return new UmpleSourceData().setFileNames("TupleTupleBinding.ump").setUmpleLines(20).setJavaLines(51).setLengths(3);}
    public UmpleSourceData TupleTupleBinding_entryToObject(){ return new UmpleSourceData().setFileNames("TupleTupleBinding.ump").setUmpleLines(16).setJavaLines(46).setLengths(1);}
    public UmpleSourceData TraceLogHandler_flush(){ return new UmpleSourceData().setFileNames("LoggingDbLogHandler_TraceLogHandler.ump").setUmpleLines(25).setJavaLines(47).setLengths(1);}
    public UmpleSourceData TraceLogHandler_TraceLogHandler(){ return new UmpleSourceData().setFileNames("LoggingDbLogHandler_TraceLogHandler.ump").setUmpleLines(18).setJavaLines(37).setLengths(1);}
    public UmpleSourceData TraceLogHandler_publish(){ return new UmpleSourceData().setFileNames("LoggingDbLogHandler_TraceLogHandler.ump").setUmpleLines(28).setJavaLines(52).setLengths(9);}
    public UmpleSourceData TraceLogHandler_close(){ return new UmpleSourceData().setFileNames("LoggingDbLogHandler_TraceLogHandler.ump").setUmpleLines(22).setJavaLines(42).setLengths(1);}
    public UmpleSourceData FileCache_add(){ return new UmpleSourceData().setFileNames("FileHandleCache_FileCache.ump").setUmpleLines(32).setJavaLines(51).setLengths(36);}
    public UmpleSourceData FileCache_get(){ return new UmpleSourceData().setFileNames("FileHandleCache_FileCache.ump").setUmpleLines(27).setJavaLines(46).setLengths(1);}
    public UmpleSourceData FileCache_clear(){ return new UmpleSourceData().setFileNames("FileHandleCache_FileCache.ump").setUmpleLines(87).setJavaLines(124).setLengths(24);}
    public UmpleSourceData FileCache_getCacheKeys(){ return new UmpleSourceData().setFileNames("FileHandleCache_FileCache.ump").setUmpleLines(102).setJavaLines(152).setLengths(1);}
    public UmpleSourceData FileCache_remove(){ return new UmpleSourceData().setFileNames("FileHandleCache_FileCache.ump").setUmpleLines(65).setJavaLines(96).setLengths(24);}
    public UmpleSourceData FileCache_FileCache(){ return new UmpleSourceData().setFileNames("FileHandleCache_FileCache.ump").setUmpleLines(21).setJavaLines(39).setLengths(3);}
    public UmpleSourceData DbCacheSize_insertRecords_DbCacheSize_insertRecords(){ return new UmpleSourceData().setFileNames("MemoryBudget_DbCacheSize_inner.ump").setUmpleLines(5).setJavaLines(29).setLengths(7);}
    public UmpleSourceData DbCacheSize_insertRecords_execute(){ return new UmpleSourceData().setFileNames("MemoryBudget_DbCacheSize_inner.ump").setUmpleLines(14).setJavaLines(40).setLengths(45);}
    public UmpleSourceData DbCacheSize_printStats(){ return new UmpleSourceData().setFileNames("Derivative_Statistics_MemoryBudget_DbCacheSize.ump").setUmpleLines(7).setJavaLines(47).setLengths(8);}
    public UmpleSourceData Evictor_isRunnable(){ return new UmpleSourceData().setFileNames("Evictor_Evictor.ump").setUmpleLines(135).setJavaLines(180).setLengths(1);}
    public UmpleSourceData ScanIterator_next(){ return new UmpleSourceData().setFileNames("Evictor_Evictor_inner.ump").setUmpleLines(44).setJavaLines(60).setLengths(1);}
    public UmpleSourceData Evictor_evictBatch_Evictor_evictBatch(){ return new UmpleSourceData().setFileNames("Evictor_Evictor_inner.ump").setUmpleLines(52).setJavaLines(32).setLengths(3);}
    public UmpleSourceData ScanIterator_resetToMark(){ return new UmpleSourceData().setFileNames("Evictor_Evictor_inner.ump").setUmpleLines(38).setJavaLines(50).setLengths(1);}
    public UmpleSourceData Evictor_isRunnable_Evictor_isRunnable(){ return new UmpleSourceData().setFileNames("Evictor_Evictor_inner.ump").setUmpleLines(127).setJavaLines(31).setLengths(2);}
    public UmpleSourceData Evictor_evictIN(){ return new UmpleSourceData().setFileNames("Evictor_Evictor.ump").setUmpleLines(269).setJavaLines(342).setLengths(55);}
    public UmpleSourceData Evictor_doCriticalEviction(){ return new UmpleSourceData().setFileNames("Derivative_Evictor_CriticalEviction_Evictor.ump").setUmpleLines(8).setJavaLines(454).setLengths(1);}
    public UmpleSourceData ScanIterator_remove(){ return new UmpleSourceData().setFileNames("Evictor_Evictor_inner.ump").setUmpleLines(47).setJavaLines(65).setLengths(1);}
    public UmpleSourceData Evictor_evict(){ return new UmpleSourceData().setFileNames("Evictor_Evictor.ump").setUmpleLines(236).setJavaLines(296).setLengths(37);}
    public UmpleSourceData Evictor_addToQueue(){ return new UmpleSourceData().setFileNames("Derivative_Evictor_EvictorDaemon_Evictor.ump").setUmpleLines(9).setJavaLines(421).setLengths(1);}
    public UmpleSourceData Evictor_alert(){ return new UmpleSourceData().setFileNames("Evictor_Evictor.ump").setUmpleLines(91).setJavaLines(124).setLengths(3);}
    public UmpleSourceData Evictor_evictBatch(){ return new UmpleSourceData().setFileNames("Evictor_Evictor.ump").setUmpleLines(128).setJavaLines(170).setLengths(1);}
    public UmpleSourceData Evictor_normalizeLevel(){ return new UmpleSourceData().setFileNames("Evictor_Evictor.ump").setUmpleLines(224).setJavaLines(281).setLengths(5);}
    public UmpleSourceData Evictor_doEvict(){ return new UmpleSourceData().setFileNames("Evictor_Evictor.ump","Evictor_Evictor.ump").setUmpleLines(100, 107).setJavaLines(136, 146).setLengths(1, 14);}
    public UmpleSourceData Evictor_selectIN(){ return new UmpleSourceData().setFileNames("Evictor_Evictor.ump").setUmpleLines(142).setJavaLines(190).setLengths(82);}
    public UmpleSourceData Evictor_setRunnableHook(){ return new UmpleSourceData().setFileNames("Evictor_Evictor.ump").setUmpleLines(322).setJavaLines(411).setLengths(1);}
    public UmpleSourceData EvictProfile_getCandidates(){ return new UmpleSourceData().setFileNames("Evictor_Evictor_inner.ump").setUmpleLines(10).setJavaLines(34).setLengths(1);}
    public UmpleSourceData EvictProfile_count(){ return new UmpleSourceData().setFileNames("Evictor_Evictor_inner.ump").setUmpleLines(6).setJavaLines(28).setLengths(2);}
    public UmpleSourceData EvictProfile_clear(){ return new UmpleSourceData().setFileNames("Evictor_Evictor_inner.ump").setUmpleLines(13).setJavaLines(39).setLengths(2);}
    public UmpleSourceData ScanIterator_hasNext(){ return new UmpleSourceData().setFileNames("Evictor_Evictor_inner.ump").setUmpleLines(41).setJavaLines(55).setLengths(1);}
    public UmpleSourceData Evictor_Evictor(){ return new UmpleSourceData().setFileNames("Evictor_Evictor.ump").setUmpleLines(62).setJavaLines(95).setLengths(13);}
    public UmpleSourceData Evictor_evictBatch_execute(){ return new UmpleSourceData().setFileNames("Derivative_Statistics_Evictor_Evictor_inner.ump","Derivative_Latches_Evictor_Evictor_inner.ump","Derivative_Latches_Evictor_Evictor_inner.ump","Evictor_Evictor_inner.ump","Derivative_Evictor_MemoryBudget_Evictor_inner.ump","Evictor_Evictor_inner.ump","Derivative_Evictor_MemoryBudget_CriticalEviction_Evictor_inner.ump","Derivative_Evictor_CriticalEviction_Evictor_inner.ump").setUmpleLines(6, 6, 6, 57, 6, 131, 6, 8).setJavaLines(41, 68, 126, 39, 84, 37, 36, 34).setLengths(3, 3, 3, 76, 3, 46, 11, 1);}
    public UmpleSourceData Evictor_onWakeup(){ return new UmpleSourceData().setFileNames("Derivative_Evictor_EvictorDaemon_Evictor.ump").setUmpleLines(23).setJavaLines(441).setLengths(4);}
    public UmpleSourceData ScanIterator_ScanIterator(){ return new UmpleSourceData().setFileNames("Evictor_Evictor_inner.ump").setUmpleLines(22).setJavaLines(28).setLengths(2);}
    public UmpleSourceData Evictor_nDeadlockRetries(){ return new UmpleSourceData().setFileNames("Derivative_Evictor_EvictorDaemon_Evictor.ump").setUmpleLines(16).setJavaLines(431).setLengths(1);}
    public UmpleSourceData Evictor_loadStats(){ return new UmpleSourceData().setFileNames("Derivative_Statistics_Evictor_Evictor.ump").setUmpleLines(26).setJavaLines(464).setLengths(13);}
    public UmpleSourceData Evictor_getNextNode(){ return new UmpleSourceData().setFileNames("Evictor_Evictor.ump").setUmpleLines(318).setJavaLines(406).setLengths(1);}
    public UmpleSourceData ScanIterator_reset(){ return new UmpleSourceData().setFileNames("Evictor_Evictor_inner.ump").setUmpleLines(26).setJavaLines(34).setLengths(1);}
    public UmpleSourceData Evictor_toString(){ return new UmpleSourceData().setFileNames("Evictor_Evictor.ump").setUmpleLines(78).setJavaLines(112).setLengths(3);}
    public UmpleSourceData ScanIterator_mark(){ return new UmpleSourceData().setFileNames("Evictor_Evictor_inner.ump").setUmpleLines(29).setJavaLines(39).setLengths(7);}
    public UmpleSourceData Evictor_doCriticalEviction_Evictor_doCriticalEviction(){ return new UmpleSourceData().setFileNames("Derivative_Evictor_CriticalEviction_Evictor_inner.ump").setUmpleLines(5).setJavaLines(29).setLengths(1);}
    public UmpleSourceData TruncateResult_getDatabase(){ return new UmpleSourceData().setFileNames("Truncate_TruncateResult.ump").setUmpleLines(15).setJavaLines(35).setLengths(1);}
    public UmpleSourceData TruncateResult_getRecordCount(){ return new UmpleSourceData().setFileNames("Truncate_TruncateResult.ump").setUmpleLines(19).setJavaLines(40).setLengths(1);}
    public UmpleSourceData TruncateResult_TruncateResult(){ return new UmpleSourceData().setFileNames("Truncate_TruncateResult.ump").setUmpleLines(10).setJavaLines(29).setLengths(2);}
    public UmpleSourceData INCompressor_removeCompressibleBinReference(){ return new UmpleSourceData().setFileNames("INCompressor_INCompressor.ump").setUmpleLines(160).setJavaLines(204).setLengths(13);}
    public UmpleSourceData INCompressor_isRunnable(){ return new UmpleSourceData().setFileNames("INCompressor_INCompressor.ump").setUmpleLines(337).setJavaLines(428).setLengths(1);}
    public UmpleSourceData INCompressor_accumulatePerRunCounters(){ return new UmpleSourceData().setFileNames("Derivative_Statistics_INCompressor_INCompressor.ump").setUmpleLines(75).setJavaLines(654).setLengths(5);}
    public UmpleSourceData INCompressor_resetPerRunCounters(){ return new UmpleSourceData().setFileNames("Derivative_Statistics_INCompressor_INCompressor.ump").setUmpleLines(67).setJavaLines(645).setLengths(5);}
    public UmpleSourceData INCompressor_addMultipleBinRefsToQueue(){ return new UmpleSourceData().setFileNames("INCompressor_INCompressor.ump").setUmpleLines(108).setJavaLines(144).setLengths(10);}
    public UmpleSourceData INCompressor_addToQueue(){ return new UmpleSourceData().setFileNames("INCompressor_INCompressor.ump").setUmpleLines(69).setJavaLines(95).setLengths(1);}
    public UmpleSourceData INCompressor_pruneBIN(){ return new UmpleSourceData().setFileNames("INCompressor_INCompressor.ump").setUmpleLines(299).setJavaLines(380).setLengths(26);}
    public UmpleSourceData INCompressor_addBinKeyToQueueAlreadyLatched(){ return new UmpleSourceData().setFileNames("INCompressor_INCompressor.ump").setUmpleLines(137).setJavaLines(179).setLengths(13);}
    public UmpleSourceData INCompressor_compressBin(){ return new UmpleSourceData().setFileNames("INCompressor_INCompressor.ump").setUmpleLines(259).setJavaLines(333).setLengths(37);}
    public UmpleSourceData INCompressor_checkForRelocatedSlots(){ return new UmpleSourceData().setFileNames("INCompressor_INCompressor.ump").setUmpleLines(320).setJavaLines(410).setLengths(14);}
    public UmpleSourceData INCompressor_getBinRefQueueSize(){ return new UmpleSourceData().setFileNames("INCompressor_INCompressor.ump").setUmpleLines(73).setJavaLines(100).setLengths(5);}
    public UmpleSourceData INCompressor_lazyCompress(){ return new UmpleSourceData().setFileNames("INCompressor_INCompressor.ump").setUmpleLines(398).setJavaLines(506).setLengths(38);}
    public UmpleSourceData INCompressor_addBinKeyToQueue(){ return new UmpleSourceData().setFileNames("INCompressor_INCompressor.ump").setUmpleLines(84).setJavaLines(114).setLengths(6);}
    public UmpleSourceData INCompressor_INCompressor(){ return new UmpleSourceData().setFileNames("INCompressor_INCompressor.ump").setUmpleLines(47).setJavaLines(73).setLengths(6);}
    public UmpleSourceData INCompressor_verifyCursors(){ return new UmpleSourceData().setFileNames("Derivative_Verifier_INCompressor_INCompressor.ump").setUmpleLines(5).setJavaLines(584).setLengths(20);}
    public UmpleSourceData INCompressor_addBinRefToQueueAlreadyLatched(){ return new UmpleSourceData().setFileNames("INCompressor_INCompressor.ump").setUmpleLines(124).setJavaLines(163).setLengths(7);}
    public UmpleSourceData INCompressor_addBinRefToQueue(){ return new UmpleSourceData().setFileNames("INCompressor_INCompressor.ump").setUmpleLines(96).setJavaLines(129).setLengths(6);}
    public UmpleSourceData INCompressor_onWakeup(){ return new UmpleSourceData().setFileNames("INCompressor_INCompressor.ump").setUmpleLines(183).setJavaLines(231).setLengths(5);}
    public UmpleSourceData INCompressor_nDeadlockRetries(){ return new UmpleSourceData().setFileNames("INCompressor_INCompressor.ump").setUmpleLines(179).setJavaLines(226).setLengths(1);}
    public UmpleSourceData INCompressor_doCompress(){ return new UmpleSourceData().setFileNames("INCompressor_INCompressor.ump").setUmpleLines(194).setJavaLines(245).setLengths(77);}
    public UmpleSourceData INCompressor_searchForBIN(){ return new UmpleSourceData().setFileNames("INCompressor_INCompressor.ump","INCompressor_INCompressor.ump").setUmpleLines(346, 350).setJavaLines(440, 445).setLengths(1, 52);}
    public UmpleSourceData INCompressor_loadStats(){ return new UmpleSourceData().setFileNames("Derivative_Statistics_INCompressor_INCompressor.ump").setUmpleLines(38).setJavaLines(613).setLengths(23);}
    public UmpleSourceData INCompressor_findDBAndBIN(){ return new UmpleSourceData().setFileNames("INCompressor_INCompressor.ump").setUmpleLines(428).setJavaLines(548).setLengths(32);}
    public UmpleSourceData INCompressor_exists(){ return new UmpleSourceData().setFileNames("INCompressor_INCompressor.ump").setUmpleLines(153).setJavaLines(196).setLengths(4);}
    public UmpleSourceData INCompressor_toString(){ return new UmpleSourceData().setFileNames("INCompressor_INCompressor.ump").setUmpleLines(56).setJavaLines(83).setLengths(3);}
    public UmpleSourceData GatherLSNs_getLsns(){ return new UmpleSourceData().setFileNames("Verifier_VerifyUtils_inner.ump").setUmpleLines(13).setJavaLines(34).setLengths(1);}
    public UmpleSourceData GatherLSNs_processLSN(){ return new UmpleSourceData().setFileNames("Verifier_VerifyUtils_inner.ump").setUmpleLines(10).setJavaLines(29).setLengths(1);}
    public UmpleSourceData VerifyUtils_checkLsns(){ return new UmpleSourceData().setFileNames("Verifier_VerifyUtils.ump").setUmpleLines(22).setJavaLines(46).setLengths(50);}
    public UmpleSourceData VerifyConfig_setAggressive(){ return new UmpleSourceData().setFileNames("Verifier_VerifyConfig.ump").setUmpleLines(42).setJavaLines(65).setLengths(1);}
    public UmpleSourceData VerifyConfig_setPropagateExceptions(){ return new UmpleSourceData().setFileNames("Verifier_VerifyConfig.ump").setUmpleLines(28).setJavaLines(45).setLengths(1);}
    public UmpleSourceData VerifyConfig_getPropagateExceptions(){ return new UmpleSourceData().setFileNames("Verifier_VerifyConfig.ump").setUmpleLines(35).setJavaLines(55).setLengths(1);}
    public UmpleSourceData VerifyConfig_getShowProgressInterval(){ return new UmpleSourceData().setFileNames("Verifier_VerifyConfig.ump").setUmpleLines(91).setJavaLines(135).setLengths(1);}
    public UmpleSourceData VerifyConfig_VerifyConfig(){ return new UmpleSourceData().setFileNames("Verifier_VerifyConfig.ump").setUmpleLines(22).setJavaLines(35).setLengths(1);}
    public UmpleSourceData VerifyConfig_setShowProgressStream(){ return new UmpleSourceData().setFileNames("Verifier_VerifyConfig.ump").setUmpleLines(70).setJavaLines(105).setLengths(1);}
    public UmpleSourceData VerifyConfig_setShowProgressInterval(){ return new UmpleSourceData().setFileNames("Verifier_VerifyConfig.ump").setUmpleLines(84).setJavaLines(125).setLengths(1);}
    public UmpleSourceData VerifyConfig_setPrintInfo(){ return new UmpleSourceData().setFileNames("Verifier_VerifyConfig.ump").setUmpleLines(56).setJavaLines(85).setLengths(1);}
    public UmpleSourceData VerifyConfig_getPrintInfo(){ return new UmpleSourceData().setFileNames("Verifier_VerifyConfig.ump").setUmpleLines(63).setJavaLines(95).setLengths(1);}
    public UmpleSourceData VerifyConfig_getShowProgressStream(){ return new UmpleSourceData().setFileNames("Verifier_VerifyConfig.ump").setUmpleLines(77).setJavaLines(115).setLengths(1);}
    public UmpleSourceData VerifyConfig_getAggressive(){ return new UmpleSourceData().setFileNames("Verifier_VerifyConfig.ump").setUmpleLines(49).setJavaLines(75).setLengths(1);}
    public UmpleSourceData VerifyConfig_toString(){ return new UmpleSourceData().setFileNames("Verifier_VerifyConfig.ump").setUmpleLines(99).setJavaLines(146).setLengths(3);}
    public UmpleSourceData DatabaseStats_DatabaseStats(){ return new UmpleSourceData().setFileNames("Statistics_DatabaseStats.ump").setUmpleLines(9).setJavaLines(31).setLengths(1);}
    public UmpleSourceData SequenceStats_getCacheSize(){ return new UmpleSourceData().setFileNames("Statistics_SequenceStats.ump").setUmpleLines(86).setJavaLines(116).setLengths(1);}
    public UmpleSourceData SequenceStats_getValue(){ return new UmpleSourceData().setFileNames("Statistics_SequenceStats.ump").setUmpleLines(58).setJavaLines(76).setLengths(1);}
    public UmpleSourceData SequenceStats_getLastValue(){ return new UmpleSourceData().setFileNames("Statistics_SequenceStats.ump").setUmpleLines(65).setJavaLines(86).setLengths(1);}
    public UmpleSourceData SequenceStats_getMin(){ return new UmpleSourceData().setFileNames("Statistics_SequenceStats.ump").setUmpleLines(72).setJavaLines(96).setLengths(1);}
    public UmpleSourceData SequenceStats_getMax(){ return new UmpleSourceData().setFileNames("Statistics_SequenceStats.ump").setUmpleLines(79).setJavaLines(106).setLengths(1);}
    public UmpleSourceData SequenceStats_getNGets(){ return new UmpleSourceData().setFileNames("Statistics_SequenceStats.ump").setUmpleLines(37).setJavaLines(46).setLengths(1);}
    public UmpleSourceData SequenceStats_toString(){ return new UmpleSourceData().setFileNames("Statistics_SequenceStats.ump").setUmpleLines(93).setJavaLines(126).setLengths(2);}
    public UmpleSourceData SequenceStats_getCurrent(){ return new UmpleSourceData().setFileNames("Statistics_SequenceStats.ump").setUmpleLines(51).setJavaLines(66).setLengths(1);}
    public UmpleSourceData SequenceStats_getNCachedGets(){ return new UmpleSourceData().setFileNames("Statistics_SequenceStats.ump").setUmpleLines(44).setJavaLines(56).setLengths(1);}
    public UmpleSourceData SequenceStats_SequenceStats(){ return new UmpleSourceData().setFileNames("Statistics_SequenceStats.ump").setUmpleLines(23).setJavaLines(29).setLengths(8);}
    public UmpleSourceData StatsAccumulator_getINsByLevel(){ return new UmpleSourceData().setFileNames("Statistics_StatsAccumulator.ump").setUmpleLines(144).setJavaLines(192).setLengths(1);}
    public UmpleSourceData StatsAccumulator_getDBINsByLevel(){ return new UmpleSourceData().setFileNames("Statistics_StatsAccumulator.ump").setUmpleLines(156).setJavaLines(207).setLengths(1);}
    public UmpleSourceData StatsAccumulator_getDupCountLNCount(){ return new UmpleSourceData().setFileNames("Statistics_StatsAccumulator.ump").setUmpleLines(164).setJavaLines(217).setLengths(1);}
    public UmpleSourceData StatsAccumulator_getBINsByLevel(){ return new UmpleSourceData().setFileNames("Statistics_StatsAccumulator.ump").setUmpleLines(148).setJavaLines(197).setLengths(1);}
    public UmpleSourceData StatsAccumulator_processDupCountLN(){ return new UmpleSourceData().setFileNames("Statistics_StatsAccumulator.ump").setUmpleLines(91).setJavaLines(131).setLengths(2);}
    public UmpleSourceData StatsAccumulator_getDeletedLNCount(){ return new UmpleSourceData().setFileNames("Statistics_StatsAccumulator.ump").setUmpleLines(168).setJavaLines(222).setLengths(1);}
    public UmpleSourceData StatsAccumulator_getDINNodeIdsSeen(){ return new UmpleSourceData().setFileNames("Statistics_StatsAccumulator.ump").setUmpleLines(136).setJavaLines(182).setLengths(1);}
    public UmpleSourceData StatsAccumulator_copyToStats(){ return new UmpleSourceData().setFileNames("Statistics_StatsAccumulator.ump").setUmpleLines(180).setJavaLines(237).setLengths(14);}
    public UmpleSourceData StatsAccumulator_processIN(){ return new UmpleSourceData().setFileNames("Statistics_StatsAccumulator.ump").setUmpleLines(63).setJavaLines(87).setLengths(7);}
    public UmpleSourceData StatsAccumulator_getINNodeIdsSeen(){ return new UmpleSourceData().setFileNames("Statistics_StatsAccumulator.ump").setUmpleLines(128).setJavaLines(172).setLengths(1);}
    public UmpleSourceData StatsAccumulator_getMainTreeMaxDepth(){ return new UmpleSourceData().setFileNames("Statistics_StatsAccumulator.ump").setUmpleLines(172).setJavaLines(227).setLengths(1);}
    public UmpleSourceData StatsAccumulator_incrementLNCount(){ return new UmpleSourceData().setFileNames("Statistics_StatsAccumulator.ump").setUmpleLines(114).setJavaLines(156).setLengths(7);}
    public UmpleSourceData StatsAccumulator_processBIN(){ return new UmpleSourceData().setFileNames("Statistics_StatsAccumulator.ump").setUmpleLines(70).setJavaLines(98).setLengths(7);}
    public UmpleSourceData StatsAccumulator_verifyNode(){ return new UmpleSourceData().setFileNames("Derivative_Statistics_Verifier_StatsAccumulator.ump").setUmpleLines(5).setJavaLines(255).setLengths(1);}
    public UmpleSourceData StatsAccumulator_processDIN(){ return new UmpleSourceData().setFileNames("Statistics_StatsAccumulator.ump").setUmpleLines(77).setJavaLines(109).setLengths(7);}
    public UmpleSourceData StatsAccumulator_StatsAccumulator(){ return new UmpleSourceData().setFileNames("Statistics_StatsAccumulator.ump").setUmpleLines(53).setJavaLines(76).setLengths(7);}
    public UmpleSourceData StatsAccumulator_incrementDeletedLNCount(){ return new UmpleSourceData().setFileNames("Statistics_StatsAccumulator.ump").setUmpleLines(124).setJavaLines(167).setLengths(1);}
    public UmpleSourceData StatsAccumulator_getDuplicateTreeMaxDepth(){ return new UmpleSourceData().setFileNames("Statistics_StatsAccumulator.ump").setUmpleLines(176).setJavaLines(232).setLengths(1);}
    public UmpleSourceData StatsAccumulator_tallyLevel(){ return new UmpleSourceData().setFileNames("Statistics_StatsAccumulator.ump").setUmpleLines(96).setJavaLines(137).setLengths(15);}
    public UmpleSourceData StatsAccumulator_processDBIN(){ return new UmpleSourceData().setFileNames("Statistics_StatsAccumulator.ump").setUmpleLines(84).setJavaLines(120).setLengths(7);}
    public UmpleSourceData StatsAccumulator_getDBINNodeIdsSeen(){ return new UmpleSourceData().setFileNames("Statistics_StatsAccumulator.ump").setUmpleLines(140).setJavaLines(187).setLengths(1);}
    public UmpleSourceData StatsAccumulator_getLNCount(){ return new UmpleSourceData().setFileNames("Statistics_StatsAccumulator.ump").setUmpleLines(160).setJavaLines(212).setLengths(1);}
    public UmpleSourceData StatsAccumulator_getBINNodeIdsSeen(){ return new UmpleSourceData().setFileNames("Statistics_StatsAccumulator.ump").setUmpleLines(132).setJavaLines(177).setLengths(1);}
    public UmpleSourceData StatsAccumulator_getDINsByLevel(){ return new UmpleSourceData().setFileNames("Statistics_StatsAccumulator.ump").setUmpleLines(152).setJavaLines(202).setLengths(1);}
    public UmpleSourceData StatsConfig_getShowProgressInterval(){ return new UmpleSourceData().setFileNames("Statistics_StatsConfig.ump").setUmpleLines(73).setJavaLines(115).setLengths(1);}
    public UmpleSourceData StatsConfig_setFast(){ return new UmpleSourceData().setFileNames("Statistics_StatsConfig.ump").setUmpleLines(24).setJavaLines(45).setLengths(1);}
    public UmpleSourceData StatsConfig_getClear(){ return new UmpleSourceData().setFileNames("Statistics_StatsConfig.ump").setUmpleLines(45).setJavaLines(75).setLengths(1);}
    public UmpleSourceData StatsConfig_StatsConfig(){ return new UmpleSourceData().setFileNames("Statistics_StatsConfig.ump").setUmpleLines(18).setJavaLines(35).setLengths(1);}
    public UmpleSourceData StatsConfig_setShowProgressStream(){ return new UmpleSourceData().setFileNames("Statistics_StatsConfig.ump").setUmpleLines(52).setJavaLines(85).setLengths(1);}
    public UmpleSourceData StatsConfig_setShowProgressInterval(){ return new UmpleSourceData().setFileNames("Statistics_StatsConfig.ump").setUmpleLines(66).setJavaLines(105).setLengths(1);}
    public UmpleSourceData StatsConfig_getShowProgressStream(){ return new UmpleSourceData().setFileNames("Statistics_StatsConfig.ump").setUmpleLines(59).setJavaLines(95).setLengths(1);}
    public UmpleSourceData StatsConfig_setClear(){ return new UmpleSourceData().setFileNames("Statistics_StatsConfig.ump").setUmpleLines(38).setJavaLines(65).setLengths(1);}
    public UmpleSourceData StatsConfig_getFast(){ return new UmpleSourceData().setFileNames("Statistics_StatsConfig.ump").setUmpleLines(31).setJavaLines(55).setLengths(1);}
    public UmpleSourceData TransactionStats_setNXACommits(){ return new UmpleSourceData().setFileNames("Statistics_TransactionStats.ump").setUmpleLines(194).setJavaLines(228).setLengths(1);}
    public UmpleSourceData Active_getName(){ return new UmpleSourceData().setFileNames("Statistics_TransactionStats_inner.ump").setUmpleLines(39).setJavaLines(57).setLengths(1);}
    public UmpleSourceData TransactionStats_setLastTxnId(){ return new UmpleSourceData().setFileNames("Statistics_TransactionStats.ump").setUmpleLines(152).setJavaLines(168).setLengths(1);}
    public UmpleSourceData TransactionStats_setActiveTxns(){ return new UmpleSourceData().setFileNames("Statistics_TransactionStats.ump").setUmpleLines(138).setJavaLines(148).setLengths(1);}
    public UmpleSourceData TransactionStats_setLastCheckpointTime(){ return new UmpleSourceData().setFileNames("Statistics_TransactionStats.ump").setUmpleLines(145).setJavaLines(158).setLengths(1);}
    public UmpleSourceData Active_getId(){ return new UmpleSourceData().setFileNames("Statistics_TransactionStats_inner.ump").setUmpleLines(25).setJavaLines(35).setLengths(1);}
    public UmpleSourceData TransactionStats_getNActive(){ return new UmpleSourceData().setFileNames("Statistics_TransactionStats.ump").setUmpleLines(110).setJavaLines(108).setLengths(1);}
    public UmpleSourceData TransactionStats_setNActive(){ return new UmpleSourceData().setFileNames("Statistics_TransactionStats.ump").setUmpleLines(173).setJavaLines(198).setLengths(1);}
    public UmpleSourceData TransactionStats_getNBegins(){ return new UmpleSourceData().setFileNames("Statistics_TransactionStats.ump").setUmpleLines(117).setJavaLines(118).setLengths(1);}
    public UmpleSourceData TransactionStats_getLastCheckpointTime(){ return new UmpleSourceData().setFileNames("Statistics_TransactionStats.ump").setUmpleLines(75).setJavaLines(58).setLengths(1);}
    public UmpleSourceData TransactionStats_getNXACommits(){ return new UmpleSourceData().setFileNames("Statistics_TransactionStats.ump").setUmpleLines(131).setJavaLines(138).setLengths(1);}
    public UmpleSourceData TransactionStats_getNXAPrepares(){ return new UmpleSourceData().setFileNames("Statistics_TransactionStats.ump").setUmpleLines(103).setJavaLines(98).setLengths(1);}
    public UmpleSourceData TransactionStats_setNCommits(){ return new UmpleSourceData().setFileNames("Statistics_TransactionStats.ump").setUmpleLines(187).setJavaLines(218).setLengths(1);}
    public UmpleSourceData TransactionStats_getNAborts(){ return new UmpleSourceData().setFileNames("Statistics_TransactionStats.ump").setUmpleLines(89).setJavaLines(78).setLengths(1);}
    public UmpleSourceData TransactionStats_getLastTxnId(){ return new UmpleSourceData().setFileNames("Statistics_TransactionStats.ump").setUmpleLines(82).setJavaLines(68).setLengths(1);}
    public UmpleSourceData TransactionStats_getActiveTxns(){ return new UmpleSourceData().setFileNames("Statistics_TransactionStats.ump").setUmpleLines(68).setJavaLines(48).setLengths(1);}
    public UmpleSourceData TransactionStats_getNXAAborts(){ return new UmpleSourceData().setFileNames("Statistics_TransactionStats.ump").setUmpleLines(96).setJavaLines(88).setLengths(1);}
    public UmpleSourceData TransactionStats_setNXAAborts(){ return new UmpleSourceData().setFileNames("Statistics_TransactionStats.ump").setUmpleLines(166).setJavaLines(188).setLengths(1);}
    public UmpleSourceData Active_Active(){ return new UmpleSourceData().setFileNames("Statistics_TransactionStats_inner.ump").setUmpleLines(45).setJavaLines(67).setLengths(3);}
    public UmpleSourceData TransactionStats_TransactionStats(){ return new UmpleSourceData().setFileNames("Statistics_TransactionStats.ump").setUmpleLines(62).setJavaLines(38).setLengths(1);}
    public UmpleSourceData TransactionStats_setNXAPrepares(){ return new UmpleSourceData().setFileNames("Statistics_TransactionStats.ump").setUmpleLines(201).setJavaLines(238).setLengths(1);}
    public UmpleSourceData TransactionStats_toString(){ return new UmpleSourceData().setFileNames("Statistics_TransactionStats.ump","Statistics_TransactionStats_inner.ump").setUmpleLines(208, 50).setJavaLines(248, 74).setLengths(18, 1);}
    public UmpleSourceData TransactionStats_setNBegins(){ return new UmpleSourceData().setFileNames("Statistics_TransactionStats.ump").setUmpleLines(180).setJavaLines(208).setLengths(1);}
    public UmpleSourceData TransactionStats_getNCommits(){ return new UmpleSourceData().setFileNames("Statistics_TransactionStats.ump").setUmpleLines(124).setJavaLines(128).setLengths(1);}
    public UmpleSourceData Active_getParentId(){ return new UmpleSourceData().setFileNames("Statistics_TransactionStats_inner.ump").setUmpleLines(32).setJavaLines(46).setLengths(1);}
    public UmpleSourceData TransactionStats_setNAborts(){ return new UmpleSourceData().setFileNames("Statistics_TransactionStats.ump").setUmpleLines(159).setJavaLines(178).setLengths(1);}
    public UmpleSourceData BtreeStats_getDuplicateInternalNodeCount(){ return new UmpleSourceData().setFileNames("Statistics_BtreeStats.ump").setUmpleLines(107).setJavaLines(138).setLengths(1);}
    public UmpleSourceData BtreeStats_getDBINsByLevel(){ return new UmpleSourceData().setFileNames("Statistics_BtreeStats.ump").setUmpleLines(205).setJavaLines(278).setLengths(1);}
    public UmpleSourceData BtreeStats_getBINsByLevel(){ return new UmpleSourceData().setFileNames("Statistics_BtreeStats.ump").setUmpleLines(177).setJavaLines(238).setLengths(1);}
    public UmpleSourceData BtreeStats_setDBINsByLevel(){ return new UmpleSourceData().setFileNames("Statistics_BtreeStats.ump").setUmpleLines(212).setJavaLines(288).setLengths(1);}
    public UmpleSourceData BtreeStats_setMainTreeMaxDepth(){ return new UmpleSourceData().setFileNames("Statistics_BtreeStats.ump").setUmpleLines(142).setJavaLines(188).setLengths(1);}
    public UmpleSourceData BtreeStats_setDuplicateBottomInternalNodeCount(){ return new UmpleSourceData().setFileNames("Statistics_BtreeStats.ump").setUmpleLines(58).setJavaLines(68).setLengths(1);}
    public UmpleSourceData BtreeStats_setLeafNodeCount(){ return new UmpleSourceData().setFileNames("Statistics_BtreeStats.ump").setUmpleLines(128).setJavaLines(168).setLengths(1);}
    public UmpleSourceData BtreeStats_setBottomInternalNodeCount(){ return new UmpleSourceData().setFileNames("Statistics_BtreeStats.ump").setUmpleLines(44).setJavaLines(48).setLengths(1);}
    public UmpleSourceData BtreeStats_getDupCountLeafNodeCount(){ return new UmpleSourceData().setFileNames("Statistics_BtreeStats.ump").setUmpleLines(79).setJavaLines(98).setLengths(1);}
    public UmpleSourceData BtreeStats_getInternalNodeCount(){ return new UmpleSourceData().setFileNames("Statistics_BtreeStats.ump").setUmpleLines(93).setJavaLines(118).setLengths(1);}
    public UmpleSourceData BtreeStats_arrayToString(){ return new UmpleSourceData().setFileNames("Statistics_BtreeStats.ump").setUmpleLines(216).setJavaLines(293).setLengths(7);}
    public UmpleSourceData BtreeStats_setBINsByLevel(){ return new UmpleSourceData().setFileNames("Statistics_BtreeStats.ump").setUmpleLines(184).setJavaLines(248).setLengths(1);}
    public UmpleSourceData BtreeStats_getDuplicateTreeMaxDepth(){ return new UmpleSourceData().setFileNames("Statistics_BtreeStats.ump").setUmpleLines(149).setJavaLines(198).setLengths(1);}
    public UmpleSourceData BtreeStats_setDuplicateTreeMaxDepth(){ return new UmpleSourceData().setFileNames("Statistics_BtreeStats.ump").setUmpleLines(156).setJavaLines(208).setLengths(1);}
    public UmpleSourceData BtreeStats_getDINsByLevel(){ return new UmpleSourceData().setFileNames("Statistics_BtreeStats.ump").setUmpleLines(191).setJavaLines(258).setLengths(1);}
    public UmpleSourceData BtreeStats_getINsByLevel(){ return new UmpleSourceData().setFileNames("Statistics_BtreeStats.ump").setUmpleLines(163).setJavaLines(218).setLengths(1);}
    public UmpleSourceData BtreeStats_setDeletedLeafNodeCount(){ return new UmpleSourceData().setFileNames("Statistics_BtreeStats.ump").setUmpleLines(72).setJavaLines(88).setLengths(1);}
    public UmpleSourceData BtreeStats_getDeletedLeafNodeCount(){ return new UmpleSourceData().setFileNames("Statistics_BtreeStats.ump").setUmpleLines(65).setJavaLines(78).setLengths(1);}
    public UmpleSourceData BtreeStats_setInternalNodeCount(){ return new UmpleSourceData().setFileNames("Statistics_BtreeStats.ump").setUmpleLines(100).setJavaLines(128).setLengths(1);}
    public UmpleSourceData BtreeStats_getLeafNodeCount(){ return new UmpleSourceData().setFileNames("Statistics_BtreeStats.ump").setUmpleLines(121).setJavaLines(158).setLengths(1);}
    public UmpleSourceData BtreeStats_setDupCountLeafNodeCount(){ return new UmpleSourceData().setFileNames("Statistics_BtreeStats.ump").setUmpleLines(86).setJavaLines(108).setLengths(1);}
    public UmpleSourceData BtreeStats_getMainTreeMaxDepth(){ return new UmpleSourceData().setFileNames("Statistics_BtreeStats.ump").setUmpleLines(135).setJavaLines(178).setLengths(1);}
    public UmpleSourceData BtreeStats_getBottomInternalNodeCount(){ return new UmpleSourceData().setFileNames("Statistics_BtreeStats.ump").setUmpleLines(37).setJavaLines(38).setLengths(1);}
    public UmpleSourceData BtreeStats_setINsByLevel(){ return new UmpleSourceData().setFileNames("Statistics_BtreeStats.ump").setUmpleLines(170).setJavaLines(228).setLengths(1);}
    public UmpleSourceData BtreeStats_setDINsByLevel(){ return new UmpleSourceData().setFileNames("Statistics_BtreeStats.ump").setUmpleLines(198).setJavaLines(268).setLengths(1);}
    public UmpleSourceData BtreeStats_toString(){ return new UmpleSourceData().setFileNames("Statistics_BtreeStats.ump").setUmpleLines(229).setJavaLines(309).setLengths(27);}
    public UmpleSourceData BtreeStats_getDuplicateBottomInternalNodeCount(){ return new UmpleSourceData().setFileNames("Statistics_BtreeStats.ump").setUmpleLines(51).setJavaLines(58).setLengths(1);}
    public UmpleSourceData BtreeStats_setDuplicateInternalNodeCount(){ return new UmpleSourceData().setFileNames("Statistics_BtreeStats.ump").setUmpleLines(114).setJavaLines(148).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setNTempBufferWrites(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(928).setJavaLines(1030).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getNFSyncTimeouts(){ return new UmpleSourceData().setFileNames("Derivative_FSync_Statistics_EnvironmentStats.ump").setUmpleLines(28).setJavaLines(1145).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getNLNsMigrated(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(459).setJavaLines(360).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setNNodesSelected(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(900).setJavaLines(990).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getLastCheckpointStart(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(368).setJavaLines(230).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getNEvictPasses(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(382).setJavaLines(250).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getNLNsMarked(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(466).setJavaLines(370).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getNTempBufferWrites(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(599).setJavaLines(560).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getNINsObsolete(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(403).setJavaLines(280).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getLastCheckpointEnd(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(361).setJavaLines(220).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setNLNsMarked(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(823).setJavaLines(880).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setNRepeatIteratorReads(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(935).setJavaLines(1040).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setNNotResident(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(627).setJavaLines(600).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setDbClosedBins(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(662).setJavaLines(650).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setNNodesExplicitlyEvicted(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(872).setJavaLines(950).setLengths(1);}
    public UmpleSourceData EnvironmentStats_reset(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(230).setJavaLines(48).setLengths(53);}
    public UmpleSourceData EnvironmentStats_getNNodesScanned(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(543).setJavaLines(480).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setNINsMigrated(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(781).setJavaLines(820).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setNCleanerRuns(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(697).setJavaLines(700).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setCacheDataBytes(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(620).setJavaLines(590).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getNLNsObsolete(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(431).setJavaLines(320).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setNMarkedLNsProcessed(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(844).setJavaLines(910).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setNLNsMigrated(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(816).setJavaLines(870).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getInCompQueueSize(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(305).setJavaLines(140).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getNCleanerDeletions(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(347).setJavaLines(200).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getNCleanerEntriesRead(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(375).setJavaLines(240).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setNINsDead(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(774).setJavaLines(810).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getNNodesSelected(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(550).setJavaLines(490).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setNPendingLNsLocked(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(865).setJavaLines(940).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setNonEmptyBins(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(907).setJavaLines(1000).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getNCleanerRuns(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(340).setJavaLines(190).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setNPendingLNsProcessed(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(837).setJavaLines(900).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setInCompQueueSize(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(669).setJavaLines(660).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getDbClosedBins(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(298).setJavaLines(130).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getNPendingLNsLocked(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(508).setJavaLines(430).setLengths(1);}
    public UmpleSourceData EnvironmentStats_EnvironmentStats(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(223).setJavaLines(38).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setNFullBINFlush(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(753).setJavaLines(780).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getNINsDead(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(417).setJavaLines(300).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setLastCheckpointId(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(676).setJavaLines(670).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getLastCheckpointId(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(312).setJavaLines(150).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setNLNQueueHits(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(830).setJavaLines(890).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setNDeltaINFlush(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(711).setJavaLines(720).setLengths(1);}
    public UmpleSourceData EnvironmentStats_toString(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(949).setJavaLines(1060).setLengths(56);}
    public UmpleSourceData EnvironmentStats_setNLNsObsolete(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(788).setJavaLines(830).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setNNodesScanned(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(893).setJavaLines(980).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getNNodesExplicitlyEvicted(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(522).setJavaLines(450).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setNRepeatFaultReads(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(921).setJavaLines(1020).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getNNotResident(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(571).setJavaLines(520).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getNINsMigrated(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(424).setJavaLines(310).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getNFullINFlush(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(389).setJavaLines(260).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setSplitBins(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(942).setJavaLines(1050).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setNCleanerDeletions(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(704).setJavaLines(710).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getNLNQueueHits(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(473).setJavaLines(380).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getNBINsStripped(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(529).setJavaLines(460).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setNClusterLNsProcessed(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(858).setJavaLines(930).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getNCheckpoints(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(326).setJavaLines(170).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getNonEmptyBins(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(578).setJavaLines(530).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setNLNsLocked(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(809).setJavaLines(860).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setCursorsBins(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(655).setJavaLines(640).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getNToBeCleanedLNsProcessed(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(494).setJavaLines(410).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getSplitBins(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(613).setJavaLines(580).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setNCacheMiss(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(634).setJavaLines(610).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getNFSyncs(){ return new UmpleSourceData().setFileNames("Derivative_FSync_Statistics_EnvironmentStats.ump").setUmpleLines(14).setJavaLines(1125).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setNLNsDead(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(802).setJavaLines(850).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getNRepeatFaultReads(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(592).setJavaLines(550).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setLastCheckpointEnd(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(718).setJavaLines(730).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getNMarkedLNsProcessed(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(487).setJavaLines(400).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setNINsCleaned(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(767).setJavaLines(800).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setProcessedBins(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(914).setJavaLines(1010).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getNClusterLNsProcessed(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(501).setJavaLines(420).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getNFullBINFlush(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(396).setJavaLines(270).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getCacheDataBytes(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(564).setJavaLines(510).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getNLNsLocked(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(452).setJavaLines(350).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setBufferBytes(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(648).setJavaLines(630).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setNEvictPasses(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(739).setJavaLines(760).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getNINsCleaned(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(410).setJavaLines(290).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getNDeltaINFlush(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(354).setJavaLines(210).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getRequiredEvictBytes(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(536).setJavaLines(470).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setRequiredEvictBytes(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(879).setJavaLines(960).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setNBINsStripped(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(886).setJavaLines(970).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setLastCheckpointStart(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(725).setJavaLines(740).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setNFSyncs(){ return new UmpleSourceData().setFileNames("Derivative_FSync_Statistics_EnvironmentStats.ump").setUmpleLines(35).setJavaLines(1155).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getCursorsBins(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(291).setJavaLines(120).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getNLogBuffers(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(515).setJavaLines(440).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setCleanerBacklog(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(690).setJavaLines(690).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getNRepeatIteratorReads(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(606).setJavaLines(570).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getNLNsCleaned(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(438).setJavaLines(330).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setNFSyncTimeouts(){ return new UmpleSourceData().setFileNames("Derivative_FSync_Statistics_EnvironmentStats.ump").setUmpleLines(49).setJavaLines(1175).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getCleanerBacklog(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(333).setJavaLines(180).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setNCheckpoints(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(683).setJavaLines(680).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getNPendingLNsProcessed(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(480).setJavaLines(390).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getProcessedBins(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(585).setJavaLines(540).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setNINsObsolete(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(760).setJavaLines(790).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setNFullINFlush(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(746).setJavaLines(770).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getCacheTotalBytes(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(557).setJavaLines(500).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getNFSyncRequests(){ return new UmpleSourceData().setFileNames("Derivative_FSync_Statistics_EnvironmentStats.ump").setUmpleLines(21).setJavaLines(1135).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setNCleanerEntriesRead(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(732).setJavaLines(750).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setNToBeCleanedLNsProcessed(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(851).setJavaLines(920).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setNFSyncRequests(){ return new UmpleSourceData().setFileNames("Derivative_FSync_Statistics_EnvironmentStats.ump").setUmpleLines(42).setJavaLines(1165).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getNCacheMiss(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(319).setJavaLines(160).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getNLNsDead(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(445).setJavaLines(340).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setNLogBuffers(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(641).setJavaLines(620).setLengths(1);}
    public UmpleSourceData EnvironmentStats_setNLNsCleaned(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(795).setJavaLines(840).setLengths(1);}
    public UmpleSourceData EnvironmentStats_getBufferBytes(){ return new UmpleSourceData().setFileNames("Statistics_EnvironmentStats.ump").setUmpleLines(284).setJavaLines(110).setLengths(1);}
    public UmpleSourceData ChecksumValidator_reset(){ return new UmpleSourceData().setFileNames("Checksum_ChecksumValidator.ump").setUmpleLines(18).setJavaLines(39).setLengths(1);}
    public UmpleSourceData ChecksumValidator_update(){ return new UmpleSourceData().setFileNames("Checksum_ChecksumValidator.ump").setUmpleLines(28).setJavaLines(51).setLengths(16);}
    public UmpleSourceData ChecksumValidator_ChecksumValidator(){ return new UmpleSourceData().setFileNames("Checksum_ChecksumValidator.ump").setUmpleLines(14).setJavaLines(34).setLengths(1);}
    public UmpleSourceData ChecksumValidator_validate(){ return new UmpleSourceData().setFileNames("Checksum_ChecksumValidator.ump","Checksum_ChecksumValidator.ump").setUmpleLines(47, 55).setJavaLines(71, 79).setLengths(4, 6);}
    public UmpleSourceData LatchedLockManager_nWaiters(){ return new UmpleSourceData().setFileNames("Latches_LatchedLockManager.ump").setUmpleLines(156).setJavaLines(208).setLengths(8);}
    public UmpleSourceData LatchedLockManager_releaseAndFindNotifyTargets(){ return new UmpleSourceData().setFileNames("Latches_LatchedLockManager.ump").setUmpleLines(54).setJavaLines(85).setLengths(12);}
    public UmpleSourceData LatchedLockManager_isWaiter(){ return new UmpleSourceData().setFileNames("Latches_LatchedLockManager.ump").setUmpleLines(142).setJavaLines(191).setLengths(8);}
    public UmpleSourceData LatchedLockManager_attemptLock(){ return new UmpleSourceData().setFileNames("Latches_LatchedLockManager.ump").setUmpleLines(22).setJavaLines(50).setLengths(8);}
    public UmpleSourceData LatchedLockManager_transferMultiple(){ return new UmpleSourceData().setFileNames("Latches_LatchedLockManager.ump").setUmpleLines(86).setJavaLines(123).setLengths(8);}
    public UmpleSourceData LatchedLockManager_dumpLockTable(){ return new UmpleSourceData().setFileNames("Latches_LatchedLockManager.ump").setUmpleLines(213).setJavaLines(276).setLengths(8);}
    public UmpleSourceData LatchedLockManager_makeTimeoutMsg(){ return new UmpleSourceData().setFileNames("Latches_LatchedLockManager.ump").setUmpleLines(38).setJavaLines(67).setLengths(9);}
    public UmpleSourceData LatchedLockManager_nOwners(){ return new UmpleSourceData().setFileNames("Latches_LatchedLockManager.ump").setUmpleLines(170).setJavaLines(225).setLengths(8);}
    public UmpleSourceData LatchedLockManager_transfer(){ return new UmpleSourceData().setFileNames("Latches_LatchedLockManager.ump").setUmpleLines(72).setJavaLines(106).setLengths(8);}
    public UmpleSourceData LatchedLockManager_demote(){ return new UmpleSourceData().setFileNames("Latches_LatchedLockManager.ump").setUmpleLines(100).setJavaLines(140).setLengths(8);}
    public UmpleSourceData LatchedLockManager_isOwner(){ return new UmpleSourceData().setFileNames("Latches_LatchedLockManager.ump").setUmpleLines(128).setJavaLines(174).setLengths(8);}
    public UmpleSourceData LatchedLockManager_isLocked(){ return new UmpleSourceData().setFileNames("Latches_LatchedLockManager.ump").setUmpleLines(114).setJavaLines(157).setLengths(8);}
    public UmpleSourceData LatchedLockManager_validateOwnership(){ return new UmpleSourceData().setFileNames("Latches_LatchedLockManager.ump").setUmpleLines(199).setJavaLines(259).setLengths(8);}
    public UmpleSourceData LatchedLockManager_LatchedLockManager(){ return new UmpleSourceData().setFileNames("Latches_LatchedLockManager.ump").setUmpleLines(14).setJavaLines(40).setLengths(1);}
    public UmpleSourceData LatchedLockManager_getWriteOwnerLocker(){ return new UmpleSourceData().setFileNames("Latches_LatchedLockManager.ump").setUmpleLines(184).setJavaLines(242).setLengths(8);}
    public UmpleSourceData Java5SharedLatchImpl_setName(){ return new UmpleSourceData().setFileNames("Latches_Java5SharedLatchImpl.ump").setUmpleLines(22).setJavaLines(42).setLengths(1);}
    public UmpleSourceData Java5SharedLatchImpl_unNoteLatch(){ return new UmpleSourceData().setFileNames("Latches_Java5SharedLatchImpl.ump").setUmpleLines(101).setJavaLines(140).setLengths(1);}
    public UmpleSourceData Java5SharedLatchImpl_noteLatch(){ return new UmpleSourceData().setFileNames("Latches_Java5SharedLatchImpl.ump").setUmpleLines(94).setJavaLines(130).setLengths(1);}
    public UmpleSourceData Java5SharedLatchImpl_setNoteLatch(){ return new UmpleSourceData().setFileNames("Latches_Java5SharedLatchImpl.ump").setUmpleLines(29).setJavaLines(52).setLengths(1);}
    public UmpleSourceData Java5SharedLatchImpl_release(){ return new UmpleSourceData().setFileNames("Latches_Java5SharedLatchImpl.ump").setUmpleLines(77).setJavaLines(110).setLengths(11);}
    public UmpleSourceData Java5SharedLatchImpl_acquireExclusive(){ return new UmpleSourceData().setFileNames("Latches_Java5SharedLatchImpl.ump").setUmpleLines(37).setJavaLines(63).setLengths(9);}
    public UmpleSourceData Java5SharedLatchImpl_acquireShared(){ return new UmpleSourceData().setFileNames("Latches_Java5SharedLatchImpl.ump").setUmpleLines(65).setJavaLines(95).setLengths(6);}
    public UmpleSourceData Java5SharedLatchImpl_Java5SharedLatchImpl(){ return new UmpleSourceData().setFileNames("Latches_Java5SharedLatchImpl.ump").setUmpleLines(15).setJavaLines(32).setLengths(1);}
    public UmpleSourceData Java5SharedLatchImpl_acquireExclusiveNoWait(){ return new UmpleSourceData().setFileNames("Latches_Java5SharedLatchImpl.ump").setUmpleLines(49).setJavaLines(76).setLengths(10);}
    public UmpleSourceData LatchException_LatchException(){ return new UmpleSourceData().setFileNames("Latches_LatchException.ump","Latches_LatchException.ump").setUmpleLines(9, 13).setJavaLines(35, 40).setLengths(1, 1);}
    public UmpleSourceData LatchSupport_makeLatch(){ return new UmpleSourceData().setFileNames("Latches_LatchSupport.ump","Latches_LatchSupport.ump").setUmpleLines(19, 35).setJavaLines(35, 52).setLengths(13, 11);}
    public UmpleSourceData LatchSupport_makeSharedLatch(){ return new UmpleSourceData().setFileNames("Latches_LatchSupport.ump").setUmpleLines(49).setJavaLines(67).setLengths(13);}
    public UmpleSourceData LatchSupport_getJava5LatchClass(){ return new UmpleSourceData().setFileNames("Latches_LatchSupport.ump").setUmpleLines(15).setJavaLines(30).setLengths(1);}
    public UmpleSourceData LatchImpl_owner(){ return new UmpleSourceData().setFileNames("Latches_LatchImpl.ump").setUmpleLines(187).setJavaLines(244).setLengths(1);}
    public UmpleSourceData LatchImpl_nWaiters(){ return new UmpleSourceData().setFileNames("Latches_LatchImpl.ump").setUmpleLines(195).setJavaLines(255).setLengths(1);}
    public UmpleSourceData LatchImpl_unNoteLatch(){ return new UmpleSourceData().setFileNames("Latches_LatchImpl.ump").setUmpleLines(223).setJavaLines(295).setLengths(6);}
    public UmpleSourceData LatchImpl_noteLatch(){ return new UmpleSourceData().setFileNames("Latches_LatchImpl.ump").setUmpleLines(216).setJavaLines(285).setLengths(1);}
    public UmpleSourceData LatchImpl_acquireNoWait(){ return new UmpleSourceData().setFileNames("Latches_LatchImpl.ump").setUmpleLines(104).setJavaLines(131).setLengths(30);}
    public UmpleSourceData LatchImpl_LatchImpl(){ return new UmpleSourceData().setFileNames("Latches_LatchImpl.ump","Latches_LatchImpl.ump").setUmpleLines(25, 33).setJavaLines(41, 52).setLengths(2, 2);}
    public UmpleSourceData LatchImpl_release(){ return new UmpleSourceData().setFileNames("Latches_LatchImpl.ump").setUmpleLines(136).setJavaLines(181).setLengths(3);}
    public UmpleSourceData LatchImpl_getLatchStats(){ return new UmpleSourceData().setFileNames("Derivative_Latches_Statistics_LatchImpl.ump").setUmpleLines(10).setJavaLines(310).setLengths(6);}
    public UmpleSourceData LatchImpl_doRelease(){ return new UmpleSourceData().setFileNames("Latches_LatchImpl.ump").setUmpleLines(146).setJavaLines(194).setLengths(29);}
    public UmpleSourceData LatchImpl_acquire(){ return new UmpleSourceData().setFileNames("Latches_LatchImpl.ump").setUmpleLines(50).setJavaLines(65).setLengths(55);}
    public UmpleSourceData LatchWaiter_LatchWaiter(){ return new UmpleSourceData().setFileNames("Latches_LatchImpl_inner.ump").setUmpleLines(7).setJavaLines(61).setLengths(2);}
    public UmpleSourceData LatchImpl_isOwner(){ return new UmpleSourceData().setFileNames("Latches_LatchImpl.ump").setUmpleLines(179).setJavaLines(233).setLengths(1);}
    public UmpleSourceData LatchImpl_toString(){ return new UmpleSourceData().setFileNames("Latches_LatchImpl.ump","Latches_LatchImpl_inner.ump").setUmpleLines(202, 11).setJavaLines(265, 67).setLengths(1, 1);}
    public UmpleSourceData LatchImpl_releaseIfOwner(){ return new UmpleSourceData().setFileNames("Latches_LatchImpl.ump").setUmpleLines(128).setJavaLines(170).setLengths(1);}
    public UmpleSourceData LatchImpl_getNameString(){ return new UmpleSourceData().setFileNames("Latches_LatchImpl.ump").setUmpleLines(209).setJavaLines(275).setLengths(1);}
    public UmpleSourceData Java5LatchImpl_setName(){ return new UmpleSourceData().setFileNames("Latches_Java5LatchImpl.ump").setUmpleLines(21).setJavaLines(44).setLengths(1);}
    public UmpleSourceData Java5LatchImpl_owner(){ return new UmpleSourceData().setFileNames("Latches_Java5LatchImpl.ump").setUmpleLines(114).setJavaLines(180).setLengths(1);}
    public UmpleSourceData Java5LatchImpl_nWaiters(){ return new UmpleSourceData().setFileNames("Latches_Java5LatchImpl.ump").setUmpleLines(122).setJavaLines(191).setLengths(1);}
    public UmpleSourceData Java5LatchImpl_unNoteLatch(){ return new UmpleSourceData().setFileNames("Latches_Java5LatchImpl.ump").setUmpleLines(143).setJavaLines(221).setLengths(6);}
    public UmpleSourceData Java5LatchImpl_noteLatch(){ return new UmpleSourceData().setFileNames("Latches_Java5LatchImpl.ump").setUmpleLines(136).setJavaLines(211).setLengths(1);}
    public UmpleSourceData Java5LatchImpl_acquireNoWait(){ return new UmpleSourceData().setFileNames("Latches_Java5LatchImpl.ump").setUmpleLines(48).setJavaLines(87).setLengths(25);}
    public UmpleSourceData Java5LatchImpl_release(){ return new UmpleSourceData().setFileNames("Latches_Java5LatchImpl.ump").setUmpleLines(78).setJavaLines(132).setLengths(3);}
    public UmpleSourceData Java5LatchImpl_getLatchStats(){ return new UmpleSourceData().setFileNames("Derivative_Latches_Statistics_Java5LatchImpl.ump").setUmpleLines(10).setJavaLines(236).setLengths(6);}
    public UmpleSourceData Java5LatchImpl_doRelease(){ return new UmpleSourceData().setFileNames("Latches_Java5LatchImpl.ump").setUmpleLines(88).setJavaLines(145).setLengths(14);}
    public UmpleSourceData JEReentrantLock_getOwner(){ return new UmpleSourceData().setFileNames("Latches_Java5LatchImpl_inner.ump").setUmpleLines(11).setJavaLines(33).setLengths(1);}
    public UmpleSourceData Java5LatchImpl_acquire(){ return new UmpleSourceData().setFileNames("Latches_Java5LatchImpl.ump").setUmpleLines(29).setJavaLines(55).setLengths(21);}
    public UmpleSourceData JEReentrantLock_JEReentrantLock(){ return new UmpleSourceData().setFileNames("Latches_Java5LatchImpl_inner.ump").setUmpleLines(8).setJavaLines(28).setLengths(1);}
    public UmpleSourceData Java5LatchImpl_Java5LatchImpl(){ return new UmpleSourceData().setFileNames("Latches_Java5LatchImpl.ump").setUmpleLines(14).setJavaLines(34).setLengths(1);}
    public UmpleSourceData Java5LatchImpl_isOwner(){ return new UmpleSourceData().setFileNames("Latches_Java5LatchImpl.ump").setUmpleLines(106).setJavaLines(169).setLengths(1);}
    public UmpleSourceData Java5LatchImpl_toString(){ return new UmpleSourceData().setFileNames("Latches_Java5LatchImpl.ump").setUmpleLines(129).setJavaLines(201).setLengths(1);}
    public UmpleSourceData Java5LatchImpl_releaseIfOwner(){ return new UmpleSourceData().setFileNames("Latches_Java5LatchImpl.ump").setUmpleLines(70).setJavaLines(121).setLengths(1);}
    public UmpleSourceData LatchTable_unNoteLatch(){ return new UmpleSourceData().setFileNames("Latches_LatchTable.ump").setUmpleLines(40).setJavaLines(65).setLengths(7);}
    public UmpleSourceData LatchTable_noteLatch(){ return new UmpleSourceData().setFileNames("Latches_LatchTable.ump").setUmpleLines(25).setJavaLines(47).setLengths(8);}
    public UmpleSourceData LatchTable_latchesHeldToString(){ return new UmpleSourceData().setFileNames("Latches_LatchTable.ump").setUmpleLines(63).setJavaLines(92).setLengths(10);}
    public UmpleSourceData LatchTable_countLatchesHeld(){ return new UmpleSourceData().setFileNames("Latches_LatchTable.ump").setUmpleLines(53).setJavaLines(81).setLengths(7);}
    public UmpleSourceData LatchTable_clearNotes(){ return new UmpleSourceData().setFileNames("Latches_LatchTable.ump").setUmpleLines(76).setJavaLines(106).setLengths(1);}
    public UmpleSourceData LatchTable_LatchTable(){ return new UmpleSourceData().setFileNames("Latches_LatchTable.ump").setUmpleLines(17).setJavaLines(36).setLengths(2);}
    public UmpleSourceData LatchTable_toString(){ return new UmpleSourceData().setFileNames("Latches_LatchTable.ump").setUmpleLines(94).setJavaLines(130).setLengths(15);}
    public UmpleSourceData LatchTable_getNameString(){ return new UmpleSourceData().setFileNames("Latches_LatchTable.ump").setUmpleLines(83).setJavaLines(116).setLengths(5);}
    public UmpleSourceData LatchNotHeldException_LatchNotHeldException(){ return new UmpleSourceData().setFileNames("Latches_LatchNotHeldException.ump","Latches_LatchNotHeldException.ump").setUmpleLines(7, 11).setJavaLines(33, 38).setLengths(1, 1);}
    public UmpleSourceData SharedLatchImpl_unNoteLatch(){ return new UmpleSourceData().setFileNames("Latches_SharedLatchImpl.ump").setUmpleLines(195).setJavaLines(243).setLengths(1);}
    public UmpleSourceData SharedLatchImpl_noteLatch(){ return new UmpleSourceData().setFileNames("Latches_SharedLatchImpl.ump").setUmpleLines(188).setJavaLines(233).setLengths(1);}
    public UmpleSourceData SharedLatchImpl_isWriteLockedByCurrentThread(){ return new UmpleSourceData().setFileNames("Latches_SharedLatchImpl.ump").setUmpleLines(199).setJavaLines(248).setLengths(6);}
    public UmpleSourceData Owner_Owner(){ return new UmpleSourceData().setFileNames("Latches_SharedLatchImpl_inner.ump").setUmpleLines(10).setJavaLines(76).setLengths(2);}
    public UmpleSourceData SharedLatchImpl_SharedLatchImpl(){ return new UmpleSourceData().setFileNames("Latches_SharedLatchImpl.ump").setUmpleLines(24).setJavaLines(42).setLengths(2);}
    public UmpleSourceData SharedLatchImpl_release(){ return new UmpleSourceData().setFileNames("Latches_SharedLatchImpl.ump").setUmpleLines(130).setJavaLines(159).setLengths(21);}
    public UmpleSourceData SharedLatchImpl_acquireExclusive(){ return new UmpleSourceData().setFileNames("Latches_SharedLatchImpl.ump").setUmpleLines(48).setJavaLines(55).setLengths(34);}
    public UmpleSourceData SharedLatchImpl_acquireShared(){ return new UmpleSourceData().setFileNames("Latches_SharedLatchImpl.ump").setUmpleLines(103).setJavaLines(126).setLengths(24);}
    public UmpleSourceData SharedLatchImpl_firstWriter(){ return new UmpleSourceData().setFileNames("Latches_SharedLatchImpl.ump").setUmpleLines(167).setJavaLines(206).setLengths(8);}
    public UmpleSourceData SharedLatchImpl_acquireExclusiveNoWait(){ return new UmpleSourceData().setFileNames("Latches_SharedLatchImpl.ump").setUmpleLines(76).setJavaLines(93).setLengths(23);}
    public UmpleSourceData SharedLatchImpl_indexOf(){ return new UmpleSourceData().setFileNames("Latches_SharedLatchImpl.ump").setUmpleLines(153).setJavaLines(189).setLengths(8);}
    public UmpleSourceData SharedLatchImpl_getNameString(){ return new UmpleSourceData().setFileNames("Latches_SharedLatchImpl.ump").setUmpleLines(181).setJavaLines(223).setLengths(1);}
    public UmpleSourceData LatchedLogManager_flushInternal(){ return new UmpleSourceData().setFileNames("Latches_LatchedLogManager.ump").setUmpleLines(33).setJavaLines(61).setLengths(8);}
    public UmpleSourceData LatchedLogManager_countObsoleteNodes(){ return new UmpleSourceData().setFileNames("Latches_LatchedLogManager.ump").setUmpleLines(72).setJavaLines(109).setLengths(7);}
    public UmpleSourceData LatchedLogManager_LatchedLogManager(){ return new UmpleSourceData().setFileNames("Latches_LatchedLogManager.ump").setUmpleLines(17).setJavaLines(45).setLengths(1);}
    public UmpleSourceData LatchedLogManager_countObsoleteINs(){ return new UmpleSourceData().setFileNames("Latches_LatchedLogManager.ump").setUmpleLines(85).setJavaLines(125).setLengths(6);}
    public UmpleSourceData LatchedLogManager_countObsoleteNode(){ return new UmpleSourceData().setFileNames("Latches_LatchedLogManager.ump").setUmpleLines(59).setJavaLines(93).setLengths(7);}
    public UmpleSourceData LatchedLogManager_logItem(){ return new UmpleSourceData().setFileNames("Latches_LatchedLogManager.ump").setUmpleLines(23).setJavaLines(50).setLengths(7);}
    public UmpleSourceData LatchedLogManager_getUnflushableTrackedSummary(){ return new UmpleSourceData().setFileNames("Latches_LatchedLogManager.ump").setUmpleLines(47).setJavaLines(78).setLengths(6);}
    public UmpleSourceData LookAheadCache_add(){ return new UmpleSourceData().setFileNames("LookAHEADCache_LookAheadCache.ump").setUmpleLines(32).setJavaLines(54).setLengths(7);}
    public UmpleSourceData LookAheadCache_isEmpty(){ return new UmpleSourceData().setFileNames("LookAHEADCache_LookAheadCache.ump").setUmpleLines(20).setJavaLines(39).setLengths(1);}
    public UmpleSourceData LookAheadCache_isFull(){ return new UmpleSourceData().setFileNames("LookAHEADCache_LookAheadCache.ump").setUmpleLines(24).setJavaLines(44).setLengths(1);}
    public UmpleSourceData LookAheadCache_nextOffset(){ return new UmpleSourceData().setFileNames("LookAHEADCache_LookAheadCache.ump").setUmpleLines(28).setJavaLines(49).setLengths(1);}
    public UmpleSourceData LookAheadCache_LookAheadCache(){ return new UmpleSourceData().setFileNames("LookAHEADCache_LookAheadCache.ump").setUmpleLines(15).setJavaLines(33).setLengths(2);}
    public UmpleSourceData LookAheadCache_remove(){ return new UmpleSourceData().setFileNames("LookAHEADCache_LookAheadCache.ump").setUmpleLines(38).setJavaLines(65).setLengths(10);}
    public UmpleSourceData FSyncGroup_FSyncGroup(){ return new UmpleSourceData().setFileNames("FSync_FSyncManager_inner.ump").setUmpleLines(12).setJavaLines(28).setLengths(4);}
    public UmpleSourceData FSyncGroup_getLeader(){ return new UmpleSourceData().setFileNames("FSync_FSyncManager_inner.ump").setUmpleLines(18).setJavaLines(36).setLengths(12);}
    public UmpleSourceData FSyncManager_FSyncManager(){ return new UmpleSourceData().setFileNames("FSync_FSyncManager.ump").setUmpleLines(19).setJavaLines(41).setLengths(5);}
    public UmpleSourceData FSyncGroup_waitForFsync(){ return new UmpleSourceData().setFileNames("FSync_FSyncManager_inner.ump").setUmpleLines(39).setJavaLines(61).setLengths(31);}
    public UmpleSourceData FSyncManager_fsync(){ return new UmpleSourceData().setFileNames("FSync_FSyncManager.ump").setUmpleLines(30).setJavaLines(55).setLengths(57);}
    public UmpleSourceData FSyncManager_getNFSyncs(){ return new UmpleSourceData().setFileNames("Derivative_FSync_Statistics_FSyncManager.ump").setUmpleLines(17).setJavaLines(131).setLengths(1);}
    public UmpleSourceData FSyncManager_getNTimeouts(){ return new UmpleSourceData().setFileNames("Derivative_FSync_Statistics_FSyncManager.ump").setUmpleLines(21).setJavaLines(136).setLengths(1);}
    public UmpleSourceData FSyncManager_loadStats(){ return new UmpleSourceData().setFileNames("Derivative_FSync_Statistics_FSyncManager.ump").setUmpleLines(25).setJavaLines(141).setLengths(8);}
    public UmpleSourceData FSyncManager_getNFSyncRequests(){ return new UmpleSourceData().setFileNames("Derivative_FSync_Statistics_FSyncManager.ump").setUmpleLines(13).setJavaLines(126).setLengths(1);}
    public UmpleSourceData FSyncGroup_wakeupAll(){ return new UmpleSourceData().setFileNames("FSync_FSyncManager_inner.ump").setUmpleLines(72).setJavaLines(96).setLengths(2);}
    public UmpleSourceData FSyncGroup_wakeupOne(){ return new UmpleSourceData().setFileNames("FSync_FSyncManager_inner.ump").setUmpleLines(76).setJavaLines(102).setLengths(1);}
    public UmpleSourceData FSyncManager_executeFSync(){ return new UmpleSourceData().setFileNames("FSync_FSyncManager.ump").setUmpleLines(82).setJavaLines(121).setLengths(1);}
    public UmpleSourceData CheckpointMonitor_activate(){ return new UmpleSourceData().setFileNames("Derivative_CheckpointerDaemon_CPBytes_CheckpointMonitor.ump").setUmpleLines(34).setJavaLines(57).setLengths(2);}
    public UmpleSourceData CheckpointMonitor_CheckpointMonitor(){ return new UmpleSourceData().setFileNames("Derivative_CheckpointerDaemon_CPBytes_CheckpointMonitor.ump").setUmpleLines(15).setJavaLines(32).setLengths(4);}
    public UmpleSourceData CheckpointMonitor_recordLogWrite(){ return new UmpleSourceData().setFileNames("Derivative_CheckpointerDaemon_CPBytes_CheckpointMonitor.ump").setUmpleLines(26).setJavaLines(46).setLengths(2);}
    public UmpleSourceData DbVerify_hook852(){ return new UmpleSourceData().setFileNames("Derivative_LoggingInfo_Statistics_Verifier_DbVerify.ump").setUmpleLines(10).setJavaLines(53).setLengths(2);}
    public UmpleSourceData DbVerify_hook851(){ return new UmpleSourceData().setFileNames("Derivative_LoggingInfo_Statistics_Verifier_DbVerify.ump").setUmpleLines(5).setJavaLines(47).setLengths(2);}
    public UmpleSourceData LatchStats_clone(){ return new UmpleSourceData().setFileNames("Derivative_Latches_Statistics_LatchStats.ump").setUmpleLines(57).setJavaLines(45).setLengths(1);}
    public UmpleSourceData LatchStats_toString(){ return new UmpleSourceData().setFileNames("Derivative_Latches_Statistics_LatchStats.ump").setUmpleLines(45).setJavaLines(32).setLengths(9);}

  }
  public static class UmpleSourceData
  {
    String[] umpleFileNames;
    Integer[] umpleLines;
    Integer[] umpleJavaLines;
    Integer[] umpleLengths;
    
    public UmpleSourceData(){
    }
    public String getFileName(int i){
      return umpleFileNames[i];
    }
    public Integer getUmpleLine(int i){
      return umpleLines[i];
    }
    public Integer getJavaLine(int i){
      return umpleJavaLines[i];
    }
    public Integer getLength(int i){
      return umpleLengths[i];
    }
    public UmpleSourceData setFileNames(String... filenames){
      umpleFileNames = filenames;
      return this;
    }
    public UmpleSourceData setUmpleLines(Integer... umplelines){
      umpleLines = umplelines;
      return this;
    }
    public UmpleSourceData setJavaLines(Integer... javalines){
      umpleJavaLines = javalines;
      return this;
    }
    public UmpleSourceData setLengths(Integer... lengths){
      umpleLengths = lengths;
      return this;
    }
    public int size(){
      return umpleFileNames.length;
    }
  }
}