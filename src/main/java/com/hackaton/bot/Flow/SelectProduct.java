package com.hackaton.bot.Flow;

import com.hackaton.bot.BotMemory;
import com.hackaton.bot.FlujoClienteBot;
import com.hackaton.bot.NameStepFlows;
import java.util.ArrayList;
import java.util.List;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import static com.hackaton.bot.NameStepFlows.OBTENER_TASA_ACTUAL;

/**
 * SelectProduct Description of class.
 * <b>Class</b>: SelectProduct<br/><b>Copyright</b>: &copy; 2017 Banco de Cr&eacute;dito del Per&uacute;<br/>
 * <b>Company</b>: Banco de Cr&eacute;dito del Per&uacute;<br/>
 * @author Banco de Cr&eacute;dito del Per&uacute; (BCP) <br/>
 * <u>Service Provider</u>: Everis Per&uacute; SAC (EVE) <br/>
 * <u>Developed by</u>: <br/><ul><li>Brian Steventh Torres Yañez (BTY) Everis (EVE)</li></ul>
 * <u>Changes</u>:<br/><ul><li>27/07/2018 16:24(BTY) Creaci&oacute;n de Clase.</li></ul>
 * @version 1.0
 */
public class SelectProduct {


  public void continueSelectProduct(FlujoClienteBot flujoClienteBot, Message messageRq) {
    BotMemory botMemory = flujoClienteBot.idChats.get(messageRq.getChatId());

    switch (botMemory.getStepFlowStepSelectProduct()) {
      case 0:
        this.stepSelectTasa(flujoClienteBot, messageRq);
        botMemory.setStepFlowStepSelectProduct(1);
        break;
      case 1:
        this.seleccionarProducto(flujoClienteBot, messageRq);
        botMemory.setStepFlowFuntionary(2);
        break;
      case 2:
        this.stepSolicitarMonto(flujoClienteBot, messageRq);
        botMemory.setStepFlowStepSelectProduct(3);
        break;
//      case 3:
//        this.stepSolicitarMonto(flujoClienteBot, messageRq);
//        botMemory.setStepFlowStepSelectProduct(4);
//        break;
      case 3:
        this.resspuestaSolicitarMonto(flujoClienteBot, messageRq);
        botMemory.setStepFlowStepSelectProduct(4);
        break;
      case 4:
        this.respuestaSeleccionPlazo(flujoClienteBot, messageRq);
        botMemory.setStepFlowStepSelectProduct(5);
        break;
      case 5:
        this.stepNegociar(flujoClienteBot, messageRq);
        botMemory.setStepFlowStepSelectProduct(6);
        break;
      case 6:
        botMemory.setStepFlowsCross(NameStepFlows.OFRECER_OTRA_COSA);
        botMemory.setStepFlowStepSelectProduct(0);
        flujoClienteBot.managerFlow.continueFlow(flujoClienteBot,messageRq);
        break;
    }

  }

  public void stepSelectTasa(FlujoClienteBot flujoClienteBot, Message messageRq) {
    String message = "estas son las opciones de información que puedo ofrecerte:";
    SendMessage sendMessage = new SendMessage() // Create a SendMessage object with mandatory fields
            .setChatId(messageRq.getChatId())
            .setText(message);

//    message = new SendMessage() // Create a message object object
//            .setChatId(update.getMessage().getChatId())
//            .setText("You send /start");
    InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
    List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
    List<InlineKeyboardButton> rowInline = new ArrayList<>();
    rowInline.add(new InlineKeyboardButton().setText("Tasa Actual").setCallbackData
            ("boton_tasa_actual"));
    rowInline.add(new InlineKeyboardButton().setText("Información de mi Funcionario")
            .setCallbackData("boton_info_funcionario"));
    // Set the keyboard to the markup
    rowsInline.add(rowInline);
    // Add it to the message
    markupInline.setKeyboard(rowsInline);
    sendMessage.setReplyMarkup(markupInline);
//    try {
//      execute(message); // Sending our message object to user
//    } catch (TelegramApiException e) {
//      e.printStackTrace();
//    }

    flujoClienteBot.executeMessage(sendMessage);
  }

  public void seleccionarProducto(FlujoClienteBot flujoClienteBot, Message messageRq) {
    String message = "Para ver tu tasa actual selecciona el producto en el que se quiere " +
            "financiar:";
    SendMessage sendMessage = new SendMessage() // Create a SendMessage object with mandatory fields
            .setChatId(messageRq.getChatId())
            .setText(message);

//    message = new SendMessage() // Create a message object object
//            .setChatId(update.getMessage().getChatId())
//            .setText("You send /start");
    InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
    List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
    List<InlineKeyboardButton> rowInline = new ArrayList<>();
    rowInline.add(new InlineKeyboardButton().setText("FEC Local").setCallbackData
            ("boton_FEC_Local"));
    rowInline.add(new InlineKeyboardButton().setText("FEC exrerior")
            .setCallbackData("boton_FEC_exrerior"));
    rowInline.add(new InlineKeyboardButton().setText("Autodesembolso")
            .setCallbackData("boton_Autodesembolso"));
    // Set the keyboard to the markup
    rowsInline.add(rowInline);
    // Add it to the message
    markupInline.setKeyboard(rowsInline);
    sendMessage.setReplyMarkup(markupInline);

    flujoClienteBot.executeMessage(sendMessage);
  }


  public void stepSolicitarMonto(FlujoClienteBot flujoClienteBot, Message messageRq) {
    SendMessage message = new SendMessage()
            .setChatId(messageRq.getChatId())
            .setText(("Muy bien! ahora coloca el monto a financiar \n (ejemplo:1000.00)."));
    flujoClienteBot.executeMessage(message);
  }

  public void resspuestaSolicitarMonto(FlujoClienteBot flujoClienteBot, Message messageRq) {
    String message = "Excelente! solo te queda elegir el plazo:";
    SendMessage sendMessage = new SendMessage() // Create a SendMessage object with mandatory fields
            .setChatId(messageRq.getChatId())
            .setText(message);

    InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
    List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
    List<InlineKeyboardButton> rowInline = new ArrayList<>();
    rowInline.add(new InlineKeyboardButton().setText("Plazo de 60 días ").setCallbackData
            ("boton_FEC_Local_plazo_60"));
    rowsInline.add(rowInline);

    rowInline = new ArrayList<>();
    rowInline.add(new InlineKeyboardButton().setText("Plazo de 90 días ").setCallbackData
            ("boton_FEC_Local_plazo_90"));
    rowsInline.add(rowInline);

    rowInline = new ArrayList<>();
    rowInline.add(new InlineKeyboardButton().setText("Plazo de 180 días ").setCallbackData
            ("boton_FEC_Local_plazo_180"));
    rowsInline.add(rowInline);

    rowInline = new ArrayList<>();
    rowInline.add(new InlineKeyboardButton().setText("Otro plazo").setCallbackData
            ("boton_FEC_Local_plazo_otro_plazo"));
    rowsInline.add(rowInline);

    // Set the keyboard to the markup
    // Add it to the message
    markupInline.setKeyboard(rowsInline);
    sendMessage.setReplyMarkup(markupInline);

    flujoClienteBot.executeMessage(sendMessage);
  }

  public void respuestaSeleccionPlazo(FlujoClienteBot flujoClienteBot, Message messageRq) {
    BotMemory botMemory = flujoClienteBot.idChats.get(messageRq.getChatId());
    String message = "Las opciones de tasas tanto en Dólares como en Soles que te ofrecemos por " +
            "ser nuestro cliente preferencial son:";
    SendMessage sendMessage = new SendMessage() // Create a SendMessage object with mandatory fields
            .setChatId(messageRq.getChatId())
            .setText(message);

    InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
    List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
    List<InlineKeyboardButton> rowInline = new ArrayList<>();
    rowInline.add(new InlineKeyboardButton().setText(String.format("Tasa en plazo de 90 días %2.2f%%(SOLES)",botMemory.getTasaSoles()))
            .setCallbackData
            ("boton_FEC_Local_plazo_tasa_soles_90"));
    rowsInline.add(rowInline);
    rowInline = new ArrayList<>();
    rowInline.add(new InlineKeyboardButton().setText(String.format("Tasa en plazo de 90 días %2.2f%%(DOLARES)",botMemory.getTasaDolares()))
            .setCallbackData
                    ("boton_FEC_Local_plazo_tasa_soles_90"));
    rowsInline.add(rowInline);
    rowInline = new ArrayList<>();
    rowInline.add(new InlineKeyboardButton().setText("volver a cotizar")
            .setCallbackData
                    ("boton_FEC_Local_plazo_tasa_volver_a_cotizar"));
    // Set the keyboard to the markup

    rowsInline.add(rowInline);
    // Add it to the message
    markupInline.setKeyboard(rowsInline);
    sendMessage.setReplyMarkup(markupInline);

    flujoClienteBot.executeMessage(sendMessage);
  }

  public void stepNegociar(FlujoClienteBot flujoClienteBot, Message messageRq) {
    String message = "Bien! Parece que estas de acuerdo con la tasa proporcionada: sólo queda " +
            "confirmar tu solicitud";
    SendMessage sendMessage = new SendMessage() // Create a SendMessage object with mandatory fields
            .setChatId(messageRq.getChatId())
            .setText(message);

    InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
    List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
    List<InlineKeyboardButton> rowInline = new ArrayList<>();
    rowInline.add(new InlineKeyboardButton().setText("Acepto")
            .setCallbackData
                    ("boton_acepto"));

    rowInline.add(new InlineKeyboardButton().setText("No acepto")
            .setCallbackData
                    ("boton_no_acepto"));



    // Set the keyboard to the markup
    rowsInline.add(rowInline);
    // Add it to the message
    markupInline.setKeyboard(rowsInline);
    sendMessage.setReplyMarkup(markupInline);

    flujoClienteBot.executeMessage(sendMessage);
  }

}
