package com.acuity.web.site.views.impl.dashboard.menu;

import com.acuity.web.site.events.DashboardEvent;
import com.acuity.web.site.events.Events;
import com.acuity.web.site.views.View;
import com.google.common.eventbus.Subscribe;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by Zachary Herridge on 8/1/2017.
 */
public class MenuItem extends Button {

    private static final String STYLE_SELECTED = "acuity-selected";

    private View view;

    public MenuItem(View view, VaadinIcons icon) {
        this.view = view;
        setWidthUndefined();
        setCaption(view.getName());
        setIcon(icon);
        addStyleName("acuity-menu-item");
        addStyleName(ValoTheme.BUTTON_SMALL);
        addStyleName(ValoTheme.BUTTON_BORDERLESS);
        Events.register(this);
        addClickListener(clickEvent ->  {
            UI.getCurrent().getNavigator().navigateTo(view.getName());
        });
    }

    @Subscribe
    public void onViewChange(DashboardEvent.ViewChange viewChange){
        removeStyleName(STYLE_SELECTED);
        if (viewChange.getView().equals(view.getName())) {
            addStyleName(STYLE_SELECTED);
        }
    }
}
