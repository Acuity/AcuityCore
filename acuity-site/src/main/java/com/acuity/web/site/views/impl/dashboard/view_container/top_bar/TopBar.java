package com.acuity.web.site.views.impl.dashboard.view_container.top_bar;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
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

        GridLayout leftGrid = new GridLayout(2, 1);
        addComponent(leftGrid);
        setComponentAlignment(leftGrid, Alignment.MIDDLE_LEFT);
        setExpandRatio(leftGrid, 0f);

        Label title = new Label("<strong>Acuity Botting</strong>", ContentMode.HTML);
        leftGrid.addComponent(title);
        leftGrid.setComponentAlignment(title, Alignment.MIDDLE_LEFT);

        Button toggleSideMenu = new Button();
        toggleSideMenu.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
        toggleSideMenu.setIcon(VaadinIcons.MENU);
        toggleSideMenu.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        leftGrid.addComponent(toggleSideMenu);

        UserProfileWidget userProfileWidget = new UserProfileWidget();
        addComponent(userProfileWidget);
        setComponentAlignment(userProfileWidget, Alignment.MIDDLE_RIGHT);
    }
}
