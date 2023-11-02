package com.ru.configuration.utiliedBot.bot.botinterface;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;

public interface BaseBotActions {
    void handle(TelegramBot telegramBot);
    void listen(TelegramBot telegramBot);
}
