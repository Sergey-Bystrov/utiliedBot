package com.ru.configuration.utiliedBot.bot.botinterface;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.ru.configuration.utiliedBot.constants.TelegrammCommands;
import com.ru.configuration.utiliedBot.repository.Addresses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import static com.ru.configuration.utiliedBot.enums.Location.DEFAULT;

public abstract class ScreenReplyMarkup {
    @Autowired
    protected TelegrammCommands command;

    @Value("${bot.chat.id}")
    private String stringChatId;

    public ScreenReplyMarkup(){

    }
    //todo поменять все константы команд на переменные, передающиеся в конструктор класса для реализации абстракции с точки зрения бизнес логики.
    // Либо завести абстракные командв на подобие КОМАНДА1 и тд
    protected SendMessage getStartScreenReplyMarkup(long chatId, String text) {
        return new SendMessage(chatId, text).replyMarkup(new ReplyKeyboardMarkup(new String[]{command.PRICE}, new String[]{command.HELPER}, new String[]{command.ADRESSES}));
    }
    protected SendMessage getAddressesScreenReplyMarkup(long chatId, String text) {
        //return new SendMessage(chatId, text).replyMarkup(new ReplyKeyboardMarkup(new String[]{command.PRICE}, new String[]{command.HELPER}));
        return new SendMessage(chatId, text).replyMarkup(new InlineKeyboardMarkup(
                new InlineKeyboardButton("Адрес").url(Addresses.addressesYandexMap.get(DEFAULT.getName())),
                new InlineKeyboardButton("Адрес").url(Addresses.addressesYandexMap.get(DEFAULT.getName()))
                ));
    }
    protected SendMessage getPriceScreenReplyMarkup(long chatId, String text) {
        return new SendMessage(chatId, text).replyMarkup(new ReplyKeyboardMarkup(
                new String[]{command.WASTE_PAPER},
                new String[]{command.PLASTIC},
                new String[]{command.ALL_POSITIONS},
                new String[]{command.BACK}));
    }
    protected SendMessage getStartKeyboard(long chatId, String text) {

        if (command.PRICE.equals(text)){
            return getPriceScreenReplyMarkup(chatId,command.PRICE);
        }else if(command.HELPER.equals(text)){
            return getStartScreenReplyMarkup(chatId, command.HELPER);
        }else if(command.ADRESSES.equals(text)){
            return getAddressesScreenReplyMarkup(chatId, command.ADRESSES);
        }else if(command.WASTE_PAPER.equals(text)){
            return getStartScreenReplyMarkup(chatId, command.WASTE_PAPER);
        }else if(command.PLASTIC.equals(text)){
            return getStartScreenReplyMarkup(chatId, command.PLASTIC);
        }else if(command.PLASTIC_FILM.equals(text)){
            return getStartScreenReplyMarkup(chatId, command.PLASTIC_FILM);
        }else if(command.ALL_POSITIONS.equals(text)){
            return getStartScreenReplyMarkup(chatId, command.ALL_POSITIONS);
        }else if(command.BACK.equals(text)){
            return getStartScreenReplyMarkup(chatId, command.BACK);
        }else return getStartScreenReplyMarkup(chatId, "NOT_FOUND");
    }
/*        String response = "NOT_FOUND";
        switch (text) {
            case (command.PRICE):
                return getPriceScreenReplyMarkup(Long.parseLong(stringChatId),command.PRICE);

            case (command.HELPER):
                response = command.HELPER;
                break;
            case (command.ADRESSES):
                response = command.ADRESSES;
                break;
            case (command.WASTE_PAPER):
                response = command.WASTE_PAPER;
                break;
            case (command.PLASTIC):
                response = command.PLASTIC;
                break;
            case (command.PLASTIC_FILM):
                response = command.PLASTIC_FILM;
                break;
            case (command.ALL_POSITIONS):
                response = command.ALL_POSITIONS;
                break;
            case (command.BACK):
                return getStartScreenReplyMarkup(chatId, command.BACK);
            default:
                response = text;
        }
        return getStartScreenReplyMarkup(chatId, response);*/
}
