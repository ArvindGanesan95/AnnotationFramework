package correctimplementation;

import annotationlibrary.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Handler
public class ClassNotFoundExceptionClass extends RequestHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(ClassNotFoundExceptionClass.class);

    ClassNotFoundExceptionClass(RequestHandler handler){
        super(handler);
    }

    @Override
    void handleRequest(Throwable request){
        //Check if this class can  handle ClassNotFound exception. If
        // yes, process it. Else call the next handler.
        if(request instanceof ClassNotFoundException){
            LOGGER.info("[Handler]Caught ClassNotFoundException");
            System.out.println("[Handler]Caught ClassNotFoundException");
        }
        else {
            LOGGER.info("[Handler] Cannot process the request. Passing to next handler");
            super.handleRequest(request);
        }
    }
}
