package com.ru.configuration.utiliedBot.service.action;

import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;

/**
 * Интерфейс разметки клавиатуры
 * @param <T> - тип кнопок
 * @param <B> - Модель кнопки
 */
public interface ScreenKeyboardMarkup<T, B> {
    ReplyKeyboardMarkup getStartKeyboardMarkup();
    ReplyKeyboardMarkup getReplyKeyboardMarkup(T[] buttons);
    InlineKeyboardMarkup getInlineKeyboardMarkup(B[] buttons);
}
