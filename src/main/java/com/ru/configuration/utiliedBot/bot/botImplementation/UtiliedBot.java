package com.ru.configuration.utiliedBot.bot.botImplementation;

import com.pengrad.telegrambot.TelegramBot;
import com.ru.configuration.utiliedBot.enums.UtilType;
import com.ru.configuration.utiliedBot.service.action.BaseBotAction;
import com.ru.configuration.utiliedBot.service.messages.MultipleTypesMessageInterface;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private MultipleTypesMessageInterface<UtilType> priceMessageService;

    @PostConstruct
    public void UtiliedBot() {
        telegramBot = new TelegramBot(botToken);
        this.bot = new BaseBotAction(telegramBot, botToken, stringChatId, priceMessageService);
    }

    public void run() {
        bot.handle();
    }

}
