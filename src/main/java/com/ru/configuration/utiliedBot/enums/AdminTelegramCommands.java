package com.ru.configuration.utiliedBot.enums;

import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.ru.configuration.utiliedBot.funcInterface.ReplyInterface;
import com.ru.configuration.utiliedBot.service.action.AdminMarkup;

import static com.ru.configuration.utiliedBot.enums.Catalog.*;

public enum AdminTelegramCommands {
    BACK("НАЗАД", (chatId, message, markup) -> new SendMessage(chatId, "Главное меню")
            .parseMode(ParseMode.Markdown)
            .replyMarkup(markup.getStartKeyboardMarkup())),
    CHANGE_PRISE("ИЗМЕНИТЬ РАСЦЕНКИ", (chatId, message, markup) -> new SendMessage(chatId, message)
            .replyMarkup(markup.getReplyKeyboardMarkup(
                    new String[]{WASTE_PAPER.getCommand(), PLASTIC.getCommand(), PLASTIC_FILM.getCommand(), BACK.getCommand()}
            )))
    ;

    private final String command;
    private final ReplyInterface<AdminMarkup> reply;

    AdminTelegramCommands(String command, ReplyInterface<AdminMarkup> reply) {
        this.command = command;
        this.reply = reply;
    }

    public ReplyInterface<AdminMarkup> getReply() {
        return reply;
    }

    public String getCommand() {
        return command;
    }
}
