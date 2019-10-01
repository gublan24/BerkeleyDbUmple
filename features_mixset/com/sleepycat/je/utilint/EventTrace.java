/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je.utilint;
import de.ovgu.cide.jakutil.*;

// line 3 "../../../../EventTrace.ump"
// line 3 "../../../../EventTrace_static.ump"
public class EventTrace
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public EventTrace()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 21 "../../../../EventTrace.ump"
   public  EventTrace(String comment){
    this.comment = comment;
  }

  // line 25 "../../../../EventTrace.ump"
   public  EventTrace(){
    comment = null;
  }

  // line 29 "../../../../EventTrace.ump"
   public String toString(){
    return comment;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 6 "../../../../EventTrace.ump"
  static private int MAX_EVENTS = 100 ;
// line 8 "../../../../EventTrace.ump"
  static public final boolean TRACE_EVENTS = false ;
// line 10 "../../../../EventTrace.ump"
  static int currentEvent = 0 ;
// line 12 "../../../../EventTrace.ump"
  static final EventTrace[] events = new EventTrace[MAX_EVENTS] ;
// line 14 "../../../../EventTrace.ump"
  static final int[] threadIdHashes = new int[MAX_EVENTS] ;
// line 16 "../../../../EventTrace.ump"
  static boolean disableEvents = false ;
// line 18 "../../../../EventTrace.ump"
  protected String comment ;

// line 32 "../../../../EventTrace.ump"
  static public void addEvent (EventTrace event) 
  {
    if (disableEvents) {
	    return;
	}
	int nextEventIdx = currentEvent++ % MAX_EVENTS;
	events[nextEventIdx] = event;
	threadIdHashes[nextEventIdx] = System.identityHashCode(Thread.currentThread());
  }

// line 41 "../../../../EventTrace.ump"
  static public void addEvent (String comment) 
  {
    if (disableEvents) {
	    return;
	}
	addEvent(new EventTrace(comment));
  }

// line 48 "../../../../EventTrace.ump"
  static public void dumpEvents () 
  {
    if (disableEvents) {
	    return;
	}
	System.out.println("----- Event Dump -----");
	EventTrace[] oldEvents = events;
	int[] oldThreadIdHashes = threadIdHashes;
	disableEvents = true;
	int j = 0;
	for (int i = currentEvent; j < MAX_EVENTS; i++) {
	    EventTrace ev = oldEvents[i % MAX_EVENTS];
	    if (ev != null) {
		int thisEventIdx = i % MAX_EVENTS;
		System.out.print(oldThreadIdHashes[thisEventIdx] + " ");
		System.out.println(j + "(" + thisEventIdx + "): " + ev);
	    }
	    j++;
	}
  }

// line 4 "../../../../EventTrace_static.ump"
  static  class ExceptionEventTrace extends EventTrace 
  {
    private Exception event;
      public ExceptionEventTrace(){
        event=new Exception();
      }
      public String toString(){
        return Tracer.getStackTrace(event);
      }
  }

  
}