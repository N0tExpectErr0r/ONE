package com.nullptr.one.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;
import android.widget.Toast;
import com.nullptr.one.ContextApplication;
import com.nullptr.one.R;
import com.nullptr.one.article.detail.IArticleDetail.ArticleDetailPresenter;
import com.nullptr.one.article.detail.IArticleDetail.ArticleDetailView;
import com.nullptr.one.article.detail.presenter.ArticleDetailPresenterImpl;
import com.nullptr.one.article.list.IArticleList.ArticleListPresenter;
import com.nullptr.one.article.list.IArticleList.ArticleListView;
import com.nullptr.one.article.list.presenter.ArticleListPresenterImpl;
import com.nullptr.one.bean.Article;
import com.nullptr.one.bean.ArticleDetail;
import com.nullptr.one.download.db.DownloadBaseHelper;
import com.nullptr.one.download.db.DownloadDbSchema.DownloadTable;
import com.nullptr.one.download.db.DownloadListBaseHelper;
import com.nullptr.one.download.db.DownloadListDbSchema.DownloadListTable;
import com.nullptr.one.download.db.DownloadListDbSchema.DownloadListTable.Cols;
import com.nullptr.one.main.view.MainActivity;
import java.util.ArrayList;
import java.util.List;

public class DownloadArticleService extends Service implements ArticleListView,ArticleDetailView {
    private static int listIndex;
    private static int detailIndex;
    private static int listProgress;
    private static int detailProgress;
    private static ArticleListPresenter sListPresenter;
    private static ArticleDetailPresenter sDetailPresenter;
    private static SQLiteDatabase sListDatabase;
    private static SQLiteDatabase sDetailDatabase;
    private static NotificationManager sManager;
    private final int NOTIFICATION_ID = 4;
    private List<Article> mArticleList;

    public DownloadArticleService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (sListPresenter == null){
            sListPresenter = new ArticleListPresenterImpl(this);
        }
        if (sListDatabase == null){
            sListDatabase = new DownloadListBaseHelper(ContextApplication.getContext()).getWritableDatabase();
        }
        if (sDetailPresenter == null){
            sDetailPresenter = new ArticleDetailPresenterImpl(this);
        }
        if (sDetailDatabase == null){
            sDetailDatabase = new DownloadBaseHelper(ContextApplication.getContext()).getWritableDatabase();
        }
        if (sManager == null){
            sManager = (NotificationManager) ContextApplication.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        listIndex = 0;
        detailIndex = 0;
        listProgress = 0;
        detailProgress = 0;
        mArticleList = new ArrayList<>();
        cleanDatabase();
        downloadArticleList();
        return super.onStartCommand(intent, flags, startId);
    }

    //清空数据库
    private void cleanDatabase() {
        sListDatabase.delete(DownloadListTable.NAME,null,null);
        sDetailDatabase.delete(DownloadTable.NAME,null,null);
    }

    //存储List到数据库
    private void saveListToDatabase(List<Article> articleList) {
        for (Article article : articleList) {
            sListDatabase.insert(DownloadListTable.NAME,null, getListContentValues(article));
        }
    }

    //获取Notification对象
    private Notification getNotification(String title,int progress){
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
        Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            String channelId = "channel_download";
            NotificationChannel channel = manager.getNotificationChannel(channelId);
            if (channel == null) {
                channel = new NotificationChannel(channelId, "Download Channel", NotificationManager.IMPORTANCE_DEFAULT);
                channel.setDescription("Download Channel");
                manager.createNotificationChannel(channel);
            }
            builder = configBuilder(new Builder(this,channelId),pendingIntent,title,progress);
        }else{
            builder = configBuilder(new Builder(this),pendingIntent,title,progress);
        }
        return builder.build();
    }

    //配置Notification的Builder
    private Builder configBuilder(Builder builder, PendingIntent pendingIntent,String title,int progress){
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setContentTitle(title);
        if (progress>=0){
            builder.setContentText(progress+"%");
            builder.setProgress(100,progress,false);
        }
        return builder;
    }

    //获取列表文章项ContentValues
    private ContentValues getListContentValues(Article article) {
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

    //下载文章
    private void downloadArticleList() {
        sManager.notify(NOTIFICATION_ID,getNotification("正在下载文章列表...",listProgress));
        sListPresenter.updateList();
    }

    //获得首页文章列表
    @Override
    public void setArticleList(List<Article> articleList) {
        listProgress+=20;
        sManager.notify(NOTIFICATION_ID,getNotification("正在下载文章列表...",listProgress));
        String lastId = articleList.get(articleList.size() - 1).getId();
        saveListToDatabase(articleList);
        mArticleList.addAll(articleList);
        sListPresenter.loadMore(lastId);
    }

    //获得更多文章列表
    @Override
    public void addArticleList(List<Article> articleList) {
        if (listIndex < 3){
            listIndex++;
            listProgress+=20;
            sManager.notify(NOTIFICATION_ID,getNotification("正在下载文章列表...",listProgress));
            String lastId = articleList.get(articleList.size() - 1).getId();
            saveListToDatabase(articleList);
            mArticleList.addAll(articleList);
            sListPresenter.loadMore(lastId);
        }else {
            saveListToDatabase(articleList);
            mArticleList.addAll(articleList);
            downloadArticleDetail();
        }
    }

    //下载文章详情
    private void downloadArticleDetail() {
        detailProgress+=2;
        sManager.notify(NOTIFICATION_ID,getNotification("正在下载文章详情...",detailProgress));
        Article article = mArticleList.get(detailIndex);
        sDetailPresenter.getArticleDetail(article.getItemId());
    }

    //得到文章详情
    @Override
    public void setArticle(ArticleDetail article) {
        if (detailIndex < 49){
            detailIndex++;
            detailProgress+=2;
            sManager.notify(NOTIFICATION_ID,getNotification("正在下载文章详情...",detailProgress));
            Article newArticle = mArticleList.get(detailIndex);
            sDetailPresenter.getArticleDetail(newArticle.getItemId());
            saveDetailToDatabase(article);
        }else{
            sManager.notify(NOTIFICATION_ID,getNotification("最近50条文章已全部下载完成！",detailProgress));
            //告诉Activity已下载完成
            Intent intent = new Intent();
            intent.setAction("download");
            intent.putExtra("action_type","OVER");
            sendBroadcast(intent);
        }
    }

    //保存文章详情至数据库
    private void saveDetailToDatabase(ArticleDetail article) {
        sDetailDatabase.insert(DownloadTable.NAME, null,
                getDetailContentValues(article));
    }

    //获取文章详情ContentValues
    private ContentValues getDetailContentValues(ArticleDetail article) {
        ContentValues values = new ContentValues();
        values.put(DownloadTable.Cols.ITEM_ID, article.getContentId());
        values.put(DownloadTable.Cols.TITLE, article.getTitle());
        values.put(DownloadTable.Cols.AUTHOR_NAME, article.getAuthorName());
        values.put(DownloadTable.Cols.AUTHOR_DESC, article.getAuthorDesc());
        values.put(DownloadTable.Cols.CONTENT, article.getContent());
        values.put(DownloadTable.Cols.DATE, article.getDate());
        values.put(DownloadTable.Cols.COPYRIGHT, article.getCopyright());

        return values;
    }

    //出现错误
    @Override
    public void showError(String errorMsg) {
        sManager.notify(NOTIFICATION_ID,getNotification("下载失败!",-1));
        //下载失败，清空数据库
        cleanDatabase();
        //下载失败，发送下载结束的广播
        Intent intent = new Intent();
        intent.setAction("download");
        intent.putExtra("action_type","OVER");
        sendBroadcast(intent);
    }

    @Override
    public void showLoading() {
    }


    @Override
    public void hideLoading() {

    }
}
