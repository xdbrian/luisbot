package com.hackaton.bot.Flow;

import com.hackaton.bot.BotMemory;
import com.hackaton.bot.FlujoClienteBot;
import com.hackaton.bot.NameStepFlows;
import org.apache.commons.lang3.SerializationUtils;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
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

    public void initialSoliciteInfoInitial(FlujoClienteBot flujoClienteBot, Message messageRq) {
        BotMemory botMemory = flujoClienteBot.idChats.get(messageRq.getChatId());
        botMemory.setStepFlowsCross(NameStepFlows.AUTENTICACION_DEL_USUARIO);
        botMemory.setStepFlowSoliciteInfoInitial(0);
    }

    public void continueSoliciteInfoInitial(FlujoClienteBot flujoClienteBot, Message messageRq) {
        BotMemory botMemory = flujoClienteBot.idChats.get(messageRq.getChatId());
        SendMessage message = null;
        switch (botMemory.getStepFlowSoliciteInfoInitial()) {
            case 0:
                message = FlujoClienteBot.botBusiness.pedirPermsisoCelular(messageRq);
                botMemory.setStepFlowSoliciteInfoInitial(1);
                botMemory.setStepFlowsCross(NameStepFlows.AUTENTICACION_DEL_USUARIO);
                flujoClienteBot.executeMessage(message);
                break;
            case 1:
                if (botMemory != null) {
                    botMemory.setTelephone(messageRq.getContact().getPhoneNumber());
                }
                botMemory.setStepFlowSoliciteInfoInitial(2);
                message = FlujoClienteBot.botBusiness.solicitarDocumentoIdentidad(messageRq);
                flujoClienteBot.executeMessage(message);
                break;
            case 2:
                Boolean isCorrect = FlujoClienteBot.botBusiness.validarDocumentoIdentidad
                        (flujoClienteBot, botMemory, messageRq.getText(),messageRq.getChat().getFirstName());
                if (isCorrect) {
                    botMemory.setStepFlowSoliciteInfoInitial(4);
                    botMemory.setStepFlowsCross(NameStepFlows.AUTENTICACION_DEL_USUARIO);
                } else {
                    botMemory.setStepFlowSoliciteInfoInitial(2);
                }
                break;
            case 4:
                Boolean isCorrectRuc = FlujoClienteBot.botBusiness.validarRuc
                        (flujoClienteBot, botMemory, messageRq.getText());
                if (isCorrectRuc) {
                    botMemory.setStepFlowSoliciteInfoInitial(5);
                    botMemory.setStepFlowsCross(NameStepFlows.OFRECER_OTRA_COSA);
                }else {
                    botMemory.setStepFlowSoliciteInfoInitial(4);
                }
                break;
            case 5:
                FlujoClienteBot.botBusiness.respuestaSolicitarRuc(flujoClienteBot, botMemory, messageRq);
                botMemory.setStepFlowSoliciteInfoInitial(0);
                botMemory.setStepFlowsCross(NameStepFlows.OFRECER_OTRA_COSA);
                flujoClienteBot.managerFlow.continueFlow(flujoClienteBot, messageRq);
                break;
        }
    }
}
