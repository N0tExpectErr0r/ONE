package com.nullptr.one.download.db;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/7/13
 * @DESCRIPTION Article Schema，用于描述article_list数据表
 */
public class DownloadListDbSchema {

    public static final class DownloadListTable {

        public static final String NAME = "download_list";

        public static final class Cols {

            public static final String ID = "id";
            public static final String ITEM_ID = "item_id";
            public static final String TITLE = "title";
            public static final String FORWARD = "forward";
            public static final String IMG_URL = "img_url";
            public static final String LIKE_COUNT = "like_count";
            public static final String DATE = "date";
            public static final String AUTHOR_NAME = "author_name";
            public static final String AUTHOR_DESC = "author_desc";
        }
    }
}
