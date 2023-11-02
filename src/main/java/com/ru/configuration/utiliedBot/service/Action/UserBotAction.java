package com.ru.configuration.utiliedBot.service.Action;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.ru.configuration.utiliedBot.repository.Addresses;
import com.ru.configuration.utiliedBot.repository.MessagesForUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.ru.configuration.utiliedBot.enums.Location.KOSTROMA;
import static com.ru.configuration.utiliedBot.enums.Location.SHARYA;

@Component
public class UserBotAction extends BaseBotAction{
    private TelegramBot userTelegramBot;
    private String userBotChatId;

    public UserBotAction() {
    }

    @Autowired
    public UserBotAction(TelegramBot userTelegramBot, String userBotChatId) {
        this.userTelegramBot = userTelegramBot;
        this.userBotChatId = userBotChatId;
    }
    @Override
    protected SendMessage getStartKeyboard(long chatId, String text) {

        if (command.PRICE.equals(text)) {
            return getPriceScreenReplyMarkup(chatId, command.PRICE);
        } else if (command.HELPER.equals(text)) {
            return getStartScreenReplyMarkup(chatId, MessagesForUser.messageForUser.get("helperMessage"));
        } else if (command.ADDRESSES.equals(text)) {
            return getAddressesScreenReplyMarkup(chatId, MessagesForUser.messageForUser.get("ourAddresses"));
        } else if (command.WASTE_PAPER.equals(text)) {
            return getStartScreenReplyMarkup(chatId, command.WASTE_PAPER);
        } else if (command.PLASTIC.equals(text)) {
            return getStartScreenReplyMarkup(chatId, command.PLASTIC);
        } else if (command.PLASTIC_FILM.equals(text)) {
            return getStartScreenReplyMarkup(chatId, command.PLASTIC_FILM);
        } else if (command.ALL_POSITIONS.equals(text)) {
            return getStartScreenReplyMarkup(chatId, command.ALL_POSITIONS);
        } else if (command.BACK.equals(text)) {
            return getStartScreenReplyMarkup(chatId, command.BACK);
        } else return getStartScreenReplyMarkup(chatId, "NOT_FOUND");
    }

    @Override
    protected SendMessage getAddressesScreenReplyMarkup(long chatId, String text) {
        return new SendMessage(chatId, text).replyMarkup(new InlineKeyboardMarkup(
                new InlineKeyboardButton(KOSTROMA.getName()).url(Addresses.addressesYandexMap.get(KOSTROMA.getName())),
                new InlineKeyboardButton(SHARYA.getName()).url(Addresses.addressesYandexMap.get(SHARYA.getName()))
        ));
    }
}
