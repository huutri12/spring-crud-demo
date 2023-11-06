package com.example.democrud.service.Impl;

import com.example.democrud.request.CategoryRequest;
import org.apache.commons.lang3.StringUtils;

public class CategoryServiceImplHelper {
    public static boolean isValidBeforeGetList(String name) {
        boolean isValid = true;
        if (StringUtils.isNotBlank(name) && name.strip().length() > 40) {
            isValid = false;
        }
        return isValid;
    }
}
