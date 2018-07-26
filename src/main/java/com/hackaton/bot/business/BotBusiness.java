package com.hackaton.bot.business;

import java.util.ArrayList;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

/**
 * BotBusiness.
 *
 * @author lballena.
 */
public class BotBusiness {

  public String saludar(Update update, String botUsername) {
    return String.format("Hola %s %s, soy %s y he venido a ayudarte con tus tasas.",
            update.getMessage().getChat().getFirstName(),
            update.getMessage().getChat().getLastName(),
            botUsername);
  }

  public SendMessage pedirPermsisoCelular(SendMessage sendMessage, Update update) {
    sendMessage = new SendMessage()
            .setChatId(update.getMessage().getChatId())
            .setText("Disculpa, para poder brindarte un mejor servicio, favor de brindarme " +
                    "permisos de tu celular");
    KeyboardRow row = new KeyboardRow();
    KeyboardButton keyboardButton = new KeyboardButton("Enviar mi número telefonico");
    keyboardButton.setRequestContact(true);
    row.add(keyboardButton);
    ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
    markup.setResizeKeyboard(true);
    ArrayList<KeyboardRow> rows = new ArrayList<>();
    rows.add(row);
    markup.setKeyboard(rows);
    sendMessage.setReplyMarkup(markup);
    return sendMessage;
  }


}
