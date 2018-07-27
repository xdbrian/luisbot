package com.hackaton.bot.controller;

import com.hackaton.bot.Flow.Funtionary;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * MyAmazingBot.
 *
 * @author lballena.
 */
public class MyAmazingBot extends TelegramLongPollingBot {

  private static final Logger LOGGER = LoggerFactory.getLogger(MyAmazingBot.class);


  private String matricula = "";
  Boolean isSaveFuntionary = false;

  Funtionary funtionary = new Funtionary();
  @Override
  public void onUpdateReceived(Update update)
  {
    final SendMessage msg = new SendMessage();

    if (update.hasMessage() && update.getMessage().hasText())
    {

      final Message message = update.getMessage();
      ReplyKeyboard markup = null;

      if(isSaveFuntionary){
        this.matricula = message.getText();
        isSaveFuntionary = false;
      }else
      if (message.getText().startsWith("/soybcp")){
        msg.setText("por favor ingresa tu funcionario");

      }
      else if (message.getText().startsWith("tasa"))
      {
        markup = new InlineKeyboardMarkup();
        final List<List<InlineKeyboardButton>> keyboard = ((InlineKeyboardMarkup) markup).getKeyboard();
        for (int i = 0; i < 9; i++)
        {
          if (keyboard.isEmpty() || (keyboard.get(keyboard.size() - 1).size() >= 3))
          {
            keyboard.add(new ArrayList<>());
          }
          keyboard.get(keyboard.size() - 1).add(new InlineKeyboardButton().setText("🔘 Button name #" + (i + 1)).setCallbackData("Button callback " + (i + 1)));
        }
      }
      else if (message.getText().startsWith("/keyboard"))
      {
        markup = new ReplyKeyboardMarkup();
        final List<KeyboardRow> keyboard = ((ReplyKeyboardMarkup) markup).getKeyboard();
        for (int i = 0; i < 9; i++)
        {
          if (keyboard.isEmpty() || (keyboard.get(keyboard.size() - 1).size() >= 3))
          {
            keyboard.add(new KeyboardRow());
          }
          keyboard.get(keyboard.size() - 1).add(new KeyboardButton().setText("🔘 Button #" + (i + 1)));
        }
      }
      else if (message.getText().startsWith("/funtionary")) {
       new Funtionary().getFuntionaryInfo(this,message);
      }




      msg.setChatId(Long.toString(message.getChat().getId()));
      //msg.setText("Your text here");
      msg.setReplyToMessageId(message.getMessageId());
      msg.setReplyMarkup(markup);
      try
      {
        execute(msg);
      }
      catch (TelegramApiException e)
      {
        LOGGER.warn("Failed to execute SendMessage: ", e);
      }
    }
    else if (update.hasCallbackQuery())
    {
      final AnswerCallbackQuery answer = new AnswerCallbackQuery();
      answer.setCallbackQueryId(update.getCallbackQuery().getId());
      answer.setText("You've clicked at the button: " + update.getCallbackQuery().getData());
      answer.setShowAlert(true);
      try
      {
        execute(answer);
      }
      catch (Exception e)
      {
        LOGGER.warn("Failed to execute AnswerCallbackQuery: ", e);
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
    return "686336894:AAEAlhqRqmg9JEJ0WIhuM6AY5VYkBr4O7VQ";
  }

}
