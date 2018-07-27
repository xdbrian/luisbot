package com.hackaton.bot.business;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackaton.bot.BotMemory;
import com.hackaton.bot.FlujoClienteBot;
import com.hackaton.bot.NameStepFlows;
import java.io.IOException;
import java.util.ArrayList;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
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

  private String urlHost = "http://192.168.43.188";

  public String saludar(Update update, String botUsername) {
    return String.format("Hola %s %s, soy soy Trinity la asistente virtual de Zbank y estoy " +
                    "aquí para ayudarte con la información que necesitas sobre tus tasas y asi " +
                    "mejorar tu experiencia.",
            update.getMessage().getChat().getFirstName(),
            update.getMessage().getChat().getLastName(),
            botUsername);
  }

  public SendMessage pedirPermsisoCelular(Message messageRq) {

    SendMessage sendMessage = new SendMessage()
            .setChatId(messageRq.getChatId())
            .setText("Para ello es necesario que pueda conocer tu número celular presionando el " +
                    "siguiente botón:");

    KeyboardRow row = new KeyboardRow();
    KeyboardButton keyboardButton = new KeyboardButton("Enviar mi número telefónico");
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

  public SendMessage solicitarDocumentoIdentidad(Message messageRq) {
    SendMessage message = new SendMessage()
            .setChatId(messageRq.getChatId())
            .setText(String.format("Lo tengo! %s ahora necesito que ingreses el número de " +
                    "doc. De identidad para identificarte.",messageRq.getChat()
                    .getFirstName()));
    return message;
  }

  public Boolean  validarDocumentoIdentidad(FlujoClienteBot flujoClienteBot,BotMemory botMemory,
                                            Update update) {
    SendMessage message = null;
    String dni = update.getMessage().getText();
    System.out.println("---------------------------- DNI : " + dni);
    String url = String.format("%s:8080/consultarDNI?dni=%s",urlHost,dni);
//    RestTemplate restTemplate = new RestTemplate();
//    ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
//    String bodyResponse = response.getBody();
//    ObjectMapper mapper = new ObjectMapper();
    ConsultaDNI consultaDNI = new ConsultaDNI();
    consultaDNI.estadoHTTP = "200";
//    try {
//      consultaDNI = mapper.readValue(bodyResponse.getBytes(), ConsultaDNI.class);
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
    if (consultaDNI.getEstadoHTTP().equalsIgnoreCase("200")) {
      botMemory.setDni(true);
      message = new SendMessage() // Create a SendMessage object with mandatory fields
              .setChatId(update.getMessage().getChatId())
              .setText("Su DNI ha sido reconocido como existente. ¿De qué manera lo puedo ayudar el día de hoy?");
      botMemory.setStepFlowsCross(NameStepFlows.OFRECER_SERVICIOS);

      flujoClienteBot.executeMessage(message);
      return true;
    } else {
      message = new SendMessage() // Create a SendMessage object with mandatory fields
              .setChatId(update.getMessage().getChatId())
              .setText("Su DNI no existe o fue ingresado incorrectamente. Por favor, volver a ingresarlo.");
      flujoClienteBot.executeMessage(message);
    }
    return false;
  }


  public void solicitarRuc(FlujoClienteBot flujoClienteBot,BotMemory botMemory, Message messageRq) {
  SendMessage message = new SendMessage()
            .setChatId(messageRq.getChatId())
            .setText(String.format("Muy bien! %s ahora necesito que ingreses el RUC de la empresa" +
                    " para validar que tasas tiene disponible la empresa.",messageRq
                    .getChat()
                    .getFirstName()));
    flujoClienteBot.executeMessage(message);
  }

  public void respuestaSolicitarRuc(FlujoClienteBot flujoClienteBot,BotMemory botMemory, Message messageRq) {
    SendMessage message = new SendMessage()
            .setChatId(messageRq.getChatId())
            .setText(String.format("Listo! se han validado los datos de tu empresa",messageRq
                    .getChat()
                    .getFirstName()));
    flujoClienteBot.executeMessage(message);
  }


}
