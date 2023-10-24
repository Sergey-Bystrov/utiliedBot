package com.ru.configuration.utiliedBot.bot.botImplementation;


import com.ru.configuration.utiliedBot.bot.botinterface.BotStarter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UtiliedBotStarter implements BotStarter {
    @Autowired
    private UtiliedBot utiliedBot;
    @Override
    public void start() {
        utiliedBot.run();
    }
}

