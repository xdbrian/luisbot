package com.hackaton.bot;

import com.hackaton.bot.business.ConsultaRUC;

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
  private int counter = 0;

  private Double tasaActualSol;
  private Double tasaActualDol;


  private int counterDolares = 0;

  private ConsultaRUC consultaRUC;

  public Double getTasaSoles(){
    tasaActualSol = new Double(0.0);
    if(counter == 0 ){
      tasaActualSol = (consultaRUC.getProductos().get(0).getTasaSoles()-0.02);
      counter = counter +1;
    }else if(counter == 1){
      tasaActualSol = (consultaRUC.getProductos().get(0).getTasaSoles()-0.04);
      counter = counter +1;
    }else if(counter == 2){
      tasaActualSol = (consultaRUC.getProductos().get(0).getTasaSoles()-0.06);
      counter = counter +1;
    }else if(counter == 3){
      tasaActualSol = (consultaRUC.getProductos().get(0).getTasaSoles()-0.07);
      counter = counter +1;
    }
    return tasaActualSol;
  }

  public Double getTasaDolares(){
    tasaActualDol = new Double(0.0);
    if(counterDolares == 0 ){
      tasaActualDol = (consultaRUC.getProductos().get(0).getTasaDolares()-0.02);
      counterDolares = counterDolares +1;
    }else if(counterDolares == 1){
      tasaActualDol = (consultaRUC.getProductos().get(0).getTasaDolares()-0.04);
      counterDolares = counterDolares +1;
    }else if(counterDolares == 2){
      tasaActualDol = (consultaRUC.getProductos().get(0).getTasaDolares()-0.06);
      counterDolares = counterDolares +1;
    }else if(counter == 3){
      tasaActualDol = (consultaRUC.getProductos().get(0).getTasaDolares()-0.07);
      counterDolares = counterDolares +1;
    }
    return tasaActualDol;
  }

  public ConsultaRUC getConsultaRUC() {
    return consultaRUC;
  }

  public void setConsultaRUC(ConsultaRUC consultaRUC) {
    this.consultaRUC = consultaRUC;
  }

  public Integer getStepFlowStepSelectProduct() {
    return stepFlowStepSelectProduct;
  }

  public void setStepFlowStepSelectProduct(Integer stepFlowStepSelectProduct) {
    this.stepFlowStepSelectProduct = stepFlowStepSelectProduct;
  }

  private boolean functionary = false;

  private Long chatIdCliente = null;


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

    public Long getChatIdCliente() {
        return chatIdCliente;
    }

    public void setChatIdCliente(Long chatIdCliente) {
        this.chatIdCliente = chatIdCliente;
    }

  public Double getTasaActualSol() {
    return tasaActualSol;
  }

  public void setTasaActualSol(Double tasaActualSol) {
    this.tasaActualSol = tasaActualSol;
  }

  public Double getTasaActualDol() {
    return tasaActualDol;
  }

  public void setTasaActualDol(Double tasaActualDol) {
    this.tasaActualDol = tasaActualDol;
  }
}
