package com.hackaton.bot.Flow;

import com.hackaton.bot.BotMemory;
import com.hackaton.bot.FlujoClienteBot;
import com.hackaton.bot.NameStepFlows;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

/**
 * SoliciteInfoInitial Description of class.
 * <b>Class</b>: SoliciteInfoInitial<br/><b>Copyright</b>: &copy; 2017 Banco de Cr&eacute;dito del Per&uacute;<br/>
 * <b>Company</b>: Banco de Cr&eacute;dito del Per&uacute;<br/>
 *
 * @author Banco de Cr&eacute;dito del Per&uacute; (BCP) <br/>
 * <u>Service Provider</u>: Everis Per&uacute; SAC (EVE) <br/>
 * <u>Developed by</u>: <br/><ul><li>Brian Steventh Torres Yañez (BTY) Everis (EVE)</li></ul>
 * <u>Changes</u>:<br/><ul><li>27/07/2018 08:50(BTY) Creaci&oacute;n de Clase.</li></ul>
 * @version 1.0
 */
public class SoliciteAutenticationInitial {

    public void initialSoliciteInfoInitial(FlujoClienteBot flujoClienteBot, Update update) {
        BotMemory botMemory = flujoClienteBot.idChats.get(update.getMessage().getChatId());
        botMemory.setStepFlowsCross(NameStepFlows.AUTENTICACION_DEL_USUARIO);
        botMemory.setStepFlowSoliciteInfoInitial(0);
        continueSoliciteInfoInitial(flujoClienteBot, update);
    }

    public void continueSoliciteInfoInitial(FlujoClienteBot flujoClienteBot, Update update) {
        BotMemory botMemory = flujoClienteBot.idChats.get(update.getMessage().getChatId());
        SendMessage message = null;
        switch (botMemory.getStepFlowSoliciteInfoInitial()) {
            case 0:
                message = FlujoClienteBot.botBusiness.pedirPermsisoCelular(update);
                botMemory.setStepFlowSoliciteInfoInitial(1);
                flujoClienteBot.executeMessage(message);
                break;
            case 1:
                if (botMemory != null) {
                    botMemory.setTelephone(update.getMessage().getContact().getPhoneNumber());
                }
                botMemory.setStepFlowSoliciteInfoInitial(2);
                message = FlujoClienteBot.botBusiness.solicitarDocumentoIdentidad(update);
                flujoClienteBot.executeMessage(message);
                break;
            case 2:
                Boolean isCorrect = FlujoClienteBot.botBusiness.validarDocumentoIdentidad
                        (flujoClienteBot, botMemory,
                                update);

                if (isCorrect) {
                    botMemory.setFunctionary(false);
                    botMemory.setStepFlowsCross(NameStepFlows.AUTENTICACION_DEL_USUARIO);
                    botMemory.setStepFlowSoliciteInfoInitial(3);
                    flujoClienteBot.managerFlow.continueFlow(flujoClienteBot, update);
                }
                break;
            case 3:
                BotMemory botMemFunctionary =
                        flujoClienteBot.idChats.values().stream().filter(v -> v.isFunctionary()).findFirst().orElse(null);
                if (botMemFunctionary != null) {
                    message = new SendMessage()
                            .setChatId(botMemFunctionary.getChatId())
                            .setText(String.format("Estimado funcionario, hay un cliente al cual no me es posible " +
                                    "resolver una consulta en particular: \"%s\"", update.getMessage().getText()));
                    flujoClienteBot.executeMessage(message);
                }
                break;
        }
    }
}
