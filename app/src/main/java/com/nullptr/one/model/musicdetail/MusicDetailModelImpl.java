package com.nullptr.one.model.musicdetail;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.nullptr.one.MyApplication;
import com.nullptr.one.bean.MusicDetail;
import com.nullptr.one.db.MusicBaseHelper;
import com.nullptr.one.db.MusicDbSchema.MusicTable;
import com.nullptr.one.db.MusicDbSchema.MusicTable.Cols;
import com.nullptr.one.util.HttpUtil;
import com.nullptr.one.util.JsonUtil;
import com.nullptr.one.util.OnRequestListener;

/**
 * Model层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/13
 * @DESCRIPTION MusicDetailModel接口实现类
 */
public class MusicDetailModelImpl implements MusicDetailModel {

    private SQLiteDatabase mDatabase;   //本地缓存音乐详情的数据库

    public MusicDetailModelImpl() {
        mDatabase = new MusicBaseHelper(MyApplication.getContext()).getWritableDatabase();
    }

    @Override
    public void getMusicDetail(final OnMusicDetailListener onMusicDetailListener,
            final String itemId) {
        final Cursor cursor = mDatabase.query(MusicTable.NAME, new String[]{Cols.JSON}, "item_id = ?",
                new String[]{itemId}, null, null, null);
        if (cursor.getCount() > 1) {
            //如果已经有数据了，直接读取
            new Thread(new Runnable() {
                @Override
                public void run() {
                    cursor.moveToFirst();
                    String json = cursor.getString(cursor.getColumnIndex(Cols.JSON));
                    MusicDetail musicDetail = JsonUtil.parseJsonToMusicDetail(json);
                    onMusicDetailListener.onSuccess(musicDetail);
                }
            }).start();
        } else {
            //如果数据库没有数据库，向服务器申请数据并存入数据库
            HttpUtil.sendHttpRequest("http://v3.wufazhuce.com:8000/api/music/detail/{id}", itemId, new OnRequestListener() {
                @Override
                public void onResponse(String response) {
                    //将该音乐详情存入数据库
                    mDatabase.insert(MusicTable.NAME, null, getContentValues(itemId, response));
                    MusicDetail musicDetail = JsonUtil.parseJsonToMusicDetail(response);
                    onMusicDetailListener.onSuccess(musicDetail);
                }

                @Override
                public void onError(String errorMsg) {
                    onMusicDetailListener.onFail(errorMsg);
                }

                @Override
                public void onStart() {
                    onMusicDetailListener.onStart();
                }

                @Override
                public void onFinish() {
                    onMusicDetailListener.onFinish();
                }
            });
        }
    }

    private static ContentValues getContentValues(String itemId, String json) {
        ContentValues values = new ContentValues();
        values.put(Cols.ITEM_ID, itemId);
        values.put(Cols.JSON, json);

        return values;
    }
}
