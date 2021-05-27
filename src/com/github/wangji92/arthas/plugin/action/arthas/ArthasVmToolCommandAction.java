package com.github.wangji92.arthas.plugin.action.arthas;

import com.github.wangji92.arthas.plugin.ui.ArthasVmToolDialog;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;

/**
 * vmtool 利用JVMTI接口，实现查询内存对象，强制GC等功能。
 *
 * @author xc
 */
public class ArthasVmToolCommandAction extends BaseArthasPluginAction {

    @Override
    public void doCommand(String className, String methodName, Project project, PsiElement psiElement) {
        new ArthasVmToolDialog(project, className).open("arthas VmTool use");
    }
}
