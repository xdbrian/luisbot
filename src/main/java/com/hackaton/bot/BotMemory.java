package com.hackaton.bot;

/**
 * Secuencia.
 *
 * @author lballena.
 */
public class BotMemory {

  private boolean init = true;

  private String telephone;

  private boolean dni = false;


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

  public boolean isDni() {
    return dni;
  }

  public void setDni(boolean dni) {
    this.dni = dni;
  }
}
