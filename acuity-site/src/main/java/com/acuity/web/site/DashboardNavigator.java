package com.acuity.web.site;

import com.acuity.db.domain.vertex.impl.AcuityAccount;
import com.acuity.web.site.events.DashboardEvent;
import com.acuity.web.site.events.Events;
import com.acuity.web.site.views.View;
import com.acuity.web.site.views.impl.ErrorView;
import com.vaadin.navigator.NavigationStateManager;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewProvider;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.UI;

/**
 * Created by Zachary Herridge on 8/1/2017.
 */
public class DashboardNavigator extends Navigator {

    public DashboardNavigator(ComponentContainer container) {
        super(UI.getCurrent(), container);

        addViewChangeListener(viewChangeEvent -> {
            Events.post(new DashboardEvent.ViewChange(viewChangeEvent.getViewName()));
            Events.post(new DashboardEvent.BrowserResizeEvent());
            Events.post(new DashboardEvent.CloseOpenWindowsEvent());
            return true;
        });

        setErrorProvider(new ViewProvider() {
            @Override
            public String getViewName(String s) {
                return View.ERROR;
            }

            @Override
            public com.vaadin.navigator.View getView(String s) {
                return new ErrorView();
            }
        });
    }

    @Override
    protected NavigationStateManager getStateManager() {
        return super.getStateManager();
    }

    public void initViewProviders(AcuityAccount acuityAccount) {
        for (View view : View.values()) {
            if (view.isAccessible(acuityAccount)){
                ViewProvider viewProvider = new ClassBasedViewProvider(view.getName(), view.getViewClass());
                addProvider(viewProvider);
            }
        }
    }
}
