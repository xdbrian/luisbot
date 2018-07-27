package com.hackaton.bot;

import com.hackaton.bot.Flow.Funtionary;
import com.hackaton.bot.Flow.ShowService;
import com.hackaton.bot.Flow.SoliciteAutenticationInitial;
import org.telegram.telegrambots.api.objects.Update;

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

  public void continueFlow(FlujoClienteBot flujoClienteBot, Update update) {
    BotMemory botMemory = flujoClienteBot.idChats.get(update.getMessage().getChatId());

    switch (botMemory.getStepFlowsCross()) {
      case GUARDAR_INFORMACION_DEL_AGENTE:
        funtionary.continueSaveFuntionary(flujoClienteBot, update);
        break;
      case OFRECER_SERVICIOS:
        funtionary.continueSaveFuntionary(flujoClienteBot, update);
        break;
      case OFRECER_OTRA_COSA:
        showService.continueFlowShowOtherThing(flujoClienteBot, update);
        break;
      case AUTENTICACION_DEL_USUARIO:
        soliciteAutenticationInitial.continueSoliciteInfoInitial(flujoClienteBot, update);
        break;
      case CHAT_USER_WHIT_AGENT:

        break;
    }
  }
}
