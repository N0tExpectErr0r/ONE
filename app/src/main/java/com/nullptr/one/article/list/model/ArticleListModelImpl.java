package com.nullptr.one.article.list.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.nullptr.one.ContextApplication;
import com.nullptr.one.article.list.db.ArticleListBaseHelper;
import com.nullptr.one.article.list.db.ArticleListDbSchema.ArticleListTable;
import com.nullptr.one.article.list.db.ArticleListDbSchema.ArticleListTable.Cols;
import com.nullptr.one.bean.Article;
import com.nullptr.one.article.list.IArticleList.ArticleListModel;
import com.nullptr.one.article.list.IArticleList.OnArticleListListener;
import com.nullptr.one.article.list.IArticleList.OnMoreArticleListener;
import com.nullptr.one.bean.Author;
import com.nullptr.one.util.HttpUtil;
import com.nullptr.one.util.JsonUtil;
import com.nullptr.one.util.OnRequestListener;
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
        final Cursor cursor = mDatabase.query(ArticleListTable.NAME, null, null,null,
                null, null, null);
        if (cursor.getCount() > 0) {
            //如果数据库已经有数据
            getListFromLocal(cursor,onArticleListListener);
        }else{
            //如果数据库没有数据库，向服务器申请数据并存入数据库
            cursor.close();
            getListFromNet(onArticleListListener);
        }
    }

    private void getListFromLocal(final Cursor cursor,final OnArticleListListener onArticleListListener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //耗时操作，在新线程
                cursor.moveToFirst();
                List<Article> articleList = new ArrayList<>();
                while (!cursor.isLast()){
                    Article article = new Article();
                    article.setId(cursor.getString(cursor.getColumnIndex(Cols.ID)));
                    article.setItemId(cursor.getString(cursor.getColumnIndex(Cols.ITEM_ID)));
                    article.setTitle(cursor.getString(cursor.getColumnIndex(Cols.TITLE)));
                    article.setForward(cursor.getString(cursor.getColumnIndex(Cols.FORWARD)));
                    article.setImageURL(cursor.getString(cursor.getColumnIndex(Cols.IMG_URL)));
                    article.setLikeCount(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Cols.LIKE_COUNT))));
                    article.setDate(cursor.getString(cursor.getColumnIndex(Cols.DATE)));
                    Author author = new Author();
                    author.setName(cursor.getString(cursor.getColumnIndex(Cols.AUTHOR_NAME)));
                    author.setDesc(cursor.getString(cursor.getColumnIndex(Cols.AUTHOR_DESC)));
                    article.setAuthor(author);
                    articleList.add(article);

                    cursor.moveToNext();
                }
                cursor.close();
                onArticleListListener.onSuccess(articleList);
                onArticleListListener.onFinish();
            }
        }).start();
    }

    @Override
    public void getListFromNet(final OnArticleListListener onArticleListListener) {
        StringBuilder url = new StringBuilder();
        url.append("http://v3.wufazhuce.com:8000/api/channel/reading/more/")
                .append("0")
                .append("?channel=wdj&version=4.0.2&platform=android");
        HttpUtil.sendHttpRequest(url.toString(), new OnRequestListener() {
            @Override
            public void onResponse(String response) {
                List<Article> articleList = JsonUtil.parseJsonToArticleList(response);
                onArticleListListener.onSuccess(articleList);
                for (Article article : articleList) {
                    mDatabase.insert(ArticleListTable.NAME,null,getContentValues(article));
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
        values.put(Cols.ID,article.getId());
        values.put(Cols.ITEM_ID, article.getItemId());
        values.put(Cols.TITLE, article.getTitle());
        values.put(Cols.FORWARD, article.getForward());
        values.put(Cols.IMG_URL,article.getImageUrl());
        values.put(Cols.LIKE_COUNT,String.valueOf(article.getLikeCount()));
        values.put(Cols.DATE,article.getDate());
        values.put(Cols.AUTHOR_NAME,article.getAuthor().getName());
        values.put(Cols.AUTHOR_DESC, article.getAuthor().getDesc());

        return values;
    }

    @Override
    public void getMore(final OnMoreArticleListener onMoreArticleListener, String lastId) {
        StringBuilder url = new StringBuilder();
        url.append("http://v3.wufazhuce.com:8000/api/channel/reading/more/")
                .append(lastId)
                .append("?channel=wdj&version=4.0.2&platform=android");
        HttpUtil.sendHttpRequest(url.toString(), new OnRequestListener() {
            @Override
            public void onResponse(String response) {
                List<Article> articleList = JsonUtil.parseJsonToArticleList(response);
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
