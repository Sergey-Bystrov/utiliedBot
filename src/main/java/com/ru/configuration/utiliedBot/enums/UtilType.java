package com.ru.configuration.utiliedBot.enums;

import com.ru.configuration.utiliedBot.service.TypeInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UtilType implements TypeInterface {
    WASTE_PAPER("Макулатура"),
    PLASTIC("Пластик"),
    MEMBRANE("Плёнка"),
    ;

    private String title;
}
