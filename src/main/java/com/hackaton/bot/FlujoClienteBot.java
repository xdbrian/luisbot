package com.hackaton.bot;

import com.hackaton.bot.Flow.SoliciteAutenticationInitial;
import com.hackaton.bot.business.BotBusiness;
import java.util.HashMap;
import java.util.Map;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import static com.hackaton.bot.NameStepFlows.OFRECER_OTRA_COSA;

/**
 * MyAmazingBot.
 * @author lballena.
 */
public class FlujoClienteBot extends TelegramLongPollingBot {


  public static BotBusiness botBusiness = new BotBusiness();

  public Map<Long, BotMemory> idChats = new HashMap();


  public ManagerFlow managerFlow = new ManagerFlow();

  public SoliciteAutenticationInitial soliciteInfoInitial = new SoliciteAutenticationInitial();

  public FlujoClienteBot() {
  }

  @Override
  public void onUpdateReceived(Update update) {
    SendMessage message = null;
    BotMemory botMemory = idChats.get(update.getMessage().getChatId());
    if (update.hasMessage() && update.getMessage().hasText()) {

      if (botMemory == null) {
        System.out.println("---------------------> ChatId: -->" + update.getMessage().getChatId());
        idChats.put(update.getMessage().getChatId(), new BotMemory());
        message = new SendMessage() // Create a SendMessage object with mandatory fields
                .setChatId(update.getMessage().getChatId())
                .setText(botBusiness.saludar(update, getBotUsername()));
        executeMessage(message);
//      } else if (StringUtils.isEmpty(botMemory.getTelephone()) || !botMemory.isDni()) {

        //  if (StringUtils.isEmpty(botMemory.getTelephone()))

        //message = botBusiness.pedirPermsisoCelular(update);

        managerFlow.soliciteAutenticationInitial.initialSoliciteInfoInitial(this,update);
      }
//      } else if (!botMemory.isDni()) {
//          botBusiness.validarDocumentoIdentidad(botMemory, update);
//        }
//        else {
//          message = new SendMessage() // Create a SendMessage object with mandatory fields
//                  .setChatId(update.getMessage().getChatId())
//                  .setText(update.getMessage().getText());
//        }

      else if (update.getMessage().getText().startsWith("/soybcp")) {
        botMemory.setStepFlowsCross(NameStepFlows.GUARDAR_INFORMACION_DEL_AGENTE);
        managerFlow.funtionary.initFlowSaveFuntionary(this,update);
        //conversationRestart(this,update);
      } else if (update.getMessage().getText().startsWith("/funtionary")) {
        managerFlow.funtionary.getFuntionaryInfo(this,update);
      }
      else {
        managerFlow.continueFlow(this, update);
      }


        //executeMessage(message);
      } else {

        managerFlow.continueFlow(this, update);

    }


//    else {
//      System.out.println("---------------------> Telephone: -->" + update.getMessage()
//      .getContact()
//              .getPhoneNumber());
//      BotMemory botMemory = idChats.get(update.getMessage().getChatId());
//      message = botBusiness.solicitarDocumentoIdentidad(update);
//      executeMessage(message);
//      if (botMemory != null) {
//        botMemory.setTelephone(update.getMessage().getContact().getPhoneNumber());
//      }
//      message = removeKeyBoard(update);
//      executeMessage(message);
//    }
  }


  @Override
  public String getBotUsername() {
    return "trinity21bot";
  }

  @Override
  public String getBotToken() {

    return "684560452:AAEiLnIqClLuOlC_KeGEwC8lVE7IbALecWE";
  }

  public void executeMessage(SendMessage message) {
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

  public static void conversationRestart(FlujoClienteBot flujoClienteBot,Update update) {
    BotMemory botMemory = flujoClienteBot.idChats.get(update.getMessage().getChatId());
    botMemory.initialCount();
    botMemory.setStepFlowsCross(OFRECER_OTRA_COSA);
    flujoClienteBot.managerFlow.continueFlow(flujoClienteBot,update);
  }

}
