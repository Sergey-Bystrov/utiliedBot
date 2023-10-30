package com.ru.configuration.utiliedBot.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UtiliedBotExceptions extends Exception {
    public UtiliedBotExceptions(String message) {
        super(message);
        log.error(message);
    }

    public UtiliedBotExceptions(String message, Throwable cause) {
        super(message, cause);
    }
}
