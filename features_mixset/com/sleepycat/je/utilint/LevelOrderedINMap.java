/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.utilint;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.tree.IN;
import java.util.TreeMap;
import java.util.Set;
import java.util.HashSet;
import com.sleepycat.bind.serial.*;

// line 3 "../../../../LevelOrderedINMap.ump"
public class LevelOrderedINMap extends TreeMap
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LevelOrderedINMap()
  {
    super();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 12 "../../../../LevelOrderedINMap.ump"
   public void putIN(IN in){
    Integer level = new Integer(in.getLevel());
	Set inSet = (Set) get(level);
	if (inSet == null) {
	    inSet = new HashSet();
	    put(level, inSet);
	}
	inSet.add(in);
  }

}