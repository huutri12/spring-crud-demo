package com.example.democrud.utils;

public class Enum {
    public static enum CommonSort {
        ASC("ASC"),
        DESC("DESC");

        private String value;

        CommonSort(String value) {
            this.value = value;
        }

        // Có thể viết một static method lấy ra CommonSort theo value.
        public static CommonSort getByValue(String value) {
            for (CommonSort d : CommonSort.values()) {
                if (d.value.equals(value)) {
                    return d;
                }
            }
            return null;
        }
    }
}
