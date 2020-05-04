package com.github.wangji92.arthas.plugin.action.arthas;

import com.github.wangji92.arthas.plugin.core.ToolkitProject;
import com.github.wangji92.arthas.plugin.ui.AboutMeDialog;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.util.IconLoader;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class AboutSupportAction extends AnAction {

    public AboutSupportAction() {
    }

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        try {
            AboutMeDialog dialog = new AboutMeDialog();
            dialog.setTitle(ToolkitProject.message("About.dialog.title"));
            dialog.setSize(700, 630);
            dialog.setLocationRelativeTo((Component) null);
            dialog.pack();
            dialog.setVisible(true);
            Map<String, String> data = new HashMap();
            data.put("click", "About");
        } catch (Exception ignored) {
            System.out.println(ignored.getMessage());
        }

    }


    @Override
    public void update(AnActionEvent e) {
        if (ToolkitProject.getInstance().pluginHasNewVersion) {
            e.getPresentation().setText("New version is available");
            e.getPresentation().setIcon(IconLoader.getIcon("/icons/update.png"));
        } else {
            e.getPresentation().setText("About...");
            e.getPresentation().setIcon((Icon) null);
        }

    }
}
