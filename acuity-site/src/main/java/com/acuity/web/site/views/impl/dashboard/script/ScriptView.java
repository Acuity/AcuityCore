package com.acuity.web.site.views.impl.dashboard.script;

import com.acuity.db.domain.vertex.impl.AcuityAccount;
import com.acuity.db.domain.vertex.impl.scripts.Script;
import com.acuity.db.services.impl.ScriptService;
import com.acuity.dropbox.AcuityRepo;
import com.acuity.web.site.components.InlineLabel;
import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.WriteMode;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;

import java.io.*;
import java.util.List;
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

        Panel uploadPanel = new Panel("Developer Controls");
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

    class ScriptUploader implements Upload.Receiver, Upload.StartedListener, Upload.FailedListener, Upload.FinishedListener {

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


                AcuityRepo.getClient().files().uploadBuilder("/" + script.getKey() + "/Script.jar").withMode(WriteMode.OVERWRITE).uploadAndFinish(new FileInputStream(file));
                Notification.show("Dropbox upload Complete", Notification.Type.TRAY_NOTIFICATION);


                List<SharedLinkMetadata> links = AcuityRepo.getClient().sharing().listSharedLinksBuilder().withPath("/" + script.getKey() + "/Script.jar").withDirectOnly(true).start().getLinks();
                String link = links.stream().findAny().map(SharedLinkMetadata::getUrl).orElseGet(() -> {
                    try {
                        return AcuityRepo.getClient().sharing().createSharedLinkWithSettings("/" + script.getKey() + "/Script.jar").getUrl();
                    } catch (DbxException e) {
                        e.printStackTrace();
                    }
                    return null;
                });

                if (link != null){
                    ScriptService.getInstance().setLink(script.getKey(), link);
                    Notification.show("Update link complete.", Notification.Type.TRAY_NOTIFICATION);
                }
            } catch (Throwable exception) {
                exception.printStackTrace();
            }
        }

        @Override
        public void uploadStarted(Upload.StartedEvent startedEvent) {
            Notification.show("Starting Upload", Notification.Type.TRAY_NOTIFICATION);
        }
    }
}
