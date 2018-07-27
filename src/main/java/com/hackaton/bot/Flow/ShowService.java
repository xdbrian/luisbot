package com.hackaton.bot.Flow;

import com.hackaton.bot.BotMemory;
import com.hackaton.bot.FlujoClienteBot;
import java.util.ArrayList;
import java.util.List;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import static com.hackaton.bot.NameStepFlows.OFRECER_SERVICIOS;

/**
 * ShowService Description of class.
 * <b>Class</b>: ShowService<br/><b>Copyright</b>: &copy; 2017 Banco de Cr&eacute;dito del Per&uacute;<br/>
 * <b>Company</b>: Banco de Cr&eacute;dito del Per&uacute;<br/>
 * @author Banco de Cr&eacute;dito del Per&uacute; (BCP) <br/>
 * <u>Service Provider</u>: Everis Per&uacute; SAC (EVE) <br/>
 * <u>Developed by</u>: <br/><ul><li>Brian Steventh Torres Yañez (BTY) Everis (EVE)</li></ul>
 * <u>Changes</u>:<br/><ul><li>27/07/2018 09:21(BTY) Creaci&oacute;n de Clase.</li></ul>
 * @version 1.0
 */
public class ShowService {

  public void initFlowShowService(FlujoClienteBot flujoClienteBot, Update update) {
    BotMemory botMemory = flujoClienteBot.idChats.get(update.getMessage().getChatId());
    botMemory.setStepFlowsCross(OFRECER_SERVICIOS);
    continueFlowShowService(flujoClienteBot, update);
  }

  public void continueFlowShowService(FlujoClienteBot flujoClienteBot, Update update) {
    String message = "Qué deseas hacer?";
    new SendMessage() // Create a SendMessage object with mandatory fields
            .setChatId(update.getMessage().getChatId())
            .setText(message);
  }

  public void continueFlowShowOtherThing(FlujoClienteBot flujoClienteBot, Message messageRq) {
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
}
