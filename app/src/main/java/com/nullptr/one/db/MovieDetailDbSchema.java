package com.nullptr.one.db;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/21
 * @DESCRIPTION Movie Schema，用于描述movie数据表
 */
public class MovieDetailDbSchema {
    public static  final class MovieDetailTable {
        public static final String NAME = "movie";

        public static final class Cols{
            public static final String ITEM_ID = "item_id";
            public static final String JSON = "json";
        }
    }
}
