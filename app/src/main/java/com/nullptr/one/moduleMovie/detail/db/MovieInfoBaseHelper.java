package com.nullptr.one.moduleMovie.detail.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.nullptr.one.moduleMovie.detail.db.MovieInfoDbSchema.MovieInfoTable;
import com.nullptr.one.moduleMovie.detail.db.MovieInfoDbSchema.MovieInfoTable.Cols;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/21
 * @DESCRIPTION 电影信息数据库
 */
public class MovieInfoBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "movieInfoBase.db";

    public MovieInfoBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        //创建Movie表
        db.execSQL("create table if not exists " + MovieInfoTable.NAME + "( "
                + Cols.ITEM_ID + " text, "
                + Cols.COVER_URL + " text, "
                + Cols.MOVIE_TITLE + " text, "
                + Cols.INFO + " text, "
                + Cols.STORY + " text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
