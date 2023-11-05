package com.ru.configuration.utiliedBot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import com.ru.configuration.utiliedBot.repository.PricesLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class TelegrammBotConfig {
    @Bean
    public TelegramBot adminTelegramBot(@Value("${admin.bot.token:}") String botToken) {
        return new TelegramBot(botToken);
    }

    @Bean
    public String adminBotChatId(@Value("${admin.bot.chat.id:}") String chatId) {
        return chatId;
    }

    @Bean
    public TelegramBot userTelegramBot(@Value("${user.bot.token:}") String botToken) {
        return new TelegramBot(botToken);
    }

    @Bean
    public String userBotChatId(@Value("${user.bot.chat.id:}") String chatId) {
        return chatId;
    }

/*    @Bean
    public PricesLoader pricesLoader(){return new PricesLoader();}*/
}
