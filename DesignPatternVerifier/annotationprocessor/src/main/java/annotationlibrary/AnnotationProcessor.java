package annotationlibrary;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.tools.Diagnostic;
import java.util.*;


@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class AnnotationProcessor extends AbstractProcessor {

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new HashSet<>(Arrays.asList(

                Handler.class.getName(),
                AbstractHandle.class.getName(),
                AbstractHandler.class.getName(),
                CORChain.class.getName(),
                CORInitiator.class.getName(),
                ChainObject.class.getName()
        ));
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {
    for (TypeElement annotation : annotations) {
        for (Element element : roundEnv.getElementsAnnotatedWith(annotation)) {

            boolean doesClassInheritNothing=false;
            boolean isSubClassingProper=false;
            // Block to handle the abstract class for COR Pattern
            if(annotation.getSimpleName().toString().equals("AbstractHandler"))
            {
                List outList = new ArrayList();
                //Check if there is a private @AbstractHandle method
                if(element.getKind().toString().equals("CLASS")) {
                    //Check if the class is Abstract
                   if (element.getModifiers().contains(Modifier.ABSTRACT)){
                        doesClassInheritNothing = false;
                    // check if the abstract class doesn't extend any other class other than Object class
                    if (((TypeElement) element).getSuperclass().toString().equals(new Object().getClass().getName())) {
                        doesClassInheritNothing = true;
                    }
                    if (doesClassInheritNothing) {
                        String className = element.getSimpleName().toString();
                        for (Element elem : roundEnv.getRootElements()) {
                            if (elem.getSimpleName().toString().equals(className)) {
                                //get list of methods and check if there is a method
                                // with AbstractHandle
                                int abstractHandleCount = 0;

                                for (Element methodDeclaration : elem.getEnclosedElements()) {

                                    if (methodDeclaration instanceof ExecutableElement)
                                        //outList.add((ExecutableElement) methodDeclaration);
                                        if (methodDeclaration.getAnnotation(AbstractHandle.class) != null) {
                                            //The class with AbstractHandler must contain only, one
                                            // abstract handle method.
                                            //If it has >1 , throw error.
                                            abstractHandleCount++;
                                        }
                                }
                                if (abstractHandleCount > 1 || abstractHandleCount == 0) {
                                    //There is more than 1 handler method in a class. So warn
                                    processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "There is" +
                                            "more than 1 or 0 method(s) annotated as a handler.");
                                }

                            }
                        }
                    }
                }
                   else {
                       processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING,"The class " +element.getSimpleName()+
                               " annotated with AbstractHandler is not abstract");
                   }
                }
                else
                    {
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING,"The element "+
                            element.getSimpleName()+" which is annotated is not a class type");
                }
          }

            // Block to handle the class that subclasses the abstract class for COR pattern
            if(annotation.getSimpleName().toString().equals("Handler")){

                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,"" +
                        "Found AbstractHandler element at "+element);


                 if(element.getKind().toString().equals("CLASS")){
                    //  check if the class's superclass is a class whose annotation is
                     // AbstractHandler
                     // Get the superclass of the current class.
                     String superClass=((TypeElement) element).getSuperclass().toString();

                     isSubClassingProper=false;
                     //check if the class's annotation is AbstractHandler
                     if(roundEnv.getElementsAnnotatedWith(AbstractHandler.class).size()>0)
                     {
                         for(Element elements:roundEnv.getElementsAnnotatedWith(AbstractHandler.class))
                         {
                             // Check if the current class's superclass matches with
                             // Any of existing class that is annotated as AbstractHandler
                             // If yes, the inheritance of current subclass is valid.
                            if(elements.getKind().toString().equals("CLASS") &&
                                    (((TypeElement) elements).getQualifiedName().toString()
                                            .equals(superClass))
                              )
                            {
                                //It means that the current class extends the exact base class
                                isSubClassingProper=true;

                                boolean isConstructorArgumentValidType = false;
                                //check the constructor if it has a only 1 argument whose type is
                                // of the class that has AbstractHandler annotation
                                for (Element members : element.getEnclosedElements()) {
                                    if (members.getKind() == ElementKind.CONSTRUCTOR) {
                                        //Code to check of the constructor parameter is of length 1 and if its of type
                                        // RequestHandler, the super Class
                                        if ((((ExecutableElement) members).getParameters().size() == 1) &&
                                                (((ExecutableElement) members).getParameters().get(0).asType().equals(((TypeElement) element).getSuperclass()))) {
                                            isConstructorArgumentValidType = true;
                                        }
                                    }
                                }
                                if (isConstructorArgumentValidType == false)
                                {
                                    //Constructor is not valid. report a warning
                                    processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "The class's " +
                                            "constructor must have one argument of type" + superClass);
                                }

                                }
                            else
                                {
                                //The current class superClass does not match with proper class name.
                                //The current class inherits some other random class
                                processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING,"The current class "+element.getSimpleName()+
                                        " does not properly extend the required abstract class with annotation AbstractHandler");

                                }

                         }
                     }
                     if(isSubClassingProper==false){
                             //The current class with annotation Handler is subtype of some other class
                             processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING,"The class "+
                                     element.getSimpleName()+" does not extend the class with AbstractHandler annotation");

                         }
                     }
                     // The currently processing element is not a class, though it has the annotation Handler
                     else {
                         //The class does not extend any class with out annotation. So warn.
                         processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING,"The class"+
                                 element.getClass().getSuperclass().getName()+"does not extend the class with AbstractHandler annotation");
                     }
                 }
            }
        }
        return true;
    }
}

