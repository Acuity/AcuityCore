package com.acuity.web.site.views;

import com.acuity.db.domain.vertex.impl.AcuityAccount;
import com.acuity.web.site.views.impl.LoginView;
import com.acuity.web.site.views.impl.RegistrationView;
import com.acuity.web.site.views.impl.dashboard.botclient.BotClientView;
import com.acuity.web.site.views.impl.dashboard.botclient.BotClientsListView;
import com.acuity.web.site.views.impl.dashboard.landing.LandingView;
import com.acuity.web.site.views.impl.dashboard.machine.MachineListView;
import com.acuity.web.site.views.impl.dashboard.menu.MenuItem;
import com.acuity.web.site.views.impl.dashboard.proxy.ProxyListView;
import com.acuity.web.site.views.impl.dashboard.rs.account.RSAccountView;
import com.acuity.web.site.views.impl.dashboard.rs.account.RSAccountsListView;
import com.acuity.web.site.views.impl.dashboard.script.AddScriptView;
import com.acuity.web.site.views.impl.dashboard.script.ScriptView;
import com.acuity.web.site.views.impl.dashboard.script.ScriptsListView;
import com.vaadin.icons.VaadinIcons;

import java.util.Optional;

/**
 * Created by Zach on 8/5/2017.
 */
public enum View {

    ACCOUNTS(RSAccountsListView.class, "RS-Accounts", true, VaadinIcons.USER, 1),
    ACCOUNT(RSAccountView.class, "RS-Account", false, null, 1),
    CLIENTS(BotClientsListView.class, "Clients", true, VaadinIcons.CLUSTER, 1),
    CLIENT(BotClientView.class, "Client", false, null, 1),
    ADD_SCRIPT(AddScriptView.class, "AddScript", false, null, 1),
    PROXIES(ProxyListView.class, "Proxies", true, VaadinIcons.CONNECT, 1),
    MACHINES(MachineListView.class, "Machines", true, VaadinIcons.LAPTOP, 1),

    SCRIPTS(ScriptsListView.class, "Scripts", true, VaadinIcons.CODE, 0),
    SCRIPT(ScriptView.class, "Script", false, null, 0),
    LOGIN(LoginView.class, "Login", false, null, 0),
    REGISTER(RegistrationView.class, "Register", false, null, 0),

    LANDING(LandingView.class, "", false, null, 0),
    ;

    public static String ERROR = "ERROR VIEW";

    public static Optional<View> getWithURL(String url){
        for (View view : values()) {
            if (view.getName().equals(url)) return Optional.of(view);
        }
        return Optional.empty();
    }

    private Class<? extends com.vaadin.navigator.View> viewClass;
    private String name;
    private boolean navBar;
    private VaadinIcons icon;
    private int access;

    View(Class<? extends com.vaadin.navigator.View> viewClass, String name, boolean navBar, VaadinIcons icon, int access) {
        this.viewClass = viewClass;
        this.name = name;
        this.navBar = navBar;
        this.icon = icon;
        this.access = access;
    }

    public int getAccess() {
        return access;
    }

    public Class<? extends com.vaadin.navigator.View> getViewClass() {
        return viewClass;
    }

    public String getName() {
        return name;
    }

    public MenuItem createMenuItem(){
        return new MenuItem(this, icon);
    }

    public boolean isNavBar() {
        return navBar;
    }

    public boolean isAccessible(AcuityAccount acuityAccount) {
        if (access == 0) return true;
        else if (acuityAccount != null && access == 1) return true;
        return false;
    }
}
