package com.nullptr.one.db;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/21
 * @DESCRIPTION Article Schema，用于描述article数据表
 */
public class ArticleDbSchema {
    public static  final class ArticleTable{
        public static final String NAME = "article";

        public static final class Cols{
            public static final String ITEM_ID = "item_id";
            public static final String JSON = "json";
        }
    }
}
