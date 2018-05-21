package com.nullptr.one.model.movieinfo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.nullptr.one.MyApplication;
import com.nullptr.one.bean.MovieInfo;
import com.nullptr.one.db.MovieInfoBaseHelper;
import com.nullptr.one.db.MovieInfoDbSchema.MovieInfoTable;
import com.nullptr.one.db.MovieInfoDbSchema.MovieInfoTable.Cols;
import com.nullptr.one.model.movieinfo.MovieInfoModel;
import com.nullptr.one.model.movieinfo.OnMovieInfoListener;
import com.nullptr.one.util.HttpUtil;
import com.nullptr.one.util.JsonUtil;
import com.nullptr.one.util.OnRequestListener;

/**
 * Model层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/19
 * @DESCRIPTION MovieInfoModel接口实现类
 */
public class MovieInfoModelImpl implements MovieInfoModel {

    private SQLiteDatabase mDatabase;   //本地缓存音乐详情的数据库

    public MovieInfoModelImpl() {
        mDatabase = new MovieInfoBaseHelper(MyApplication.getContext()).getWritableDatabase();
    }

    @Override
    public void getMovieInfo(final OnMovieInfoListener onMovieInfoListener,
            final String itemId) {
        final Cursor cursor = mDatabase.query(MovieInfoTable.NAME, new String[]{Cols.JSON}, "item_id = ?",
                new String[]{itemId}, null, null, null);
        if (cursor.getCount() > 1) {
            //如果已经有数据了，直接读取
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //耗时操作，在新线程
                    cursor.moveToFirst();
                    String json = cursor.getString(cursor.getColumnIndex(Cols.JSON));
                    MovieInfo movieInfo = JsonUtil.parseJsonToMovieInfo(json);
                    onMovieInfoListener.onSuccess(movieInfo);
                }
            }).start();
        } else {
            //如果数据库没有数据库，向服务器申请数据并存入数据库
            HttpUtil.sendHttpRequest("getMovieInfo", itemId, new OnRequestListener() {
                @Override
                public void onResponse(String response) {
                    //将该影视信息存入数据库
                    mDatabase.insert(MovieInfoTable.NAME, null, getContentValues(itemId, response));
                    MovieInfo movieInfo = JsonUtil.parseJsonToMovieInfo(response);
                    onMovieInfoListener.onSuccess(movieInfo);
                }

                @Override
                public void onError(String errorMsg) {
                    onMovieInfoListener.onFail(errorMsg);
                }

                @Override
                public void onStart() {
                    onMovieInfoListener.onStart();
                }

                @Override
                public void onFinish() {
                    onMovieInfoListener.onFinish();
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
