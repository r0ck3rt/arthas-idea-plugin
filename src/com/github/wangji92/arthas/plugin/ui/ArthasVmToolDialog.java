package com.github.wangji92.arthas.plugin.ui;

import com.github.wangji92.arthas.plugin.common.enums.VmToolActionEnum;
import com.github.wangji92.arthas.plugin.utils.ClipboardUtils;
import com.github.wangji92.arthas.plugin.utils.NotifyUtils;
import com.intellij.icons.AllIcons;
import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.components.labels.ActionLink;
import com.intellij.ui.components.labels.LinkLabel;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Optional;

/**
 * vmtool 利用JVMTI接口，实现查询内存对象，强制GC等功能。
 *
 * @author xc
 */
@SuppressWarnings("unchecked")
public class ArthasVmToolDialog extends JDialog {

    private static final Logger LOG = Logger.getInstance(ArthasVmToolDialog.class);

    private JPanel contentPane;
    /**
     * vmtool --action xx ---classloader xx --classLoaderClass xx -className xx --expand xx --express xx --libPath xx --limit xx
     */
    private static final String VMTOOL_CMD = "vmtool %s %s %s %s %s %s %s %s";
    /**
     * 下拉选个数 预设5个
     */
    private static final int COMBO_PRESET_COUNT = 5;

    /**
     * action 下拉选命令构造
     */
    private JComboBox actionComboBox;
    /**
     * --classLoaderHash 文本命令构造
     */
    private JTextField classLoaderHashTextField;
    /**
     * --classLoaderClass 文本命令构造
     */
    private JTextField classLoaderClassTextField;
    /**
     * express 文本命令构造
     */
    private JTextField expressTextField;
    /**
     * libPath 文本命令构造
     */
    private JTextField libPathTextField;
    /**
     * expand 下拉选命令构造
     */
    private JComboBox<Integer> expandComboBox;
    /**
     * limit 下拉选命令构造
     */
    private JComboBox<Integer> limitComboBox;
    /**
     * classNameLabel
     */
    private JLabel classNameLabel;

    //------------------------------ misc component ------------------------------//
    /**
     * 获取命令的信息
     */
    private JButton vmtoolButton;
    /**
     * 关闭按钮
     */
    private JButton closeButton;
    /**
     * 帮助链接
     */
    private LinkLabel helpLink;
    /**
     * 最佳案列
     */
    private LinkLabel vmtoolBestLink;
    /**
     * 工程信息
     */
    private Project project;


    public ArthasVmToolDialog(Project project, String classname) {
        this.project = project;
        setContentPane(this.contentPane);
        setModal(true);
        getRootPane().setDefaultButton(closeButton);
        this.classNameLabel.setText(classname);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        init();
    }

    private void init() {
        vmtoolButton.addActionListener(e -> {
            String action = Optional.ofNullable(actionComboBox.getSelectedItem()).map(String::valueOf).map(actionType -> "--action " + actionType).orElse("");
            String classloaderHash = StringUtils.isBlank(classLoaderHashTextField.getText()) ? "" : "--classloader ".concat(classLoaderHashTextField.getText());
            String classLoaderClass = StringUtils.isBlank(classLoaderClassTextField.getText()) ? "" : "--classLoaderClass ".concat(classLoaderClassTextField.getText());
            String className = StringUtils.isBlank(classNameLabel.getText()) ? "" : "--className ".concat(classNameLabel.getText());
            String expand = Optional.ofNullable(expandComboBox.getSelectedItem()).map(String::valueOf).map(expandNum -> "--expand " + expandNum).orElse("");
            String express = StringUtils.isBlank(expressTextField.getText()) ? "" : "--express ".concat(expressTextField.getText());
            String libPath = StringUtils.isBlank(libPathTextField.getText()) ? "" : "--libPath ".concat(libPathTextField.getText());
            String limit = Optional.ofNullable(limitComboBox.getSelectedItem()).map(String::valueOf).map(limitNum -> "--limit " + limitNum).orElse("");
            // vmtool --action xx ---classloader xx --classLoaderClass xx -className xx --expand xx --express xx --libPath xx --limit xx
            String text = String.format(VMTOOL_CMD, new String[]{action, classloaderHash, classLoaderClass, className, expand, express, libPath, limit});
            text = text.replaceAll("  ", "");
            if (StringUtils.isNotBlank(text)) {
                ClipboardUtils.setClipboardString(text);
                NotifyUtils.notifyMessageDefault(project);
                LOG.info("cmd:" + text);
            }

        });

        // 预设action下拉选
        for (VmToolActionEnum value : VmToolActionEnum.values()) {
            actionComboBox.addItem(value);
        }

        // 预设expand 和 limit 参数的数字下拉选
        for (int i = 0; i < COMBO_PRESET_COUNT; i++) {
            expandComboBox.addItem(i);
            limitComboBox.addItem(i);
        }

        closeButton.addActionListener(e -> onCancel());
    }

    /**
     * 关闭
     */
    private void onCancel() {
        dispose();
    }


    private void createUIComponents() {
        vmtoolBestLink = new ActionLink("", AllIcons.Ide.Link, new AnAction() {
            @Override
            public void actionPerformed(AnActionEvent anActionEvent) {
                BrowserUtil.browse("https://github.com/alibaba/arthas/issues/482");
            }
        });
        vmtoolBestLink.setPaintUnderline(false);

        helpLink = new ActionLink("", AllIcons.Ide.Link, new AnAction() {
            @Override
            public void actionPerformed(AnActionEvent anActionEvent) {
                BrowserUtil.browse("https://arthas.aliyun.com/doc/tt.html");
            }
        });
        helpLink.setPaintUnderline(false);
    }

    /**
     * 打开窗口
     */
    public void open(String title) {
        setTitle(title);
        pack();
        //两个屏幕处理出现问题，跳到主屏幕去了
        setLocationRelativeTo(WindowManager.getInstance().getFrame(this.project));
        setVisible(true);

    }
}
