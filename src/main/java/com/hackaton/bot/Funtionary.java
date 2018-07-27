package com.hackaton.bot;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Message;
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

  public void getFuntionaryInfo (TelegramLongPollingBot telegramLongPollingBot, Message message){
    SendPhoto msgPhoto = new SendPhoto()
            .setChatId(Long.toString(message.getChat().getId()))
            .setPhoto("https://thumbs.dreamstime.com/b/d-security-agent-works-laptop-white-background-69375550.jpg")
            .setCaption("Puedes contactarte con Brian Steventh" +
                    "910236555 para cualquier información");
    try {
      telegramLongPollingBot.sendPhoto(msgPhoto); // Call method to send the photo
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }


  public void saveFuntionary(Message msn,SendMessage message){
    this.matricula = msn.getText();
    message.setText("La matricula ha sido guardada");
    message.setText("Ahora Por favor ingresa tu clave");
    isSaveFuntionary = false;
    isSavePassword = true;

  }

  public void initBcp(Message msn,SendMessage message){
    message.setText("por favor ingresa tu matricula");
    isSaveFuntionary = true;
  }

  public void savePassword(Message msn,SendMessage message){
    this.password =  msn.getText();
    isSavePassword = false;
    message.setText("Gracias tu información ha sido guardada");
  }
  public String getMatricula() {
    return matricula;
  }

  public void setMatricula(String matricula) {
    this.matricula = matricula;
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
