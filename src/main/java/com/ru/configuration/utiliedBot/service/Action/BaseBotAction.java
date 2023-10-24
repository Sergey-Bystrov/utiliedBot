package com.ru.configuration.utiliedBot.service.Action;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.GetMe;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetMeResponse;
import com.ru.configuration.utiliedBot.bot.botinterface.BaseBotActions;
import com.ru.configuration.utiliedBot.bot.botinterface.ScreenReplyMarkup;
import com.ru.configuration.utiliedBot.service.Log.Loger;
import org.springframework.beans.factory.annotation.Value;

public class BaseBotAction extends ScreenReplyMarkup implements BaseBotActions {
    private TelegramBot telegramBot;
    private String botToken;
    private String stringChatId;

    public BaseBotAction(TelegramBot telegramBot, String botToken, String stringChatId) {
        this.telegramBot = telegramBot;
        this.botToken = botToken;
        this.stringChatId = stringChatId;
    }

    public void checkBotStatus() {
        GetMe getMe = new GetMe();
        GetMeResponse botUser = telegramBot.execute(getMe);
        Loger.logInfo("Bot username: " + botUser.toString());
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

    protected void getStartKeyboard(){
        //todo наша реализация
    }

    protected SendMessage handleUpdates(com.pengrad.telegrambot.model.Update update) {
        return super.getStartKeyboard(update.message().chat().id(), update.message().text());
    }


}
