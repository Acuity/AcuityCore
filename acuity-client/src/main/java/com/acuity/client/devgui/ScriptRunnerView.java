/*
 * Created by JFormDesigner on Sun Jun 11 12:11:40 CDT 2017
 */

package com.acuity.client.devgui;

import com.acuity.api.AcuityInstance;
import com.acuity.api.script.ScriptManager;
import com.acuity.api.script.impl.AcuityScript;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * @author unknown
 */
public class ScriptRunnerView extends JFrame {

    private Logger logger = LoggerFactory.getLogger(ScriptRunnerView.class);

    private AcuityScript last;
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Mad Dev
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton resumeScript;
    private JLabel scriptState;
    private JButton button4;


    public ScriptRunnerView() {
        initComponents();
    }

    private void onRunScript(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Jar file", "jar");

        chooser.setFileFilter(filter);

        int returnVal = chooser.showOpenDialog(this);
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File file = chooser.getSelectedFile();
        if (file == null) {
            return;
        }
        AcuityInstance.getScriptManager().runScript(file.getPath());
        final AcuityScript script = AcuityInstance.getScriptManager().getScript();
        last = script;
        scriptState.setText("State: " + script.getState());
    }

    private void onPauseScript(ActionEvent e) {
        final ScriptManager manager = AcuityInstance.getScriptManager();
        if (manager.getScript() == null)
            return;
        manager.pauseScript();
        scriptState.setText("State: " + manager.getScript().getState());
    }

    private void onStopScript(ActionEvent e) {
        final ScriptManager manager = AcuityInstance.getScriptManager();
        if (manager.getScript() == null)
            return;
        manager.stopScript();
        scriptState.setText("State: " + "NO_SCRIPT");
    }

    private void resumeScriptActionPerformed(ActionEvent e) {
        final ScriptManager manager = AcuityInstance.getScriptManager();
        if (manager.getScript() == null)
            return;
        manager.resumeScript();
        scriptState.setText("State: " + manager.getScript().getState());
    }

    private void onReRun(ActionEvent e) {
        if (this.last == null) {
            logger.warn("Last script is null can't rerun.");
            return;
        }
        AcuityInstance.getScriptManager().runScript(this.last);
        final AcuityScript script = AcuityInstance.getScriptManager().getScript();
        scriptState.setText("State: " + script.getState());
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Mad Dev
        button1 = new JButton();
        button2 = new JButton();
        button3 = new JButton();
        resumeScript = new JButton();
        scriptState = new JLabel();
        button4 = new JButton();

        //======== this ========
        setResizable(false);
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- button1 ----
        button1.setText("Run Script");
        button1.addActionListener(e -> onRunScript(e));
        contentPane.add(button1);
        button1.setBounds(30, 30, 185, button1.getPreferredSize().height);

        //---- button2 ----
        button2.setText("Pause Script");
        button2.addActionListener(e -> onPauseScript(e));
        contentPane.add(button2);
        button2.setBounds(30, 70, 185, button2.getPreferredSize().height);

        //---- button3 ----
        button3.setText("Stop Script");
        button3.addActionListener(e -> onStopScript(e));
        contentPane.add(button3);
        button3.setBounds(30, 140, 185, 30);

        //---- resumeScript ----
        resumeScript.setText("Resume Script");
        resumeScript.addActionListener(e -> resumeScriptActionPerformed(e));
        contentPane.add(resumeScript);
        resumeScript.setBounds(30, 105, 185, 30);

        //---- scriptState ----
        scriptState.setText("State: NO_SCRIPT");
        scriptState.setFont(new Font(".SF NS Text", Font.BOLD, 16));
        contentPane.add(scriptState);
        scriptState.setBounds(30, 235, 195, 31);

        //---- button4 ----
        button4.setText("Re-run Last Script");
        button4.addActionListener(e -> onReRun(e));
        contentPane.add(button4);
        button4.setBounds(30, 180, 185, 35);

        contentPane.setPreferredSize(new Dimension(250, 380));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
