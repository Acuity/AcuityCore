package com.acuity.control.server.messages.handlers.impl;

import com.acuity.control.server.Events;
import com.acuity.control.server.messages.handlers.MessageHandler;
import com.acuity.control.server.websockets.WSocket;
import com.acuity.control.server.websockets.WSocketEvent;
import com.acuity.db.arango.monitor.events.ArangoEvent;
import com.acuity.db.arango.monitor.events.wrapped.impl.MessagePackageEvent;
import com.acuity.db.arango.monitor.events.wrapped.impl.bot.client.id_events.BotClientIDEvent;
import com.acuity.db.arango.monitor.events.wrapped.impl.bot.client.id_events.impl.BotClientConfigEvent;
import com.acuity.db.arango.monitor.events.wrapped.impl.bot.client.id_events.impl.RSAccountAssignedToEvent;
import com.acuity.db.domain.vertex.Vertex;
import com.acuity.db.domain.vertex.impl.AcuityAccount;
import com.acuity.db.domain.vertex.impl.MessagePackage;
import com.acuity.db.domain.vertex.impl.RSAccount;
import com.acuity.db.domain.vertex.impl.bot_clients.BotClientConfig;
import com.acuity.db.services.impl.BotClientConfigService;
import com.acuity.db.services.impl.BotClientService;
import com.acuity.db.services.impl.RSAccountService;
import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Created by Zachary Herridge on 8/3/2017.
 */
public class BotClientHandler extends MessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(BotClientHandler.class);

    private String ownerID;
    private Vertex botClient;
    private Vertex config;
    private String machineID;
    private String machineKey;

    public BotClientHandler(WSocket wSocket) {
        super(wSocket);
        Events.getDBEventBus().register(this);
    }

    @Override
    public void handle(MessagePackage messagePackage) {
        if (messagePackage.getMessageType() == MessagePackage.Type.MACHINE_INFO){
     /*       String name = messagePackage.getBody("user.name", null);
            if (name != null){
                Machine machine = new Machine(ownerID, machineKey);
                machine.getProperties().putAll(messagePackage.getBody());
                MachineService.getInstance().getCollection().updateDocument(machineKey, machine, new DocumentUpdateOptions().mergeObjects(true));
            }*/
        }
    }

    @Subscribe
    public void onMessageEvent(MessagePackageEvent event){
        if (botClient == null) return;

        if (event.getType() == ArangoEvent.CREATE_OR_UPDATE){
            String destinationKey = event.getMessagePackage().getDestinationKey();
            if (botClient.getKey().equals(destinationKey)){
                getSocket().send(event.getMessagePackage());
                //MessagePackageService.getInstance().delete(event.getMessagePackage());
            }
        }
    }

    @Subscribe
    public void onClose(WSocketEvent.Closed closed){
        Events.getDBEventBus().unregister(this);
        BotClientService.getInstance().removeClient(botClient.getKey());
    }

    @Subscribe
    public void onClientEvent(BotClientIDEvent event){
        if (botClient == null) return;

        if (botClient.getID().equals(event.getBotClientID())){
            if (event instanceof BotClientConfigEvent){
                if (event.getType() == ArangoEvent.CREATE_OR_UPDATE){
                    BotClientConfig config = BotClientConfigService.getInstance().getJoinedConfig(BotClientConfigService.getInstance().getCollectionName() + "/" + botClient.getKey()).orElse(null);
                    getSocket().send(new MessagePackage(MessagePackage.Type.CONFIG_UPDATE, botClient.getKey()).setBody(config));
                }
            }

            if (event instanceof RSAccountAssignedToEvent){
                if (event.getType() == ArangoEvent.DELETE){
                    getSocket().send(new MessagePackage(MessagePackage.Type.ACCOUNT_ASSIGNMENT_CHANGE, botClient.getKey()).setBody(null));
                }
                else {
                    RSAccount rsAccount = RSAccountService.getInstance().getByID(((RSAccountAssignedToEvent) event).getEdge().getFrom()).orElse(null);
                    getSocket().send(new MessagePackage(MessagePackage.Type.ACCOUNT_ASSIGNMENT_CHANGE, botClient.getKey()).setBody(rsAccount));
                }
            }
        }
    }

    @Subscribe
    public void loginComplete(WSocketEvent.LoginComplete loginComplete){
        logger.debug("Login complete");
        AcuityAccount acuityAccount = getSocket().getSession().getAttribute(AcuityAccount.class);
        if (acuityAccount != null){
            ownerID = acuityAccount.getID();

     /*       machineKey = MachineService.getInstance().getKey(acuityAccount.getKey(), loginComplete.getLoginPackage().getBody("machineUsername", null));
            machineID = MachineService.getInstance().getCollectionName() + "/" + machineKey;
            if (!MachineService.getInstance().getCollection().documentExists(machineKey)){
                machineID = MachineService.getInstance().insert(new Machine(ownerID, machineKey)).getId();
            }*/
            BotClientService.getInstance().registerClient(UUID.randomUUID().toString(), acuityAccount.getKey(), "TEMP", getSocket().getSocket().getRemoteSocketAddress().getAddress().toString()).ifPresent(botClient -> {
                this.botClient = botClient;
                BotClientConfigService.getInstance().registerConfig(acuityAccount.getID(), botClient.getKey()).ifPresent(botClientConfig -> {
                    this.config = botClientConfig;
                });
            });
        }
    }
}
