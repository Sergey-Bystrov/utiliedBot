package com.ru.configuration.utiliedBot.service.action;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.*;
import com.ru.configuration.utiliedBot.exceptions.UtiliedBotExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

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

    protected void handlePhoto(File file, String contact) throws UtiliedBotExceptions {
        if (file != null) {
            adminTelegramBot.execute(new SendPhoto(adminBotChatId, file));
            adminTelegramBot.execute(new SendMessage(adminBotChatId, contact));
            //adminTelegramBot.execute(new SendContact(adminBotChatId, userContact.getPhoneNumber(), userContact.getFirstName()));
        } else throw new UtiliedBotExceptions("Error while processing photo");
    }
    //todo тут падал в runtime exception
    protected void handleVideo(File file, String contact) throws UtiliedBotExceptions {
        if (file != null) {
            adminTelegramBot.execute(new SendVideo(adminBotChatId, file));
            adminTelegramBot.execute(new SendMessage(adminBotChatId, contact));
        } else throw new UtiliedBotExceptions("Error while processing video");
    }

    protected void handleVideoNote(File file, String contact) throws UtiliedBotExceptions {
        if (file != null) {
            adminTelegramBot.execute(new SendVideoNote(adminBotChatId, file));
            adminTelegramBot.execute(new SendMessage(adminBotChatId, contact));
        } else throw new UtiliedBotExceptions("Error while processing videoNote");
    }


}
