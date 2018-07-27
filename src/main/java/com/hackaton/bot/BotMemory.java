package com.hackaton.bot;

import static com.hackaton.bot.NameStepFlows.NONE;

/**
 * Secuencia.
 *
 * @author lballena.
 */
public class BotMemory {

  private boolean init = true;

  private String telephone;

  private boolean dni = false;

  private Long chatId;
  //nombre del flujo en curso
  private NameStepFlows stepFlowsCross = NONE;

  //posicion del flujo actual del usuario
  private Integer stepFlowFuntionary = 0;
  private Integer stepFlowSoliciteInfoInitial = 0;
  private Integer stepFlowStepSelectProduct = 0;

  public Integer getStepFlowStepSelectProduct() {
    return stepFlowStepSelectProduct;
  }

  public void setStepFlowStepSelectProduct(Integer stepFlowStepSelectProduct) {
    this.stepFlowStepSelectProduct = stepFlowStepSelectProduct;
  }

  private boolean functionary;


  public BotMemory(Long chatId) {
    this.chatId = chatId;
  }

  public void initialCount(){
    Integer stepFlowFuntionary = 0;
    Integer stepFlowSoliciteInfoInitial = 0;
    stepFlowsCross = NONE;
  }
  public Integer getStepFlowSoliciteInfoInitial() {
    return stepFlowSoliciteInfoInitial;
  }

  public void setStepFlowSoliciteInfoInitial(Integer stepFlowSoliciteInfoInitial) {
    this.stepFlowSoliciteInfoInitial = stepFlowSoliciteInfoInitial;
  }

  public Integer getStepFlowFuntionary() {
    return stepFlowFuntionary;
  }

  public NameStepFlows getStepFlowsCross() {
    return stepFlowsCross;
  }

  public void setStepFlowsCross(NameStepFlows stepFlowsCross) {
    this.stepFlowsCross = stepFlowsCross;
  }

  public void setStepFlowFuntionary(Integer stepFlowFuntionary) {
    this.stepFlowFuntionary = stepFlowFuntionary;
  }

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

  public boolean isFunctionary() {
    return functionary;
  }

  public void setFunctionary(boolean functionary) {
    this.functionary = functionary;
  }

  public Long getChatId() {
    return chatId;
  }

  public void setChatId(Long chatId) {
    this.chatId = chatId;
  }
}
