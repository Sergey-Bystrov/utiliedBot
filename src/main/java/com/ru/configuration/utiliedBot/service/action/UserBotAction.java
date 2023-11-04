package com.ru.configuration.utiliedBot.service.action;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.*;
import com.pengrad.telegrambot.response.GetFileResponse;
import com.ru.configuration.utiliedBot.constants.TelegrammCommands;
import com.ru.configuration.utiliedBot.enums.UtilType;
import com.ru.configuration.utiliedBot.exceptions.UtiliedBotExceptions;
import com.ru.configuration.utiliedBot.repository.Addresses;
import com.ru.configuration.utiliedBot.repository.MessagesForUser;
import com.ru.configuration.utiliedBot.service.messages.MultipleTypesMessageInterface;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static com.ru.configuration.utiliedBot.enums.Location.KOSTROMA;
import static com.ru.configuration.utiliedBot.enums.Location.SHARYA;
import static com.ru.configuration.utiliedBot.enums.UtilType.*;

@Component
public class UserBotAction extends BaseBotAction {
    private TelegramBot userTelegramBot;
    private String userBotChatId;
    private TelegramBot adminTelegramBot;
    private String adminBotChatId;
    private AdminBotAction adminBotAction;
    private MultipleTypesMessageInterface<UtilType> priceMessageService;

/*    public UserBotAction() {
    }*/

    @Autowired
    public UserBotAction(TelegramBot userTelegramBot, String userBotChatId, MultipleTypesMessageInterface<UtilType> priceMessageService,
                         TelegramBot adminTelegramBot, String adminBotChatId, AdminBotAction adminBotAction) {
        this.userTelegramBot = userTelegramBot;
        this.userBotChatId = userBotChatId;
        this.priceMessageService = priceMessageService;
        this.adminTelegramBot = adminTelegramBot;
        this.adminBotChatId =adminBotChatId;
        this.adminBotAction = adminBotAction;
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
    @Override
    protected AbstractSendRequest handleUpdates(com.pengrad.telegrambot.model.Update update, TelegramBot telegramBot) {
        Message inputMessage = update.message();
        if (inputMessage.photo() != null) {
            savePhoto(inputMessage, userTelegramBot);
            try {
                adminBotAction.handlePhoto(new File(TelegrammCommands.PHOTO_PATH));
            } catch (UtiliedBotExceptions e) {
                //log.error(Errors.errors.get("HANDLE_PHOTO_ERROR"), e);
            }
        } else if (inputMessage.video() != null) {
            saveVideo(inputMessage, userTelegramBot);
            try {
                adminBotAction.handleVideo(new File(TelegrammCommands.VIDEO_PATH));
                //handleVideo(inputMessage,adminTelegramBot);
            } catch (UtiliedBotExceptions e) {
                //log.error(Errors.errors.get("HANDLE_VIDEO_ERROR"), e);
            }
        } else if(inputMessage.videoNote() != null){
            saveVideoNote(inputMessage, userTelegramBot);
            try {
                adminBotAction.handleVideoNote(new File(TelegrammCommands.VIDEO_PATH));
                //handleVideoNote(inputMessage,adminTelegramBot);
            } catch (UtiliedBotExceptions e) {
                //log.error(Errors.errors.get("HANDLE_VIDEO_ERROR"), e);
            }
        }
        return getStartKeyboard(inputMessage.chat().id(), inputMessage.text());
    }

    protected void handlePhoto(Message message) throws UtiliedBotExceptions {
        PhotoSize[] photoInputArray = message.photo();
        int photoArraySize = message.photo().length;
        if (photoArraySize != 0) {
            adminTelegramBot.execute(new SendPhoto(adminBotChatId, photoInputArray[0].fileId()));
        } else throw new UtiliedBotExceptions("Error while processing photo");
    }
}
