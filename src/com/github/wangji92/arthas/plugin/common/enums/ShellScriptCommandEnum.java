package com.github.wangji92.arthas.plugin.common.enums;

import com.github.wangji92.arthas.plugin.common.enums.base.EnumCodeMsg;

/**
 * 可以直接执行的脚本通用信息
 *
 * @author 汪小哥
 * @date 04-05-2021
 */
public enum ShellScriptCommandEnum implements EnumCodeMsg<String> {
    /**
     * 调用静态变量 或者方法
     */
    OGNL_GETSTATIC("ognl "
            + " -x "
            + ShellScriptVariableEnum.PROPERTY_DEPTH.getCode() + " @"
            + ShellScriptVariableEnum.CLASS_NAME.getCode() + "@"
            + ShellScriptVariableEnum.EXECUTE_INFO.getCode()
            + " -c "
            + ShellScriptVariableEnum.CLASSLOADER_HASH_VALUE.getCode(),
            "ognl to get static method field 注意需要编执行方法的参数", true, null, null, true, null, true),
    /**
     * 简单的字段
     */
    GETSTATIC("getstatic "
            + ShellScriptVariableEnum.CLASS_NAME.getCode() + " "
            + ShellScriptVariableEnum.FIELD_NAME.getCode(),
            "get simple static field", true, true, null, true, null, true),

    /**
     * watch static field
     */
    WATCH_STATIC_FILED("watch "
            + ShellScriptVariableEnum.CLASS_NAME.getCode() + " * "
            + " '{params,returnObj,throwExp,@" + ShellScriptVariableEnum.CLASS_NAME.getCode() + "@" + ShellScriptVariableEnum.FIELD_NAME.getCode() + "}'"
            + ShellScriptVariableEnum.PRINT_CONDITION_RESULT.getCode() + " -n "
            + ShellScriptVariableEnum.INVOKE_COUNT.getCode() + " "
            + " -x "
            + ShellScriptVariableEnum.PROPERTY_DEPTH.getCode() + " "
            + ShellScriptVariableEnum.CONDITION_EXPRESS_DEFAULT.getCode(),
            "watch  static field", true, true, null, true, null, true),
    /**
     * watch non static field
     */
    WATCH_NON_STATIC_FILED("watch "
            + ShellScriptVariableEnum.CLASS_NAME.getCode() + " "
            + ShellScriptVariableEnum.METHOD_NAME.getCode() + " "
            + " '{params,returnObj,throwExp,target." + ShellScriptVariableEnum.FIELD_NAME.getCode() + "}'"
            + ShellScriptVariableEnum.PRINT_CONDITION_RESULT.getCode() + " -n "
            + ShellScriptVariableEnum.INVOKE_COUNT.getCode() + " "
            + " -x "
            + ShellScriptVariableEnum.PROPERTY_DEPTH.getCode() + " "
            + "'method.initMethod(),method.constructor!=null || !@java.lang.reflect.Modifier@isStatic(method.method.getModifiers())'",
            "watch non static field", true, true, null, false, null, null),
    /**
     * watch
     */
    WATCH("watch "
            + ShellScriptVariableEnum.CLASS_NAME.getCode() + " "
            + ShellScriptVariableEnum.METHOD_NAME.getCode() + " "
            + "'{params,returnObj,throwExp}'"
            + ShellScriptVariableEnum.PRINT_CONDITION_RESULT.getCode() + " -n "
            + ShellScriptVariableEnum.INVOKE_COUNT.getCode() + " "
            + " -x "
            + ShellScriptVariableEnum.PROPERTY_DEPTH.getCode() + " "
            + ShellScriptVariableEnum.CONDITION_EXPRESS_DEFAULT.getCode(),
            "display the input/output parameter, return object, and thrown exception of specified method invocation", true, null, null, null, null, null),

    /**
     * trace
     */
    TRACE("trace "
            + ShellScriptVariableEnum.CLASS_NAME.getCode() + " "
            + ShellScriptVariableEnum.METHOD_NAME.getCode() + " "
            + ShellScriptVariableEnum.PRINT_CONDITION_RESULT.getCode() + " -n "
            + ShellScriptVariableEnum.INVOKE_COUNT.getCode() + " "
            + ShellScriptVariableEnum.SKIP_JDK_METHOD.getCode() + " "
            + ShellScriptVariableEnum.CONDITION_EXPRESS_DEFAULT.getCode(),
            "trace the execution time of specified method invocation. ", true, null, null, null, null, null),

    /**
     * spring get bean
     */
    SPRING_GET_BEAN("ognl "
            + " -x "
            + "'#springContext=" + ShellScriptVariableEnum.SPRING_CONTEXT.getCode() + ",#springContext.getBean(\"" + ShellScriptVariableEnum.SPRING_BEAN_NAME.getCode() + "\")."
            + ShellScriptVariableEnum.EXECUTE_INFO.getCode() + " "
            + " -c "
            + ShellScriptVariableEnum.CLASSLOADER_HASH_VALUE.getCode(),
            "invoke static spring bean【手动编辑填写参数】【bean名称可能不正确,可以手动修改】 ", true, null, null, null, true, true),

    /**
     * trace
     */
    STACK("stack "
            + ShellScriptVariableEnum.CLASS_NAME.getCode() + " "
            + ShellScriptVariableEnum.METHOD_NAME.getCode() + " "
            + ShellScriptVariableEnum.PRINT_CONDITION_RESULT.getCode() + " -n "
            + ShellScriptVariableEnum.INVOKE_COUNT.getCode() + " "
            + ShellScriptVariableEnum.CONDITION_EXPRESS_DEFAULT.getCode(),
            "display the stack trace for the specified class and method", true, null, null, null, null, null),
    /**
     * monitor
     */
    MONITOR("monitor "
            + ShellScriptVariableEnum.CLASS_NAME.getCode() + " "
            + ShellScriptVariableEnum.METHOD_NAME.getCode() + " "
            + ShellScriptVariableEnum.PRINT_CONDITION_RESULT.getCode() + " -n "
            + ShellScriptVariableEnum.INVOKE_MONITOR_COUNT.getCode() + "  --cycle "
            + ShellScriptVariableEnum.INVOKE_MONITOR_INTERVAL.getCode() + " "
            + ShellScriptVariableEnum.CONDITION_EXPRESS_DEFAULT.getCode(),
            "monitor method execution statistics ", true, null, null, null, null, null),
    /**
     * jad
     */
    JAD("jad --source-only "
            + ShellScriptVariableEnum.CLASS_NAME.getCode() + " "
            + ShellScriptVariableEnum.METHOD_NAME_NOT_STAR.getCode(),
            "decompile class", true, null, null, null, null, true),
    /**
     * sc
     */
    SC("sc -d "
            + ShellScriptVariableEnum.CLASS_NAME.getCode(),
            "search all the classes loaded by jvm", true, null, null, null, null, null),
    /**
     * sc
     */
    SM("sm -d "
            + ShellScriptVariableEnum.CLASS_NAME.getCode() + " "
            + ShellScriptVariableEnum.METHOD_NAME.getCode(),
            "search the method of classes loaded by jvm", true, null, null, null, null, null),


    /**
     * watch * to execute static method
     */
    WATCH_EXECUTE_STATIC_METHOD("watch "
            + ShellScriptVariableEnum.CLASS_NAME.getCode() + " * "
            + " '{params,returnObj,throwExp,@" + ShellScriptVariableEnum.CLASS_NAME.getCode() + "@" + ShellScriptVariableEnum.EXECUTE_INFO.getCode() + "}'"
            + ShellScriptVariableEnum.PRINT_CONDITION_RESULT.getCode() + " -n "
            + ShellScriptVariableEnum.INVOKE_COUNT.getCode() + " "
            + " -x "
            + ShellScriptVariableEnum.PROPERTY_DEPTH.getCode() + " "
            + ShellScriptVariableEnum.CONDITION_EXPRESS_DEFAULT.getCode(),
            "watch * to execute static method 注意需要编辑执行静态方法的参数", true, null, true, true, null, true),
    /**
     * watch 执行 非静态方法
     */
    WATCH_EXECUTE_NO_STATIC_METHOD("watch "
            + ShellScriptVariableEnum.CLASS_NAME.getCode() + " * "
            + " '{params,returnObj,throwExp,target." + ShellScriptVariableEnum.EXECUTE_INFO.getCode() + "}'"
            + ShellScriptVariableEnum.PRINT_CONDITION_RESULT.getCode() + " -n "
            + ShellScriptVariableEnum.INVOKE_COUNT.getCode() + " "
            + " -x "
            + ShellScriptVariableEnum.PROPERTY_DEPTH.getCode() + " "
            + "'method.initMethod(),method.constructor!=null || !@java.lang.reflect.Modifier@isStatic(method.method.getModifiers())'",
            "watch * to execute method 注意需要编执行方法的参数", true, null, true, false, null, true),
    /**
     * logger
     */
    LOGGER("logger --name "
            + ShellScriptVariableEnum.CLASS_NAME.getCode() + " "
            + "--level debug "
            + " -c "
            + ShellScriptVariableEnum.CLASSLOADER_HASH_VALUE.getCode(),
            "--level debug 可以编辑修改为 info、error", true, null, null, null, null, true),


    /**
     * dump
     */
    DUMP("dump "
            + ShellScriptVariableEnum.CLASS_NAME.getCode()
            + " -d /tmp/output "
            + " -c "
            + ShellScriptVariableEnum.CLASSLOADER_HASH_VALUE.getCode(),
            "dump class byte array from jvm", true, null, null, null, null, true),


    ;

    /**
     * true 一定需要  false 一定不需要  null 不关心
     *
     * @param code
     * @param msg
     * @param needClass
     * @param needField
     * @param needMethod
     * @param needStatic
     * @param needSpringBean
     * @param notAnonymousClass
     */
    ShellScriptCommandEnum(String code, String msg, Boolean needClass, Boolean needField, Boolean needMethod, Boolean needStatic, Boolean needSpringBean, Boolean notAnonymousClass) {
        this.code = code;
        this.msg = msg;
        this.needClass = needClass;
        this.needField = needField;
        this.needMethod = needMethod;
        this.needStatic = needStatic;
        this.needSpringBean = needSpringBean;
        this.notAnonymousClass = notAnonymousClass;
    }

    /**
     * code 脚本
     */
    private String code;

    /**
     * 提示信息
     */
    private String msg;

    private Boolean needClass;

    private Boolean needField;

    private Boolean needMethod;

    private Boolean needStatic;

    private Boolean needSpringBean;

    /**
     * 是否需要classloader hashcode(非匿名内部类)
     */
    private Boolean notAnonymousClass;


    @Override
    public String getEnumMsg() {
        return msg;
    }

    @Override
    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public Boolean getNeedClass() {
        return needClass;
    }

    public void setNeedClass(Boolean needClass) {
        this.needClass = needClass;
    }

    public Boolean getNeedField() {
        return needField;
    }

    public void setNeedField(Boolean needField) {
        this.needField = needField;
    }

    public Boolean getNeedMethod() {
        return needMethod;
    }

    public void setNeedMethod(Boolean needMethod) {
        this.needMethod = needMethod;
    }

    @Override
    public String toString() {
        return this.getCode();
    }

    public Boolean getNeedStatic() {
        return needStatic;
    }

    public void setNeedStatic(Boolean needStatic) {
        this.needStatic = needStatic;
    }

    public Boolean getNeedSpringBean() {
        return needSpringBean;
    }

    public void setNeedSpringBean(Boolean needSpringBean) {
        this.needSpringBean = needSpringBean;
    }

    public Boolean getNotAnonymousClass() {
        return notAnonymousClass;
    }

    public void setNotAnonymousClass(Boolean notAnonymousClass) {
        this.notAnonymousClass = notAnonymousClass;
    }
}
