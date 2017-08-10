package com.acuity.web.site.views.impl.dashboard.script;

import com.acuity.db.domain.vertex.impl.AcuityAccount;
import com.acuity.db.domain.vertex.impl.scripts.Script;
import com.acuity.db.services.impl.ScriptService;
import com.acuity.dropbox.AcuityRepo;
import com.acuity.web.site.components.InlineLabel;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.WriteMode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;

import java.io.*;
import java.util.UUID;

/**
 * Created by Zachary Herridge on 8/7/2017.
 */
public class ScriptView extends VerticalLayout implements View {

    private AcuityAccount acuityAccount = VaadinSession.getCurrent().getAttribute(AcuityAccount.class);
    private Script script;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        script = ScriptService.getInstance().getByKey(event.getParameters()).orElse(null);
        if (script == null) getUI().getNavigator().navigateTo(com.acuity.web.site.views.View.ERROR);
        else buildComponent();
    }

    private void buildComponent() {
        addStyleName("view");

        addComponent(createInfoPanel());

        if (script.getOwnerID().equals(acuityAccount.getID())) {
            addComponent(createUploadPanel());
        }
    }

    private Panel createUploadPanel() {
        ScriptUploader scriptUploader = new ScriptUploader();
        Upload upload = new Upload("Upload it here", scriptUploader);
        upload.addFinishedListener(scriptUploader);
        upload.addFailedListener(scriptUploader);

        Panel uploadPanel = new Panel();
        uploadPanel.setContent(upload);
        return uploadPanel;
    }

    private Panel createInfoPanel() {
        Panel panel = new Panel("<strong>Information</strong>");
        panel.setResponsive(true);
        panel.setCaptionAsHtml(true);

        GridLayout content = new GridLayout(2, 2);
        content.setResponsive(true);
        content.setSpacing(true);
        content.addStyleName("view-top");

        content.addComponents(new InlineLabel("Title:", VaadinIcons.TEXT_LABEL), new Label(script.getTitle()));
        content.addComponents(new InlineLabel("Category:", VaadinIcons.FILE_TREE_SMALL), new Label(script.getCategory()));

        panel.setContent(content);
        return panel;
    }

    class ScriptUploader implements Upload.Receiver, Upload.FailedListener, Upload.FinishedListener {

        private static final long serialVersionUID = 2215337036540966711L;
        private OutputStream outputFile = null;
        private File file;

        @Override
        public OutputStream receiveUpload(String strFilename, String strMIMEType) {
            try {
                file = new File(AcuityRepo.getWorkingDir(), UUID.randomUUID().toString() + ".jar");
                if (!file.exists()) {
                    file.createNewFile();
                }
                outputFile = new FileOutputStream(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return outputFile;
        }


        @Override
        public void uploadFailed(Upload.FailedEvent failedEvent) {
            System.out.println();
        }

        @Override
        public void uploadFinished(Upload.FinishedEvent finishedEvent) {
            try {
                if (outputFile != null) {
                    outputFile.close();
                    Notification.show("Upload Complete", Notification.Type.TRAY_NOTIFICATION);
                }

                FileMetadata fileMetadata = AcuityRepo.getClient().files().uploadBuilder("/" + script.getKey() + "/Script.jar").withMode(WriteMode.OVERWRITE).uploadAndFinish(new FileInputStream(file));
                ScriptService.getInstance().setLink(script.getKey(), AcuityRepo.getClient().sharing().createSharedLinkWithSettings("/" + script.getKey() + "/Script.jar").getUrl());
                Notification.show("Dropbox upload Complete", Notification.Type.TRAY_NOTIFICATION);

            } catch (Throwable exception) {
                exception.printStackTrace();
            }
        }
    }
}
