package com.ru.configuration.utiliedBot.bot.botImplementation;

import com.pengrad.telegrambot.TelegramBot;
import com.ru.configuration.utiliedBot.service.action.AdminBotAction;
import com.ru.configuration.utiliedBot.service.action.BaseBotAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BotAdmin {
    private TelegramBot adminTelegramBot;
    private BaseBotAction bot;

    @Autowired
    //@PostConstruct
    public BotAdmin(TelegramBot adminTelegramBot, String adminBotChatId) {
        this.adminTelegramBot = adminTelegramBot;
        this.bot = new AdminBotAction(adminTelegramBot, adminBotChatId);
    }

    public void run() {
        bot.handle(adminTelegramBot);
    }
}
