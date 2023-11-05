package com.ru.configuration.utiliedBot.bot.botImplementation;

import com.pengrad.telegrambot.TelegramBot;
import com.ru.configuration.utiliedBot.enums.UtilType;
import com.ru.configuration.utiliedBot.repository.PricesLoader;
import com.ru.configuration.utiliedBot.service.action.AdminBotAction;
import com.ru.configuration.utiliedBot.service.action.AdminMarkup;
import com.ru.configuration.utiliedBot.service.action.BaseBotAction;
import com.ru.configuration.utiliedBot.service.messages.MultipleTypesMessageInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BotAdmin {
    private final TelegramBot adminTelegramBot;
    private final BaseBotAction bot;

    @Autowired
    //@PostConstruct
    public BotAdmin(TelegramBot adminTelegramBot, String adminBotChatId,
                    MultipleTypesMessageInterface<UtilType> priceMessageService,
                    AdminMarkup adminMarkup, PricesLoader pricesLoader) {
        this.adminTelegramBot = adminTelegramBot;
        this.bot = new AdminBotAction(adminTelegramBot, adminBotChatId, priceMessageService, adminMarkup, pricesLoader);
    }

    public void run() {
        bot.handle(adminTelegramBot);
    }
}
