package com.hackaton.bot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackaton.bot.business.BotBusiness;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.hackaton.bot.business.ConsultaDNI;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
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
public class MyAmazingBot extends TelegramLongPollingBot {

  private BotBusiness botBusiness = new BotBusiness();

  private Map<Long, BotMemory> idChats = new HashMap();

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
          message = botBusiness.pedirPermsisoCelular(message, update);
        } else if (!botMemory.isDni()){
          String dni = update.getMessage().getText();
          System.out.println("---------------------------- DNI ?????? : " + dni);
          String url = "http://192.168.43.188:8080/consultarDNI?dni="+dni;
          RestTemplate restTemplate = new RestTemplate();
          ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
          String bodyResponse = response.getBody();
          ObjectMapper mapper = new ObjectMapper();
          ConsultaDNI consultaDNI = null;
          try {
            consultaDNI = mapper.readValue(bodyResponse.getBytes(), ConsultaDNI.class);
          } catch (IOException e) {
            e.printStackTrace();
          }
          if(consultaDNI.getEstadoHTTP().equalsIgnoreCase("200")){
            botMemory.setDni(true);
            message = new SendMessage() // Create a SendMessage object with mandatory fields
                    .setChatId(update.getMessage().getChatId())
                    .setText("Su DNI ha sido reconocido como existente. ¿De qué manera lo puedo ayudar el día de hoy?");
          } else {
            message = new SendMessage() // Create a SendMessage object with mandatory fields
                    .setChatId(update.getMessage().getChatId())
                    .setText("Su DNI no existe o fue ingresado incorrectamente. Por favor, volver a ingresarlo.");
          }
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
        message = botBusiness.solicitarDocumentoIdentidad(message, update);
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
