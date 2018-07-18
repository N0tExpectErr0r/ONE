package com.nullptr.one.music.detail;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.nullptr.one.music.detail.MusicDbSchema.MusicTable;
import com.nullptr.one.music.detail.MusicDbSchema.MusicTable.Cols;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/21
 * @DESCRIPTION 音乐信息数据库
 */
public class MusicBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "musicBase.db";

    public MusicBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        //创建Music表
        db.execSQL("create table if not exists " + MusicTable.NAME + "( "
                + Cols.ITEM_ID + " text, "
                + Cols.TITLE + " text, "
                + Cols.STORY_SUMMARY + " text, "
                + Cols.COVER_URL + " text, "
                + Cols.STORY_TITLE + " text, "
                + Cols.STORY + " text, "
                + Cols.LYRIC + " text, "
                + Cols.INFO + " text, "
                + Cols.DATE + " text, "
                + Cols.AUTHOR_NAME + " text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
