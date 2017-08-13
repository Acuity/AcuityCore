package com.acuity.web.site.views.impl.dashboard.rs.account;

import com.acuity.db.arango.monitor.events.wrapped.impl.RSAccountEvent;
import com.acuity.db.domain.common.tracking.RSAccountState;
import com.acuity.db.domain.vertex.impl.AcuityAccount;
import com.acuity.db.domain.vertex.impl.RSAccount;
import com.acuity.db.services.impl.RSAccountService;
import com.acuity.security.AcuityEncryption;
import com.acuity.web.site.components.InlineLabel;
import com.acuity.web.site.events.Events;
import com.acuity.web.site.views.impl.dashboard.modals.ConfirmPasswordModal;
import com.google.common.base.Strings;
import com.google.common.eventbus.Subscribe;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by Zachary Herridge on 8/7/2017.
 */
public class RSAccountView extends VerticalLayout implements View{

    private AcuityAccount acuityAccount = VaadinSession.getCurrent().getAttribute(AcuityAccount.class);
    private RSAccount rsAccount;
    private boolean passwordShown = false;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        RSAccountService.getInstance().getByKey(event.getParameters()).ifPresent(result -> {
            if (result.getOwnerID().equals(acuityAccount.getID())){
                rsAccount = result;
                Events.getDBEventBus().register(this);
            }
        });
        if (rsAccount == null) getUI().getNavigator().navigateTo("ERROR VIEW");
        else buildComponent();
    }

    @Subscribe
    public void onRSAcccountEvent(RSAccountEvent event){

    }

    public void buildComponent(){
        addStyleName("view");
        Events.getDBEventBus().register(this);
        addInformationPanel();
        addStatePanel();
        addSkillsPanel();
    }

    private void addStatePanel(){
        GridLayout content = new GridLayout(2, 3);
        content.setResponsive(true);
        content.setSpacing(true);
        content.addStyleName("view-top");

        content.addComponent(new InlineLabel("Health:", VaadinIcons.DOCTOR_BRIEFCASE));
        content.addComponent(new Label(rsAccount.getState().map(RSAccountState::getHpPercent).orElse(-1) + "%"));

        content.addComponent(new InlineLabel("Prayer Points:", VaadinIcons.MAGIC));
        content.addComponent(new Label(String.valueOf(rsAccount.getState().map(RSAccountState::getPrayerPoints).orElse(-1))));

        content.addComponent(new InlineLabel("Run Energy:", VaadinIcons.EXIT));
        content.addComponent(new Label(String.valueOf(rsAccount.getState().map(RSAccountState::getRunEnergy).orElse(-1))));


        Panel panel = new Panel("<strong>State</strong>");
        panel.setResponsive(true);
        panel.setCaptionAsHtml(true);
        panel.setContent(content);
        addComponent(panel);
    }

    private void addSkillsPanel(){
        RSAccountStatsGrid statsGrid = new RSAccountStatsGrid(rsAccount);
        statsGrid.setSizeFull();
        Panel panel = new Panel("<strong>Stats</strong>");
        panel.setResponsive(true);
        panel.setCaptionAsHtml(true);
        panel.setContent(statsGrid);
        addComponent(panel);
    }

    private void addInformationPanel() {
        Panel panel = new Panel("<strong>Information</strong>");
        panel.setResponsive(true);
        panel.setCaptionAsHtml(true);
        panel.setContent(createInformationGrid());
        addComponent(panel);
    }

    private GridLayout createInformationGrid(){
        GridLayout content = new GridLayout(2, 3);
        content.setResponsive(true);
        content.setSpacing(true);
        content.addStyleName("view-top");

        content.addComponent(new InlineLabel("Email:", VaadinIcons.MAILBOX));
        content.addComponent(new Label(rsAccount.getEmail()));

        content.addComponent(new InlineLabel("IGN:", VaadinIcons.CHAT));
        content.addComponent(new Label(rsAccount.getIgn()));

        content.addComponent(new InlineLabel("Password:", VaadinIcons.PASSWORD));

        Button passwordBttn = new Button(Strings.repeat("*", 10));
        passwordBttn.setStyleName(ValoTheme.BUTTON_BORDERLESS);
        passwordBttn.addStyleName("grid-button");
        passwordBttn.setHeight(25, Unit.PIXELS);
        passwordBttn.addClickListener(clickEvent -> {
            if (!passwordShown) {
                final Window window = new Window("Confirm Acuity Password");
                window.setWidth(360.0f, Unit.PIXELS);
                ConfirmPasswordModal confirmPasswordModal = new ConfirmPasswordModal(){
                    @Override
                    public void onConfirm(String acuityPassword, AcuityAccount acuityAccount) {
                        if (acuityAccount != null){
                            try {
                                String password1 = AcuityEncryption.decryptString(rsAccount.getPassword(), acuityPassword, acuityAccount.getPasswordEncryptionKey());
                                passwordBttn.setCaption(password1);
                                passwordShown = !passwordShown;
                            } catch (Exception e) {
                                Notification.show("Error: " + e.getLocalizedMessage(), Notification.Type.TRAY_NOTIFICATION);
                            }
                        }
                        else {
                            Notification.show("Incorrect Acuity Login.", Notification.Type.TRAY_NOTIFICATION);
                        }
                        window.close();
                    }
                };
                confirmPasswordModal.setMargin(true);
                window.setModal(true);
                window.setContent(confirmPasswordModal);
                getUI().addWindow(window);
            }
            else {
                passwordBttn.setCaption(Strings.repeat("*", 10));
                passwordShown = !passwordShown;
            }

        });
        content.addComponent(passwordBttn);
        return content;
    }

    @Override
    public void detach() {
        Events.getDBEventBus().unregister(this);
        super.detach();
    }
}
