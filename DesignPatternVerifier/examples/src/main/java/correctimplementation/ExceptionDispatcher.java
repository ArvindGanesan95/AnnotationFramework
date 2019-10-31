package correctimplementation;

import annotationlibrary.*;


import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.Set;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ExceptionDispatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionDispatcher.class);

    public static void main(String[] args) {

        try {
            LOGGER.info("Checking Annotations using reflections");
            // Check the program using java reflection and reflections API
            // to validate the usage of annotations
            checkProgramUsingReflection();

            RequestsMaker requests = new RequestsMaker();
            Throwable[] exceptions = new Throwable[3];
            exceptions[0] = new NullPointerException();
            exceptions[1] = new IOException();
            exceptions[2] = new ClassNotFoundException();
            for (int index = 0; index < 3; index++) {
                try {
                    throw exceptions[index];
                } catch (Throwable exception) {
                    LOGGER.info("Throwing exception request "+(index+1));
                    requests.makeRequest(exception);
                }
            }
        }catch (Exception error){
            if(error instanceof InvocationTargetException){
                LOGGER.error("[Exception thrown] "+error.getCause().getMessage());
                System.out.println("[Exception thrown] "+error.getCause().getMessage());
            }
            else{
            LOGGER.error("[Exception thrown] "+error.getMessage());
            System.out.println("[Exception thrown] "+error.getMessage());
            }
        }
    }

    static void checkProgramUsingReflection() throws InvocationTargetException,IllegalAccessException, InstantiationException{

        //Use reflection to check the CORInitiator class
        Annotation annotatedElement=RequestsMaker.class.getAnnotation(ChainObject.class);
        Reflections reflections = new Reflections(
                "correctimplementation"
        );
        Set<Class<?>> types = reflections.getTypesAnnotatedWith(CORInitiator.class);
        if (types.size()>0){
            // Get the class
            Class sclass = null;
            Iterator<Class<?>> c=types.iterator();
            while(c.hasNext()){
                sclass=c.next();
                //Get the number of private fields declared
                Field[] fields=sclass.getDeclaredFields();
                int noOFFields=fields.length;
                while(noOFFields>0){
                    boolean doesFieldTypeMatches=false;
                    //Check if the field is private
                    if(Modifier.isPrivate(fields[noOFFields-1].getModifiers())){
                        // Check if an annotation is present
                        if(fields[noOFFields-1].getDeclaredAnnotations().length>0) {
                            if (fields[noOFFields - 1].getDeclaredAnnotations()[0].annotationType().getSimpleName().equals("ChainObject")) {

                                //Check if the type is AbstractHandler type
                                Set<Class<?>> subtypes = reflections.getTypesAnnotatedWith(AbstractHandler.class);
                                if (subtypes.size() > 0) {
                                    Iterator<Class<?>> classes = subtypes.iterator();
                                    while (classes.hasNext()) {
                                        Class subclass = classes.next();
                                        if (fields[noOFFields - 1].getType().getSimpleName().equals(subclass.getSimpleName())) {
                                            doesFieldTypeMatches = true;
                                        }
                                    }
                                }
                                // If a annotated private field does not match with type, throw exception
                                if (doesFieldTypeMatches == false) {
                                    //Class does not have a field with that of type of the class abstracthandler
                                    // so throw exception
                                    throw new InvocationTargetException(new Exception("The class does not have a private field annotated " +
                                            "as ChainHandler and of type of Class which is annotated as AbstractHandler"));

                                }
                            }
                        }
                    }
                    noOFFields--;
                }
            }

            // Get the methods declared in the class
            Method[] allMethods = sclass.getDeclaredMethods();
            for (Method m : allMethods) {
                // Check if the method has an annotation
                if(allMethods[0].isAnnotationPresent(CORChain.class)){
                    // If there is a method annotated as a buildChain, calling it
                    // must make'chain' variable non null. It means, that method is
                    //instantiating the chain variable
                    Object requestMaker= sclass.newInstance();
                    //Check if the chain variable has become nonnull.
                    requestMaker.getClass().getDeclaredFields();
                    Field f=requestMaker.getClass().getDeclaredFields()[0];
                    f.setAccessible(true);
                    Object o=f.get(requestMaker);
                    if(o==null) {
                        //chain variable is being initialized
                        throw new InvocationTargetException(new Exception("The method with annotation " +
                                "CORChain does not initialize the annotated variable ChainObject"));
                    }

                }
            }

        }
    }
}

@CORInitiator
class RequestsMaker {

    private final Logger LOGGER = LoggerFactory.getLogger(RequestsMaker.class);

    @ChainObject
    private RequestHandler chain;

    RequestsMaker(){

        buildChain();
    }

    @CORChain
    private void buildChain()
    {
        //Form a non-null chain link
        chain = new NullPointerExceptionClass(new ClassNotFoundExceptionClass(new IOExceptionClass(null)));
    }

    public void makeRequest(Throwable request) {

        LOGGER.info("Calling handleRequest method");
        chain.handleRequest(request);
    }
}

