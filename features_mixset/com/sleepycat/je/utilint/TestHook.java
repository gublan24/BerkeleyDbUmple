/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/
package com.sleepycat.je.utilint;
import de.ovgu.cide.jakutil.*;
import java.io.IOException;
// line 3 "../../../../TestHook.ump"
public interface TestHook
{
  
  public void doIOHook() throws IOException ;

  public void doHook() ;

  public Object getHookValue() ;

}