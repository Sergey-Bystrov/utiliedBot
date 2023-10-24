package com.ru.configuration.utiliedBot;

import com.ru.configuration.utiliedBot.bot.botImplementation.UtiliedBot;
import com.ru.configuration.utiliedBot.bot.botImplementation.UtiliedBotStarter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class UtiliedBotApplication {

	@Autowired
	private UtiliedBotStarter utiliedBotStarter;

	public static void main(String[] args) {
		SpringApplication.run(UtiliedBotApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void runBot() {
		utiliedBotStarter.start();
	}


}
