/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.log;
import de.ovgu.cide.jakutil.*;
import java.util.StringTokenizer;
import java.io.FilenameFilter;
import java.io.File;
import java.util.*;

// line 3 "../../../../JEFileFilter.ump"
public class JEFileFilter
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //JEFileFilter Attributes
  private List<String> suffix;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public JEFileFilter()
  {
    suffix = new ArrayList<String>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template attribute_SetMany */
  public boolean addSuffix(String aSuffix)
  {
    boolean wasAdded = false;
    wasAdded = suffix.add(aSuffix);
    return wasAdded;
  }

  public boolean removeSuffix(String aSuffix)
  {
    boolean wasRemoved = false;
    wasRemoved = suffix.remove(aSuffix);
    return wasRemoved;
  }
  /* Code from template attribute_GetMany */
  public String getSuffix(int index)
  {
    String aSuffix = suffix.get(index);
    return aSuffix;
  }

  public String[] getSuffix()
  {
    String[] newSuffix = suffix.toArray(new String[suffix.size()]);
    return newSuffix;
  }

  public int numberOfSuffix()
  {
    int number = suffix.size();
    return number;
  }

  public boolean hasSuffix()
  {
    boolean has = suffix.size() > 0;
    return has;
  }

  public int indexOfSuffix(String aSuffix)
  {
    int index = suffix.indexOf(aSuffix);
    return index;
  }

  public void delete()
  {}

  // line 13 "../../../../JEFileFilter.ump"
  public  JEFileFilter(String [] suffix){
    this.suffix = suffix;
  }

  // line 17 "../../../../JEFileFilter.ump"
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
  // line 29 "../../../../JEFileFilter.ump"
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


  public String toString()
  {
    return super.toString() + "["+ "]";
  }
}