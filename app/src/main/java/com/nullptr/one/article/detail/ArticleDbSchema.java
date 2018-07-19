package com.nullptr.one.article.detail;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/21
 * @DESCRIPTION Article Schema，用于描述article数据表
 */
class ArticleDbSchema {

    public static final class ArticleTable {

        public static final String NAME = "article";

        public static final class Cols {

            static final String ITEM_ID = "item_id";
            static final String TITLE = "title";
            static final String AUTHOR_NAME = "author_name";
            static final String AUTHOR_DESC = "author_desc";
            static final String CONTENT = "content";
            static final String DATE = "date";
            static final String COPYRIGHT = "copyright";
        }
    }
}
