package com.ru.configuration.utiliedBot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

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
    CARDBOARD(0, "Картон", "cardboard"),
    OFFICE_PAPER(0, "Офисная бумага", "officePaper"),
    BOOKS_ETC(0, "Книги, газеты, журналы", "booksEtc"),

    /** PLASTIC */
    BOTTLE(1, "Бутылки", "bottle"),
    HOUSEHOLD_CHEMICAL_BOTTLES(1, "Флаконы из под бытовой химии", "householdChemicalBottles"),
    CANS_BARRELS(1, "Канистры и бочки", "cansBarrels"),
    LID(1, "Крышечки", "lid"),

    /** MEMBRANE */
    STRATCH(2, "Стрейч", "stratch"),
    COLORED_MEMBRANE(2, "Цветная", "coloredMembrane"),
    PVD(2, "ПВД", "pvd"),
    ;

    private Integer type;
    private String title;
    private String name;
    private String measure;
    private String currency;

    Price(int type, String title, String name) {
        this.type = type;
        this.title = title;
        this.name = name;
        this.measure = "кг";
        this.currency = "руб.";
    }
}
