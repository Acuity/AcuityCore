package com.acuity.web.site.views.impl.dashboard.view_container.top_bar;

import com.acuity.web.site.views.View;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by Zachary Herridge on 8/11/2017.
 */
public class TopBar extends HorizontalLayout  {

    public TopBar() {
        buildComponent();
    }

    private void buildComponent(){
        addStyleName("acuity-top-bar");
        setMargin(false);
        setSpacing(false);
        setWidth(100, Unit.PERCENTAGE);

        GridLayout leftGrid = new GridLayout(3, 1);
        addComponent(leftGrid);
        setComponentAlignment(leftGrid, Alignment.MIDDLE_LEFT);
        setExpandRatio(leftGrid, 0f);

        Image logo = new Image(null, new ThemeResource("img/logo_white.png"));
        logo.setWidth(40, Unit.PIXELS);
        logo.setHeight(40, Unit.PIXELS);
        logo.addClickListener(clickEvent -> getUI().getNavigator().navigateTo(View.LANDING.getName()));
        leftGrid.addComponent(logo);
        leftGrid.setComponentAlignment(logo, Alignment.MIDDLE_LEFT);

        Button title = new Button("Acuity Botting", clickEvent -> getUI().getNavigator().navigateTo(View.LANDING.getName()));
        title.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        leftGrid.addComponent(title);
        leftGrid.setComponentAlignment(title, Alignment.MIDDLE_LEFT);

        Button toggleSideMenu = new Button();
        toggleSideMenu.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
        toggleSideMenu.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        toggleSideMenu.addStyleName(ValoTheme.BUTTON_SMALL);
        toggleSideMenu.setIcon(VaadinIcons.MENU);
        leftGrid.addComponent(toggleSideMenu);

        UserProfileWidget userProfileWidget = new UserProfileWidget();
        addComponent(userProfileWidget);
        setComponentAlignment(userProfileWidget, Alignment.MIDDLE_RIGHT);
    }
}
