package com.nullptr.one.model.movieinfo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.nullptr.one.MyApplication;
import com.nullptr.one.bean.Movie;
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
        final Cursor cursor = mDatabase.query(MovieInfoTable.NAME, null, "item_id = ?",
                new String[]{itemId}, null, null, null);
        if (cursor.getCount() > 1) {
            //如果已经有数据了，直接读取
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //耗时操作，在新线程
                    cursor.moveToFirst();

                    MovieInfo movieInfo = new MovieInfo();
                    movieInfo.setMovieId(cursor.getString(cursor.getColumnIndex(Cols.ITEM_ID)));
                    movieInfo.setCoverURL(cursor.getString(cursor.getColumnIndex(Cols.COVER_URL)));
                    movieInfo.setMovieTitle(cursor.getString(cursor.getColumnIndex(Cols.MOVIE_TITLE)));
                    movieInfo.setInfo(cursor.getString(cursor.getColumnIndex(Cols.INFO)));
                    movieInfo.setStory(cursor.getString(cursor.getColumnIndex(Cols.STORY)));

                    onMovieInfoListener.onSuccess(movieInfo);
                }
            }).start();
        } else {
            //如果数据库没有数据库，向服务器申请数据并存入数据库
            HttpUtil.sendHttpRequest("http://v3.wufazhuce.com:8000/api/movie/detail/{id}", itemId, new OnRequestListener() {
                @Override
                public void onResponse(String response) {
                    //将该影视信息存入数据库
                    MovieInfo movieInfo = JsonUtil.parseJsonToMovieInfo(response);
                    mDatabase.insert(MovieInfoTable.NAME, null, getContentValues(movieInfo));
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

    private static ContentValues getContentValues(MovieInfo movieInfo) {
        ContentValues values = new ContentValues();
        values.put(Cols.ITEM_ID, movieInfo.getMovieId());
        values.put(Cols.COVER_URL, movieInfo.getCoverURL());
        values.put(Cols.MOVIE_TITLE, movieInfo.getMovieTitle());
        values.put(Cols.INFO, movieInfo.getInfo());
        values.put(Cols.STORY, movieInfo.getStory());

        return values;
    }
}
