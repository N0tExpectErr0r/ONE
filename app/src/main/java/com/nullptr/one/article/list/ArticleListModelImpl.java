package com.nullptr.one.article.list;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.nullptr.one.ContextApplication;
import com.nullptr.one.article.list.ArticleListDbSchema.ArticleListTable;
import com.nullptr.one.article.list.ArticleListDbSchema.ArticleListTable.Cols;
import com.nullptr.one.article.list.IArticleList.ArticleListModel;
import com.nullptr.one.article.list.IArticleList.OnArticleListListener;
import com.nullptr.one.article.list.IArticleList.OnMoreArticleListener;
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
 * @DATE 创建时间: 2018/5/12
 * @DESCRIPTION ArticleModel实现类
 */
public class ArticleListModelImpl implements ArticleListModel {

    private SQLiteDatabase mDatabase;   //本地缓存文章列表的数据库

    public ArticleListModelImpl() {
        mDatabase = new ArticleListBaseHelper(ContextApplication.getContext()).getWritableDatabase();
    }

    @Override
    public void getList(final OnArticleListListener onArticleListListener) {
        final Cursor cursor = mDatabase.query(ArticleListTable.NAME, null, null, null,
                null, null, null);
        if (cursor.getCount() > 0) {
            //如果数据库已经有数据
            getListFromLocal(cursor, onArticleListListener);
        } else {
            //如果数据库没有数据库，向服务器申请数据并存入数据库
            cursor.close();
            getListFromNet(onArticleListListener);
        }
    }

    private void getListFromLocal(final Cursor cursor, final OnArticleListListener onArticleListListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //耗时操作，在新线程
                cursor.moveToFirst();
                List<Article> articleList = new ArrayList<>();
                while (!cursor.isLast()) {
                    articleList.add(getArticle(cursor));
                    cursor.moveToNext();
                }
                articleList.add(getArticle(cursor));
                cursor.close();
                onArticleListListener.onSuccess(articleList);
                onArticleListListener.onFinish();
            }
        }).start();
    }

    private Article getArticle(Cursor cursor) {
        Article article = new Article();
        article.setId(cursor.getString(cursor.getColumnIndex(Cols.ID)));
        article.setItemId(cursor.getString(cursor.getColumnIndex(Cols.ITEM_ID)));
        article.setTitle(cursor.getString(cursor.getColumnIndex(Cols.TITLE)));
        article.setForward(cursor.getString(cursor.getColumnIndex(Cols.FORWARD)));
        article.setImageUrl(cursor.getString(cursor.getColumnIndex(Cols.IMG_URL)));
        article.setLikeCount(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Cols.LIKE_COUNT))));
        article.setDate(cursor.getString(cursor.getColumnIndex(Cols.DATE)));
        Author author = new Author();
        author.setName(cursor.getString(cursor.getColumnIndex(Cols.AUTHOR_NAME)));
        author.setDesc(cursor.getString(cursor.getColumnIndex(Cols.AUTHOR_DESC)));
        article.setAuthor(author);
        return article;
    }

    @Override
    public void getListFromNet(final OnArticleListListener onArticleListListener) {
        Request request = new Request(UrlUtil.getArticleListUrl("0"));
        RequestExecutor.getInstance().execute(request, new HttpListener() {
            @Override
            public void onResponse(Response response) {
                List<Article> articleList = JsonUtil.parseJsonToArticleList(response.getResult());
                mDatabase.delete(ArticleListTable.NAME, null, null);
                for (Article article : articleList) {
                    mDatabase.insert(ArticleListTable.NAME, null, getContentValues(article));
                }
                onArticleListListener.onSuccess(articleList);
            }

            @Override
            public void onError(String errorMsg) {
                onArticleListListener.onFail(errorMsg);
            }

            @Override
            public void onStart() {
                onArticleListListener.onStart();
            }

            @Override
            public void onFinish() {
                onArticleListListener.onFinish();
            }
        });
    }

    private ContentValues getContentValues(Article article) {
        ContentValues values = new ContentValues();
        values.put(Cols.ID, article.getId());
        values.put(Cols.ITEM_ID, article.getItemId());
        values.put(Cols.TITLE, article.getTitle());
        values.put(Cols.FORWARD, article.getForward());
        values.put(Cols.IMG_URL, article.getImageUrl());
        values.put(Cols.LIKE_COUNT, String.valueOf(article.getLikeCount()));
        values.put(Cols.DATE, article.getDate());
        values.put(Cols.AUTHOR_NAME, article.getAuthor().getName());
        values.put(Cols.AUTHOR_DESC, article.getAuthor().getDesc());

        return values;
    }

    @Override
    public void getMore(final OnMoreArticleListener onMoreArticleListener, String lastId) {
        Request request = new Request(UrlUtil.getArticleListUrl(lastId));
        RequestExecutor.getInstance().execute(request, new HttpListener() {
            @Override
            public void onResponse(Response response) {
                List<Article> articleList = JsonUtil.parseJsonToArticleList(response.getResult());
                onMoreArticleListener.onMoreSuccess(articleList);
            }

            @Override
            public void onError(String errorMsg) {
                onMoreArticleListener.onFail(errorMsg);
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
