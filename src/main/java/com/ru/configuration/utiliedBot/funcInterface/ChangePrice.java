package com.ru.configuration.utiliedBot.funcInterface;

import com.ru.configuration.utiliedBot.repository.PricesLoader;

/**
 * Функциональный интерфейс обновления прайса
 */
@FunctionalInterface
public interface ChangePrice {
    void update(PricesLoader loader, float value);
}
