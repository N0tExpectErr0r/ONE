package com.nullptr.one.download.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.nullptr.one.download.db.DownloadDbSchema.DownloadTable;
import com.nullptr.one.download.db.DownloadDbSchema.DownloadTable.Cols;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/7/13
 * @DESCRIPTION 文章详情下载数据库
 */
public class DownloadBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "downloadBase.db";

    public DownloadBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        //创建Article表
        db.execSQL("create table if not exists " + DownloadTable.NAME + "( "
                + Cols.ITEM_ID + " text, "
                + Cols.TITLE + " text,"
                + Cols.AUTHOR_NAME + " text,"
                + Cols.AUTHOR_DESC + " text,"
                + Cols.CONTENT + " text,"
                + Cols.DATE + " text,"
                + Cols.COPYRIGHT + " text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
