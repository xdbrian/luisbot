package com.hackaton.bot;

import com.hackaton.bot.Flow.Funtionary;
import com.hackaton.bot.Flow.SelectProduct;
import com.hackaton.bot.Flow.ShowService;
import com.hackaton.bot.Flow.SoliciteAutenticationInitial;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.Message;

/**
 * ManagerFlow Description of class.
 * <b>Class</b>: ManagerFlow<br/><b>Copyright</b>: &copy; 2017 Banco de Cr&eacute;dito del Per&uacute;<br/>
 * <b>Company</b>: Banco de Cr&eacute;dito del Per&uacute;<br/>
 * @author Banco de Cr&eacute;dito del Per&uacute; (BCP) <br/>
 * <u>Service Provider</u>: Everis Per&uacute; SAC (EVE) <br/>
 * <u>Developed by</u>: <br/><ul><li>Brian Steventh Torres Yañez (BTY) Everis (EVE)</li></ul>
 * <u>Changes</u>:<br/><ul><li>27/07/2018 07:15(BTY) Creaci&oacute;n de Clase.</li></ul>
 * @version 1.0
 */
public class ManagerFlow {

  protected Funtionary funtionary = new Funtionary();
  protected ShowService showService = new ShowService();
  protected SoliciteAutenticationInitial soliciteAutenticationInitial = new
          SoliciteAutenticationInitial();

  protected SelectProduct selectProduct = new SelectProduct();
  public void continueFlow(FlujoClienteBot flujoClienteBot, Message messageRq) {
    BotMemory botMemory = flujoClienteBot.idChats.get(messageRq.getChatId());

    switch (botMemory.getStepFlowsCross()) {
      case GUARDAR_INFORMACION_DEL_AGENTE:
        funtionary.continueSaveFuntionary(flujoClienteBot, messageRq);
        break;
      case OFRECER_SERVICIOS:
        funtionary.continueSaveFuntionary(flujoClienteBot, messageRq);
        break;
      case OFRECER_OTRA_COSA:
        showService.continueFlowShowOtherThing(flujoClienteBot, messageRq);
        break;
      case AUTENTICACION_DEL_USUARIO:
        soliciteAutenticationInitial.continueSoliciteInfoInitial(flujoClienteBot, messageRq);
        break;
      case CHAT_USER_WHIT_AGENT:
        break;
      case OBTENER_TASA_ACTUAL:
        selectProduct.continueSelectProduct(flujoClienteBot, messageRq);
        break;
    }
  }
}
