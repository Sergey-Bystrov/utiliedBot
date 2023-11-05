package com.ru.configuration.utiliedBot.bot.botImplementation;


import com.ru.configuration.utiliedBot.bot.botinterface.BotStarter;
//import com.ru.configuration.utiliedBot.service.Action.AdminBotAction;
import com.ru.configuration.utiliedBot.repository.PricesLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BotsStarter implements BotStarter {

    private final UtiliedBot utiliedBot;
    private final BotAdmin botAdmin;
    private final PricesLoader pricesLoader;

    @Autowired
    public BotsStarter(UtiliedBot utiliedBot, BotAdmin botAdmin, PricesLoader pricesLoader) {
        this.utiliedBot = utiliedBot;
        this.botAdmin = botAdmin;
        this.pricesLoader = pricesLoader;
    }

    @Override
    public void startBot() {
        pricesLoader.loadDataFromFile();
        utiliedBot.run();
        botAdmin.run();
    }
}

