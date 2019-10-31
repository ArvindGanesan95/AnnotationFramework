# Homework 2

# DESIGN DOCUMENT FOR DESIGN PATTERN VERIFIER
*Arvind Ganesan(UIN: 669377621)*

## Table of Contents
  - Design Pattern Used
  - Implementations of the design pattern
  - Constraints and Entities for the design pattern
  - Annotations Used
  - Use Cases covered by annotation processor
  - Use Cases Covered by annotation processor
  - Use Cases covered by Reflection
  - Limitations

## Design Pattern Used

  - ### Chain of Responsibility : 
    - This design pattern, as is intuitive from the name, is used to form a link of responsible objects. Here, it means that when the first responsible handler is unable to process the request, it will pass on the given input to the next responsible handler. If the last handler is unable to process the input, then an error may be thrown.
    - Some of the examples are : 
      - when a user clicks a button, the event propagates through the chain of GUI elements that starts with the button, goes along its containers (like forms or panels), and ends up with the main application window. The event is processed by the first element in the chain that’s capable of handling it. 
      - One of the real world analogy is calling customer care support where the representative who picks up the call, based on your queries, transfer the call to the other representative and this may continue until your query is answered.

## Implementations of the design pattern

In the project, three different implementations that uses chain of responsibility pattern are given:

* ##### Module 1 Name : examples
    - Uses annotations at the required target locations in the code and causes no compilation errors/warnings.
    - This module consists of three types of exception handling custom classes and a client class that throws 3 different types of exceptions. The appropriate handler catches the exception and prints a console message.
* ##### Module 2 Name : example2 - 
   - Uses 1 annotation at improper target and a warning is generated during compilation.
   - This Module consists of three types of custom loggers. Based on logging level, appropriate logger handler is invoked. A client passes 3 different types of logging messages as input to the design pattern code.
* ##### Module 3 Name : example3 -
   - Uses 1 annotation at improper target and a warning is generated during compilation
   - This Module consists of logic that represents a company where leave approval has to be taken care by manager, supervisor and accounthead. The number of days of leave required is the input and based on that number, corresponding person will approve the leave.
   
## Constraints and Entities for the design pattern
 - The Entities as shown in the block diagram above is an abstract central   
   co-ordinator that manages the request handlers and the handles themselves.
 - Each of the handle registers with other handlers through the central co-ordinator
 - If a handler is unable to handle the request, the control flow is taken to the central co-ordinator class which delegates the request to the next handler.
 - ##### Constraints - 
   - The central class has to be abstract
   - The subclasses needs to be inheriting the central class
   - The constructor of the subclass has to call the base class constructor
   - The method that handles the user request, if its not able to handle, must pass    the request to the central class
   - The handlers must form a chain through constructor initiation
 - ##### Note- Not all constraints can be checked. Please refer to Limitations section
   

## Annotations Used
    - Handler: To mark as class a one of the request handlers
            @Retention(RetentionPolicy.SOURCE)
            @Target(ElementType.TYPE)
            public @interface Handler {
            }

    - AbstractHandle: To Denote a Method that handles user request; declared in class annotated as AbstractHandler
            @Retention(RetentionPolicy.SOURCE)
            @Target(ElementType.METHOD)
            public @interface AbstractHandle {
            }

    - AbstractHandler - To denote an abstract class that acts as central co-ordinator for the chain of responsibility pattern
            @Retention(RetentionPolicy.RUNTIME)
            @Target(ElementType.TYPE)
            public @interface AbstractHandler {
            }

    - CORChain - To denote a method that initializes the chain variable by creating objects of handler classes
            @Retention(RetentionPolicy.RUNTIME)
            @Target(ElementType.METHOD)
            public @interface CORChain {
            }

    - CORInitiator- To denote a class that helps in forming the chain
            @Retention(RetentionPolicy.RUNTIME)
            @Target(ElementType.TYPE)
            public @interface CORInitiator {
            }

    - ChainObject - To denote a variable that point to class pointed by AbstractHandler
            @Retention(RetentionPolicy.RUNTIME)
            @Target(ElementType.FIELD)
            public @interface ChainObject {
            }


## Use Cases covered by annotation processor
 The annotation processor checks for the following cases and reports warnings/errors.
  - If a class is annotated as @AbstractHandler, the class must be abstract
   and it must have a method annotated as @AbstractHandle and it must not be a subclass of any other class.
 - If a class is annotated as @Handle, then the class must extend only from the class that is annotated with @AbstractHandler. And, the @Handle class must have a constructor of 1 argument, whose type must match with the class annotated with @AbstractHandler
 
## Use Cases covered by Reflection
Reflection is used to cover the following cases
 - If a class is annotated as @CORInitiator, that class must have a private variable, annotated as @ChainObject, whose type matches with the class that is annotated as @AbstractHandler. 
 - The class, annotated as @CORInitiator, must have a method annotated as @CORChain and the invocation of that method must make the variable, annotated as @ChainObject, to point to non-null object. If the variable points to null after calling the method with @CORChain annotation, then the annotation is inappropriate for that method.

## Limitations
 - It's is not possible to check the body of a method to verify if super() is being called. AnnotationProcessor and Reflection API don't support operations to verify a method body
 - It's not possible to find and accurately tell that the design pattern is not being instantiated anywhere in the code.


