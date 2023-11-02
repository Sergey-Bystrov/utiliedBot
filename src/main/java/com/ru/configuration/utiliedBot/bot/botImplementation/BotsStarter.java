package com.ru.configuration.utiliedBot.bot.botImplementation;


import com.ru.configuration.utiliedBot.bot.botinterface.BotStarter;
//import com.ru.configuration.utiliedBot.service.Action.AdminBotAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BotsStarter implements BotStarter {
    @Autowired
    private UtiliedBot utiliedBot;
    @Autowired
    private BotAdmin botAdmin;
    @Override
    public void startBot() {
        utiliedBot.run();
        botAdmin.run();
    }
}

