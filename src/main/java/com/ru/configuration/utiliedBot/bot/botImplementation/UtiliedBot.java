package com.ru.configuration.utiliedBot.bot.botImplementation;

import com.pengrad.telegrambot.TelegramBot;
import com.ru.configuration.utiliedBot.service.Action.UserBotAction;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class UtiliedBot {
    private TelegramBot userTelegramBot;
    private UserBotAction bot;

    @Autowired
    //@PostConstruct
    public UtiliedBot(TelegramBot userTelegramBot, String userBotChatId) {
        this.userTelegramBot = userTelegramBot;
        this.bot = new UserBotAction(userTelegramBot, userBotChatId);
    }

    public void run() {
        bot.handle(userTelegramBot);
    }

}
