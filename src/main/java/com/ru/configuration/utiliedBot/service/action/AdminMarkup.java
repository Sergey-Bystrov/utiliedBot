package com.ru.configuration.utiliedBot.service.action;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.ru.configuration.utiliedBot.enums.AdminTelegramCommands;
import com.ru.configuration.utiliedBot.model.ButtonModel;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class AdminMarkup implements ScreenKeyboardMarkup<String, ButtonModel> {
    @Override
    public ReplyKeyboardMarkup getStartKeyboardMarkup() {
        return new ReplyKeyboardMarkup(AdminTelegramCommands.CHANGE_PRISE.getCommand());
    }

    @Override
    public ReplyKeyboardMarkup getReplyKeyboardMarkup(String[] buttons) {
        return new ReplyKeyboardMarkup(buttons);
    }

    @Override
    public InlineKeyboardMarkup getInlineKeyboardMarkup(ButtonModel[] buttons) {
        return new InlineKeyboardMarkup(Arrays.stream(buttons)
                .map(b -> new InlineKeyboardButton(b.getTitle())
                        .url(b.getUrl()))
                .toArray(InlineKeyboardButton[]::new));
    }
}
