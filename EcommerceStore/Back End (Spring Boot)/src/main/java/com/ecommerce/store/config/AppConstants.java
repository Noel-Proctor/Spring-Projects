package com.ecommerce.store.config;

public class AppConstants {

    public static final String PAGE_NUMBER = "0";
    public static final String PAGE_SIZE = "8";
    public static final String CATEGORY_SORT_BY = "categoryName";
    public static final String CATEGORY_SORT_ORDER = "ASC";
    public static final String PRODUCT_SORT_BY = "productName";
    public static final String DIRECTION = "ASC";
    public static final String DEFAULT_KEYWORD = "";
    public static final String MAX_RECORDS_SMALL = "100";

    public static final boolean RESET_DATABASE = false;
    public static final DBType DATABASE_ENGINE = DBType.POSTGRES;


    public enum DBType{
        POSTGRES, MYSQL
    }


}


