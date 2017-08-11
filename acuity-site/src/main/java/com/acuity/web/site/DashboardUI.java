package com.acuity.web.site;

import com.acuity.db.domain.vertex.impl.AcuityAccount;
import com.acuity.db.services.impl.AcuityAccountService;
import com.acuity.web.site.events.DashboardEvent;
import com.acuity.web.site.events.Events;
import com.acuity.web.site.views.View;
import com.acuity.web.site.views.impl.dashboard.view_container.ViewContainer;
import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import java.util.Locale;

/**
 * Created by Zach on 7/31/2017.
 */
@Push
@Theme("dashboard")
@Title("Acuity Botting")
public class DashboardUI extends UI {

    private Events events = new Events();
    private ViewContainer mainView;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Events.register(this);
        setLocale(Locale.US);
        Responsive.makeResponsive(this);
        addStyleName(ValoTheme.UI_WITH_MENU);
        updateContent();
        Page.getCurrent().addBrowserWindowResizeListener(browserWindowResizeEvent -> Events.post(new DashboardEvent.BrowserResizeEvent()));
    }

    private void updateContent(){
        mainView = new ViewContainer();
        AcuityAccount acuityAccount = VaadinSession.getCurrent().getAttribute(AcuityAccount.class);
        mainView.getDashboardNavigator().initViewProviders(acuityAccount);
        setContent(mainView);
        if (acuityAccount != null){
            getNavigator().navigateTo(getNavigator().getState());
        }
        else {
            getNavigator().navigateTo(View.LOGIN.getName());
        }
    }

    @Subscribe
    public void userLoginRequested(final DashboardEvent.UserLoginRequestedEvent event) {
        AcuityAccount acuityAccount = AcuityAccountService.getInstance().checkLoginByEmail(event.getUserName(), event.getPassword()).orElse(null);
        if (acuityAccount != null){
            getSession().setAttribute(AcuityAccount.class, acuityAccount);
            if (acuityAccount.isAllowPasswordMemoryStoring()) getSession().setAttribute("passwordStore", event.getPassword());
            mainView.getDashboardNavigator().initViewProviders(acuityAccount);
            Notification.show("Welcome  back " + acuityAccount.getDisplayName() + "!", Notification.Type.TRAY_NOTIFICATION);
        }
        else {
            Notification.show("Incorrect Acuity Login.", Notification.Type.TRAY_NOTIFICATION);
        }
        updateContent();
    }

    @Subscribe
    public void userLoggedOut(final DashboardEvent.UserLoggedOutEvent event) {
        VaadinSession.getCurrent().close();
        Page.getCurrent().reload();
    }

    @Subscribe
    public void closeOpenWindows(final DashboardEvent.CloseOpenWindowsEvent event) {
        getWindows().forEach(Window::close);
    }

    @Override
    public void detach() {
        Events.unregister(this);
    }

    public static Events getEventBus() {
        return ((DashboardUI) getCurrent()).events;
    }
}
