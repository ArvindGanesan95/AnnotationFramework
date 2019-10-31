package correctimplementation;

import annotationlibrary.AbstractHandle;
import annotationlibrary.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AbstractHandler
public abstract class RequestHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(RequestHandler.class);

    private RequestHandler next;

    public RequestHandler(RequestHandler next) {
        this.next = next;
    }

    @AbstractHandle
    void handleRequest(Throwable request){
        // If there is a next logger to handle the request, pass the request to it
        if (next != null) {
            LOGGER.info("[Passing to next handler]");
            next.handleRequest(request);
        }
        else {
            LOGGER.info("No handler can process the request.");
            System.out.println("Error: No handler can catch the exception thrown");
        }
    }
}
