package com.nullptr.one.movie.detail;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/21
 * @DESCRIPTION Movie Schema，用于描述movie数据表
 */
class MovieInfoDbSchema {

    public static final class MovieInfoTable {

        public static final String NAME = "movie_info";

        public static final class Cols {

            public static final String ITEM_ID = "item_id";
            static final String COVER_URL = "cover_url";
            static final String MOVIE_TITLE = "movie_title";
            static final String INFO = "info";
            static final String STORY = "story";
        }
    }
}
