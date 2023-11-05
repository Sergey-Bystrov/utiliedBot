package com.ru.configuration.utiliedBot.bot.botImplementation;

import com.pengrad.telegrambot.TelegramBot;
import com.ru.configuration.utiliedBot.service.action.AdminBotAction;
import com.ru.configuration.utiliedBot.service.action.UserBotAction;
import com.ru.configuration.utiliedBot.enums.UtilType;
import com.ru.configuration.utiliedBot.service.messages.MultipleTypesMessageInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class UtiliedBot {
    private TelegramBot userTelegramBot;
    private UserBotAction bot;

    @Autowired
    //@PostConstruct
    public UtiliedBot(TelegramBot userTelegramBot, String userBotChatId, MultipleTypesMessageInterface<UtilType> priceMessageService,
                      TelegramBot adminTelegramBot, String adminBotChatId, AdminBotAction adminBotAction) {
        this.userTelegramBot = userTelegramBot;
        this.bot = new UserBotAction(userTelegramBot, userBotChatId, priceMessageService, adminTelegramBot, adminBotChatId, adminBotAction);
    }

    public void run() {
        bot.handle(userTelegramBot);
    }

}
