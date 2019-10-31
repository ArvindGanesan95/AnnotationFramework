package correctimplementation;

import annotationlibrary.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Handler
public class NullPointerExceptionClass extends RequestHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(NullPointerExceptionClass.class);

    NullPointerExceptionClass(RequestHandler handler){
        super(handler);
    }

    @Override
    void handleRequest(Throwable request){
        //Check if this class can  handle null pointer exception. If
        // yes, process it. Else call the next handler.
        if(request instanceof NullPointerException){
            LOGGER.info("[Handler]Caught a null pointer exception");
            System.out.println("[Handler]Caught a null pointer exception");
        }
        else {
            LOGGER.info("[Handler] Cannot process the request. Passing to next handler");
            super.handleRequest(request);
        }
    }
}
