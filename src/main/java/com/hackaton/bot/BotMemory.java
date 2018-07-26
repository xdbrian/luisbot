package com.hackaton.bot;

/**
 * Secuencia.
 *
 * @author lballena.
 */
public class BotMemory {

  private boolean init = true;

  private String telephone;


  public boolean isInit() {
    return init;
  }

  public void setInit(boolean init) {
    this.init = init;
  }

  public String getTelephone() {
    return telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }
}
