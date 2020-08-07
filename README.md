
# Berkeley DB (Umple)

## Description
This repo offers an SPL compositional version of Berkeley DB JE writen in Umple. It is refactored from a FeatureHouse implementaion. Mixsets are used to represent the features of the Berkeley DB. Initial translation to Umple was accomplished by Umplificator. Then each feature’s fragments are refactored into mixset fragments. The feature diagram below (click to zoom-in) can be viewed and generated using Feature Diagram examples in UmpleOnline tool : http://cruise.eecs.uottawa.ca/umpleonline/

![alt text](https://raw.githubusercontent.com/gublan24/BerkeleyDbUmple/e9aebd48dc937a2cad2b7c0640107144d6f6113b/src/configuration/graphviz.svg)
##  Installation
There two simple requirements : 
 1. Download or clone this repo.
 2. Download [Umple.jar](https://cruise.eecs.uottawa.ca/umpleonline/download_eclipse_umple_plugin.shtml). 

 
## Usage
 Use Umple command line : `java -jar UMPLE_JAR_DIR/umple.jar BerkeleyDb_DIR/src/umpleMaster.ump` to generate an SPL variant. Where UMPLE_JAR_DIR is the directory containing umple.jar and BerkeleyDb_DIR is the directory containing this repo code. The SPL variant is specified in `src/configuration/SPLVariaintConfig.ump`.
 
To run Berkelely DB JE 3.0.12 (Code generated in Java), there are three requirements: 
 1. ant (you can use ant-1.8.2.jar).  
 2. Javax (you can use javax.resource-api-1.7.jar).  
 3. Fix Junit testing Junit3 to resolve the issue regarding "Assert cannot be resolved".

I've used Eclipse IDE :
 - Version: Photon Release (4.8.0) 
 - Java JDK version: You can run Berkelely DB JE 3.0.12 on old version of JDK like 1.5.

## Contents of the directories
This project is split into many smaller subprojects; the folder hierarchy is explained below to better describe the structure.
 * **src**
The src folder includes the source code of Berkeley DB in Umple format. The src folder contains the **features** and their interaction in **feature_interaction**. 
 * **alternative_imp**
 The alternative_imp folder contains the original source code of Berkeley DB JE, [CIDE](https://github.com/ckaestne/CIDE/tree/master/CIDE_Samples/cide_samples/Berkeley%20DB%20JE) implementation, and [FeatureHouse](https://github.com/FeatureIDE/FeatureIDE/tree/develop/plugins/de.ovgu.featureide.examples/featureide_examples/BerkeleyDB-FH-Java) Implementation.  
 * **umplificator**/ 
It includes a jar file "umplificator.jar" that we've used to reverse engineer Java code to Umple. 

