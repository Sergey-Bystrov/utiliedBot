package com.ru.configuration.utiliedBot.bot.botinterface;

import com.pengrad.telegrambot.request.SendMessage;

public interface BaseBotActions {
    void checkBotStatus();
    void handle();
}
