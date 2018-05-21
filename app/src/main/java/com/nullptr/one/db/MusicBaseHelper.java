package com.nullptr.one.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.nullptr.one.db.MusicDbSchema.MusicTable;
import com.nullptr.one.db.MusicDbSchema.MusicTable.Cols;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/21
 * @DESCRIPTION
 */
public class MusicBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "musicBase.db";

    public MusicBaseHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        //创建Music表
        db.execSQL("create table if not exists "+MusicTable.NAME+"( "
                +Cols.ITEM_ID+" text, "
                +Cols.JSON+" text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
