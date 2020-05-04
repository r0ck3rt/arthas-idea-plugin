package com.github.wangji92.arthas.plugin.ui;

import com.github.wangji92.arthas.plugin.core.ToolkitProject;
import com.intellij.openapi.ui.Messages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AboutMeDialog extends JDialog {
    private JPanel contentPane;
    private JTextPane aboutMeTextPane;
    private JTextPane supportTextPane;
    private JTextPane envTextPane;
    private JButton buttonClose;

    public AboutMeDialog() {
        this.setContentPane(this.contentPane);
        this.setModal(true);
        this.getRootPane().setDefaultButton(this.buttonClose);
        this.aboutMeTextPane.setText(ToolkitProject.message("About.intro"));
        Messages.configureMessagePaneUi(this.envTextPane, ToolkitProject.message("About.env.text"));
        Messages.configureMessagePaneUi(this.supportTextPane, ToolkitProject.message("About.support.text"));
        this.envTextPane.setFont(new Font((String) null, 0, 10));
        this.aboutMeTextPane.setBackground(this.contentPane.getBackground());
        this.envTextPane.setBackground(this.contentPane.getBackground());
        this.supportTextPane.setBackground(this.contentPane.getBackground());

//        获取os/jdk版本 设备信息等
//        String envStr = null;
//        try {
//            envStr = ToolkitProject.message("About.env.content", new Object[]{PluginUtils.getIdeVersion(), PluginUtils.getOs(), PluginUtils.getJavaVersion(), PluginUtils.getMavenPluginVersion(), DockerUtils.getDockerVersion(), RDataHelper.getInstance().getDeviceId()});
//        } catch (ConnectTimeoutException var3) {
//            ApplicationManager.getApplication().invokeLater(() -> {
//                (new ConnectTimeoutDialog()).show();
//            });
//        }
//        this.envEditorPane.setText(envStr);

        this.buttonClose.addActionListener((e) -> {
            this.onCancel();
        });
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                AboutMeDialog.this.onCancel();
            }
        });
        this.contentPane.registerKeyboardAction((e) -> {
            this.onCancel();
        }, KeyStroke.getKeyStroke(27, 0), 1);
    }

    private void onCancel() {
        this.dispose();
    }

}
