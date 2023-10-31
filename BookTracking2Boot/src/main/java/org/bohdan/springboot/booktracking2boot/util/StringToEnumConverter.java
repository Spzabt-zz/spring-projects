package org.bohdan.springboot.booktracking2boot.util;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.bohdan.springboot.booktracking2boot.models.enums.Role;
import org.springframework.core.convert.converter.Converter;

public class StringToEnumConverter implements Converter<String, Role> {
    @Override
    public Role convert(String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        return EnumUtils.getEnum(Role.class, source.toUpperCase());
    }
}
