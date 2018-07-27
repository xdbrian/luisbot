package com.hackaton.bot.Flow;

import com.hackaton.bot.BotMemory;
import com.hackaton.bot.FlujoClienteBot;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

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

  public void continueFlowShowOtherThing(FlujoClienteBot flujoClienteBot, Update update) {
    String message = "Te puedo ayudar en otra tarea?";
    SendMessage sendMessage = new SendMessage() // Create a SendMessage object with mandatory fields
            .setChatId(update.getMessage().getChatId())
            .setText(message);

    flujoClienteBot.executeMessage(sendMessage);
  }
}
