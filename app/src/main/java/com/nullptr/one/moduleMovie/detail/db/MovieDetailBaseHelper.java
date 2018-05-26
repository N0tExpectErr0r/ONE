package com.nullptr.one.moduleMovie.detail.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.nullptr.one.moduleMovie.detail.db.MovieDetailDbSchema.MovieDetailTable;
import com.nullptr.one.moduleMovie.detail.db.MovieDetailDbSchema.MovieDetailTable.Cols;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/21
 * @DESCRIPTION 电影详细数据库
 */
public class MovieDetailBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "movieDetailBase.db";

    public MovieDetailBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        //创建Movie表
        db.execSQL("create table if not exists " + MovieDetailTable.NAME + "( "
                + Cols.ITEM_ID + " text, "
                + Cols.TITLE + " text, "
                + Cols.CONTENT + " text, "
                + Cols.AUTHOR_NAME + " text, "
                + Cols.AUTHOR_DESC + " text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
