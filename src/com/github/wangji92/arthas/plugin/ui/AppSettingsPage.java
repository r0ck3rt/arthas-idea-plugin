package com.github.wangji92.arthas.plugin.ui;

import com.aliyun.oss.OSS;
import com.github.wangji92.arthas.plugin.constants.ArthasCommandConstants;
import com.github.wangji92.arthas.plugin.setting.AppSettingsState;
import com.github.wangji92.arthas.plugin.utils.*;
import com.intellij.icons.AllIcons;
import com.intellij.ide.BrowserUtil;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.project.Project;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.labels.ActionLink;
import com.intellij.ui.components.labels.LinkLabel;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.Jedis;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import static com.github.wangji92.arthas.plugin.constants.ArthasCommandConstants.AT;

/**
 * https://jetbrains.org/intellij/sdk/docs/reference_guide/settings_guide.html 属性配置 参考
 * https://github.com/pwielgolaski/shellcheck-plugin
 *
 * @author 汪小哥
 * @date 15-08-2020
 */
public class AppSettingsPage implements Configurable {
    /**
     * arthas 的设置
     */
    private JTextField springContextStaticOgnlExpressionTextFiled;
    /**
     * arthas -n
     */
    private JSpinner invokeCountField;
    private LinkLabel linkLabel;

    private JPanel contentPane;
    /**
     * 跳过jdk trace
     */
    private JRadioButton traceSkipJdkRadio;
    /**
     * 调用次数
     */
    private JSpinner invokeMonitorCountField;
    /**
     * 时间间隔
     */
    private JSpinner invokeMonitorIntervalField;

    /**
     * 打印属性的深度
     */
    private JSpinner depthPrintPropertyField;
    /**
     * 是否展示默认的条件表达式
     */
    private JRadioButton conditionExpressDisplayRadio;

    private JTextField selectProjectNameTextField;

    private LinkLabel selectLink;
    private LinkLabel batchSupportLink;
    private LinkLabel redefineHelpLinkLabel;
    private LinkLabel ossHelpLink;
    /**
     * 主pane
     */
    private JTabbedPane settingTabPane;
    /**
     * 基础设置pane
     */
    private JPanel basicSettingPane;
    /**
     * 热更新 面板
     */
    private JPanel hotRedefineSettingPane;
    /**
     * 设置选中 剪切板
     */
    private JRadioButton clipboardRadioButton;
    /**
     * 设置选中 阿里云
     */
    private JRadioButton aliYunOssRadioButton;
    /**
     * oss 配置信息 Endpoint
     */
    private JTextField ossEndpointTextField;
    /**
     * oss 配置信息 AccessKeyId
     */
    private JPasswordField ossAccessKeyIdPasswordField;
    /**
     * oss 配置信息 AccessKeySecret
     */
    private JPasswordField ossAccessKeySecretPasswordField;
    /**
     * oss 配置信息 DirectoryPrefix
     */
    private JTextField ossDirectoryPrefixTextField;
    /**
     * oss 配置信息 BucketName
     */
    private JTextField ossBucketNameTextField;
    /**
     * 检测 oss 配置是否正确 button
     */
    private JButton ossSettingCheckButton;

    /**
     * 检测异常的信息
     */
    private JLabel ossCheckMsgLabel;

    /**
     * 阿里云Oss Setting Pane
     */
    private JPanel aliyunOssSettingPane;
    /**
     * 全局spring context 开关
     */
    private JRadioButton springContextGlobalSettingRadioButton;
    /**
     * oss 全局开关
     */
    private JRadioButton ossGlobalSettingRadioButton;
    /**
     * 热更新完成后删除文件
     */
    private JRadioButton hotRedefineDeleteFileRadioButton;
    /**
     * watch/trace/monitor support verbose option, print ConditionExpress result #1348
     */
    private JRadioButton printConditionExpressRadioButton;

    private LinkLabel printConditionExpressLink;
    /**
     * 热更新之前先编译
     */
    private JRadioButton redefineBeforeCompileRadioButton;
    private JRadioButton manualSelectPidRadioButton;
    private JRadioButton preConfigurationSelectPidRadioButton;
    private JPanel preConfigurationSelectPidPanel;
    /**
     * redis 选择 按钮
     */
    private JRadioButton redisRadioButton;
    private JPanel redisSettingPane;
    /**
     * redis 地址
     */
    private JTextField redisAddressTextField;
    /**
     * redis 端口
     */
    private JSpinner redisPortField;
    /**
     * redis 密码
     */
    private JPasswordField redisPasswordField;
    /**
     * redis 检测 button
     */
    private JButton redisCheckConfigButton;
    /**
     * 错误信息
     */
    private JLabel redisMessageLabel;
    /**
     * cache key
     */
    private JTextField redisCacheKeyTextField;
    /**
     * cache key ttl
     */
    private JSpinner redisCacheKeyTtl;

    /**
     * arthas zip 信息的地址
     */
    private JTextField arthasPackageZipDownloadUrlTextField;


    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Arthas Idea Plugin";
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return springContextStaticOgnlExpressionTextFiled;
    }

    private Project project;

    /**
     * 设置信息
     */
    private AppSettingsState settings;

    /**
     * 自动构造  idea 会携带当前的project 参数信息
     *
     * @param project
     */
    public AppSettingsPage(Project project) {
        this.project = project;
        settings = AppSettingsState.getInstance(this.project);
        springContextStaticOgnlExpressionTextFiled.setText(settings.staticSpringContextOgnl);
        invokeCountField.setValue(1);


    }

    private void createUIComponents() {
        linkLabel = new ActionLink("", AllIcons.Ide.Link, new AnAction() {
            @Override
            public void actionPerformed(AnActionEvent anActionEvent) {
                BrowserUtil.browse("https://github.com/WangJi92/arthas-plugin-demo/blob/master/src/main/java/com/wangji92/arthas/plugin/demo/common/ApplicationContextProvider.java");
            }
        });
        linkLabel.setPaintUnderline(false);

        selectLink = new ActionLink("", AllIcons.Ide.Link, new AnAction() {
            @Override
            public void actionPerformed(AnActionEvent anActionEvent) {
                BrowserUtil.browse("https://arthas.aliyun.com/doc/advanced-use.html");
            }
        });
        selectLink.setPaintUnderline(false);

        batchSupportLink = new ActionLink("", AllIcons.Ide.Link, new AnAction() {
            @Override
            public void actionPerformed(AnActionEvent anActionEvent) {
                BrowserUtil.browse("https://arthas.aliyun.com/doc/batch-support.html");
            }
        });
        batchSupportLink.setPaintUnderline(false);

        redefineHelpLinkLabel = new ActionLink("", AllIcons.Ide.Link, new AnAction() {
            @Override
            public void actionPerformed(AnActionEvent anActionEvent) {
                BrowserUtil.browse("https://arthas.aliyun.com/doc/redefine.html#");
            }
        });
        redefineHelpLinkLabel.setPaintUnderline(false);

        ossHelpLink = new ActionLink("", AllIcons.Ide.Link, new AnAction() {
            @Override
            public void actionPerformed(AnActionEvent anActionEvent) {
                BrowserUtil.browse("https://helpcdn.aliyun.com/document_detail/84781.html?spm=a2c4g.11186623.6.823.148d1144LOadRS");
            }
        });
        ossHelpLink.setPaintUnderline(false);
        printConditionExpressLink = new ActionLink("", AllIcons.Ide.Link, new AnAction() {
            @Override
            public void actionPerformed(AnActionEvent anActionEvent) {
                BrowserUtil.browse("https://github.com/alibaba/arthas/issues/1348");
            }
        });
        printConditionExpressLink.setPaintUnderline(false);
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        return contentPane;
    }

    @Override
    public boolean isModified() {
        boolean modify = !springContextStaticOgnlExpressionTextFiled.getText().equals(settings.staticSpringContextOgnl)
                || !invokeCountField.getValue().toString().equals(settings.invokeCount)
                || !invokeMonitorCountField.getValue().toString().equals(settings.invokeMonitorCount)
                || !invokeMonitorIntervalField.getValue().toString().equals(settings.invokeMonitorInterval)
                || !depthPrintPropertyField.getValue().toString().equals(settings.depthPrintProperty)
                || !selectProjectNameTextField.getText().equals(settings.selectProjectName)
                || traceSkipJdkRadio.isSelected() != settings.traceSkipJdk
                || conditionExpressDisplayRadio.isSelected() != settings.conditionExpressDisplay
                || ossGlobalSettingRadioButton.isSelected() != settings.ossGlobalSetting
                || springContextGlobalSettingRadioButton.isSelected() != settings.springContextGlobalSetting
                || aliYunOssRadioButton.isSelected() != settings.aliYunOss
                || redisRadioButton.isSelected() != settings.hotRedefineRedis
                || hotRedefineDeleteFileRadioButton.isSelected() != settings.hotRedefineDelete
                || redefineBeforeCompileRadioButton.isSelected() != settings.redefineBeforeCompile
                || printConditionExpressRadioButton.isSelected() != settings.printConditionExpress
                || manualSelectPidRadioButton.isSelected() != settings.manualSelectPid
                || !arthasPackageZipDownloadUrlTextField.getText().equalsIgnoreCase(settings.arthasPackageZipDownloadUrl);

        if (modify) {
            return modify;
        }
        if (aliYunOssRadioButton.isSelected()) {
            modify = !settings.endpoint.equals(ossEndpointTextField.getText())
                    || !settings.accessKeyId.equals(String.valueOf(ossAccessKeyIdPasswordField.getPassword()))
                    || !settings.accessKeySecret.equals(String.valueOf(ossAccessKeySecretPasswordField.getPassword()))
                    || !settings.bucketName.equals(ossBucketNameTextField.getText())
                    || !settings.directoryPrefix.equals(ossDirectoryPrefixTextField.getText());
        }

        if (redisRadioButton.isSelected()) {
            modify = !settings.redisAddress.equals(redisAddressTextField.getText())
                    || !settings.redisPort.equals((redisPortField.getValue()))
                    || !settings.redisAuth.equals(String.valueOf(redisPasswordField.getPassword()))
                    || !settings.redisCacheKey.equals(redisCacheKeyTextField.getText())
                    || !settings.redisCacheKeyTtl.equals(redisCacheKeyTtl.getValue());
        }
        return modify;
    }

    @Override
    public void apply() {
        saveSettings();
    }

    @Override
    public void reset() {
        loadSettings();
    }

    /**
     * 保存配置
     */
    private void saveSettings() {
        StringBuilder error = new StringBuilder();
        this.saveStaticSpringContext(error);
        if (((int) invokeCountField.getValue()) <= 0) {
            error.append("invokeCountField <= 0 ");
        } else {
            settings.invokeCount = invokeCountField.getValue().toString();
        }
        if (((int) invokeMonitorCountField.getValue()) <= 0) {
            error.append("invokeMonitorCount <= 0 ");
        } else {
            settings.invokeMonitorCount = invokeMonitorCountField.getValue().toString();
        }
        if (((int) invokeMonitorIntervalField.getValue()) <= 0) {
            error.append("invokeMonitorCount <= 0 ");
        } else {
            settings.invokeMonitorInterval = invokeMonitorIntervalField.getValue().toString();
        }
        if (((int) depthPrintPropertyField.getValue()) <= 0) {
            error.append("invokeMonitorCount <= 0 ");
        } else {
            settings.depthPrintProperty = depthPrintPropertyField.getValue().toString();
        }
        settings.traceSkipJdk = traceSkipJdkRadio.isSelected();
        settings.conditionExpressDisplay = conditionExpressDisplayRadio.isSelected();
        settings.selectProjectName = selectProjectNameTextField.getText();
        settings.manualSelectPid = manualSelectPidRadioButton.isSelected();
        settings.hotRedefineDelete = hotRedefineDeleteFileRadioButton.isSelected();
        settings.redefineBeforeCompile = redefineBeforeCompileRadioButton.isSelected();
        settings.printConditionExpress = printConditionExpressRadioButton.isSelected();
        settings.arthasPackageZipDownloadUrl = arthasPackageZipDownloadUrlTextField.getText();
        // 设置到全局
        PropertiesComponentUtils.setValue("arthasPackageZipDownloadUrl", arthasPackageZipDownloadUrlTextField.getText());
        if (clipboardRadioButton.isSelected()) {
            settings.hotRedefineClipboard = true;
            settings.aliYunOss = false;
            settings.hotRedefineRedis = false;
        } else if (aliYunOssRadioButton.isSelected()) {
            this.saveAliyunOssConfig(error);
        } else if (redisRadioButton.isSelected()) {
            this.saveRedisConfig(error);
        }

        if (StringUtils.isNotBlank(error)) {
            NotifyUtils.notifyMessage(project, error.toString(), NotificationType.ERROR);
        }

    }

    /**
     * 保存spring static 配置信息
     *
     * @param error
     */
    private void saveStaticSpringContext(StringBuilder error) {
        String staticOgnlExpressionTextFiledText = springContextStaticOgnlExpressionTextFiled.getText();
        if (StringUtils.isBlank(staticOgnlExpressionTextFiledText) || !staticOgnlExpressionTextFiledText.contains(AT)) {
            error.append("配置静态spring context 错误");
        } else {
            if (!springContextStaticOgnlExpressionTextFiled.getText().equals(ArthasCommandConstants.DEFAULT_SPRING_CONTEXT_SETTING)) {
                String springContextValue = PropertiesComponentUtils.getValue(ArthasCommandConstants.SPRING_CONTEXT_STATIC_OGNL_EXPRESSION);
                // 有一个地方设置 默认设置为全局的！
                if (StringUtils.isBlank(springContextValue) || springContextValue.equals(ArthasCommandConstants.DEFAULT_SPRING_CONTEXT_SETTING)) {
                    PropertiesComponentUtils.setValue(ArthasCommandConstants.SPRING_CONTEXT_STATIC_OGNL_EXPRESSION, springContextStaticOgnlExpressionTextFiled.getText());
                }
            }
            settings.staticSpringContextOgnl = springContextStaticOgnlExpressionTextFiled.getText();
            settings.springContextGlobalSetting = springContextGlobalSettingRadioButton.isSelected();
            //全局设置
            if (springContextGlobalSettingRadioButton.isSelected()) {
                PropertiesComponentUtils.setValue(ArthasCommandConstants.SPRING_CONTEXT_STATIC_OGNL_EXPRESSION, springContextStaticOgnlExpressionTextFiled.getText());
            }

        }
    }

    /**
     * 保存redis的配置信息
     *
     * @param error
     */
    private void saveRedisConfig(StringBuilder error) {
        if (((int) redisCacheKeyTtl.getValue()) <= 0) {
            error.append("redisCacheKeyTtl <= 0 ");
        } else {
            settings.redisCacheKeyTtl = (Integer) redisCacheKeyTtl.getValue();
        }
        if (StringUtils.isBlank(redisCacheKeyTextField.getText())) {
            settings.redisCacheKey = "arthasIdeaPluginRedefineCacheKey";
        }
        try (Jedis jedis = JedisUtils.buildJedisClient(redisAddressTextField.getText(), (Integer) redisPortField.getValue(), 5000, String.valueOf(redisPasswordField.getPassword()));) {
            JedisUtils.checkRedisClient(jedis);
            settings.redisAddress = redisAddressTextField.getText();
            settings.redisPort = (Integer) redisPortField.getValue();
            settings.redisAuth = String.valueOf(redisPasswordField.getPassword());
            settings.hotRedefineRedis = true;
            settings.aliYunOss = false;
            PropertiesComponentUtils.setValue("redisAddress", settings.redisAddress);
            PropertiesComponentUtils.setValue("redisPort", "" + settings.redisPort);
            PropertiesComponentUtils.setValue("redisAuth", settings.redisAuth);
            PropertiesComponentUtils.setValue("redisCacheKey", settings.redisCacheKey);
            PropertiesComponentUtils.setValue("redisCacheKeyTtl", "" + settings.redisCacheKeyTtl);
        } catch (Exception ex) {
            error.append(ex.getMessage());
        }
    }

    /**
     * 保存 阿里云oss的配置
     *
     * @param error
     */
    private void saveAliyunOssConfig(StringBuilder error) {
        OSS oss = null;
        try {
            oss = AliyunOssUtils.buildOssClient(ossEndpointTextField.getText(), String.valueOf(ossAccessKeyIdPasswordField.getPassword()), String.valueOf(ossAccessKeySecretPasswordField.getPassword()), ossBucketNameTextField.getText(), ossDirectoryPrefixTextField.getText());
            AliyunOssUtils.checkBuckNameExist(ossBucketNameTextField.getText(), oss);
            settings.endpoint = ossEndpointTextField.getText();
            settings.accessKeyId = String.valueOf(ossAccessKeyIdPasswordField.getPassword());
            settings.accessKeySecret = String.valueOf(ossAccessKeySecretPasswordField.getPassword());
            settings.bucketName = ossBucketNameTextField.getText();
            settings.directoryPrefix = ossDirectoryPrefixTextField.getText();
            settings.aliYunOss = true;
            settings.hotRedefineRedis = false;
            settings.ossGlobalSetting = ossGlobalSettingRadioButton.isSelected();
            if (ossGlobalSettingRadioButton.isSelected()) {
                PropertiesComponentUtils.setValue("endpoint", settings.endpoint);
                PropertiesComponentUtils.setValue("accessKeyId", settings.accessKeyId);
                PropertiesComponentUtils.setValue("accessKeySecret", settings.accessKeySecret);
                PropertiesComponentUtils.setValue("bucketName", settings.bucketName);
                PropertiesComponentUtils.setValue("directoryPrefix", settings.directoryPrefix);
            }
            oss.shutdown();
        } catch (Exception e) {
            error.append(e.getMessage());
        } finally {
            if (oss != null) {
                oss.shutdown();
            }
        }
    }

    /**
     * 加载配置
     */
    private void loadSettings() {
        springContextStaticOgnlExpressionTextFiled.setText(settings.staticSpringContextOgnl);
        invokeCountField.setValue(Integer.parseInt(settings.invokeCount));
        invokeMonitorCountField.setValue(Integer.parseInt(settings.invokeMonitorCount));
        invokeMonitorIntervalField.setValue(Integer.parseInt(settings.invokeMonitorInterval));
        depthPrintPropertyField.setValue(Integer.parseInt(settings.depthPrintProperty));
        traceSkipJdkRadio.setSelected(settings.traceSkipJdk);
        conditionExpressDisplayRadio.setSelected(settings.conditionExpressDisplay);
        hotRedefineDeleteFileRadioButton.setSelected(settings.hotRedefineDelete);
        redefineBeforeCompileRadioButton.setSelected(settings.redefineBeforeCompile);
        printConditionExpressRadioButton.setSelected(settings.printConditionExpress);
        selectProjectNameTextField.setText(settings.selectProjectName);

        ossEndpointTextField.setText(settings.endpoint);
        ossAccessKeyIdPasswordField.setText(settings.accessKeyId);
        ossAccessKeySecretPasswordField.setText(settings.accessKeySecret);
        ossBucketNameTextField.setText(settings.bucketName);
        ossDirectoryPrefixTextField.setText(settings.directoryPrefix);
        redisAddressTextField.setText(settings.redisAddress);
        redisPortField.setValue(settings.redisPort);
        redisPasswordField.setText(settings.redisAuth);
        redisCacheKeyTtl.setValue(settings.redisCacheKeyTtl);
        redisCacheKeyTextField.setText(settings.redisCacheKey);

        if (settings.aliYunOss) {
            // 阿里云oss
            aliYunOssRadioButton.setSelected(true);
            redisSettingPane.setVisible(false);
            aliyunOssSettingPane.setVisible(true);
        } else if (settings.hotRedefineRedis) {
            // redis
            aliyunOssSettingPane.setVisible(false);
            redisRadioButton.setSelected(true);
            redisSettingPane.setVisible(true);
        } else {
            // 剪切板
            clipboardRadioButton.setSelected(true);
            aliyunOssSettingPane.setVisible(false);
            redisSettingPane.setVisible(false);
        }
        if (settings.manualSelectPid) {
            preConfigurationSelectPidPanel.setVisible(false);
            preConfigurationSelectPidRadioButton.setSelected(false);
            manualSelectPidRadioButton.setSelected(true);
        } else {
            preConfigurationSelectPidPanel.setVisible(true);
            preConfigurationSelectPidRadioButton.setSelected(true);
            manualSelectPidRadioButton.setSelected(false);
        }
        springContextGlobalSettingRadioButton.setSelected(settings.springContextGlobalSetting);
        ossGlobalSettingRadioButton.setSelected(settings.ossGlobalSetting);

        // 设置远程的下载地址
        arthasPackageZipDownloadUrlTextField.setText(settings.arthasPackageZipDownloadUrl);
        initEvent();
    }

    private void initEvent() {
        ossCheckMsgLabel.setText("");
        ossCheckMsgLabel.setForeground(JBColor.BLACK);
        ossSettingCheckButton.addActionListener(e -> {
            OSS oss = null;
            try {
                oss = AliyunOssUtils.buildOssClient(ossEndpointTextField.getText(), String.valueOf(ossAccessKeyIdPasswordField.getPassword()), String.valueOf(ossAccessKeySecretPasswordField.getPassword()), ossBucketNameTextField.getText(), ossDirectoryPrefixTextField.getText());
                AliyunOssUtils.checkBuckNameExist(ossBucketNameTextField.getText(), oss);
                oss.shutdown();
                ossCheckMsgLabel.setText("oss setting check success");
                ossCheckMsgLabel.setForeground(JBColor.BLACK);
            } catch (Exception ex) {
                ossCheckMsgLabel.setText(ex.getMessage());
                ossCheckMsgLabel.setForeground(JBColor.RED);
            } finally {
                if (oss != null) {
                    oss.shutdown();
                }
            }
        });

        redisCheckConfigButton.addActionListener(e -> {
            try (Jedis jedis = JedisUtils.buildJedisClient(redisAddressTextField.getText(), (Integer) redisPortField.getValue(), 5000, String.valueOf(redisPasswordField.getPassword()));) {
                JedisUtils.checkRedisClient(jedis);
                redisMessageLabel.setText("redis setting check success");
                redisMessageLabel.setForeground(JBColor.BLACK);
            } catch (Exception ex) {
                redisMessageLabel.setText(ex.getMessage());
                redisMessageLabel.setForeground(JBColor.RED);
            }
        });

        ButtonGroup group = new ButtonGroup();
        group.add(aliYunOssRadioButton);
        group.add(clipboardRadioButton);
        group.add(redisRadioButton);
        ItemListener itemListener = e -> {
            if (e.getSource().equals(aliYunOssRadioButton) && e.getStateChange() == ItemEvent.SELECTED) {
                aliyunOssSettingPane.setVisible(true);
                redisSettingPane.setVisible(false);
            } else if (e.getSource().equals(clipboardRadioButton) && e.getStateChange() == ItemEvent.SELECTED) {
                aliyunOssSettingPane.setVisible(false);
                redisSettingPane.setVisible(false);

            } else if (e.getSource().equals(redisRadioButton) && e.getStateChange() == ItemEvent.SELECTED) {
                aliyunOssSettingPane.setVisible(false);
                redisSettingPane.setVisible(true);
            }
            ossCheckMsgLabel.setText("");
            redisMessageLabel.setText("");
        };
        aliYunOssRadioButton.addItemListener(itemListener);
        clipboardRadioButton.addItemListener(itemListener);
        redisRadioButton.addItemListener(itemListener);

        // 设置是否手动选择pid
        ItemListener itemListenerSelectPid = e -> {
            if (e.getSource().equals(manualSelectPidRadioButton) && e.getStateChange() == ItemEvent.SELECTED || e.getSource().equals(preConfigurationSelectPidRadioButton) && e.getStateChange() == ItemEvent.DESELECTED) {
                preConfigurationSelectPidPanel.setVisible(false);
                preConfigurationSelectPidRadioButton.setSelected(false);
                manualSelectPidRadioButton.setSelected(true);
            } else if (e.getSource().equals(preConfigurationSelectPidRadioButton) && e.getStateChange() == ItemEvent.SELECTED || e.getSource().equals(manualSelectPidRadioButton) && e.getStateChange() == ItemEvent.DESELECTED) {
                preConfigurationSelectPidPanel.setVisible(true);
                preConfigurationSelectPidRadioButton.setSelected(true);
                manualSelectPidRadioButton.setSelected(false);
            }
        };
        manualSelectPidRadioButton.addItemListener(itemListenerSelectPid);
        preConfigurationSelectPidRadioButton.addItemListener(itemListenerSelectPid);
    }

    @Override
    public void disposeUIResources() {
        contentPane = null;
    }


}
