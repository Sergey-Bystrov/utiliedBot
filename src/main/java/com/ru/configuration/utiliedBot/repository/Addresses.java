package com.ru.configuration.utiliedBot.repository;

import java.util.Map;

import static com.ru.configuration.utiliedBot.enums.Location.*;

public class Addresses {
    /**
     * Map for addresses url
     * <br>default - Кострома
     * */
    public static Map<String, String> addressesYandexMap = Map.of(
            DEFAULT.getName(), "https://yandex.ru/maps/-/CDawb-zd",
            KOSTROMA.getName(), "https://yandex.ru/maps/-/CDawbPpn",
            SHARYA.getName(), "https://yandex.ru/maps/-/CDawbP1H"

    );
}
