package com.example.democrud.utils;

public class Enum {
    public enum CommonSortEnum {
        ASC("ASC"),
        DESC("DESC");

        private String value;

        CommonSortEnum(String value) {
            this.value = value;
        }
    }

    public enum CategorySortEnum {
        I("id"),
        N("name");

        private String value;

        CategorySortEnum(String value) {
            this.value = value;
        }
    }
}
