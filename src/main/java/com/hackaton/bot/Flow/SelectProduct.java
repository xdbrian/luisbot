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

  public void initSelectProduct(FlujoClienteBot flujoClienteBot, Message messageRq) {
    BotMemory botMemory = flujoClienteBot.idChats.get(messageRq.getChatId());
    botMemory.setStepFlowStepSelectProduct(0);
    botMemory.setStepFlowsCross(OBTENER_TASA_ACTUAL);
    continueSelectProduct(flujoClienteBot,messageRq);
  }

  public void continueSelectProduct(FlujoClienteBot flujoClienteBot, Message messageRq) {
    BotMemory botMemory = flujoClienteBot.idChats.get(messageRq.getChatId());

    switch (botMemory.getStepFlowFuntionary()) {
      case 0:
        this.stepSelectProduct(flujoClienteBot, messageRq);
        botMemory.setStepFlowFuntionary(1);
        break;
      case 1:
        this.respuestaSeleccionarProducto(flujoClienteBot, messageRq);
        botMemory.setStepFlowFuntionary(2);
        break;
      case 2:
        botMemory.setStepFlowsCross(NameStepFlows.OFRECER_OTRA_COSA);
        botMemory.setStepFlowFuntionary(0);
        flujoClienteBot.managerFlow.continueFlow(flujoClienteBot,messageRq);
        break;
    }

  }

  public void stepSelectProduct(FlujoClienteBot flujoClienteBot, Message messageRq) {
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

  public void respuestaSeleccionarProducto(FlujoClienteBot flujoClienteBot, Message messageRq) {
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
}
