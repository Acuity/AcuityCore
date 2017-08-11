package com.acuity.web.site.views.impl.dashboard.view_container.top_bar;

import com.acuity.db.domain.vertex.impl.AcuityAccount;
import com.acuity.web.site.events.DashboardEvent;
import com.acuity.web.site.events.Events;
import com.acuity.web.site.utils.AcuitySession;
import com.acuity.web.site.views.View;
import com.google.common.eventbus.Subscribe;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by Zachary Herridge on 8/11/2017.
 */
public class UserProfileWidget extends HorizontalLayout {

    public UserProfileWidget() {
        buildComponent();
        Events.register(this);
    }

    @Subscribe
    public void onLoginAttempt(DashboardEvent.UserLoginRequestedEvent event){
        removeAllComponents();
        buildComponent();
    }

    @Subscribe
    public void onLogout(DashboardEvent.UserLoggedOutEvent event){
        removeAllComponents();
        buildComponent();
    }

    private void buildComponent(){
        AcuityAccount acuityAccount = AcuitySession.getAccount().orElse(null);
        if (acuityAccount == null) addLogin();
        else addLogout();
    }

    private void addLogin(){
        Button login = new Button("Login");
        login.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        login.addStyleName(ValoTheme.BUTTON_SMALL);
        login.addClickListener(clickEvent -> getUI().getNavigator().navigateTo(View.LOGIN.getName()));
        addComponent(login);
    }

    private void addLogout(){
        Button logout = new Button("Logout");
        logout.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        logout.addStyleName(ValoTheme.BUTTON_SMALL);
        logout.addClickListener(clickEvent -> {
            getUI().getNavigator().navigateTo(View.LOGIN.getName());
            Events.post(new DashboardEvent.UserLoggedOutEvent());
        });
        addComponent(logout);
    }

}
