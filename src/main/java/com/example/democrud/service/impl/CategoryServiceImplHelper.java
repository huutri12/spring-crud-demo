package com.example.democrud.service.impl;

import org.apache.commons.lang3.StringUtils;

public class CategoryServiceImplHelper {
    public static boolean isValidBeforeGetList(String name) {
        boolean isValid = true;
        if (StringUtils.isNotBlank(name) && name.trim().length() > 40) {
            isValid = false;
        }
        return isValid;
    }
}
