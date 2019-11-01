package COR_example2;

import annotationlibrary.Handler;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Handler
public class ConsoleLogger extends AbstractLogger {
    private final Logger LOGGER = LoggerFactory.getLogger(ConsoleLogger.class);

    ConsoleLogger(AbstractLogger logger){
        super(logger);
    }

   @Override
    public void logMessage(int level, String message) throws IOException {
        if(level == 1){
            LOGGER.info("[Handler] Input caught at Console handler");
            write(message);
        }
        else {
            LOGGER.info("[Handler] Cannot process the request. Passing to next handler");
            super.logMessage(level,message);
        }
    }

    @Override
    protected void write(String message) throws IOException {

        System.out.println("Standard Console::Logger: " + message);
        File file=new File(ConfigFactory.load().getString("ConsoleLoggerLogFile"));
        FileWriter writer = new FileWriter(file);
        writer.write("Standard Console::Logger: " + message);
        writer.close();
    }
}