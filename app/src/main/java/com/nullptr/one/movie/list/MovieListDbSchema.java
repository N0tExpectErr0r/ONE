package com.nullptr.one.movie.list;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/7/13
 * @DESCRIPTION Movie Schema，用于描述movie_list数据表
 */
public class MovieListDbSchema {

    public static final class MovieListTable {

        public static final String NAME = "movie_list";

        public static final class Cols {

            public static final String ID = "id";
            public static final String ITEM_ID = "item_id";
            public static final String TITLE = "title";
            static final String SUBTITLE = "subtitle";
            public static final String FORWARD = "forward";
            public static final String IMG_URL = "img_url";
            public static final String DATE = "data";
            public static final String AUTHOR_NAME = "author_name";
        }
    }
}
