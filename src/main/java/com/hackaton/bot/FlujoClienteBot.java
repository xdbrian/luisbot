package com.hackaton.bot;

import com.hackaton.bot.business.BotBusiness;
import java.util.HashMap;
import java.util.Map;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * MyAmazingBot.
 *
 * @author lballena.
 */
public class FlujoClienteBot extends TelegramLongPollingBot {

  protected BotBusiness botBusiness = new BotBusiness();

  protected Map<Long, BotMemory> idChats = new HashMap();


  public FlujoClienteBot() {

  }

  @Override
  public void onUpdateReceived(Update update) {
    SendMessage message = null;
    if (update.hasMessage() && update.getMessage().hasText()) {
      if (idChats.get(update.getMessage().getChatId()) == null) {
        System.out.println("---------------------> ChatId: -->" + update.getMessage().getChatId());
        idChats.put(update.getMessage().getChatId(), new BotMemory());
        message = new SendMessage() // Create a SendMessage object with mandatory fields
                .setChatId(update.getMessage().getChatId())
                .setText(botBusiness.saludar(update, getBotUsername()));
      } else {
        BotMemory botMemory = idChats.get(update.getMessage().getChatId());
        if (StringUtils.isEmpty(botMemory.getTelephone())) {
          message = botBusiness.pedirPermsisoCelular(update);
        } else if (!botMemory.isDni()) {
          message = botBusiness.validarDocumentoIdentidad(botMemory,update);
        } else {
          message = new SendMessage() // Create a SendMessage object with mandatory fields
                  .setChatId(update.getMessage().getChatId())
                  .setText(update.getMessage().getText());
        }
      }
      executeMessage(message);
    } else {
      System.out.println("---------------------> Telephone: -->" + update.getMessage().getContact()
              .getPhoneNumber());
      BotMemory botMemory = idChats.get(update.getMessage().getChatId());
      message = botBusiness.solicitarDocumentoIdentidad(update);
      executeMessage(message);
      if (botMemory != null) {
        botMemory.setTelephone(update.getMessage().getContact().getPhoneNumber());
      }
      message = removeKeyBoard(update);
      executeMessage(message);
    }
  }

  @Override
  public String getBotUsername() {
    return "Luis21Bot";
  }

  @Override
  public String getBotToken() {
    return "680599289:AAF9mRhmMiSWxyFjZqQIaREljaiWwtcDsXE";
  }

  private void executeMessage(SendMessage message) {
    try {
      execute(message); // Call method to send the message
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }

  private SendMessage removeKeyBoard(Update update) {
    SendMessage message = new SendMessage();
    ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
    message.setReplyMarkup(markup);
    message.setChatId(update.getMessage().getChatId());
    return message;
  }

}
