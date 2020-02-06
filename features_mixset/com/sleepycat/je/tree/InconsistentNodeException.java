/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.tree;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.*;

// line 3 "../../../../InconsistentNodeException.ump"
public class InconsistentNodeException extends DatabaseException
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public InconsistentNodeException()
  {
    super();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }

  // line 11 "../../../../InconsistentNodeException.ump"
   public  InconsistentNodeException(String message){
    super(message);
  }

}