package com.nullptr.one.music.detail.db;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/21
 * @DESCRIPTION Music Schema，用于描述music数据表
 */
public class MusicDbSchema {

    public static final class MusicTable {

        public static final String NAME = "music";

        public static final class Cols {

            public static final String ITEM_ID = "item_id";
            public static final String TITLE = "title";
            public static final String STORY_SUMMARY = "story_summary";
            public static final String COVER_URL = "cover_url";
            public static final String STORY_TITLE = "story_title";
            public static final String STORY = "story";
            public static final String LYRIC = "lyric";
            public static final String INFO = "info";
            public static final String DATE = "date";
            public static final String AUTHOR_NAME = "author_name";
        }
    }
}