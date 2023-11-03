package com.ru.configuration.utiliedBot.bot.botinterface;

import com.pengrad.telegrambot.TelegramBot;

public interface BaseBotActions {
    void handle(TelegramBot telegramBot);
    void listen(TelegramBot telegramBot);
}
