package com.github.idea.json.parser.typevalue.jdk.time;

import com.github.idea.json.parser.typevalue.TypeDefaultValue;
import com.github.idea.json.parser.typevalue.TypeValueContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author wangji
 * @date 2024/5/19 13:34
 */
public class LocalDateTypeValue implements TypeDefaultValue {
    private final LocalDate today = LocalDate.now(); // 获取当前日期

    @Override
    public Object getValue(TypeValueContext context) {
        return today;
    }

    @Override
    public String getQualifiedName() {
        return LocalDate.class.getName();
    }
}
