package com.acuity.web.site.views.impl.dashboard.menu;

import com.acuity.web.site.events.DashboardEvent;
import com.acuity.web.site.events.Events;
import com.acuity.web.site.views.View;
import com.google.common.eventbus.Subscribe;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;

/**
 * Created by Zachary Herridge on 8/1/2017.
 */
public class MenuItem extends Button {

    private static final String STYLE_SELECTED = "selected";

    private View view;

    public MenuItem(View view, VaadinIcons icon) {
        this.view = view;
        setWidthUndefined();
        setPrimaryStyleName("valo-menu-item");
        setCaption(view.getName());
        setIcon(icon);
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
