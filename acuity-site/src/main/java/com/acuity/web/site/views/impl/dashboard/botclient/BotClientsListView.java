package com.acuity.web.site.views.impl.dashboard.botclient;

import com.acuity.db.arango.monitor.events.ArangoEvent;
import com.acuity.db.arango.monitor.events.wrapped.impl.bot.client.id_events.BotClientIDEvent;
import com.acuity.db.arango.monitor.events.wrapped.impl.bot.client.id_events.impl.BotClientEvent;
import com.acuity.db.domain.vertex.impl.AcuityAccount;
import com.acuity.db.domain.vertex.impl.bot_clients.BotClient;
import com.acuity.db.services.impl.BotClientService;
import com.acuity.web.site.components.OpenButton;
import com.acuity.web.site.events.Events;
import com.google.common.eventbus.Subscribe;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.navigator.View;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.MultiSelectionModel;
import com.vaadin.ui.renderers.LocalDateTimeRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zachary Herridge on 8/4/2017.
 */
public class BotClientsListView extends VerticalLayout implements View {

    private AcuityAccount acuityAccount = VaadinSession.getCurrent().getAttribute(AcuityAccount.class);

    private List<BotClient> clients = new ArrayList<>();
    private Grid<BotClient> clientGrid = new Grid<>();
    private MultiSelectionModel<BotClient> clientSelectionModel = (MultiSelectionModel<BotClient>) clientGrid.setSelectionMode(Grid.SelectionMode.MULTI);

    public BotClientsListView() {
        addStyleName("view");
        setSizeFull();
        Events.getDBEventBus().register(this);
        clients.addAll(BotClientService.getInstance().getJoinedByOwnerID(acuityAccount.getID()));
        buildActions();
        buildGrid();
    }

    private void buildActions(){

    }

    private void buildGrid(){
        clientGrid.setDataProvider(DataProvider.ofCollection(clients));
        clientGrid.addColumn(BotClient::getKey).setCaption("Key").setHidden(true);
        clientGrid.addColumn(BotClient::getConnectionTime, new LocalDateTimeRenderer()).setCaption("Connected").setHidden(true);
        Grid.Column<BotClient, String> account = clientGrid.addColumn(botClient -> botClient.getAssignedAccount() != null ? botClient.getAssignedAccount().getEmail() : "None").setCaption("Account");
        clientGrid.sort(account);
        clientGrid.addColumn(botClient -> botClient.getAssignedScript() == null ? "None" : botClient.getAssignedScript().getTitle()).setCaption("Script");
        clientGrid.addColumn(botClient -> botClient.getAssignedProxy() == null ? "None" : botClient.getAssignedProxy().getHost() + ":" + botClient.getAssignedProxy().getPort()).setCaption("Proxy");
        clientGrid.addColumn(botClient -> botClient.getMachine() == null ? "None" : botClient.getMachine().getProperties().getOrDefault("user.name", "")).setCaption("Machine").setHidden(true);
        clientGrid.addColumn(BotClient::getIP).setCaption("IP").setHidden(true);


        clientGrid.addComponentColumn(client -> {
            HorizontalLayout content = new HorizontalLayout();

            OpenButton openButton = new OpenButton(com.acuity.web.site.views.View.CLIENT.getName() + "/" + client.getKey());
            content.addComponent(openButton);

            return content;
        }).setCaption("Actions").setSortable(false);
        clientGrid.setSizeFull();
        clientGrid.setColumnReorderingAllowed(true);
        clientGrid.getColumns().forEach(column -> column.setHidable(true));
        addComponent(new Panel("Clients", clientGrid));
    }

    @Override
    public void detach() {
        Events.getDBEventBus().unregister(this);
        super.detach();
    }

    @Subscribe
    public void onClientEvent(BotClientIDEvent event){
        String clientID = event.getBotClientID();

        if (event.getType() == ArangoEvent.DELETE){
            if (clients.removeIf(botClient -> botClient.getID().equals(clientID))){
                if (!(event instanceof BotClientEvent)){
                    BotClientService.getInstance().getJoinedByID(clientID).ifPresent(clients::add);
                }
                clientGrid.getDataProvider().refreshAll();
            }
        }
        else if (event.getOwnerID().equals(acuityAccount.getID())){
            clients.removeIf(botClient -> botClient.getID().equals(clientID));
            BotClientService.getInstance().getJoinedByID(clientID).ifPresent(clients::add);
            clientGrid.getDataProvider().refreshAll();
        }
    }
}
