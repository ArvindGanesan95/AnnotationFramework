# Homework 2

# DEPLOYMENT DOCUMENT FOR DESIGN PATTERN VERIFIER
*Arvind Ganesan(UIN: 669377621)*

## Table of Contents
  - Required Tools and Frameworks
  - Dependencies Used
  - Working of Modules
  - Setting up Modules in gradle project
  - Enabling custom annotation processor in gradle
  - Setting up Configuration library
  - Unit Tests
  - Execution Logs
   - Results
  
### Required Tools and Frameworks

 - **IDE:** IntelliJ Ultimate 2018.1.8
 - **Gradle Version:** 5.0
 - **Java Version:** Java 8 SDK

## Dependencies Used
    
  - Name : Slf4j - Wrapper API for logging
 - Name: Reflections - To perform runtime checking of annotations
 - Name: logback - Logging library
 - Name: compile-testing - For Unit testing Annotation Processor
 - Name: JUnit - Unit Testing library
 - Name: Typesafe Config - Configuration library
       
   
## Working of Modules

- **Abbreviation=>** COR stands for Chain of Responsibility pattern
     - ### Module 1: examples
         - Demonstrates COR by throwing 3 different kinds of exceptions to 3 different custom exception handlers. This code uses annotations at appropriate places. 
         - No compile time errors are generated
         - Classes Used
            - ClassNotFoundExceptionClass
            - ExceptionDispatcher
            - RequestsMaker
            - IOExceptionClass
            - NullPointerExceptionClass
            - RequestHandler
         - The method checkProgramUsingReflection() performs runtime checking of annotations
     - ### Module 2: example2
        - Demonstrates COR by passing different kinds of logging levels. By passing logging levels, the corresponding handler is called. This code **generates compile time warnings** 
        - Classes Used
            - AbstractLogger
            - ChainPatternDemo
            - ConsoleLogger
            - ErrorLogger
            - FileLogger

     - ### Module 3: example3
        - Demonstrates COR through a program that approves leave based
        on the number of days. The approvers are Manager,Supervisor, and AccountHead
        - Classes Used
            - LeaveRequest
            - LeaveSystem
            - Manager
            - SuperVisor
            - Approver
            - AccountHead

## Setting up Modules in gradle project
    - There are 4 sub modules in total: **examples,example2, example3 and annotationprocessor** and a root module
    - Add the following lines in build.gradle file for the modules: **examples,example2, example3**
    
        ```
            apply plugin: 'application'
            apply plugin: 'java'
            mainClassName = 'packagename.classNameThatHasMainFunction'

            dependencies {
            compile project(':annotationprocessor')
            }    
        ```
        
    - Add the following lines in annotation processor module's build.gradle file
        ```
        apply plugin: 'java'
        
        
        dependencies {
        
        }
        
        ```
        
    - Add the following lines in **project root's build.gradle file**
        
        ```
        apply plugin: 'idea'
        
        buildscript {
        repositories {
        mavenCentral()
        }
        }
        
        allprojects {
        repositories {
        mavenCentral()
        }
        }
        
        subprojects {
        
        apply plugin: 'java'
        group = "verifier.${rootProject.name}"
        
        dependencies {
        testImplementation 'junit:junit:4.12'
        // https://mvnrepository.com/artifact/org.slf4j/slf4j-api
        compile group: 'org.slf4j', name: 'slf4j-api', version: '1.7.25'
        
        // https://mvnrepository.com/artifact/org.reflections/reflections
        compile group: 'org.reflections', name: 'reflections', version: '0.9.10'
        // https://mvnrepository.com/artifact/ch.qos.logback/logback-classic
        compile group: 'ch.qos.logback', name: 'logback-classic', version: '1.2.1'
        
        // https://mvnrepository.com/artifact/com.google.testing.compile/compile-testing
        compile group: 'com.google.testing.compile', name: 'compile-testing', version: '0.15'
        }
        }
        
        ```
        
    - Add the following lines in **project root's settings.gradle file**
    
        ```
            include 'examples'
            include 'annotationprocessor'
            include 'example2'
            include 'example3'
        ```
        
## Enabling Custom Annotation Processor in gradle project
 - In the **annotationprocessor module** of the project, create resources folder
 - In resources folder, create a nested folder : META-INF/services
 - Inside the nested folder, create a .Processor file with the name: javax.annotation.processing
 - Add the following lines in that file: packagename.classNameOfAnnotationProcessor

## Setting up Configuration library
   - After adding TypeSafe dependency, an application.conf file
     is created inside the required module under resources folder.
   - In this project, example2 module uses a configuration file.

## Unit Tests
 - There are 5 unit tests written for validating annotation processor logic using google compile-testing library
 - Tests are
     - CheckIfWarningIsThrownOnMissingAnnotation
     - CheckIfErrorIsThrownForNonAbstractClassOnAnnotatingAsAbstract
     - CheckIfInheritanceIsNotProperOnHandler
     - checkIfConstructorTypeMatches
     - checkIfAnnotatedElementIsClassType

## Execution Logs
- Writing logs to a file can be done as follows: Create a file called logback.xml inside resources folder of the 3 modules: examples, example2 and example3.
- Add the following lines to logback.xml

    ```
            <?xml version="1.0" encoding="UTF-8"?>
            <configuration>
            <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
            <encoder>
            <pattern>%d{dd-MM-yyyy HH:mm:ss.SSS} %magenta([%thread]) %highlight(%-5level) %logger{36}.%M - %msg%n</pattern>
            </encoder>
            </appender>
            <appender name="WRITE-IN-FILE" class="ch.qos.logback.core.FileAppender">
            <file>logs.txt</file>
            <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <Pattern>
            %d{dd-MM-yyyy HH:mm:ss.SSS} [%thread] %-5level %logger{36}.%M - %msg%n
            </Pattern>
            </encoder>
            </appender>
            <root level="info">
            <appender-ref ref="STDOUT" />
            <appender-ref ref="WRITE-IN-FILE" />
            </root>
            </configuration>
    ```

## Results
-   **Using IntelliJ**
    - Run the following commands in gradle window in IntelliJ for the whole project: clean, build.
    - This shows the results of the compilation in output window in IntelliJ. After that executing 'run' task in gradle window in IntelliJ runs the code and shows appropriate logs
- **Using gradle Commandline**
    - Go to the project root's directory. Open gradlew.bat file in command line
    - Execute the following command: gradlew clean build run
    
    ![buildOutput](https://bitbucket.org/arvind_ganesan/arvind_ganesan_hw2/raw/e8c1a7dcb237ec80bd85c93d31694a65ea6bf93d/OutputImages/buildOutput.JPG)
    ![runOutput](https://bitbucket.org/arvind_ganesan/arvind_ganesan_hw2/raw/e8c1a7dcb237ec80bd85c93d31694a65ea6bf93d/OutputImages/runOutput.JPG)

