package com.ru.configuration.utiliedBot.service.action;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.*;
import com.ru.configuration.utiliedBot.enums.AdminTelegramCommands;
import com.ru.configuration.utiliedBot.enums.Catalog;
import com.ru.configuration.utiliedBot.enums.Price;
import com.ru.configuration.utiliedBot.enums.UtilType;
import com.ru.configuration.utiliedBot.exceptions.UtiliedBotExceptions;
import com.ru.configuration.utiliedBot.repository.PricesLoader;
import com.ru.configuration.utiliedBot.service.messages.MultipleTypesMessageInterface;
import com.ru.configuration.utiliedBot.utils.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AdminBotAction extends BaseBotAction {
    private TelegramBot adminTelegramBot;
    private String adminBotChatId;
    private AdminMarkup adminMarkup;
    private MultipleTypesMessageInterface<UtilType> priceMessageService;
    private PricesLoader pricesLoader;

    private final List<AdminTelegramCommands> telegramCommands = Arrays.stream(AdminTelegramCommands.values())
            .collect(Collectors.toList());
    private final List<Catalog> catalog = Arrays.stream(Catalog.values())
            .collect(Collectors.toList());
    private final List<String> catalogCommands = catalog.stream()
            .map(Catalog::getCommand)
            .collect(Collectors.toList());
    private final List<String> infoCommands = telegramCommands.stream()
            .map(AdminTelegramCommands::getCommand)
            .collect(Collectors.toList());
    private final List<String> priceChangeCommands =  Arrays.stream(Price.values())
            .map(Price::getTitle)
            .collect(Collectors.toList());

    private Price toChange;

    public AdminBotAction() {

    }

    @Autowired
    public AdminBotAction(TelegramBot adminTelegramBot, String adminBotChatId,
                          MultipleTypesMessageInterface<UtilType> priceMessageService,
                          AdminMarkup adminMarkup, PricesLoader pricesLoader) {
        this.adminTelegramBot = adminTelegramBot;
        this.adminBotChatId = adminBotChatId;
        this.priceMessageService = priceMessageService;
        this.adminMarkup = adminMarkup;
        this.pricesLoader = pricesLoader;
        super.setAdminTelegramBot(adminTelegramBot);
    }

    @Override
    protected SendMessage getStartScreenReplyMarkup(long chatId, String text) {
        return new SendMessage(chatId, text)
                .replyMarkup(adminMarkup.getReplyKeyboardMarkup(new String[]{command.CHANGE_PRISE}));
    }

    @Override
    protected SendMessage getStartKeyboard(long chatId, String text) {
        Optional<SendMessage> result = Optional.empty();
        if (infoCommands.contains(text)) {
            result = getReplyForAdminInfoCommand(chatId, text);
        } else if (catalogCommands.contains(text)) {
            result = getReplyForCatalogCommand(chatId, text);
        } else if (priceChangeCommands.contains(text)) {
            result = getReplyForAdminChangePriceCommand(chatId, text);
        } else if (TextUtils.isNumeric(text) && toChange != null) {
            result = updatePriceAndGetReplyForAdmin(chatId, text);
        }
        return result.orElseGet(() -> getStartScreenReplyMarkup(chatId, "NOT_FOUND"));
    }

    /**
     * Метод проверяет, является ли ответ пользователя одним из типов отходов UtilType. Если является, то
     * с помощью сервиса <b>priceMessageService</b> в переменную message заносится сообщение с прайсом для этой категории
     * отходов, иначе текст ответа = тексту введенной команды.
     * Далее происходит установка actualMessage (является final-переменной, т.к. не изменяется, а стримы требуют для
     * использования внутри неизменных переменных) в объект ответа SendMessage
     * @param chatId - id чата, в который необходимо отдать ответ
     * @param text - текст сообщения, полученного от пользователя
     * @return Optional<SendMessage> - Объект ответа пользователю
     */
    private Optional<SendMessage> getReplyForAdminInfoCommand(long chatId, String text) {
        String message = text;
        UtilType type = UtilType.getTypeByTitle(text);
        if (type != null) {
            message = priceMessageService.getByType(type);
        }
        String actualMessage = message;

        return telegramCommands.stream()
                .filter(c -> c.getCommand().equals(actualMessage))
                .map(c -> c.getReply().get(chatId, actualMessage, adminMarkup))
                .findFirst();
    }

    /**
     * Метод с помощью сервиса <b>priceMessageService</b> в переменную message заносит сообщение с прайсом для категории
     * отходов.
     * @param chatId - id чата, в который необходимо отдать ответ
     * @param text - текст сообщения, полученного от пользователя
     * @return Optional<SendMessage> - Объект ответа пользователю
     */
    private Optional<SendMessage> getReplyForCatalogCommand(long chatId, String text) {
        return catalog.stream()
                .filter(c -> c.getCommand().equals(text))
                .map(c -> c.getReply().get(chatId, text, adminMarkup))
                .findFirst();
    }

    /**
     * Метод создает стрим по прайсу, ищет совпадение по title, сохраняет объект для изменения цены в оперативной памяти
     * и отправляет сообщение пользователю о необходимости ввести число и кнопкой для возврата
     * @param chatId - id чата, в который необходимо отдать ответ
     * @param text - текст сообщения, полученного от пользователя
     * @return Optional<SendMessage> - Объект ответа пользователю
     */
    private Optional<SendMessage> getReplyForAdminChangePriceCommand(long chatId, String text) {
        return Arrays.stream(Price.values())
                .filter(p -> p.getTitle().equalsIgnoreCase(text))
                .peek(p -> toChange = p)
                .map(p -> p.getReply().get(chatId, "Введите стоимость", adminMarkup))
                .findFirst();
    }

    /**
     * Метод создает стрим по прайсу, ищет совпадение по title, изменяет цену и возвращает ответ пользователю об успешном
     * изменении цены
     * @param chatId - id чата, в который необходимо отдать ответ
     * @param text - текст сообщения, полученного от пользователя
     * @return Optional<SendMessage> - Объект ответа пользователю
     */
    private Optional<SendMessage> updatePriceAndGetReplyForAdmin(long chatId, String text) {
        return Arrays.stream(Price.values())
                .filter(p -> p.getTitle().equalsIgnoreCase(toChange.getTitle()))
                .map(p -> {
                    p.getChangePriceFunction().update(pricesLoader, Float.parseFloat(text));
                    pricesLoader.saveDataToFile();
                    return p.getOnUpdatePriceReply().get(chatId, adminMarkup);
                })
                .findFirst();
    }

    protected void handlePhoto(File file, String contact) throws UtiliedBotExceptions {
        if (file != null) {
            adminTelegramBot.execute(new SendPhoto(adminBotChatId, file));
            adminTelegramBot.execute(new SendMessage(adminBotChatId, contact));
            //adminTelegramBot.execute(new SendContact(adminBotChatId, userContact.getPhoneNumber(), userContact.getFirstName()));
        } else throw new UtiliedBotExceptions("Error while processing photo");
    }
    //todo тут падал в runtime exception
    protected void handleVideo(File file, String contact) throws UtiliedBotExceptions {
        if (file != null) {
            adminTelegramBot.execute(new SendVideo(adminBotChatId, file));
            adminTelegramBot.execute(new SendMessage(adminBotChatId, contact));
        } else throw new UtiliedBotExceptions("Error while processing video");
    }

    protected void handleVideoNote(File file, String contact) throws UtiliedBotExceptions {
        if (file != null) {
            adminTelegramBot.execute(new SendVideoNote(adminBotChatId, file));
            adminTelegramBot.execute(new SendMessage(adminBotChatId, contact));
        } else throw new UtiliedBotExceptions("Error while processing videoNote");
    }


}
