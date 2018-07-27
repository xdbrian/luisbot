package com.hackaton.bot;

public enum NameStepFlows {
  NONE(-1),
  AUTENTICACION_DEL_USUARIO(0),
  OFRECER_SERVICIOS(1),
  OFRECER_OTRA_COSA(2),
  GUARDAR_INFORMACION_DEL_AGENTE(3),
  CONSULTAR_INFORMACION_DEL_AGENTE(4),
  CHAT_USER_WHIT_AGENT(5),
  OBTENER_TASA_ACTUAL(6),
  NEGOCIAR_WHIT_FUNTIONARY(7);


  int value;

  NameStepFlows(int i) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }

}
