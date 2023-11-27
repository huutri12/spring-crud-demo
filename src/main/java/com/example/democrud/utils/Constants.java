package com.example.democrud.utils;

/**
 * Constants class
 *
 * @author Tri
 */
public class Constants {
    public static final String AUTHOR = "TRI";

    public static final String PERCENT = "%";

    public static final String ID_COLUMN = "id";

    public static class IS_DELETED {
        public static final boolean YES = true;
        public static final boolean NO = false;

        private IS_DELETED() {
        }
    }

    public static class SORT_VALUE {
        public static final String ASC = "ASC";
        public static final String DESC = "DESC";

        private SORT_VALUE() {
        }
    }
}
