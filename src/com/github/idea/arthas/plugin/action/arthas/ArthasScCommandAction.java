package com.github.idea.arthas.plugin.action.arthas;

import com.github.idea.arthas.plugin.utils.ClipboardUtils;
import com.github.idea.arthas.plugin.utils.NotifyUtils;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;

/**
 * sc -d 获取classloader
 *
 * @author 汪小哥
 * @date 21-12-2019
 */
public class ArthasScCommandAction extends BaseArthasPluginAction {
    @Override
    public void doCommand(String className, String methodName, Project project, PsiElement psiElement, Editor editor) {
        String command = String.join(" ", "sc", "-d", className);
        ClipboardUtils.setClipboardString(command);
        String message = NotifyUtils.COMMAND_COPIED + "(View the class information that the JVM has loaded)";
        NotifyUtils.notifyMessageOpenTerminal(project, message, command, editor);
    }
}
