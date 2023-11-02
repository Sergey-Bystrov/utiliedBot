package com.ru.configuration.utiliedBot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * type:<br>
 * Макулатура - 0, Пластик - 1, Плёнка - 2<br>
 * Default measure - кг, default currency - руб.
 */
@Getter
@AllArgsConstructor
public enum Price {
    /** WASTE PAPER */
    CARDBOARD(0, "Картон", BigDecimal.valueOf(5f)),
    OFFICE_PAPER(0, "Офисная бумага", BigDecimal.valueOf(8f)),
    BOOKS_ETC(0, "Книги, газеты, журналы", BigDecimal.valueOf(6f)),

    /** PLASTIC */
    BOTTLE(1, "Бутылки", BigDecimal.valueOf(12f)),
    HOUSEHOLD_CHEMICAL_BOTTLES(1, "Флаконы из под бытовой химии", BigDecimal.valueOf(12f)),
    CANS_BARRELS(1, "Канистры и бочки", BigDecimal.valueOf(12f)),
    LID(1, "Крышечки", BigDecimal.valueOf(15f)),

    /** MEMBRANE */
    STRATCH(2, "Стрейч", BigDecimal.valueOf(15f)),
    COLORED_MEMBRANE(2, "Цветная", BigDecimal.valueOf(12f)),
    PVD(2, "ПВД", BigDecimal.valueOf(15f)),
    ;

    private Integer type;
    private String title;
    private BigDecimal amount;
    private String measure;
    private String currency;

    Price(int type, String title, BigDecimal amount) {
        this.type = type;
        this.title = title;
        this.amount = amount;
        this.measure = "кг";
        this.currency = "руб.";
    }
}
