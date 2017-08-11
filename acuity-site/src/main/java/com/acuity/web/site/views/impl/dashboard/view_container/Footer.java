package com.acuity.web.site.views.impl.dashboard.view_container;

import com.vaadin.ui.HorizontalLayout;

/**
 * Created by Zachary Herridge on 8/11/2017.
 */
public class Footer extends HorizontalLayout{

    public Footer() {
        buildComponent();
    }

    private void buildComponent(){
        addStyleName("acuity-top-bar");
        setHeight(25, Unit.PIXELS);
        setWidth(100, Unit.PERCENTAGE);
    }
}
