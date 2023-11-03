package com.ru.configuration.utiliedBot.service.messages;

import com.ru.configuration.utiliedBot.service.TypeInterface;

import java.util.Collection;

public interface MultipleTypesMessageInterface<T extends TypeInterface> extends MessageInterface<T> {
    String getByAllTypes(Collection<T> type);
}
