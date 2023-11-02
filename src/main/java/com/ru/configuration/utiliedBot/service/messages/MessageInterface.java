package com.ru.configuration.utiliedBot.service.messages;

import com.ru.configuration.utiliedBot.service.TypeInterface;

public interface MessageInterface<T extends TypeInterface> {
    String getByType(T type);
}
