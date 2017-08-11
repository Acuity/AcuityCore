package com.acuity.web.site.views.impl.dashboard.view_container;

import com.acuity.web.site.events.DashboardEvent;
import com.acuity.web.site.events.Events;
import com.acuity.web.site.utils.AcuitySession;
import com.acuity.web.site.views.View;
import com.google.common.eventbus.Subscribe;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by Zachary Herridge on 8/11/2017.
 */
public class SideNav extends VerticalLayout{

    private VerticalLayout buttons = new VerticalLayout();

    public SideNav() {
        Events.register(this);
        buildComponent();
    }

    private void buildComponent(){
        setMargin(false);
        setSpacing(false);
        setPrimaryStyleName("acuity-menu");
        setHeight(100, Unit.PERCENTAGE);
        setWidth(140, Unit.PIXELS);

        buttons.setMargin(false);
        buttons.setSpacing(false);
        buttons.setWidthUndefined();
        addComponent(buttons);

        buildButtons();
    }

    @Subscribe
    public void onLogin(DashboardEvent.UserLoginRequestedEvent event){
        buildButtons();
    }

    @Subscribe
    public void onLogout(DashboardEvent.UserLoggedOutEvent event){
        buildButtons();
    }

    private void buildButtons(){
        buttons.removeAllComponents();
        for (View view : View.values()) {
            if (view.isNavBar() && view.isAccessible(AcuitySession.getAccount().orElse(null))){
                buttons.addComponent(view.createMenuItem());
            }
        }
    }
}
