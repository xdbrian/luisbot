package com.hackaton.bot;

import com.hackaton.bot.Flow.SoliciteAutenticationInitial;
import com.hackaton.bot.business.BotBusiness;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import static com.hackaton.bot.NameStepFlows.OFRECER_OTRA_COSA;
import static java.lang.Math.toIntExact;

/**
 * MyAmazingBot.
 *
 * @author lballena.
 */
public class FlujoClienteBot extends TelegramLongPollingBot {

    public int contador = 0;
    public static BotBusiness botBusiness = new BotBusiness();

    public Map<Long, BotMemory> idChats = new HashMap();


    public ManagerFlow managerFlow = new ManagerFlow();

    public FlujoClienteBot() {
    }

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage message = null;

        if (update.hasMessage() && update.getMessage().hasText()) {
            BotMemory botMemory = idChats.get(update.getMessage().getChatId());
            if (botMemory == null) {
                System.out.println("---------------------> ChatId: -->" + update.getMessage().getChatId());
                idChats.put(update.getMessage().getChatId(), new BotMemory(update.getMessage().getChatId()));
                message = new SendMessage() // Create a SendMessage object with mandatory fields
                        .setChatId(update.getMessage().getChatId())
                        .setText(botBusiness.saludar(update, getBotUsername()));
                executeMessage(message);
                managerFlow.soliciteAutenticationInitial.initialSoliciteInfoInitial(this, update.getMessage());
            } else if (update.getMessage().getText().startsWith("/soybcp")) {
                botMemory.setStepFlowsCross(NameStepFlows.GUARDAR_INFORMACION_DEL_AGENTE);
                managerFlow.funtionary.initFlowSaveFuntionary(this, update.getMessage());
                //conversationRestart(this,update);
            } else if (update.getMessage().getText().startsWith("/funtionary")) {
                managerFlow.funtionary.getFuntionaryInfo(this, update.getMessage());
            } else if (update.getMessage().getText().startsWith("/chatWhitAgent")) {
                botMemory.setStepFlowsCross(NameStepFlows.CHAT_USER_WHIT_AGENT);
                managerFlow.continueFlow(this, update.getMessage());
            } else if (update.getMessage().getText().equals("/keyboard")) {

                message = new SendMessage() // Create a message object object
                        .setChatId(update.getMessage().getChatId())
                        .setText("You send /start");
                InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
                List<InlineKeyboardButton> rowInline = new ArrayList<>();
                rowInline.add(new InlineKeyboardButton().setText("Conocer mi tasa Actual").setCallbackData
                        ("boton_tasa_actual"));
                rowInline.add(new InlineKeyboardButton().setText("Información de mi Funcionario")
                        .setCallbackData("boton_info_funcionario"));
                // Set the keyboard to the markup
                rowsInline.add(rowInline);
                // Add it to the message
                markupInline.setKeyboard(rowsInline);
                message.setReplyMarkup(markupInline);
                try {
                    execute(message); // Sending our message object to user
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (update.getMessage().getText().equals("/clear")){
                idChats.remove(update.getMessage().getChatId());
            }else {
                managerFlow.continueFlow(this, update.getMessage());
            }
            //executeMessage(message);
        } else if (update.hasCallbackQuery()) {
            // Set variables
            String call_data = update.getCallbackQuery().getData();
            long message_id = update.getCallbackQuery().getMessage().getMessageId();
            long chat_id = update.getCallbackQuery().getMessage().getChatId();

            if (call_data.equals("boton_tasa_actual")) {
                BotMemory botMemory = idChats.get(chat_id);
                botMemory.setStepFlowsCross(NameStepFlows.OBTENER_TASA_ACTUAL);
                botMemory.setStepFlowStepSelectProduct(1);
                managerFlow.selectProduct.continueSelectProduct(this, update.getCallbackQuery().getMessage());
            } else if (call_data.equals("boton_info_funcionario")) {
                managerFlow.funtionary.getFuntionaryInfo(this, update.getCallbackQuery().getMessage());
                BotMemory botMemory = idChats.get(update.getCallbackQuery().getMessage().getChatId());
                botMemory.setStepFlowsCross(NameStepFlows.OFRECER_OTRA_COSA);
                managerFlow.selectProduct.continueSelectProduct(this, update.getCallbackQuery().getMessage());
            } else if (call_data.equals("boton_FEC_Local")) {
                BotMemory botMemory = idChats.get(update.getCallbackQuery().getMessage().getChatId());
                botMemory.setStepFlowStepSelectProduct(2);
                managerFlow.selectProduct.continueSelectProduct(this, update.getCallbackQuery().getMessage());
            } else if (call_data.equals("boton_FEC_Local_parametro_monto")) {
                managerFlow.selectProduct.continueSelectProduct(this, update.getCallbackQuery().getMessage());
            } else if (call_data.equals("boton_FEC_Local_plazo_60")) {
                BotMemory botMemory = idChats.get(update.getCallbackQuery().getMessage().getChatId());
                botMemory.setStepFlowStepSelectProduct(4);
                managerFlow.selectProduct.continueSelectProduct(this, update.getCallbackQuery().getMessage());
            } else if (call_data.equals("boton_FEC_Local_plazo_90")) {

//        BotMemory botMemory = idChats.get(update.getCallbackQuery().getMessage().getChatId());
//        botMemory.setStepFlowStepSelectProduct(5);
                BotMemory botMemory = idChats.get(update.getCallbackQuery().getMessage().getChatId());
                botMemory.setStepFlowStepSelectProduct(4);
                managerFlow.selectProduct.continueSelectProduct(this, update.getCallbackQuery().getMessage());
            } else if (call_data.equals("boton_FEC_Local_plazo_180")) {

                managerFlow.selectProduct.continueSelectProduct(this, update.getCallbackQuery().getMessage());
            } else if (call_data.equals("boton_FEC_Local_plazo_otro_plazo")) {

                managerFlow.selectProduct.continueSelectProduct(this, update.getCallbackQuery().getMessage());
            } else if (call_data.equals("boton_FEC_Local_plazo_tasa_soles_90")) {
                BotMemory botMemory = idChats.get(update.getCallbackQuery().getMessage().getChatId());
                botMemory.setStepFlowStepSelectProduct(5);
                managerFlow.selectProduct.continueSelectProduct(this, update.getCallbackQuery().getMessage());
            } else if (call_data.equals("boton_FEC_Local_plazo_tasa_volver_a_cotizar")) {
                BotMemory botMemory = idChats.get(update.getCallbackQuery().getMessage().getChatId());
                botMemory.setStepFlowStepSelectProduct(5);
                managerFlow.selectProduct.continueSelectProduct(this, update.getCallbackQuery().getMessage());
            } else if (call_data.equals("boton_acepto")) {
                message = new SendMessage()
                        .setChatId(update.getCallbackQuery().getMessage().getChatId())
                        .setText((": ¡Excelente decisión " + update
                                .getCallbackQuery
                                        ().getMessage()
                                .getChat()
                                .getFirstName() + "! ¡Me alegra que confies en " +
                                "nosotros! Tu tasa se actualizo correctamente  en el modulo de financiamiento " +
                                "electrónico, ya puede realizar tus operaciones. Algo mas en lo que pueda" +
                                " ayudarte?"));
                executeMessage(message);
                BotMemory botMemory = idChats.get(update.getCallbackQuery().getMessage().getChatId());
                botBusiness.registrarTasa(this,botMemory);
                botMemory.setStepFlowsCross(NameStepFlows.OFRECER_OTRA_COSA);
                botMemory.setStepFlowStepSelectProduct(0);
//                managerFlow.continueFlow(this, update.getCallbackQuery().getMessage());


            } else if (call_data.equals("boton_no_acepto")) {
                if (contador < 2) {
                    BotMemory botMemory = idChats.get(update.getCallbackQuery().getMessage().getChatId());
                    botMemory.setStepFlowStepSelectProduct(4);
                    managerFlow.selectProduct.continueSelectProduct(this, update.getCallbackQuery().getMessage());
                    contador++;
                } else {
                    BotMemory botMemory = idChats.get(update.getCallbackQuery().getMessage().getChatId());
                    botMemory.setStepFlowsCross(NameStepFlows.NEGOCIAR_WHIT_FUNTIONARY);
                    message = new SendMessage()
                            .setChatId(update.getCallbackQuery().getMessage().getChatId())
                            .setText(("Por ser un cliente importante para la empresa te referiremos de " +
                                    "inmediato con un funcionario."));
                    this.executeMessage(message);
                    managerFlow.continueFlow(this, update.getCallbackQuery().getMessage());

                }
            }


        } else {
            managerFlow.continueFlow(this, update.getMessage());
        }
    }


    @Override
    public String getBotUsername() {
        return "Luis21Bot";
    }

    @Override
    public String getBotToken() {
        return "680599289:AAF9mRhmMiSWxyFjZqQIaREljaiWwtcDsXE";
    }

    public void executeMessage(SendMessage message) {
        try {
            execute(message); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private SendMessage removeKeyBoard(Update update) {
        SendMessage message = new SendMessage();
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        message.setReplyMarkup(markup);
        message.setChatId(update.getMessage().getChatId());
        return message;
    }

    public static void conversationRestart(FlujoClienteBot flujoClienteBot, Message messageRq) {
        BotMemory botMemory = flujoClienteBot.idChats.get(messageRq.getChatId());
        botMemory.initialCount();
        botMemory.setStepFlowsCross(OFRECER_OTRA_COSA);
        flujoClienteBot.managerFlow.continueFlow(flujoClienteBot, messageRq);
    }

}
