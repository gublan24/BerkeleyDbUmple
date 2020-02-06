/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;
import de.ovgu.cide.jakutil.*;
import java.util.StringTokenizer;
import java.io.FilenameFilter;
import java.io.File;
import com.sleepycat.bind.serial.*;

// line 3 "../../../../JEFileFilter.ump"
public class JEFileFilter implements FilenameFilter
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public JEFileFilter()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 14 "../../../../JEFileFilter.ump"
  public  JEFileFilter(String [] suffix){
    this.suffix = suffix;
  }

  // line 18 "../../../../JEFileFilter.ump"
   private boolean matches(String fileSuffix){
    for (int i = 0; i < suffix.length; i++) {
	    if (fileSuffix.equalsIgnoreCase(suffix[i])) {
		return true;
	    }
	}
	return false;
  }


  /**
   * 
   * A JE file has to be of the format nnnnnnnn.suffix.
   */
  // line 30 "../../../../JEFileFilter.ump"
   public boolean accept(File dir, String name){
    boolean ok = false;
	StringTokenizer tokenizer = new StringTokenizer(name, ".");
	int nTokens = tokenizer.countTokens();
	if (nTokens == 2 || nTokens == 3) {
	    boolean hasVersion = (nTokens == 3);
	    String fileNumber = tokenizer.nextToken();
	    String fileSuffix = "." + tokenizer.nextToken();
	    String fileVersion = (hasVersion ? tokenizer.nextToken() : null);
	    if ((fileNumber.length() == 8) && matches(fileSuffix)) {
		try {
		    Integer.parseInt(fileNumber, 16);
		    ok = true;
		} catch (NumberFormatException e) {
		    ok = false;
		}
		if (hasVersion) {
		    try {
			Integer.parseInt(fileVersion);
			ok = true;
		    } catch (NumberFormatException e) {
			ok = false;
		    }
		}
	    }
	}
	return ok;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 11 "../../../../JEFileFilter.ump"
  public String[] suffix ;

  
}