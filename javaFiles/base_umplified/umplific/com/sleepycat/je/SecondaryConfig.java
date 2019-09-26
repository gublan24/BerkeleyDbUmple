/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;
import de.ovgu.cide.jakutil.*;

// line 3 "../../../SecondaryConfig.ump"
public class SecondaryConfig extends DatabaseConfig
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SecondaryConfig()
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


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 29 "../../../SecondaryConfig.ump"
   public  SecondaryConfig(){
    
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 35 "../../../SecondaryConfig.ump"
   public void setKeyCreator(SecondaryKeyCreator keyCreator){
    this.keyCreator = keyCreator;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 42 "../../../SecondaryConfig.ump"
   public SecondaryKeyCreator getKeyCreator(){
    return keyCreator;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 49 "../../../SecondaryConfig.ump"
   public void setMultiKeyCreator(SecondaryMultiKeyCreator multiKeyCreator){
    this.multiKeyCreator = multiKeyCreator;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 56 "../../../SecondaryConfig.ump"
   public SecondaryMultiKeyCreator getMultiKeyCreator(){
    return multiKeyCreator;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 63 "../../../SecondaryConfig.ump"
   public void setAllowPopulate(boolean allowPopulate){
    this.allowPopulate = allowPopulate;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 70 "../../../SecondaryConfig.ump"
   public boolean getAllowPopulate(){
    return allowPopulate;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 77 "../../../SecondaryConfig.ump"
   public void setForeignKeyDatabase(Database foreignKeyDatabase){
    this.foreignKeyDatabase = foreignKeyDatabase;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 84 "../../../SecondaryConfig.ump"
   public Database getForeignKeyDatabase(){
    return foreignKeyDatabase;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 91 "../../../SecondaryConfig.ump"
   public void setForeignKeyDeleteAction(ForeignKeyDeleteAction foreignKeyDeleteAction){
    DatabaseUtil.checkForNullParam(foreignKeyDeleteAction, "foreignKeyDeleteAction");
	this.foreignKeyDeleteAction = foreignKeyDeleteAction;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 99 "../../../SecondaryConfig.ump"
   public ForeignKeyDeleteAction getForeignKeyDeleteAction(){
    return foreignKeyDeleteAction;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 106 "../../../SecondaryConfig.ump"
   public void setForeignKeyNullifier(ForeignKeyNullifier foreignKeyNullifier){
    this.foreignKeyNullifier = foreignKeyNullifier;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 113 "../../../SecondaryConfig.ump"
   public ForeignKeyNullifier getForeignKeyNullifier(){
    return foreignKeyNullifier;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 120 "../../../SecondaryConfig.ump"
   public void setForeignMultiKeyNullifier(ForeignMultiKeyNullifier foreignMultiKeyNullifier){
    this.foreignMultiKeyNullifier = foreignMultiKeyNullifier;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 127 "../../../SecondaryConfig.ump"
   public ForeignMultiKeyNullifier getForeignMultiKeyNullifier(){
    return foreignMultiKeyNullifier;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 134 "../../../SecondaryConfig.ump"
   public void setImmutableSecondaryKey(boolean immutableSecondaryKey){
    this.immutableSecondaryKey = immutableSecondaryKey;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 141 "../../../SecondaryConfig.ump"
   public boolean getImmutableSecondaryKey(){
    return immutableSecondaryKey;
  }

  // line 145 "../../../SecondaryConfig.ump"
  public void validate(DatabaseConfig configArg) throws DatabaseException{
    super.validate(configArg);
	if (configArg == null || !(configArg instanceof SecondaryConfig)) {
	    throw new DatabaseException("The SecondaryConfig argument is null.");
	}
	SecondaryConfig config = (SecondaryConfig) configArg;
	boolean kcMatch = equalOrBothNull(config.getKeyCreator(), keyCreator);
	boolean mkcMatch = equalOrBothNull(config.getMultiKeyCreator(), multiKeyCreator);
	boolean fkdMatch = (config.getForeignKeyDatabase() == foreignKeyDatabase);
	boolean fkdaMatch = (config.getForeignKeyDeleteAction() == foreignKeyDeleteAction);
	boolean fknMatch = equalOrBothNull(config.getForeignKeyNullifier(), foreignKeyNullifier);
	boolean fmknMatch = equalOrBothNull(config.getForeignMultiKeyNullifier(), foreignMultiKeyNullifier);
	boolean imskMatch = (config.getImmutableSecondaryKey() == immutableSecondaryKey);
	if (kcMatch && mkcMatch && fkdMatch && fkdaMatch && fknMatch && fmknMatch && imskMatch) {
	    return;
	}
	String message = genSecondaryConfigMismatchMessage(config, kcMatch, mkcMatch, fkdMatch, fkdaMatch, fknMatch,
		fmknMatch, imskMatch);
	throw new DatabaseException(message);
  }

  // line 166 "../../../SecondaryConfig.ump"
   private boolean equalOrBothNull(Object o1, Object o2){
    return (o1 != null) ? o1.equals(o2) : (o2 == null);
  }

  // line 171 "../../../SecondaryConfig.ump"
  public String genSecondaryConfigMismatchMessage(DatabaseConfig config, boolean kcMatch, boolean mkcMatch, boolean fkdMatch, boolean fkdaMatch, boolean fknMatch, boolean fmknMatch, boolean imskMatch){
    StringBuffer ret = new StringBuffer("The following SecondaryConfig parameters for the\n"
		+ "cached Database do not match the parameters for the\n" + "requested Database:\n");
	if (!kcMatch) {
	    ret.append(" SecondaryKeyCreator\n");
	}
	if (!mkcMatch) {
	    ret.append(" SecondaryMultiKeyCreator\n");
	}
	if (!fkdMatch) {
	    ret.append(" ForeignKeyDelete\n");
	}
	if (!fkdaMatch) {
	    ret.append(" ForeignKeyDeleteAction\n");
	}
	if (!fknMatch) {
	    ret.append(" ForeignKeyNullifier\n");
	}
	if (!fknMatch) {
	    ret.append(" ForeignMultiKeyNullifier\n");
	}
	if (!imskMatch) {
	    ret.append(" ImmutableSecondaryKey\n");
	}
	return ret.toString();
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 7 "../../../SecondaryConfig.ump"
  static SecondaryConfig DEFAULT = new SecondaryConfig() ;
// line 9 "../../../SecondaryConfig.ump"
  private boolean allowPopulate ;
// line 11 "../../../SecondaryConfig.ump"
  private SecondaryKeyCreator keyCreator ;
// line 13 "../../../SecondaryConfig.ump"
  private SecondaryMultiKeyCreator multiKeyCreator ;
// line 15 "../../../SecondaryConfig.ump"
  private Database foreignKeyDatabase ;
// line 17 "../../../SecondaryConfig.ump"
  private ForeignKeyDeleteAction foreignKeyDeleteAction = ForeignKeyDeleteAction.ABORT ;
// line 19 "../../../SecondaryConfig.ump"
  private ForeignKeyNullifier foreignKeyNullifier ;
// line 21 "../../../SecondaryConfig.ump"
  private ForeignMultiKeyNullifier foreignMultiKeyNullifier ;
// line 23 "../../../SecondaryConfig.ump"
  private boolean immutableSecondaryKey ;

  
}