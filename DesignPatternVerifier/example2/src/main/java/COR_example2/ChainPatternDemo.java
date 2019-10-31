package COR_example2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ChainPatternDemo {
    private final static Logger LOGGER = LoggerFactory.getLogger(ChainPatternDemo.class);

    private static AbstractLogger getChainOfLoggers(){
        LOGGER.info("Creating chain of handlers");
    // Form a chain of logging handlers
    AbstractLogger errorLogger= new ErrorLogger(new FileLogger(new ConsoleLogger(null)));

        return errorLogger;
    }

    public static void main(String[] args) {
        AbstractLogger loggerChain = getChainOfLoggers();
        LOGGER.info("Creating requests...");
        loggerChain.logMessage(AbstractLogger.ERROR,
                "This is an error level information.");

        loggerChain.logMessage(AbstractLogger.DEBUG,
                "This is an debug level information.");

        loggerChain.logMessage(AbstractLogger.INFO,
                "This is a console information.");
    }
}