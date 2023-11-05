package com.ru.configuration.utiliedBot.service.action;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.*;
import com.pengrad.telegrambot.request.*;
import com.pengrad.telegrambot.response.GetFileResponse;
import com.pengrad.telegrambot.response.GetMeResponse;
import com.ru.configuration.utiliedBot.bot.botinterface.BaseBotActions;
import com.ru.configuration.utiliedBot.bot.botinterface.ScreenReplyMarkup;
import com.ru.configuration.utiliedBot.constants.Errors;
import com.ru.configuration.utiliedBot.constants.TelegrammCommands;
import com.ru.configuration.utiliedBot.exceptions.UtiliedBotExceptions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

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

    protected void savePhoto(Message inputPhoto, TelegramBot telegramBot){
        int num = inputPhoto.photo().length - 1;
        GetFile getFile = new GetFile(inputPhoto.photo()[num].fileId());
        GetFileResponse file = telegramBot.execute(getFile);
        try {
            byte[] fileBytes = telegramBot.getFileContent(file.file());
            FileUtils.writeByteArrayToFile(new File(TelegrammCommands.PHOTO_PATH), fileBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void saveVideo(Message inputVideo, TelegramBot telegramBot){
        GetFile getFile = new GetFile(inputVideo.video().fileId());
        GetFileResponse file = telegramBot.execute(getFile);
        try {
            byte[] fileBytes = telegramBot.getFileContent(file.file());
            FileUtils.writeByteArrayToFile(new File(TelegrammCommands.VIDEO_PATH), fileBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void saveVideoNote(Message inputVideo, TelegramBot telegramBot){
        GetFile getFile = new GetFile(inputVideo.videoNote().fileId());
        GetFileResponse file = telegramBot.execute(getFile);
        try {
            byte[] fileBytes = telegramBot.getFileContent(file.file());
            FileUtils.writeByteArrayToFile(new File(TelegrammCommands.VIDEO_PATH), fileBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void handleVideo(Message message, TelegramBot telegramBot) throws UtiliedBotExceptions {
        Video inputVideo = message.video();
        if (inputVideo.fileSize() != null) {
            telegramBot.execute(new SendVideo(message.chat().id(), inputVideo.fileId()));
        } else throw new UtiliedBotExceptions("Error while handling video");
    }

    protected String getUserReference(Message message){
        return "@".concat(message.chat().username());
    }

    protected void handleVideoNote(Message message, TelegramBot telegramBot) throws UtiliedBotExceptions {
        VideoNote inputVideoNote = message.videoNote();
        if (inputVideoNote.fileSize() != null) {
            telegramBot.execute(new SendVideoNote(message.chat().id(), inputVideoNote.fileId()));
        } else throw new UtiliedBotExceptions("Error while handling video");
    }
}
