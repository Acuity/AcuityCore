package com.acuity.web.site.views.impl.dashboard.view_container;

import com.acuity.web.site.DashboardNavigator;
import com.acuity.web.site.views.impl.dashboard.view_container.top_bar.TopBar;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by Zachary Herridge on 8/11/2017.
 */
public class ViewContainer extends VerticalLayout implements View {

    private DashboardNavigator dashboardNavigator;
    private ComponentContainer container = new CssLayout();
    private TopBar topBar = new TopBar();
    private SideNav sideNav = new SideNav();
    private Footer footer = new Footer();

    public ViewContainer() {
        buildComponent();
    }

    private void buildComponent() {
        setSizeFull();
        addStyleName("mainview");
        setMargin(false);
        setSpacing(false);

        addComponent(topBar);

        HorizontalLayout midContainer = new HorizontalLayout();
        midContainer.setSizeFull();

        Panel scrollable = new Panel();
        scrollable.addStyleName(ValoTheme.PANEL_BORDERLESS);
        scrollable.setContent(container);
        scrollable.setSizeFull();
        midContainer.addComponents(sideNav, scrollable);


        container.addStyleName("smallMargin");
        container.addStyleName("view-content");
        container.setWidth(100, Unit.PERCENTAGE);
        midContainer.setExpandRatio(scrollable, 1.0f);

        addComponents(midContainer, footer);
        setExpandRatio(midContainer, 1.0f);


        dashboardNavigator = new DashboardNavigator(container);
    }

    public ComponentContainer getContainer() {
        return container;
    }

    public DashboardNavigator getDashboardNavigator() {
        return dashboardNavigator;
    }
}
