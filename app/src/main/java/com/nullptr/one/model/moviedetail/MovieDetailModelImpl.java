package com.nullptr.one.model.moviedetail;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.nullptr.one.MyApplication;
import com.nullptr.one.bean.MovieDetail;
import com.nullptr.one.db.MovieDetailBaseHelper;
import com.nullptr.one.db.MovieDetailDbSchema.MovieDetailTable;
import com.nullptr.one.db.MovieDetailDbSchema.MovieDetailTable.Cols;
import com.nullptr.one.util.HttpUtil;
import com.nullptr.one.util.JsonUtil;
import com.nullptr.one.util.OnRequestListener;

/**
 * Model层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/19
 * @DESCRIPTION MovieDetailModel接口实现类
 */
public class MovieDetailModelImpl implements MovieDetailModel {

    private SQLiteDatabase mDatabase;   //本地缓存影视详情的数据库

    public MovieDetailModelImpl() {
        mDatabase = new MovieDetailBaseHelper(MyApplication.getContext()).getWritableDatabase();
    }


    @Override
    public void getMovieDetail(final OnMovieDetailListener onMovieDetailListener,
            final String itemId) {
        final Cursor cursor = mDatabase.query(MovieDetailTable.NAME, new String[]{Cols.JSON}, "item_id = ?",
                new String[]{itemId}, null, null, null);
        if (cursor.getCount()>0){
            //如果已经有数据了，直接读取
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //耗时操作，在新线程
                    cursor.moveToFirst();
                    String json = cursor.getString(cursor.getColumnIndex(Cols.JSON));
                    MovieDetail movieDetail = JsonUtil.parseJsonToMovieDetail(json);
                    onMovieDetailListener.onSuccess(movieDetail);
                }
            }).start();

        }else {
            //如果数据库没有数据库，向服务器申请数据并存入数据库
            HttpUtil.sendHttpRequest("http://v3.wufazhuce.com:8000/api/movie/{id}/story/1/0", itemId, new OnRequestListener() {
                @Override
                public void onResponse(String response) {
                    //将该影视详情存入数据库
                    mDatabase.insert(MovieDetailTable.NAME, null, getContentValues(itemId, response));
                    MovieDetail movieDetail = JsonUtil.parseJsonToMovieDetail(response);
                    onMovieDetailListener.onSuccess(movieDetail);
                }

                @Override
                public void onError(String errorMsg) {
                    onMovieDetailListener.onFail(errorMsg);
                }

                @Override
                public void onStart() {
                    onMovieDetailListener.onStart();
                }

                @Override
                public void onFinish() {
                    onMovieDetailListener.onFinish();
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
