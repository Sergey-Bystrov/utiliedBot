package com.ru.configuration.utiliedBot.enums;

import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.ru.configuration.utiliedBot.funcInterface.ChangePrice;
import com.ru.configuration.utiliedBot.funcInterface.OnUpdatePriceReply;
import com.ru.configuration.utiliedBot.funcInterface.ReplyInterface;
import com.ru.configuration.utiliedBot.service.action.AdminMarkup;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * type:<br>
 * Макулатура - 0, Пластик - 1, Плёнка - 2<br>
 * Default measure - кг, default currency - руб.<br>
 * В статическом блоке инициализируются callback-функции (функциональные интерфейсы) для каждого объекта прайса.<br><br>
 * Param <b><i>changePriceFunction</i></b> - функция, которая изменяет цену для текущего объекта<br><br>
 * Param <b><i>reply</i></b> - функция, возвращающая объект ответа <i>SendMessage</i> для пользователя <br><br>
 * Param <b><i>onUpdatePriceReply</i></b> - функция, которая вызывается после изменения стоимости объекта. Возвращает информационное
 * сообщение и объект клавиатуры с кнопкой <b>"Назад"</b><br><br>
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


    static {
        Arrays.stream(Price.values())
                .forEach(p -> {
                    p.setChangePriceFunction((pricesLoader, value) -> pricesLoader.putValue(p.getName(), value));
                    p.setReply((chatId, message, markup) -> new SendMessage(chatId, message)
                            .replyMarkup(markup.getReplyKeyboardMarkup(
                                    new String[]{AdminTelegramCommands.BACK.getCommand()}
                            )));
                    UtilType type = UtilType.getTypeById(p.getType());
                    String onUpdateReply = "Обновлена стоимость для позиции" +
                            "\n" + "*" + p.getTitle() + "* " + type.getTitle() + "." + "\s";
                    p.setOnUpdatePriceReply((chatId, markup) -> new SendMessage(chatId, onUpdateReply)
                            .parseMode(ParseMode.Markdown)
                            .replyMarkup(markup.getReplyKeyboardMarkup(
                                    new String[]{AdminTelegramCommands.CHANGE_PRISE.getCommand()}
                            )));
                });

    }

    private Integer type;
    private String title;
    private String name;
    private String measure;
    private String currency;
    @Setter private ChangePrice changePriceFunction;
    @Setter private ReplyInterface<AdminMarkup> reply;
    @Setter private OnUpdatePriceReply<AdminMarkup> onUpdatePriceReply;

    Price(int type, String title, String name) {
        this.type = type;
        this.title = title;
        this.name = name;
        this.measure = "кг";
        this.currency = "руб";
    }

    Price(int type, String title, String name, ReplyInterface<AdminMarkup> reply) {
        this.type = type;
        this.title = title;
        this.name = name;
        this.measure = "кг";
        this.currency = "руб";
        this.reply = reply;;
    }

    Price(Integer type, String title, String name, ChangePrice changePriceFunction, ReplyInterface<AdminMarkup> reply) {
        this.type = type;
        this.title = title;
        this.name = name;
        this.measure = "кг";
        this.currency = "руб";
        this.changePriceFunction = changePriceFunction;
        this.reply = reply;
    }

    public static List<Price> getWastePaper() {
        return Arrays.stream(Price.values())
                .filter(e -> e.getType().equals(0))
                .collect(Collectors.toList());
    }

    public static List<Price> getPlastic() {
        return Arrays.stream(Price.values())
                .filter(e -> e.getType().equals(1))
                .collect(Collectors.toList());
    }

    public static List<Price> getMembrane() {
        return Arrays.stream(Price.values())
                .filter(e -> e.getType().equals(2))
                .collect(Collectors.toList());
    }

    public static List<Price> getAll() {
        return Arrays.stream(Price.values())
                .collect(Collectors.toList());
    }
}
