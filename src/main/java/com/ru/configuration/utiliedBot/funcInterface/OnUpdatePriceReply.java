package com.ru.configuration.utiliedBot.funcInterface;

import com.pengrad.telegrambot.request.SendMessage;
import com.ru.configuration.utiliedBot.model.ButtonModel;
import com.ru.configuration.utiliedBot.service.action.ScreenKeyboardMarkup;

/**
 * Функциональный интерфейс callback-функции, вызывающейся после обновления прайса
 * @param <T> - Объект разметки клавиатуры
 */
@FunctionalInterface
public interface OnUpdatePriceReply<T extends ScreenKeyboardMarkup<String, ButtonModel>> {
    SendMessage get(long chatId, T markup);
}
