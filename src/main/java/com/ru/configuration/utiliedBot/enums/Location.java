package com.ru.configuration.utiliedBot.enums;

public enum Location {
    KOSTROMA("Кострома"),
    SHARYA("Шарья"),
    DEFAULT("default")
    ;

    private final String name;

    Location(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
