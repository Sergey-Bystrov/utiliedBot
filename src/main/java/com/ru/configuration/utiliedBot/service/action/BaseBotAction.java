package com.ru.configuration.utiliedBot.service.action;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Video;
import com.pengrad.telegrambot.request.*;
import com.pengrad.telegrambot.response.GetMeResponse;
import com.ru.configuration.utiliedBot.bot.botinterface.BaseBotActions;
import com.ru.configuration.utiliedBot.bot.botinterface.ScreenReplyMarkup;
import com.ru.configuration.utiliedBot.constants.Errors;
import com.ru.configuration.utiliedBot.exceptions.UtiliedBotExceptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BaseBotAction extends ScreenReplyMarkup implements BaseBotActions {
    private TelegramBot adminTelegramBot;

    @Autowired
    public void setAdminTelegramBot(TelegramBot adminTelegramBot) {
        this.adminTelegramBot = adminTelegramBot;
    }

    public BaseBotAction() {
    }

/*    @Autowired
    private BaseBotAction(@Qualifier("adminTelegramBot") TelegramBot adminTelegramBot) {
        this.adminTelegramBot = adminTelegramBot;
    }*/

    @Override
    public void checkBotStatus(TelegramBot telegramBot) {
        GetMe getMe = new GetMe();
        GetMeResponse botUser = telegramBot.execute(getMe);
        log.info("Bot username: " + botUser.toString());
    }

    @Override
    public void handle(TelegramBot telegramBot) {
        listen(telegramBot);
    }

    @Override
    public void listen(TelegramBot telegramBot) {
        telegramBot.setUpdatesListener(list -> {
            list.forEach(update -> telegramBot.execute(handleUpdates(update, telegramBot)));
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    protected AbstractSendRequest handleUpdates(com.pengrad.telegrambot.model.Update update, TelegramBot adminTelegramBot) {
        Message inputMessage = update.message();
        if (inputMessage.photo() != null) {
            try {
                handlePhoto(inputMessage, adminTelegramBot);
            } catch (UtiliedBotExceptions e) {
                log.error(Errors.errors.get("HANDLE_PHOTO_ERROR"), e);
            }
        } else if (inputMessage.video() != null) {
            try {
                handleVideo(inputMessage,adminTelegramBot);
            } catch (UtiliedBotExceptions e) {
                log.error(Errors.errors.get("HANDLE_VIDEO_ERROR"), e);
            }
        }
        return getStartKeyboard(inputMessage.chat().id(), inputMessage.text());
    }

    //todo сейчас фото и видео пересылаются обратно в тот же чат нужно решить куда их надо отправлять

    protected void handlePhoto(Message message, TelegramBot telegramBot) throws UtiliedBotExceptions {
        PhotoSize[] photoInputArray = message.photo();
        int photoArraySize = message.photo().length;
        if (photoArraySize != 0) {
            telegramBot.execute(new SendPhoto(message.chat().id(), photoInputArray[0].fileId()));
        } else throw new UtiliedBotExceptions("Error while processing photo");
    }

    protected void handleVideo(Message message, TelegramBot telegramBot) throws UtiliedBotExceptions {
        Video inputVideo = message.video();
        if (inputVideo.fileSize() != null) {
            telegramBot.execute(new SendVideo(message.chat().id(), inputVideo.fileId()));
        } else throw new UtiliedBotExceptions("Error while handling video");
    }
}
