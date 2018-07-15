package com.nullptr.one.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import com.nullptr.one.ContextApplication;
import com.nullptr.one.article.list.IArticleList.ArticleListPresenter;
import com.nullptr.one.article.list.IArticleList.ArticleListView;
import com.nullptr.one.article.list.presenter.ArticleListPresenterImpl;
import com.nullptr.one.bean.Article;
import com.nullptr.one.bean.Movie;
import com.nullptr.one.bean.Music;
import com.nullptr.one.movie.list.IMovieList.MovieListPresenter;
import com.nullptr.one.movie.list.IMovieList.MovieListView;
import com.nullptr.one.movie.list.presenter.MovieListPresenterImpl;
import com.nullptr.one.music.list.IMusicList.MusicListPresenter;
import com.nullptr.one.music.list.IMusicList.MusicListView;
import com.nullptr.one.music.list.presenter.MusicListPresenterImpl;
import com.nullptr.one.util.UpdateNotificationFactory;
import java.util.List;

public class AutoUpdateService extends Service implements MusicListView, ArticleListView, MovieListView {

    private final static int ARTICLE_ID = 1;
    private final static int MUSIC_ID = 2;
    private final static int MOVIE_ID = 3;

    private static MusicListPresenter sMusicListPresenter;
    private static MovieListPresenter sMovieListPresenter;
    private static ArticleListPresenter sArticleListPresenter;
    private static NotificationManager sManager;

    @Override
    public void onCreate() {
        super.onCreate();
        if (sMusicListPresenter == null) {
            sMusicListPresenter = new MusicListPresenterImpl(this);
        }
        if (sMovieListPresenter == null) {
            sMovieListPresenter = new MovieListPresenterImpl(this);
        }
        if (sArticleListPresenter == null) {
            sArticleListPresenter = new ArticleListPresenterImpl(this);
        }
        if (sManager == null) {
            sManager = (NotificationManager) ContextApplication.getContext()
                    .getSystemService(Context.NOTIFICATION_SERVICE);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean isStart = intent.getBooleanExtra("isStart", false);
        if (!isStart) {
            //不是启动MainActivity时启动的服务才调用
            sArticleListPresenter.updateList();
            sMovieListPresenter.updateList();
            sMusicListPresenter.updateList();
        }
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int loopTime = 4*60*60 * 1000;   //每4小时刷新一次
        long triggerAtTime = SystemClock.elapsedRealtime() + loopTime;
        Intent newIntent = new Intent(this, AutoUpdateService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, newIntent, 0);
        manager.cancel(pendingIntent);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pendingIntent);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void setArticleList(List<Article> articleList) {
        Article firstArticle = articleList.get(0);
        Notification notification = UpdateNotificationFactory
                .createNotification(UpdateNotificationFactory.TYPE_ARTICLE, firstArticle.getTitle(),
                        firstArticle.getForward(),firstArticle.getItemId());
        sManager.notify(ARTICLE_ID, notification);
    }

    @Override
    public void setMovieList(List<Movie> movieList) {
        Movie firstMovie = movieList.get(0);
        Notification notification = UpdateNotificationFactory
                .createNotification(UpdateNotificationFactory.TYPE_MOVIE, firstMovie.getTitle(),
                        firstMovie.getForward(),firstMovie.getItemId());
        sManager.notify(MOVIE_ID, notification);
    }

    @Override
    public void setMusicList(List<Music> musicList) {
        Music firstMusic = musicList.get(0);
        Notification notification = UpdateNotificationFactory
                .createNotification(UpdateNotificationFactory.TYPE_MUSIC, firstMusic.getTitle(),
                        firstMusic.getForward(),firstMusic.getItemId());
        sManager.notify(MUSIC_ID, notification);
    }

    @Override
    public void addArticleList(List<Article> articleList) {
    }

    @Override
    public void addMovieList(List<Movie> movieList) {
    }

    @Override
    public void addMusicList(List<Music> musicList) {
    }

    @Override
    public void showError(String errorMsg) {
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }
}
