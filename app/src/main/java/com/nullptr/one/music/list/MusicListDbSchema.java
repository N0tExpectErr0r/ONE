package com.nullptr.one.music.list;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/7/13
 * @DESCRIPTION Music Schema，用于描述music_list数据表
 */
public class MusicListDbSchema {

    public static final class MusicListTable {

        public static final String NAME = "music_list";

        public static final class Cols {

            public static final String ID = "id";
            public static final String ITEM_ID = "item_id";
            public static final String TITLE = "title";
            public static final String FORWARD = "forward";
            public static final String IMG_URL = "img_url";
            public static final String SINGER_NAME = "singer_name";
            public static final String SINGER_DESC = "singer_desc";
        }
    }
}
