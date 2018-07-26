package com.hackaton.bot;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * MyAmazingBot.
 *
 * @author lballena.
 */
public class MyAmazingBot extends TelegramLongPollingBot {

  @Override
  public void onUpdateReceived(Update update) {
    if (update.hasMessage() && update.getMessage().hasText()) {
      SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
              .setChatId(update.getMessage().getChatId())
              .setText(update.getMessage().getText());
      try {
        execute(message); // Call method to send the message
      } catch (TelegramApiException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public String getBotUsername() {
    // TODO
    return "Luis21Bot";
  }

  @Override
  public String getBotToken() {
    return "680599289:AAF9mRhmMiSWxyFjZqQIaREljaiWwtcDsXE";
  }

}
