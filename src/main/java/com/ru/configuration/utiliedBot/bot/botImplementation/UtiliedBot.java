package com.ru.configuration.utiliedBot.bot.botImplementation;

import com.pengrad.telegrambot.TelegramBot;
import com.ru.configuration.utiliedBot.service.Action.BaseBotAction;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class UtiliedBot {
    private TelegramBot telegramBot;
    @Value("${bot.token}")
    private String botToken;
    @Value("${bot.chat.id}")
    private String stringChatId;
    private BaseBotAction bot;

    @PostConstruct
    public void UtiliedBot() {
        telegramBot = new TelegramBot(botToken);
        this.bot = new BaseBotAction(telegramBot, botToken, stringChatId);
    }

    public void run() {
        bot.handle();
    }

}
