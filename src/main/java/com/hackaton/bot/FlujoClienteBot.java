package com.hackaton.bot;

import com.hackaton.bot.Flow.SoliciteAutenticationInitial;
import com.hackaton.bot.business.BotBusiness;
import java.util.HashMap;
import java.util.Map;
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
        idChats.put(update.getMessage().getChatId(), new BotMemory(update.getMessage().getChatId()));
        message = new SendMessage() // Create a SendMessage object with mandatory fields
                .setChatId(update.getMessage().getChatId())
                .setText(botBusiness.saludar(update, getBotUsername()));
        executeMessage(message);

        managerFlow.soliciteAutenticationInitial.initialSoliciteInfoInitial(this,update);
      } else if (update.getMessage().getText().startsWith("/soybcp")) {
        botMemory.setStepFlowsCross(NameStepFlows.GUARDAR_INFORMACION_DEL_AGENTE);
        managerFlow.funtionary.initFlowSaveFuntionary(this,update);
      } else if (update.getMessage().getText().startsWith("/funtionary")) {
        managerFlow.funtionary.getFuntionaryInfo(this,update);
      } else if (update.getMessage().getText().startsWith("/chatWhitAgent")) {
        botMemory.setStepFlowsCross(NameStepFlows.CHAT_USER_WHIT_AGENT);
        managerFlow.funtionary.getFuntionaryInfo(this,update);
      } else if (update.getMessage().getText().startsWith("/chatWhitAgent")) {
      }
      else {
        managerFlow.continueFlow(this, update);
      }
        //executeMessage(message);
      } else {
        managerFlow.continueFlow(this, update);
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
