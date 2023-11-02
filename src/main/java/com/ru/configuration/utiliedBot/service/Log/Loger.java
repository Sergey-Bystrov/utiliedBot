package com.ru.configuration.utiliedBot.service.Log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Loger {
    private static Logger logger = LoggerFactory.getLogger(Loger.class);

    private Loger(){

    }
    public static Logger LOG() {
        return logger;
    }

    /**
     * log information
     * @param info
     * **/
    public static void logInfo(String info){
        LOG().info(info);
    }

    /**
     * log custom information message with exception ifo
     * @param info - String
     * @param exception - java.lang.Exception
     * **/
    public static void logExceptionInfo(String info, Exception exception){
        LOG().info(info.concat(" ").concat(exception.getMessage()).concat(exception.getStackTrace().toString()));
    }
}
