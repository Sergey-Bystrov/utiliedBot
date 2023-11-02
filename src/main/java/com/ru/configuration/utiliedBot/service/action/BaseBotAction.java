package com.ru.configuration.utiliedBot.service.action;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Video;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.*;
import com.pengrad.telegrambot.response.GetMeResponse;
import com.ru.configuration.utiliedBot.bot.botinterface.BaseBotActions;
import com.ru.configuration.utiliedBot.bot.botinterface.ScreenReplyMarkup;
import com.ru.configuration.utiliedBot.constants.Errors;
import com.ru.configuration.utiliedBot.enums.UtilType;
import com.ru.configuration.utiliedBot.exceptions.UtiliedBotExceptions;
import com.ru.configuration.utiliedBot.repository.Addresses;
import com.ru.configuration.utiliedBot.repository.MessagesForUser;
import com.ru.configuration.utiliedBot.service.messages.MultipleTypesMessageInterface;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

import static com.ru.configuration.utiliedBot.enums.Location.KOSTROMA;
import static com.ru.configuration.utiliedBot.enums.Location.SHARYA;
import static com.ru.configuration.utiliedBot.enums.UtilType.*;

@Slf4j
public class BaseBotAction extends ScreenReplyMarkup implements BaseBotActions {
    private TelegramBot telegramBot;
    private String botToken;
    private String stringChatId;
    private MultipleTypesMessageInterface<UtilType> priceMessageService;

    public BaseBotAction(TelegramBot telegramBot, String botToken, String stringChatId,
                         MultipleTypesMessageInterface<UtilType> priceMessageService) {
        this.telegramBot = telegramBot;
        this.botToken = botToken;
        this.stringChatId = stringChatId;
        this.priceMessageService = priceMessageService;
    }

    public void checkBotStatus() {
        GetMe getMe = new GetMe();
        GetMeResponse botUser = telegramBot.execute(getMe);
        log.info("Bot username: " + botUser.toString());
    }

    @Override
    public void handle() {
        listen();
    }

    protected void listen() {
        telegramBot.setUpdatesListener(list -> {
            list.forEach(update -> telegramBot.execute(handleUpdates(update)));
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    protected void getStartKeyboard() {
        //todo наша реализация
    }

    protected AbstractSendRequest handleUpdates(com.pengrad.telegrambot.model.Update update) {
        Message inputMessage = update.message();
        if (inputMessage.photo() != null) {
            try {
                handlePhoto(inputMessage);
            } catch (UtiliedBotExceptions e) {
                log.error(Errors.errors.get("HANDLE_PHOTO_ERROR"), e);
            }
        } else if (inputMessage.video() != null) {
            try {
                handleVideo(inputMessage);
            } catch (UtiliedBotExceptions e) {
                log.error(Errors.errors.get("HANDLE_VIDEO_ERROR"), e);
            }
        } else if (inputMessage.text() != null) {
            return getStartKeyboard(inputMessage.chat().id(), inputMessage.text());
        }
        return getStartKeyboard(inputMessage.chat().id(), inputMessage.text());
    }

    //todo сейчас фото и видео пересылаются обратно в тот же чат нужно решить куда их надо отправлять
    protected void handlePhoto(Message message) throws UtiliedBotExceptions {
        PhotoSize[] photoInputArray = message.photo();
        int photoArraySize = message.photo().length;
        if (photoArraySize != 0) {
            telegramBot.execute(new SendPhoto(message.chat().id(), photoInputArray[0].fileId()));
        } else throw new UtiliedBotExceptions("Error while processing photo");
    }

    protected void handleVideo(Message message) throws UtiliedBotExceptions {
        Video inputVideo = message.video();
        if (inputVideo.fileSize() != null) {
            telegramBot.execute(new SendVideo(message.chat().id(), inputVideo.fileId()));
        }else throw new UtiliedBotExceptions("Error while handling video");
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
            return getStartScreenReplyMarkup(chatId, priceMessageService.getByType(WASTE_PAPER));
        } else if (command.PLASTIC.equals(text)) {
            return getStartScreenReplyMarkup(chatId, priceMessageService.getByType(PLASTIC));
        } else if (command.PLASTIC_FILM.equals(text)) {
            return getStartScreenReplyMarkup(chatId, priceMessageService.getByType(MEMBRANE));
        } else if (command.ALL_POSITIONS.equals(text)) {
            return getStartScreenReplyMarkup(chatId,
                    priceMessageService.getByAllTypes(Arrays.stream(values()).toList()));
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
