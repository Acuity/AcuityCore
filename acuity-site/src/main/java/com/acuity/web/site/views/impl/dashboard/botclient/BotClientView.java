package com.acuity.web.site.views.impl.dashboard.botclient;

import com.acuity.db.arango.monitor.events.ArangoEvent;
import com.acuity.db.arango.monitor.events.wrapped.impl.BotClientEvent;
import com.acuity.db.arango.monitor.events.wrapped.impl.RSAccountAssignedToEvent;
import com.acuity.db.domain.edge.impl.AssignedTo;
import com.acuity.db.domain.vertex.impl.AcuityAccount;
import com.acuity.db.domain.vertex.impl.MessagePackage;
import com.acuity.db.domain.vertex.impl.RSAccount;
import com.acuity.db.domain.vertex.impl.bot_clients.BotClient;
import com.acuity.db.domain.vertex.impl.scripts.Script;
import com.acuity.db.services.impl.*;
import com.acuity.web.site.components.InlineLabel;
import com.acuity.web.site.events.Events;
import com.google.common.eventbus.Subscribe;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;

import java.util.List;

/**
 * Created by Zach on 8/4/2017.
 */
public class BotClientView extends VerticalLayout implements View {

    private AcuityAccount acuityAccount = VaadinSession.getCurrent().getAttribute(AcuityAccount.class);
    private BotClient botClient;

    private ComboBox<RSAccount> assignedAccount = new ComboBox<>();
    private ComboBox<Script> assignedScript = new ComboBox<>();

    private void build(){
        addStyleName("view");
        Events.getDBEventBus().register(this);

        addInformationPanel();

        Button killBot = new Button("Kill Bot", clickEvent -> {
            MessagePackageService.getInstance().insert(new MessagePackage(MessagePackage.Type.DIRECT)
                    .putHeader("destinationKey", botClient.getKey())
                    .putBody("command", "kill-bot"));
        });
        killBot.setIcon(VaadinIcons.CLOSE_CIRCLE_O);
        addComponent(killBot);
    }

    private void addInformationPanel(){
        Panel panel = new Panel("<strong>Information</strong>");
        panel.setResponsive(true);
        panel.setCaptionAsHtml(true);

        GridLayout content = new GridLayout(2, 4);
        content.setResponsive(true);
        content.setSpacing(true);
        content.addStyleName("view-top");

        content.addComponent(new InlineLabel("Client Key:", VaadinIcons.KEY_O));
        content.addComponent(new Label(botClient.getKey()));

        content.addComponent(new InlineLabel("Connected:", VaadinIcons.TIMER));
        content.addComponent(new Label(botClient.getConnectionTime().toString()));

        Label accountLabel = new InlineLabel("Account:", VaadinIcons.USER);
        content.addLayoutClickListener(layoutClickEvent -> {
            if (layoutClickEvent.getClickedComponent() != null && layoutClickEvent.getClickedComponent().equals(accountLabel)){
                assignedAccount.getSelectedItem().ifPresent(account -> {
                    getUI().getNavigator().navigateTo(com.acuity.web.site.views.View.ACCOUNT.getName() + "/" + account.getKey());
                });
            }
        });
        content.addComponent(accountLabel);
        content.addComponent(createAccountComboBox());

        Label scriptLabel = new InlineLabel("Script:", VaadinIcons.CODE);


        content.addComponent(scriptLabel);
        content.addComponent(createScriptComboBox());

        panel.setContent(content);
        addComponent(panel);
    }

    private ComboBox createScriptComboBox(){
        assignedScript.setHeight(25, Unit.PIXELS);
        assignedScript.setWidth(100, Unit.PERCENTAGE);
        assignedScript.setItemCaptionGenerator(script -> script.getTitle());
        List<Script> addedScripts = ScriptAddedService.getInstance().getAdded(acuityAccount.getID());
        assignedScript.setItems(addedScripts);
        ScriptService.getInstance().getByID(botClient.getClientConfig().getAssignedScriptID()).ifPresent(script -> {
            assignedScript.setSelectedItem(script);
        });
        assignedScript.addSelectionListener(singleSelectionEvent -> {
            if (singleSelectionEvent.isUserOriginated()){
                singleSelectionEvent.getFirstSelectedItem().ifPresent(script -> {
                    BotClientConfigService.getInstance().assignScript(BotClientConfigService.getInstance().getCollectionName() + "/" + botClient.getKey(), script.getID());
                });
            }
        });
        return assignedScript;
    }

    private ComboBox createAccountComboBox(){
        assignedAccount.setHeight(25, Unit.PIXELS);
        assignedAccount.setWidth(100, Unit.PERCENTAGE);
        List<RSAccount> byOwner = RSAccountService.getInstance().getByOwner(acuityAccount.getID());
        assignedAccount.setDataProvider(new ListDataProvider<RSAccount>(byOwner));
        assignedAccount.setItemCaptionGenerator(RSAccount::getEmail);
        RSAccountAssignmentService.getInstance().getByToID(botClient.getID()).stream().findFirst().ifPresent(assignedTo -> RSAccountService.getInstance().getByID(assignedTo.getFrom()).ifPresent(account -> {
            assignedAccount.setSelectedItem(account);
        }));
        assignedAccount.addSelectionListener(singleSelectionEvent -> {
            if (singleSelectionEvent.isUserOriginated()){
                RSAccount selectedAccount = singleSelectionEvent.getFirstSelectedItem().orElse(null);
                if (selectedAccount != null){
                    RSAccountAssignmentService.getInstance().insert(new AssignedTo(acuityAccount.getID(), selectedAccount.getID(), botClient.getID()));
                }
                else {
                    RSAccountAssignmentService.getInstance().removeByToID(botClient.getID());
                }
            }
        });

        return assignedAccount;
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        BotClientService.getInstance().getJoinedByID(BotClientService.getInstance().getCollectionName() + "/" + event.getParameters()).ifPresent(result -> {
            if (result.getOwnerID().equals(acuityAccount.getID())){
                botClient = result;
                Events.getDBEventBus().register(this);
            }
        });
        if (botClient == null) getUI().getNavigator().navigateTo("ERROR VIEW");
        else build();
    }

    @Override
    public void detach() {
        Events.getDBEventBus().unregister(this);
        super.detach();
    }


    @Subscribe
    public void onAssignmentUpdate(RSAccountAssignedToEvent event){
        String clientID = BotClientService.getInstance().getCollectionName() + "/" + event.getEdge().getKey();

        if (clientID.equals(botClient.getID())){
            if (event.getType() == ArangoEvent.DELETE){
                getUI().access(() -> assignedAccount.clear());
            }
            else if (event.getEdge().getOwnerID().equals(acuityAccount.getID())){
                RSAccountService.getInstance().getByID(event.getEdge().getFrom()).ifPresent(account -> {
                    getUI().access(() -> assignedAccount.setSelectedItem(account));
                });
            }
        }
    }

    @Subscribe
    public void onBotClientEvent(BotClientEvent event) {
        if (event.getBotClient().getKey().equals(botClient.getKey())){
            if (event.getType() == ArangoEvent.DELETE) {
                getUI().access(() -> getUI().getNavigator().navigateTo(com.acuity.web.site.views.View.CLIENTS.getName()));
            }
            else if (event.getBotClient().getID().equals(botClient.getID())) {

            }
        }
    }
}
