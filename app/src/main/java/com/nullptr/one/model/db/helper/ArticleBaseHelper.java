package com.nullptr.one.model.db.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.nullptr.one.model.db.schema.ArticleDbSchema.ArticleTable;
import com.nullptr.one.model.db.schema.ArticleDbSchema.ArticleTable.Cols;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/21
 * @DESCRIPTION 文章数据库
 */
public class ArticleBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "articleBase.db";

    public ArticleBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        //创建Article表
        db.execSQL("create table if not exists " + ArticleTable.NAME + "( "
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
