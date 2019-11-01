package COR_example2;

import annotationlibrary.Handler;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


@Handler
public class ErrorLogger extends AbstractLogger {

    private final Logger LOGGER = LoggerFactory.getLogger(ErrorLogger.class);

    ErrorLogger(AbstractLogger logger){
        super(logger);
    }


   @Override
    public void logMessage(int level, String message) throws IOException {
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
    protected void write(String message) throws IOException {
        System.out.println("Error Console::Logger: " + message);
        File file= new File(ConfigFactory.load().getString("ErrorLoggerLogFile"));
        FileWriter writer = new FileWriter(file);
        writer.write("Error Console::Logger: " + message);
        writer.close();
    }
}