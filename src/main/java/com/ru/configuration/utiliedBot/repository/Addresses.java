package com.ru.configuration.utiliedBot.repository;

import java.util.Map;

public class Addresses {
    /**
     * Map for addresses url
     * default - Кострома
     * */
    public static Map<String, String> addressesYandexMap = Map.of(
            "default", "https://yandex.ru/maps/-/CDawb-zd",
            "Кострома", "https://yandex.ru/maps/-/CDawbPpn",
            "Шарья", "https://yandex.ru/maps/-/CDawbP1H"

    );
}
