package com.nullptr.one.db;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/21
 * @DESCRIPTION Music Schema，用于描述music数据表
 */
public class MusicDbSchema {
    public static  final class MusicTable{
        public static final String NAME = "music";

        public static final class Cols{
            public static final String ITEM_ID = "item_id";
            public static final String JSON = "json";
        }
    }
}