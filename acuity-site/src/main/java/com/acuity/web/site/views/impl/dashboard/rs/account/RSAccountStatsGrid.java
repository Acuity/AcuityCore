package com.acuity.web.site.views.impl.dashboard.rs.account;

import com.acuity.db.arango.monitor.events.ArangoEvent;
import com.acuity.db.arango.monitor.events.wrapped.impl.RSAccountEvent;
import com.acuity.db.domain.common.tracking.RSAccountState;
import com.acuity.db.domain.common.tracking.StatLevel;
import com.acuity.db.domain.vertex.impl.RSAccount;
import com.acuity.web.site.events.Events;
import com.google.common.eventbus.Subscribe;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Panel;
import com.vaadin.ui.ProgressBar;
import javafx.util.Pair;

import java.util.*;

/**
 * Created by Zach on 8/13/2017.
 */
public class RSAccountStatsGrid extends Grid<Map.Entry<String, Integer>> {


    private RSAccount rsAccount;
    private List<Map.Entry<String, Integer>> skillPairs = new ArrayList<>();

    public RSAccountStatsGrid(RSAccount rsAccount) {
        this.rsAccount = rsAccount;
        refreshStats();
        Events.getDBEventBus().register(this);
        buildComponent();
    }

    private void refreshStats(){
        if (rsAccount.getState() != null){
            skillPairs.clear();
            skillPairs.addAll(rsAccount.getState().map(RSAccountState::getSkillExperience).orElse(new HashMap<>()).entrySet());
            getDataProvider().refreshAll();
        }
    }

    private void buildComponent(){
        addColumn(Map.Entry::getKey).setCaption("Skill");
        addComponentColumn(entry -> {
            ProgressBar progressBar = new ProgressBar(StatLevel.getPercentToLevel(entry.getValue()));
            progressBar.setSizeFull();
            return progressBar;
        }).setCaption("Progress");
        sort(addColumn(entry -> StatLevel.getLevelAtXP(entry.getValue())).setCaption("Level"), SortDirection.DESCENDING);
        sort(addColumn(Map.Entry::getValue).setCaption("Experience").setHidden(true), SortDirection.DESCENDING);


        setColumnReorderingAllowed(true);
        getColumns().forEach(column -> column.setHidable(true));

        setDataProvider(DataProvider.ofCollection(skillPairs));
    }

    @Subscribe
    public void onRSAccountUpdate(RSAccountEvent accountEvent){
        if (accountEvent.getType() == ArangoEvent.CREATE_OR_UPDATE && this.rsAccount.getKey().equals(accountEvent.getRsAccount().getKey())){
            this.rsAccount = accountEvent.getRsAccount();
            refreshStats();
        }
    }

    @Override
    public void detach() {
        super.detach();
        Events.getDBEventBus().unregister(this);
    }
}
