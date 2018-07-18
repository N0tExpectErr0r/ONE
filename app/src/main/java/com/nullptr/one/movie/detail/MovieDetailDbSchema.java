package com.nullptr.one.movie.detail;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/21
 * @DESCRIPTION Movie Schema，用于描述movie数据表
 */
public class MovieDetailDbSchema {

    public static final class MovieDetailTable {

        public static final String NAME = "movie";

        public static final class Cols {

            public static final String ITEM_ID = "item_id";
            public static final String TITLE = "title";
            public static final String CONTENT = "content";
            public static final String AUTHOR_NAME = "author_name";
            public static final String AUTHOR_DESC = "author_desc";
        }
    }
}
