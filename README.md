### Object-oriented design and implementation of an annotation framework and a verifier for a design pattern.


### Required Tools and Frameworks

 - **IDE:** IntelliJ Ultimate 2018.1.8
 - **Gradle Version:** 5.0
 - **Java Version:** Java 8 SDK
 
### Setting up the project

 - IntelliJ
    -   Import the project in IntelliJ
    - Sync/Rebuild the project
    - Hit the run task
 - Gradle
    - In gradle commandline, type the commands: gradlew clean build run


## Description
A Java program containing Chain of Responsibility pattern is written. It is annotated using Java Annotations Framework by defining custom annotations. Next, Java annotation processing API and Java reflection package is used to determine if design pattern is used correctly. An Annotation Processor that is derived from the abstract class AbstractProcessor is written that analyzes the annotation and the code that uses the annotation in the design pattern program and  issues warnings about the violations of the rules.


