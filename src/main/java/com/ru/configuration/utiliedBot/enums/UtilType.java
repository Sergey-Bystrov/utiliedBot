package com.ru.configuration.utiliedBot.enums;

import com.ru.configuration.utiliedBot.service.TypeInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum UtilType implements TypeInterface {
    WASTE_PAPER("Макулатура"),
    PLASTIC("Пластик"),
    MEMBRANE("Плёнка"),
    ;

    private String title;

    public static UtilType getTypeByTitle(String text) {
        return Arrays.stream(UtilType.values())
                .filter(t -> t.getTitle().equalsIgnoreCase(text))
                .findFirst().orElse(null);
    }

    public static UtilType getTypeById(Integer typeId) {
        return Arrays.stream(UtilType.values())
                .filter(t -> typeId.equals(t.ordinal()))
                .findFirst().orElse(null);
    }
}
