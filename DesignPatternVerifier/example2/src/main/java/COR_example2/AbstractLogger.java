package COR_example2;

import annotationlibrary.AbstractHandle;
import annotationlibrary.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@AbstractHandler
public class AbstractLogger  {

    private final Logger LOGGER = LoggerFactory.getLogger(AbstractLogger.class);
    public static int INFO = 1;
    public static int DEBUG = 2;
    public static int ERROR = 3;

    //next element in chain or responsibility
    protected AbstractLogger nextLogger;

    AbstractLogger(AbstractLogger logger){
        this.nextLogger=logger;
    }


    @AbstractHandle
    public void logMessage(int level, String message){
        // If there is a next logger to handle the request, pass the request to it
        if(nextLogger !=null){
            LOGGER.info("[Passing to next handler]");
            nextLogger.logMessage(level, message);
        }
        else {
            LOGGER.info("No handler can process the request.");
            System.out.println("Error: No handler can catch the logger");
        }
    }

     protected void write(String message){}



}