package com.ru.configuration.utiliedBot.service.action;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.ru.configuration.utiliedBot.exceptions.UtiliedBotExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdminBotAction extends BaseBotAction {
    private TelegramBot adminTelegramBot;
    private String adminBotChatId;

    public AdminBotAction() {

    }

    @Autowired
    public AdminBotAction(TelegramBot adminTelegramBot, String adminBotChatId) {
        this.adminTelegramBot = adminTelegramBot;
        this.adminBotChatId = adminBotChatId;
        super.setAdminTelegramBot(adminTelegramBot);
    }

    @Override
    protected SendMessage getStartScreenReplyMarkup(long chatId, String text) {
        return new SendMessage(chatId, text)
                .replyMarkup(new ReplyKeyboardMarkup(
                                new String[]{command.CHANGE_PRISE}
                        )
                );
    }

    @Override
    protected SendMessage getStartKeyboard(long chatId, String text) {
        if (command.CHANGE_PRISE.equals(text)) {
            return getPriceScreenReplyMarkup(chatId, command.CHANGE_PRISE);
        } else return getStartScreenReplyMarkup(chatId, "NOT_FOUND");
    }

    protected void handlePhoto(Message message) throws UtiliedBotExceptions {
        PhotoSize[] photoInputArray = message.photo();
        int photoArraySize = message.photo().length;
        if (photoArraySize != 0) {
            adminTelegramBot.execute(new SendPhoto(adminBotChatId, photoInputArray[0].fileId()));
        } else throw new UtiliedBotExceptions("Error while processing photo");
    }


}
