package com.acuity.web.site.views.impl.dashboard.script;

import com.acuity.db.domain.edge.impl.AddedScript;
import com.acuity.db.domain.vertex.impl.AcuityAccount;
import com.acuity.db.domain.vertex.impl.scripts.Script;
import com.acuity.db.services.impl.ScriptAddedService;
import com.acuity.db.services.impl.ScriptService;
import com.acuity.web.site.components.OpenButton;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.LocalDateTimeRenderer;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;
import java.util.Optional;

/**
 * Created by Zachary Herridge on 8/7/2017.
 */
public class ScriptsListView extends VerticalLayout implements View {

    private AcuityAccount acuityAccount = VaadinSession.getCurrent().getAttribute(AcuityAccount.class);

    private Grid<Script> grid = new Grid<>();

    public ScriptsListView() {
        addStyleName("view");
        updateScripts();
        buildGrid();
        buildAddScriptButton();
    }

    private void updateScripts() {
        Optional<AcuityAccount> acuityAccount = Optional.ofNullable(this.acuityAccount);
        List<Script> scripts = ScriptService.getInstance().getByAccess(acuityAccount.map(AcuityAccount::getID).orElse(""), Script.Access.PUBLIC.getCode(), acuityAccount.map(AcuityAccount::getRank).orElse(AcuityAccount.Rank.USER));
        grid.setItems(scripts);
        grid.getDataProvider().refreshAll();
    }

    private void buildAddScriptButton() {
        Button addScript = new Button("New Script", clickEvent -> {
            getUI().getNavigator().navigateTo(com.acuity.web.site.views.View.ADD_SCRIPT.getName());
        });
        addComponent(addScript);
    }

    private void buildGrid() {
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        grid.addColumn(Script::getTitle).setCaption("Title");
        grid.addColumn(script -> script.getAuthor().getDisplayName()).setCaption("Author");
        grid.addColumn(Script::getCategory).setCaption("Category");
        grid.addColumn(Script::getLastUpdateTimestamp, new LocalDateTimeRenderer()).setCaption("Updated");
        grid.addColumn(Script::getCreationTimestamp, new LocalDateTimeRenderer()).setCaption("Created").setHidden(true);
        grid.addColumn(Script::getDesc).setCaption("Description").setHidden(true);

        grid.addComponentColumn(script -> {
            HorizontalLayout content = new HorizontalLayout();

            if (acuityAccount != null){
                if (script.getAdded() == null) {
                    Button add = new Button(VaadinIcons.PLUS_CIRCLE);
                    add.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
                    add.addStyleName(ValoTheme.BUTTON_TINY);
                    add.addStyleName(ValoTheme.BUTTON_FRIENDLY);

                    add.addClickListener(clickEvent -> {
                        ScriptAddedService.getInstance().insert(new AddedScript(acuityAccount.getID(), script.getID()));
                        updateScripts();
                    });

                    content.addComponent(add);
                } else {
                    Button remove = new Button(VaadinIcons.MINUS_CIRCLE);
                    remove.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
                    remove.addStyleName(ValoTheme.BUTTON_TINY);
                    remove.addStyleName(ValoTheme.BUTTON_DANGER);

                    remove.addClickListener(clickEvent -> {
                        ScriptAddedService.getInstance().getCollection().deleteDocument(acuityAccount.getKey() + ":" + script.getKey());
                        updateScripts();
                    });
                    content.addComponent(remove);
                }
            }

            OpenButton openButton = new OpenButton(com.acuity.web.site.views.View.SCRIPT.getName() + "/" + script.getKey());
            content.addComponent(openButton);

            return content;
        }).setCaption("Actions").setSortable(false);


        grid.setSizeFull();
        grid.setColumnReorderingAllowed(true);
        grid.getColumns().forEach(column -> column.setHidable(true));
        addComponent(grid);
    }

}
