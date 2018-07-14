package com.nullptr.one.download.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.nullptr.one.download.db.DownloadListDbSchema.DownloadListTable;
import com.nullptr.one.download.db.DownloadListDbSchema.DownloadListTable.Cols;


/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/7/13
 * @DESCRIPTION 文章列表数据库
 */
public class DownloadListBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "downloadListBase.db";

    public DownloadListBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        //创建Article表
        db.execSQL("create table if not exists " + DownloadListTable.NAME + "( "
                + Cols.ID + " text,"
                + Cols.ITEM_ID + " text, "
                + Cols.TITLE + " text, "
                + Cols.FORWARD + " text, "
                + Cols.IMG_URL + " text, "
                + Cols.LIKE_COUNT + " text, "
                + Cols.DATE + " text, "
                + Cols.AUTHOR_NAME + " text, "
                + Cols.AUTHOR_DESC + " text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
