package com.hackaton.bot.Flow;

import com.hackaton.bot.BotMemory;

import com.hackaton.bot.FlujoClienteBot;
import com.hackaton.bot.NameStepFlows;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Message;
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

  public void initFlowSaveFuntionary(FlujoClienteBot flujoClienteBot, Message messageRq) {
    BotMemory botMemory = flujoClienteBot.idChats.get(messageRq.getChatId());
    botMemory.setStepFlowsCross(NameStepFlows.GUARDAR_INFORMACION_DEL_AGENTE);
    botMemory.setStepFlowFuntionary(0);
    continueSaveFuntionary(flujoClienteBot, messageRq);
  }

  public void continueSaveFuntionary(FlujoClienteBot flujoClienteBot, Message messageRq) {
    BotMemory botMemory = flujoClienteBot.idChats.get(messageRq.getChatId());

    switch (botMemory.getStepFlowFuntionary()) {
      case 0:
        this.initBcp(flujoClienteBot,messageRq);
        botMemory.setStepFlowsCross(NameStepFlows.GUARDAR_INFORMACION_DEL_AGENTE);
        botMemory.setStepFlowFuntionary(1);
        break;
      case 1:
        botMemory.setStepFlowsCross(NameStepFlows.GUARDAR_INFORMACION_DEL_AGENTE);
        saveFuntionary(flujoClienteBot,messageRq);
        botMemory.setStepFlowFuntionary(2);
        break;
      case 2:
        savePassword(flujoClienteBot,messageRq);
        botMemory.setFunctionary(true);
        botMemory.setStepFlowFuntionary(3);
        break;
      case 3:
        if(botMemory.getChatIdCliente() != null){
          SendMessage sendMessage = new SendMessage() // Create a SendMessage object with mandatory fields
                  .setChatId(botMemory.getChatIdCliente())
                  .setText(messageRq.getText());
          flujoClienteBot.executeMessage(sendMessage);
        }
        break;
    }

  }

  public void getFuntionaryInfo(TelegramLongPollingBot telegramLongPollingBot, Message messageRq) {
    SendPhoto msgPhoto = new SendPhoto()
            .setChatId(Long.toString(messageRq.getChat().getId()))
            .setPhoto("https://thumbs.dreamstime.com/b/d-security-agent-works-laptop-white-background-69375550.jpg")
            .setCaption("Puedes contactarte con Brian Steventh \n " +
                    "910236555 para cualquier información");
    try {
      telegramLongPollingBot.sendPhoto(msgPhoto); // Call method to send the photo
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
    new SendMessage()
            .setChatId(messageRq.getChatId());
  }


  public void saveFuntionary(FlujoClienteBot flujoClienteBot, Message messageRq) {
    String messageReturn = "";
    if (validateMatricula(messageRq.getText())) {
      this.matricula = messageRq.getText();
      messageReturn = "\"La matrícula ha sido guardada   \n  Ahora Por favor ingresa tu clave\"";
      isSaveFuntionary = false;
      isSavePassword = true;
    } else {
      messageReturn = "el número de matrícula se encuentra en otro formato, por favor agregalo " +
              "nuevamente";
    }
    SendMessage sendMessage = new SendMessage() // Create a SendMessage object with mandatory fields
            .setChatId(messageRq.getChatId())
            .setText(messageReturn);
    flujoClienteBot.executeMessage(sendMessage);
  }

  private boolean validateMatricula(String text) {
    return true;
  }

  public void initBcp(FlujoClienteBot flujoClienteBot,Message messageRq) {
    isSaveFuntionary = true;
    isSavePassword = false;
    SendMessage sendMessage = new SendMessage() // Create a SendMessage object with mandatory fields
            .setChatId(messageRq.getChatId())
            .setText("por favor ingresa tu matricula");
    flujoClienteBot.executeMessage(sendMessage);
  }

  public void savePassword(FlujoClienteBot flujoClienteBot,Message messageRq) {
    this.password = messageRq.getText();
    isSavePassword = false;
    isSaveFuntionary = false;
    SendMessage sendMessage = new SendMessage() // Create a SendMessage object with mandatory fields
            .setChatId(messageRq.getChatId())
            .setText("Tu información ha sido actualizada, favor de esperar que tengamos respuesta de un cliente");
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
