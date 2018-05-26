package com.nullptr.one.moduleMovie.detail.mdoel;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.nullptr.one.ContextApplication;
import com.nullptr.one.bean.Author;
import com.nullptr.one.bean.MovieDetail;
import com.nullptr.one.moduleMovie.detail.IMovieDetail.MovieDetailModel;
import com.nullptr.one.moduleMovie.detail.IMovieDetail.OnMovieDetailListener;
import com.nullptr.one.moduleMovie.detail.db.MovieDetailBaseHelper;
import com.nullptr.one.moduleMovie.detail.db.MovieDetailDbSchema.MovieDetailTable;
import com.nullptr.one.moduleMovie.detail.db.MovieDetailDbSchema.MovieDetailTable.Cols;
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
        mDatabase = new MovieDetailBaseHelper(ContextApplication.getContext()).getWritableDatabase();
    }

    private static ContentValues getContentValues(MovieDetail movie) {
        ContentValues values = new ContentValues();
        values.put(Cols.ITEM_ID, movie.getMovieId());
        values.put(Cols.TITLE, movie.getTitle());
        values.put(Cols.CONTENT, movie.getContent());
        values.put(Cols.AUTHOR_NAME, movie.getAuthor().getName());
        values.put(Cols.AUTHOR_DESC, movie.getAuthor().getDesc());

        return values;
    }

    @Override
    public void getMovieDetail(final OnMovieDetailListener onMovieDetailListener,
            final String itemId) {
        final Cursor cursor = mDatabase.query(MovieDetailTable.NAME, null, "item_id = ?",
                new String[]{itemId}, null, null, null);
        if (cursor.getCount() > 0) {
            //如果已经有数据了，直接读取
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //耗时操作，在新线程
                    cursor.moveToFirst();

                    MovieDetail movie = new MovieDetail();
                    movie.setMovieId(cursor.getString(cursor.getColumnIndex(Cols.ITEM_ID)));
                    movie.setTitle(cursor.getString(cursor.getColumnIndex(Cols.TITLE)));
                    movie.setContent(cursor.getString(cursor.getColumnIndex(Cols.CONTENT)));
                    Author author = new Author();
                    author.setName(cursor.getString(cursor.getColumnIndex(Cols.AUTHOR_NAME)));
                    author.setDesc(cursor.getString(cursor.getColumnIndex(Cols.AUTHOR_DESC)));
                    movie.setAuthor(author);

                    onMovieDetailListener.onSuccess(movie);
                    onMovieDetailListener.onFinish();
                }
            }).start();

        } else {
            //如果数据库没有数据库，向服务器申请数据并存入数据库
            HttpUtil.sendHttpRequest(
                    "http://v3.wufazhuce.com:8000/api/movie/{id}/story/1/0?platform=android",
                    itemId, new OnRequestListener() {
                        @Override
                        public void onResponse(String response) {
                            //将该影视详情存入数据库
                            MovieDetail movieDetail = JsonUtil.parseJsonToMovieDetail(response);
                            mDatabase.insert(MovieDetailTable.NAME, null,
                                    getContentValues(movieDetail));
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

}
