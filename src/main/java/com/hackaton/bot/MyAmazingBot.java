package com.hackaton.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * MyAmazingBot.
 *
 * @author lballena.
 */
public class MyAmazingBot extends TelegramLongPollingBot {


  @Autowired
  Funtionary funtionary;

  @Override
  public void onUpdateReceived(Update update) {

    SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
            .setChatId(update.getMessage().getChatId());

    if (update.hasMessage() && update.getMessage().hasText()) {
      final Message msn = update.getMessage();

      if (msn.getText().startsWith("/soybcp")){
        funtionary.initBcp(msn,message);
      }else  if(funtionary.getSaveFuntionary()){
        funtionary.saveFuntionary(msn,message);
      }else  if(funtionary.getSavePassword()) {
        funtionary.savePassword(msn,message);
      }

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
    return "Brian";
  }

  @Override
  public String getBotToken() {
    return "686336894:AAEAlhqRqmg9JEJ0WIhuM6AY5VYkBr4O7VQ";
  }

}
