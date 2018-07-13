package com.nullptr.one.music.list.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.nullptr.one.music.list.db.MusicListDbSchema.MusicListTable;
import com.nullptr.one.music.list.db.MusicListDbSchema.MusicListTable.Cols;


/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/7/13
 * @DESCRIPTION 音乐列表数据库
 */
public class MusicListBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "musicListBase.db";

    public MusicListBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        //创建Movie表
        db.execSQL("create table if not exists " + MusicListTable.NAME + "( "
                + Cols.ID + " text,"
                + Cols.ITEM_ID + " text, "
                + Cols.TITLE + " text, "
                + Cols.FORWARD + " text, "
                + Cols.IMG_URL + " text, "
                + Cols.SINGER_NAME + " text, "
                + Cols.SINGER_DESC + " text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
