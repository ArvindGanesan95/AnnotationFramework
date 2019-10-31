package correctimplementation;

import java.io.IOException;

import annotationlibrary.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Handler
public class IOExceptionClass extends RequestHandler {


    private final Logger LOGGER = LoggerFactory.getLogger(IOExceptionClass.class);
    public IOExceptionClass(RequestHandler next) {
        super(next);
    }

    @Override
    void handleRequest(Throwable request){
        //Check if this class can  handle IO exception. If
        // yes, process it. Else call the next handler.
        if(request instanceof IOException){
            LOGGER.info("[Handler]Caught IOException");
            System.out.println("[Handler]Caught IOException");
        }
        else {
            LOGGER.info("[Handler] Cannot process the request. Passing to next handler");
            super.handleRequest(request);
        }
    }
}
