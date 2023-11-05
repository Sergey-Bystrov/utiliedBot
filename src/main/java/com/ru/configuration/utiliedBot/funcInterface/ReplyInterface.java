package com.ru.configuration.utiliedBot.funcInterface;

import com.pengrad.telegrambot.request.SendMessage;
import com.ru.configuration.utiliedBot.model.ButtonModel;
import com.ru.configuration.utiliedBot.service.action.ScreenKeyboardMarkup;

/**
 * Функциональный интерфейс для реализации реакции на нажатие конкретной кнопки
 * @param <T> - Объект разметки клавиатуры
 */
@FunctionalInterface
public interface ReplyInterface<T extends ScreenKeyboardMarkup<String, ButtonModel>> {
    /**
     * Метод вызывает объект разметки клавиатуры, который возвращает объект ответа SendMessage для chatId
     * с сообщением message
     */
    SendMessage get(long chatId, String message, T markup);
}
