package com.hackaton.bot.Flow;

import com.hackaton.bot.BotMemory;

import com.hackaton.bot.FlujoClienteBot;
import com.hackaton.bot.NameStepFlows;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * Funtionary Description of class.
 * <b>Class</b>: Funtionary<br/><b>Copyright</b>: &copy; 2017 Banco de Cr&eacute;dito del Per&uacute;<br/>
 * <b>Company</b>: Banco de Cr&eacute;dito del Per&uacute;<br/>
 * @author Banco de Cr&eacute;dito del Per&uacute; (BCP) <br/>
 * <u>Service Provider</u>: Everis Per&uacute; SAC (EVE) <br/>
 * <u>Developed by</u>: <br/><ul><li>Brian Steventh Torres Yañez (BTY) Everis (EVE)</li></ul>
 * <u>Changes</u>:<br/><ul><li>26/07/2018 21:44(BTY) Creaci&oacute;n de Clase.</li></ul>
 * @version 1.0
 */

public class Funtionary {

  private String matricula = "";
  private Boolean isSaveFuntionary = false;

  private String password = "";
  private Boolean isSavePassword = false;

  public void initFlowSaveFuntionary(FlujoClienteBot flujoClienteBot, Update update) {
    BotMemory botMemory = flujoClienteBot.idChats.get(update.getMessage().getChatId());
    botMemory.setStepFlowsCross(NameStepFlows.GUARDAR_INFORMACION_DEL_AGENTE);
    botMemory.setStepFlowFuntionary(0);
    continueSaveFuntionary(flujoClienteBot, update);
  }

  public void continueSaveFuntionary(FlujoClienteBot flujoClienteBot, Update update) {
    BotMemory botMemory = flujoClienteBot.idChats.get(update.getMessage().getChatId());

    switch (botMemory.getStepFlowFuntionary()) {
      case 0:
        this.initBcp(flujoClienteBot,update);
        botMemory.setStepFlowFuntionary(1);
        break;
      case 1:
        saveFuntionary(flujoClienteBot,update);
        botMemory.setStepFlowFuntionary(2);
        break;
      case 2:
        savePassword(flujoClienteBot,update);
        botMemory.setStepFlowFuntionary(0);
        botMemory.setStepFlowsCross(NameStepFlows.OFRECER_OTRA_COSA);
        flujoClienteBot.managerFlow.continueFlow(flujoClienteBot,update);
        break;
    }

  }

  public void getFuntionaryInfo(TelegramLongPollingBot telegramLongPollingBot, Update update) {
    SendPhoto msgPhoto = new SendPhoto()
            .setChatId(Long.toString(update.getMessage().getChat().getId()))
            .setPhoto("https://thumbs.dreamstime.com/b/d-security-agent-works-laptop-white-background-69375550.jpg")
            .setCaption("Puedes contactarte con Brian Steventh" +
                    "910236555 para cualquier información");
    try {
      telegramLongPollingBot.sendPhoto(msgPhoto); // Call method to send the photo
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
    new SendMessage()
            .setChatId(update.getMessage().getChatId());
  }


  public void saveFuntionary(FlujoClienteBot flujoClienteBot, Update update) {
    String messageReturn = "";
    if (validateMatricula(update.getMessage().getText())) {
      this.matricula = update.getMessage().getText();
      messageReturn = "\"La matrícula ha sido guardada \\nAhora Por favor ingresa tu clave\"";
      isSaveFuntionary = false;
      isSavePassword = true;
    } else {
      messageReturn = "el número de matrícula se encuentra en otro formato, por favor agregalo " +
              "nuevamente";
    }
    SendMessage sendMessage = new SendMessage() // Create a SendMessage object with mandatory fields
            .setChatId(update.getMessage().getChatId())
            .setText(messageReturn);
    flujoClienteBot.executeMessage(sendMessage);
  }

  private boolean validateMatricula(String text) {
    if (text.toCharArray()[0] != 'S') {
      return false;
    }

    if (text.length() != 6) {
      return false;
    }

    if (text.split(" ").length > 1) {
      return false;
    }
    return true;
  }

  public void initBcp(FlujoClienteBot flujoClienteBot,Update update) {
    isSaveFuntionary = true;
    isSavePassword = false;
    SendMessage sendMessage = new SendMessage() // Create a SendMessage object with mandatory fields
            .setChatId(update.getMessage().getChatId())
            .setText("por favor ingresa tu matricula");
    flujoClienteBot.executeMessage(sendMessage);
  }

  public void savePassword(FlujoClienteBot flujoClienteBot,Update update) {
    this.password = update.getMessage().getText();
    isSavePassword = false;
    isSaveFuntionary = false;
    SendMessage sendMessage = new SendMessage() // Create a SendMessage object with mandatory fields
            .setChatId(update.getMessage().getChatId())
            .setText("Tu información ha sido actualizada");
    flujoClienteBot.executeMessage(sendMessage);
  }

  public String getMatricula() {
    return matricula;
  }

  public void setMatricula(Update update, SendMessage message) {
    this.matricula = update.getMessage().getText();
  }

  public Boolean getSaveFuntionary() {
    return isSaveFuntionary;
  }

  public void setSaveFuntionary(Boolean saveFuntionary) {
    isSaveFuntionary = saveFuntionary;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Boolean getSavePassword() {
    return isSavePassword;
  }

  public void setSavePassword(Boolean savePassword) {
    isSavePassword = savePassword;
  }
}
