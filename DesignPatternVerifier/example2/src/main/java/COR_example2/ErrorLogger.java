package COR_example2;

import annotationlibrary.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




@Handler
public class ErrorLogger extends AbstractLogger {

    private final Logger LOGGER = LoggerFactory.getLogger(ErrorLogger.class);

    ErrorLogger(AbstractLogger logger){
        super(logger);
    }


   @Override
    public void logMessage(int level, String message){
        if(level == 3){
            LOGGER.info("[Handler] Input caught at file handler");
            write(message);
        }
        else {
            LOGGER.info("[Handler] Cannot process the request. Passing to next handler");
            super.logMessage(level, message);
        }
    }

    @Override
    protected void write(String message) {
        System.out.println("Error Console::Logger: " + message);
    }
}