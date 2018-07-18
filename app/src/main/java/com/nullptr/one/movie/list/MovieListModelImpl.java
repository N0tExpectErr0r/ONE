package com.nullptr.one.movie.list;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.nullptr.one.ContextApplication;
import com.nullptr.one.movie.list.Author;
import com.nullptr.one.movie.list.IMovieList.MovieListModel;
import com.nullptr.one.movie.list.IMovieList.OnMoreMovieListener;
import com.nullptr.one.movie.list.IMovieList.OnMovieListListener;
import com.nullptr.one.movie.list.MovieListDbSchema.MovieListTable;
import com.nullptr.one.movie.list.MovieListDbSchema.MovieListTable.Cols;
import com.nullptr.one.net.HttpListener;
import com.nullptr.one.net.Request;
import com.nullptr.one.net.RequestExecutor;
import com.nullptr.one.net.Response;
import com.nullptr.one.util.JsonUtil;
import com.nullptr.one.util.UrlUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * Model层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/17
 * @DESCRIPTION MovieModel实现类
 */
public class MovieListModelImpl implements MovieListModel {

    private SQLiteDatabase mDatabase;   //本地缓存影视列表的数据库

    public MovieListModelImpl() {
        mDatabase = new MovieListBaseHelper(ContextApplication.getContext()).getWritableDatabase();
    }


    @Override
    public void getList(final OnMovieListListener onMovieListListener) {
        final Cursor cursor = mDatabase.query(MovieListTable.NAME, null, null,null,
                null, null, null);
        if (cursor.getCount() > 0) {
            //如果数据库已经有数据
            getListFromLocal(cursor,onMovieListListener);
        }else{
            //如果数据库没有数据库，向服务器申请数据并存入数据库
            cursor.close();
            getListFromNet(onMovieListListener);
        }

    }

    private void getListFromLocal(final Cursor cursor, final OnMovieListListener onMovieListListener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //耗时操作，在新线程
                cursor.moveToFirst();
                List<Movie> movieList = new ArrayList<>();
                while (!cursor.isLast()){
                    movieList.add(getMovie(cursor));
                    cursor.moveToNext();
                }
                movieList.add(getMovie(cursor));
                cursor.close();
                onMovieListListener.onSuccess(movieList);
                onMovieListListener.onFinish();
            }
        }).start();
    }

    private Movie getMovie(Cursor cursor) {
        Movie movie = new Movie();
        movie.setId(cursor.getString(cursor.getColumnIndex(Cols.ID)));
        movie.setItemId(cursor.getString(cursor.getColumnIndex(Cols.ITEM_ID)));
        movie.setTitle(cursor.getString(cursor.getColumnIndex(Cols.TITLE)));
        movie.setSubTitle(cursor.getString(cursor.getColumnIndex(Cols.SUBTITLE)));
        movie.setForward(cursor.getString(cursor.getColumnIndex(Cols.FORWARD)));
        movie.setImageURL(cursor.getString(cursor.getColumnIndex(Cols.IMG_URL)));
        movie.setDate(cursor.getString(cursor.getColumnIndex(Cols.DATE)));
        Author author = new Author();
        author.setName(cursor.getString(cursor.getColumnIndex(Cols.AUTHOR_NAME)));
        author.setDesc("");
        movie.setAuthor(author);
        return movie;
    }

    @Override
    public void getListFromNet(final OnMovieListListener onMovieListListener){
        Request request = new Request(UrlUtil.getMovieListUrl("0"));
        RequestExecutor.getInstance().execute(request, new HttpListener() {
            @Override
            public void onResponse(Response response) {
                List<Movie> movieList = JsonUtil.parseJsonToMovieList(response.getResult());
                mDatabase.delete(MovieListTable.NAME,null,null);
                for (Movie movie : movieList) {
                    mDatabase.insert(MovieListTable.NAME,null,getContentValues(movie));
                }
                onMovieListListener.onSuccess(movieList);
            }

            @Override
            public void onError(String errorMsg) {
                onMovieListListener.onFail(errorMsg);
            }

            @Override
            public void onStart() {
                onMovieListListener.onStart();
            }

            @Override
            public void onFinish() {
                onMovieListListener.onFinish();
            }
        });
    }

    private ContentValues getContentValues(Movie movie) {
        ContentValues values = new ContentValues();
        values.put(Cols.ID,movie.getId());
        values.put(Cols.ITEM_ID, movie.getItemId());
        values.put(Cols.TITLE, movie.getTitle());
        values.put(Cols.SUBTITLE,movie.getSubTitle());
        values.put(Cols.FORWARD, movie.getForward());
        values.put(Cols.IMG_URL,movie.getImageURL());
        values.put(Cols.DATE,movie.getDate());
        values.put(Cols.AUTHOR_NAME, movie.getAuthor().getName());

        return values;
    }

    @Override
    public void getMore(final OnMoreMovieListener onMoreMovieListener, String lastId) {

        Request request = new Request(UrlUtil.getMovieListUrl(lastId));
        RequestExecutor.getInstance().execute(request, new HttpListener() {
            @Override
            public void onResponse(Response response) {
                List<Movie> movieList = JsonUtil.parseJsonToMovieList(response.getResult());
                onMoreMovieListener.onMoreSuccess(movieList);
            }

            @Override
            public void onError(String errorMsg) {
                onMoreMovieListener.onFail(errorMsg);
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onFinish() {
            }
        });
    }


}
