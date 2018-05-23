package com.nullptr.one.model.articledetail;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.nullptr.one.MyApplication;
import com.nullptr.one.bean.ArticleDetail;
import com.nullptr.one.db.ArticleBaseHelper;
import com.nullptr.one.db.ArticleDbSchema.ArticleTable;
import com.nullptr.one.db.ArticleDbSchema.ArticleTable.Cols;
import com.nullptr.one.util.HttpUtil;
import com.nullptr.one.util.JsonUtil;
import com.nullptr.one.util.OnRequestListener;

/**
 * Model层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/12
 * @DESCRIPTION ArticleDetailModel接口实现类
 */
public class ArticleDetailModelImpl implements ArticleDetailModel {

    private SQLiteDatabase mDatabase;   //本地缓存文章详情的数据库

    public ArticleDetailModelImpl() {
        mDatabase = new ArticleBaseHelper(MyApplication.getContext()).getWritableDatabase();
    }

    @Override
    public void getArticleDetail(final OnArticleDetailListener onArticleDetailListener,
            final String itemId) {
        final Cursor cursor = mDatabase
                .query(ArticleTable.NAME, null, "item_id = ?",
                        new String[]{itemId}, null, null, null);
        if (cursor.getCount() > 0) {
            //如果已经有数据了，直接读取
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //耗时操作，在新线程
                    cursor.moveToFirst();

                    //创建对象
                    ArticleDetail article = new ArticleDetail();
                    article.setContentId(cursor.getString(cursor.getColumnIndex(Cols.ITEM_ID)));
                    article.setTitle(cursor.getString(cursor.getColumnIndex(Cols.TITLE)));
                    article.setAuthorName(cursor.getString(cursor.getColumnIndex(Cols.AUTHOR_NAME)));
                    article.setAuthorDesc(cursor.getString(cursor.getColumnIndex(Cols.AUTHOR_DESC)));
                    article.setContent(cursor.getString(cursor.getColumnIndex(Cols.CONTENT)));
                    article.setDate(cursor.getString(cursor.getColumnIndex(Cols.DATE)));
                    article.setCopyright(cursor.getString(cursor.getColumnIndex(Cols.COPYRIGHT)));

                    onArticleDetailListener.onSuccess(article);
                    onArticleDetailListener.onFinish();
                }
            }).start();
        } else {
            //如果数据库没有数据库，向服务器申请数据并存入数据库
            HttpUtil.sendHttpRequest("http://v3.wufazhuce.com:8000/api/essay/{id}", itemId,
                    new OnRequestListener() {
                        @Override
                        public void onResponse(String response) {
                            //将该文章详情存入数据库
                            ArticleDetail articleDetail = JsonUtil
                                    .parseJsonToArticleDetail(response);
                            mDatabase.insert(ArticleTable.NAME, null,
                                    getContentValues(articleDetail));
                            onArticleDetailListener.onSuccess(articleDetail);
                        }

                        @Override
                        public void onError(String errorMsg) {
                            onArticleDetailListener.onFail(errorMsg);
                        }

                        @Override
                        public void onStart() {
                            onArticleDetailListener.onStart();
                        }

                        @Override
                        public void onFinish() {
                            onArticleDetailListener.onFinish();
                        }
                    });
        }
    }

    private static ContentValues getContentValues(ArticleDetail article) {
        ContentValues values = new ContentValues();
        values.put(Cols.ITEM_ID, article.getContentId());
        values.put(Cols.TITLE,article.getTitle());
        values.put(Cols.AUTHOR_NAME, article.getAuthorName());
        values.put(Cols.AUTHOR_DESC, article.getAuthorDesc());
        values.put(Cols.CONTENT,article.getContent());
        values.put(Cols.DATE,article.getDate());
        values.put(Cols.COPYRIGHT,article.getCopyright());

        return values;
    }

}
