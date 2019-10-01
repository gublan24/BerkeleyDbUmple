/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4260.b21abf3a3 modeling language!*/

package com.sleepycat.je;
import de.ovgu.cide.jakutil.*;
import com.sleepycat.je.dbi.DatabaseImpl;
import java.util.Comparator;
import com.sleepycat.je.log.entry.*;

// line 3 "../../../DatabaseConfig.ump"
public class DatabaseConfig implements Cloneable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DatabaseConfig()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 39 "../../../DatabaseConfig.ump"
   public  DatabaseConfig(){
    
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 45 "../../../DatabaseConfig.ump"
   public void setAllowCreate(boolean allowCreate){
    this.allowCreate = allowCreate;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 52 "../../../DatabaseConfig.ump"
   public boolean getAllowCreate(){
    return allowCreate;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 59 "../../../DatabaseConfig.ump"
   public void setExclusiveCreate(boolean exclusiveCreate){
    this.exclusiveCreate = exclusiveCreate;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 66 "../../../DatabaseConfig.ump"
   public boolean getExclusiveCreate(){
    return exclusiveCreate;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 73 "../../../DatabaseConfig.ump"
   public void setSortedDuplicates(boolean duplicatesAllowed){
    this.duplicatesAllowed = duplicatesAllowed;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 80 "../../../DatabaseConfig.ump"
   public boolean getSortedDuplicates(){
    return duplicatesAllowed;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 87 "../../../DatabaseConfig.ump"
   public void setTransactional(boolean transactional){
    this.transactional = transactional;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 94 "../../../DatabaseConfig.ump"
   public boolean getTransactional(){
    return transactional;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 101 "../../../DatabaseConfig.ump"
   public void setReadOnly(boolean readOnly){
    this.readOnly = readOnly;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 108 "../../../DatabaseConfig.ump"
   public boolean getReadOnly(){
    return readOnly;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 115 "../../../DatabaseConfig.ump"
   public void setNodeMaxEntries(int nodeMaxEntries){
    this.nodeMax = nodeMaxEntries;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 122 "../../../DatabaseConfig.ump"
   public void setNodeMaxDupTreeEntries(int nodeMaxDupTreeEntries){
    this.nodeMaxDupTree = nodeMaxDupTreeEntries;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 129 "../../../DatabaseConfig.ump"
   public int getNodeMaxEntries(){
    return nodeMax;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 136 "../../../DatabaseConfig.ump"
   public int getNodeMaxDupTreeEntries(){
    return nodeMaxDupTree;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 143 "../../../DatabaseConfig.ump"
   public void setBtreeComparator(Class btreeComparator){
    this.btreeComparator = validateComparator(btreeComparator, "Btree");
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 150 "../../../DatabaseConfig.ump"
   public Comparator getBtreeComparator(){
    return btreeComparator;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 157 "../../../DatabaseConfig.ump"
   public void setOverrideBtreeComparator(boolean override){
    overrideBtreeComparator = override;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 164 "../../../DatabaseConfig.ump"
   public boolean getOverrideBtreeComparator(){
    return overrideBtreeComparator;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 171 "../../../DatabaseConfig.ump"
   public void setDuplicateComparator(Class duplicateComparator){
    this.duplicateComparator = validateComparator(duplicateComparator, "Duplicate");
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 178 "../../../DatabaseConfig.ump"
   public Comparator getDuplicateComparator(){
    return duplicateComparator;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 185 "../../../DatabaseConfig.ump"
   public void setOverrideDuplicateComparator(boolean override){
    overrideDupComparator = override;
  }


  /**
   * 
   * Javadoc for this public method is generated via the doc templates in the doc_src directory.
   */
  // line 192 "../../../DatabaseConfig.ump"
   public boolean getOverrideDuplicateComparator(){
    return overrideDupComparator;
  }


  /**
   * 
   * For utilities, to avoid having to know the configuration of a database.
   */
  // line 199 "../../../DatabaseConfig.ump"
  public void setUseExistingConfig(boolean useExistingConfig){
    this.useExistingConfig = useExistingConfig;
  }


  /**
   * 
   * For utilities, to avoid having to know the configuration of a database.
   */
  // line 206 "../../../DatabaseConfig.ump"
  public boolean getUseExistingConfig(){
    return useExistingConfig;
  }


  /**
   * 
   * Used by Database to create a copy of the application supplied configuration. Done this way to provide non-public cloning.
   */
  // line 213 "../../../DatabaseConfig.ump"
  public DatabaseConfig cloneConfig(){
    try {
	    return (DatabaseConfig) super.clone();
	} catch (CloneNotSupportedException willNeverOccur) {
	    return null;
	}
  }

  // line 221 "../../../DatabaseConfig.ump"
  public void validate(DatabaseConfig config) throws DatabaseException{
    if (config == null) {
	    config = DatabaseConfig.DEFAULT;
	}
	boolean txnMatch = config.getTransactional() == transactional;
	boolean roMatch = config.getReadOnly() == readOnly;
	boolean sdMatch = config.getSortedDuplicates() == duplicatesAllowed;
	boolean btCmpMatch = (config.overrideBtreeComparator
		? btreeComparator.getClass() == config.getBtreeComparator().getClass()
		: true);
	boolean dtCmpMatch = (config.getOverrideDuplicateComparator()
		? duplicateComparator.getClass() == config.getDuplicateComparator().getClass()
		: true);
	if (txnMatch && roMatch && sdMatch && btCmpMatch && dtCmpMatch) {
	    return;
	} else {
	    String message = genDatabaseConfigMismatchMessage(config, txnMatch, roMatch, sdMatch, btCmpMatch,
		    dtCmpMatch);
	    throw new DatabaseException(message);
	}
  }

  // line 244 "../../../DatabaseConfig.ump"
  public String genDatabaseConfigMismatchMessage(DatabaseConfig config, boolean txnMatch, boolean roMatch, boolean sdMatch, boolean btCmpMatch, boolean dtCmpMatch){
    StringBuffer ret = new StringBuffer("The following DatabaseConfig parameters for the\n"
		+ "cached Database do not match the parameters for the\n" + "requested Database:\n");
	if (!txnMatch) {
	    ret.append(" Transactional\n");
	}
	if (!roMatch) {
	    ret.append(" Read-Only\n");
	}
	if (!sdMatch) {
	    ret.append(" Sorted Duplicates\n");
	}
	if (!btCmpMatch) {
	    ret.append(" Btree Comparator\n");
	}
	if (!dtCmpMatch) {
	    ret.append(" Duplicate Comparator\n");
	}
	return ret.toString();
  }


  /**
   * 
   * Check that this comparator can be instantiated by JE.
   */
  // line 268 "../../../DatabaseConfig.ump"
   private Comparator validateComparator(Class comparator, String type) throws IllegalArgumentException{
    if (comparator == null) {
	    return null;
	}
	try {
	    Comparator ret = DatabaseImpl.instantiateComparator(comparator, type);
	    if (ret instanceof Comparator) {
		return ret;
	    } else {
		throw new IllegalArgumentException(comparator.getName() + " is is not valid as a " + type
			+ " comparator because it does not " + " implement java.util.Comparator.");
	    }
	} catch (DatabaseException e) {
	    throw new IllegalArgumentException(type + " comparator is not valid: " + e.getMessage()
		    + "\nPerhaps you have not implemented a zero-parameter "
		    + "constructor for the comparator or the comparator class " + "cannot be found.");
	}
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 9 "../../../DatabaseConfig.ump"
  public static final DatabaseConfig DEFAULT = new DatabaseConfig() ;
// line 11 "../../../DatabaseConfig.ump"
  private boolean allowCreate = false ;
// line 13 "../../../DatabaseConfig.ump"
  private boolean exclusiveCreate = false ;
// line 15 "../../../DatabaseConfig.ump"
  private boolean transactional = false ;
// line 17 "../../../DatabaseConfig.ump"
  private boolean readOnly = false ;
// line 19 "../../../DatabaseConfig.ump"
  private boolean duplicatesAllowed = false ;
// line 21 "../../../DatabaseConfig.ump"
  private int nodeMax ;
// line 23 "../../../DatabaseConfig.ump"
  private int nodeMaxDupTree ;
// line 25 "../../../DatabaseConfig.ump"
  private Comparator btreeComparator = null ;
// line 27 "../../../DatabaseConfig.ump"
  private Comparator duplicateComparator = null ;
// line 29 "../../../DatabaseConfig.ump"
  private boolean overrideBtreeComparator = false ;
// line 31 "../../../DatabaseConfig.ump"
  private boolean overrideDupComparator = false ;
// line 33 "../../../DatabaseConfig.ump"
  private boolean useExistingConfig = false ;

  
}