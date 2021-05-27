package com.github.wangji92.arthas.plugin.common.enums;

import com.github.wangji92.arthas.plugin.common.enums.base.EnumCodeMsg;

public enum VmToolActionEnum implements EnumCodeMsg<String> {
    FORCE_GC("forceGc", "forceGc"),
    GET_INSTANCES("getInstances", "getInstances");


    private String code;

    private String msg;


    VmToolActionEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getEnumMsg() {
        return msg;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code;
    }
}
