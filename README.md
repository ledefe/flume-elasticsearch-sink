# Flume 1.7.0 supporting ElasticSearch 5.3.0
  
  Base on: https://github.com/Redliver/flume-ng-elasticsearch5-sink
  
  
## Modifications to your Flume lib path:

## 1.install project to get flume-elasticsearch-sink-0.0.1-SNAPSHOT.jar

## 2.mvn dependency:copy-dependencies to get dependencies jars

## 3.copy target/dependencies jars into flume/lib 
  
## 4.Libraries to be removed from flume/lib:

  * jackson-annotations-2.3.0.jar

  * jackson-core-2.3.1.jar

  * jackson-databind-2.3.1.jar

  * jopt-simple-3.2.jar

  * log4j-1.2.17.jar

  * netty-3.9.4.Final.jar
  
  * guava-11.0.2.jar

  * flume-ng-elasticsearch-sink-1.6.7.jar

  * slf4j-log4j12-1.6.1.jar
  
 ## 5.Upgrade log conf from log4j to log4j2
 
  * copy log4j2.xml to flume/conf
  

