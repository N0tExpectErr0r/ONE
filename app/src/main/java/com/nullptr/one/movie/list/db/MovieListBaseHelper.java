package com.nullptr.one.movie.list.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.nullptr.one.movie.list.db.MovieListDbSchema.MovieListTable;
import com.nullptr.one.movie.list.db.MovieListDbSchema.MovieListTable.Cols;


/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/7/13
 * @DESCRIPTION 电影列表数据库
 */
public class MovieListBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "movieListBase.db";

    public MovieListBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        //创建Movie表
        db.execSQL("create table if not exists " + MovieListTable.NAME + "( "
                + Cols.ID + " text,"
                + Cols.ITEM_ID + " text, "
                + Cols.TITLE + " text, "
                + Cols.SUBTITLE + " text, "
                + Cols.FORWARD + " text, "
                + Cols.IMG_URL + " text, "
                + Cols.DATE + " text,"
                + Cols.AUTHOR_NAME + " text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
