
# Berkeley DB (Umple)

## Description
This repo has a compositional SPL version of Berkeley DB JE in Umple. It was refacttoed from a version written in FeatureHouse. Mixsets are used to represent features of  SPL Berkeley DB. Initial translation to Umple was accomplished by Umplificator. Then we transformed each feature’s fragments written in FeatureHouse code into mixset fragments.

##  Installation
There two simple requirements : 
 1. Download or clone this repo.
 2. Download [Umple.jar](https://cruise.eecs.uottawa.ca/umpleonline/download_eclipse_umple_plugin.shtml). 

 
## Usage
 Use Umple command line : `java -jar UMPLE_JAR_DIR/umple.jar BerkeleyDb_DIR/src/MainUmpleMaster.ump` to generate an SPL variant. Where UMPLE_JAR_DIR is the directory containing umple.jar and BerkeleyDb_DIR is the directory containing this repo code. 
 
To run Berkelely DB JE 3.0.12 (Code generated in Java), there are three requirements: 
 1. ant (you can use ant-1.8.2.jar).  
 2. Javax (you can use javax.resource-api-1.7.jar).  
 3. Fix Junit testing Junit3 to resolve the issue regarding "Assert cannot be resolved".

I've used Eclipse IDE :
 - Version: Photon Release (4.8.0) 
 - Java JDK version: You can run Berkelely DB JE 3.0.12 on old version of JDK like 1.5.

## Contents of the directories
This project is split into many smaller subprojects; the folder hierarchy is explained below to better describe the structure.
 * **src**/
The src folder includes the source code of Berkeley DB in Umple format. The src folder contains the **Features**/ and their interaction in **Feature_Interaction**/. 
 * **alternative_imp**/
 The alternative_imp folder contains the original source code of Berkeley DB JE, [CIDE](https://github.com/ckaestne/CIDE/tree/master/CIDE_Samples/cide_samples/Berkeley%20DB%20JE) implementation, and FeatureHouse Implementation.  
 * **umplificator**/ 
It includes a jar file "umplificator.jar" that we've used to reverse engineer Java code to Umple. 

