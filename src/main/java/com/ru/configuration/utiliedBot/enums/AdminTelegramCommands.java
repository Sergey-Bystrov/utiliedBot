package com.ru.configuration.utiliedBot.enums;

import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.ru.configuration.utiliedBot.funcInterface.ReplyInterface;
import com.ru.configuration.utiliedBot.service.action.AdminMarkup;

public enum AdminTelegramCommands {
    BACK("НАЗАД", (chatId, message, markup) -> new SendMessage(chatId, "Главное меню")
            .parseMode(ParseMode.Markdown)
            .replyMarkup(markup.getStartKeyboardMarkup())),
    WASTE_PAPER("МАКУЛАТУРА", (chatId, message, markup) -> new SendMessage(chatId, message)
            .parseMode(ParseMode.Markdown)
            .replyMarkup(markup.getReplyKeyboardMarkup(
                    Price.getWastePaper().stream()
                            .map(Price::getTitle)
                            .toArray(String[]::new)
            ))),
    PLASTIC("ПЛАСТИК", (chatId, message, markup) -> new SendMessage(chatId, message)
            .parseMode(ParseMode.Markdown)
            .replyMarkup(markup.getReplyKeyboardMarkup(
                    Price.getPlastic().stream()
                            .map(Price::getTitle)
                            .toArray(String[]::new)
            ))),
    PLASTIC_FILM("ПЛЁНКА", (chatId, message, markup) -> new SendMessage(chatId, message)
            .parseMode(ParseMode.Markdown)
            .replyMarkup(markup.getReplyKeyboardMarkup(
                    Price.getMembrane().stream()
                            .map(Price::getTitle)
                            .toArray(String[]::new)
            ))),
    ALL_POSITIONS("ВСЕ ПОЗИЦИИ", (chatId, message, markup) -> new SendMessage(chatId, message)
            .parseMode(ParseMode.Markdown)
            .replyMarkup(markup.getReplyKeyboardMarkup(
                    Price.getAll().stream()
                            .map(Price::getTitle)
                            .toArray(String[]::new)
            ))),
    CHANGE_PRISE("ИЗМЕНИТЬ РАСЦЕНКИ", (chatId, message, markup) -> new SendMessage(chatId, message)
            .replyMarkup(markup.getReplyKeyboardMarkup(
                    new String[]{WASTE_PAPER.getCommand(), PLASTIC.getCommand(), PLASTIC_FILM.getCommand(), BACK.getCommand()}
            )))
    ;

    private final String command;
    private ReplyInterface<AdminMarkup> reply;

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
