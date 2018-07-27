package com.hackaton.bot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * Application.
 *
 * @author lballena.
 */
public class Application {

  public static void main(String args[]) {
    ApiContextInitializer.init();

    TelegramBotsApi botsApi = new TelegramBotsApi();

    try {
      botsApi.registerBot(new FlujoClienteBot());
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }

}
