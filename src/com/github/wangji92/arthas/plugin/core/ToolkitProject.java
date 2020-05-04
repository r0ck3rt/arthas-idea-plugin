package com.github.wangji92.arthas.plugin.core;

import com.intellij.openapi.components.AbstractProjectComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.updateSettings.impl.PluginDownloader;

import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

public class ToolkitProject extends AbstractProjectComponent {
    public boolean pluginHasNewVersion = false;
    private static final ResourceBundle resBundle;
    private static ToolkitProject myProject;
    private Project project;

    private ToolkitProject(Project project) {
        super(project);
        this.project = project;
        myProject = this;
    }

    public Project getProject() {
        return this.project;
    }

    @Override
    public void projectOpened() {
//        (new Thread(() -> {
//            PluginDownloader pd = PluginUtils.checkUpdateAndReturnDownloader("com.alibabacloud.intellij.toolkit-intellij");
//            if (pd == null) {
//                this.pluginHasNewVersion = false;
//            } else {
//                this.pluginHasNewVersion = true;
//                VersionChecker.getInstance().notifyNewVersion(this.project, pd);
//            }
//
//        })).start();
    }

    @Override
    public void projectClosed() {
    }
    public static synchronized ToolkitProject getInstance() {
        return myProject;
    }

    public static String message(String msgKey) {
        return resBundle.getString(msgKey);
    }

    public static String message(String msgKey, Object... params) {
        return String.format(resBundle.getString(msgKey), params);
    }

    static {
        resBundle = ResourceBundle.getBundle("messages.toolkitMessage", Control.getControl(Control.FORMAT_PROPERTIES));
    }
}
