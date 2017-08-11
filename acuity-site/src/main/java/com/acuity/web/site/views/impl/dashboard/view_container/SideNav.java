package com.acuity.web.site.views.impl.dashboard.view_container;

import com.acuity.web.site.views.View;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by Zachary Herridge on 8/11/2017.
 */
public class SideNav extends VerticalLayout{

    public SideNav() {
        buildComponent();
    }

    private VerticalLayout buttons = new VerticalLayout();

    private void buildComponent(){
        setMargin(false);
        setSpacing(false);
        setPrimaryStyleName("acuity-menu");
        setHeight(100, Unit.PERCENTAGE);
        setWidthUndefined();

        buttons.setMargin(false);
        buttons.setSpacing(false);
        buttons.setWidthUndefined();
        addComponent(buttons);

        buildButtons();
    }

    private void buildButtons(){
        buttons.removeAllComponents();
        for (View view : View.values()) {
            if (view.isNavBar()){
                buttons.addComponent(view.createMenuItem());
            }
        }
    }
}
